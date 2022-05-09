package fr.firefoxx.graphicengine.dimention3.light;

import java.util.ArrayList;

import fr.firefoxx.graphicengine.data.MyColor;
import fr.firefoxx.graphicengine.dimention3.math.Vector3;
import fr.firefoxx.graphicengine.dimention3.myobject.MyObject;

public interface Light {
	public MyColor lightValue(Vector3 point, Vector3 normal, ArrayList<MyObject> objs);
}
