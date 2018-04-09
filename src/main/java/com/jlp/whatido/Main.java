package com.jlp.whatido;

import java.awt.Dimension;
import java.awt.List;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Stream;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class Main extends Application {

	public static Properties hmWheel = new Properties();
	public static Properties props = new Properties();
	public static Properties propsTrad = new Properties();
	public static Properties propsCtrlMod = new Properties();
	public static Properties propsCtrlMod_INV = new Properties();

	public static String osCurrent = "Windows";
	public static String language = "FR_fr";
	public static String repCfg = "";

	public static double screenWidth = 1920;
	public static double screenHeigh = 1080;
	public static int TFWIDTH = 100;
	public static String keyboard = "AZERTY";
	public static String sizeMouse = "Medium";
	public static String mouseMobile = "Fixed";
	public static double xFixed = 10.0;
	public static double yFixed = 1050;
	public static double distFollowX = 10;
	public static double distFollowY = 10;
	public static String[] modifKeys;
	public static String[] shutdownKey = new String[3];
	public static String shutdownKey1;
	public static String shutdownKey2;
	public static String shutdownKey3;
	public static int latency = 100;
	public static String rootProject = ".";
	@SuppressWarnings("restriction")
	public static ObservableList<String> optionsModifKeys;
	public static boolean boolSounds = false;
	public static boolean boolKeyboard = false;
	public static String version = " WhatIDo 3.0.1 : Tracking keyboard and mouse events!!!";
	public static URL STYLECSS;
public static boolean boolMouseTransparent=true;
public static boolean boolKeyBoardTransparent=true;
	// All Controls to retrieve the static Field
	@SuppressWarnings("restriction")
	public final TextField taOs = new TextField();
	public final ComboBox<String> cbKb = new ComboBox<String>();
	public final ComboBox<String> cbLang = new ComboBox<String>();
	public final TextField tfSizeWidth = new TextField();
	public final TextField tfSizeHeigh = new TextField();
	public final ComboBox<String> cbSizeMouse = new ComboBox<String>();
	public final ComboBox<String> cbMouseMobile = new ComboBox<String>();
	public final TextField tfXFixed = new TextField();
	public final TextField tfYFixed = new TextField();
	public final TextField tfDistCursor = new TextField();
	public final TextField tfDelayFollow = new TextField();
	public final ComboBox<String> cbModifKeys1 = new ComboBox<String>();
	public final ComboBox<String> cbModifKeys2 = new ComboBox<String>();
	public final TextField tfShutdownKey3 = new TextField();
	public final TextField tfLatency = new TextField();
	public final CheckBox withSounds = new CheckBox(" With Sounds ?");
	public final CheckBox withImgKeyboard = new CheckBox(" With Keyboard image ?");
	public final CheckBox mouseTransparent=  new CheckBox(" With transparent mouse  ?");
	public final CheckBox keyBoardTransparent=  new CheckBox(" With transparent keyboard ?");
	public final ComboBox<String> cbSizeKb = new ComboBox<String>();
	//public final CheckBox withImgKeyboardSmall = new CheckBox("Small size Keyboard ?");
	public static double kbScale = 1;
	public static String sizeKb = "Normal";

	protected void majStatics() {
		// TODO Auto-generated method stub
		osCurrent = this.taOs.getText();
		keyboard = cbKb.getValue();
		sizeMouse = cbSizeMouse.getValue();
		mouseMobile = cbMouseMobile.getValue();
		xFixed = Double.valueOf(tfXFixed.getText());
		yFixed = Double.valueOf(tfYFixed.getText());
		distFollowX = Double.valueOf(tfDistCursor.getText());
		distFollowY = Double.valueOf(this.tfDelayFollow.getText());
		latency = Integer.valueOf(tfLatency.getText());
		shutdownKey[0] = cbModifKeys1.getValue();
		shutdownKey[1] = cbModifKeys2.getValue();
		shutdownKey[2] = tfShutdownKey3.getText();
		screenWidth = Double.valueOf(tfSizeWidth.getText());
		screenHeigh = Double.valueOf(tfSizeHeigh.getText());
		language = cbLang.getValue();
		if (this.withSounds.isSelected()) {
			boolSounds = true;
		} else {
			boolSounds = false;
		}
		
		if (this.withImgKeyboard.isSelected()) {
			boolKeyboard = true;
		} else {
			boolKeyboard = false;
		}
		if (this.mouseTransparent.isSelected()) {
		boolMouseTransparent=true;
		}
		else
		{
			boolMouseTransparent=false;
		}
		if (this.keyBoardTransparent.isSelected()) {
			boolKeyBoardTransparent=true;
			}
			else
			{
				boolKeyBoardTransparent=false;
			}
		
		props.setProperty("osCurrent", osCurrent);
		props.setProperty("keyboard", keyboard);
		props.setProperty("sizeMouse", sizeMouse);
		props.setProperty("mouseMobile", mouseMobile);
		props.setProperty("screenWidth", tfSizeWidth.getText());
		props.setProperty("screenHeigh", tfSizeHeigh.getText());
		props.setProperty("xFixed", Double.toString(xFixed));
		props.setProperty("yFixed", Double.toString(yFixed));
		props.setProperty("distFollowX", Double.toString(distFollowX));
		props.setProperty("distFollowY", Double.toString(distFollowY));
		props.setProperty("latency", Integer.toString(latency));
		props.setProperty("shutdownKey1", shutdownKey[0]);
		props.setProperty("shutdownKey2", shutdownKey[1]);
		props.setProperty("shutdownKey3", shutdownKey[2]);
		props.setProperty("boolSounds", Boolean.toString(boolSounds));
		props.setProperty("boolKeyboard", Boolean.toString(boolKeyboard));
		try (FileInputStream in = new FileInputStream(new File(rootProject + File.separator + "config" + File.separator
				+ "keyboards" + File.separator + language + "_Trad.properties"));) {

			propsTrad.load(in);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try (FileOutputStream fos = new FileOutputStream(
				new File(rootProject + File.separator + "config" + File.separator + "config.properties"));) {
			props.store(fos, "Modification configuration ");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (System.getProperty("os.name").toUpperCase().contains("WINDOW")) {
			try (FileInputStream in = new FileInputStream(
					new File(rootProject + File.separator + "config" + File.separator + "windowsWheel.properties"));) {

				hmWheel.load(in);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (System.getProperty("os.name").toUpperCase().contains("LINUX")) {
			try (InputStream in = new FileInputStream(
					new File(rootProject + File.separator + "config" + File.separator + "linuxWheel.properties"));) {

				hmWheel.load(in);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try (InputStream in = new FileInputStream(
					new File(rootProject + File.separator + "config" + File.separator + "windowsWheel.properties"));) {

				hmWheel.load(in);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void start(final Stage primaryStage) {
		primaryStage.setTitle(version + " ; keyboard=" + keyboard + " ; os=" + osCurrent);
		// primaryStage.initStyle(StageStyle.TRANSPARENT);
		repCfg = rootProject + File.separator + "config" + File.separator;
		AnchorPane root = new AnchorPane();
		Scene scene = new Scene(root, 800, 600);
		Button btnRun = new Button();
		btnRun.setText("Run");
		Button btnCancel = new Button();
		btnCancel.setText("Cancel");

		btnRun.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				// lancer un thread
				// Maj Variables statics
				majStatics();
				if (!withImgKeyboard.isSelected()) {
					Platform.runLater(new Runnable() {

						@Override
						public void run() {
							System.out.println("Lancement du thread Souris");
							primaryStage.hide();
							Stage stage=null;
							if (Main.boolMouseTransparent) {
								stage = new Stage(StageStyle.TRANSPARENT);
							}else
							{
								stage = new Stage(StageStyle.UNDECORATED);
							}
							
							new MyTracker(stage);
						}

					});
				} else {
					// lancement de 2 threads:
					// - un thread de tracking souris
					// - un thread de tracking clavier
					Platform.runLater(new Runnable() {

						@Override
						public void run() {
							System.out.println("Lancement du thread Souris");
							primaryStage.hide();
							Stage stage=null;
							if (Main.boolMouseTransparent) {
								stage = new Stage(StageStyle.TRANSPARENT);
							}else
							{
								stage = new Stage(StageStyle.UNDECORATED);
							}
							
							new MyTracker(stage);
						}

					});
					Platform.runLater(new Runnable() {

						@Override
						public void run() {
							System.out.println("Lancement du thread clavier");
							primaryStage.hide();
							Stage stage=null;
							if (Main.boolKeyBoardTransparent) {
								stage = new Stage(StageStyle.TRANSPARENT);
							}else
							{
								stage = new Stage(StageStyle.UNDECORATED);
							}
							new MyKbTracker(stage);
						}

					});
				}

			}
		});
		btnCancel.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				System.exit(0);
			}
		});

		Button btnMap = new Button();
		btnMap.setText("Adjust Mapping");
		btnMap.setTooltip(
				new Tooltip("For modifying few keys and mouse events\n Otherwise modify text file in config folder"));
		btnMap.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				System.out.println("Mapping Keyboard and Mouse");
				majStatics();
				Stage stage = new Stage();
				new AdjustMapping(stage, primaryStage);
			}
		});
		// Hbox OS et Clavier et lang
		HBox osKeyboradHbox = new HBox();
		Label lbOs = new Label("     System :");
		lbOs.setStyle("-fx-text-fill : rgb(255,0,0);-fx-font-size: 11pt;" + "-fx-font-family: Segoe UI Semibold;");

		taOs.setPrefWidth(TFWIDTH);
		/* recuperer le systeme et servir le tes area */
		osCurrent = System.getProperty("os.name");
		taOs.setText(osCurrent);
		taOs.setEditable(false);
		osKeyboradHbox.getChildren().add(lbOs);
		osKeyboradHbox.getChildren().add(taOs);

		Label lbKb = new Label("     Keyboard:");
		lbKb.setStyle("-fx-text-fill : rgb(255,0,0);-fx-font-size: 11pt;" + "-fx-font-family: Segoe UI Semibold;");
		ObservableList<String> options = getOptions("keyboards");

		cbKb.setItems(options);
		cbKb.getSelectionModel().select("AZERTY");
		propsTrad.clear();
		try (InputStream in = new FileInputStream(rootProject + File.separator + "config" + File.separator + "keyboards"
				+ File.separator + "FR_fr_Trad.properties");) {
			propsTrad.clear();
			propsTrad.load(in);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try (FileInputStream in = (FileInputStream) new FileInputStream(rootProject + File.separator + "config"
				+ File.separator + "keyboards" + File.separator + "AZERTY_CtrlMod.properties");) {
			propsCtrlMod.clear();
			propsCtrlMod.load(in);
			propsCtrlMod_INV.clear();
			Set<Object> set = propsCtrlMod.keySet();
			Iterator<Object> it = set.iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				propsCtrlMod_INV.put(propsTrad.get(key), key);
			}
			shutdownKey1 = propsTrad.getProperty("ALT", "ALT");
			shutdownKey2 = propsTrad.getProperty("CTRL_L", "CTRL_L");
			shutdownKey3 = "F";
			Set<Object> set2 = propsCtrlMod_INV.keySet();
			modifKeys = Arrays.copyOf(set2.toArray(), set2.toArray().length, String[].class);

			optionsModifKeys = (ObservableList<String>) FXCollections.observableArrayList(modifKeys);
			cbModifKeys1.setItems(null);
			cbModifKeys2.setItems(null);

			cbModifKeys1.setItems(optionsModifKeys);
			cbModifKeys2.setItems(optionsModifKeys);
			cbModifKeys1.setValue(shutdownKey1);
			cbModifKeys2.setValue(shutdownKey2);
			shutdownKey[0] = shutdownKey1;
			shutdownKey[1] = shutdownKey2;
			shutdownKey[2] = shutdownKey3;

		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Properties tmpProps = new Properties();

		cbKb.setOnAction((event) -> {
			keyboard = cbKb.getSelectionModel().getSelectedItem();
			System.out.println("Change keyBoard to :" + keyboard);
			primaryStage.setTitle(version + " ; keyboard=" + keyboard + " ; os=" + osCurrent + " ; lang=" + language);
			// modifKeys

		});

		ObservableList<String> optionsTrad = getOptions("trad");
		cbLang.setItems(optionsTrad);
		Label lbLang = new Label("     Lang :");
		lbOs.setStyle("-fx-text-fill : rgb(255,0,0);-fx-font-size: 11pt;" + "-fx-font-family: Segoe UI Semibold;");

		cbLang.getSelectionModel().select("FR_fr");
		cbLang.setOnAction((event) -> {
			language = cbLang.getSelectionModel().getSelectedItem();
			System.out.println("Change language to :" + language);
			primaryStage.setTitle(version + " ; keyboard=" + keyboard + " ; os=" + osCurrent + " ; lang=" + language);
			// modifKeys

			try (InputStream in = new FileInputStream(rootProject + File.separator + "config" + File.separator
					+ "keyboards" + File.separator + language + "_Trad.properties");) {
				propsTrad.clear();
				propsTrad.load(in);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try (InputStream in = new FileInputStream(rootProject + File.separator + "config" + File.separator
					+ "keyboards" + File.separator + keyboard + "_CtrlMod.properties");) {

				propsCtrlMod.clear();
				propsCtrlMod.load(in);
				propsCtrlMod_INV.clear();
				Set<Object> set = propsCtrlMod.keySet();
				Iterator<Object> it = set.iterator();
				while (it.hasNext()) {
					String key = (String) it.next();
					System.out.println("key =" + key);
					;
					propsCtrlMod_INV.put(propsTrad.get(key), key);
				}
				Set<Object> set2 = propsCtrlMod_INV.keySet();
				shutdownKey1 = propsTrad.getProperty("ALT", "ALT");
				shutdownKey2 = propsTrad.getProperty("CTRL_L", "CTRL_L");
				shutdownKey3 = "F";

				modifKeys = Arrays.copyOf(set2.toArray(), set2.toArray().length, String[].class);

				optionsModifKeys = (ObservableList<String>) FXCollections.observableArrayList(modifKeys);
				cbModifKeys1.setItems(null);
				cbModifKeys2.setItems(null);

				cbModifKeys1.setItems(optionsModifKeys);
				cbModifKeys2.setItems(optionsModifKeys);
				cbModifKeys1.setValue(shutdownKey1);
				cbModifKeys2.setValue(shutdownKey2);
				shutdownKey[0] = shutdownKey1;
				shutdownKey[1] = shutdownKey2;
				shutdownKey[2] = shutdownKey3;

			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		});
		osKeyboradHbox.setSpacing(30.0);
		osKeyboradHbox.getChildren().add(lbKb);
		osKeyboradHbox.getChildren().add(cbKb);
		osKeyboradHbox.getChildren().add(lbLang);
		osKeyboradHbox.getChildren().add(cbLang);

		// Fin Hbox OS et Clavier

		// taille ecran
		HBox hbSizeScreen = new HBox();
		Label lbSizeScreen = new Label(" Screen Size (pixels) W :  ");
		lbSizeScreen
				.setStyle("-fx-text-fill : rgb(255,0,0);-fx-font-size: 11pt;" + "-fx-font-family: Segoe UI Semibold;");
		Label lbMult = new Label("  x H : ");
		lbMult.setStyle("-fx-text-fill : rgb(255,0,0);-fx-font-size: 11pt;" + "-fx-font-family: Segoe UI Semibold;");

		tfSizeWidth.setPrefWidth(TFWIDTH);
		tfSizeHeigh.setPrefWidth(TFWIDTH);
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dimScreen = tk.getScreenSize();
		screenWidth = dimScreen.width;
		screenHeigh = dimScreen.height;
		tfSizeWidth.setText(((Double) dimScreen.getWidth()).toString());
		tfSizeHeigh.setText(((Double) dimScreen.getHeight()).toString());
		tfSizeWidth.setEditable(false);
		tfSizeHeigh.setEditable(false);
		hbSizeScreen.getChildren().add(lbSizeScreen);
		hbSizeScreen.getChildren().add(tfSizeWidth);
		hbSizeScreen.getChildren().add(lbMult);
		hbSizeScreen.getChildren().add(tfSizeHeigh);

		// fin taille ecran

		// Debut gestion souris

		HBox hbSizeMouse = new HBox();
		HBox hbFixedMouse = new HBox();
		HBox hbMobileMouse = new HBox();
		Label lbSizeMouse = new Label(" Size of the Mouse :  ");
		lbSizeMouse
				.setStyle("-fx-text-fill : rgb(255,0,0);-fx-font-size: 11pt;" + "-fx-font-family: Segoe UI Semibold;");
		ObservableList<String> optionsMouse = (ObservableList<String>) FXCollections.observableArrayList("Large",
				"Medium", "Small", "Tiny");

		cbSizeMouse.setItems(optionsMouse);
		cbSizeMouse.getSelectionModel().select("Medium");
		cbSizeMouse.setOnAction((event) -> {
			sizeMouse = cbSizeMouse.getSelectionModel().getSelectedItem();
			System.out.println("Change size Mouse to :" + sizeMouse);
			if (sizeMouse.equals("Large")) {
				mouseMobile = "Fixed";
				hbFixedMouse.setVisible(true);
				hbMobileMouse.setVisible(false);
				cbMouseMobile.getSelectionModel().select("Fixed");
			}
			if (sizeMouse.equals("Tiny")) {
				mouseMobile = "Mobile";
				hbFixedMouse.setVisible(false);
				hbMobileMouse.setVisible(true);
				cbMouseMobile.getSelectionModel().select("Mobile");
			}

		});
		hbSizeMouse.getChildren().add(lbSizeMouse);
		hbSizeMouse.getChildren().add(cbSizeMouse);
		Label lbMobilMouse = new Label("         Mobility :  ");
		lbMobilMouse
				.setStyle("-fx-text-fill : rgb(255,0,0);-fx-font-size: 11pt;" + "-fx-font-family: Segoe UI Semibold;");
		ObservableList<String> optionsMouseMobility = (ObservableList<String>) FXCollections
				.observableArrayList("Fixed", "Mobile");
		cbMouseMobile.setItems(optionsMouseMobility);
		cbMouseMobile.getSelectionModel().select("Fixed");
		cbMouseMobile.setOnAction((event) -> {
			mouseMobile = cbMouseMobile.getSelectionModel().getSelectedItem();
			System.out.println("Change mobility of Mouse to :" + mouseMobile);
			if (sizeMouse.equals("Large")) {

				//

				hbMobileMouse.setVisible(false);
				hbFixedMouse.setVisible(true);
				mouseMobile = "Fixed";

				new Thread() {
					@Override
					public void run() {
						javafx.application.Platform.runLater(new Runnable() {
							@Override
							public void run() {
								try {
									Thread.sleep(100);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								cbMouseMobile.getSelectionModel().select("Fixed");
							}
						});
					}
				}.start();

				return;
			}

			if (sizeMouse.equals("Tiny")) {

				//

				hbMobileMouse.setVisible(true);
				hbFixedMouse.setVisible(false);
				mouseMobile = "Mobile";

				new Thread() {
					@Override
					public void run() {
						javafx.application.Platform.runLater(new Runnable() {
							@Override
							public void run() {
								try {
									Thread.sleep(100);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								cbMouseMobile.getSelectionModel().select("Mobile");
							}
						});
					}
				}.start();

				return;
			}

			switch (mouseMobile) {
			case "Fixed":
				hbFixedMouse.setVisible(true);
				hbMobileMouse.setVisible(false);

				break;
			default:

				hbFixedMouse.setVisible(false);
				hbMobileMouse.setVisible(true);

				break;
			}
		});

		hbSizeMouse.getChildren().add(lbMobilMouse);
		hbSizeMouse.getChildren().add(cbMouseMobile);
		// transparency mouse
		HBox.setMargin(mouseTransparent, new Insets(0,0,0,20));
		mouseTransparent
		.setStyle("-fx-text-fill : rgb(255,0,0);-fx-font-size: 11pt;" + "-fx-font-family: Segoe UI Semibold;");
		mouseTransparent.setSelected(Main.boolMouseTransparent);
		mouseTransparent.setOnAction(e -> {
			if (mouseTransparent.isSelected()) {
				Main.boolMouseTransparent=true;
			}
			else
			{
				Main.boolMouseTransparent=false;
			}
			
		});
		hbSizeMouse.getChildren().add(mouseTransparent);
		hbSizeMouse.setVisible(true);
		hbSizeMouse.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;" + "-fx-border-width: 2;"
				+ "-fx-border-insets: 5;" + "-fx-border-radius: 5;" + "-fx-border-color: blue;");

		// Hbox Fixed => point bas gauche de la souris
		Label lbXFixed = new Label(" XFixed Mouse :  ");
		lbXFixed.setStyle("-fx-text-fill : rgb(255,0,0);-fx-font-size: 11pt;" + "-fx-font-family: Segoe UI Semibold;");
		Label lbYFixed = new Label("         YFixed Mouse :  ");
		lbYFixed.setStyle("-fx-text-fill : rgb(255,0,0);-fx-font-size: 11pt;" + "-fx-font-family: Segoe UI Semibold;");

		tfXFixed.setPrefWidth(TFWIDTH);
		tfYFixed.setPrefWidth(TFWIDTH);
		tfXFixed.setText("10");
		tfYFixed.setText(((Double) (new Double(this.tfSizeHeigh.getText()) - 10)).toString());
		hbFixedMouse.getChildren().add(lbXFixed);
		hbFixedMouse.getChildren().add(tfXFixed);
		hbFixedMouse.getChildren().add(lbYFixed);
		hbFixedMouse.getChildren().add(tfYFixed);
		hbFixedMouse.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;" + "-fx-border-width: 2;"
				+ "-fx-border-insets: 5;" + "-fx-border-radius: 5;" + "-fx-border-color: blue;");
		hbFixedMouse.setVisible(true);
		// Hbox Mobile => Distance from the cursor in px
		// Delay to follow the cursor in ms
		Label lbDistCursor = new Label("DistX. Cursor (px)  :  ");
		lbDistCursor
				.setStyle("-fx-text-fill : rgb(255,0,0);-fx-font-size: 11pt;" + "-fx-font-family: Segoe UI Semibold;");
		Label lbDelayFollow = new Label("         DistY. Cursor (px) :  ");
		lbDelayFollow
				.setStyle("-fx-text-fill : rgb(255,0,0);-fx-font-size: 11pt;" + "-fx-font-family: Segoe UI Semibold;");

		tfDistCursor.setPrefWidth(TFWIDTH);
		tfDelayFollow.setPrefWidth(TFWIDTH);
		tfDistCursor.setText("20");
		tfDelayFollow.setText("20");
		hbMobileMouse.getChildren().add(lbDistCursor);
		hbMobileMouse.getChildren().add(tfDistCursor);
		hbMobileMouse.getChildren().add(lbDelayFollow);
		hbMobileMouse.getChildren().add(tfDelayFollow);
		hbMobileMouse.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;" + "-fx-border-width: 2;"
				+ "-fx-border-insets: 5;" + "-fx-border-radius: 5;" + "-fx-border-color: blue;");
		hbMobileMouse.setVisible(false);

		// Combinaison de fin de programme
		VBox vbox = new VBox();
		Label lbShutdown = new Label(" Two Controls and a letter to stop spying ");
		lbShutdown
				.setStyle("-fx-text-fill : rgb(255,0,0);-fx-font-size: 11pt;" + "-fx-font-family: Segoe UI Semibold;");
		vbox.getChildren().add(lbShutdown);

		HBox hbControls = new HBox();

		cbModifKeys1.getSelectionModel().select(shutdownKey1);
		cbModifKeys1.setOnAction((event) -> {
			shutdownKey1 = cbModifKeys1.getSelectionModel().getSelectedItem();

		});
		cbModifKeys2.getSelectionModel().select(shutdownKey2);
		cbModifKeys2.setOnAction((event) -> {
			shutdownKey2 = cbModifKeys2.getSelectionModel().getSelectedItem();

		});
		cbModifKeys1.setValue(shutdownKey1);
		cbModifKeys2.setValue(shutdownKey2);
		cbModifKeys2.setPrefWidth(TFWIDTH + 10);
		cbModifKeys1.setPrefWidth(TFWIDTH + 10);

		tfShutdownKey3.setPrefWidth(60);
		tfShutdownKey3.setText("F");

		hbControls.getChildren().add(cbModifKeys1);
		hbControls.getChildren().add(cbModifKeys2);
		hbControls.getChildren().add(tfShutdownKey3);
		System.out.println("*".toLowerCase(Locale.FRANCE));

		hbControls.setStyle("-fx-padding: 10;-fx-spacing:20; -fx-alignment:center;");
		vbox.setStyle("-fx-padding: 10;-fx-alignment:center;");
		vbox.getChildren().add(hbControls);
		vbox.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;" + "-fx-border-width: 2;"
				+ "-fx-border-insets: 5;" + "-fx-border-radius: 5;" + "-fx-border-color: blue;");
		// Fin gestion souris

		// debut gestion latency in ms ( for click, wheel manipulation ...)
		HBox hbLatency = new HBox();
		Label lbLatency = new Label("Latency(for clicks, wheel motions ...) in ms : ");
		lbLatency.setStyle("-fx-text-fill : rgb(255,0,0);-fx-font-size: 11pt;" + "-fx-font-family: Segoe UI Semibold;");
		tfLatency.setPrefWidth(TFWIDTH);
		hbLatency.getChildren().add(lbLatency);
		hbLatency.getChildren().add(tfLatency);
		tfLatency.setText(Integer.toString(latency));
		hbLatency.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;" + "-fx-border-width: 2;"
				+ "-fx-border-insets: 5;" + "-fx-border-radius: 5;" + "-fx-border-color: blue;");
		// fin gestion latency

		// debut Gestion Sounds
		HBox hbSounds = new HBox();
		hbSounds.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;" + "-fx-border-width: 2;"
				+ "-fx-border-insets: 5;" + "-fx-border-radius: 5;" + "-fx-border-color: blue;");
		withSounds.setSelected(false);
		withSounds
				.setStyle("-fx-text-fill : rgb(255,0,0);-fx-font-size: 11pt;" + "-fx-font-family: Segoe UI Semibold;");
		withSounds.setPrefWidth(150);

		withImgKeyboard.setSelected(false);
		withImgKeyboard
				.setStyle("-fx-text-fill : rgb(255,0,0);-fx-font-size: 11pt;" + "-fx-font-family: Segoe UI Semibold;");
		withImgKeyboard.setPrefWidth(250);

		hbSounds.getChildren().add(withSounds);
		hbSounds.getChildren().add(withImgKeyboard);
		Label lbSizeKb = new Label(" Size of Keyboard :  ");

		lbSizeKb.setStyle("-fx-text-fill : rgb(255,0,0);-fx-font-size: 11pt;" + "-fx-font-family: Segoe UI Semibold;");
		ObservableList<String> optionsSizeKb = (ObservableList<String>) FXCollections.observableArrayList("Normal",
				"Medium", "Small");
		cbSizeKb.setItems(optionsSizeKb);
		cbSizeKb.getSelectionModel().select("Normal");
		cbSizeKb.setOnAction((event) -> {
			sizeKb = cbSizeKb.getSelectionModel().getSelectedItem();
			System.out.println("Change size of Keyboard :" + sizeKb);
			switch (sizeKb) {
			case "Normal":
				Main.kbScale=1.0;
				break;
			case "Medium" :
				Main.kbScale=0.75;
				break;
			case "Small" :
				Main.kbScale=0.5;

			}
		});

		

		hbSounds.getChildren().add(lbSizeKb);
		hbSounds.getChildren().add(cbSizeKb);
		
		// keyBoard mouse
				HBox.setMargin(keyBoardTransparent, new Insets(0,0,0,20));
				keyBoardTransparent
				.setStyle("-fx-text-fill : rgb(255,0,0);-fx-font-size: 11pt;" + "-fx-font-family: Segoe UI Semibold;");
				keyBoardTransparent.setSelected(Main.boolKeyBoardTransparent);
				keyBoardTransparent.setOnAction(e -> {
					if (keyBoardTransparent.isSelected()) {
						Main.boolKeyBoardTransparent=true;
					}
					else
					{
						Main.boolKeyBoardTransparent=false;
					}
					
				});
				hbSounds.getChildren().add(keyBoardTransparent);
		// Fin gestion Sounds

		root.getStylesheets().add(Main.STYLECSS.toExternalForm());

		scene.getStylesheets().add(Main.STYLECSS.toExternalForm());
		AnchorPane.setLeftAnchor(btnRun, 100.0);
		AnchorPane.setRightAnchor(btnCancel, 100.0);
		AnchorPane.setRightAnchor(btnMap, 250.0);

		AnchorPane.setBottomAnchor(btnRun, 10.0);
		AnchorPane.setBottomAnchor(btnCancel, 10.0);
		AnchorPane.setBottomAnchor(btnMap, 10.0);

		root.getChildren().add(btnRun);
		root.getChildren().add(btnMap);
		root.getChildren().add(btnCancel);

		AnchorPane.setTopAnchor(osKeyboradHbox, 10.0);
		root.getChildren().add(osKeyboradHbox);

		AnchorPane.setTopAnchor(hbSizeScreen, 50.0);
		AnchorPane.setTopAnchor(hbSizeMouse, 100.0);
		AnchorPane.setTopAnchor(hbFixedMouse, 150.0);
		AnchorPane.setTopAnchor(hbMobileMouse, 150.0);
		AnchorPane.setTopAnchor(vbox, 200.0);
		AnchorPane.setTopAnchor(hbLatency, 300.0);
		AnchorPane.setTopAnchor(hbSounds, 350.0);
		AnchorPane.setLeftAnchor(hbSounds, 5.0);

		root.getChildren().add(hbSizeScreen);
		root.getChildren().add(hbSizeMouse);
		root.getChildren().add(hbFixedMouse);
		root.getChildren().add(hbMobileMouse);
		root.getChildren().add(vbox);
		root.getChildren().add(hbLatency);
		root.getChildren().add(hbSounds);
		primaryStage.setScene(scene);
		scene.setFill(null);

		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
				// TODO Auto-generated method stub
				System.exit(0);
			}

		});
		
		primaryStage.setAlwaysOnTop(true);
		primaryStage.show();
		primaryStage.setMinWidth(hbSounds.getWidth()+40);
		primaryStage.setMinHeight(500);
	}

	private ObservableList<String> getOptions(String forwhat) {
		String repKeyboards = repCfg + File.separator + "keyboards" + File.separator;
		switch (forwhat) {

		case "keyboards":
			try {
				ArrayList<String> arr = new ArrayList<String>();
				Stream<Path> stream = Files.list(new File(repKeyboards).toPath()).filter((f) -> {
					if (f.toFile().getName().contains("Code"))
						return true;
					else
						return false;
				});

				stream.forEach(str -> {

					arr.add(str.toFile().getName().split("_Code")[0]);

				});
				String[] tabStr = (String[]) arr.stream().toArray(String[]::new);

				return FXCollections.observableArrayList(tabStr);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case "trad":
			try {
				ArrayList<String> arr = new ArrayList<String>();
				Stream<Path> stream = Files.list(new File(repKeyboards).toPath()).filter((f) -> {
					if (f.toFile().getName().contains("Trad"))
						return true;
					else
						return false;
				});

				stream.forEach(str -> {

					arr.add(str.toFile().getName().split("_Trad")[0]);

				});
				String[] tabStr = (String[]) arr.stream().toArray(String[]::new);

				return FXCollections.observableArrayList(tabStr);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;

		}

		return null;
	}

	public static void main(String[] args) {
		rootProject = System.getProperty("root", ".");
		System.out.println("root=" + rootProject);
		try {
			STYLECSS = new File(rootProject + File.separator + "config" + File.separator + "style.css").toURI().toURL();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		launch(args);
	}

}
