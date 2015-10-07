angular.module("oraculo").controller("usuarioCtrl", function($scope, $routeParams, usuarioAPI, $location, Upload, $timeout) {

    $scope.senhasIguais = false;
    $scope.tamanhoMaximo = 5;
    $scope.currentPage = 1;
    $scope.totalItems = 60; //Dinamico
    var editar = false;
    var user = "";
    $scope.usuario = "";
    $scope.f = "";

    $scope.salvar = function(usuario) {
        if (!editar) {
            usuario.foto = $scope.f.name;
            usuarioAPI.saveUsuario(usuario).success(function(data) {
                    console.log("Salvar!");
                    salvarImagem($scope.f);
                    delete $scope.usuario;
                    $scope.usuarioForm.$setPristine();
                    $scope.confirmarSenha = "";
                    $scope.resetar();
                    $scope.submitted = true;
                    $scope.message = "Usuário Cadastrado com Sucesso!!";
                    $scope.classAlert = "alert alert-success";

                    carregar();
                    carregarInicial(1);
                    $scope.tab = 2;

                })
                .error(function(response, status) {
                    console.log("erro " + status);
                    $scope.submitted = true;
                    $scope.message = "Erro ao Cadastrar Usuário: " + status;
                    $scope.classAlert = "alert alert-danger";
                });

        } else if (editar) {
            usuario.foto = $scope.f.name;
            usuarioAPI.editaUsuario(usuario.codigo, usuario).success(function(data) {
                    console.log("Editando..");
                    salvarImagem($scope.f);
                    $scope.submitted = true;
                    $scope.message = "Usuário Editado com Sucesso!!";
                    $scope.classAlert = "alert alert-success";
                    carregar();
                    carregarInicial($scope.currentPage);
                    $scope.tab = 2;
                })
                .error(function(response, status) {
                    console.log("erro " + status);
                    $scope.submitted = true;
                    $scope.message = "Erro ao Editar Usuário: " + status;
                    $scope.classAlert = "alert alert-danger";
                });
        }
        editar = false;
    }



    //Função pra entrar na verificação de senha(Isso evita a chamada do ng-blur caso editar seja true)
    $scope.verificaSenha = function(usuario, showAlert) {
       
        if ($scope.confirmarSenha == usuario.senha) {
            console.log("uhul");
            $scope.showAlert = false;
            $scope.cssClass = 'has-success';
            $scope.message = "";
            $scope.senhasIguais = true;
        } else {
            $scope.showAlert = true;
            $scope.cssClass = 'has-error';
            $scope.message = "As senhas não batem!";
            $scope.classAlert = "alert alert-danger";
            $scope.senhasIguais = false;
        }
        

    }
     //Pega um usuario e atribui a uma váriavel para ser usada na exclusão
     $scope.pegaUsuario = function(usuario) {
        user = usuario;
     }

    /**Exclui um usuario*/
    $scope.excluir = function() {
            usuarioAPI.deleteUsuario(user.codigo, user.foto).success(function(data) {
                    console.log("Excluindo..");
                    $scope.submitted = true;
                    $scope.message = "Usuário Excluido com Sucesso!!";
                    $scope.classAlert = "alert alert-success";
                    carregar();
                    carregarInicial($scope.currentPage);


                })
                .error(function(response, status) {
                    console.log("Erro: " + status);
                    $scope.submitted = true;
                    $scope.message = "Erro ao Excluir Usuário: " + status;
                    $scope.classAlert = "alert alert-danger";
                });
        }
        /**Pega os dados do usuario e preenche o modal com os dados para que eles possam ser editados*/
    $scope.pegaDadosEdicao = function(usuario) {
        editar = true;
        $scope.usuario = "";
        console.log("usuario: " + usuario.nome)
        $scope.usuario = usuario;
        $scope.confirmarSenha = usuario.senha;
        $scope.tab = 1;
    }

    

    /**Carrega um novo usuario a cada troca de página*/
    $scope.loading = function(currentPage, criterioDeBusca) {
            //Verifica se o criterio de busca esta em branco ou undefined se sim faz uma pesquisa nova
            if (criterioDeBusca === "" || criterioDeBusca === undefined) {
                usuarioAPI.getUsuarios(currentPage).success(function(data) {
                    $scope.usuarios = data;

                });
                //Se tiver um critério, ou seja o input preenchido ele chama o método filtrar passando o critério e a página
            } else {
                console.log("Tem criterio");
                $scope.filtrar(criterioDeBusca, currentPage);

            }

        }
        /**Consulta inicial, recebe uma pagina como parametro*/
    var carregarInicial = function(numPag) {
        usuarioAPI.getUsuarios(numPag).success(function(data) {
            $scope.usuarios = data;
        });
    }

    /**Carregar inicial que verifica o total de items para que possa renderizar a paginação corretamente*/
    var carregar = function(criterio) {

        usuarioAPI.getUsuariosPesquisa().success(function(data) {
            var listaUsuarios = data;
            $scope.totalItems = listaUsuarios.length;
            console.log("total items: " + $scope.totalItems)
        });
    }

    /**Filtra os dados por nome*/
    $scope.filtrar = function(criterioDeBusca, currentPage) {
        //Se o input for diferente de vazio ele carrega os dados
        console.log("Criterio: " + criterioDeBusca)
        if (criterioDeBusca != "" && criterioDeBusca != undefined) {

            console.log("criterio" + criterioDeBusca);
            console.log("entrou")
            usuarioAPI.getUsuariosFiltro(criterioDeBusca, currentPage).success(function(data) {
                $scope.usuarios = data;
                carregarQuantidadeFiltro(criterioDeBusca);

            });
            //Caso o input passe a ficar vazio ele carreta todos normalmente sem filtro
        } else if (criterioDeBusca == "" || criterioDeBusca == undefined) {
            carregarInicial(1);
            carregar();
        }
    }

    /**Carrega todos os usuarioes que estejam dentro do critério de busca, 
    dessa forma podemos ter um total para renderizar a paginação corretamente*/
    var carregarQuantidadeFiltro = function(criterioDeBusca) {
        usuarioAPI.getUsuariosFiltroFind(criterioDeBusca).success(function(data) {
            var listaUsuarios = data;
            $scope.totalItems = listaUsuarios.length;
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
                url: 'http://10.80.117.123:8080/Oraculo/usuario/imagem',
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

    $scope.resetar = function() {
        editar = false;
        $scope.showAlert = false;
        $scope.submitted = false;
        $scope.classAlert = "";
        $scope.cssClass = '';
        $scope.message = "";
        $scope.usuario = "";
        $scope.confirmarSenha = "";
    }

    var init = function() {
        var temp = sessionStorage.getItem('userLogado');
        var viewName = $.parseJSON(temp);

        if (viewName != null) {
            $scope.usuarioLogado = viewName.usuario.nome;
            $scope.user = viewName;
        } else {
            $scope.usuarioLogado = "";
        }
    };

    $scope.sair = function() {
        $scope.usuarioLogado = "";
        sessionStorage.removeItem('userLogado');
        sessionStorage.setItem('logado', false);
        console.log("Sair")

    }

    init();
    carregar();
    carregarInicial(1);



});