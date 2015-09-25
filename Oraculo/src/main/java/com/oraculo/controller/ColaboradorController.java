package com.oraculo.controller;

import javax.inject.Inject;
import javax.transaction.Transactional;

import com.oraculo.dao.ColaboradorDAO;
import com.oraculo.model.Colaborador;

import br.com.caelum.vraptor.Consumes;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Delete;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.serialization.gson.WithoutRoot;
import br.com.caelum.vraptor.view.Results;

@Controller
@Path("/colaborador")
public class ColaboradorController {

	@Inject
	private Result result;
	@Inject
	private ColaboradorDAO colaboradorDAO;

	@Get
	@Path("/colaboradores/{paginaInicio}")
	public void listarTodos(Integer paginaInicio) {
		System.out.println("TESTE: " + paginaInicio);
		result.use(Results.json()).withoutRoot().from(colaboradorDAO.listarColaboradores(paginaInicio, 5)).serialize();
	}

	@Get
	@Path("/colaboradores/consultar")
	public void listarTodosConsulta() {
		result.use(Results.json()).withoutRoot().from(colaboradorDAO.listarColaboradoresConsulta()).serialize();
	}
	
	@Get
	@Path("/colaboradoresFiltro/{nome}{pagina}")
	public void listarFiltro(String nome, Integer pagina) {
		System.out.println("Nome: " + nome);
		result.use(Results.json()).withoutRoot().from(colaboradorDAO.listarColaboradoresFiltro(nome, pagina, 5)).serialize();
	}
	
	@Get
	@Path("/colaboradoresFiltroFind/{nome}")
	public void listarFiltro(String nome) {
		System.out.println("Nome: " + nome);
		result.use(Results.json()).withoutRoot().from(colaboradorDAO.listarColaboradoresFiltro(nome)).serialize();
	}
	
	

	@Get
	@Path("/colaborador/{codigo}")
	public void listarColaborador(Integer codigo) {
		result.use(Results.json()).withoutRoot().from(colaboradorDAO.buscarColaborador(codigo)).serialize();

	}

	@Delete
	@Path(value = "/{codigo}")
	public void remover(Integer codigo) {
		colaboradorDAO.excluir(codigo);
	}

	@Post
	@Path(value = "/")
	@Transactional
	@Consumes(value = "application/json", options = WithoutRoot.class)
	public void salvarColaborador(Colaborador colaborador) {
		colaboradorDAO.salvar(colaborador);

	}
	
	@Put
	@Path(value = "/{codigo}")
	@Consumes(value = "application/json", options = WithoutRoot.class)
	public void editar(Colaborador colaborador, Integer codigo) {
		colaborador.setCodigo(codigo);
		colaboradorDAO.editar(colaborador);
	}

}
