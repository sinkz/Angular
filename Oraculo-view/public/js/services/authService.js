angular.module("oraculo").factory("userAuthAPI", function () {

	var usuarioAuth = "" ;

	var _getUserAuth = function(usuario){
		usuarioAuth = usuario;
		console.log("Usuario logadaço: "+ usuarioAuth.nome);
		return usuarioAuth;
	};


	var _getUsuario = function(){
		return usuarioAuth;
	};


	return {
		getUserAuth: _getUserAuth,
		getUsuario: _getUsuario
	};

});