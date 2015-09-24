package com.oraculo.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import br.com.caelum.vraptor.BeforeCall;
import br.com.caelum.vraptor.InterceptionException;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;

@Intercepts
public class CORSInterceptor {

	@Inject
	private Result result;
	@Inject
	private HttpServletRequest request;

	@BeforeCall
	public void intercept() throws InterceptionException {
		// Fix your origin if possible for security reasons
		String origin = request.getHeader("origin") != null ? request.getHeader("origin") : "*";

		result.use(Results.status()).header("Access-Control-Allow-Origin", origin);
		result.use(Results.status()).header("Access-Control-Allow-Credentials", "true");
		result.use(Results.status()).header("Access-Control-Expose-Headers", "Content-Type, Location");
	}
}