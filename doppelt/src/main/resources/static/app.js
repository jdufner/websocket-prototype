'use strict';

// Declare app level module which depends on views, and components
angular.module('doppelt', [
  'ngRoute',
  'doppelt.welcome'
]).
config(['$routeProvider', function($routeProvider) {
  $routeProvider.otherwise({redirectTo: '/welcome'});
}]);
