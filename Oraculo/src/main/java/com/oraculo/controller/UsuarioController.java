package com.oraculo.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;

import javax.transaction.Transactional;

import com.oraculo.dao.UsuarioDAO;
import com.oraculo.model.Usuario;

import br.com.caelum.vraptor.Consumes;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Delete;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.observer.upload.UploadedFile;
import br.com.caelum.vraptor.serialization.gson.WithoutRoot;
import br.com.caelum.vraptor.view.Results;

@Controller
@Path("/usuario")
public class UsuarioController {

	@Inject
	private Result result;
	@Inject
	private UsuarioDAO usuarioDAO;

	final String DIRETORIO_UPLOAD = "C:\\Desenvolvimento\\Angular\\Oraculo-view\\upload\\users\\";

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
	public void salvarUsuario(Usuario usuario) {
		try {
			usuarioDAO.salvar(usuario);
			result.use(Results.status()).ok();
		} catch (RuntimeException ex) {
			result.use(Results.status()).notAcceptable();
			ex.printStackTrace();
		}

	}

	@Post
	@Path(value = "/imagem")
	public void pegaImagem(UploadedFile file) throws IOException {

		InputStream in = new BufferedInputStream(file.getFile());
		File fotoSalva = new File(DIRETORIO_UPLOAD, file.getFileName());
		System.out.println(fotoSalva);
		FileOutputStream fos = new FileOutputStream(fotoSalva);
		while (in.available() != 0) {
			fos.write(in.read());
		}
		fos.close();
	}

	@Get
	@Path("/usuarios/{paginaInicio}")
	public void listarTodos(Integer paginaInicio) {
		System.out.println("TESTE: " + paginaInicio);
		result.use(Results.json()).withoutRoot().from(usuarioDAO.listarUsuarios(paginaInicio, 3)).serialize();
	}

	@Get
	@Path("/usuarios/consultar")
	public void listarTodosConsulta() {
		result.use(Results.json()).withoutRoot().from(usuarioDAO.listarUsuariosConsulta()).serialize();
	}

	@Get
	@Path("/usuariosFiltro/{nome}{pagina}")
	public void listarFiltro(String nome, Integer pagina) {
		System.out.println("Nome: " + nome);
		result.use(Results.json()).withoutRoot().from(usuarioDAO.listarUsuariosFiltro(nome, pagina, 3)).serialize();
	}

	@Get
	@Path("/usuariosFiltroFind/{nome}")
	public void listarFiltro(String nome) {
		System.out.println("Nome: " + nome);
		result.use(Results.json()).withoutRoot().from(usuarioDAO.listarUsuariosFiltro(nome)).serialize();
	}

	@Get
	@Path("/usuario/{codigo}")
	public void listarColaborador(Integer codigo) {
		result.use(Results.json()).withoutRoot().from(usuarioDAO.buscarUsuario(codigo)).serialize();

	}

	@Delete
	@Path(value = "/{codigo}/{arquivo}")
	public void remover(Integer codigo, String arquivo) {
		try {
			usuarioDAO.excluir(codigo);
			String nome = DIRETORIO_UPLOAD + arquivo;
			File f = new File(nome);
			f.delete();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Put
	@Path(value = "/{codigo}")
	@Consumes(value = "application/json", options = WithoutRoot.class)
	public void editar(Usuario usuario, Integer codigo) {
		usuario.setCodigo(codigo);
		usuarioDAO.editar(usuario);
	}

}
