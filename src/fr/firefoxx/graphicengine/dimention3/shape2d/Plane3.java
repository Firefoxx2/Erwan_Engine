package fr.firefoxx.graphicengine.dimention3.shape2d;

import fr.firefoxx.graphicengine.dimention3.math.*;

public class Plane3 extends Shape2d {
	private final Vector3 normal;
	private final double off;

	public Plane3(Vector3 vnormal, double offset) {
		if (vnormal.isNull()) {
			throw new IllegalArgumentException("Null Vector");
		} else {
			normal = vnormal;
			off = offset;
		}
	}
	
	public Plane3(Vector3 point, Vector3 vnormal) {
		this(vnormal, -Vector3.dot(vnormal, point));
	}

	public Vector3 getIntersectionPoint(Ray3 ray) {
		if (!this.isCrossed((Line3) ray))
			return null;// les 2 objets ne se touchent pas
		
		double denom, nomin, t, x, y, z;
		Vector3 pointIntersec;
		denom = normal.getX() * ray.getVDirector().getX() 
				+ normal.getY() * ray.getVDirector().getY() 
				+ normal.getZ() * ray.getVDirector().getZ();
		
		if (denom == 0) {
			System.out.println("erreur div0 impossible");
		}
		
		nomin = - (normal.getX() * ray.getOrigin().getX() 
				+ normal.getY() * ray.getOrigin().getY() 
				+ normal.getZ() * ray.getOrigin().getZ()
				+off
		);
		t = nomin/denom;
		
		if (t <= 0)
			return null;//rayon mal orienté
		
		x = ray.getOrigin().getX() + t * ray.getVDirector().getX();
		y = ray.getOrigin().getY() + t * ray.getVDirector().getY();
		z = ray.getOrigin().getZ() + t * ray.getVDirector().getZ();
		pointIntersec = new Vector3(x,y,z);
		
		return pointIntersec;
	}
	
	private boolean isCrossed(Line3 line) {
		return line.getVDirector().dot(normal) != 0;
	}
	
	@Override
	public String toString() {
		return "Plane3 [normal=" + normal + ", off=" + off + "]";
	}

	@Override
	public Vector3 getNormal(Vector3 point) {
		return normal;
	}

}
