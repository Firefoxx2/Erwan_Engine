package fr.firefoxx.graphicengine.dimention3.math;

public class Ray3 extends Line3 {

	public Ray3(Vector3 vdirecteur, Vector3 origin) {
		super(vdirecteur, origin);
	}

	public boolean isOnDirection(Vector3 p) {//ne vérifie pas que le point est sur la ligne, seulement la direction
		double a = this.getVDirector().getX();
		double b = this.getVDirector().getY();
		double c = this.getVDirector().getZ();

		double k;
		if (a != 0) {
			k = (p.getX() - this.getOrigin().getX()) / a;
		} else if (b != 0) {
			k = (p.getY() - this.getOrigin().getY()) / b;
		} else {
			k = (p.getZ() - this.getOrigin().getZ()) / c;
		}
		return (k>0.00001);//
	}
}
