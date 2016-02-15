(function () {
    'use strict';

    angular.module('marcopolo')
        .factory('Person', Person)
        .factory('CurrentPerson', CurrentPerson);

    Person.$inject = ['$resource'];
    function Person ($resource) {

        return $resource(
            '/:uri/:id',
            {},
            { // return $resource('/users/:userId', {userId:'@id'}  ???
                'query': 	{method:'GET', isArray: false},
                'save': 	{method:'POST', isArray: false},
                'update': 	{method:'PUT', isArray: false},
                'delete': 	{method:'DELETE', isArray: false}
            }
        );

    } // function

    // sauvegarde de la ressource: son utilisation reste à vérifier et à valider
    function CurrentPerson() {
        var nPerson = {};

        return {
            get: function () {
                return nPerson;
            },
            set: function (pPerson) {
                nPerson = pPerson;
            }
        }
    } // function

})();