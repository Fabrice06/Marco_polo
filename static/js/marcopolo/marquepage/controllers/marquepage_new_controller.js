/** 
* Function Controller for new Marque page
* @author Fred
* @copyright les Huit salopards
*/
(function () {
    'use strict';

    angular
        .module('marcopolo')
        .controller('marquepageNewCtrl',
                    marquepageNewCtrl);
    
    marquepageNewCtrl.$inject = ['$scope',
                                 '$resource',
                                 '$location',
                                 'MarquepageNew',
                                 '$routeParams'];
    function marquepageNewCtrl($scope,
                                $resource,
                                $location,
                                MarquepageNew,
                                $routeParams) {
        
        // recuperation id de l'utilisateur
        $scope.personId = $routeParams.personId;
        console.log("récup idPerson "+$scope.personId);
        // definition du Mqp
        // $scope.mqpNewModel = {url: url, nameMqp: nameMqp};
        
        /** 
        * Validation de la creation du nouveau Mqp.
        * @param Données du Formulaire Marquepage
        * Adresse uri du Marque page
        * Nom du Marque page
        */
        $scope.validate = function(mqpNewModel) {

        	console.log("id : "
                        + $scope.personId+" lien : "
                        +mqpNewModel.url+" nom : "
                        +mqpNewModel.nameMqp);
            
            // Post du formulaire
        	MarquepageNew.save({ id1:$scope.personId},
        						{lien:mqpNewModel.url,
                                nom:mqpNewModel.nameMqp
                               }, 
        	/** 
            * Succes du POST avec les données du nouveau Marque page.
            * On récupère le retour de la Base de données qui correspond
            * a l'id du nouveau marque page.
            * Puis on bascule sur la page Detail pour ajouter les
            * tags de ce nouveau marque page.
            * @param success
            */
        	function (success) {
        		//on récupère l'id du mqp créé
        		var jsonMqp = success.links[0].href
        		var idMqpArray=jsonMqp.split('/');
        		var idMqp=idMqpArray[4];
        		console.log(idMqp);

        		// Redirection vers la page détail du Mqp      		
        		$location.path('/persons/'
                               +$scope.personId
                               +'/marquepages/'
                               +idMqpArray[4]);
        		console.log($location.path());
        	},
                               
        	/**
            * Echec de l'envoi du POST vers la Base de Données
            * @param : pData et headers
            */
        	function (pData, headers) {
        		console.log("marquepageLogCtl Query Echec");
        	})
        };
        
        /**
        * Utilisation du bouton Annuler :
        * Retour sur la liste des marque page.
        */ 
        $scope.cancel = function() {
            //console.log("Canceling" + $scope.mqpNewModel.nameMqp + $scope.mqpNewModel.url);

            var nUrlArray=$location.url().split('/');
            var id = nUrlArray[2];
            $location.path('/persons/'
                           +id
                           +'/marquepages').replace();
        };                
        
        /**
        * Utilisation du bouton Déconnexion :
        * On retourne sur la page de Connexion.
        */
        $scope.onProfil = function () {

            console.log("clic marquepageDetailCtrl déconnexion en cours");
            nProfilLink = nUrlArray[1]
                            +'/'
                            +nUrlArray[2];
            $location.path(nProfilLink).replace();
        };

       /* // clic sur le bouton profil
        $scope.onExit = function () {
            $location.path('/').replace();
        };*/
        
    } // function

})();