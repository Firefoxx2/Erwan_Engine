package fr.firefoxx.graphicengine.dimention3.light;

import java.util.ArrayList;

import fr.firefoxx.graphicengine.dimention3.math.Vector3;
import fr.firefoxx.graphicengine.dimention3.myobject.MyObject;
import fr.firefoxx.graphicengine.data.MyColor;
import fr.firefoxx.graphicengine.dimention3.math.Ray3;

public class LightSource implements Light {
	private Vector3 origin;
	private double distmax;
	private MyColor lum;
	
	public LightSource(Vector3 origin, double distmax, MyColor lum) {
		this.origin = origin;
		this.distmax = distmax;
		this.lum = lum;
	}
	
	@Override
	public MyColor lightValue(Vector3 point, Vector3 normal, ArrayList<MyObject> objs) {
		Vector3 v = new Vector3(origin, point);
		double d = v.size();
		float dist = (float) d;	//TODO Réduire le traitement
		
		if (dist < distmax) {
			Ray3 r = new Ray3(v, origin);
			for (MyObject obj: objs) {
				Vector3 p = obj.getIntersectionPoint(r);
				if (p != null) {
					if (Vector3.distance(origin, p) < dist - 0.001f)
						return MyColor.BLACK();
				}
				
			}
			
			Vector3 vu = new Vector3(point, origin);
			
			double dot = Vector3.dot(normal.Normalize(), vu.Normalize());
			double diff = (distmax - dist)/distmax;
			
			return  lum.scal((float) (
					diff// * (dot>0 ? dot:0)
					));
		}
		return MyColor.BLACK();
	}
}
