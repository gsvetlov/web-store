const marketService = 'http://localhost:8189/market';
angular.module('product-catalog', ['ui.bootstrap'])
    .controller('indexController', function ($scope, $http) {
        console.log("indexController starting...");
        let updateCatalog = function () {
            $http.get(marketService + '/products')
                .then(function (response) {
                    console.log('update response: ' + response);
                    $scope.products = response.data;
                })
        };
        updateCatalog();
        $scope.btnDeleteClick = function (id) {
            console.log('button clicked ' + id);
            $http.get(marketService + '/products/delete/' + id)
                .then(function (response) {
                    console.log('delete response: ' + response);
                    updateCatalog();
                });
        };
        $scope.showNextPage = function () {
            console.log("show page" + ($scope.currentPage - 1));
        }
        $scope.itemsPerPage = 5;
        $scope.totalItems = 100;
        $scope.currentPage = 1;
        $scope.firstPage = function (){
            console.log('first page');
            $scope.currentPage = 1
        }
        $scope.previousPage = function (){
            console.log('previous page');
            $scope.currentPage--;
        }
        $scope.nextPage = function (){
            console.log('next page');
            $scope.currentPage++;
        }
        $scope.lastPage = function (){
            console.log('last page');
            $scope.currentPage = $scope.totalItems / $scope.itemsPerPage;
        }

    });