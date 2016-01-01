'use strict';

angular.module('doppelt.welcome', ['ngRoute', 'doppelt.communicationService'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/welcome', {
    templateUrl: 'welcome/welcome.html',
    controller: 'WelcomeCtrl'
  });
}])

.controller('WelcomeCtrl', ['$scope', '$location', '$anchorScroll', 'Communication', 
                            function($scope, $location, $anchorScroll, Communication) {
  var messageListElement = angular.element(document.querySelector('#messageList'));
  
  $scope.onConnect = function() {
    Communication.connect();
    $scope.connect();
    Communication.listener().then(
      function(message){
        //console.log('Success:' + message);
      },
      function(error) {
        //console.log('Error: ' + error);
      },
      function(update) {
        //console.log('Update: ' + update);
        messageListElement.append('<div id="panel_' + update.id + '" class="panel panel-default"><div class="panel-body"><span class="badge">' + update.id + '</span>' + update.content + '</div></div>');
        $anchorScroll('panel_' + update.id);
      }
    );
  };
  
  $scope.onSend = function() {
    Communication.send($scope.message);
    $scope.message = null;
  };
  
  $scope.onDisconnect = function() {
    Communication.disconnect();
    $scope.disconnect();
  };

  $scope.connect = function() {
    $scope.connected = true;
    $scope.connectBtnState = true;
    $scope.sendBtnState = false;
    $scope.disconnectBtnState = false;
    $scope.messageInputState = false;
  }
  
  $scope.disconnect = function() {
    $scope.connected = false;
    $scope.connectBtnState = false;
    $scope.sendBtnState = true;
    $scope.disconnectBtnState = true;
    $scope.messageInputState = true;
  }
  
  $scope.disconnect();
  
}]);
