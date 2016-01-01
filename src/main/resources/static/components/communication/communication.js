'use strict';

angular.module('doppelt.communicationService', ['ngResource'])

.factory('Communication', ['$q', function($q){
  
  var listener;
  var stompClient;
  
  function connect() {
    listener = $q.defer();
    var options = {
        debug: false
      };
    var socket = new SockJS('/hello', undefined, options);
    stompClient = Stomp.over(socket);
    var headers = {
      login: 'mylogin',
      passcode: 'mypasscode',
      'client-id': 'my-client-id',
      'heart-beat': '25000, 25000'
    };
    
    function onConnect(frame) {
      //console.log('Connected: ' + frame);
      stompClient.subscribe('/topic/greetings', receive);
    };
    
    function onConnectError(error) {
      //console.log('Connectionerror: ' + error);
    };
    
    stompClient.heartbeat.outgoing = 25000;
    stompClient.heartbeat.incoming = 25000;
    stompClient.debug = null;
    stompClient.connect('', '', onConnect, onConnectError);
  }
  
  function disconnect() {
    if (stompClient != null) {
      stompClient.disconnect();
      stompClient = null;
      listener = null;
    }
    //console.log('Disconnected!');
  }
  
  function send(message) {
    var headers = {};
    stompClient.send('/app/hello', headers, JSON.stringify({'content': message}));
  }
  
  function receive(stompMessage) {
    var message = JSON.parse(stompMessage.body);
    //console.log(message);
    listener.notify(message);
  }
  
  function promise() {
    return listener.promise;
  }
  
  return {
    connect: connect,
    disconnect: disconnect,
    send: send,
    listener: promise
  }
  
}]);
