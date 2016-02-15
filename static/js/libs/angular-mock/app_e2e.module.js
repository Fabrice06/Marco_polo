(function() {
    'use strict';

	angular.module('Pocretform', [
        /*
         * Everybody has access to these.
         * We could place these under every feature area,
         * but this is easier to maintain.
         */ 
         'Pocretform.core',

        /*
         * httpbackend
         */
        'ngMockE2E',

        /*
         * Feature areas
         */
         'Pocretform.Controllers.Global',		 
		 'Pocretform.User.modules',
		 'Pocretform.Formation.modules',
         'Pocretform.Session.modules',
		 'Pocretform.Menu.modules',
		 'Pocretform.Notification.Service',
		 'Pocretform.Security.modules'
	]);
})();