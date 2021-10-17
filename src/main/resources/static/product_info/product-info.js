angular.module('market-app').controller('productInfoController', function ($scope, $http, $location, $localStorage) {
        $scope.commentsForm = {comment: null};

        let updateComments = function (pid) {
            console.log('updateComments called for pid: ' + pid);
            $http({
                url: marketService + '/products/' + pid + '/comments',
                method: 'GET'
            }).then(function (response) {
                console.log(response);
                $scope.comments = response.data.comments;
                $scope.canAddComment = response.data.canAddComment;
            });
        };
        console.log('$scope.product: ' + $localStorage.marketProduct);
        $scope.product = $localStorage.marketProduct;
        updateComments($localStorage.marketProduct.id);

        $scope.goBack = function () {
            $location.path('/catalog');
        }

        $scope.createComment = function () {
            console.log('posting: ' + $scope.commentsForm.comment);
            $http({
                url: marketService + '/products/' + $scope.product.id + '/comments/add',
                method: 'POST',
                data: $scope.commentsForm.comment
            }).then(function successCallback(response){
                $scope.commentsForm.comment = null;
                updateComments($scope.product.id)
            }, function failureCallback(response) {
                console.log(response);
                alert(response.data);
            });
        }
    });