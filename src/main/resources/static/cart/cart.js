angular.module('market-app').controller('cartController', function ($scope, $http, $location, $localStorage) {
    let updateCart = function () {
        $http.get(marketService + '/cart/' + $localStorage.marketAppUser.cartId)
            .then(function (response) {
                console.log(response.data);
                $scope.cart = response.data;
                $localStorage.marketAppUser.cartId = $scope.cart.cartId;
            });
    };
    updateCart();
    console.log('cart update...');

    $scope.incrementItem = function (productId) {
        $http.get(marketService + '/cart/' + $localStorage.marketAppUser.cartId + '/add/' + productId)
            .then(function successCallback(response) {
                console.log('add item: ' + productId);
                updateCart();
            });
    }

    $scope.decrementItem = function (productId) {
        $http.get(marketService + '/cart/' + $localStorage.marketAppUser.cartId + '/remove/' + productId)
            .then(function successCallback(response) {
                console.log('remove item: ' + productId);
                updateCart();
            });
    }

    $scope.deleteItem = function (productId) {
        $http.get(marketService + '/cart/' + $localStorage.marketAppUser.cartId + '/delete/' + productId)
            .then(function successCallback(response) {
                console.log('delete item: ' + productId);
                updateCart();
            });
    }

    $scope.clearCart = function (productId) {
        $http.get(marketService + '/cart/' + $localStorage.marketAppUser.cartId + '/clear')
            .then(function successCallback(response) {
                console.log('clearing cart...')
                updateCart();
            });
    }

    $scope.createOrder = function () {
        $location.path('/checkout')
    }


});