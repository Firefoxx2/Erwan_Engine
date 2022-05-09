package fr.firefoxx.graphicengine.dimention3.myobject;

import fr.firefoxx.graphicengine.dimention3.math.Vector3;
import fr.firefoxx.graphicengine.data.MyColor;
import fr.firefoxx.graphicengine.dimention3.math.Ray3;

public interface MyObject {
	public Vector3 getIntersectionPoint(Ray3 r);
	public MyColor getPointColor(Vector3 point);
	public Vector3 getNormal(Vector3 point);
}
