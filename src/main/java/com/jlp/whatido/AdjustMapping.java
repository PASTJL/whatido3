package com.jlp.whatido;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Properties;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AdjustMapping implements NativeKeyListener, NativeMouseInputListener {
	Stage primaryStage;
	Scene scene;
	AnchorPane root;
	static public Properties propsKeyboard = new Properties();
	static public Properties propsTrad = new Properties();
	static public Properties propsCtrlMod = new Properties();
	static public Properties propsWheel = new Properties();
	static String commentPropsKeyBoardFr = "Fichier de mapping du clavier respectant la structure suivante \n"
			+ "<codeTouche_Modifiers> = <PivotidentifiantdeTouche>\n" + " exemple : 2_0 = AMPER";

	static String commentPropsKeyBoardEn = "File for mapping the keyboard according to the following rule \n"
			+ "<keyCode_Modifiers> = <PivotkeyIdentifiant>\n" + " example : 2_0 = AMPER";

	static String commentPropsTradFr = "Fichier de mapping de traduction dans la langue  de certain caractères \n"
			+ "<PivotidentifiantdeTouche> = <Traduction Ou unicode>\n" + " exemple : AMPER = \\u0026";

	static String commentPropsTradEn = "File for mapping of Translation for several characters \n"
			+ "<PivotkeyIdentifiant> = <Translation or Unicode>\n" + " example : AMPER = \\u0026";

	static String commentPropsWheelFr = "Fichier de mapping des boutons de la souris \n"
			+ "L = Left ( Gauche), R = Right (Droit) , M = Middle (Milieu)\n "+
			"<1 ou 2 ou 3> = <L ou M ou R>\n" + " exemple : 1 = L";

	static String commentPropsWheelEn = "File for mapping of Mouse buttons \n"
			+ "L = Left , R = Right  , M = Middle \n "+
			"<1 ou 2 ou 3> = <L ou M ou R>\n" + " example : 1 = L";
	
	static public TextField tfIn = new TextField();
	static public TextField tfNativeCode = new TextField();
	static public TextField tfMappedWith = new TextField();
	static public TextField tfTranslate = new TextField();

	static public TextField tfMouseLeft = new TextField();
	static public TextField tfMouseMiddle = new TextField();
	static public TextField tfMouseRight = new TextField();

	private static final int LIMIT = 6;
	public Button btnModify = new Button("Modify?");
	public Button btnModify2 = new Button("Modify?");

	public Button btnApply = new Button("Apply");

	public Button btnCancel = new Button("Cancel");
	public Button btnReinit = new Button("Re-Init");

	static public Label lbCode = new Label("Native Code");

	int TFWHIDTH = 100;

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public AdjustMapping(Stage primaryStage, Stage parent) {
		super();
		this.primaryStage = primaryStage;
		if (Main.osCurrent.toUpperCase().contains("WINDOW")) {
			try (InputStream in = new FileInputStream(
					Main.rootProject + File.separator + "config" + File.separator + "windowsWheel.properties");) {
				propsWheel.load(in);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try (InputStream in = new FileInputStream(
					Main.rootProject + File.separator + "config" + File.separator + "linuxWheel.properties");) {
				propsWheel.load(in);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		try (InputStream in = new FileInputStream(Main.rootProject + File.separator + "config" + File.separator
				+ "keyboards" + File.separator + Main.keyboard + "_Code.properties");) {
			propsKeyboard.load(in);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try (InputStream in = new FileInputStream(Main.rootProject + File.separator + "config" + File.separator
				+ "keyboards" + File.separator + Main.language + "_Trad.properties");) {
			propsTrad.load(in);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		tfIn.lengthProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if (newValue.intValue() > oldValue.intValue()) {
					// Check if the new character is greater than LIMIT
					if (tfIn.getText().length() >= LIMIT) {

						// if it's 11th character then just setText to previous
						// one
						tfIn.setText(tfIn.getText().substring(0, LIMIT));
					}
				}
			}
		});

		tfMouseLeft.lengthProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if (newValue.intValue() > oldValue.intValue()) {
					// Check if the new character is greater than LIMIT
					if (tfMouseLeft.getText().length() >= LIMIT) {

						// if it's 11th character then just setText to previous
						// one
						tfMouseLeft.setText(tfMouseLeft.getText().substring(0, LIMIT));
					}
				}
			}
		});

		tfMouseMiddle.lengthProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if (newValue.intValue() > oldValue.intValue()) {
					// Check if the new character is greater than LIMIT
					if (tfMouseMiddle.getText().length() >= LIMIT) {

						// if it's 11th character then just setText to previous
						// one
						tfMouseMiddle.setText(tfMouseMiddle.getText().substring(0, LIMIT));
					}
				}
			}
		});

		tfMouseRight.lengthProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if (newValue.intValue() > oldValue.intValue()) {
					// Check if the new character is greater than LIMIT
					if (tfMouseRight.getText().length() >= LIMIT) {

						// if it's 11th character then just setText to previous
						// one
						tfMouseRight.setText(tfMouseRight.getText().substring(0, LIMIT));
					}
				}
			}
		});
		primaryStage.initOwner(parent);
		primaryStage.initModality(Modality.APPLICATION_MODAL);
		primaryStage.setTitle("Adjusting the mapping of keyboard and mouse");
		GridPane grPane = new GridPane();
		GridPane grPane2 = new GridPane();
		grPane.setPrefSize(800, 30);
		grPane2.setPrefSize(800, 30);
		grPane.setHgap(80.0);
		grPane.setVgap(20.0);
		grPane2.setHgap(80.0);
		grPane2.setVgap(20.0);
		grPane2.setPadding(new Insets(0, 10, 10, 10));
		grPane.setPadding(new Insets(0, 10, 10, 10));

		grPane.add(new Label("Type Here"), 0, 0);
		grPane.add(lbCode, 1, 0);
		grPane.add(new Label("Mapped with"), 2, 0);
		grPane.add(new Label("Translation"), 3, 0);
		grPane.add(new Label("Modify ?"), 4, 0);

		grPane2.add(new Label("Left Clic"), 0, 0);
		grPane2.add(new Label("Middle Clic"), 1, 0);
		grPane2.add(new Label("Right Clic"), 2, 0);
		grPane2.add(new Label("Modify ?"), 3, 0);

		ContextMenu ctx = new ContextMenu();
		ctx.setHeight(0.0);
		ctx.setWidth(0.0);

		tfIn.setMinWidth(TFWHIDTH);
		tfNativeCode.setMinWidth(TFWHIDTH);
		AdjustMapping.tfMappedWith.setMinWidth(TFWHIDTH);
		tfTranslate.setMinWidth(TFWHIDTH);
		this.btnModify.setMinWidth(TFWHIDTH);

		tfMouseLeft.setMinWidth(TFWHIDTH);
		tfMouseMiddle.setMinWidth(TFWHIDTH);
		tfMouseRight.setMinWidth(TFWHIDTH);
		btnModify2.setMinWidth(TFWHIDTH);

		// menu contextuel de taille nulle pour le cacher sur tous les champs
		tfIn.setContextMenu(ctx);
		tfNativeCode.setContextMenu(ctx);
		tfMappedWith.setContextMenu(ctx);
		tfTranslate.setContextMenu(ctx);
		tfMouseLeft.setContextMenu(ctx);
		tfMouseMiddle.setContextMenu(ctx);
		tfMouseRight.setContextMenu(ctx);

		// btnModify => keyboard local properties => propsKeyboard et propsTrad
		// . ModifyHandler
		btnModify.setOnAction(new ModifyHandler());

		// btnModify2 => Mouse local properties propsWheel ModifyHandler2
		btnModify2.setOnAction(new ModifyHandler2());

		this.btnReinit.setOnAction(new ReInitHandler());
		this.btnApply.setOnAction(new ApplyHandler());

		btnApply.setMinWidth(TFWHIDTH);

		btnCancel.setMinWidth(TFWHIDTH);
		btnReinit.setMinWidth(TFWHIDTH);

		grPane.add(tfIn, 0, 1);
		grPane.add(tfNativeCode, 1, 1);
		grPane.add(tfMappedWith, 2, 1);
		grPane.add(tfTranslate, 3, 1);
		grPane.add(btnModify, 4, 1);

		grPane2.add(tfMouseLeft, 0, 1);
		grPane2.add(tfMouseMiddle, 1, 1);
		grPane2.add(tfMouseRight, 2, 1);
		grPane2.add(btnModify2, 3, 1);

		Tooltip tooltip = new Tooltip("Modify only in memory");

		btnModify.setTooltip(tooltip);
		btnModify2.setTooltip(tooltip);
		HBox hbuts = new HBox();
		hbuts.setSpacing(150);
		hbuts.getChildren().add(btnApply);

		hbuts.getChildren().add(btnReinit);
		hbuts.getChildren().add(btnCancel);

		btnCancel.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				primaryStage.close();
			}
		});

		root = new AnchorPane();
		root.getStylesheets().add(Main.STYLECSS.toExternalForm());
		grPane.setId("grPane");
		grPane2.setId("grPane2");
		grPane.getStylesheets().add(Main.STYLECSS.toExternalForm());
		grPane2.getStylesheets().add(Main.STYLECSS.toExternalForm());
		Label lbKbMap = new Label("Mapping keyboard");
		lbKbMap.setStyle("-fx-text-fill : rgb(255,0,0);-fx-font-size: 20pt;" + "-fx-font-family: Segoe UI Semibold;");

		Label lbMouseMap = new Label("Mapping Buttons Mouse");
		lbMouseMap
				.setStyle("-fx-text-fill : rgb(255,0,0);-fx-font-size: 20pt;" + "-fx-font-family: Segoe UI Semibold;");

		scene = new Scene(root, 900, 450);
		AnchorPane.setLeftAnchor(lbKbMap, 300.0);
		AnchorPane.setTopAnchor(lbKbMap, 10.0);
		AnchorPane.setTopAnchor(grPane, 60.0);
		AnchorPane.setLeftAnchor(grPane, 10.0);
		AnchorPane.setTopAnchor(lbMouseMap, 230.0);
		AnchorPane.setLeftAnchor(lbMouseMap, 250.0);
		AnchorPane.setTopAnchor(grPane2, 280.0);
		AnchorPane.setLeftAnchor(grPane2, 10.0);
		AnchorPane.setTopAnchor(hbuts, 390.0);
		AnchorPane.setLeftAnchor(hbuts, 80.0);

		root.getChildren().add(lbKbMap);
		root.getChildren().add(grPane);
		root.getChildren().add(lbMouseMap);
		root.getChildren().add(grPane2);
		root.getChildren().add(hbuts);

		primaryStage.setScene(scene);

		primaryStage.show();
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
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				if (tfIn.isFocused()) {
					tfIn.clear();
					btnModify.setDisable(false);
					System.out.println("Keycode In =" + arg0.getKeyCode());
					tfNativeCode.setEditable(true);
					tfNativeCode.clear();
					String key = Integer.toString(arg0.getKeyCode()) + '_' + Integer.toString(arg0.getModifiers());
					tfNativeCode.setText(key);

					tfMappedWith.setEditable(true);
					tfMappedWith.clear();
					// TODOJLP re
					String pivotValue = propsKeyboard.getProperty(key, "");
					String value = propsTrad.getProperty(propsKeyboard.getProperty(key, ""),
							propsKeyboard.getProperty(key, ""));
					tfMappedWith.setText(pivotValue);
					System.out.println("key =" + key + " ; pivotValue=" + pivotValue);
					System.out.println("key =" + key + " ; translate value=" + value);
					tfTranslate.clear();
					tfTranslate.setText(value);

					tfMappedWith.setEditable(true);
					tfNativeCode.setEditable(false);
				}
			}
		});
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent arg0) {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				tfIn.positionCaret(0);
				// if (tfIn.isFocused()) {
				//
				// if (null == tfMappedWith.getText() ||
				// tfMappedWith.getText().equals("")
				// ||
				// tfIn.getText().toUpperCase().equals(tfMappedWith.getText().toUpperCase()))
				// {
				// System.out.println("Disabling modift");
				// btnModify.setDisable(true);
				// }
				// }
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
	public void nativeMousePressed(NativeMouseEvent arg0) {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				if (tfMouseLeft.isFocused() && tfMouseLeft.isHover()) {
					tfMouseLeft.setText(Integer.toString(arg0.getButton()));
				} else if (tfMouseMiddle.isFocused() && tfMouseMiddle.isHover()) {
					tfMouseMiddle.setText(Integer.toString(arg0.getButton()));

				} else if (tfMouseRight.isFocused() && tfMouseRight.isHover()) {
					tfMouseRight.setText(Integer.toString(arg0.getButton()));
				}

			}

		});
	}

	@Override
	public void nativeMouseReleased(NativeMouseEvent arg0) {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				if (tfMouseLeft.isFocused() || tfMouseMiddle.isFocused() || tfMouseRight.isFocused()) {
					tfMouseLeft.positionCaret(0);
					tfMouseMiddle.positionCaret(0);
					tfMouseRight.positionCaret(0);
				}
			}

		});

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

class ModifyHandler implements EventHandler<ActionEvent> {

	// btnModify => keyboard local properties => propsKeyboard et propsTrad .
	// ModifyHandler

	String inStr;
	String code;
	String mapped;
	String translate;

	@Override
	public void handle(ActionEvent event) {
		// Rechercher la valeur de mappedWith dans tmpPropsKeyboard
		// cas des carateres imprimables
		// On va realisr une permutation
		System.out.println("Handling btnModify");
		inStr = AdjustMapping.tfIn.getText();
		code = AdjustMapping.tfNativeCode.getText();
		translate = AdjustMapping.tfTranslate.getText();
		AdjustMapping.propsKeyboard.setProperty(code, mapped);
		// useless to translate single printable letter and number
		if ("abcdefghijklmnopqrstuvwxyz1234567890".indexOf(inStr.toLowerCase()) < 0) {
			AdjustMapping.propsTrad.setProperty(mapped, translate);
		}

	}

}

class ModifyHandler2 implements EventHandler<ActionEvent> {
	String left, middle,right;
	
	// btnModify2 => Mouse local properties propsWheel ModifyHandler2
	@Override
	public void handle(ActionEvent event) {
	 AdjustMapping.propsWheel.setProperty(AdjustMapping.tfMouseLeft.getText(), "L");
	 AdjustMapping.propsWheel.setProperty(AdjustMapping.tfMouseMiddle.getText(), "M");
	 AdjustMapping.propsWheel.setProperty(AdjustMapping.tfMouseRight.getText(), "R");
	}

}

class ReInitHandler implements EventHandler<ActionEvent> {
	// static public Properties propsKeyboard = new Properties();
	// static public Properties propsKeyboardINV = new Properties();
	// static public Properties propsCtrlMod = new Properties();
	// static public Properties propsCtrlModINV = new Properties();
	// static public Properties propsWheel = new Properties();
	// static public Properties tmpPropsWheel = new Properties();
	//
	// static public Properties tmpPropsKeyboard = new Properties();
	// static public Properties tmpPropsKeyboardINV = new Properties();
	// static public Properties tmpPropsCtrlMod = new Properties();
	// static public Properties tmpPropsCtrlModINV = new Properties();
	String inStr;
	String code;
	String mapped;

	@Override
	public void handle(ActionEvent event) {
		String keyboard = Main.keyboard;
		String osCurrent = Main.osCurrent;
		String repBaseSource = Main.rootProject + File.separator + "config.backup" + File.separator + "keyboards"
				+ File.separator;
		String repBaseTarget = Main.rootProject + File.separator + "config" + File.separator + "keyboards"
				+ File.separator;
		Path pathSource = new File(repBaseSource + keyboard + ".properties").toPath();
		Path pathTarget = new File(repBaseTarget + keyboard + ".properties").toPath();
		try {
			Files.copy(pathSource, pathTarget, StandardCopyOption.REPLACE_EXISTING);
			pathSource = new File(repBaseSource + keyboard + "_Code.properties").toPath();
			pathTarget = new File(repBaseTarget + keyboard + "_Code.properties").toPath();
			Files.copy(pathSource, pathTarget, StandardCopyOption.REPLACE_EXISTING);
			pathSource = new File(repBaseSource + keyboard + "_CtrlMod.properties").toPath();
			pathTarget = new File(repBaseTarget + keyboard + "_CtrlMod.properties").toPath();
			Files.copy(pathSource, pathTarget, StandardCopyOption.REPLACE_EXISTING);
			pathSource = new File(repBaseSource + Main.language + "_Trad.properties").toPath();
			pathTarget = new File(repBaseTarget + "CtrlMod" + keyboard + "_INV.properties").toPath();
			Files.copy(pathSource, pathTarget, StandardCopyOption.REPLACE_EXISTING);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		repBaseSource = Main.rootProject + File.separator + "config.backup" + File.separator;
		repBaseTarget = Main.rootProject + File.separator + "config" + File.separator;
		if (osCurrent.toUpperCase().contains("WINDOW")) {
			pathSource = new File(repBaseSource + "windowsWheel.properties").toPath();
			pathTarget = new File(repBaseTarget + "windowsWheel.properties").toPath();
		} else {
			pathSource = new File(repBaseSource + "linuxWheel.properties").toPath();
			pathTarget = new File(repBaseTarget + "linuxWheel.properties").toPath();
		}

		try {
			Files.copy(pathSource, pathTarget, StandardCopyOption.REPLACE_EXISTING);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

class ApplyHandler implements EventHandler<ActionEvent> {
	// static public Properties propsKeyboard = new Properties();
	// static public Properties propsKeyboardINV = new Properties();
	// static public Properties propsCtrlMod = new Properties();
	// static public Properties propsCtrlModINV = new Properties();
	// static public Properties propsWheel = new Properties();
	// static public Properties tmpPropsWheel = new Properties();
	//
	// static public Properties tmpPropsKeyboard = new Properties();
	// static public Properties tmpPropsKeyboardINV = new Properties();
	// static public Properties tmpPropsCtrlMod = new Properties();
	// static public Properties tmpPropsCtrlModINV = new Properties();

	@Override
	public void handle(ActionEvent event) {
		String keyboard = Main.keyboard;
		String osCurrent = Main.osCurrent;
		String repConfig= Main.rootProject + File.separator + "config" + File.separator;
		String repBaseTarget = Main.rootProject + File.separator + "config" + File.separator + "keyboards"
				+ File.separator;
		try (FileOutputStream out = new FileOutputStream(
				new File(repBaseTarget + keyboard + Main.keyboard + "_Code.properties"));) {
			if (Main.language.equals("Fr_fr")) {
			AdjustMapping.propsKeyboard.store(out, AdjustMapping.commentPropsKeyBoardFr);
			
			}else
			{
				AdjustMapping.propsKeyboard.store(out, AdjustMapping.commentPropsKeyBoardEn);
				
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FileOutputStream out1 ;
		try {
		if (Main.osCurrent.toUpperCase().contains("WINDOW")){
			
				out1 = new FileOutputStream(
						new File(repConfig +"windowsWheel.properties"));
				if (Main.language.equals("Fr_fr")) {
					AdjustMapping.propsWheel.store(out1, AdjustMapping.commentPropsWheelFr);
					
					}else
					{
						AdjustMapping.propsWheel.store(out1, AdjustMapping.commentPropsWheelEn);
						
					}
			
		}else
		{
			out1 = new FileOutputStream(
					new File(repConfig +"windowsWheel.properties"));
		}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try (FileOutputStream out = new FileOutputStream(
				new File(repBaseTarget +  Main.language + "_Trad.properties"));) {
			if (Main.language.equals("Fr_fr")) {
			AdjustMapping.propsTrad.store(out, AdjustMapping.commentPropsTradFr);
			
			}else
			{
				AdjustMapping.propsTrad.store(out, AdjustMapping.commentPropsTradEn);
				
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}