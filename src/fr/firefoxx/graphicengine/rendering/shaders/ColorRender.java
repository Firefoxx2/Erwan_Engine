package fr.firefoxx.graphicengine.rendering.shaders;

import fr.firefoxx.graphicengine.data.MyColor;
import fr.firefoxx.graphicengine.rendering.MyScene;
import fr.firefoxx.graphicengine.rendering.Renderer;

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
