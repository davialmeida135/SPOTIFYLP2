package com.spotify.control;

import java.io.IOException;
import java.sql.Connection;

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
import com.spotify.data.*;
import com.spotify.model.Usuario;
import com.spotify.dao.UsuarioDAO;


public class LoginController {
	
	private Stage stage;
	private Scene scene;
	private Usuario user;

	@FXML
	private AnchorPane LoginAnchorPane;
	
	@FXML
    private TextField passwordTextField;

    @FXML
    private Label registerLabel;

    @FXML
    private Label myLabel;

    @FXML
    private Button logIn;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField usernameTextField;
	
	String usuario;
	String senha;
	
	int autenticador;
	
	public void submitLogin(ActionEvent event) throws Exception { //Ao ser apertado o botão login
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		user = new Usuario();
		try {
			usuario = usernameTextField.getText();
			senha = passwordTextField.getText();
		
			Connection conn = DataBase.connect("database.db");
			autenticador = UsuarioDAO.autenticar(usuario, senha, conn);

			//-1 = usuario nao encontrado
			//-2 = senha incorreta
			if(autenticador >= 0) {
				myLabel.setText("You are now signed up!");
				
				//Funcao de receber uma instancia de usuario baseado no id
				user = UsuarioDAO.getUsuario(autenticador, conn);
				conn.close();
				UserHolder holder = UserHolder.getInstance();
				holder.setUser(user);
				MenuView menu = new MenuView();
				menu.start(stage);
			}
			else if(autenticador ==-1){
				errorLabel.setText("Usuário não encontrado");	
			}
			else if(autenticador == -2) {
				errorLabel.setText("Senha incorreta");	
			}
			else {
				errorLabel.setText("Erro não identificado");	
			}
		}
		catch (NumberFormatException e){
			myLabel.setText("enter only numbers plz");
			
		}
		catch (Exception e) {
			myLabel.setText("error");
			System.out.println(e.toString());
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