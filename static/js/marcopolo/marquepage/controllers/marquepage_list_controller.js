(function () {
	'use strict';

	var myModuleController = angular.module('marcopolo');

	myModuleController.controller('marquepageListCtrl',['$scope','MarquepageList','Marquepage','$routeParams','$location','$window', function($scope,MarquepageList,Marquepage,$routeParams,$location,$window){

           
        // pour test ------------------------------
        var nUrlArray = $location.url().split('/');
        console.log("marquepageListCtrl url " + $location.url());

        var nAddLink = "";
        var nProfilLink = "";
    
        // Récuperation des marquepages d'une personne
        
        $scope.listMqp = MarquepageList.query({id:nUrlArray[2]},function (success){

	        },function (pData, headers) { // échec
	        	window.alert("Connexion impossible");
	        	console.log("marquepageListCtrl query échec");
	   });
       
               
        //aciver les liens pour ouverture dans une autre fenêtre
        
        $scope.openLink = function(nAddLink){        	
        	//$window.location.href = nAddLink.lien; 
        	var protocole = nAddLink.lien.substring(0,4);
        	if (protocole == 'http'){
        		window.open(nAddLink.lien);        	
        	}else{
        		window.open('http://' + nAddLink.lien);
        	}
        }

        // clic sur le bouton ajouter
        
        $scope.onAdd = function () {         

        	var nAddLink = $location.url();
        	console.log(nAddLink);
        	$location.path(nAddLink +'/new').replace();
        	console.log($location.path());
        };
        
        // clic sur le bouton éditer
        $scope.onUpdate = function (findIdMqp) {
        	
        	console.log("clic modif "); 
        	
        	nAddLink = $location.url();
        	console.log(nAddLink);
        	var id =findIdMqp.links[0].href;
        	var nIdArray = id.split('/');
        	console.log('---------- identification id marquepage: ' + nIdArray[4]);        	
        	$location.path(nAddLink +'/'+ nIdArray[4]).replace();
        	console.log($location.path());
        };
        
        // bouton rechercher
        $scope.search = ""; 

        // clic sur le bouton supprimer
        $scope.onDelete = function (findIdMqp) {

        	console.log("clic X ");  
       	        	
        	//on récupère l'id de la personne passé en paramètre de l'url        	
        	$scope.nPersonId = $routeParams.personId;
        	//var requestUri = 'persons/'+ $scope.nPersonId +'/marquepages';
        	var requestUri = 'marquepages';
			console.log('---------- requestUri: ' + requestUri);
        	
        	//on récupère l'id du marquepage sélectionné   	
        	var id =findIdMqp.links[0].href;
        	console.log('url du marquepage ' + id);        	
        	var nIdArray = id.split('/');
        	var mqpToDelete = nIdArray[4];        	
        	console.log('---------- identification id marquepage: ' + nIdArray[4]);        	

			//on passe en tant que paramètre la deuxième partie de l'URL courante + l'id du marquepage 
			//nécessaire pour faire appel au HEADER du service $resource "Marquepage"
			
			Marquepage.delete({uri:requestUri,id:mqpToDelete},function (listMqp){
				//en cas de succès on met à jour la liste dans la vue
				$scope.listMqp.splice($scope.listMqp.indexOf(findIdMqp),1);				
				console.log('route du marquepage supprimé : ' + id);
				
			});
        }; 
      
	
	// clic sur le bouton déconnexion
        $scope.onProfil = function () {

            console.log("clic marquepageListCtrl déconnexion en cours"); 
            nProfilLink = nUrlArray[1]+'/'+nUrlArray[2];
            $location.path(nProfilLink).replace();           
        };

        // clic sur le bouton profil
        $scope.onExit = function () {

            $location.path('/').replace();
        };

	}]);

})();