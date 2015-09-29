angular.module("oraculo").controller("loginCtrl", function($scope, $routeParams, usuarioAPI, $rootScope){
    

    $scope.autenticar = function(usuario){
    	usuarioAPI.getUsuario(usuario).success(function(data) {
    		console.log("Data: "+data);
    		sessionStorage.setItem('userLogado', JSON.stringify(data)) //objeto de usuário que utilizo.
    		console.log("Storage: "+sessionStorage);
    		var user = sessionStorage.getItem("userLogado");

    		var userString = JSON.stringify(user);
			var objUser = JSON.parse(userString);
    		console.log("Usuario:" +objUser.login);
    	})
    	.error(function(response, status) {
            console.log("erro " + status);
                  
        });

    }
    
});