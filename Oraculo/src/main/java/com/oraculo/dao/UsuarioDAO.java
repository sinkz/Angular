package com.oraculo.dao;

import java.util.ArrayList;
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
	public List<Usuario> listarUsuarios(Integer paginaInicio, Integer count) {
		List<Usuario> lista = new ArrayList<>();
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		System.out.println("PaginaInicio: " + paginaInicio);
		try {
			transacao = sessao.beginTransaction();
			Query consulta = sessao.getNamedQuery("Usuario.listar");
			consulta.setFirstResult((paginaInicio - 1) * 3);
			consulta.setMaxResults(count);
			lista = consulta.list();
			transacao.commit();
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			sessao.close();
		}
		return lista;
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> listarUsuariosConsulta() {
		List<Usuario> lista = new ArrayList<>();
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		try {
			transacao = sessao.beginTransaction();
			Query consulta = sessao.getNamedQuery("Usuario.listar");
			lista = consulta.list();
			transacao.commit();
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			sessao.close();
		}
		return lista;
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> listarUsuariosFiltro(String nome, Integer paginaInicio, Integer count) {
		List<Usuario> lista = new ArrayList<>();
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		try {
			transacao = sessao.beginTransaction();
			System.out.println("Pagina filtro: " + paginaInicio);
			Query consulta = sessao.getNamedQuery("Usuario.listarComFiltro");
			consulta.setParameter("nome", "%" + nome + "%");
			consulta.setParameter("login", "%" + nome + "%");
			consulta.setFirstResult((paginaInicio - 1) * 3);
			consulta.setMaxResults(count);
			lista = consulta.list();
			System.out.println("Resultado: " + lista);
			transacao.commit();
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			sessao.close();
		}
		return lista;
	}

	// Verifica quantos usuarioes estão na pesquisa
	@SuppressWarnings("unchecked")
	public List<Usuario> listarUsuariosFiltro(String nome) {
		List<Usuario> lista = new ArrayList<>();
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		try {
			transacao = sessao.beginTransaction();
			Query consulta = sessao.getNamedQuery("Usuario.listarComFiltro");
			consulta.setParameter("nome", "%" + nome + "%");
			consulta.setParameter("login", "%" + nome + "%");
			lista = consulta.list();
			transacao.commit();
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			sessao.close();
		}
		return lista;
	}
	
	public Usuario buscarUsuario(Integer codigo) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();

		Usuario usuario = null;
		try {
			Query consulta = sessao.getNamedQuery("Usuario.buscarCodigo");
			consulta.setInteger("codigo", codigo);

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
	
	
	
	public void excluir(Integer codigo) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Usuario usuario = new Usuario();
		usuario.setCodigo(codigo);
		Transaction transacao = null;
		try {
			transacao = sessao.beginTransaction();
			sessao.delete(usuario);
			transacao.commit();
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			if (transacao != null) {
				transacao.rollback();
			}
			throw ex;
		} finally {
			sessao.close();
		}
	}

	public void editar(Usuario usuario) {

		Session sessao = HibernateUtil.getSessionFactory().openSession();
		System.out.println("Usuario: " + usuario);
		Transaction transacao = null;
		try {
			transacao = sessao.beginTransaction();
			Usuario usuarioEditar = buscarUsuario(usuario.getCodigo());
			System.out.println("TESTE: "+usuarioEditar);
			usuarioEditar.setNome(usuario.getNome());
			usuarioEditar.setLogin(usuario.getLogin());
			usuarioEditar.setSenha(usuario.getSenha());
			usuarioEditar.setPermissao(usuario.getPermissao());
			usuarioEditar.setFoto(usuario.getFoto());

			sessao.update(usuarioEditar);
			transacao.commit();

		} catch (RuntimeException ex) {
			ex.printStackTrace();
			if (transacao != null) {
				transacao.rollback();
			}
			throw ex;
		} finally {
			sessao.close();
		}

	}

}
