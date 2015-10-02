angular.module("oraculo").factory("userAuthAPI", function() {

    var usuarioAuth = "";
    var _getUserAuth = function() {
        var temp = sessionStorage.getItem('userLogado');
        var viewName = $.parseJSON(temp);
        if (viewName != null) {
            usuarioAuth = viewName.usuario;
            return usuarioAuth;
        } else {
            console.log("ERRO")
            return viewName;
        }
    };
    return {
        getUserAuth: _getUserAuth
    };

});