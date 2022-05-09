package fr.firefoxx.graphicengine.dimention3.shape2d;

import fr.firefoxx.graphicengine.dimention3.math.*;

public class Disk3 extends Shape2d {

	private final Vector3 midPoint;
	private final Vector3 normal;
	private final double r;
	
	public Disk3(Vector3 centralPoint, Vector3 normalVector, double radius) {
		if (normalVector.isNull()) {
			throw new IllegalArgumentException("Null Vector");
		} else {
			midPoint = centralPoint;
			r = radius;
			normal = normalVector;
		}
	}


	public Vector3 getIntersectionPoint(Ray3 ray) {
		Plane3 plane;
		plane = new Plane3(midPoint, normal);
		
		Vector3 point = plane.getIntersectionPoint(ray);
		if (point == null)
			return null;
		if (Vector3.distance(point, midPoint) > r)
			return null;
		
		return point;
	}


	@Override
	public Vector3 getNormal(Vector3 point) {
		return normal;
	}
}
