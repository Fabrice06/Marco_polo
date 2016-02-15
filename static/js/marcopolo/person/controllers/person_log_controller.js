(function () {
    'use strict';

    angular
        .module('marcopolo')
        .controller('personLogCtrl', personLogCtrl);

    personLogCtrl.$inject = ['$scope', '$location', '$filter', 'PersonZ', 'Crypto', 'Hateoas', 'Session', 'Translate', '$rootScope'];
    function personLogCtrl($scope, $location, $filter, PersonZ, Crypto, Hateoas, Session, Translate, $rootScope) {

        /* jshint validthis: true */
        var vm = this;
        vm.onSubmit = onSubmit;
        vm.onNew = onNew;
        vm.onLost = onLost;
        vm.onTestA = onTestA;
        vm.onTestB = onTestB;
        vm.onTestC = onTestC;

        Session.clear();
		//TraducF.changeLanguage('french');

        vm.emptyLogin = false;
        vm.incorrectLogin = false;

        // clic sur le bouton se connecter
        function onSubmit(pPersonLog) {
        	
        	if (angular.isDefined(pPersonLog)){

	            var nMail = pPersonLog.mail;
	            var nMdp = Crypto.SHA1(pPersonLog.mdp);
	            //var nMdp = pPersonLog.mdp;
	
	            // faire un check regex ????
	
	            var nParams = {
	                mail: nMail,
	                mdp: nMdp
	            };
	            console.log("personLogCtrl " + JSON.stringify(nParams));
	
	            PersonZ.query('/persons', nParams).then(
	                function successCallback(pResponse) { // OK pResponse est le retour du backEnd
	
	                    // this callback will be called asynchronously
	                    // when the response is available
	                    if (angular.isUndefined(pResponse.data.links)) {
	                        console.log("personLogCtrl query ok but Person don't exist");
	                        vm.incorrectLogin = true;
	
	                    } else {
	                        console.log("personLogCtrl save ok " + JSON.stringify(pResponse.data));			
							
							if(pResponse.data.langue == "english") {
								$scope.changeLangue("english");
								$rootScope.lang = 2;
							}else{
								$scope.changeLangue("french");
								$rootScope.lang = 1;
							}
                            						
	                        Session.setCurrent(pResponse.data.idperson, nMdp);
	
	                        // ce service fourni directement les liens hateoas sous forme de clé/valeur
	                        Hateoas.setLinks(pResponse.data.links);
	
	                        $location.url(Hateoas.getUri("marquepages")).replace();
	                    } // else
	
	                },
	                function errorCallback(pResponse) {
	
	                    // called asynchronously if an error occurs
	                    // or server returns response with an error status.
	                    console.log("personLogCtrl query échec");
	                    vm.incorrectLogin = true;
	                }
	            );
        	} else {
        		vm.emptyLogin = true;
        	}
        }

        // clic sur le bouton nouvel utilisateur
        function onNew() {

            $location.url("/persons/new").replace();
        }
        
        //$scope.doSomething = function() {
        //	focus('useremail');
        //};

        // clic sur le lien mot de passe perdu
        function onLost(pPersonLog){
			       	
            alert($filter('translate')('PERSON_MESSAGE'));
        }


        // pour test uniquement ---------------------------------------------------
            function onTestA(pIdPerson) {
                // direct vers persons-detail
                $location.path('/persons/' + pIdPerson).replace();
            }
            function onTestB(pIdPerson) {
                // direct vers marquepages-detail
                $location.path('/persons/' + pIdPerson + '/marquepages/2').replace();
            }
            function onTestC(pIdPerson) {
                // direct vers marquepages-list
                $location.path('/persons/' + pIdPerson + '/marquepages').replace();
            }
        // pour test uniquement ---------------------------------------------------
    } // function    


    //angular
    //	.module('marcopolo')
    //	.factory('focus', function($timeout, $window) {
    //		return function(id) {
	 //         // timeout makes sure that is invoked after any other event has been triggered.
	 //         // e.g. click events that need to run before the focus or
	 //         // inputs elements that are in a disabled state but are enabled when those events
	 //         // are triggered.
	 //         $timeout(function() {
	 //           var element = $window.document.getElementById(id);
	 //           if(element)
	 //             element.focus();
	 //         });
    //		};
    //	})

})();