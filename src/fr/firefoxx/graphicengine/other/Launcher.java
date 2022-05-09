package fr.firefoxx.graphicengine.other;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import fr.firefoxx.graphicengine.rendering.Renderer;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;

public class Launcher extends Application{
	private static Stage WidowedEngine;
	private static int xPxResolution;
	private static int yPxResolution;
	private static int tileResolution;
	
	{
		int resol = 16;	
		//1, 2, 3, 4, 6, 8, 12, 16, 24, 32, 48, 96
		//1, 2,    4,    8,     16,     32
		
		int scaleInv = 1;//must be < resol
		xPxResolution = 1536/resol;
		yPxResolution = 864/resol;
		tileResolution = resol/scaleInv;
	}
	
	@Override
	public void init() throws Exception {
		
	}
	
	@Override
	public void start(Stage NewStage) throws Exception {
		WidowedEngine = new Stage();
		
		WidowedEngine.setTitle("ErwanEngine");
		WidowedEngine.setResizable(true);
		
		Group root = new Group();
		final Canvas canvas = new Canvas(xPxResolution * tileResolution,yPxResolution * tileResolution);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		PixelWriter pr = gc.getPixelWriter();
		root.getChildren().add(canvas);
		Scene scene = new Scene(root);
		WidowedEngine.setScene(scene);
		WidowedEngine.show();
		WidowedEngine.setFullScreen(false);
		
		Renderer renderer = new Renderer(pr, xPxResolution, yPxResolution, tileResolution);
		Thread th = new Thread(renderer);
        th.setDaemon(true);
        th.start();
        
        while(th.isAlive());
        System.out.print("Saving render... ");
        saveImage(canvas);
        System.out.println("Saved.");
	}
	
	@Override
	public void stop() throws Exception {
		System.out.println("Windows Stopped");
	}
	
	public static void main(String[] args) {
		launch(args); //lancement de l'application
	}
	
	private void saveImage(Canvas canvas) {
		WritableImage image = canvas.snapshot(new SnapshotParameters(), null);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy HH-mm-ssSSS");
		Date date = new Date();
		String st = dateFormat.format(date);
		File file = new File("C:/Users/Erwan/OneDrive - Universite De Technologie De Belfort-Montbeliard/Hors Cours/Info Perso/Erwan Engine/Render/" + st + ".png");
		try {
			if(file != null) {
				ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
			}
		} catch (IOException ex)  { 
			ex.printStackTrace();
		} 
	}
}
