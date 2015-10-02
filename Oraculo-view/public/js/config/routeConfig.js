angular.module("oraculo").config(function($routeProvider) {

    $routeProvider.when("/home", {
        templateUrl: "public/views/colaborador.html",
        controller: "colaboradorController",
        resolve: {
            usuarioAutenticado: function(userAuthAPI, $location) {
                var usuarioLogado = userAuthAPI.getUserAuth();
                //Função que verifica se o usuário está nulo dessa forma não pode acessar essa rota
                usuarioNulo(usuarioLogado, $location);
            }
        }


    });
    $routeProvider.when("/detalhesColaborador/:codigo", {
        templateUrl: "public/views/detalhesColaborador.html",
        controller: "detalhesContatoCtrl",
        resolve: {
            colaborador: function(colaboradorAPI, $route) {
                return colaboradorAPI.getColaborador($route.current.params.codigo);
            },
            usuarioAutenticado: function(userAuthAPI, $location) {
                var usuarioLogado = userAuthAPI.getUserAuth();
                //Função que verifica se o usuário está nulo dessa forma não pode acessar essa rota
                usuarioNulo(usuarioLogado, $location);
            }
        }
    });

    $routeProvider.when("/detalhesUsuario/:codigo", {
        templateUrl: "public/views/detalhesUsuarios.html",
        controller: "detalhesUsuarioCtrl",
        resolve: {
            usuario: function(usuarioAPI, $route) {
                return usuarioAPI.getUsuarioCodigo($route.current.params.codigo);
            },
            /**Verifica se o usuario tem permissão para acessar essa rota*/
            usuarioAutenticado: function(userAuthAPI, $location) {
                var usuarioLogado = userAuthAPI.getUserAuth();
                
                if (usuarioLogado == null) {
                    usuarioNulo(usuarioLogado, $location);
                } else {
                    usuarioNegado(usuarioLogado, $location);
                }
            }
        }
    });

    $routeProvider.when("/login", {
        templateUrl: "public/views/login.html",
        controller: "loginCtrl"

    });

    $routeProvider.when("/usuario", {
        templateUrl: "public/views/usuario.html",
        controller: "usuarioCtrl",
        resolve: {
            /**Verifica se o usuario tem permissão para acessar essa rota*/
            usuarioAutenticado: function(userAuthAPI, $location) {
                var usuarioLogado = userAuthAPI.getUserAuth();
                /**Se o usuario for nullo ele cai na função usuarioNulo dessa forma é redirecionado pro login*/
                if (usuarioLogado == null) {
                    usuarioNulo(usuarioLogado, $location);
                } else {
                    usuarioNegado(usuarioLogado, $location);
                }

            }
        }

    });

    $routeProvider.otherwise({
        redirectTo: "/home"
    });

    var usuarioNulo = function(usuarioLogado, $location) {
        if (usuarioLogado == null) {
            $location.path("/login")
        }
    }

    var usuarioNegado = function(usuarioLogado, $location) {
        if (usuarioLogado.permissao != 1) {
            $location.path("/home")
        }
    }

});