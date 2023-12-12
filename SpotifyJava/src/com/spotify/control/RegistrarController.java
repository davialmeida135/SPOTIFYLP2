package com.spotify.control;

import java.io.IOException;
import java.sql.Connection;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import com.spotify.view.*;
import com.spotify.data.*;
import com.spotify.model.Usuario;
import com.spotify.dao.UsuarioDAO;


public class RegistrarController {
	
	private Stage stage;
	private Scene scene;
	private Usuario user;

	@FXML
    private PasswordField passwordTextField;

    @FXML
    private Label logInRedirect;

    @FXML
    private CheckBox vipSelection;

    @FXML
    private TextField nomeTextField;

    @FXML
    private Label myLabel;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField usernameTextField;

	
	String usuario;
	String senha;
	Connection conn = DataBase.connect("database.db");

	int autenticador;
	
	public void submitRegistro(ActionEvent event) throws Exception { //Ao ser apertado o botão login
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		user = new Usuario();
		try {
			
			String nome = nomeTextField.getText();
			String tipo = vipSelection.isSelected() ? "VIP" : "Comum";
			usuario = usernameTextField.getText();
			senha = passwordTextField.getText();

			
			// Verifica se o nome de usuário já existe
			int idUsuario = UsuarioDAO.getUsuarioId(usuario, conn);
			if (idUsuario >=0) {
				errorLabel.setText("Usuário já existe!");
				
				return;
			}
			
			if(nome.equals("")) {
			    errorLabel.setText("Insira seu nome!");
			    
			    return;
			}
			if(usuario.equals("")) {
			    errorLabel.setText("Escolha um usuário!");
			    
			    return;
			}
			if(senha.equals("")) {
			    errorLabel.setText("Escolha uma senha!");
			   
			    return;
			}
			// Insere o usuário no banco de dados
			UsuarioDAO.novoUsuario(nome, usuario, senha, tipo, conn);
			errorLabel.setText(" ");

			// Mostra uma mensagem de sucesso
			myLabel.setText("Você está cadastrado!");
		} catch (Exception e) {
			// Mostra uma mensagem de erro
			errorLabel.setText("Erro ao cadastrar usuário");
		}
	}

	
	public void logInRedirect() {
		stage = (Stage) myLabel.getScene().getWindow();
		LoginView login = new LoginView();
		try {
			conn.close();
			login.start(stage);
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		
	}

	public Usuario getUser() {
		return user;
	}

	public void setUser(Usuario user) {
		this.user = user;
	}
}