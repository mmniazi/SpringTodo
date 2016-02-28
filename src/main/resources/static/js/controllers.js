'use strict';

myApp.controller("todoCtrl" ,function ($scope, $http, connectionService) {
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

//    connectionService.webSocket.onmessage = function(message) {
//        var change = JSON.parse(message.data);
//
//        switch(change.type) {
//            case add:
//                $scope.tasksList.push(change.task);
//                break;
//            case remove:
//                $scope.tasksList.splice( $scope.tasksList.indexOf(change.task), 1 );
//                break;
//            case update:
//                var index = $scope.tasksList.findIndex(function(task) {
//                    return task.id == change.task.id
//                });
//                $scope.tasksList[index] = change.task;
//                break;
//        }
//
//        $scope.$apply();
//    };
//
//    connectionService.webSocket.onclose = function() {
//        console.log("Error in websocket")
//    }
});
