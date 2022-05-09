package fr.firefoxx.graphicengine.dimention3.myobject;

import java.util.ArrayList;

import fr.firefoxx.graphicengine.data.MyColor;
import fr.firefoxx.graphicengine.dimention3.math.Line3;
import fr.firefoxx.graphicengine.dimention3.math.Ray3;
import fr.firefoxx.graphicengine.dimention3.math.Vector3;

public class Sphere implements MyObject {
	private final Vector3 centerPoint;
	private final double r;
	private final MyColor c;

	public Sphere(Vector3 centerPoint, double r, MyColor c) {
		this.centerPoint = centerPoint;
		this.r = r;
		this.c = c;
	}

	private ArrayList<Vector3> getIntersectionPoints(Line3 l) {
		ArrayList<Vector3> ret = new ArrayList<Vector3>();
		
		double vx = l.getVDirector().getX();
		double vy = l.getVDirector().getY();
		double vz = l.getVDirector().getZ();
		
		double px = l.getOrigin().getX();
		double py = l.getOrigin().getY();
		double pz = l.getOrigin().getZ();

		double cx = centerPoint.getX();
		double cy = centerPoint.getY();
		double cz = centerPoint.getZ();

		double coefa = Math.pow(vx, 2) + Math.pow(vy, 2) + Math.pow(vz, 2);
		double coefb = 2 * ((px - cx) * vx + (py - cy) * vy + (pz - cz) * vz);
		double coefc = Math.pow(px - cx, 2) + Math.pow(py - cy, 2) + Math.pow(pz - cz, 2) - r*r;
		
		double delta = Math.pow(coefb, 2) - 4 * coefa * coefc;

		//System.out.println("a: " + coefa + ", b: " + coefb + ", c: " + coefc + ", delta: " + delta);
		
		if (delta == 0) {
			double t;
			t = -coefb / (2 * coefa);
			double x = vx * t + px;
			double y = vy * t + py;
			double z = vz * t + pz;
			Vector3 p = new Vector3(x,y,z);
			ret.add(p);
		} else if (delta > 0){
			double t1 = (-coefb - Math.sqrt(delta)) / (2 * coefa);
			double t2 = (-coefb + Math.sqrt(delta)) / (2 * coefa);
			
			double x1 = vx * t1 + px;
			double y1 = vy * t1 + py;
			double z1 = vz * t1 + pz;
			
			Vector3 p1 = new Vector3(x1,y1,z1);
			ret.add(p1);

			double x2 = vx * t2 + px;
			double y2 = vy * t2 + py;
			double z2 = vz * t2 + pz;
			Vector3 p2 = new Vector3(x2,y2,z2);
			ret.add(p2);
		}
		
		return ret;
	}

	@Override
	public Vector3 getIntersectionPoint(Ray3 ray) {
		ArrayList<Vector3> plist = getIntersectionPoints((Line3) ray);
		double dist = -1;
		Vector3 ret = null;
		for (Vector3 p:plist) {
			double temp = Vector3.distance(p, ray.getOrigin());
			if (ray.isOnDirection(p)) {
				if (dist == -1 || dist>temp) {
					dist = temp;
					ret = p;
				}
			}
		}
		if (ret == null)
			return null;
		return ret;
	}
	
	@Override
	public MyColor getPointColor(Vector3 p) {
		return this.c;
	}

	@Override
	public Vector3 getNormal(Vector3 point) {
		return new Vector3(centerPoint, point);
	}

}
