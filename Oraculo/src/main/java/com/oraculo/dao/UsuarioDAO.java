package com.oraculo.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.oraculo.model.Usuario;
import com.oraculo.util.HibernateUtil;



public class UsuarioDAO {
	
	public void salvar(Usuario usuario) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		try {
			transacao = sessao.beginTransaction();
			sessao.save(usuario);
			transacao.commit();

		} catch (RuntimeException ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			sessao.close();
		}

	}

	@SuppressWarnings("unchecked")
	public List<Usuario> buscar() {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = sessao.beginTransaction();
		List<Usuario> list = null;
		try {
			Query consulta = sessao.getNamedQuery("Usuario.listar");
			list = consulta.list();
			transacao.commit();
		} catch (RuntimeException ex) {
			throw ex;
		} finally {
			sessao.close();
		}
		return list;
	}
	
	public Usuario buscarPorCodigo(String codigo) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();

		Usuario usuario = null;
		try {
			Query consulta = sessao.getNamedQuery("Usuario.buscarCodigo");
			consulta.setString("codigo", codigo);

			usuario = (Usuario) consulta.uniqueResult();
		} catch (RuntimeException ex) {
			throw ex;
		} finally {
			sessao.close();
		}
		return usuario;
	}

	public Usuario autenticar(String login, String senha) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();

		Usuario usuario = null;
		try {
			Query consulta = sessao.getNamedQuery("Usuario.Autenticar");
			consulta.setString("login", login);
			consulta.setString("senha", senha);

			usuario = (Usuario) consulta.uniqueResult();
		} catch (RuntimeException ex) {
			throw ex;
		} finally {
			sessao.close();
		}
		return usuario;
	}

}
