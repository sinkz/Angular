package com.oraculo.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.oraculo.model.Colaborador;
import com.oraculo.util.HibernateUtil;

public class ColaboradorDAO {

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
	public List<Colaborador> listarColaboradores() {
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

		Session sessao = HibernateUtil.getSessionFactory().openSession();
		System.out.println("Colaborador: " + colaborador);
		Transaction transacao = null;
		try {
			transacao = sessao.beginTransaction();
			Colaborador colaboradorEditar = buscarColaborador(colaborador.getCodigo());
			System.out.println("Nome: " + colaborador.getNome());
			colaboradorEditar.setNome(colaborador.getNome());
			colaboradorEditar.setSetor(colaborador.getSetor());
			colaboradorEditar.setSenhaEmail(colaborador.getSenhaEmail());
			colaboradorEditar.setSenhaSype(colaborador.getSenhaSype());
			colaboradorEditar.setUserEmail(colaborador.getUserEmail());
			colaboradorEditar.setUserSkype(colaborador.getUserSkype());

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
