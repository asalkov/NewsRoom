var xAuthTokenHeaderName = 'x-auth-token';

var newsRoomApp = angular.module('newsRoomApp', ['ngRoute', 'ngCookies', 'newsRoomApp.services']);

newsRoomApp.config()

var services = angular.module('newsRoomApp.services', ['ngResource']);

services.factory('LoginService', function($resource) {

    return $resource(':action', {},
        {
            authenticate: {
                method: 'POST',
                params: {'action' : 'authenticate'},
                headers : {'Content-Type': 'application/x-www-form-urlencoded'}
            }
        }
    );
});