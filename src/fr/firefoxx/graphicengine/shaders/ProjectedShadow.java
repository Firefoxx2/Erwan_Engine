package fr.firefoxx.graphicengine.shaders;

import fr.firefoxx.graphicengine.dimention3.light.Light;
import fr.firefoxx.graphicengine.data.MyColor;
import fr.firefoxx.graphicengine.display.Renderer;
import fr.firefoxx.graphicengine.other.MyScene;

public class ProjectedShadow extends InProcessEffect{
	private Renderer rd;
	private MyScene scene;
	
	public ProjectedShadow(MyScene scene, Renderer rd) {
		super();
		this.rd = rd;
		this.scene = scene;
	}

	@Override
	public MyColor apply(int x, int y) {
		MyColor lightVal = MyColor.BLACK();
		if (rd.pointBuffer.get(x, y) != null) {
			for (Light l: scene.getLightList()) {
				//lightVal += (1-lightVal) * l.lightValue(rd.pointBuffer.get(x, y), scene.getObjectList());
				//lightsVals.add(l.lightValue(rd.pointBuffer.get(x, y), scene.getObjectList()));
				MyColor MC2 = lightVal.inverse().multiply(l.lightValue(rd.pointBuffer.get(x, y), rd.normalBuffer.get(x, y), scene.getObjectList()));
				
				lightVal = new MyColor(lightVal.getRed() + MC2.getRed(),
						lightVal.getGreen() + MC2.getGreen(),
						lightVal.getBlue() + MC2.getBlue(),
						lightVal.getOpacity() * MC2.getOpacity()
						);
				
			}
		} else {
			lightVal = MyColor.WHITE();
		}
		return lightVal;
		
	}

}
