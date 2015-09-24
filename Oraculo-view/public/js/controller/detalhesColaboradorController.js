angular.module("oraculo").controller("detalhesContatoCtrl", function($scope, $routeParams, colaborador){
    console.log($routeParams.codigo)
    $scope.colaborador = colaborador.data;
    
});