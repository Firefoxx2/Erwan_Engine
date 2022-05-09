package fr.firefoxx.graphicengine.dimention3.math;

public class Line3 {
	private final Vector3 vdir;
	private final Vector3 o;
	
	public Line3(Vector3 vdirecteur, Vector3 origin) {
		if (vdirecteur.isNull()) {
			throw new IllegalArgumentException("Null Vector");
		} else {
			vdir = vdirecteur;
			o = origin;
		}
	}

	public Vector3 getVDirector() {
		return vdir;
	}
	
	public Vector3 getOrigin() {
		return o;
	}

	@Override
	public String toString() {
		return "Line3 [vdir=" + vdir + ", o=" + o + "]";
	}
}
