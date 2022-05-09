package fr.firefoxx.graphicengine.dimention3.shape2d;

import fr.firefoxx.graphicengine.dimention3.math.*;

public abstract class Shape2d {
	public abstract Vector3 getIntersectionPoint(Ray3 ray);
	public abstract Vector3 getNormal(Vector3 point); 
}
