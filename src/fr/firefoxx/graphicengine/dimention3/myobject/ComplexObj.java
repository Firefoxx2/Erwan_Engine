package fr.firefoxx.graphicengine.dimention3.myobject;

import java.util.ArrayList;

import fr.firefoxx.graphicengine.dimention3.math.Vector3;
import fr.firefoxx.graphicengine.data.MyColor;
import fr.firefoxx.graphicengine.dimention3.math.Ray3;
import fr.firefoxx.graphicengine.dimention3.shape2d.Losange3;
import fr.firefoxx.graphicengine.dimention3.shape2d.Shape2d;

@Deprecated
public class ComplexObj implements MyObject {
	private final ArrayList<Shape2d> objList;
	private final MyColor c;
	
	public ComplexObj(ArrayList<Shape2d> objList, MyColor c) {
		this.objList = objList;
		this.c = c;
	}

	private ArrayList<Vector3> getIntersectionPoints(Ray3 ray) {
		ArrayList<Vector3> plistret = new ArrayList<Vector3>();
		for (Shape2d obj : objList) {
			Vector3 p = obj.getIntersectionPoint(ray);
			if (p != null)
				plistret.add(p);
		}
		return plistret;
	}

	@Override
	public Vector3 getIntersectionPoint(Ray3 ray) {
		ArrayList<Vector3> plist = getIntersectionPoints(ray);
		double dist = Double.MAX_VALUE;
		Vector3 ret = null;
		
		for (Vector3 p:plist) {
			double temp = Vector3.distance(p, ray.getOrigin());
			if (dist>temp) {
				dist = temp;
				ret = p;
			}
		}
		return ret;
	}

	@Override
	public MyColor getPointColor(Vector3 p) {
		return this.c;
	}
	
	public static ComplexObj Parallelepipede(Vector3 p1, Vector3 vx, Vector3 vy, Vector3 vz, MyColor c) {
		ArrayList<Shape2d> faces = new ArrayList<Shape2d>();
		
		faces.add(new Losange3(vx, vy, p1));
		faces.add(new Losange3(vy, vz, p1));
		faces.add(new Losange3(vz, vx, p1));
		
		faces.add(new Losange3(vx, vy, p1.add(vz)));
		faces.add(new Losange3(vy, vz, p1.add(vx)));
		faces.add(new Losange3(vz, vx, p1.add(vy)));
		ComplexObj ret = new ComplexObj(faces, c);
		
		return ret;
	}

	@Override
	public Vector3 getNormal(Vector3 point) {
		return Vector3.infinity;
	}
}
