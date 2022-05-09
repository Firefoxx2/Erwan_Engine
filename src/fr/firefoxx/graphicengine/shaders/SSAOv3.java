package fr.firefoxx.graphicengine.shaders;

import fr.firefoxx.graphicengine.dimention3.math.Vector3;
import fr.firefoxx.graphicengine.data.MyColor;
import fr.firefoxx.graphicengine.display.Renderer;
import fr.firefoxx.graphicengine.other.MyScene;

public class SSAOv3 extends PostProcessEffect {
	private double maxRange;
	private int nbIteration;
	private MyScene scene;
	private Renderer rd;
	
	public SSAOv3(double maxRange, int nbIteration, MyScene scene, Renderer rd) {
		super();
		this.rd = rd;
		this.maxRange = maxRange;
		this.nbIteration = nbIteration;
		this.scene = scene;
	}
	
	@Override
	public MyColor apply(int x, int y) {
		double count = 0;
		double sumRange = 0;
		if (rd.pointBuffer.get(x,y) != null) {
			for (int i = 0; i< nbIteration; i++) {
				double rdNumber = Math.random();
				double range = rdNumber * maxRange;
				Vector3 p = rd.pointBuffer.get(x,y).add(Vector3.newRandomUnitVector().mutliply(range));
				double coord[] = scene.getCam().throwOnScreen(p);
				if (coord != null) {
					int xc = (int) coord[0];
					int yc = (int) coord[1];
					double dist = Vector3.distance(p, scene.getCam().getOrigin());
					if (dist - rd.Zbuffer.get(xc,yc) < 4*range) {
						sumRange += 1.0f-rdNumber;
						if (dist < rd.Zbuffer.get(xc,yc)) {
							count += 1.0f-rdNumber;
						}
					}
				}
			}
			return MyColor.GREY((float) (count/sumRange));
		} else {
			return MyColor.GREY(1.0f);
		}
	}
	
}
