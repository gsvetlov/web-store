angular.module('market-app').controller('checkoutController', function ($scope, $http, $location) {

        $scope.checkout = function () {
            console.log('checkout: {' +
                ' address: ' + $scope.inputForm.address +
                '; phone: ' + $scope.inputForm.phone +
                '; }');
            $http.put(marketService + '/orders', $scope.inputForm)
                .then(function successCallback(response) {
                    console.log(response.data);
                    alert('Заказ успешно создан');
                    $scope.inputForm.address = null;
                    $scope.inputForm.phone = null;
                    $location.path("/");
                }, function errorCallback(response) {
                    console.log(response.data);
                    alert(response.data.message);
                });
        }
    });