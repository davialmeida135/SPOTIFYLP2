package com.spotify.control;



import java.sql.Connection;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;

import javafx.scene.Node;


import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javafx.stage.Stage;
import com.spotify.view.*;
import com.spotify.data.*;
import com.spotify.model.Usuario;
import com.spotify.dao.UsuarioDAO;


public class RegistrarController {
	
	private Stage stage;

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
	/**

	Este método é chamado quando o usuário clica no botão Registrar. Ele recupera as informações do usuário dos campos do formulário e tenta registrar um novo usuário no banco de dados.

	@param event O evento associado ao clique do botão.

	@throws Exception Em caso de qualquer erro durante o registro.
	*/
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

	/**

	Este método é chamado quando o usuário clica no link "Log In". 
	Ele fecha a cena atual e exibe a cena de login.
	*/
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
	/**
	 * Retorna o objeto usuário atualmente associado ao controller.
	 *
	 * @return Objeto usuário.
	 */
	public Usuario getUser() {
		return user;
	}
	
	/**
	 * Define o objeto usuário associado ao controller.
	 *
	 * @param user Objeto usuário.
	 */
	public void setUser(Usuario user) {
		this.user = user;
	}
}