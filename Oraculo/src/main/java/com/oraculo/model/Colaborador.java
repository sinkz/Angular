package com.oraculo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table
@NamedQueries({ @NamedQuery(name = "Colaborador.listar", query = "SELECT colaborador FROM Colaborador colaborador"),
		@NamedQuery(name = "Colaborador.listarComFiltro", query = "SELECT colaborador FROM Colaborador colaborador WHERE colaborador.nome like :nome OR colaborador.setor like :setor"),
		@NamedQuery(name = "Colaborador.buscarCodigo", query = "SELECT colaborador FROM Colaborador colaborador WHERE colaborador.codigo = :codigo") })
public class Colaborador {
	@Id
	@GeneratedValue
	private Integer codigo;

	@Column(nullable = false, length = 120)
	private String nome;
	@Column(nullable = false, length = 50)
	private String userSkype;
	@Column(nullable = false, length = 30)
	private String senhaSype;
	@Column(nullable = false, length = 60)
	private String userEmail;
	@Column(nullable = false, length = 30)
	private String senhaEmail;
	@Column(nullable = false, length = 30)
	private String setor;
	@Column(nullable = true, length = 200)
	private String arquivo;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "usuario_id", referencedColumnName = "codigo", nullable = false)
	private Usuario usuario;

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

	public String getUserSkype() {
		return userSkype;
	}

	public void setUserSkype(String userSkype) {
		this.userSkype = userSkype;
	}

	public String getSenhaSype() {
		return senhaSype;
	}

	public void setSenhaSype(String senhaSype) {
		this.senhaSype = senhaSype;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getSenhaEmail() {
		return senhaEmail;
	}

	public void setSenhaEmail(String senhaEmail) {
		this.senhaEmail = senhaEmail;
	}

	public String getSetor() {
		return setor;
	}

	public void setSetor(String setor) {
		this.setor = setor;
	}
	

	public String getArquivo() {
		return arquivo;
	}

	public void setArquivo(String arquivo) {
		this.arquivo = arquivo;
	}
	
	

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public String toString() {
		return "Colaborador [codigo=" + codigo + ", nome=" + nome + ", userSkype=" + userSkype + ", senhaSype="
				+ senhaSype + ", userEmail=" + userEmail + ", senhaEmail=" + senhaEmail + ", setor=" + setor
				+ ", arquivo=" + arquivo + "]";
	}

	

}
