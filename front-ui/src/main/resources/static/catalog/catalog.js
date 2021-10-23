angular.module('market-app').controller('catalogController', function ($scope, $http, $location, $localStorage) {
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

    $scope.addToCart = function (productId) {
        $http.get(marketService + '/cart/' + $localStorage.marketCartId + '/add/' + productId);
        console.log('adding to cart: ' + productId);
    }

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

    $scope.getInfo = function (pid, title, price) {
        console.log('getInfo called with {' + pid + ', ' + title + ', ' + price + '}');
        $localStorage.marketProduct = {id: pid, title: title, price: price};
        console.log('info request called with {' + $localStorage.marketProduct.id + ', ' + $localStorage.marketProduct.title + ', ' + $localStorage.marketProduct.price + '}');
        $location.path('/product_info');
    }


});