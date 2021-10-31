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
        $http.get(marketService + '/cart/' + $localStorage.marketCartId + '/add/' + productId)
            .then(function successCallback(response) {
                console.log('add item: ' + productId);
                updateCart();
            });
    }

    $scope.decrementItem = function (productId) {
        $http.get(marketService  + '/cart/' + $localStorage.marketCartId + '/remove/' + productId)
            .then(function successCallback(response) {
                console.log('remove item: ' + productId);
                updateCart();
            });
    }

    $scope.deleteItem = function (productId) {
        $http.get(marketService + '/cart/' + $localStorage.marketCartId + '/delete/' + productId)
            .then(function successCallback(response) {
                console.log('delete item: ' + productId);
                updateCart();
            });
    }

    $scope.clearCart = function () {
        $http.get(marketService + '/cart/' + $localStorage.marketCartId + '/clear')
            .then(function successCallback() {
                console.log('clearing cart...')
                updateCart();
            });
    }

    $scope.createOrder = function () {
        $location.path('/checkout')
    }
});