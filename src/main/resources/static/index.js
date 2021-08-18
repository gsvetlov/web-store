const marketService = 'http://localhost:8189/market';
angular.module('product-catalog', ['ui.bootstrap'])
    .controller('indexController', function ($scope, $http) {
        $scope.itemsPerPage = 5;
        $scope.totalElements = 0;
        $scope.totalPages = 0;
        $scope.currentPage = 1;

        let updateCatalog = function (pageIndex = 1, pageSize = 10){
            $http({
                url: marketService + '/products/',
                method: 'GET',
                params: {
                    p: Math.floor(pageIndex - 1),
                    ps: pageSize
                }
            }).then(function (response){
                console.log(response);
                $scope.products = response.data.content;
                $scope.totalElements = response.data.totalElements;
                $scope.totalPages = response.data.totalPages;
            });
        };

        updateCatalog($scope.currentPage, $scope.itemsPerPage);

        $scope.btnDeleteClick = function (id) {
            console.log('button clicked ' + id);
            $http.get(marketService + '/products/delete/' + id)
                .then(function (response) {
                    console.log('delete response: ' + response);
                    updateCatalog($scope.currentPage, $scope.itemsPerPage);
                });
        };

        $scope.firstPage = function (){
            console.log('first page');
            $scope.currentPage = 1;
            updateCatalog($scope.currentPage, $scope.itemsPerPage);
        }
        $scope.previousPage = function (){
            console.log('previous page');
            if ($scope.currentPage > 1) {
                $scope.currentPage--;
                updateCatalog($scope.currentPage, $scope.itemsPerPage);
            }
        }
        $scope.nextPage = function (){
            console.log('next page');
            if ($scope.currentPage < $scope.totalPages) {
                $scope.currentPage++;
                updateCatalog($scope.currentPage, $scope.itemsPerPage);
            }
        }
        $scope.lastPage = function (){
            console.log('last page');
            $scope.currentPage = Math.floor($scope.totalElements / $scope.itemsPerPage);
            if ($scope.currentPage < 1) $scope.currentPage = 1;
            updateCatalog($scope.currentPage, $scope.itemsPerPage);
        }

    });