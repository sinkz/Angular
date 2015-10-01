angular.module("oraculo").controller("loginCtrl", function($scope, $routeParams, usuarioAPI, $location, userAuthAPI){
    

    $scope.autenticar = function(usuario){
    	//console.log("usuario" +usuario)
    	usuarioAPI.getUsuario(usuario).success(function(data) {
    		//console.log("Data: "+data);
    		
    		sessionStorage.setItem('userLogado', JSON.stringify(data)) //objeto Usuario
    		//console.log("Storage: "+sessionStorage);
    		
    		var temp = sessionStorage.getItem('userLogado');
        	var viewName = $.parseJSON(temp);

        	if(viewName != null){
        		sessionStorage.setItem('logado', true);
        		//console.log("Usuario Logado: " +viewName.usuario.login)
        		$scope.usuarioLogado = viewName.usuario;
                //var teste = userAuthAPI.getUserAuth($scope.usuarioLogado);
               // console.log("TESTE: "+teste.senha)
    			$location.path("/home");
        	}
    		
    		
    	})
    	.error(function(response, status) {
    		console.log("erro " + status);
    		if(status == 406){
    			$scope.submitted = true;
            	$scope.message = "Usuário ou Senha Inválidos";
            	$scope.classAlert = "alert alert-danger";
            	$scope.usuario = "";
            	$scope.erro = false;
    		}              
        });

    }
    var init = function () {
        var temp = sessionStorage.getItem('userLogado');
        var viewName = $.parseJSON(temp); 
        
        if(viewName != null){   
            $scope.usuarioLogado = viewName.usuario.nome;
            $scope.user = viewName;
        }else{
            $scope.usuarioLogado = "";
        }
    };

    $scope.sair = function(){
        $scope.usuarioLogado = "";
        sessionStorage.removeItem('userLogado');
        sessionStorage.setItem('logado', false);
        console.log("Sair")

    }

    init();
    
});