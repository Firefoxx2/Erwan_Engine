package fr.firefoxx.graphicengine.dimention3.shape2d;

import Jama.Matrix;
import fr.firefoxx.graphicengine.dimention3.math.Vector3;

public class Losange3 extends Triangle3 {

	public Losange3(Vector3 vector1, Vector3 vector2, Vector3 point) {
		super(vector1, vector2, point);
	}
	
	public Losange3(Vector3 vector1, Vector3 vector2, Vector3 point, int t) {
		super(vector1, vector2, point, t);
	}

	@Override
	protected boolean isVectorOnSurface(Matrix PosCoef) {
		boolean test = true;
		for (int i = 0; i<3; i++) {
			if (PosCoef.get(i, 0) < -0.001) {//tout coef positif ou nul
				test = false;
				break;
			}
			if (PosCoef.get(i, 0) > 1) {//tout coef inferieur à 1
				test = false;
				break;
			}
		}
		
		//rendu
		return test;
	}

}
