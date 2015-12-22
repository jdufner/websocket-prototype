'use strict';

angular.module('doppelt.welcome', ['ngRoute', 'doppelt.communicationService'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/welcome', {
    templateUrl: 'welcome/welcome.html',
    controller: 'WelcomeCtrl'
  });
}])

.controller('WelcomeCtrl', ['$scope', '$location', 'Communication', function($scope, $location, Communication) {
  var messageListElement = angular.element(document.querySelector('#messageList'));
  
  $scope.test = 'Hello World!';
  
  $scope.onConnect = function() {
    Communication.connect();
    Communication.listener().then(
      function(message){
        //console.log('Success:' + message);
      },
      function(error) {
        //console.log('Error: ' + error);
      },
      function(update) {
        //console.log('Update: ' + update);
        messageListElement.append('<p>' + update + '</p>');
      }
    );
  };
  
  $scope.onSend = function() {
    Communication.send('xyz');
  };
  
  $scope.onDisconnect = function() {
    Communication.disconnect();
  };
  
}]);
