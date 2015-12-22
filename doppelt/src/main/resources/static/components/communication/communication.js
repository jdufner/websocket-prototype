'use strict';

angular.module('doppelt.communicationService', ['ngResource'])

.factory('Communication', ['$q', function($q){
  
  var listener = $q.defer();
  var stompClient;
  
  function connect() {
    var socket = new SockJS('/hello');
    stompClient = Stomp.over(socket);
    var headers = {
      login: 'myLogin',
      passcode: 'myPasscode',
      // additional header
      'client-id': 'myClientId'
    };
    
    function onConnect(frame) {
      console.log('Connected: ' + frame);
      stompClient.subscribe('/topic/greetings', receive);
    };
    
    function onConnectError(error) {
      console.log('Connectionerror: ' + error);
    };
    
    stompClient.connect('', '', onConnect, onConnectError);
  }
  
  function disconnect() {
    if (stompClient != null) {
      stompClient.disconnect();
    }
    console.log('Disconnected!');
  }
  
  function send(message) {
    var headers = {};
    
    stompClient.send('/app/hello', headers, JSON.stringify({'name': message}));
  }
  
  function receive(greeting) {
    var message = JSON.parse(greeting.body).content;
    console.log(message);
    listener.notify(message);
  }
  
  return {
    connect: connect,
    disconnect: disconnect,
    send: send
  }
  
}]);
