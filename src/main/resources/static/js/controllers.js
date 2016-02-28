'use strict';

myApp.controller("todoCtrl" ,function ($scope, $http) {
    $scope.newTask = "";
    $scope.tasksList = [];
    $http.get('/tasks').success(function(tasks) {
      $scope.tasksList = tasks;
    });

    $scope.addTask = function() {
        var task = {string: $scope.newTask, done: false};
        $http.post('/tasks/add', task).success(function(id){
            task.id = id;
            $scope.tasksList.push(task);
        });
        $scope.newTask = "";
    }

    $scope.removeTask = function(task) {
        $http.post('/tasks/remove', task).success(function(){
            $scope.tasksList.splice( $scope.tasksList.indexOf(task), 1 );
        });
    }

    $scope.updateTask = function(task) {
        task.done = !task.done;
        $http.post('/tasks/update', task);
    }
});
