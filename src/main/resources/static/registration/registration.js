angular.module('market-app').controller('registrationController', function ($scope, $http, $location) {

        $scope.registerUser = function () {
            console.log('submitted: {' +
                ' login: ' + $scope.inputForm.username +
                '; passw: ' + $scope.inputForm.password +
                '; }');
            $http.post(marketService + '/users', $scope.inputForm)
                .then(function successCallback(response) {
                    console.log(response.data);
                    alert('Пользователь успешно зарегистрирован');
                    $scope.inputForm.username = null;
                    $scope.inputForm.password = null;
                    $location.path("/");
                }, function errorCallback(response) {
                    console.log(response.data);
                    alert(response.data.message);
                });

        }

        $scope.resetForm = function () {
            console.log('reset form');
        }

    });