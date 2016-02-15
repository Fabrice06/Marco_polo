(function () {
    'use strict';

    angular
        .module('marcopolo')
        .controller('globalCtrl', globalCtrl);

    globalCtrl.$inject = ['$scope', '$location', '$rootScope'];
    function globalCtrl($scope, $location, $rootScope) {
        
        ;
      
		// clic sur aide dans le footer
        $scope.onHelp = function() {
			console.log("clic sur Help");
            $rootScope.nHelpPreviousUrl = $location.path();
            console.log("url d'où je viens : " + $rootScope.nHelpPreviousUrl);
			
			if ($rootScope.lang == '2') {
				$location.path('/help_en').replace();
			}else {
				$location.path('/help_fr').replace();
			}
            
        };
        
		// clic sur retour pour aide
        $scope.onExit = function() {
            console.log("clic sur Retour Help");
            
            console.log("retour sur l'url précédente : " + $rootScope.nHelpPreviousUrl);
            $location.path($rootScope.nHelpPreviousUrl).replace();
        }
        
		// clic sur qui sommes nous
        $scope.aboutUs = function() {
        	console.log("clic sur aboutUs");
            $rootScope.nAboutUsPreviousUrl = $location.path();
            console.log("url d'où je viens : " + $rootScope.nAboutUsPreviousUrl);
            $location.path('/aboutUs').replace();
            
        };
		
		// clic sur sortir de qui sommes nous
		$scope.onExit2 = function() {
            console.log("clic sur Retour AboutUs");
            
            console.log("retour sur l'url précédente : " + $rootScope.nAboutUsPreviousUrl);
            $location.path($rootScope.nAboutUsPreviousUrl).replace();
        }
           
    }
    
    
})();