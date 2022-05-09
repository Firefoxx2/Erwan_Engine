package fr.firefoxx.graphicengine.rendering.shaders;

import fr.firefoxx.graphicengine.data.MyColor;
import fr.firefoxx.graphicengine.rendering.Renderer;

public class SSAOv2 extends PostProcessEffect {
	private int range;
	private Renderer rd;
	private double[][] distmap;

	public SSAOv2(int range, Renderer rd) {
		super();
		this.rd = rd;
		this.range = range;
		
		distmap = new double[range][range];
		for (int x = 0; x<range; x++) {
			for (int y = 0; y<range; y++) {
				distmap[x][y] = range - Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
			}
		}
		
	}
	
	@Override
	public MyColor apply(int x, int y) {
		double dist = rd.Zbuffer.get(x, y);
		double moy = 0;
		double div = 0;
		
		int distm = (int) (range - dist);
		if (distm>0) {
			for(int ys = -distm; ys<=distm; ys++) {
				for(int xs = -distm; xs<=distm; xs++) {
					double coefmap = (distmap[Math.abs(xs)][Math.abs(ys)] - (range - distm))/distm;
					if (coefmap>0) {
						double val = dist - rd.Zbuffer.get(x+xs, y+ys);//distance positive
						double b = 1.0f;
						if (val<b && val>-b) {
							moy += coefmap * (val);
							div += coefmap;
						}
					} else {
						//not in range

					}
				}

			}
		} else {
			//too far
		}
		
		double rd;
		if (div!=0) {
			rd = moy/div;
		} else {
			rd = 0;
		}
		if (rd != 0)
			System.out.println(rd);
		
		return MyColor.GREY((float) ((rd+1)/2));
	}
}
