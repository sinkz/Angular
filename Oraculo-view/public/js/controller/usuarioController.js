angular.module("oraculo").controller("usuarioCtrl", function($scope, $routeParams, usuarioAPI, $location){

	$scope.senhasIguais = false;
	$scope.salvar = function(usuario){
		usuarioAPI.saveUsuario(usuario).success(function(data){
			console.log("Salvar!");
            delete $scope.usuario;
            $scope.colaboradorForm.$setPristine();
            $scope.confirmarSenha = ""; 
            $scope.resetar();      
            $scope.submitted = true;
            $scope.message = "Usuário Cadastrado com Sucesso!!";
            $scope.classAlert = "alert alert-success";

		})
		.error(function(response, status) {
            console.log("erro " + status);
            $scope.submitted = true;
            $scope.message = "Erro ao Cadastrar Usuário: "+status;
            $scope.classAlert = "alert alert-danger";
        });
	}

	 $scope.verificaSenha = function(usuario, showAlert){
	 	if($scope.confirmarSenha == usuario.senha){
	 		console.log("uhul");
	 		$scope.showAlert = false;
            $scope.cssClass = 'has-success';
            $scope.message = "";
            $scope.senhasIguais = true;
	 	}else{
	 		 $scope.showAlert = true;
             $scope.cssClass = 'has-error';
             $scope.message = "As senhas não batem!";
             $scope.classAlert = "alert alert-danger";
             $scope.senhasIguais = false;
	 	}

	 }

	 $scope.resetar = function(){
        $scope.showAlert = false;
        $scope.submitted = false;
        $scope.classAlert = "";
        $scope.cssClass = '';
        $scope.message = "";
        $scope.usuario= "";
    }

    

});