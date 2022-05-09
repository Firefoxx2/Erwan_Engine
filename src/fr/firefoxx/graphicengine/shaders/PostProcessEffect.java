package fr.firefoxx.graphicengine.shaders;

import fr.firefoxx.graphicengine.data.MyColor;

public abstract class PostProcessEffect implements ProcessEffect {
	public abstract MyColor apply(int x, int y);
}
