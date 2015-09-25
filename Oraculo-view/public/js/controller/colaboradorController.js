angular.module("oraculo").controller("colaboradorController", function($scope, $routeParams, $location, colaboradorAPI){

        $scope.tamanhoMaximo = 5;
        $scope.currentPage = 1;
        $scope.totalItems = 60;//Dinamico
        $scope.colaborador = "";
        var editar = false;

    /**Adiciona um colaborador ou Edita*/
    $scope.adicionarColaborador = function(colaborador){
        
        if(!editar){
    	   colaboradorAPI.saveColaborador(colaborador).success(function(data){
    		  console.log("Salvar!");
    		  delete $scope.colaborador;
 			    $scope.colaboradorForm.$setPristine();	
                carregar(); 
                carregarInicial($scope.currentPage);
            		
    	   })
           .error(function(response, status){
            console.log("erro "+status);
        });

        }else if(editar){
            colaboradorAPI.editaColaborador(colaborador.codigo, colaborador).success(function(data){
                console.log("Editando..");
                carregar();
                carregarInicial($scope.currentPage);
            })
            .error(function(response, status){
            console.log("erro "+status);
            });
        }
        editar = false;
        
    }

    /**Exclui um colaborador*/
    $scope.excluir = function(colaborador){
        colaboradorAPI.deleteColaborador(colaborador.codigo).success(function(data){
            console.log("Excluindo..");
            carregar();
            carregarInicial($scope.currentPage);
            
        })
        .error(function(response, status){
            console.log("Erro: "+status);
        });
    }
    /**Pega os dados do colaborador e preenche o modal com os dados para que eles possam ser editados*/
    $scope.pegaDadosEdicao = function(colaborador){
        editar = true;
        $scope.colaborador = "";
        console.log("colaborador: "+colaborador.nome)
        $scope.colaborador = colaborador;
    }

   
    /**Limpa o modal quando aberto pelo botão de Novo*/
    $scope.limparColaborador = function(){
         $scope.colaborador = "";
    }

    /**Carrega um novo colaborador a cada troca de página*/
    $scope.loading = function(currentPage, criterioDeBusca){  
        //Verifica se o criterio de busca esta em branco ou undefined se sim faz uma pesquisa nova
        if(criterioDeBusca === "" || criterioDeBusca === undefined){  
            colaboradorAPI.getColaboradores(currentPage).success(function(data){
                $scope.colaboradores = data;
            
            });
        //Se tiver um critério, ou seja o input preenchido ele chama o método filtrar passando o critério e a página
        }else{
            console.log("Tem criterio");
            $scope.filtrar(criterioDeBusca, currentPage);
        
        }

    }
     /**Consulta inicial, recebe uma pagina como parametro*/
    var carregarInicial = function(numPag){
        colaboradorAPI.getColaboradores(numPag).success(function(data){
            $scope.colaboradores = data;
        });
    }
   
    /**Carregar inicial que verifica o total de items para que possa renderizar a paginação corretamente*/
    var carregar = function(criterio){
        
        colaboradorAPI.getColaboradoresPesquisa().success(function(data){
            var listaColaboradores = data;
             $scope.totalItems = listaColaboradores.length;
             console.log("total items: " + $scope.totalItems)
        });
    }

    /**Filtra os dados por nome*/
    $scope.filtrar = function(criterioDeBusca, currentPage){
        //Se o input for diferente de vazio ele carrega os dados
        if(criterioDeBusca != "" || criterioDeBusca != undefined){

            console.log("criterio"+ criterioDeBusca);
            colaboradorAPI.getColaboradoresFiltro(criterioDeBusca, currentPage).success(function(data){
                $scope.colaboradores = data;
                carregarQuantidadeFiltro(criterioDeBusca);
            
            });
        //Caso o input passe a ficar vazio ele carreta todos normalmente sem filtro
        }else{
            carregarInicial(1);
            carregar();
        }
    }

     /**Carrega todos os colaboradores que estejam dentro do critério de busca, 
     dessa forma podemos ter um total para renderizar a paginação corretamente*/
    var carregarQuantidadeFiltro = function(criterioDeBusca){
        colaboradorAPI.getColaboradoresFiltroFind(criterioDeBusca).success(function(data){
             var listaColaboradores = data;
             $scope.totalItems = listaColaboradores.length;
             console.log("total items: " + $scope.totalItems)
        });
    }


    carregar();
    carregarInicial(1);

});