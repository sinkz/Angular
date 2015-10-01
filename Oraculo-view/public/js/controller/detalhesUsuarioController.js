angular.module("oraculo").controller("detalhesUsuarioCtrl", function($scope, $routeParams, usuario){
    console.log($routeParams.codigo)
    $scope.usuario = usuario.data;

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