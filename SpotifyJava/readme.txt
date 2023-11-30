Database SQLITE:
	Temos 4 tabelas:
		Musicas: Guarda as músicas e seu path
		Playlists: Guarda o nome, a descrição, e o id do dono
		Musicas_e_playlists: Relaciona o id de cada musica com o id da playlist que ela esta
		Usuarios: Guarda o nome, o tipo, o usuario e a senha de cada cliente
		
	Usaremos essas tabelas para criar playlists, relacionar elas a usuarios e
	adicionar músicas a elas.
	
	Foram criadas alguns métodos estáticos que serão uteis no acesso as databases,
	ainda será discutido o tipo de retorno de cada função:
		métodos de inserção e remoção para as 4 tables.
	
		playlists_do_usuario(Connection conn, int proprietario_id): Busca todas as playlists
		cujo proprietário tem o id fornecido.
		
		musicas_da_playlist(Connection conn, int id_playlist): Busca todas as musicas de
		uma playlist com o id fornecido.
		
		busca_usuario(Connection conn, String username): Busca se há um usuário com o
		referido username dentro da database.
		
WHATS NEXT:

- Função que recebe id do usuário e retorna objeto Usuario com todas suas informações
- Implementar essa função no LoginController

- Criar playlist chamada "todas as músicas", onde toda música que for lida em um diretório será adicionada

- Carregar todas playlists e dados do usuário após login 

- Conseguir tocar músicas

- Remover músicas vai dar trabalho

- Criar tela de cadastro