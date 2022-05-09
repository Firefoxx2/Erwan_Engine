package fr.firefoxx.graphicengine.dimention3.light;

import java.util.ArrayList;

import fr.firefoxx.graphicengine.dimention3.math.Vector3;
import fr.firefoxx.graphicengine.data.MyColor;
import fr.firefoxx.graphicengine.dimention3.math.Ray3;
import fr.firefoxx.graphicengine.dimention3.myobject.MyObject;

public class DirectiveLight implements Light {
	private Vector3 lumDirInv;
	private MyColor lum;
	
	public DirectiveLight(Vector3 lumDir, MyColor lum) {
		if (lumDir.isNull()) {
			throw new IllegalArgumentException("Vecteur directeur null");
		} else {
			this.lumDirInv = lumDir.negative();
			this.lum = lum;
		}
	}
	
	@Override
	public MyColor lightValue(Vector3 point, Vector3 normal, ArrayList<MyObject> objs) {
		Ray3 r = new Ray3(lumDirInv, point);
		for (MyObject obj: objs) {
			Vector3 p = obj.getIntersectionPoint(r);
			if (p != null) {
				if (Vector3.distance(point, p) > 0.001f)
					return MyColor.BLACK();
			}
		}
		float dot = (float) Vector3.dot(lumDirInv.Normalize(), normal.Normalize());
		
		return lum;//.scal(dot>0 ? dot:0);
	}
}
