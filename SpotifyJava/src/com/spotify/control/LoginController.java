package com.spotify.control;

import java.io.IOException;


import javafx.event.ActionEvent;

import javafx.fxml.FXML;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import com.spotify.view.*;
import com.spotify.model.Usuario;


public class LoginController {
	
	private Stage stage;
	private Scene scene;
	private Usuario user;

	@FXML
	private AnchorPane LoginAnchorPane;
	
	@FXML
	private Label myLabel;
	@FXML
	private TextField passwordTextField;
	@FXML
	private Button myButton;
	@FXML
	private TextField usernameTextField;
	
	int age;
	
	public void submit(ActionEvent event) throws Exception { //Ao ser apertado o botão login
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		user = new Usuario();
		try {
			age = Integer.parseInt(passwordTextField.getText());
			
			if(age >= 18) {
				myLabel.setText("You are now signed up!");
				System.out.println("fase1");
				
				user.setId(2);
				user.setNome("Gustavo");
				System.out.println("fase2");
				UserHolder holder = UserHolder.getInstance();
				holder.setUser(user);
				System.out.println("fase3");
				MenuView teste = new MenuView();
				teste.start(stage);
			}
			else {
				myLabel.setText("You must be 18+");
				
			}
		}
		catch (NumberFormatException e){
			myLabel.setText("enter only numbers plz");
			
		}
		catch (Exception e) {
			myLabel.setText("error");
		}
	}
	
	public void registerRedirect() {
		
		System.out.println("oii");
		
		
	}

	public Usuario getUser() {
		return user;
	}

	public void setUser(Usuario user) {
		this.user = user;
	}
}