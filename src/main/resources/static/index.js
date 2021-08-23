const marketService = 'http://localhost:8189/market/api/v1';
angular.module('product-catalog', ['ui.bootstrap'])
    .controller('indexController', function ($scope, $http) {
        $scope.itemsPerPage = 5;
        $scope.totalElements = 0;
        $scope.totalPages = 0;
        $scope.currentPage = 1;
        $scope.inputForm = {id: null, title: null, price: null};



        let updateCatalog = function (pageIndex = 1, pageSize = 10) {
            $http({
                url: marketService + '/products/',
                method: 'GET',
                params: {
                    p: Math.floor(pageIndex - 1),
                    ps: pageSize
                }
            }).then(function (response) {
                console.log(response);
                $scope.products = response.data.content;
                $scope.totalElements = response.data.totalElements;
                $scope.totalPages = response.data.totalPages;
            });
        };

        updateCatalog($scope.currentPage, $scope.itemsPerPage);

        $scope.btnDeleteClick = function (id) {
            console.log('button clicked ' + id);
            $http.delete(marketService + '/products/' + id)
                .then(function (response) {
                    console.log('delete response: ' + response);
                    updateCatalog($scope.currentPage, $scope.itemsPerPage);
                });
        };

        $scope.firstPage = function () {
            console.log('first page');
            $scope.currentPage = 1;
            updateCatalog($scope.currentPage, $scope.itemsPerPage);
        }
        $scope.previousPage = function () {
            if ($scope.currentPage > 1) {
                $scope.currentPage--;
                updateCatalog($scope.currentPage, $scope.itemsPerPage);
            }
            console.log('previous page: ' + $scope.currentPage);
        }
        $scope.nextPage = function () {
            if ($scope.currentPage < Math.ceil($scope.totalElements / $scope.itemsPerPage)) {
                $scope.currentPage++;
                updateCatalog($scope.currentPage, $scope.itemsPerPage);
            }
            console.log('next page: ' + $scope.currentPage);
        }
        $scope.lastPage = function () {
            $scope.currentPage = Math.ceil($scope.totalElements / $scope.itemsPerPage);
            console.log('last page: ' + $scope.currentPage);
            updateCatalog($scope.currentPage, $scope.itemsPerPage);
        }

        $scope.btnUpdateClick = function (product) {
            console.log('init update: {' +
                ' id: ' + product.id +
                '; title: ' + product.title +
                '; price: ' + product.price +
                '; }');
            $scope.inputForm.id = product.id;
            $scope.inputForm.title = product.title;
            $scope.inputForm.price = product.price;
            console.log('update: {' +
                ' id: ' + $scope.inputForm.id +
                '; title: ' + $scope.inputForm.title +
                '; price: ' + $scope.inputForm.price +
                '; }');
        }

        $scope.createProduct = function () {
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