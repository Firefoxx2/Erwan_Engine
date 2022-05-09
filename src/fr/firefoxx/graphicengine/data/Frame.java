package fr.firefoxx.graphicengine.data;

import java.lang.reflect.Array;

public class Frame<T> {
	public T[][] frame;
	public final T nullReference;
	
	private final int xPxResolution;
	private final int yPxResolution;


	@SuppressWarnings("unchecked")
	public Frame(int xPxResolution, int yPxResolution, T nullReference, Class<T> cls) {
		this.xPxResolution = xPxResolution;
		this.yPxResolution = yPxResolution;
		this.nullReference = nullReference;
		frame = (T[][]) Array.newInstance(cls, xPxResolution, yPxResolution);
		
		for(int y = 0; y<yPxResolution; y++) {
			for(int x = 0; x<xPxResolution; x++) {
				frame[x][y] = nullReference;
			}
		}
	}

	public T getSafe(int x, int y) {
		if (x < 0)
			return getSafe(0,y);
		if (x >= xPxResolution)
			return getSafe(xPxResolution-1,y);
		if (y < 0)
			return getSafe(x,0);
		if (y >= yPxResolution)
			return getSafe(x,yPxResolution-1);
		return frame[x][y];
	}
	
	public T get(int x, int y) {
		if (x < 0)
			throw new IllegalArgumentException("MINBOUNDX");
		if (x >= xPxResolution)
			throw new IllegalArgumentException("MAXBOUNDX");
		if (y < 0)
			throw new IllegalArgumentException("MINBOUNDY");
		if (y >= yPxResolution)
			throw new IllegalArgumentException("MAXBOUNDY");
		return frame[x][y];
	}


	public void set(int x, int y, T e) {
		if (x < 0)
			throw new IllegalArgumentException("MINBOUNDX");
		if (x >= xPxResolution)
			throw new IllegalArgumentException("MAXBOUNDX");
		if (y < 0)
			throw new IllegalArgumentException("MINBOUNDY");
		if (y >= yPxResolution)
			throw new IllegalArgumentException("MAXBOUNDY");
		this.frame[x][y] = e;
	}
	
	/**
	@Deprecated
	public MyColor[][] renderDerivativeAO(float range, float coef) {
		MyColor[][] finalRender = new MyColor[xPxResolution][yPxResolution];
		for(int y = 0; y<yPxResolution; y++) {
			for(int x = 0; x<xPxResolution; x++) {
				finalRender[x][y] = MyColor.GREY();
			}
		}
		
		double[][] dx = new double[xPxResolution][yPxResolution];
		double[][] dy = new double[xPxResolution][yPxResolution];
		
		for(int y = 1; y<yPxResolution-1; y++) {
			for(int x = 1; x<xPxResolution-1; x++) {
				dx[x][y] = Zbuffer[x+1][y] - Zbuffer[x-1][y];
				dy[x][y] = Zbuffer[x][y+1] - Zbuffer[x][y-1];
			}
		}

		float b = 1.0f;
		double[][] d2 = new double[xPxResolution][yPxResolution];
		for(int y = 1; y<yPxResolution-1; y++) {
			for(int x = 1; x<xPxResolution-1; x++) {
				double d2x = dx[x+1][y] - dx[x-1][y];
				double d2y = dy[x][y+1] - dy[x][y-1];
				d2[x][y] = d2x + d2y;
				if (d2[x][y]<-b) {
					d2[x][y] = 0;
				}
				if (d2[x][y]<0) {
					d2[x][y] *= 2;
				}
				if (d2[x][y]<-1) {
					d2[x][y] = -1;
				}
				if (d2[x][y]>0.25) {
					d2[x][y] = 0.25;
				}
			}
		}

		for(int y = 1; y<yPxResolution; y++) {
			for(int x = 1; x<xPxResolution; x++) {
				finalRender[x][y].setLight(d2[x][y] * coef);
			}
		}
		return finalRender;
	}
	
	@Deprecated
	public MyColor[][] contourRender(float coef) 
		double[][] dx = new double[xPxResolution][yPxResolution];
		double[][] dy = new double[xPxResolution][yPxResolution];
		MyColor[][] finalRender = new MyColor[xPxResolution][yPxResolution];
		for(int y = 0; y<yPxResolution; y++) {
			for(int x = 0; x<xPxResolution; x++) {
				finalRender[x][y] = MyColor.GREY();
			}
		}
		
		for(int y = 1; y<yPxResolution-1; y++) {
			for(int x = 1; x<xPxResolution-1; x++) {
				dx[x][y] = Zbuffer[x+1][y] - Zbuffer[x-1][y];
				dy[x][y] = Zbuffer[x][y+1] - Zbuffer[x][y-1];
			}
		}
		
		double[][] d2x = new double[xPxResolution][yPxResolution];
		double[][] d2y = new double[xPxResolution][yPxResolution];
		double[][] d2 = new double[xPxResolution][yPxResolution];
		for(int y = 1; y<yPxResolution-1; y++) {
			for(int x = 1; x<xPxResolution-1; x++) {
				d2x[x][y] = dx[x+1][y] - dx[x-1][y];
				d2y[x][y] = dy[x][y+1] - dy[x][y-1];
				d2[x][y] = d2x[x][y] + d2y[x][y];
			}
		}
		
		for(int y = 1; y<yPxResolution; y++) {
			for(int x = 1; x<xPxResolution; x++) {
				finalRender[x][y].setLight(d2[x][y] * coef);
			}
		}
		return finalRender;
	}
	**/
}

