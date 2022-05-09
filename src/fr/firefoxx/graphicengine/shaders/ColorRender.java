package fr.firefoxx.graphicengine.shaders;

import fr.firefoxx.graphicengine.data.MyColor;
import fr.firefoxx.graphicengine.display.Renderer;
import fr.firefoxx.graphicengine.other.MyScene;

public class ColorRender extends PostProcessEffect {
	private Renderer rd;
	
	public ColorRender(MyScene scene,Renderer rd) {
		super();
		this.rd = rd;
	}
	@Override
	public MyColor apply(int x, int y) {
		return rd.colorBuffer.get(x, y);
	}

}
