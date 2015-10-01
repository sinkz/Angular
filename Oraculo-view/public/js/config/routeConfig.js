angular.module("oraculo").config(function($routeProvider){

	$routeProvider.when("/home", {
		templateUrl: "public/views/colaborador.html",
		controller: "colaboradorController",
		
		
	});

	$routeProvider.when("/detalhesColaborador/:codigo", {
		templateUrl: "public/views/detalhesColaborador.html",
		controller: "detalhesContatoCtrl",
		resolve: {
			colaborador: function (colaboradorAPI, $route) {
				return colaboradorAPI.getColaborador($route.current.params.codigo);
			}
		}
	});

	$routeProvider.when("/detalhesUsuario/:codigo", {
		templateUrl: "public/views/detalhesUsuarios.html",
		controller: "detalhesUsuarioCtrl",
		resolve: {
			usuario: function (usuarioAPI, $route) {
				return usuarioAPI.getUsuarioCodigo($route.current.params.codigo);
			}
		}
	});

	$routeProvider.when("/login", {
		templateUrl: "public/views/login.html",
		controller: "loginCtrl"
	});

	$routeProvider.when("/usuario", {
		templateUrl: "public/views/usuario.html",
		controller: "usuarioCtrl"
	});


	$routeProvider.otherwise({redirectTo: "/login"});

});