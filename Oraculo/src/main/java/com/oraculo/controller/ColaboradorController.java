package com.oraculo.controller;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.apache.commons.io.IOUtils;

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
import br.com.caelum.vraptor.observer.upload.UploadedFile;
import br.com.caelum.vraptor.serialization.gson.WithoutRoot;
import br.com.caelum.vraptor.view.Results;

@Controller
@Path("/colaborador")
public class ColaboradorController {

	@Inject
	private Result result;
	@Inject
	private ColaboradorDAO colaboradorDAO;

	final String DIRETORIO_UPLOAD = "C:\\Desenvolvimento\\Angular\\Oraculo-view\\upload\\";

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
		result.use(Results.json()).withoutRoot().from(colaboradorDAO.listarColaboradoresFiltro(nome, pagina, 5))
				.serialize();
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
	@Path(value = "/{codigo}/{arquivo}")
	public void remover(Integer codigo, String arquivo) {
		System.out.println("colaborador: " + arquivo);
		try {
			colaboradorDAO.excluir(codigo);
			String nome = "C:\\Desenvolvimento\\Angular\\Oraculo-view\\upload\\" + arquivo;
			File f = new File(nome);
			f.delete();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Post
	@Path(value = "/")
	@Transactional
	@Consumes(value = "application/json", options = WithoutRoot.class)
	public void salvarColaborador(Colaborador colaborador) {
		try {
			colaboradorDAO.salvar(colaborador);
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

	@Put
	@Path(value = "/{codigo}")
	@Consumes(value = "application/json", options = WithoutRoot.class)
	public void editar(Colaborador colaborador, Integer codigo) {
		colaborador.setCodigo(codigo);
		colaboradorDAO.editar(colaborador);
	}

}
