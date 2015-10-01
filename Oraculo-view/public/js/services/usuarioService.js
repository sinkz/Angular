angular.module("oraculo").factory("usuarioAPI", function ($http, config) {
	
	var _getUsuario = function(usuario){
		return $http.post(config.baseURL + "/Oraculo/usuario/usuario", usuario);
	};

	var _saveUsuario = function(usuario){
		return  $http.post(config.baseURL +"/Oraculo/usuario/", usuario);
	};

	var _getUsuarios = function(paginaInicio){
		return $http.get(config.baseURL + "/Oraculo/usuario/usuarios/" + paginaInicio);
	};

	var _getUsuariosPesquisa = function(){
		return $http.get(config.baseURL + "/Oraculo/usuario/usuarios/consultar");
	};

	var _getUsuariosFiltro = function(nome, pagina){
		return $http.get(config.baseURL + "/Oraculo/usuario/usuariosFiltro/" + nome + pagina);
	}

	var _getUsuariosFiltroFind = function(nome){
		return $http.get(config.baseURL + "/Oraculo/usuario/usuariosFiltroFind/" + nome);
	}

	var _getUsuarioCodigo = function(codigo){
		return $http.get(config.baseURL + "/Oraculo/usuario/usuario/" +codigo);
	};


	var _deleteUsuario = function(codigo, foto){
		return  $http.delete(config.baseURL +"/Oraculo/usuario/" + codigo+"/"+foto);
	}

	var _editaUsuario = function(codigo, usuario){
		return $http.put(config.baseURL + "/Oraculo/usuario/" +codigo, usuario);
	}
	

	return {
		getUsuario: _getUsuario,
		saveUsuario: _saveUsuario,
		getUsuarioCodigo: _getUsuarioCodigo,
		getUsuarios: _getUsuarios,
		getUsuariosPesquisa: _getUsuariosPesquisa,
		getUsuariosFiltro: _getUsuariosFiltro,
		getUsuariosFiltroFind: _getUsuariosFiltroFind,
		editaUsuario: _editaUsuario,
		deleteUsuario: _deleteUsuario
		
	};
});