(function () {
    'use strict';

    angular.module('marcopolo')
        .factory('PersonZ', PersonZ);

    PersonZ.$inject = ['$http', 'Session'];
    function PersonZ ($http, Session) {

        var dataFactory = {};

        dataFactory.save = function (pUrl, pParams, pDatas) {

            // définition de la requête à usage unique
            var nTimestamp = Date.now();
            var nSignature = Session.getSignature(pUrl, pDatas, nTimestamp);

            var nReq = {
                method: 'POST',
                url: pUrl,
                headers: {'Content-Type': 'application/json'},
                params: {
                    // pour l'usage unique
                    timestamp: nTimestamp,
                    user: pParams.user,
                    signature: nSignature
                }, // params

                data: pDatas
            };
            return $http(nReq);
        };


        // GET sert à lire des données, pas à en envoyer, ainsi une requête GET ne peut contenir de corps.
        // Une requête GET peut être mise en cache, elle ne doit contenir aucune information susceptible de
        // varier dans le temps (du contenu par exemple).
        dataFactory.query = function (pUrl, pParams) {

            // définition de la requête à usage unique
            var nTimestamp = Date.now();
            var nSignature = Session.getSignature(pUrl, JSON.stringify(pParams), nTimestamp);

            var nReq = {
                method: 'GET',
                url: pUrl,
                headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                params: {
                    // requête à usage unique
                    signature: nSignature,
                    timestamp: nTimestamp,
                    user: pParams.user,

                    //params supplémentaires
                    mail: pParams.mail,
                    mdp: pParams.mdp
                } // params
            };
            return $http(nReq);
        };


        dataFactory.delete = function (pUrl, pParams) {

            // définition de la requête à usage unique
            var nTimestamp = Date.now();
            var nSignature = Session.getSignature(pUrl, JSON.stringify(pParams), nTimestamp);

            var nReq = {
                method: 'DELETE',
                url: pUrl,
                headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                params: {
                    // requête à usage unique
                    signature: nSignature,
                    timestamp: nTimestamp,
                    user: pParams.user
                } // params
            };
            return $http(nReq);
        };


        dataFactory.update = function (pUrl, pParams, pDatas) {

            // définition de la requête à usage unique
            var nTimestamp = Date.now();
            var nSignature = Session.getSignature(pUrl, pDatas, nTimestamp);

            console.log("person factory update pDatas " + pDatas);

            var nReq = {
                method: 'PUT',
                url: pUrl,
                headers: {'Content-Type': 'application/json'},
                params: {
                    // pour l'usage unique
                    timestamp: nTimestamp,
                    user: pParams.user,
                    signature: nSignature
                }, // params

                data: pDatas
            };
            return $http(nReq);
        };

        return dataFactory;

    } // function

})();