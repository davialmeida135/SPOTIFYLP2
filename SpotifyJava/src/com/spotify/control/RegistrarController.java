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
	
	public void submitRegistro(ActionEvent event) throws Exception { //Ao ser apertado o botão login
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		user = new Usuario();
		try {
			nome = nomeTextField.getText();
			tipo = isVIP.isSelected() ? "VIP" : "COMUM";
			usuario = usernameTextField1.getText();
			senha = passwordTextField.getText();

			Connection conn = DataBase.connect("database.db");

			// Verifica se o nome de usuário já existe
			int idUsuario = UsuarioDAO.getUsuarioId(usuario, conn);
			if (idUsuario != -1) {
				errorLabel.setText("Usuário já existe!");
				return;
			}

			// Insere o usuário no banco de dados
			UsuarioDAO.inserirUsuario(nome, tipo, usuario, senha, conn);
			conn.close();

			// Mostra uma mensagem de sucesso
			myLabel.setText("Você está cadastrado!");
		} catch (Exception e) {
			// Mostra uma mensagem de erro
			errorLabel.setText("Erro ao cadastrar usuário");
		}
	}

	
	public void registerRedirect() {
		stage = (Stage) registerLabel.getScene().getWindow();
		System.out.println("oii");
		
		
	}

	public Usuario getUser() {
		return user;
	}

	public void setUser(Usuario user) {
		this.user = user;
	}
}