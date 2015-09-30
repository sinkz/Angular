package com.oraculo.controller;

import javax.inject.Inject;

import javax.transaction.Transactional;

import com.oraculo.dao.UsuarioDAO;
import com.oraculo.model.Usuario;

import br.com.caelum.vraptor.Consumes;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.serialization.gson.WithoutRoot;
import br.com.caelum.vraptor.view.Results;

@Controller
@Path("/usuario")
public class UsuarioController {

	@Inject
	private Result result;
	@Inject
	private UsuarioDAO usuarioDAO;

	@Post
	@Path("/usuario")
	@Transactional
	@Consumes(value = "application/json", options = WithoutRoot.class)
	public void listarUsuario(Usuario usuario) {
		Usuario u = usuarioDAO.autenticar(usuario.getLogin(), usuario.getSenha());
		if (u != null) {
			result.use(Results.status()).ok();
			result.use(Results.json()).from(u).serialize();

		} else {
			result.use(Results.status()).notAcceptable();

		}

	}
	
	@Post
	@Path(value = "/")
	@Transactional
	@Consumes(value = "application/json", options = WithoutRoot.class)
	public void salvarColaborador(Usuario usuario) {
		try {
			usuarioDAO.salvar(usuario);
			result.use(Results.status()).ok();
		} catch (RuntimeException ex) {
			result.use(Results.status()).notAcceptable();
			ex.printStackTrace();
		}

	}

}
