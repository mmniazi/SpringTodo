'use strict';

var myApp = angular.module('myApp', ['ngRoute', 'ngMaterial']);

myApp.config(function($routeProvider) {
    $routeProvider.when('/', {
        templateUrl: 'templates/todo.html',
        controller: 'todoCtrl'
    	});

    $routeProvider.otherwise({
        redirectTo: '/'
    });
});
