angular.module('market-app').controller('mainPageController', function ($scope, $http) {

        $scope.registerUser = function () {
            console.log('submitted: {' +
                ' id: ' + $scope.inputForm.id +
                '; title: ' + $scope.inputForm.title +
                '; price: ' + $scope.inputForm.price +
                '; }');

        }

        $scope.resetForm = function () {
            console.log('reset form');
        }

    });