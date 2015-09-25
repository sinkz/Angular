angular.module("oraculo").factory("colaboradorAPI", function ($http, config) {
	
	var _getColaboradores = function(paginaInicio){
		return $http.get(config.baseURL + "/Oraculo/colaborador/colaboradores/" + paginaInicio);
	};

	var _getColaboradoresPesquisa = function(){
		return $http.get(config.baseURL + "/Oraculo/colaborador/colaboradores/consultar");
	};

	var _getColaboradoresFiltro = function(nome, pagina){
		return $http.get(config.baseURL + "/Oraculo/colaborador/colaboradoresFiltro/" + nome + pagina);
	}

	var _getColaboradoresFiltroFind = function(nome){
		return $http.get(config.baseURL + "/Oraculo/colaborador/colaboradoresFiltroFind/" + nome);
	}

	var _saveColaborador = function(colaborador){
		return  $http.post(config.baseURL +"/Oraculo/colaborador/", colaborador);
	};

	var _getColaborador = function(codigo){
		return $http.get(config.baseURL + "/Oraculo/colaborador/colaborador/" +codigo);
	};

	var _deleteColaborador = function(codigo){
		return  $http.delete(config.baseURL +"/Oraculo/colaborador/" + codigo);
	}

	var _editaColaborador = function(codigo, colaborador){
		return $http.put(config.baseURL + "/Oraculo/colaborador/" +codigo, colaborador);
	}

	return {
		getColaboradores: _getColaboradores,
		saveColaborador: _saveColaborador,
		getColaborador: _getColaborador,
		getColaboradoresPesquisa: _getColaboradoresPesquisa,
		getColaboradoresFiltro: _getColaboradoresFiltro,
		getColaboradoresFiltroFind: _getColaboradoresFiltroFind,
		editaColaborador: _editaColaborador,
		deleteColaborador: _deleteColaborador
	};
});