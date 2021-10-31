const marketService = 'http://localhost:9999';
(function () {
    angular.module('market-app', ['ngRoute', 'ngStorage'])
        .config(function config($routeProvider, $locationProvider) {
            $routeProvider
                .when('/', {
                    templateUrl: 'main_page/main_page.html',
                    controller: 'mainPageController'
                })
                .when('/catalog', {
                    templateUrl: 'catalog/catalog.html',
                    controller: 'catalogController'
                })
                .when('/registration', {
                    templateUrl: 'registration/registration.html',
                    controller: 'registrationController'
                })
                .when('/cart', {
                    templateUrl: 'cart/cart.html',
                    controller: 'cartController'
                })
                .when('/checkout', {
                    templateUrl: 'checkout/checkout.html',
                    controller: 'checkoutController'
                })
                .when('/product_info', {
                    templateUrl: 'product_info/product_info.html',
                    controller: 'productInfoController'
                })
                .otherwise({
                    redirectTo: '/'
                });
        })
        .run(function run($rootScope, $http, $localStorage) {
            if ($localStorage.marketAppUser) {
                $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.marketAppUser.token;
            }
        });
})();

angular.module('market-app').controller('indexController', function ($rootScope, $scope, $http, $localStorage, $location) {

    $scope.tryToAuth = function () {
        $http.post(marketService + '/auth', $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.marketAppUser = {
                        userId: $scope.user.id,
                        username: $scope.user.username,
                        token: response.data.token};
                    $scope.user.username = null;
                    $scope.user.password = null;
                }
            }, function errorCallback(response) {
                console.log(response);
            });
    };

    $scope.tryToLogout = function () {
        $scope.clearUser();
        if ($scope.user.username) {
            $scope.user.username = null;
        }
        if ($scope.user.password) {
            $scope.user.password = null;
        }
        $location.path('/');
    };

    $scope.clearUser = function () {
        delete $localStorage.marketAppUser;
        $http.defaults.headers.common.Authorization = '';
    };

    $rootScope.isUserLoggedIn = function () {
        return !!$localStorage.marketAppUser;
        }
});