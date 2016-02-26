'use strict';

myApp.controller("todoCtrl" ,function ($scope, $http) {
    $scope.newTask = "";
    $scope.todoList = [];
    $http.get('/tasks/').success(function(data) {
      $scope.todoList = data;
    });

    $scope.addTodo = function() {
        $scope.todoList.push({string: $scope.newTask, done: false});
        $scope.newTask = "";
        $http.post('/tasks', $scope.todoList);
    }

    $scope.removeTodo = function(task) {
        $scope.todoList.splice( $scope.todoList.indexOf(task), 1 );
        $http.post('/tasks', $scope.todoList);
    }
});
