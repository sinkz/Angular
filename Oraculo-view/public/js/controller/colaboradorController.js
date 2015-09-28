angular.module("oraculo").controller("colaboradorController", function($scope, $routeParams, $location, colaboradorAPI, Upload, $timeout) {

    $scope.tamanhoMaximo = 5;
    $scope.currentPage = 1;
    $scope.totalItems = 60; //Dinamico
    $scope.colaborador = "";
    var editar = false;
    $scope.message = ""

    /**Adiciona um colaborador ou Edita*/
    $scope.adicionarColaborador = function(colaborador) {
        if (!editar) {
            colaborador.arquivo = $scope.f.name;
            console.log('colaborador: ' + colaborador.arquivo)
            colaboradorAPI.saveColaborador(colaborador).success(function(data) {
                    salvarImagem($scope.f);
                    console.log("Salvar!");
                    delete $scope.colaborador;
                    $scope.colaboradorForm.$setPristine();
                    
                    $scope.submitted = true;
                    $scope.message = "Colaborador Cadastrado com Sucesso!!";
                    $scope.classAlert = "alert alert-success";
                    
                    carregar();
                    carregarInicial($scope.currentPage);

                })
                .error(function(response, status) {
                    console.log("erro " + status);
                    $scope.submitted = true;
                    $scope.message = "Erro ao Cadastrar Colaborador: "+status;
                    $scope.classAlert = "alert alert-danger";
                });

        } else if (editar) {
            colaboradorAPI.editaColaborador(colaborador.codigo, colaborador).success(function(data) {
                    console.log("Editando..");
                    $scope.submitted = true;
                    $scope.message = "Colaborador Editado com Sucesso!!";
                    $scope.classAlert = "alert alert-success";
                    carregar();
                    carregarInicial($scope.currentPage);
                })
                .error(function(response, status) {
                    console.log("erro " + status);
                    $scope.submitted = true;
                    $scope.message = "Erro ao Editar Colaborador: "+status;
                    $scope.classAlert = "alert alert-danger";
                });
        }
        editar = false;

    }

    /**Exclui um colaborador*/
    $scope.excluir = function(colaborador) {
            colaboradorAPI.deleteColaborador(colaborador.codigo, colaborador.arquivo).success(function(data) {
                    console.log("Excluindo..");
                    $scope.submitted = true;
                    $scope.message = "Colaborador Excluído com Sucesso!!";
                    $scope.classAlert = "alert alert-success";
                    carregar();
                    carregarInicial($scope.currentPage);


                })
                .error(function(response, status) {
                    console.log("Erro: " + status);
                    $scope.submitted = true;
                    $scope.message = "Erro ao Excluír Colaborador: "+status;
                    $scope.classAlert = "alert alert-danger";
                });
        }
        /**Pega os dados do colaborador e preenche o modal com os dados para que eles possam ser editados*/
    $scope.pegaDadosEdicao = function(colaborador) {
        editar = true;
        $scope.colaborador = "";
        console.log("colaborador: " + colaborador.nome)
        $scope.colaborador = colaborador;
    }


    /**Limpa o modal quando aberto pelo botão de Novo*/
    $scope.limparColaborador = function() {
        $scope.colaborador = "";
        $scope.f = "";
        $scope.showAlert = false;
        $scope.submitted = false;
        $scope.classAlert = "";
        $scope.message = "";
    }

    /**Carrega um novo colaborador a cada troca de página*/
    $scope.loading = function(currentPage, criterioDeBusca) {
            //Verifica se o criterio de busca esta em branco ou undefined se sim faz uma pesquisa nova
            if (criterioDeBusca === "" || criterioDeBusca === undefined) {
                colaboradorAPI.getColaboradores(currentPage).success(function(data) {
                    $scope.colaboradores = data;

                });
                //Se tiver um critério, ou seja o input preenchido ele chama o método filtrar passando o critério e a página
            } else {
                console.log("Tem criterio");
                $scope.filtrar(criterioDeBusca, currentPage);

            }

        }
        /**Consulta inicial, recebe uma pagina como parametro*/
    var carregarInicial = function(numPag) {
        colaboradorAPI.getColaboradores(numPag).success(function(data) {
            $scope.colaboradores = data;
        });
    }

    /**Carregar inicial que verifica o total de items para que possa renderizar a paginação corretamente*/
    var carregar = function(criterio) {

        colaboradorAPI.getColaboradoresPesquisa().success(function(data) {
            var listaColaboradores = data;
            $scope.totalItems = listaColaboradores.length;
            console.log("total items: " + $scope.totalItems)
        });
    }

    /**Filtra os dados por nome*/
    $scope.filtrar = function(criterioDeBusca, currentPage) {
        //Se o input for diferente de vazio ele carrega os dados
        if (criterioDeBusca != "" || criterioDeBusca != undefined) {

            console.log("criterio" + criterioDeBusca);
            colaboradorAPI.getColaboradoresFiltro(criterioDeBusca, currentPage).success(function(data) {
                $scope.colaboradores = data;
                carregarQuantidadeFiltro(criterioDeBusca);

            });
            //Caso o input passe a ficar vazio ele carreta todos normalmente sem filtro
        } else {
            carregarInicial(1);
            carregar();
        }
    }

    /**Carrega todos os colaboradores que estejam dentro do critério de busca, 
    dessa forma podemos ter um total para renderizar a paginação corretamente*/
    var carregarQuantidadeFiltro = function(criterioDeBusca) {
        colaboradorAPI.getColaboradoresFiltroFind(criterioDeBusca).success(function(data) {
            var listaColaboradores = data;
            $scope.totalItems = listaColaboradores.length;
            console.log("total items: " + $scope.totalItems)
        });
    }

    /**Pega o arquivo da página e atribui em uma váriavel*/
    $scope.uploadFiles = function(file) {
        $scope.f = file;

    }

    /**Envia a imagem para o servidor para que possa ser tratada, envia apenas quando clicar no botão salvar*/
    var salvarImagem = function(file) {
        if (file && !file.$error) {
            file.upload = Upload.upload({
                url: 'http://10.80.117.123:8080/Oraculo/colaborador/imagem',
                data: {
                    file: file
                }
            });

            file.upload.then(function(response) {
                $timeout(function() {
                    file.result = response.data;
                    console.log("REsposta: " + response.data);
                });
            }, function(response) {
                if (response.status > 0)
                    $scope.errorMsg = response.status + ': ' + response.data;
            });

            file.upload.progress(function(evt) {
                file.progress = Math.min(100, parseInt(100.0 *
                    evt.loaded / evt.total));
            });
        }
    }


    carregar();
    carregarInicial(1);

});