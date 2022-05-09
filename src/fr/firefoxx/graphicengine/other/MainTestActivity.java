package fr.firefoxx.graphicengine.other;

import fr.firefoxx.graphicengine.data.MyColor;

public class MainTestActivity {

	public static void main(String[] args) {
		MyColor M1 = new MyColor(1.0F, 0.0F, 0.0F, 0.5F);
		MyColor M2 = new MyColor(0.0F, 1.0F, 1.0F, 0.5F);
		M1.superImpose(M2);
	}

}
