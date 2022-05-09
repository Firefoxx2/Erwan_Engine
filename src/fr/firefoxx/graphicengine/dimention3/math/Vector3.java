package fr.firefoxx.graphicengine.dimention3.math;

import java.util.Arrays;

public class Vector3 {
	public static final Vector3 infinity = new Vector3(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE);

	private final double coord[];
	
	public double getX() {return coord[0];}
	public double getY() {return coord[1];}
	public double getZ() {return coord[2];}
	
	public Vector3(double xCoef, double yCoef, double zCoef) {this(new double[]{xCoef, yCoef, zCoef});}
	public Vector3(Vector3 p1, Vector3 p2) {this(new double[]{p2.getX() - p1.getX(), p2.getY() - p1.getY(), p2.getZ() - p1.getZ()});}
	public Vector3(double[] coefs) {
		super();
		coord = coefs;
	}
	
	public double[][] getColCoefs() {
		return new double[][] {{getX()}, {getY()}, {getZ()}};
	}
	public double[][] getRawCoefs() {
		return new double[][] {{getX(), getY(), getZ()}};
	}

	public Vector3 mutliply(double c) {
		return new Vector3(new double[]{getX() * c, getY() * c, getZ() * c});
	}

	public Vector3 negative() {return Vector3.minus(this);}
	public static Vector3 minus(Vector3 vector) {
		return new Vector3(new double[]{-vector.getX(),-vector.getY(),-vector.getZ()});
	}

	public Vector3 add(Vector3 vector) {return add(this, vector);}
	public Vector3 substract(Vector3 vector) {return add(this, Vector3.minus(vector));}
	public static Vector3 add(Vector3 u, Vector3 v) {
		double x = u.getX() + v.getX();
		double y = u.getY() + v.getY();
		double z = u.getZ() + v.getZ();
		return new Vector3(new double[]{x,y,z});
	}

	public double dot(Vector3 vector) {return dot(this, vector);}
	public static double dot(Vector3 u, Vector3 v) {
		return u.getX() * v.getX() + u.getY() * v.getY() + u.getZ() * v.getZ();
	}
	
	public final double angle(Vector3 u, Vector3 v){
		double vDot = this.dot(v) / ( u.size()*v.size() );
		if( vDot < -1.0) vDot = -1.0;
		if( vDot >  1.0) vDot =  1.0;
		return((double) (Math.acos( vDot )));
	}

	public double size() {
		return Math.sqrt(Math.pow(getX(),2) + Math.pow(getY(),2) + Math.pow(getZ(),2));
	}

	public double distance(Vector3 v) {return distance(this,v);}
	public static double distance(Vector3 v1, Vector3 v2) {
		return Math.sqrt(Math.pow(v2.getX() - v1.getX(),2) + Math.pow(v2.getY() - v1.getY(),2) + Math.pow(v2.getZ() - v1.getZ(),2));
	}

	public Vector3 vectorial(Vector3 vector) {return vectorial(this, vector);}
	public static Vector3 vectorial(Vector3 u, Vector3 v) {
		double x = u.getY() * v.getZ() - u.getZ() * v.getY();
		double y = u.getZ() * v.getX() - u.getX() * v.getZ();
		double z = u.getX() * v.getY() - u.getY() * v.getX();
		
		return new Vector3(new double[]{x,y,z});
	}

	public Vector3 Normalize() {return Normalize(1.0f);}
	public Vector3 Normalize(double d) {
		double div = d/this.size();
		double x = getX() * div;
		double y = getY() * div;
		double z = getZ() * div;
		
		return new Vector3(new double[]{x,y,z});
	}
	
	public boolean isNull() {
		return (getX()==0 && getY()==0 && getZ()==0);
	}
	
	public static Vector3 newRandomUnitVector() {
		double phi = Math.random() * 3.142;
		double theta = Math.random() * 2 * 3.142;
		
		double x = Math.sin(theta) * Math.cos(phi);
		double y = Math.sin(theta) * Math.sin(phi);
		double z = Math.cos(theta);
		
		return new Vector3(new double[]{x,y,z});		
	}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " [x=" + getX() + ", y=" + getY() + ", z=" + getZ() + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(coord);
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vector3 other = (Vector3) obj;
		if (!Arrays.equals(coord, other.coord))
			return false;
		return true;
	}
}
