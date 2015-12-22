'use strict';

angular.module('doppelt.welcome', ['ngRoute', 'doppelt.communicationService'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/welcome', {
    templateUrl: 'welcome/welcome.html',
    controller: 'WelcomeCtrl'
  });
}])

.controller('WelcomeCtrl', ['$scope', '$location', 'Communication', function($scope, $location, Communication) {
  $scope.test = 'Hello World!';
  
  $scope.onConnect = function() {
    console.log('welcome.onConnect');
    Communication.connect();
  };
  
  $scope.onSend = function() {
    console.log('welcome.onSend');
    Communication.send('xyz');
  };
  
  $scope.onDisconnect = function() {
    console.log('welcome.onDisconnect');
    Communication.disconnect();
  };
  
}]);
