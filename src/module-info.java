module moduleinfo {
	requires transitive javafx.graphics;
	requires javafx.base;
	requires javafx.controls;
	requires javafx.fxml;
	requires java.desktop;
	requires javafx.swing;
	requires jama;
	
	//opens fr.utbm.ap4b.controller to javafx.fxml;
	exports fr.firefoxx.graphicengine.other to javafx.graphics;
	exports fr.firefoxx.graphicengine.rendering to fr.firefoxx.graphicengine;
	exports fr.firefoxx.graphicengine.dimention3.myobject to fr.firefoxx.graphicengine;
	exports fr.firefoxx.graphicengine.dimention3.light to fr.firefoxx.graphicengine;
	exports fr.firefoxx.graphicengine.dimention3.math to fr.firefoxx.graphicengine;
}