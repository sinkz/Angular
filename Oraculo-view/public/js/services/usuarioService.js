angular.module("oraculo").factory("usuarioAPI", function ($http, config) {
	

	var _getUsuario = function(usuario){
		return $http.post(config.baseURL + "/Oraculo/usuario/usuario", usuario);
	};


	return {
		getUsuario: _getUsuario
		
	};
});