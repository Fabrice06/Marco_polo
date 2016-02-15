(function () {
    'use strict';

    angular.module('marcopolo')
        .factory('MarquepageList', MarquepageList);

    MarquepageList.$inject = ['$resource'];
    function MarquepageList ($resource) {

    	return $resource(
    			'/persons/:id/marquepages',
    			{},{ 
    				'query': 	{method:'GET',		isArray: true},
    				'get': 	    {method:'GET',		isArray: false},
    				'save': 	{method:'POST', 	isArray: false},
    				'update': 	{method:'PUT', 		isArray: false},
    				'delete': 	{method:'DELETE', 	isArray: false}
    			}
    	);

    } 

})();
