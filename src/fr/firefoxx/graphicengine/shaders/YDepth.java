package fr.firefoxx.graphicengine.shaders;

import fr.firefoxx.graphicengine.data.MyColor;
import fr.firefoxx.graphicengine.display.Renderer;

public class YDepth extends PostProcessEffect{
	private Renderer rd;
	private double min, max;
	
	public YDepth(double min, double max, Renderer rd) {
		super();
		this.rd = rd;
		this.min = min;
		this.max = max;
	}

	@Override
	public MyColor apply(int x, int y) {
		float temp;
		if (rd.pointBuffer.get(x, y) != null) {
			double h = rd.pointBuffer.get(x, y).getY();
			if (h < min) {
				temp = 1;
			} else if (h > max) {
				temp = 0;
			} else {
				temp = (float) ((max - h)/(max-min));
			}
		} else {
			temp = 0;
		}
		return MyColor.GREY(temp, temp);
	}
}
