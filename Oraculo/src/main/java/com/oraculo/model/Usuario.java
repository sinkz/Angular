package com.oraculo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table
@NamedQueries({ @NamedQuery(name = "Usuario.listar", query = "SELECT usuario FROM Usuario usuario"),
		@NamedQuery(name = "Usuario.buscarCodigo", query = "SELECT usuario FROM Usuario usuario WHERE usuario.codigo = :codigo"),
		@NamedQuery(name = "Usuario.Autenticar", query = "SELECT usuario FROM Usuario usuario WHERE usuario.login= :login AND usuario.senha = :senha") })
public class Usuario {

	@Id
	@GeneratedValue
	private Integer codigo;
	@Column(nullable = false, length = 200)
	private String nome;
	@Column(nullable = false, length = 40)
	private String login;
	@Column(nullable = false, length = 30)
	private String senha;
	@Column(nullable = false, length = 30)
	private String permissao;

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getPermissao() {
		return permissao;
	}

	public void setPermissao(String permissao) {
		this.permissao = permissao;
	}

	@Override
	public String toString() {
		return "Usuario [codigo=" + codigo + ", nome=" + nome + ", login=" + login + ", senha=" + senha + ", permissao="
				+ permissao + "]";
	}

}
