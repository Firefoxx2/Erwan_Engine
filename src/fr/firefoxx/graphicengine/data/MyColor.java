package fr.firefoxx.graphicengine.data;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.scene.paint.Color;

public class MyColor implements Cloneable {
	private final float[] rgb;
	private final float opacity;
	
	public static MyColor getRandomColor() {
		return new MyColor((float) Math.random(),(float) Math.random(),(float) Math.random(),(float) Math.random());
	}
	
	public MyColor setOpacity(float opacity) {
		return new MyColor(this.getRed(), this.getGreen(), this.getBlue(), opacity);
	}
	
	public static MyColor multiply(MyColor color1, MyColor color2) {
		return new MyColor(
				color1.getRed() * color1.getOpacity() * color2.getRed() * color2.getOpacity(),
				color1.getGreen() * color1.getOpacity() * color2.getGreen() * color2.getOpacity(),
				color1.getBlue() * color1.getOpacity() * color2.getBlue() * color2.getOpacity(),
				color1.getOpacity() * color2.getOpacity());
		}
	
	public MyColor multiply(MyColor color) {
		return multiply(this, color);
	}
	
	public static MyColor inverse(MyColor color) {
		return new MyColor(1-color.getRed(), 1-color.getGreen(), 1-color.getBlue());
	}

	public MyColor inverse() {
		return inverse(this);
	}

	public MyColor divide(MyColor color) {
		return multiply(this, color.inverse());
	}

	public static MyColor mean(MyColor color1, MyColor color2) {
		return new MyColor((color1.getRed() + color2.getRed())/2, 
				(color1.getGreen() + color2.getGreen())/2, 
				(color1.getBlue() + color2.getBlue())/2);
	}
	
	public static MyColor mean(ArrayList<MyColor> colors) {
		float red = 0, green = 0, blue = 0, opacity = 0;
		for (MyColor c:colors) {
			red += c.getRed();
			green += c.getGreen();
			blue += c.getBlue();
			opacity += c.getOpacity();
		}
		int nb = colors.size();
		return new MyColor(
				red / nb,
				green / nb,
				blue / nb,
				opacity / nb
				);
	}
	
	public static MyColor scal(MyColor color, float scal) {
		return new MyColor(
				color.getRed() * scal,
				color.getGreen() * scal,
				color.getBlue() * scal,
				color.getOpacity() * scal
				);
	}
	
	public MyColor scal(float scal) {
		return new MyColor(
				this.getRed() * scal,
				this.getGreen() * scal,
				this.getBlue() * scal,
				this.getOpacity() * scal
				);
	}
	
	public MyColor mean(MyColor color) {
		return mean(this, color);
	}
	
	public static MyColor meanWeight(MyColor color1, MyColor color2) {
		float sum = color1.getOpacity() + color2.getOpacity();
		return new MyColor((color1.getRed() * color1.getOpacity() + color2.getRed() * color2.getOpacity()) / sum, 
				(color1.getGreen() * color1.getOpacity() + color2.getGreen() * color2.getOpacity()) / sum,
				(color1.getBlue() * color1.getOpacity() + color2.getBlue() * color2.getOpacity()) / sum
				);
	}
	
	public static MyColor superImpose(MyColor color1, MyColor color2) {
		float coefbase = (1 - color2.opacity) * color1.getOpacity();
		float coefsuper = color2.opacity;
		float sum = coefsuper + coefbase;
		float suminv = 1/sum;
		float red = (color1.getRed() * coefbase + color2.getRed() * coefsuper) * suminv;
		float green = (color1.getGreen() * coefbase + color2.getGreen() * coefsuper) * suminv;
		float blue = (color1.getBlue() * coefbase + color2.getBlue() * coefsuper) * suminv;
		
		return new MyColor(red, green, blue, sum);
	}
	
	public MyColor superImpose(MyColor color) {
		return superImpose(this, color);
	}
	
	public MyColor CastDark(MyColor light, float coef) {//1 -> Bright; 0-> Color
		float min = 1 - coef;
		float diffRed = this.getRed() * (light.getRed() * coef + min);
		float diffBreen = this.getGreen() * (light.getGreen() * coef + min);
		float diffBlue = this.getBlue() * (light.getBlue() * coef + min);

		return new MyColor(diffRed, diffBreen, diffBlue, this.getOpacity());
	}
	
	public MyColor CastWhite(MyColor light, float coef) {//1 -> Bright; 0-> Color
		float diffRed = this.getRed() + (1- this.getRed()) * light.getRed() * coef;
		float diffBreen = this.getGreen() + (1- this.getGreen()) * light.getGreen() * coef;
		float diffBlue = this.getBlue() + (1- this.getBlue()) * light.getBlue() * coef;

		return new MyColor(diffRed, diffBreen, diffBlue, this.getOpacity());
	}
	
	public MyColor lightUp(MyColor light) {
		return new MyColor(
				this.getRed() * (1- light.getRed()*light.getOpacity()),
				this.getGreen() * (1- light.getGreen()*light.getOpacity()),
				this.getBlue() * (1- light.getBlue()*light.getOpacity()),
				this.getOpacity()
				);
	}
	public MyColor lightDown(MyColor dark) {
		return new MyColor(
				this.getRed() * (dark.getRed()*dark.getOpacity()),
				this.getGreen() * (dark.getGreen()*dark.getOpacity()),
				this.getBlue() * (dark.getBlue()*dark.getOpacity()),
				this.getOpacity()
				);
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
	public MyColor(float[] rgb, float opacity) {
		if (rgb[0]>1.0f || rgb[0]<0.0f && rgb[1]>1.0f || rgb[1]<0.0f && rgb[2]>1.0f || rgb[2]<0.0f || opacity<=0.0f && opacity>1.0f)
			throw new IllegalArgumentException("forbidden color : " + rgb[0] + ":" + rgb[1] + ":" + rgb[2] + ":"+opacity);
		this.rgb = rgb;
		this.opacity = opacity;
	}
	
	public MyColor(float r, float g, float b, float opacity) {
		this(new float[] {r,g,b}, opacity);
	}

	public MyColor(float r, float g, float b) {
		this(new float[] {r,g,b}, 1.0f);
	}

	public static final MyColor RED() {return new MyColor(1.0F,0,0);}
	public static final MyColor GREEN() {return new MyColor(0,1.0F,0);}
	public static final MyColor BLUE() {return new MyColor(0,0,1.0F);}
	public static final MyColor WHITE() {return new MyColor(1.0F,1.0F,1.0F);}
	public static final MyColor BLACK() {return new MyColor(0.0F,0.0F,0.0F);}
	public static final MyColor YELLOW() {return new MyColor(1.0F,1.0F,0);}
	public static final MyColor CYAN() {return new MyColor(0,1.0F,1.0F);}
	public static final MyColor MAGENTA() {return new MyColor(1.0F,0,1.0F);}
	@Deprecated public static final MyColor GREY(int g) {return new MyColor(g,g,g);}
	public static final MyColor GREY(float g) {return new MyColor(g,g,g);}
	public static final MyColor GREY(float g, float coef) {return new MyColor(g,g,g,coef);}
	public static final MyColor GREY() {return GREY(0.5F);}
	public static final MyColor NULL() {return BLACK();}
	
	@Deprecated
	public MyColor(int[] rgb, float opacity) {
		this(new float[] {(float) rgb[0]/255, (float) rgb[1]/255,(float) rgb[2]/255}, opacity);
	}
	
	@Deprecated
	public MyColor(int red, int green, int blue, float opacity) {
		this(new int[] {red, green, blue}, opacity);
	}
	
	@Deprecated
	public MyColor(int red, int green, int blue) {
		this(new int[] {red, green, blue}, 1.0f);
	}

	public float getRed() {return rgb[0];}
	public float getGreen() {return rgb[1];}
	public float getBlue() {return rgb[2];}
	@Deprecated public int getRed256() {return (int) (rgb[0] * 255);}
	@Deprecated public int getGreen256() {return (int) (rgb[1] * 255);}
	@Deprecated public int getBlue256() {return (int) (rgb[2] * 255);}
	@Deprecated public void setRed(int red) {rgb[0] = red;}
	@Deprecated public void setGreen(int green) {rgb[1] = green;}
	@Deprecated public void setBlue(int blue) {rgb[2] = blue;}
	public float getOpacity() {return opacity;}
	
	@Deprecated
	public void darken(double lightValue) {
		if (lightValue >= 0 && lightValue < 1) {
			double invCoef = lightValue;
			setRed((int) (invCoef * getRed256()));
			setGreen((int) (invCoef * getGreen256()));
			setBlue((int) (invCoef * getBlue256()));
		} else if (lightValue >= 1) {
			fogen(lightValue-1);
		}
	}
	
	@Deprecated
	/**TODO Mettre en place un meilleur systeme de gestion de lumière pour que les objets soient éclairés de plus loin (fx de transformation)**/
	public void setLight(double lightValue) {
		if (lightValue >= -1 && lightValue < 0) {//plus noir
			double darkcoef = lightValue + 1;
			setRed((int) (darkcoef * getRed256()));
			setGreen((int) (darkcoef * getGreen256()));
			setBlue((int) (darkcoef * getBlue256()));
		} else if (lightValue > 0 && lightValue <= 1) {//plus blanc
			int lightCoef = (int) (255 * lightValue);
			double invVal = 1-lightValue;
			setRed((int) (getRed256() * invVal + lightCoef));
			setGreen((int) (getGreen256() * invVal + lightCoef));
			setBlue((int) (getBlue256() * invVal + lightCoef));
		} else if (lightValue < -1) {//noir à fond
			setRed(0);
			setGreen(0);
			setBlue(0);
		} else if (lightValue > 1) {//blanc à fond
			setRed(255);
			setGreen(255);
			setBlue(255);
		}
	}
	
	@Deprecated
	public void fogen(double coef) {
		if (coef <= 1 && coef > 0) {
			int off = (int) (255 * coef);
			double invCoef = 1-coef;
			setRed((int) (getRed256() * invCoef + off));
			setGreen((int) (getGreen256() * invCoef + off));
			setBlue((int) (getBlue256() * invCoef + off));
		} else if (coef > 1) {
			fogen(1);
		}
	}
	
	public static Color toJavaFXColor(MyColor c) {
		if (c != null) {
			return Color.rgb(c.getRed256(),c.getGreen256(),c.getBlue256());
		} else {
			return toJavaFXColor(NULL());
		}
		
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(opacity);
		result = prime * result + Arrays.hashCode(rgb);
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
		MyColor other = (MyColor) obj;
		if (Float.floatToIntBits(opacity) != Float.floatToIntBits(other.opacity))
			return false;
		if (!Arrays.equals(rgb, other.rgb))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "#" + Integer.toHexString(getRed256()) + Integer.toHexString(getGreen256()) + Integer.toHexString(getBlue256());
	}
}
