//(function () {
//    'use strict';
//
//    angular
//        .module('marcopolo')
//        .config(function ($translateProvider){
//			$translateProvider.translations('english', {
//				PERSON_LOG_WELCOME1: 'Welcome to Marco Polo',
//				PERSON_LOG_WELCOME: "Join us and handle your bookmarks in your own way !",
//				BUTTON_LANG_EN: 'English',
//				BUTTON_LANG_FR: 'French',
//				PERSON_LOG_TITLE: 'Get identified and retrieve your bookmarks list',
//				PERSON_LOG_MAIL: "Email address",		
//				PERSON_LOG_VALID: "Thank you for indicating a valid email address",
//				PERSON_LOG_PASS: "Password",
//				PERSON_LOG_MESSAGE: "I forgot my password !",
//				PERSON_LOG_NEW: "I want to create an account",
//				PERSON_LOG_CONNECT: "Log in",				
//				PERSON_LOG_LOGIN_ERROR: "login and/or password is not correct",		
//				PERSON_LOG_EMPTY_LOGIN_ERROR : "Please specify an e-mail address and a password",
//				PERSON_LOG_MINLENGTH: "The password must contain at least 3 caracters",
//				PERSON_LOG_MAXLENGTH: "The password must not exceed 20 caracters",
//				PERSON_LOG_WHO: "About us",
//				PERSON_LOG_CONTACT: "Contact us",
//				PERSON_DETAIL_PROFILE: "Update my profile",
//				PERSON_DETAIL_LANGUAGE: "Language",
//				PERSON_MESSAGE: 'TEST ENGLISH',
//
//                PERSON_NEW_PROFILE: "Fill up your profile form",
//                PERSON_NEW_MDP: "Enter your password",
//                PERSON_NEW_CONFIRM: "Confirm your password",
//                PERSON_NEW_MAIL: "Enter your e-mail",
//                PERSON_NEW_LANG: "Select your favorite language",
//
//                MQP_NEW_TITLE: 'My new bookmark',
//                
//                MQP_DET_TITLE: 'My bookmark',
//                MQP_DET_NAME: 'Name of bookmark :',
//                MQP_DET_LINK: 'Link of bookmark :',
//                MQP_DET_KEY: 'Key',
//                MQP_DET_VALUE: 'Value',
//                
//                MQP_LIST_NAME: 'Names',
//                MQP_LIST_LINKS: 'Links',
//                MQP_LIST_FILTER: 'Filter',
//                
//                REQUIRED: "This field is required",
//                URL: 'This field must be an URL',
//                SEARCH: 'Search',
//                
//				ADD: "Add",
//				PROFILE: "My profile",				
//				LOGOUT: "Log out",
//				CANCEL: "Cancel",
//				DELETE: "Delete",
//				UPDATE: "Update",
//                CREATE: 'Create',
//                HELP: 'Help'
//			});
//			
//			$translateProvider.translations('french', {
//				PERSON_LOG_WELCOME1: 'Bienvenue sur Marcopolo',
//				PERSON_LOG_WELCOME: "Rejoignez-nous pour gérer vos marque-pages selon vos envies !",
//				BUTTON_LANG_EN: 'Anglais',
//				BUTTON_LANG_FR: 'Français',
//				PERSON_LOG_TITLE: 'Identifiez-vous et accédez à la liste de vos marque-pages',
//				PERSON_LOG_MAIL: "Adresse email",
//				PERSON_LOG_VALID: "Merci d'indiquer une adresse e-mail valide",
//				PERSON_LOG_PASS: "Mot de passe",
//				PERSON_LOG_MESSAGE: "J'ai oublié mon mot de passe !",
//				PERSON_LOG_NEW: "Je veux créer un compte ",
//				PERSON_LOG_CONNECT: "Je me connecte",				
//				PERSON_LOG_LOGIN_ERROR: "Le login et/ou mot de passe est incorrect",
//				PERSON_LOG_EMPTY_LOGIN_ERROR : "Vous devez indiquer une adresse e-mail et un mot de passe",
//				PERSON_LOG_MINLENGTH: "Le mot de passe doit contenir au minimum 3 caract&egrave;res",
//				PERSON_LOG_MAXLENGTH: "Le mot de passe doit contenir au maximum 20 caract&egrave;res",
//				PERSON_LOG_WHO: "Qui sommes nous ?",
//				PERSON_LOG_CONTACT: "Contactez-nous",
//				PERSON_DETAIL_PROFILE: "Mettre à jour mon profil",
//				PERSON_DETAIL_LANGUAGE: "Langue",
//				PERSON_MESSAGE: 'Un mot de passe provisoire a été envoyé à votre adresse e-mail',
//
//                PERSON_NEW_PROFILE: "Veuillez saisir les informations votre compte",
//                PERSON_NEW_MDP: "Mot de passe",
//                PERSON_NEW_CONFIRM: "Confirmez votre mot de passe",
//                PERSON_NEW_MAIL: "Adresse e-mail",
//                PERSON_NEW_LANG: "Choix de la langue",
//
//                MQP_NEW_TITLE: 'Mon nouveau Marquepage',
//                
//                MQP_DET_TITLE: 'Mon Marquepage',
//                MQP_DET_NAME: 'Nom du marquepage :',
//                MQP_DET_LINK: 'Lien du marquepage :',
//                MQP_DET_KEY: 'Clé',
//                MQP_DET_VALUE: 'Valeur',
//                
//                MQP_LIST_NAME: 'Noms',
//                MQP_LIST_LINKS: 'Liens',
//                MQP_LIST_FILTER: 'Filtrer',
//                
//                REQUIRED: "Ce champ est obligatoire",
//                URL: 'Ce champ doit être une URL',
//                SEARCH: 'Recherche',
//                
//				ADD: "Ajouter",
//				PROFILE: "Mon profil",				
//				LOGOUT: "Déconnexion",
//				CANCEL: "Annuler",
//				DELETE: "Supprimer",
//				UPDATE: "Modifier",
//                CREATE: 'Créer',
//                HELP: 'Aide'
//			});
//
//			$translateProvider.preferredLanguage('french');
//
//		});
//		
///* <p>{{'TITLE' | translate}}</p>  ou  <p translate="TITLE"></p>  */
//	
//	
//	/*angular
//        .module('marcopolo')
//		.controller('traductCtrl', function ($scope, $translate, $rootScope) {
//			$scope.changeLanguage = function (key) {
//				$translate.use(key);
//                
//                switch (key) {
//                    case 'french': $rootScope.lang = 1;
//                        break;
//                    case 'english' : $rootScope.lang = 2;
//                        break;
//                    default: $rootScope.lang = 1;
//                }
//                
//                console.log('La langue est : '+key+' : '+ $rootScope.lang);
//			};
//		});*/
//        
//    } // function
//
//)();