angular.module('market-app').controller('cartController', function ($scope, $http, $location) {
    let updateCart = function () {
        $http.get(marketService + '/cart')
            .then(function (response) {
                console.log(response.data);
                $scope.cart = response.data;
            });
    };
    updateCart();
    console.log('cart update...');

    $scope.incrementItem = function (productId) {
        $http.get(marketService + '/cart/add/' + productId)
            .then(function successCallback(response) {
                console.log('add item: ' + productId);
                updateCart();
            });
    }

    $scope.decrementItem = function (productId) {
        $http.get(marketService + '/cart/remove/' + productId)
            .then(function successCallback(response) {
                console.log('remove item: ' + productId);
                updateCart();
            });
    }

    $scope.deleteItem = function (productId) {
        $http.get(marketService + '/cart/delete/' + productId)
            .then(function successCallback(response) {
                console.log('delete item: ' + productId);
                updateCart();
            });
    }

    $scope.clearCart = function (productId) {
        $http.get(marketService + '/cart/clear')
            .then(function successCallback(response) {
                console.log('clearing cart...')
                updateCart();
            });
    }

    $scope.createOrder = function () {
        $location.path('/checkout')
    }


});