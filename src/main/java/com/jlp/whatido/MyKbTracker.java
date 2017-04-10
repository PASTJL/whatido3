package com.jlp.whatido;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MyKbTracker implements NativeKeyListener, NativeMouseInputListener {
	static String pat = "\\d+_\\d+";
	Properties propsKeyboard = new Properties();
	Properties propsKeyboardTrad = new Properties();
	Properties propsKeyboardPosition = new Properties();
	Image imKeyboard;
	Image imRed1;
	Image imRed2;
	Image imBlue1;
	ImageView ivKeyboard;
	ImageView ivRed1;
	ImageView ivRed2;
	ImageView ivBlue1;
	public static boolean moveKbFixed = false;
	Stage primaryStage;
	Scene scene;
	AnchorPane root;
	Button btnMove = new Button();
	double decalX = 0;
	double decalY = 0;
	public static boolean boolRed1 = false;
	public static boolean boolRed2 = false;
	public HashMap<Integer, ImageView> hmPressed = new HashMap<Integer, ImageView>();

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public MyKbTracker(Stage stage) {
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
				+ "keyboards" + File.separator + Main.keyboard + "_Position.properties");) {
			propsKeyboardPosition.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		root = new AnchorPane();

		btnMove.setStyle(" -fx-background-color: black;" + "-fx-background-radius: 15em; " + "-fx-min-width: 10px; "
				+ "-fx-min-height: 10px; " + "-fx-max-width: 10px; " + "-fx-max-height: 10px;");

		scene = new Scene(root, 800, 275);
		root.setStyle("-fx-background-color: transparent");
		btnMove.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						if (moveKbFixed) {

							btnMove.setStyle(" -fx-background-color: black;" + "-fx-background-radius: 15em; "
									+ "-fx-min-width: 10px; " + "-fx-min-height: 10px; " + "-fx-max-width: 10px; "
									+ "-fx-max-height: 10px;");
							moveKbFixed = false;
						} else {

							btnMove.setStyle(" -fx-background-color: green;" + "-fx-background-radius: 15em; "
									+ "-fx-min-width: 10px; " + "-fx-min-height: 10px; " + "-fx-max-width: 10px; "
									+ "-fx-max-height: 10px;");
							moveKbFixed = true;
						}
					}
				});

			}
		});

		// Chargement des images
		URL imageURL;
		try {
			imageURL = new File(Main.rootProject + File.separator + "config" + File.separator + "images"
					+ File.separator + "keyboards" + File.separator + Main.keyboard + ".png").toURI().toURL();
			imKeyboard = new Image(imageURL.toExternalForm());

			ivKeyboard = new ImageView(imKeyboard);

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			imageURL = new File(Main.rootProject + File.separator + "config" + File.separator + "images"
					+ File.separator + "keyboards" + File.separator + "blue1.png").toURI().toURL();
			imBlue1 = new Image(imageURL.toExternalForm());

			ivBlue1 = new ImageView(imBlue1);

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			imageURL = new File(Main.rootProject + File.separator + "config" + File.separator + "images"
					+ File.separator + "keyboards" + File.separator + "red1.png").toURI().toURL();
			imRed1 = new Image(imageURL.toExternalForm());

			ivRed1 = new ImageView(imRed1);

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			imageURL = new File(Main.rootProject + File.separator + "config" + File.separator + "images"
					+ File.separator + "keyboards" + File.separator + "red2.png").toURI().toURL();
			imRed2 = new Image(imageURL.toExternalForm());

			ivRed2 = new ImageView(imRed2);

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		root.getChildren().add(ivKeyboard);

		AnchorPane.setTopAnchor(btnMove, 250.0);
		AnchorPane.setLeftAnchor(btnMove, 350.0);

		root.getChildren().add(btnMove);
		stage.setAlwaysOnTop(true);
		stage.close();
		// scene.setFill(null);
		scene.setFill(Color.TRANSPARENT);
		stage.setScene(scene);
		stage.show();
		decalX = (double) (Main.screenWidth - 850);
		decalY = (double) (Main.screenHeigh - 300);
		stage.setX(Main.screenWidth - 850);
		stage.setY(Main.screenHeigh - 300);
		addListeners();

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
			GlobalScreen.addNativeMouseListener(this);

		} catch (NativeHookException ex) {
			System.err.println("There was a problem registering the native hook.");
			System.err.println(ex.getMessage());

			System.exit(1);
		}

	}

	@Override
	public void nativeKeyPressed(NativeKeyEvent arg0) {

		// recuperer le code et le modifier de la touche
		int code = arg0.getKeyCode();
		int modifiers = arg0.getModifiers() % 8192;

		// on verifie que la touche n'est pas shift, ctrl ou AltGr
		switch (code) {
		case 29:

			// Ctrl G mod =2 CtrlD mod=32
		case 42:
			// shift G
		case 3638:
			// shift D
		case 56:
			// Alt Modif 8 56_8
			// Alt Gr modif 130 56_130

			String XY = this.propsKeyboardPosition.getProperty(Integer.toString(code) + "_" + modifiers);
			if (null != XY && XY.matches(pat)) {
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						int absc = 0;
						int ord = 0;
						// System.out.println("code="+code+ " ;
						// modifiers="+modifiers);;

						absc = Integer.parseInt(XY.split("_")[0]) - 15; // image
																		// is
																		// 30*30
																		// px
						ord = Integer.parseInt(XY.split("_")[1]) - 15;
						if (!boolRed1) {
							AnchorPane.setTopAnchor(ivRed1, (double) ord);
							AnchorPane.setLeftAnchor(ivRed1, (double) absc);
							root.getChildren().remove(ivRed1);
							root.getChildren().add(ivRed1);
							boolRed1 = true;
							hmPressed.put(code, ivRed1);
						} else {
							AnchorPane.setTopAnchor(ivRed2, (double) ord);
							AnchorPane.setLeftAnchor(ivRed2, (double) absc);
							root.getChildren().remove(ivRed2);
							root.getChildren().add(ivRed2);
							hmPressed.put(code, ivRed2);
							if (modifiers >= 128 && modifiers < 140) {
								Integer in = getKeyFromValue(hmPressed, ivRed1);
								hmPressed.remove(in);
								root.getChildren().remove(ivRed1);

							}
							boolRed2 = true;

						}
					}
				});
			}

			break;

		default:
			// toutes les aures touches
			XY = this.propsKeyboardPosition.getProperty(Integer.toString(code) + "_0");
			if (null != XY && XY.matches(pat)) {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					int absc = 0;
					int ord = 0;
					absc = Integer.parseInt(XY.split("_")[0]) - 15; // image is
																	// 30*30 px
					ord = Integer.parseInt(XY.split("_")[1]) - 15;
					AnchorPane.setTopAnchor(ivBlue1, (double) ord);
					AnchorPane.setLeftAnchor(ivBlue1, (double) absc);
					root.getChildren().remove(ivBlue1);
					root.getChildren().add(ivBlue1);
					hmPressed.put(code, ivBlue1);
				}
			});
			}
			break;
		}

	}

	public static Integer getKeyFromValue(Map<Integer, ImageView> hm, ImageView value) {
		for (Integer o : hm.keySet()) {
			if (hm.get(o).equals(value)) {
				return o;
			}
		}
		return null;
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent arg0) {
		// TODO Auto-generated method stub
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				int code = arg0.getKeyCode();

				if (hmPressed.containsKey(code)) {

					ImageView iv = hmPressed.get(code);
					hmPressed.remove(code);
					if (null != iv) {
						if (iv.equals(ivRed1)) {
							boolRed1 = false;
							root.getChildren().remove(ivRed1);
							root.getChildren().remove(ivRed2);
						} else if (iv.equals(ivRed2)) {
							boolRed2 = false;
							root.getChildren().remove(ivRed1);
							root.getChildren().remove(ivRed2);
						} else
							root.getChildren().remove(ivBlue1);
					}

				}

			}
		});
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void nativeMouseClicked(NativeMouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void nativeMousePressed(NativeMouseEvent nativeEvent) {

		// TODO Auto-generated method stub
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(Main.latency);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (moveKbFixed) {
					moveKbFixed = false;
					btnMove.setStyle(
							" -fx-background-color: black;" + "-fx-background-radius: 15em; " + "-fx-min-width: 10px; "
									+ "-fx-min-height: 10px; " + "-fx-max-width: 10px; " + "-fx-max-height: 10px;");

					double X = Math.abs(Math.min(Main.screenWidth - 800, nativeEvent.getPoint().getX()));
					double Y = Math.abs(Math.min(Main.screenHeigh - 275, nativeEvent.getPoint().getY()));
					primaryStage.setX(X);
					primaryStage.setY(Y);

				}

				// root.getChildren().remove(0);
				// root.getChildren().add(0, ivKeyboard);

			}
		});

	}

	@Override
	public void nativeMouseReleased(NativeMouseEvent nativeEvent) {
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
	public void nativeMouseDragged(NativeMouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void nativeMouseMoved(NativeMouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
