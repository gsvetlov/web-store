angular.module('market-app').controller('registrationController', function ($scope, $http, $location) {
    $scope.registerUser = function () {
        if ($scope.inputForm.password !== $scope.inputForm.passwordRepeat) {
            alert('Пароли не совпадают');
            return;
        } else {
            $http.post(marketService + '/users', $scope.inputForm)
                .then(function successCallback(response) {
                    console.log(response.data);
                    alert('Пользователь успешно зарегистрирован');
                    $scope.inputForm.username = null;
                    $scope.inputForm.password = null;
                    $scope.inputForm.firstName = null;
                    $scope.inputForm.lastName = null;
                    $scope.inputForm.middleName = null;
                    $scope.inputForm.email = null;
                    $location.path("/");
                }, function errorCallback(response) {
                    console.log(response.data);
                    alert(response.data.message);
                });
        }
    }
});