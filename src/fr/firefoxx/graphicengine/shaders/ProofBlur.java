package fr.firefoxx.graphicengine.shaders;

import java.util.ArrayList;

import fr.firefoxx.graphicengine.display.Frame;
import fr.firefoxx.graphicengine.data.MyColor;
import fr.firefoxx.graphicengine.display.Renderer;

public class ProofBlur extends Blur {
	private final double dist;
	
	public ProofBlur(double f, double dist, Frame<MyColor> frm, Renderer rd) {
		super(f, frm, rd);
		this.dist = dist;
	}

	@Override
	public MyColor apply(int x, int y) {
		int lim = (int) (pxDist * Math.abs(dist - rd.Zbuffer.get(x, y)));
		return blur(x,y,lim < 1000 ? lim:0);
	}
	

	@Override
	protected MyColor blur(int x, int y, int dist) {
		ArrayList<MyColor> mean = new ArrayList<MyColor>();
		
		for (int ix = -dist; ix<=dist; ix++) {
			for (int iy = -dist; iy<=dist; iy++) {
				if (rd.Zbuffer.getSafe(x+ix, y+iy) >= rd.Zbuffer.get(x, y)) {
					mean.add(frm.getSafe(x+ix, y+iy));
				}
				
			}
		}
		return MyColor.mean(mean);
	}
}
