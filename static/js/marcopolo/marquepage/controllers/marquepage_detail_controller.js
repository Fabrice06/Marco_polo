(function () {
    'use strict';

    angular
        .module('marcopolo')
        .controller('marquepageDetailCtrl', marquepageDetailCtrl);


    marquepageDetailCtrl.$inject = ['$scope', '$resource', '$location', 'Marquepage', 'Tag', 'Cle', '$route'];
    function marquepageDetailCtrl($scope, $resource, $location, Marquepage, Tag, Cle, $route) {
		
        // Récupération en tableau de l'url coupé au /
        var nUrlArray = $location.url().split('/');
        
        // ressource Persons
        var nPersons = nUrlArray[1];
        // id de la personne
        var nIdPerson = nUrlArray[2];
        console.log("idPersonne : "+nPersons + "/"+ nIdPerson);
        
        // ressource Marquepages
        var nMqp = nUrlArray[3];
        // id du marquepage courant
        var nIdMqp = nUrlArray[4];
         console.log("idMqpCourant : "+nMqp+"/"+nIdMqp);
        
        // ressource Tags
        var nTag = 'tags';
        
        console.log("Langue : "+$scope.lang);

        
        // Booléen de controle d'informations complétes
        $scope.invalidInfos = false;
        

        // Récuperation et affichage des informations de la ressource person selectionnée
        $scope.marquepageDetailModel = Marquepage.query(
            {
                uri:nMqp,
                id:nIdMqp
            },
            function (pMarquePage) { // OK
                console.log("marquepageDetailCtrl get query " + pMarquePage.lien);
            },
            function (pData, headers) { // échec
                console.log("marquepageDetailCtrl get query échec");
            }
        );
     
         // clic sur le bouton deconnexion
        $scope.onExit = function () {
			console.log("clic marquepageDetailCtrl déconnexion en cours");
            $location.path('/').replace();
        };
		
		// clic sur le bouton back
		$scope.onBack = function () {
			console.log("clic marquepageDetailCtrl Back en cours");
			$location.path('/persons/'+ nIdPerson +'/marquepages');			
		};
        
        // clic sur le bouton supprimer
		$scope.onDelete = function () {
			console.log("clic marquepageDetailCtrl Suppression en cours");	
			
			Marquepage.delete (
				{
					uri:nMqp,
					id:nIdMqp
				},
				function (success) { // OK
					console.log('marquepageDetailCtrl delete query ok id : ' + nIdMqp);
					$scope.onBack();
				},
				function (pData, headers) { // échec
					console.log("marquepageDetailCtrl delete query échec");
				}
			)
		};
        
        // clic sur le bouton modifier
		$scope.onUpdate = function (pMarquepageDetail) {
			console.log("clic marquepageDetailCtrl Modification en cours");
            console.log("lien : "+pMarquepageDetail.lien+" nom : "+pMarquepageDetail.nom);
            
            var lien = pMarquepageDetail.lien;
            var nom = pMarquepageDetail.nom;
            var cle = pMarquepageDetail.cle;
            var valeur = pMarquepageDetail.valeur;
                
            if (cle && valeur) {
                $scope.onAdd(pMarquepageDetail);
            }
            
            if (lien && nom) {
                $scope.invalidInfos = false;
                Marquepage.update (
                    {
                        uri:nMqp,
                        id:nIdMqp
                    },
                    {
                        lien:lien,
                        nom:nom
                    },
                    function (success) { // OK
                        console.log('marquepageDetailCtrl update query ok id : ' + nIdMqp);
                        $scope.onBack();
                    },
                    function (pData, header) { // échec
                        console.log('marquepageDetailCtrl update query échec');
                    }
                )
            }else{
                console.log("Manque le champ nom ou lien");
                $scope.invalidInfos = true;
            }    
		};
        
        // clic sur le bouton ajouter un tag
        $scope.onAdd = function (pTag) {
			console.log('clic marquepageDetailCtrl Add Tag');
            console.log("/"+nMqp+"/"+nIdMqp+"/"+nTag+"?cle="+pTag.cle+"&valeur="+pTag.valeur);
            
            if (pTag.cle && pTag.valeur){
                Tag.save (
                    {
                        id:nIdMqp
                    },
                    {
                        cle:pTag.cle,
                        valeur:pTag.valeur,
                        idLangue:$scope.lang
                    },
                    function (success) { // OK
                        console.log('marquepageDetailCtrl add Tag query ok id : ' + nIdMqp);
                        $route.reload();					
                    },
                    function (pData, header) { // échec
                        console.log('marquepageDetailCtrl add Tag query échec');
                    }
                )
            }else{
                alert('Les deux champs sont obligatoire pour créer un nouveau tag');
            }
			
            
		};
        
        // clic sur le bouton supprimer un tag
        $scope.onDel = function (pTag) {
			console.log('clic marquepageDetailCtrl del Tag');
            
            var hrefTag = pTag.links[0].href;
            var infoTag = hrefTag.split('/');
            var idTag = infoTag[4];
            
            console.log('idTag : '+ idTag);
                    
			Marquepage.delete (
				{
                    uri:nTag,
					id:idTag
				},
				function (success) { // OK
					console.log('marquepageDetailCtrl del Tag query ok id : ' + idTag);
                    $scope.marquepageDetailModel.tags.splice($scope.marquepageDetailModel.tags.indexOf(pTag),1);
				},
				function (pData, header) { // échec
					console.log('marquepageDetailCtrl del Tag query échec');
				}
			)
		};
        
        // clic sur mon profil
        $scope.onProfil = function () {
            console.log("clic mon profil"); 
            $location.path(nPersons+"/"+nIdPerson).replace();           
        };
        
        
        $scope.cles = Cle.query (
            {
                id:$scope.lang
            },
            function (success) { // OK
                console.log('marquepageDetailCtrl liste cle query ok');	
            },
            function (pData, header) { // échec
                console.log('marquepageDetailCtrl liste cle query échec');
            }
        )     
    
    } // function

})();