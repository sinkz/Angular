angular.module("oraculo").factory("colaboradorAPI", function ($http, config) {
	
	var _getColaboradores = function(){
		return $http.get(config.baseURL + "/Oraculo/colaborador/colaboradores");
	};
	
	var _saveColaborador = function(colaborador){
		return  $http.post(config.baseURL +"/Oraculo/colaborador/", colaborador);
	};

	var _getColaborador = function(codigo){
		return $http.get(config.baseURL + "/Oraculo/colaborador/colaborador/" +codigo);
	};

	var _deleteColaborador = function(codigo){
		return  $http.delete(config.baseURL +"/Oraculo/colaborador/" + codigo);
	}

	return {
		getColaboradores: _getColaboradores,
		saveColaborador: _saveColaborador,
		getColaborador: _getColaborador,
		deleteColaborador: _deleteColaborador
	};
});