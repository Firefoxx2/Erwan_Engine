package fr.firefoxx.graphicengine.rendering.shaders;

import java.util.ArrayList;

import fr.firefoxx.graphicengine.data.Frame;
import fr.firefoxx.graphicengine.data.MyColor;
import fr.firefoxx.graphicengine.rendering.Renderer;

public class Blur extends PostProcessEffect {
	protected final Renderer rd;
	protected final Frame<MyColor> frm;
	protected final double pxDist;
	
	public Blur(double pxDist, Frame<MyColor> frm, Renderer rd) {
		super();
		this.rd = rd;
		this.frm = frm;
		this.pxDist = pxDist;
	}
	@Override
	public MyColor apply(int x, int y) {
		return blur(x,y,(int) pxDist);
	}
	
	protected MyColor blur(int x, int y, int dist) {
		ArrayList<MyColor> mean = new ArrayList<MyColor>();
		for (int ix = -dist; ix<=dist; ix++) {
			for (int iy = -dist; iy<=dist; iy++) {
				mean.add(frm.getSafe(x+ix, y+iy));
			}
		}
		return MyColor.mean(mean);
	}
}
