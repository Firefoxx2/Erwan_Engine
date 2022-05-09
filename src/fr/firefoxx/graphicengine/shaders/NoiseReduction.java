package fr.firefoxx.graphicengine.shaders;

import java.util.ArrayList;

import fr.firefoxx.graphicengine.display.Frame;
import fr.firefoxx.graphicengine.data.MyColor;
import fr.firefoxx.graphicengine.display.Renderer;

public class NoiseReduction extends Blur {
	private Frame<?> refFrame;
	
	public NoiseReduction(double pxDist, Frame<MyColor> frm, Frame<?> ref, Renderer rd) {
		super(pxDist, frm, rd);
		this.refFrame = ref;
	}

	@Override
	protected MyColor blur(int x, int y, int dist) {
		ArrayList<MyColor> mean = new ArrayList<MyColor>();
		for (int ix = -dist; ix<=dist; ix++) {
			for (int iy = -dist; iy<=dist; iy++) {
				if (equals(refFrame.get(x, y), (refFrame.getSafe(x+ix, y+iy)))) {
					mean.add(frm.getSafe(x+ix, y+iy));
				}
			}
		}
		return MyColor.mean(mean);
	}
	
	public static boolean equals(Object obj1, Object obj2) {
		if (obj1 == obj2)
			return true;
		if (obj1 == null)
			return false;
		if (obj2 == null)
			return false;
		if (obj1.getClass() != obj2.getClass())
			return false;
		return true;
	}
}
