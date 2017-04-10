package com.jlp.whatido;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;
import org.jnativehook.mouse.NativeMouseWheelEvent;
import org.jnativehook.mouse.NativeMouseWheelListener;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MyTracker implements NativeKeyListener, NativeMouseInputListener, NativeMouseWheelListener {
	Stage primaryStage;

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public static AudioClip clip;
	public static int wheelCount = 100;
	public static boolean isPlaying = false;
	Scene scene;
	AnchorPane root;
	int modifier1 = -1;
	int modifier2 = -1;
	int modifier3 = -1;

	Properties propsKeyboard = new Properties();
	Properties propsKeyboardTrad = new Properties();
	Properties propsCtrlMod = new Properties();
	Properties propsCtrlModINV = new Properties();

	int decalX = 0;
	int decalY = 0;
	javafx.collections.ObservableList<Node> list;
	Button btnMove = new Button();
	Image imMouseVide;
	Image imMouseCLK_L;
	Image imMouseCLK_R;
	Image imMouseCLK_ROUL;
	Image imMouseAV_ROUL;
	Image imMouseAR_ROUL;

	ImageView ivMouseVide;
	ImageView ivMouseCLK_L;
	ImageView ivMouseCLK_R;
	ImageView ivMouseCLK_ROUL;
	ImageView ivMouseAV_ROUL;
	ImageView ivMouseAR_ROUL;
	public static boolean moveFixed = false;
	public static AtomicBoolean isInProcess = new AtomicBoolean(false);

	TextField tfCrtl1 = new TextField();
	TextField tfCrtl2 = new TextField();
	TextField tfCarac = new TextField();
	String styleTfLarge = "-fx-min-width: 80;-fx-min-height: 30;-fx-max-width: 80;-fx-max-height: 30;-fx-background-color: transparent;"
			+ "-fx-border-color: black;-fx-border-width:3;-fx-font-family: Segoe UI Semibold;-fx-font-size:14;-fx-font-weight: bold;";
	String styleTfMedium = "-fx-min-width: 55;-fx-min-height: 25;-fx-max-width: 55;-fx-max-height: 25;-fx-background-color: transparent;"
			+ "-fx-border-color: black;-fx-border-width:3;-fx-font-family: Segoe UI Semibold;-fx-font-size:10;-fx-font-weight: bold;";
	String styleTfSmall = "-fx-min-width: 25;-fx-min-height: 12;-fx-max-width: 25;-fx-max-height: 12;-fx-background-color: transparent;"
			+ "-fx-border-color: black;-fx-border-width:1;-fx-font-family: Segoe UI Semibold;-fx-font-size:6;-fx-font-weight: bold;";
	public static HashMap<String, AudioClip> hmAudioClips = new HashMap<String, AudioClip>();
	String styleTf ="";
	public MyTracker(Stage stage) {
		// TODO Auto-generated constructor stub
		primaryStage = stage;

		try (InputStream in = new FileInputStream(Main.rootProject + File.separator + "config" + File.separator
				+ "keyboards" + File.separator + Main.keyboard + "_Code.properties");) {
			propsKeyboard.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try (InputStream in = new FileInputStream(Main.rootProject + File.separator + "config" + File.separator
				+ "keyboards" + File.separator + Main.language + "_Trad.properties");) {
			propsKeyboardTrad.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try (InputStream in = new FileInputStream(Main.rootProject + File.separator + "config" + File.separator
				+ "keyboards" + File.separator + Main.keyboard + "_CtrlMod.properties");) {
			propsCtrlMod.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		propsCtrlModINV = (Properties) Main.propsCtrlMod_INV.clone();

		switch (Main.sizeMouse) {

		case "Large":

			createSceneLargeFixed(stage);
			break;

		case "Medium":

			createSceneMedium(stage);

			break;

		case "Small":

			createSceneSmall(stage);

			break;

		case "Tiny":

			createSceneTinyMobile(stage);

			break;
		}
		if (Main.boolSounds) {
			loadsWav(Main.language);
		} else {
			String baseRep = Main.rootProject + File.separator + "config" + File.separator + "voices" + File.separator
					+ Main.language + File.separator;

			String key = "BYE";
			if (new File(baseRep + key + ".wav").exists()) {
				hmAudioClips.put(key, new AudioClip(new File(baseRep + key + ".wav").toURI().toString()));
			}
		}
	}

	private void loadsWav(String language) {

		// trouver toutes les valeurs de propsKeyBoard
		String baseRep = Main.rootProject + File.separator + "config" + File.separator + "voices" + File.separator
				+ Main.language + File.separator;
		ArrayList<Object> al = new ArrayList<Object>((Collection<Object>) propsKeyboard.values());

		Set<Object> set2 = new LinkedHashSet<>(al);
		Iterator<Object> it = set2.iterator();
		while (it.hasNext()) {
			String key = it.next().toString();
			if (new File(baseRep + key + ".wav").exists()) {
				hmAudioClips.put(key, new AudioClip(new File(baseRep + key + ".wav").toURI().toString()));
			}
		}

		// Ajout Evenement Souris
		String key = "CLK_L";
		if (new File(baseRep + key + ".wav").exists()) {
			hmAudioClips.put(key, new AudioClip(new File(baseRep + key + ".wav").toURI().toString()));
		}
		key = "CLK_R";
		if (new File(baseRep + key + ".wav").exists()) {
			hmAudioClips.put(key, new AudioClip(new File(baseRep + key + ".wav").toURI().toString()));
		}
		key = "CLK_M";
		if (new File(baseRep + key + ".wav").exists()) {
			hmAudioClips.put(key, new AudioClip(new File(baseRep + key + ".wav").toURI().toString()));
		}
		key = "WHEEL_AV";
		if (new File(baseRep + key + ".wav").exists()) {
			hmAudioClips.put(key, new AudioClip(new File(baseRep + key + ".wav").toURI().toString()));
		}
		key = "WHEEL_AR";
		if (new File(baseRep + key + ".wav").exists()) {
			hmAudioClips.put(key, new AudioClip(new File(baseRep + key + ".wav").toURI().toString()));
		}
		key = "BYE";
		if (new File(baseRep + key + ".wav").exists()) {
			hmAudioClips.put(key, new AudioClip(new File(baseRep + key + ".wav").toURI().toString()));
		}
	}

	private void createSceneSmall(Stage stage) {

		decalX = 40;
		decalY = 60;
		root = new AnchorPane();
		styleTf=this.styleTfSmall;
		btnMove.setStyle(" -fx-background-color: black;" + "-fx-background-radius: 15em; " + "-fx-min-width: 10px; "
				+ "-fx-min-height: 10px; " + "-fx-max-width: 10px; " + "-fx-max-height: 10px;");

		scene = new Scene(root, 84, 126);
		root.setStyle("-fx-background-color: transparent");
		root.setBackground(Background.EMPTY);
		btnMove.setOnMouseReleased(new EventHandler<MouseEvent>() {
		
		@Override
		public void handle(MouseEvent event) {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					if (moveFixed) {

						btnMove.setStyle(" -fx-background-color: black;" + "-fx-background-radius: 15em; "
								+ "-fx-min-width: 10px; " + "-fx-min-height: 10px; " + "-fx-max-width: 10px; "
								+ "-fx-max-height: 10px;");
						moveFixed = false;
					} else {

						btnMove.setStyle(" -fx-background-color: green;" + "-fx-background-radius: 15em; "
								+ "-fx-min-width: 10px; " + "-fx-min-height: 10px; " + "-fx-max-width: 10px; "
								+ "-fx-max-height: 10px;");
						moveFixed = true;
					}
				}
			});
			
		}
		});
		// Chargement des images
		URL imageURL;
		try {
			imageURL = new File(Main.rootProject + File.separator + "config" + File.separator + "images"
					+ File.separator + "small" + File.separator + "mouseVideS.png").toURI().toURL();
			imMouseVide = new Image(imageURL.toExternalForm());
			;
			ivMouseVide = new ImageView(imMouseVide);
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			imageURL = new File(Main.rootProject + File.separator + "config" + File.separator + "images"
					+ File.separator + "small" + File.separator + "mouseS_CLK_G.png").toURI().toURL();
			imMouseCLK_L = new Image(imageURL.toExternalForm());
			ivMouseCLK_L = new ImageView(imMouseCLK_L);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			imageURL = new File(Main.rootProject + File.separator + "config" + File.separator + "images"
					+ File.separator + "small" + File.separator + "mouseS_CLK_D.png").toURI().toURL();
			imMouseCLK_R = new Image(imageURL.toExternalForm());
			ivMouseCLK_R = new ImageView(imMouseCLK_R);

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			imageURL = new File(Main.rootProject + File.separator + "config" + File.separator + "images"
					+ File.separator + "small" + File.separator + "mouseS_CLK_ROUL.png").toURI().toURL();
			imMouseCLK_ROUL = new Image(imageURL.toExternalForm());
			ivMouseCLK_ROUL = new ImageView(imMouseCLK_ROUL);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			imageURL = new File(Main.rootProject + File.separator + "config" + File.separator + "images"
					+ File.separator + "small" + File.separator + "mouseS_AV_ROUL.png").toURI().toURL();
			imMouseAV_ROUL = new Image(imageURL.toExternalForm());
			ivMouseAV_ROUL = new ImageView(imMouseAV_ROUL);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			imageURL = new File(Main.rootProject + File.separator + "config" + File.separator + "images"
					+ File.separator + "small" + File.separator + "mouseS_AR_ROUL.png").toURI().toURL();
			imMouseAR_ROUL = new Image(imageURL.toExternalForm());
			ivMouseAR_ROUL = new ImageView(imMouseAR_ROUL);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 3 textField por 2 controles (6 lettres Maxi) et une Lettre

		HBox hbKeyboard = new HBox();

		tfCrtl1.setStyle(styleTfSmall);
		tfCrtl2.setStyle(styleTfSmall);
		tfCarac.setStyle(styleTfSmall);
		hbKeyboard.setStyle("-fx-background-color: transparent;");

		// test
		// tfCrtl1.setText("ALT_G");
		hbKeyboard.setSpacing(0);
		hbKeyboard.getChildren().add(tfCrtl1);
		hbKeyboard.getChildren().add(tfCrtl2);
		hbKeyboard.getChildren().add(tfCarac);

		root.getChildren().add(ivMouseVide);
		AnchorPane.setTopAnchor(ivMouseVide, 0.0);

		AnchorPane.setTopAnchor(btnMove, 70.0);
		AnchorPane.setLeftAnchor(btnMove, 35.0);

		root.getChildren().add(btnMove);

		AnchorPane.setTopAnchor(hbKeyboard, 85.0);
		AnchorPane.setLeftAnchor(hbKeyboard, 2.0);
		root.getChildren().add(hbKeyboard);
		//root.setStyle("-fx-background-color: transparent");
		stage.setAlwaysOnTop(true);
		stage.close();
		//scene.setFill(null);
		scene.setFill(Color.TRANSPARENT);
		
		stage.setScene(scene);
		//stage.setOpacity(0.0);
		stage.show();
		addListeners();
		stage.setX(Main.xFixed);
		stage.setY(Main.yFixed - decalY * 2);

	}

	private void createSceneTinyMobile(Stage stage) {

		// TODO Auto-generated method stub

		decalX = 12;
		decalY = 18;
		root = new AnchorPane();

		scene = new Scene(root, 24, 35);
		root.setStyle("-fx-background-color: transparent");

		// Chargement des images
		URL imageURL;
		try {
			imageURL = new File(Main.rootProject + File.separator + "config" + File.separator + "images"
					+ File.separator + "tiny" + File.separator + "mouseVideT.png").toURI().toURL();
			imMouseVide = new Image(imageURL.toExternalForm());
			;
			ivMouseVide = new ImageView(imMouseVide);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			imageURL = new File(Main.rootProject + File.separator + "config" + File.separator + "images"
					+ File.separator + "tiny" + File.separator + "mouseT_CLK_G.png").toURI().toURL();
			imMouseCLK_L = new Image(imageURL.toExternalForm());
			ivMouseCLK_L = new ImageView(imMouseCLK_L);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			imageURL = new File(Main.rootProject + File.separator + "config" + File.separator + "images"
					+ File.separator + "tiny" + File.separator + "mouseT_CLK_D.png").toURI().toURL();
			imMouseCLK_R = new Image(imageURL.toExternalForm());
			ivMouseCLK_R = new ImageView(imMouseCLK_R);

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			imageURL = new File(Main.rootProject + File.separator + "config" + File.separator + "images"
					+ File.separator + "tiny" + File.separator + "mouseT_CLK_ROUL.png").toURI().toURL();
			imMouseCLK_ROUL = new Image(imageURL.toExternalForm());
			ivMouseCLK_ROUL = new ImageView(imMouseCLK_ROUL);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			imageURL = new File(Main.rootProject + File.separator + "config" + File.separator + "images"
					+ File.separator + "tiny" + File.separator + "mouseT_AV_ROUL.png").toURI().toURL();
			imMouseAV_ROUL = new Image(imageURL.toExternalForm());
			ivMouseAV_ROUL = new ImageView(imMouseAV_ROUL);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			imageURL = new File(Main.rootProject + File.separator + "config" + File.separator + "images"
					+ File.separator + "tiny" + File.separator + "mouseT_AR_ROUL.png").toURI().toURL();
			imMouseAR_ROUL = new Image(imageURL.toExternalForm());
			ivMouseAR_ROUL = new ImageView(imMouseAR_ROUL);

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		root.getChildren().add(ivMouseVide);
		AnchorPane.setTopAnchor(ivMouseVide, 0.0);

		// root.getChildren().add(btnMove);

		/*
		 * AnchorPane.setTopAnchor(hbKeyboard, 14.0);
		 * AnchorPane.setLeftAnchor(hbKeyboard, 0.0);
		 * root.getChildren().add(hbKeyboard);
		 */
		stage.setAlwaysOnTop(true);
		stage.close();
		//scene.setFill(null);
		scene.setFill(Color.TRANSPARENT);
		stage.setScene(scene);
		stage.show();
		addListeners();
		stage.setX(Main.xFixed);
		stage.setY(Main.yFixed - decalY * 2);

	}

	private void createSceneMedium(Stage stage) {

		// TODO Auto-generated method stub
		styleTf=this.styleTfMedium;
		decalX = 100;
		decalY = 150;
		root = new AnchorPane();

		btnMove.setStyle(" -fx-background-color: black;" + "-fx-background-radius: 15em; " + "-fx-min-width: 10px; "
				+ "-fx-min-height: 10px; " + "-fx-max-width: 10px; " + "-fx-max-height: 10px;");

		scene = new Scene(root, 200, 300);
		root.setStyle("-fx-background-color: transparent");
		btnMove.setOnMouseReleased(new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent event) {
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						if (moveFixed) {

							btnMove.setStyle(" -fx-background-color: black;" + "-fx-background-radius: 15em; "
									+ "-fx-min-width: 10px; " + "-fx-min-height: 10px; " + "-fx-max-width: 10px; "
									+ "-fx-max-height: 10px;");
							moveFixed = false;
						} else {

							btnMove.setStyle(" -fx-background-color: green;" + "-fx-background-radius: 15em; "
									+ "-fx-min-width: 10px; " + "-fx-min-height: 10px; " + "-fx-max-width: 10px; "
									+ "-fx-max-height: 10px;");
							moveFixed = true;
						}
					}
				});
				
			}
			});
		// Chargement des images
		URL imageURL;
		try {
			imageURL = new File(Main.rootProject + File.separator + "config" + File.separator + "images"
					+ File.separator + "medium" + File.separator + "mouseVideM.png").toURI().toURL();
			imMouseVide = new Image(imageURL.toExternalForm());

			ivMouseVide = new ImageView(imMouseVide);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			imageURL = new File(Main.rootProject + File.separator + "config" + File.separator + "images"
					+ File.separator + "medium" + File.separator + "mouseM_CLK_G.png").toURI().toURL();
			imMouseCLK_L = new Image(imageURL.toExternalForm());
			ivMouseCLK_L = new ImageView(imMouseCLK_L);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			imageURL = new File(Main.rootProject + File.separator + "config" + File.separator + "images"
					+ File.separator + "medium" + File.separator + "mouseM_CLK_D.png").toURI().toURL();
			imMouseCLK_R = new Image(imageURL.toExternalForm());
			ivMouseCLK_R = new ImageView(imMouseCLK_R);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		;

		try {
			imageURL = new File(Main.rootProject + File.separator + "config" + File.separator + "images"
					+ File.separator + "medium" + File.separator + "mouseM_CLK_ROUL.png").toURI().toURL();
			imMouseCLK_ROUL = new Image(imageURL.toExternalForm());
			ivMouseCLK_ROUL = new ImageView(imMouseCLK_ROUL);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			imageURL = new File(Main.rootProject + File.separator + "config" + File.separator + "images"
					+ File.separator + "medium" + File.separator + "mouseM_AV_ROUL.png").toURI().toURL();
			imMouseAV_ROUL = new Image(imageURL.toExternalForm());
			ivMouseAV_ROUL = new ImageView(imMouseAV_ROUL);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			imageURL = new File(Main.rootProject + File.separator + "config" + File.separator + "images"
					+ File.separator + "medium" + File.separator + "mouseM_AR_ROUL.png").toURI().toURL();
			imMouseAR_ROUL = new Image(imageURL.toExternalForm());
			ivMouseAR_ROUL = new ImageView(imMouseAR_ROUL);

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		;

		// 3 textField por 2 controles (6 lettres Maxi) et une Lettre
		Label lbKb = new Label("Keyboard");
		lbKb.setStyle("-fx-text-fill : rgb(0,0,0);-fx-font-size: 18pt;" + "-fx-font-family: Segoe UI Semibold;");
		if (Main.keyboard.contains("AZERTY")) {
			lbKb.setText("Clavier");
		} else {
			lbKb.setText("Keyboard");
		}
		HBox hbKeyboard = new HBox();

		tfCrtl1.setStyle(styleTfMedium);
		tfCrtl2.setStyle(styleTfMedium);
		tfCarac.setStyle(styleTfMedium);
		hbKeyboard.setStyle("-fx-background-color: transparent;");

		// test
		// tfCrtl1.setText("ALT_G");
		hbKeyboard.setSpacing(5);
		hbKeyboard.getChildren().add(tfCrtl1);
		hbKeyboard.getChildren().add(tfCrtl2);
		hbKeyboard.getChildren().add(tfCarac);

		root.getChildren().add(ivMouseVide);
		AnchorPane.setTopAnchor(ivMouseVide, 0.0);

		AnchorPane.setTopAnchor(btnMove, 180.0);
		AnchorPane.setLeftAnchor(btnMove, 94.0);

		root.getChildren().add(btnMove);

		AnchorPane.setTopAnchor(lbKb, 225.0);
		AnchorPane.setLeftAnchor(lbKb, 60.0);
		root.getChildren().add(lbKb);
		AnchorPane.setTopAnchor(hbKeyboard, 200.0);
		AnchorPane.setLeftAnchor(hbKeyboard, 10.0);
		root.getChildren().add(hbKeyboard);
		stage.setAlwaysOnTop(true);
		stage.close();
		//scene.setFill(null);
		scene.setFill(Color.TRANSPARENT);
		stage.setScene(scene);
		stage.show();
		addListeners();
		stage.setX(Main.xFixed);
		stage.setY(Main.yFixed - decalY * 2);

	}

	private void createSceneLargeFixed(Stage stage) {
		styleTf=this.styleTfLarge;
		decalX = 200;
		decalY = 300;
		root = new AnchorPane();

		btnMove.setStyle(" -fx-background-color: black;" + "-fx-background-radius: 15em; " + "-fx-min-width: 10px; "
				+ "-fx-min-height: 10px; " + "-fx-max-width: 10px; " + "-fx-max-height: 10px;");

		scene = new Scene(root, 400, 600);
		root.setStyle("-fx-background-color: transparent");
		btnMove.setOnMouseReleased(new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent event) {
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						if (moveFixed) {

							btnMove.setStyle(" -fx-background-color: black;" + "-fx-background-radius: 15em; "
									+ "-fx-min-width: 10px; " + "-fx-min-height: 10px; " + "-fx-max-width: 10px; "
									+ "-fx-max-height: 10px;");
							moveFixed = false;
						} else {

							btnMove.setStyle(" -fx-background-color: green;" + "-fx-background-radius: 15em; "
									+ "-fx-min-width: 10px; " + "-fx-min-height: 10px; " + "-fx-max-width: 10px; "
									+ "-fx-max-height: 10px;");
							moveFixed = true;
						}
					}
				});
				
			}
			});
		// Chargement des images
		URL imageURL;
		try {
			imageURL = new File(Main.rootProject + File.separator + "config" + File.separator + "images"
					+ File.separator + "large" + File.separator + "mouseVideL400_600.png").toURI().toURL();
			imMouseVide = new Image(imageURL.toExternalForm());
			;
			ivMouseVide = new ImageView(imMouseVide);

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			imageURL = new File(Main.rootProject + File.separator + "config" + File.separator + "images"
					+ File.separator + "large" + File.separator + "mouseL_CLK_G400_600.png").toURI().toURL();
			imMouseCLK_L = new Image(imageURL.toExternalForm());
			ivMouseCLK_L = new ImageView(imMouseCLK_L);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			imageURL = new File(Main.rootProject + File.separator + "config" + File.separator + "images"
					+ File.separator + "large" + File.separator + "mouseL_CLK_D400_600.png").toURI().toURL();
			imMouseCLK_R = new Image(imageURL.toExternalForm());
			ivMouseCLK_R = new ImageView(imMouseCLK_R);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			imageURL = new File(Main.rootProject + File.separator + "config" + File.separator + "images"
					+ File.separator + "large" + File.separator + "mouseL_CLK_ROUL400_600.png").toURI().toURL();
			imMouseCLK_ROUL = new Image(imageURL.toExternalForm());
			ivMouseCLK_ROUL = new ImageView(imMouseCLK_ROUL);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			imageURL = new File(Main.rootProject + File.separator + "config" + File.separator + "images"
					+ File.separator + "large" + File.separator + "mouseL_AV_ROUL400_600.png").toURI().toURL();
			imMouseAV_ROUL = new Image(imageURL.toExternalForm());
			ivMouseAV_ROUL = new ImageView(imMouseAV_ROUL);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			imageURL = new File(Main.rootProject + File.separator + "config" + File.separator + "images"
					+ File.separator + "large" + File.separator + "mouseL_AR_ROUL400_600.png").toURI().toURL();
			imMouseAR_ROUL = new Image(imageURL.toExternalForm());
			ivMouseAR_ROUL = new ImageView(imMouseAR_ROUL);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 3 textField por 2 controles (6 lettres Maxi) et une Lettre
		Label lbKb = new Label("Keyboard");
		lbKb.setStyle("-fx-text-fill : rgb(0,0,0);-fx-font-size: 20pt;" + "-fx-font-family: Segoe UI Semibold;");
		if (Main.keyboard.contains("AZERTY")) {
			lbKb.setText("Clavier");
		} else {
			lbKb.setText("Keyboard");
		}
		HBox hbKeyboard = new HBox();

		tfCrtl1.setStyle(styleTfLarge);
		tfCrtl2.setStyle(styleTfLarge);
		tfCarac.setStyle(styleTfLarge);
		hbKeyboard.setStyle("-fx-background-color: transparent;");

		// test
		// tfCrtl1.setText("ALT_G");
		hbKeyboard.setSpacing(10);
		hbKeyboard.getChildren().add(tfCrtl1);
		hbKeyboard.getChildren().add(tfCrtl2);
		hbKeyboard.getChildren().add(tfCarac);

		root.getChildren().add(ivMouseVide);
		AnchorPane.setTopAnchor(ivMouseVide, 0.0);

		AnchorPane.setTopAnchor(btnMove, 400.0);
		AnchorPane.setLeftAnchor(btnMove, 200.0);

		root.getChildren().add(btnMove);

		AnchorPane.setTopAnchor(lbKb, 450.0);
		AnchorPane.setLeftAnchor(lbKb, 160.0);
		root.getChildren().add(lbKb);
		AnchorPane.setTopAnchor(hbKeyboard, 500.0);
		AnchorPane.setLeftAnchor(hbKeyboard, 60.0);
		root.getChildren().add(hbKeyboard);
		stage.setAlwaysOnTop(true);
		stage.close();
		//scene.setFill(null);
		scene.setFill(Color.TRANSPARENT);
		stage.setScene(scene);
		stage.show();
		addListeners();
		stage.setX(Main.xFixed);
		stage.setY(Main.yFixed - decalY * 2);
	}

	private void addListeners() {
		// TODO Auto-generated method stub
		System.out.println("Adding Listeners");
		// Get the logger for "org.jnativehook" and set the level to off.
		java.util.logging.Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());

		logger.setLevel(Level.OFF);

		// Change the level for all handlers attached to the default logger.
		Handler[] handlers = Logger.getLogger("").getHandlers();
		for (int i = 0; i < handlers.length; i++) {
			handlers[i].setLevel(Level.OFF);
		}

		try {
			GlobalScreen.registerNativeHook();
			GlobalScreen.addNativeKeyListener(this);
			GlobalScreen.addNativeMouseWheelListener(this);
			GlobalScreen.addNativeMouseListener(this);
			GlobalScreen.addNativeMouseMotionListener(this);
		} catch (NativeHookException ex) {
			System.err.println("There was a problem registering the native hook.");
			System.err.println(ex.getMessage());

			System.exit(1);
		}

	}

	@Override
	public synchronized void nativeKeyPressed(NativeKeyEvent nativeEvent) {
		// TODO Auto-generated method stub
		// System.out.println("Key Pressed nativeEvent.getKeyCode()=" +
		// nativeEvent.getKeyCode() + " ; modifiers = "
		// + nativeEvent.getModifiers());
		/*
		 * System.out.println( "Key Pressed getKeyCode : " +
		 * nativeEvent.getKeyCode() + " ;modifier=" +
		 * nativeEvent.getModifiers());
		 */
		String key = concat(nativeEvent.getKeyCode(), nativeEvent.getModifiers());
		Platform.runLater(new Runnable() {

			@Override
			public void run() {

				// System .out.println ( "nativeEvent.getKeyCode() =
				// "+nativeEvent.getKeyCode()+ " ; nativeEvent.getModifiers()
				// ="+nativeEvent.getModifiers());

				//System.out.println("key = " + key);
				if (propsCtrlMod.containsKey(propsKeyboard.getProperty(key, ""))) {
					// control
					if (null == tfCrtl1.getText() || tfCrtl1.getText().length() == 0) {
						tfCrtl1.setText(propsKeyboardTrad.getProperty(propsKeyboard.getProperty(key, ""), ""));
						tfCrtl1.setStyle(styleTf+"-fx-background-color: RGB(0,255,0);");

					} else {
						tfCrtl2.setText(propsKeyboardTrad.getProperty(propsKeyboard.getProperty(key, ""), ""));
						tfCrtl2.setStyle(styleTf+"-fx-background-color: RGB(0,255,0);");
					}
				} else {
					// character
					
					tfCarac.setText(propsKeyboardTrad.getProperty(propsKeyboard.getProperty(key, ""),
							propsKeyboard.getProperty(key, "")));
					if(tfCarac.getText().trim().length()>0){
					tfCarac.setStyle(styleTf+"-fx-background-color: RGB(0,255,0);");
					}
				}
			}
		});
		if (Main.boolSounds && !isPlaying) {
			// to play 2 audios in sequence
			if (propsKeyboard.getProperty(key, "").equals("CTRL_L")) {
				clip = hmAudioClips.get("CTRL_L");

				clip.play();
				while (clip.isPlaying()) {
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} else {
				new Thread(new Runnable() {

					@Override
					public void run() {

						if (!propsKeyboard.getProperty(key, "").equals("")  ){
						clip = hmAudioClips.get(propsKeyboard.getProperty(key, "").toUpperCase());
						//System.out.println("propsKeyboard.getProperty(key)=" + propsKeyboard.getProperty(key, ""));
						isPlaying = true;
						clip.play();

							while (clip.isPlaying()) {
							try {
								Thread.sleep(10);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

						isPlaying = false;
					}
					}

				}).start();
			}

		}
	}
	public static String getKeyStringFromValue(Properties hm, String value) {
		for (Object o : hm.keySet()) {
			if (((String) hm.get(o)).equals(value)) {
				return (String) o;
			}
		}
		return value;
	}
	public static Integer getKeyIntegerFromValue(Properties hm, String value) {
		for (Object o : hm.keySet()) {
			if (((String) hm.get(o)).equals(value)) {
				return Integer.parseInt(((String)o).split("_")[0]);
			}
		}
		return -1;
	}
	private String concat(int keyCode, int modifiers) {

		// For modifiers make a division modulo 8192 ( Verr Numm and Verr Cap)
		modifiers=modifiers % 8192;
		String ret = new StringBuilder().append(keyCode).append("_").append(modifiers).toString();

		if (propsCtrlMod.containsKey(propsKeyboard.getProperty(ret, ""))) {
			return ret;
		} else {
			// en fonction du modifierdr
			if (modifiers > 1 && modifiers < 128) {
				ret = new StringBuilder().append(keyCode).append("_1").toString();
				return ret;
			}
		}
		return new StringBuilder().append(keyCode).append("_").append(modifiers).toString();

	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent nativeEvent) {
		// TODO Auto-generated method stub
		// System.out.println("Key Realesased NativeKeyEvent.getKeyText : " +
		// NativeKeyEvent.getKeyText(nativeEvent.getKeyCode()));
		/*
		 * System.out.println( "Key Realesased getKeyCode : " +
		 * nativeEvent.getKeyCode() + " ;modifier=" +
		 * nativeEvent.getModifiers());
		 */

		int code=nativeEvent.getKeyCode();
		if ((this.tfCrtl1.getText().equals(Main.shutdownKey1) && this.tfCrtl2.getText().equals(Main.shutdownKey2)
				&& this.tfCarac.getText().equals(Main.shutdownKey3))
				|| (this.tfCrtl2.getText().equals(Main.shutdownKey1) && this.tfCrtl1.getText().equals(Main.shutdownKey2)
						&& this.tfCarac.getText().equals(Main.shutdownKey3)))

		{

			//hmAudioClips.get("BYE").play();
			AudioClip clip = hmAudioClips.get("BYE");
			clip.play();
			while (clip.isPlaying()) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			System.exit(0);
		}
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(Main.latency);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(null != tfCarac.getText()){
					//System.out.println("tfCarac.getText().trim()="+tfCarac.getText().trim());
					String chaine=getKeyStringFromValue(propsKeyboardTrad,tfCarac.getText().trim());
					//System.out.println("chaine=" + chaine);
					int code2=getKeyIntegerFromValue(propsKeyboard,chaine);
				   // System.out.println("code=" + code + " ;code2="+code2);
					if (code2 == code){
						tfCarac.clear();
						tfCarac.setStyle(styleTf+"-fx-background-color: transparent;");
					}
				}
				if(null != tfCrtl1.getText()){
					String chaine=getKeyStringFromValue(propsKeyboardTrad,tfCrtl1.getText().trim());
					int code2=Integer.parseInt(propsCtrlMod.getProperty(chaine ,"0_0").split("_")[0]);
					//int code2=getKeyIntegerFromValue(propsCtrlMod,chaine);
					if (code2 == code){
						tfCrtl1.clear();
						tfCrtl1.setStyle(styleTf+"-fx-background-color: transparent;");
					}
					
				}
				if(null != tfCrtl2.getText()){
					String chaine=getKeyStringFromValue(propsKeyboardTrad,tfCrtl2.getText().trim());
					int code2=Integer.parseInt(propsCtrlMod.getProperty(chaine ,"0_0").split("_")[0]);					if (code2 == code){
						tfCrtl2.clear();
						tfCrtl2.setStyle(styleTf+"-fx-background-color: transparent;");
					}
					
				}
				
			}
		});
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent nativeEvent) {
		// TODO Auto-generated method stub

		/*
		 * System.out.println( "Key Typed NativeKeyEvent.getKeyText : " +
		 * NativeKeyEvent.getKeyText(nativeEvent.getKeyCode()));
		 */

	}

	@Override
	public void nativeMouseClicked(NativeMouseEvent nativeEvent) {
		// TODO Auto-generated method stub
		// System.out.println("Mouse Clicked Button=" +
		// nativeEvent.getButton());
	}

	@Override
	public void nativeMousePressed(NativeMouseEvent nativeEvent) {
		// TODO Auto-generated method stub
		// System.out.println("Mouse Pressed Button=" +
		// nativeEvent.getButton());
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				if (moveFixed) {

					moveFixed = false;
					btnMove.setStyle(
							" -fx-background-color: black;" + "-fx-background-radius: 15em; " + "-fx-min-width: 10px; "
									+ "-fx-min-height: 10px; " + "-fx-max-width: 10px; " + "-fx-max-height: 10px;");
					primaryStage.setX(nativeEvent.getPoint().getX() - calculDecalX(nativeEvent.getPoint().getX()));
					primaryStage.setY(nativeEvent.getPoint().getY() - calculDecalY(nativeEvent.getPoint().getY()));

				}

				 root.getChildren().remove(0);
				 root.getChildren().add(0, ivMouseVide);

			}
		});

		switch (Main.hmWheel.getProperty(Integer.toString(nativeEvent.getButton()))) {
		case "0":
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub

					root.getChildren().remove(0);
					root.getChildren().add(0, ivMouseVide);
				}
			});

			break;
		case "L":
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub

					root.getChildren().remove(0);
					root.getChildren().add(0, ivMouseCLK_L);

				}
			});
			if (Main.boolSounds && !isPlaying) {

				new Thread(new Runnable() {

					@Override
					public void run() {

						isPlaying = true;
						clip = hmAudioClips.get("CLK_L");

						clip.play();

						while (clip.isPlaying()) {
							try {
								Thread.sleep(10);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

						isPlaying = false;
					}

				}).start();

			}

			break;
		case "R":
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub

					root.getChildren().remove(0);
					root.getChildren().add(0, ivMouseCLK_R);
				}
			});
			if (Main.boolSounds && !isPlaying) {

				new Thread(new Runnable() {

					@Override
					public void run() {

						isPlaying = true;
						clip = hmAudioClips.get("CLK_R");

						clip.play();

						while (clip.isPlaying()) {
							try {
								Thread.sleep(10);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

						isPlaying = false;
					}

				}).start();

			}
			break;

		case "M":
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub

					root.getChildren().remove(0);
					root.getChildren().add(0, ivMouseCLK_ROUL);
				}
			});
			if (Main.boolSounds && !isPlaying) {

				new Thread(new Runnable() {

					@Override
					public void run() {

						isPlaying = true;
						clip = hmAudioClips.get("CLK_M");

						clip.play();

						while (clip.isPlaying()) {
							try {
								Thread.sleep(10);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

						isPlaying = false;
					}

				}).start();

			}
			break;
		}
	}

	@Override
	public void nativeMouseReleased(NativeMouseEvent nativeEvent) {
		// TODO Auto-generated method stub
		// System.out.println("Mouse Released Button=" +
		// nativeEvent.getButton());
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				

				 root.getChildren().remove(0);
				 root.getChildren().add(0, ivMouseVide);

			}
		});
		

	}

	@Override
	public void nativeMouseDragged(NativeMouseEvent nativeEvent) {
		// TODO Auto-generated method stub
		// System.out.println("Mouse Dragged Button=" + nativeEvent.getButton()
		// + " ; " + nativeEvent.getPoint().getX());
		if (Main.mouseMobile.equals("Mobile"))

		{

			Platform.runLater(new Runnable() {

				@Override
				public void run() {

					// TODO Auto-generated method stub

					primaryStage.setX(nativeEvent.getPoint().getX() - calculDecalX(nativeEvent.getPoint().getX()));
					primaryStage.setY(nativeEvent.getPoint().getY() - calculDecalY(nativeEvent.getPoint().getY()));

				}

			});

		}

	}

	private double calculDecalX(double xMouse) {
		// Only For Mobile mouse ( Medium, Small and Tiny)

		if (xMouse > Main.screenWidth - decalX * 2) {
			return (decalX * 2 + Main.distFollowX);
		} else {
			return (Main.distFollowX);
		}

	}

	private double calculDecalY(double yMouse) {
		if (yMouse > Main.screenHeigh - decalY * 2) {
			return (decalY * 2 + Main.distFollowY);
		} else {
			return (-Main.distFollowY);
		}
	}

	@Override
	public void nativeMouseMoved(NativeMouseEvent nativeEvent) {
		// TODO Auto-generated method stub

		if (Main.mouseMobile.equals("Mobile"))

		{
			Platform.runLater(new Runnable() {

				@Override
				public void run() {

					// TODO Auto-generated method stub

					primaryStage.setX(nativeEvent.getPoint().getX() - calculDecalX(nativeEvent.getPoint().getX()));
					primaryStage.setY(nativeEvent.getPoint().getY() - calculDecalY(nativeEvent.getPoint().getY()));

				}
			});

		}

	}

	@Override
	public void nativeMouseWheelMoved(NativeMouseWheelEvent arg0) {
		// TODO Auto-generated method stub
		// System.out.println("Mouse Wheel Rotation Button=" +
		// arg0.getWheelRotation() + " ; " + arg0.getWheelDirection());

		if (arg0.getWheelRotation() < 0) {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub

					root.getChildren().remove(0);
					root.getChildren().add(0, ivMouseVide);
					root.getChildren().remove(0);
					root.getChildren().add(0, ivMouseAV_ROUL);

				}

			}

			);
			if (Main.boolSounds && !isPlaying) {
				new Thread(new Runnable() {

					@Override
					public void run() {

						isPlaying = true;
						clip = hmAudioClips.get("WHEEL_AV");

						clip.play();

						while (clip.isPlaying()) {
							try {
								Thread.sleep(0, 50);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

						isPlaying = false;
					}

				}).start();
				;
			}
		}

		else {

			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub

					root.getChildren().remove(0);
					root.getChildren().add(0, ivMouseVide);
					root.getChildren().remove(0);
					root.getChildren().add(0, ivMouseAR_ROUL);

				}

			});
			if (Main.boolSounds && !isPlaying) {

				new Thread(new Runnable() {

					@Override
					public void run() {
						isPlaying = true;
						clip = hmAudioClips.get("WHEEL_AR");
						clip.play();

						wheelCount = 0;
						while (clip.isPlaying()) {
							try {
								Thread.sleep(0, 50);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						isPlaying = false;
					}
				}).start();
			}
		}

	}
}
