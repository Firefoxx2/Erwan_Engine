package fr.firefoxx.graphicengine.dimention3.shape2d;

import Jama.Matrix;
import fr.firefoxx.graphicengine.dimention3.math.*;

public class Triangle3 extends Shape2d {

	private final Vector3 p1;

	private final Vector3 u;
	private final Vector3 v;
	private final Vector3 w;
	private final Matrix M;
	private final Matrix mInv;
	
	public Triangle3(Vector3 vector1, Vector3 vector2, Vector3 point) {
		u = vector1;
		v = vector2;
		w = Vector3.vectorial(u, v);
		p1 = point;

		M = new Matrix(3,3);
		for (int i = 0; i<3; i++) {
			M.set(i, 0, u.getColCoefs()[i][0]);
			M.set(i, 1, v.getColCoefs()[i][0]);
			M.set(i, 2, w.getColCoefs()[i][0]);
		} 
		mInv = M.inverse();
	}
	
	public Triangle3(Vector3 p1, Vector3 p2, Vector3 p3, int b) {
		u = new Vector3(p1, p2);
		v = new Vector3(p1, p3);
		w = Vector3.vectorial(u, v);
		this.p1 = p1;

		M = new Matrix(3,3);
		for (int i = 0; i<3; i++) {
			M.set(i, 0, u.getColCoefs()[i][0]);
			M.set(i, 1, v.getColCoefs()[i][0]);
			M.set(i, 2, w.getColCoefs()[i][0]);
		} 
		mInv = M.inverse();
	}

	private Matrix getCoordInLocalBase(Vector3 pointInter) {
		Matrix PosCoef;//coefficient de position dans le triangle
		
		double[][] VectorPointFromOrigin = pointInter.substract(p1).getColCoefs();
		Matrix matrixVectorOfPoint = new Matrix(VectorPointFromOrigin);
		PosCoef = mInv.times(matrixVectorOfPoint);
		
		return PosCoef;
	}
	
	protected boolean isVectorOnSurface(Matrix PosCoef) {
		//tout coef positif ou nul
		if (PosCoef.get(0, 0) < 0)
			return false;
		
		if (PosCoef.get(1, 0) < 0)
			return false;
		
		//somme coef <1 (triangle)
		if (PosCoef.norm1() > 1)
			return false;
		
		return true;
	}

	public Vector3 getIntersectionPoint(Ray3 ray) {
		Matrix PosCoef;//coefficient de position dans le triangle
		Plane3 plane;
		Vector3 pointInter;

		plane = new Plane3(p1, w);
		pointInter = plane.getIntersectionPoint(ray);
		if (pointInter == null)
			return null;
		
		PosCoef = this.getCoordInLocalBase(pointInter);
		if (!isVectorOnSurface(PosCoef))
			return null;
		
		return pointInter;
	}
	
	@Override
	public String toString() {
		return "Triangle3 [p1=" + p1 + ", M=" + M + "]";
	}

	@Override
	public Vector3 getNormal(Vector3 point) {
		return w;
	}
}
