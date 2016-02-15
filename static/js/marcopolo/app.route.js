(function () {
    'use strict';

    angular
        .module('marcopolo')
        .config(config);

    config.$inject = ['$routeProvider'];
    function config($routeProvider) {
        $routeProvider

            .when('/', {
                templateUrl: 'js/marcopolo/person/person-log.html',
                controller: 'personLogCtrl',
                controllerAs: 'personLog'
            })

            .when('/persons/:personId/marquepages', {
                templateUrl: 'js/marcopolo/marquepage/marquepage-list.html',
                controller: 'marquepageListCtrl'
            })

            .when('/persons/:personId/marquepages/new', {
                templateUrl: 'js/marcopolo/marquepage/marquepage-new.html',
                controller: 'marquepageNewCtrl'
            })

            .when('/persons/:personId/marquepages/:marquepageId', {
                templateUrl: 'js/marcopolo/marquepage/marquepage-detail.html',
                controller: 'marquepageDetailCtrl'
            })

            .when('/persons/new', {
                templateUrl: 'js/marcopolo/person/person-new.html',
                controller: 'personNewCtrl',
                controllerAs: 'personNew'
            })

            .when('/persons/:personId', {
                templateUrl: 'js/marcopolo/person/person-detail.html',
                controller: 'personDetailCtrl',
                controllerAs: 'personDetail'
            })

            .when('/help_fr', {
                templateUrl: 'js/marcopolo/commons/helpfr.html',
                controller: 'globalCtrl'
            })

            .when('/help_en', {
                templateUrl: 'js/marcopolo/commons/helpen.html',
                controller: 'globalCtrl'
            })

            .when('/aboutUs', {
                templateUrl: 'js/marcopolo/commons/aboutUs.html',
                controller: 'globalCtrl'
            })

            .otherwise({
                redirectTo: '/'
            });
    } // function

})();

