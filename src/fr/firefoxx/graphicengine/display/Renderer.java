package fr.firefoxx.graphicengine.display;

import java.util.ArrayList;
import java.util.List;

import fr.firefoxx.graphicengine.data.MyColor;
import fr.firefoxx.graphicengine.dimention3.math.Ray3;
import fr.firefoxx.graphicengine.dimention3.math.Vector3;
import fr.firefoxx.graphicengine.dimention3.myobject.MyObject;
import fr.firefoxx.graphicengine.other.MyScene;
import fr.firefoxx.graphicengine.other.TimeAnalyse;
import fr.firefoxx.graphicengine.shaders.Blur;
import fr.firefoxx.graphicengine.shaders.ColorRender;
import fr.firefoxx.graphicengine.shaders.NoiseReduction;
import fr.firefoxx.graphicengine.shaders.ProjectedShadow;
import fr.firefoxx.graphicengine.shaders.ProofBlur;
import fr.firefoxx.graphicengine.shaders.SSAOv3;
import fr.firefoxx.graphicengine.shaders.YDepth;
import fr.firefoxx.graphicengine.shaders.ZDepth;
import javafx.scene.image.PixelWriter;

public class Renderer implements Runnable {;
	private final int xPxResolution;
	private final int yPxResolution;
	private final int tileResolution;
	
	public final Frame<Double> Zbuffer;
	public final Frame<MyColor> colorBuffer;
	public final Frame<Vector3> pointBuffer;
	public final Frame<Vector3> normalBuffer;
	public final Frame<MyObject> objBuffer;
	
	private final PixelWriter writer;
	private final MyScene scene;
	private final Cam3 cam;
	private final boolean DARKMOD;
	
	private final TimeAnalyse TM;
	{
		TM = new TimeAnalyse();
	}
	public Renderer(PixelWriter writer, int xPxResolution, int yPxResolution, int tileResolution) {
		DARKMOD = false;
		this.xPxResolution = xPxResolution;
		this.yPxResolution = yPxResolution;
		this.tileResolution = tileResolution;
		colorBuffer = new Frame<MyColor>(xPxResolution, yPxResolution, DARKMOD? MyColor.BLACK():MyColor.WHITE(), MyColor.class);
		pointBuffer = new Frame<Vector3>(xPxResolution, yPxResolution, null, Vector3.class);
		Zbuffer = new Frame<Double>(xPxResolution, yPxResolution, Double.MAX_VALUE, Double.class);
		normalBuffer = new Frame<Vector3>(xPxResolution, yPxResolution, null, Vector3.class);
		objBuffer = new Frame<MyObject>(xPxResolution, yPxResolution, null, MyObject.class);
		
		this.writer = writer;
		double FOV = 0.5F;
		this.cam = new Cam3(xPxResolution,yPxResolution,FOV);
		
		this.scene = new MyScene(cam);
		this.scene.loadScene("C:/Users/Erwan/Desktop/Erwan Engine/Scene/nature.obj");
		//this.scene.addRGBLights();
		//this.scene.addSuns();;
		//this.scene.addPlane();
		this.scene.addCam();
		this.scene.addLights();
	}
	
	@Override
	public void run() {
		Frame<MyColor> Rendering = new Frame<MyColor>(xPxResolution, yPxResolution, MyColor.NULL(), MyColor.class);
		List<Integer[]> listOfNumbers = new ArrayList<Integer[]>();
		for(int y = 0; y<yPxResolution; y++) {
			for(int x = 0; x<xPxResolution; x++) {
				listOfNumbers.add(new Integer[] {x,y});
			}
		}
		TM.elapsedTime("Init");
		

		listOfNumbers.parallelStream().forEach(n ->
			throwRay(n[0], n[1])
		);
		TM.elapsedTime("RayTracing");

		ColorRender Color = new ColorRender(scene,this);
		listOfNumbers.parallelStream().forEach(n ->
				Rendering.set(n[0], n[1], Color.apply(n[0], n[1]))
		);
		TM.elapsedTime("ColorRender");

		ProjectedShadow Shadow = new ProjectedShadow(scene,this);
		listOfNumbers.parallelStream().forEach(n ->
				Rendering.set(n[0], n[1], Rendering.get(n[0], n[1])
						.CastDark(Shadow.apply(n[0], n[1]), DARKMOD ? 0.99F: 0.6F)
				)
		);
		TM.elapsedTime("Shadows");

		SSAOv3 AOv3 = new SSAOv3(0.7f, 100, scene, this);
		Frame<MyColor> SSAO = new Frame<MyColor>(xPxResolution, yPxResolution, MyColor.NULL(), MyColor.class);
		listOfNumbers.parallelStream().forEach(n ->
				SSAO.set(n[0], n[1],AOv3.apply(n[0], n[1]))
		);

		NoiseReduction SSAONoiseCorrected = new NoiseReduction(10/tileResolution, SSAO, objBuffer,this);
		listOfNumbers.parallelStream().forEach(n -> 
			{
				MyColor SSAORedMask = SSAONoiseCorrected.apply(n[0], n[1]);
				if (DARKMOD) {
					Rendering.set(n[0], n[1], Rendering.get(n[0], n[1])
						.CastDark(SSAORedMask, 1.0F)
						.CastDark(SSAORedMask, 0.4F)
					);
				} else {
					Rendering.set(n[0], n[1], Rendering.get(n[0], n[1])
						.CastDark(SSAORedMask, 0.3F)
						.CastWhite(SSAORedMask, 0.3F)
					);
				}
			}
		);
		TM.elapsedTime("SSAO");
		
		YDepth YBuffer = new YDepth(0, 5.5f, this);
		listOfNumbers.parallelStream().forEach(n ->
			{
				MyColor YBufferMask = YBuffer.apply(n[0], n[1]);
				if (DARKMOD) {
					Rendering.set(n[0], n[1], Rendering.get(n[0], n[1])
						.CastDark(YBufferMask.inverse(), 0.4F)
					);
				} else {
					Rendering.set(n[0], n[1], Rendering.get(n[0], n[1])
						//.CastWhite(YBufferMask.inverse(), 0.9F)
					);
				}
			}
		);
		TM.elapsedTime("YBuffer");

		ZDepth ZBuffer = new ZDepth(3, 15, this);
		listOfNumbers.parallelStream().forEach(n ->
			{
				if (DARKMOD) {
					Rendering.set(n[0], n[1], Rendering.get(n[0], n[1])
						.CastDark(ZBuffer.apply(n[0], n[1]).inverse(), 0.6F)
					);
				} else {
					Rendering.set(n[0], n[1], Rendering.get(n[0], n[1])
						//.CastWhite(ZBuffer.apply(n[0], n[1]), 0.8F)
					);
				}
			}
		);
		TM.elapsedTime("ZBuffer");

		Blur blur = new Blur(10, Rendering, this);
		listOfNumbers.parallelStream().forEach(n -> 
			{
				//Rendering.set(n[0], n[1], blur.apply(n[0], n[1]));
			}
		);
		TM.elapsedTime("BlurMask");

		ProofBlur pb = new ProofBlur(0.4f/tileResolution, 5, Rendering, this);
		listOfNumbers.parallelStream().forEach(n ->
				Rendering.set(n[0], n[1], pb.apply(n[0], n[1]))
		);
		TM.elapsedTime("ProofBlur");
		
		
		//display
		printOnScreen(1,tileResolution, Rendering);
		TM.elapsedTime("display");
		TM.timeSinceStard();
		
	}
	
	private void printOnScreen(int a, int tileRes, Frame<MyColor> frame) {
		for(int y = 0, yinv = yPxResolution-a; y<yPxResolution && yinv>=0; y+=a, yinv-=a) {
			for(int x = 0; x<xPxResolution; x+=a) {
				for (int xoff = 0; xoff<tileRes; xoff++) {
					for (int yoff = 0; yoff<tileRes; yoff++) {
						writer.setColor(x*tileRes + xoff, yinv*tileRes + yoff, MyColor.toJavaFXColor(frame.get(x,y)));
					}
				}
			}
		}
	}
	
	public int getxPxResolution() {
		return xPxResolution;
	}

	public int getyPxResolution() {
		return yPxResolution;
	}
	
	private void throwRay(int x, int y) {//TODO: renvoyer un objet en retour
		for (MyObject obj: scene.getObjectList()) {
			Ray3 r = cam.getRay(x,y);
			Vector3 p = obj.getIntersectionPoint(r);
			if (p != null) {
				if (Vector3.distance(cam.getOrigin(), p) <= Zbuffer.get(x,y)) {
					pointBuffer.set(x,y,p);
					objBuffer.set(x, y, obj);
					normalBuffer.set(x, y, obj.getNormal(p));
					colorBuffer.set(x, y, obj.getPointColor(p));
					Zbuffer.set(x,y,Vector3.distance(cam.getOrigin(), p));
				}
			}
		}
	}
}
