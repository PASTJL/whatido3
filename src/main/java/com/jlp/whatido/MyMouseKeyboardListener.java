package com.jlp.whatido;

import javafx.application.Application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.logging.Logger;
import java.util.logging.Handler;
import java.util.logging.Level;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;

public class MyMouseKeyboardListener extends Application  implements NativeKeyListener,NativeMouseInputListener {

	
	 public void nativeKeyTyped(NativeKeyEvent e) {
	        System.out.println("Key Typed: " +  NativeKeyEvent.getKeyText(e.getKeyCode()));
	        
	    }
	public void nativeKeyPressed(NativeKeyEvent e) {
		System.out.println("Key Pressed NativeKeyEvent.getKeyText : " + NativeKeyEvent.getKeyText(e.getKeyCode()));
		System.out.println("Key Pressed getKeyCode : " + e.getKeyCode() + " ;modifier="+ e.getModifiers());
		
		/*	System.out.println("Key Pressed GETkEYcHAR: " + e.getKeyChar());
		System.out.println("Key Pressed getKeyCode: " + e.getKeyCode() + "modifier=" + e.getModifiers());*/
//        if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
//            try {
//				GlobalScreen.unregisterNativeHook();
//				System.exit(0);
//			} catch (NativeHookException e1) {
//				// TODO ((Auto-generated catch block
//				e1.printStackTrace();
//				System.exit(1);
//			}
//        }
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
//    	System.out.println("##########################################################");
//        System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()) + " ; rawCode =" + e.getRawCode());
//       
//        System.out.println("Key Realeased getKeyCode: " +e.getKeyCode() + "   ;modifier="+ e.getModifiers());
//        System.out.println("##########################################################");
//        System.out.println();
    }

   
	
	@Override
	public void start(Stage primaryStage) {
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
	        }
	        catch (NativeHookException ex) {
	            System.err.println("There was a problem registering the native hook.");
	            System.err.println(ex.getMessage());

	            System.exit(1);
	        }

	        
		  	Button btn = new Button();
	        btn.setText("Say 'Hello World'");
	        btn.setOnAction(new EventHandler<ActionEvent>() {
	 
	            public void handle(ActionEvent event) {
	                System.out.println("Hello World!");
	            }
	        });
	        
	        StackPane root = new StackPane();
	        root.getChildren().add(btn);

	 Scene scene = new Scene(root, 300, 250);
	

	        primaryStage.setTitle("Hello World!");
	        primaryStage.setScene(scene);
	       
	        primaryStage.show();	
	        primaryStage.setAlwaysOnTop(true);
	        
	        GlobalScreen.addNativeKeyListener(this);
	        GlobalScreen.addNativeMouseListener(this);
	}

	public static void main(String[] args) {
		
		
		launch(args);
	}
	@Override
	public void nativeMouseClicked(NativeMouseEvent arg0) {
		
		
	}
	@Override
	public void nativeMousePressed(NativeMouseEvent arg0) {
		System.out.println("Button ="+arg0.getButton());
		
	}
	@Override
	public void nativeMouseReleased(NativeMouseEvent arg0) {
		// TODO Auto-generated method stub
		
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
