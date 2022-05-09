package fr.firefoxx.graphicengine.rendering.shaders;

import fr.firefoxx.graphicengine.data.MyColor;
import fr.firefoxx.graphicengine.rendering.Renderer;

public class ZDepth extends PostProcessEffect {
	private Renderer rd;
	private double min, max;
	
	public ZDepth(double min, double max, Renderer rd) {
		super();
		this.rd = rd;
		this.min = min;
		this.max = max;
	}

	@Override
	public MyColor apply(int x, int y) {
		float temp;
		if (rd.Zbuffer.get(x, y) < min) {
			temp = 0;
		} else if (rd.Zbuffer.get(x, y) > max) {
			temp = 1;
		} else {
			temp = (float) ((rd.Zbuffer.get(x, y) - min)/(max-min));
		}
		return MyColor.GREY(temp, temp);
	}

}
