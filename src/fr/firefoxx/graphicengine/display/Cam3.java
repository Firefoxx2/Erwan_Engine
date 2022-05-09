package fr.firefoxx.graphicengine.display;

import fr.firefoxx.graphicengine.dimention3.math.Vector3;
import fr.firefoxx.graphicengine.dimention3.shape2d.Plane3;
import fr.firefoxx.math.jama.Matrix;
import fr.firefoxx.graphicengine.dimention3.math.Ray3;

public class Cam3 {
	private final int xPxResolution;
	private final int yPxResolution;
	private final double FOV;
	
	private Vector3 origin;
	private Vector3 middleOfScreen; //point au milieu de l'écran

	private Matrix transferMatrixToScreen;
	private Matrix TransferMatrixToWorld;
	
	private Plane3 screenPlane;
	
	private Frame<Ray3> RaySet;
	private final Frame<Vector3> screenPoints;
	
	
	public Cam3(int xPxResolution, int yPxResolution, double FOV) {
		super();
		this.xPxResolution = xPxResolution;
		this.yPxResolution = yPxResolution;
		this.FOV = FOV;
		
		screenPoints = new Frame<Vector3>(xPxResolution,yPxResolution, Vector3.infinity, Vector3.class);
		RaySet = new Frame<Ray3>(xPxResolution, yPxResolution, new Ray3(Vector3.infinity, Vector3.infinity), Ray3.class);
		
		
		double xret = ((double) xPxResolution -1) /2;
		double yret = ((double) yPxResolution -1) /2;
		
		for(int x = 0; x<xPxResolution; x++) {
			for(int y = 0; y<yPxResolution; y++) {
				double xval = (x - xret)/xPxResolution;
				double yval = (y - yret)/xPxResolution;
				
				screenPoints.set(x, y, new Vector3(xval, yval, 0));
			}
		}
	}
	
	public void setCamPosition(Vector3 origin, Vector3 direction) {
		if (direction.getX() == 0 && direction.getZ() == 0) {
			throw new IllegalArgumentException("forbidden direction");
		} else {
			this.origin = origin;
			Vector3 zv = direction.Normalize(-1);
			Vector3 xv = new Vector3(direction.getZ(),0,-direction.getX()).Normalize(-1);
			Vector3 yv = Vector3.vectorial(xv, zv).negative();
			
			Matrix tempTransferMatrix = new Matrix(3,3);
			for (int i = 0; i<3; i++) {
				tempTransferMatrix.set(i, 0, xv.getColCoefs()[i][0]);
				tempTransferMatrix.set(i, 1, yv.getColCoefs()[i][0]);
				tempTransferMatrix.set(i, 2, zv.getColCoefs()[i][0]);
			}

			Vector3 baseOffset = direction.Normalize(FOV);
			this.middleOfScreen = origin.add(baseOffset);
			screenPlane = new Plane3(this.middleOfScreen, direction);
			
			TransferMatrixToWorld = new Matrix(3,3);
			for (int i = 0; i<3; i++) {
				TransferMatrixToWorld.set(i, 0, xv.getColCoefs()[i][0]/xPxResolution);
				TransferMatrixToWorld.set(i, 1, yv.getColCoefs()[i][0]/xPxResolution);
				TransferMatrixToWorld.set(i, 2, zv.getColCoefs()[i][0]/xPxResolution);
			}
			transferMatrixToScreen = TransferMatrixToWorld.inverse();
			
			for(int x = 0; x<xPxResolution; x++) {
				for(int y = 0; y<yPxResolution; y++) {
					double[][] ScreenPixelPosition = screenPoints.get(x,y).getColCoefs();
					Matrix matrixVectorOfPoint = new Matrix(ScreenPixelPosition);
					Matrix RayVectorMatrix = tempTransferMatrix.times(matrixVectorOfPoint);
					double[] RayVectorCoef = RayVectorMatrix.getColumnPackedCopy();
					Vector3 RayVector = new Vector3(RayVectorCoef).add(baseOffset);
					
					RaySet.set(x, y, new Ray3(RayVector, this.origin));
				}
			}
		}
	}
	
	public double[] throwOnScreen(Vector3 point) {
		Ray3 r = new Ray3(new Vector3(origin,point), origin);
		Vector3 p = screenPlane.getIntersectionPoint(r);
		if (p == null)
			return null;
		
		double[][] VectorPointFromOrigin = p.substract(this.middleOfScreen).getColCoefs();
		
		Matrix matrixVectorOfPoint = new Matrix(VectorPointFromOrigin);
		Matrix PosCoef = transferMatrixToScreen.times(matrixVectorOfPoint);

		double dx = PosCoef.get(0, 0)+xPxResolution/2;
		double dy = PosCoef.get(1, 0)+yPxResolution/2;
		if (dx >= 0 && dx < xPxResolution && dy >= 0 && dy < yPxResolution) {
			return new double[] {dx,dy};
		} else {
			return null;//hors de l'écran
		}
	}
	
	public Vector3 getOrigin() {
		return origin;
	}

	public Ray3 getRay(int x, int y) {
		return RaySet.get(x, y);
	}
	
	@Override
	public String toString() {
		StringBuffer bf1 = new StringBuffer();
		StringBuffer bf2 = new StringBuffer();

		for(int y = 0; y<yPxResolution; y++) {
			bf1.append("\n");
			for(int x = 0; x<xPxResolution; x++) {
				bf1.append("  [" + screenPoints.get(x, y).getX() + "|" + screenPoints.get(x, y).getY() + "|" + screenPoints.get(x, y).getY() + "]");
			}
		}
		for(int y = 0; y<yPxResolution; y++) {
			bf2.append("\n");
			for(int x = 0; x<xPxResolution; x++) {
				bf2.append("  [" + RaySet.get(x, y).getVDirector().getX() + "|" + RaySet.get(x, y).getVDirector().getY() + "|" + RaySet.get(x, y).getVDirector().getZ() + "]");
			}
		}
		return "Cam3 [screenPoints=" + bf1.toString() + "\n, RaySet=" + bf2.toString() + "]";
	}
}
