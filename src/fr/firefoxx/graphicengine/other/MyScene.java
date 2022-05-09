package fr.firefoxx.graphicengine.other;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import fr.firefoxx.graphicengine.dimention3.light.DirectiveLight;
import fr.firefoxx.graphicengine.dimention3.light.Light;
import fr.firefoxx.graphicengine.dimention3.light.LightSource;
import fr.firefoxx.graphicengine.dimention3.math.Vector3;
import fr.firefoxx.graphicengine.dimention3.myobject.MyObject;
import fr.firefoxx.graphicengine.dimention3.myobject.Simple2d;
import fr.firefoxx.graphicengine.dimention3.shape2d.Plane3;
import fr.firefoxx.graphicengine.dimention3.shape2d.Triangle3;
import fr.firefoxx.graphicengine.display.Cam3;
import fr.firefoxx.graphicengine.data.MyColor;

public class MyScene {
	private ArrayList<MyObject> objectList;
	private ArrayList<Light> LightList;
	{
		objectList = new ArrayList<MyObject>();
		LightList = new ArrayList<Light>();
	}
	Cam3 cm;
	
	public boolean loadScene(String URI) {
		try {
			File file = new File(URI);
			Scanner myReader = new Scanner(file);

			List<Vector3> vectorList = new ArrayList<Vector3>();
			MyColor color = MyColor.NULL();
			
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				String[] array;
				
				if (data.length() != 0) {
					switch (data.charAt(0)) {
					
					case 'o'://objet
						array = data.replaceAll("o ", "").split(" ");
						String name = array[0];
						System.out.println("Loading : " + name + " object");
						if (array.length>=4) {
							float red = Float.parseFloat(array[1]);
							float green = Float.parseFloat(array[2]);
							float blue = Float.parseFloat(array[3]);
							color = new MyColor(red, green, blue);
						}
						break;
						
					case 'm'://camera
						array = data.replaceAll("m ", "").split(" ");
						int pc = Integer.parseInt(array[0]) -1;
						int vc = Integer.parseInt(array[1]) -1;
						cm.setCamPosition(vectorList.get(pc), vectorList.get(vc));
						break;
					
					case 'v'://vector
						array = data.replaceAll("v ", "").split(" ");
						double x = Double.parseDouble(array[0]);
						double y = Double.parseDouble(array[1]);
						double z = Double.parseDouble(array[2]);
						Vector3 v = new Vector3(x,y,z);
						vectorList.add(v);
						break;

					case 'f'://triangle
						array = data.replaceAll("f ", "").split(" ");
						int p1 = Integer.parseInt(array[0]) -1;
						int p2 = Integer.parseInt(array[1]) -1;
						int p3 = Integer.parseInt(array[2]) -1;
						Triangle3 t = new Triangle3(vectorList.get(p1),vectorList.get(p2),vectorList.get(p3), 0);
						Simple2d<Triangle3> to = new Simple2d<Triangle3>(t,color);
						objectList.add(to);
						break;
						
					default:
				        System.out.println(data);
					}
				}
				
		    }

			myReader.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (Exception other) {
			other.printStackTrace();
			return false;
		}
	}
	
	public MyScene(Cam3 cm) {
		this.cm = cm;
	}

	public Cam3 getCam() {
		return cm;
	}
	
	public ArrayList<MyObject> getObjectList() {
		return objectList;
	}

	public ArrayList<Light> getLightList() {
		return LightList;
	}
	
	public void addPlane() {
		Plane3 p = new Plane3(new Vector3(0,0,0), new Vector3(0,1,0));
		Simple2d<Plane3> pl = new Simple2d<Plane3>(p,MyColor.GREY(0.6f));
		objectList.add(pl);
	}

	public void addLights() {
		Vector3 pls1 = new Vector3(7.1f, 0.5f, -0.05f);
		LightSource ls1 = new LightSource(pls1, 25f, new MyColor(1.0f, 0.65f, 0.25f));
		//LightList.add(ls1);

		Vector3 vl1 = new Vector3(-0.30f, -1, -0.25f);
		//DirectiveLight cl1 = new DirectiveLight(vl1, new MyColor(0.58f, 0.81f, 1.0f).scal(0.5f));
		DirectiveLight cl1 = new DirectiveLight(vl1, new MyColor(1.0f, 1.0f, 1.0f));
		LightList.add(cl1);
	}
	
	public void addRGBLights() {
		Vector3 pls1 = new Vector3(4, 2f, 2);
		LightSource ls1 = new LightSource(pls1, 15f, new MyColor(0.2f, 1.0F, 0.2f));
		LightList.add(ls1);

		Vector3 pls2 = new Vector3(4, 2f, -2);
		LightSource ls2 = new LightSource(pls2, 15f, new MyColor(0.2f, 0.2F, 1.0f));
		LightList.add(ls2);

		Vector3 pls3 = new Vector3(2, 2f, 0);
		LightSource ls3 = new LightSource(pls3, 15f, new MyColor(1.0F, 0.2f, 0.2f));
		LightList.add(ls3);
	}
	
	public void addCam() {
		cm.setCamPosition(new Vector3(0,2,0), new Vector3(1,-1/5,0));
	}
	
	public void addSuns() {
		Vector3 vl1 = new Vector3(-0.5f, -1, -0.5f);
		DirectiveLight cl1 = new DirectiveLight(vl1, MyColor.WHITE());
		LightList.add(cl1);
		
		Vector3 vl3 = new Vector3(0, -1, 0);
		DirectiveLight cl3 = new DirectiveLight(vl3, MyColor.WHITE().setOpacity(0.5f));
		LightList.add(cl3);
	}
	
	/**
	public void setScene1() {
		Vector3 p3 = new Vector3(10,1,0);
		double r2 = 0.8;
		Sphere sp = new Sphere(p3, r2, MyColor.YELLOW());
		objectList.add(sp);
		
		Vector3 p1 = new Vector3(6,0,-2);
		Vector3 p2 = new Vector3(6,1,-2);
		Vector3 p4 = new Vector3(0,0,0);
		Vector3 p5 = new Vector3(6,0,2);
		Vector3 p6 = new Vector3(5.3,1,2.3);
		
		Vector3 p7 = new Vector3(16,1,0.3);
		Vector3 p8 = new Vector3(15.3,0,1);
		double r1 = 1;

		Vector3 v1 = new Vector3(1,0,-1);
		Vector3 v2 = new Vector3(1,0,1);
		Vector3 v3 = new Vector3(0,1,0);

		Vector3 cube1vx = new Vector3(1,0,0);
		Vector3 cube1vy = new Vector3(0,1,0);
		Vector3 cube1vz = new Vector3(0,0,1);
		
		Vector3 cube2vx = new Vector3(1,0,1).Normalize();
		Vector3 cube2vy = new Vector3(0,1,0).Normalize();
		Vector3 cube2vz = new Vector3(1,0,-1).Normalize();

		Disk3 d1 = new Disk3(p1, v1, r1);
		Disk3 d2 = new Disk3(p2, v2, r1);
		Plane3 pl1 = new Plane3(p4, v3);
		
		Simple2d<Disk3> od1 = new Simple2d<Disk3>(d1, MyColor.RED());
		Simple2d<Disk3> od2 = new Simple2d<Disk3>(d2, MyColor.BLUE());
		Simple2d<Plane3> opl1 = new Simple2d<Plane3>(pl1, MyColor.GREY(200));
		ComplexObj cube1 = ComplexObj.Parallelepipede(p5, cube1vx, cube1vy, cube1vz, MyColor.WHITE());
		ComplexObj cube2 = ComplexObj.Parallelepipede(p6, cube2vx, cube2vy, cube2vz, MyColor.BLACK());
		ComplexObj cube3 = ComplexObj.Parallelepipede(p7, cube1vx, cube1vy, cube1vz, MyColor.GREEN());
		ComplexObj cube4 = ComplexObj.Parallelepipede(p8, cube2vx, cube2vy, cube2vz, MyColor.BLUE());

		objectList.add(od1);
		objectList.add(od2);
		objectList.add(opl1);
		objectList.add(cube1);
		objectList.add(cube2);
		objectList.add(cube3);
		objectList.add(cube4);
		
		
		Vector3 vl1 = new Vector3(-1, -1, -1);
		DirectiveLight cl1 = new DirectiveLight(vl1, MyColor.GREY(1.0F, 0.7f));
		Vector3 vl2 = new Vector3(-1, -1, 0);
		DirectiveLight cl2 = new DirectiveLight(vl2, MyColor.GREY(1.0F, 0.6f));
		LightList.add(cl1);
		LightList.add(cl2);

		Vector3 pc = new Vector3(-1,4,0);
		Vector3 vc = new Vector3(1,-0.35f,0);
		cm.setCamPosition(pc, vc);
	}
	
	public void setScene2() {
		Vector3 p3 = new Vector3(4,0,0);
		double r2 = 0.8;
		Sphere sp = new Sphere(p3, r2, MyColor.WHITE());
		objectList.add(sp);
		
		Vector3 p4 = new Vector3(0,0,0);
		Vector3 v3 = new Vector3(0,1,0);

		Plane3 pl1 = new Plane3(p4, v3);
		
		Simple2d<Plane3> opl1 = new Simple2d<Plane3>(pl1, MyColor.GREY(0.8f));
		objectList.add(opl1);
		
		
		Vector3 vl1 = new Vector3(0.5, -1, 0);
		DirectiveLight cl1 = new DirectiveLight(vl1, MyColor.GREEN().setOpacity(0.5f));
		Vector3 vl2 = new Vector3(0, -0.5, -1);
		DirectiveLight cl2 = new DirectiveLight(vl2, MyColor.RED().setOpacity(0.5f));
		Vector3 vl3 = new Vector3(0, -0.5, 1);
		DirectiveLight cl3 = new DirectiveLight(vl3, MyColor.BLUE().setOpacity(0.5f));
		//LightList.add(cl1);
		//LightList.add(cl2);
		//LightList.add(cl3);
		

		Vector3 pls1 = new Vector3(3, 1f, 1);
		LightSource ls1 = new LightSource(pls1, 15f, MyColor.GREEN());
		LightList.add(ls1);

		Vector3 pls2 = new Vector3(3, 1f, -1);
		LightSource ls2 = new LightSource(pls2, 15f, MyColor.BLUE());
		LightList.add(ls2);

		Vector3 pls3 = new Vector3(2, 1f, 0);
		LightSource ls3 = new LightSource(pls3, 15f, MyColor.RED());
		LightList.add(ls3);

		Vector3 pc = new Vector3(0,1,0);
		Vector3 vc = new Vector3(1,-0.15f,0);
		cm.setCamPosition(pc, vc);
	}
	
	public void setSceneNight() {
		Vector3 p3 = new Vector3(10,1,0);
		double r2 = 0.8;
		Sphere sp = new Sphere(p3, r2, MyColor.YELLOW());
		objectList.add(sp);
		
		Vector3 p1 = new Vector3(6,0,-2);
		Vector3 p2 = new Vector3(6,1,-2);
		Vector3 p4 = new Vector3(0,0,0);
		Vector3 p5 = new Vector3(6,0,2);
		Vector3 p6 = new Vector3(5.3,1,2.3);
		
		Vector3 p7 = new Vector3(16,1,0.3);
		Vector3 p8 = new Vector3(15.3,0,1);
		double r1 = 1;

		Vector3 v1 = new Vector3(1,0,-1);
		Vector3 v2 = new Vector3(1,0,1);
		Vector3 v3 = new Vector3(0,1,0);

		Vector3 cube1vx = new Vector3(1,0,0);
		Vector3 cube1vy = new Vector3(0,1,0);
		Vector3 cube1vz = new Vector3(0,0,1);
		
		Vector3 cube2vx = new Vector3(1,0,1).Normalize();
		Vector3 cube2vy = new Vector3(0,1,0).Normalize();
		Vector3 cube2vz = new Vector3(1,0,-1).Normalize();

		Disk3 d1 = new Disk3(p1, v1, r1);
		Disk3 d2 = new Disk3(p2, v2, r1);
		Plane3 pl1 = new Plane3(p4, v3);
		
		Simple2d<Disk3> od1 = new Simple2d<Disk3>(d1, MyColor.RED());
		Simple2d<Disk3> od2 = new Simple2d<Disk3>(d2, MyColor.BLUE());
		Simple2d<Plane3> opl1 = new Simple2d<Plane3>(pl1, MyColor.GREY(200));
		ComplexObj cube1 = ComplexObj.Parallelepipede(p5, cube1vx, cube1vy, cube1vz, MyColor.WHITE());
		ComplexObj cube2 = ComplexObj.Parallelepipede(p6, cube2vx, cube2vy, cube2vz, MyColor.BLACK());
		ComplexObj cube3 = ComplexObj.Parallelepipede(p7, cube1vx, cube1vy, cube1vz, MyColor.GREEN());
		ComplexObj cube4 = ComplexObj.Parallelepipede(p8, cube2vx, cube2vy, cube2vz, MyColor.BLUE());

		objectList.add(od1);
		objectList.add(od2);
		objectList.add(opl1);
		objectList.add(cube1);
		objectList.add(cube2);
		objectList.add(cube3);
		objectList.add(cube4);
		
		
		Vector3 vl1 = new Vector3(-1, -1, -1);
		DirectiveLight cl1 = new DirectiveLight(vl1, MyColor.GREY(1.0F, 0.7f));
		Vector3 vl2 = new Vector3(-1, -1, 0);
		DirectiveLight cl2 = new DirectiveLight(vl2, MyColor.GREY(1.0F, 0.6f));
		//LightList.add(cl1);
		//LightList.add(cl2);
		
		Vector3 pls1 = new Vector3(6, 0.001f, -2);
		LightSource ls1 = new LightSource(pls1, 15f, MyColor.RED());
		LightList.add(ls1);

		Vector3 pls2 = new Vector3(13, 0.001f, 0);
		LightSource ls2 = new LightSource(pls2, 15f, MyColor.GREEN());
		LightList.add(ls2);
		
		Vector3 pls3 = new Vector3(6, 0.001f, 2);
		LightSource ls3 = new LightSource(pls3, 15f, MyColor.BLUE());
		LightList.add(ls3);
		
		Vector3 pc = new Vector3(-1,4,0);
		Vector3 vc = new Vector3(1,-0.35f,0);
		cm.setCamPosition(pc, vc);
	}
	
	public void setSceneLightTest() {
		Vector3 p1 = new Vector3(8,0,-0.5);
		Vector3 p2 = new Vector3(8,0,0.5);
		Vector3 p3 = new Vector3(4,0,2);
		Vector3 p4 = new Vector3(4,0,3);
		Vector3 p5 = new Vector3(4,0,-3);
		Vector3 p6 = new Vector3(4,0,-2);

		Vector3 v1 = new Vector3(1,0,1);
		Vector3 v2 = new Vector3(1,0,-1);
		Vector3 v3 = new Vector3(0,1,0);

		Losange3 l1 = new Losange3(v1, v3, p1);
		Losange3 l2 = new Losange3(v2, v3, p2);
		Losange3 l3 = new Losange3(v1, v3, p3);
		Losange3 l4 = new Losange3(v2, v3, p4);
		Losange3 l5 = new Losange3(v1, v3, p5);
		Losange3 l6 = new Losange3(v2, v3, p6);
		
		Plane3 pl1 = new Plane3(p4, v3);

		Simple2d<Plane3> opl1 = new Simple2d<Plane3>(pl1, MyColor.GREY(200));
		Simple2d<Losange3> ol1 = new Simple2d<Losange3>(l1, MyColor.BLUE());
		Simple2d<Losange3> ol2 = new Simple2d<Losange3>(l2, MyColor.CYAN());
		Simple2d<Losange3> ol3 = new Simple2d<Losange3>(l3, MyColor.GREEN());
		Simple2d<Losange3> ol4 = new Simple2d<Losange3>(l4, MyColor.GREY(10));
		Simple2d<Losange3> ol5 = new Simple2d<Losange3>(l5, MyColor.WHITE());
		Simple2d<Losange3> ol6 = new Simple2d<Losange3>(l6, MyColor.YELLOW());

		objectList.add(opl1);
		objectList.add(ol1);
		objectList.add(ol2);
		objectList.add(ol3);
		objectList.add(ol4);
		objectList.add(ol5);
		objectList.add(ol6);

		Vector3 pls1 = new Vector3(7, 0.001f, 0);
		LightSource ls1 = new LightSource(pls1, 2f, MyColor.GREEN());
		LightList.add(ls1);

		Vector3 pls2 = new Vector3(5, 0.001f, -1);
		LightSource ls2 = new LightSource(pls2, 2f, MyColor.RED());
		LightList.add(ls2);
		
		Vector3 pls3 = new Vector3(5, 0.001f, 1);
		LightSource ls3 = new LightSource(pls3, 2f, MyColor.BLUE());
		LightList.add(ls3);
		
		Vector3 pc = new Vector3(-4,4,0);
		Vector3 vc = new Vector3(1,-0.35f,0);
		cm.setCamPosition(pc, vc);
	}
	
	public void setSceneSSAOtest() {
		Vector3 p1 = new Vector3(6,0,-2);
		Vector3 p2 = new Vector3(6,1,-2);
		Vector3 p3 = new Vector3(10,0.8,2);
		Vector3 p4 = new Vector3(0,0,0);
		Vector3 p5 = new Vector3(6,0,2);
		Vector3 p6 = new Vector3(5.3,1,2.3);
		
		Vector3 p7 = new Vector3(16,1,0.3);
		Vector3 p8 = new Vector3(15.3,0,1);
		double r1 = 1;
		double r2 = 0.8;

		Vector3 v1 = new Vector3(1,0,-1);
		Vector3 v2 = new Vector3(1,0,1);
		Vector3 v3 = new Vector3(0,1,0);

		Vector3 cube1vx = new Vector3(1,0,0);
		Vector3 cube1vy = new Vector3(0,1,0);
		Vector3 cube1vz = new Vector3(0,0,1);
		
		Vector3 cube2vx = new Vector3(1,0,1).Normalize();
		Vector3 cube2vy = new Vector3(0,1,0).Normalize();
		Vector3 cube2vz = new Vector3(1,0,-1).Normalize();

		Disk3 d1 = new Disk3(p1, v1, r1);
		Disk3 d2 = new Disk3(p2, v2, r1);
		Plane3 pl1 = new Plane3(p4, v3);
		
		Simple2d<Disk3> od1 = new Simple2d<Disk3>(d1, MyColor.RED());
		Simple2d<Disk3> od2 = new Simple2d<Disk3>(d2, MyColor.BLUE());
		Simple2d<Plane3> opl1 = new Simple2d<Plane3>(pl1, MyColor.GREY(200));
		Sphere sp = new Sphere(p3, r2, MyColor.YELLOW());
		ComplexObj cube1 = ComplexObj.Parallelepipede(p5, cube1vx, cube1vy, cube1vz, MyColor.GREEN());
		ComplexObj cube2 = ComplexObj.Parallelepipede(p6, cube2vx, cube2vy, cube2vz, MyColor.BLUE());
		ComplexObj cube3 = ComplexObj.Parallelepipede(p7, cube1vx, cube1vy, cube1vz, MyColor.GREEN());
		ComplexObj cube4 = ComplexObj.Parallelepipede(p8, cube2vx, cube2vy, cube2vz, MyColor.BLUE());

		objectList.add(od1);
		objectList.add(od2);
		objectList.add(opl1);
		objectList.add(sp);
		objectList.add(cube1);
		objectList.add(cube2);
		objectList.add(cube3);
		objectList.add(cube4);
		
		int a = 10;
		Vector3 p1t = new Vector3(a,0,0);
		Vector3 p2t = new Vector3(a,2,0);
		Vector3 p3t = new Vector3(4,0,0.1f);
		Vector3 p5t = new Vector3(4,0,-0.3f);
		Losange3 lzd = new Losange3(p1t,p2t,p3t);
		Losange3 lzg = new Losange3(p1t,p2t,p5t);
		Simple2d<Losange3> olzd = new Simple2d<Losange3>(lzd, new MyColor(220, 12, 30));
		Simple2d<Losange3> olzg = new Simple2d<Losange3>(lzg, new MyColor(220, 12, 30));
		objectList.add(olzd);
		objectList.add(olzg);
		
		
		Vector3 vl1 = new Vector3(-1, -1, -1);
		DirectiveLight cl1 = new DirectiveLight(vl1, MyColor.GREY(1.0f));
		LightList.add(cl1);
		
		Vector3 pc = new Vector3(0,0.05f,0);
		Vector3 vc = new Vector3(1,0,0);
		cm.setCamPosition(pc, vc);
	}

	public void setSceneTest() {
		Vector3 p1 = new Vector3(4.281960, 0.240353, -3.214861);
		Vector3 p2 = new Vector3(2.281959, 0.240353, -3.214861);
		Vector3 p3 = new Vector3(2.281959, 0.240352, -1.214861);
		Vector3 p4 = new Vector3(4.281960, 0.240352, -1.214861);
		Vector3 p5 = new Vector3(4.281960, -1.759648, -1.214861);
		Vector3 p6 = new Vector3(2.281959, -1.759648, -1.214861);
		Vector3 p7 = new Vector3(2.281959, -1.759647, -3.214861);
		Vector3 p8 = new Vector3(4.281960, -1.759647, -3.214861);
		Losange3 t1 = new Losange3(p2, p4, p1);
		Losange3 t2 = new Losange3(p4, p6, p5);
		Losange3 t3 = new Losange3(p3, p7, p6);
		Losange3 t4 = new Losange3(p8, p6, p7);
		Losange3 t5 = new Losange3(p1, p5, p8);
		Triangle3 t6 = new Triangle3(p2, p3, p4);
		Triangle3 t7 = new Triangle3(p4, p3, p6);
		Triangle3 t8 = new Triangle3(p3, p2, p7);
		Triangle3 t9 = new Triangle3(p8, p5, p6);
		Triangle3 t10 = new Triangle3(p1, p4, p5);
		Triangle3 t11 = new Triangle3(p2, p1, p8);
		Triangle3 t12 = new Triangle3(p2, p8, p7);
		Simple2d<Losange3> o1 = new Simple2d<Losange3>(t1, MyColor.WHITE());
		Simple2d<Losange3> o2 = new Simple2d<Losange3>(t2, MyColor.RED());
		Simple2d<Losange3> o3 = new Simple2d<Losange3>(t3, MyColor.GREEN());
		Simple2d<Losange3> o4 = new Simple2d<Losange3>(t4, MyColor.BLUE());
		Simple2d<Losange3> o5 = new Simple2d<Losange3>(t5, MyColor.MAGENTA());
		objectList.add(o1);
		objectList.add(o2);
		objectList.add(o3);
		objectList.add(o4);
		objectList.add(o5);

		
		Vector3 vl = new Vector3(-1, -1, -1);
		DirectiveLight cl = new DirectiveLight(vl, MyColor.GREY(0.5f));
		LightList.add(cl);
		
		Vector3 pc = new Vector3(0,0,0);
		Vector3 vc = new Vector3(1,0,0);
		cm.setCamPosition(pc, vc);
	}
	
	public void setSceneTest2() {
		Vector3 p1 = new Vector3(1, -0.5f, 0);
		Vector3 p2 = new Vector3(1, 0.5f, 0);
		Vector3 p3 = new Vector3(2, 0.5f, 0.5f);
		Triangle3 t1 = new Triangle3(p2, p1, p3, 0);
		Simple2d<Triangle3> o1 = new Simple2d<Triangle3>(t1, MyColor.WHITE());
		objectList.add(o1);

		Vector3 p4 = new Vector3(2, 0.5f, -0.5f);
		Losange3 l1 = new Losange3(p2, p1, p4, 0);
		Simple2d<Losange3> o2 = new Simple2d<Losange3>(l1, MyColor.WHITE());
		objectList.add(o2);

		
		Vector3 vl = new Vector3(-1, -1, -1);
		DirectiveLight cl = new DirectiveLight(vl, MyColor.GREY(0.5f));
		LightList.add(cl);
		
		Vector3 pc = new Vector3(0,0,0);
		Vector3 vc = new Vector3(1,0,0);
		cm.setCamPosition(pc, vc);
	}
	
	public void setEmptyScene() {
		
		//objectList.add(od3);
		
		//LightList.add(cl);
		
		Vector3 pc = new Vector3(0,0,0);
		Vector3 vc = new Vector3(1,0,0);
		cm.setCamPosition(pc, vc);
	}
	**/
}
