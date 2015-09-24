angular.module("oraculo").controller("colaboradorController", function($scope, $routeParams, $location, colaboradorAPI, colaboradores){
        
        $scope.tamanhoMaximo = 6;
        $scope.currentPage = 1;
        $scope.totalItems = 60;
        $scope.colaboradores = colaboradores.data;

    $scope.adicionarColaborador = function(colaborador){
    	colaboradorAPI.saveColaborador(colaborador).success(function(data){
    		console.log("Salvar!");
    		delete $scope.colaborador;
 			$scope.colaboradorForm.$setPristine();		
			
    	})
    	.error(function(response, status){
            console.log("erro "+status);
        });
    }

    $scope.excluir = function(colaborador){
        colaboradorAPI.deleteColaborador(colaborador.codigo).success(function(data){
            console.log("Excluindo..");
            carregar();
        })
        .error(function(response, status){
            console.log("Erro: "+status);
        });
    }

        
       
    var carregar = function(criterio){
        console.log("criterio"+ criterio)
        colaboradorAPI.getColaboradores().success(function(data){
            $scope.colaboradores = data;
        });
    }

});