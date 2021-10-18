angular.module('market-app').controller('cartController', function ($scope, $http, $location, $localStorage) {

    let updateCart = function () {
        let cart = $localStorage.marketCartId === undefined ? '' : '/' + $localStorage.marketCartId;
        $http.get(marketService + '/cart' + cart)
            .then(function (response) {
                console.log(response.data);
                $scope.cart = response.data;
                $localStorage.marketCartId = response.data.cartId;
            });
    };
    updateCart();
    console.log('cart update...');

    $scope.incrementItem = function (productId) {
        $http.get(marketService + '/cart/' + $scope.cart.cartId + '/add/' + productId)
            .then(function successCallback(response) {
                console.log('add item: ' + productId);
                updateCart();
            });
    }

    $scope.decrementItem = function (productId) {
        $http.get(marketService  + '/cart/' + $scope.cart.cartId + '/remove/' + productId)
            .then(function successCallback(response) {
                console.log('remove item: ' + productId);
                updateCart();
            });
    }

    $scope.deleteItem = function (productId) {
        $http.get(marketService + '/cart/' + $scope.cart.cartId + '/delete/' + productId)
            .then(function successCallback(response) {
                console.log('delete item: ' + productId);
                updateCart();
            });
    }

    $scope.clearCart = function (productId) {
        $http.get(marketService + '/cart/' + $scope.cart.cartId + '/clear')
            .then(function successCallback(response) {
                console.log('clearing cart...')
                delete $localStorage.marketCartId;
                updateCart();
            });
    }

    $scope.createOrder = function () {
        $location.path('/checkout')
    }

});