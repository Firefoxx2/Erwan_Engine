package fr.firefoxx.graphicengine.dimention3.myobject;

import fr.firefoxx.graphicengine.data.MyColor;
import fr.firefoxx.graphicengine.dimention3.math.*;
import fr.firefoxx.graphicengine.dimention3.shape2d.Shape2d;
import fr.firefoxx.graphicengine.rendering.*;

public class Simple2d<T extends Shape2d> implements MyObject {

	private final T shape;
	private final MyColor c;

	public Simple2d(T shape, MyColor c) {
		this.shape = shape;
		this.c = c;
	}

	public Vector3 getIntersectionPoint(Ray3 ray) {
		return shape.getIntersectionPoint(ray);
	}

	public MyColor getPointColor(Vector3 p) {
		return this.c;
	}

	@Override
	public Vector3 getNormal(Vector3 point) {
		return shape.getNormal(point);
	}
	
	@Override
	public String toString() {
		return "Simple2d [shape=" + shape + ", c=" + c + "]";
	}
}
