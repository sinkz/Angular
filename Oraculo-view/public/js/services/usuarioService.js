angular.module("oraculo").factory("usuarioAPI", function ($http, config) {
	
	var _getUsuario = function(usuario){
		return $http.post(config.baseURL + "/Oraculo/usuario/usuario", usuario);
	};

	var _saveUsuario = function(usuario){
		return  $http.post(config.baseURL +"/Oraculo/usuario/", usuario);
	};

	return {
		getUsuario: _getUsuario,
		saveUsuario: _saveUsuario
		
	};
});