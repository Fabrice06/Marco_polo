(function () {
    'use strict';

    angular
        .module('marcopolo')
        .controller('personLogCtrl', personLogCtrl);

    personLogCtrl.$inject = ['$scope', '$location', '$filter', 'PersonZ', 'Crypto', 'Hateoas', 'Session', '$rootScope'];
    function personLogCtrl($scope, $location, $filter, PersonZ, Crypto, Hateoas, Session, $rootScope) {

        /* jshint validthis: true */
        var vm = this;
        vm.onSubmit = onSubmit;
        vm.onNew = onNew;
        vm.onLost = onLost;

        Session.clear();

        vm.emptyLogin = false;
        vm.incorrectLogin = false;

        // clic sur le bouton se connecter
        function onSubmit(pPersonLog) {
        	
        	if (angular.isDefined(pPersonLog)){

	            var nMail = pPersonLog.mail;
	            var nMdp = Crypto.SHA1(pPersonLog.mdp);
	
	            // faire un check regex ????

	            Session.setMdp(nMdp);

	            var nParams = {
                    user: 0,
	                mail: nMail,
	                mdp: nMdp
	            };

	            PersonZ.query('/persons', nParams).then(
	                function successCallback(pResponse) { // OK pResponse est le retour du backEnd

	                    if (angular.isUndefined(pResponse.data.links)) {
	                        vm.incorrectLogin = true;
	
	                    } else {
                            Session.setCurrent(pResponse.data.idPerson, nMdp);

                            // ce service fourni directement les liens hateoas sous forme de clé/valeur
                            Hateoas.setLinks(pResponse.data.links);

							if(pResponse.data.langue == "english") {
								$scope.changeLangue("english");
								$rootScope.lang = 2;
							}else{
								$scope.changeLangue("french");
								$rootScope.lang = 1;
							}

	                        $location.url(Hateoas.getUri("marquepages")).replace();
	                    } // else
	
	                },
	                function errorCallback(pResponse) {
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

        // clic sur le lien mot de passe perdu
        function onLost(pPersonLog){
			       	
            alert($filter('translate')('PERSON_MESSAGE'));
        }
    } // function

})();