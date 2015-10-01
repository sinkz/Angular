package com.oraculo.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.oraculo.model.Colaborador;
import com.oraculo.util.HibernateUtil;

public class ColaboradorDAO {
	Integer guarda = 0;

	public void salvar(Colaborador colaborador) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		try {
			transacao = sessao.beginTransaction();
			sessao.save(colaborador);
			transacao.commit();
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			throw ex;

		} finally {
			sessao.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Colaborador> listarColaboradores(Integer paginaInicio, Integer count) {
		List<Colaborador> lista = new ArrayList<>();
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		System.out.println("PaginaInicio: " + paginaInicio);
		try {
			transacao = sessao.beginTransaction();
			Query consulta = sessao.getNamedQuery("Colaborador.listar");
			consulta.setFirstResult((paginaInicio - 1) * 5);
			consulta.setMaxResults(count);
			lista = consulta.list();
			transacao.commit();
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			sessao.close();
			guarda = paginaInicio;
		}
		return lista;
	}

	@SuppressWarnings("unchecked")
	public List<Colaborador> listarColaboradoresConsulta() {
		List<Colaborador> lista = new ArrayList<>();
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		try {
			transacao = sessao.beginTransaction();
			Query consulta = sessao.getNamedQuery("Colaborador.listar");
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
	public List<Colaborador> listarColaboradoresFiltro(String nome, Integer paginaInicio, Integer count) {
		List<Colaborador> lista = new ArrayList<>();
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		try {
			transacao = sessao.beginTransaction();
			System.out.println("Pagina filtro: " + paginaInicio);
			Query consulta = sessao.getNamedQuery("Colaborador.listarComFiltro");
			consulta.setParameter("nome", "%" + nome + "%");
			consulta.setParameter("setor", "%" + nome + "%");
			consulta.setFirstResult((paginaInicio - 1) * 5);
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

	// Verifica quantos colaboradores estão na pesquisa
	@SuppressWarnings("unchecked")
	public List<Colaborador> listarColaboradoresFiltro(String nome) {
		List<Colaborador> lista = new ArrayList<>();
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		try {
			transacao = sessao.beginTransaction();
			Query consulta = sessao.getNamedQuery("Colaborador.listarComFiltro");
			consulta.setParameter("nome", "%" + nome + "%");
			consulta.setParameter("setor", "%" + nome + "%");
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

	public Colaborador buscarColaborador(Integer codigo) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		Colaborador colaborador = new Colaborador();
		try {
			transacao = sessao.beginTransaction();
			Query consulta = sessao.getNamedQuery("Colaborador.buscarCodigo");
			consulta.setInteger("codigo", codigo);
			colaborador = (Colaborador) consulta.uniqueResult();
			transacao.commit();
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			sessao.close();
		}
		return colaborador;
	}

	public void excluir(Integer codigo) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Colaborador colaborador = new Colaborador();
		colaborador.setCodigo(codigo);
		Transaction transacao = null;
		try {
			transacao = sessao.beginTransaction();
			sessao.delete(colaborador);
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

	public void editar(Colaborador colaborador) {
		System.out.println("colaborador Usuario: "+colaborador.getUsuario().getLogin());
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		System.out.println("Colaborador: " + colaborador);
		Transaction transacao = null;
		try {
			transacao = sessao.beginTransaction();
			Colaborador colaboradorEditar = buscarColaborador(colaborador.getCodigo());
			System.out.println("TESTE: "+colaboradorEditar);
			colaboradorEditar.setNome(colaborador.getNome());
			colaboradorEditar.setSetor(colaborador.getSetor());
			colaboradorEditar.setSenhaEmail(colaborador.getSenhaEmail());
			colaboradorEditar.setSenhaSype(colaborador.getSenhaSype());
			colaboradorEditar.setUserEmail(colaborador.getUserEmail());
			colaboradorEditar.setUserSkype(colaborador.getUserSkype());
			colaboradorEditar.setArquivo(colaborador.getArquivo());
			colaboradorEditar.setUsuario(colaborador.getUsuario());

			sessao.update(colaboradorEditar);
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
