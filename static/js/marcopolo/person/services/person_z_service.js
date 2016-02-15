(function () {
    'use strict';

    angular.module('marcopolo')
        .factory('PersonZ', PersonZ);

    PersonZ.$inject = ['$http', 'Session'];
    function PersonZ ($http, Session) {

        var dataFactory = {};

        dataFactory.save = function (pUrl, pParams) {

            // définition de la requête à usage unique
            //var nTimestamp = Date.now();
            //var nSignature = Session.getSignature(pUrl, pParams, nTimestamp);

            var nReq = {
                method: 'POST',
                url: pUrl,
                headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                params: {
                    // requête à usage unique
                    //signature: nSignature,
                    //timestamp: nTimestamp,
                    //user: Session.getId(),

                    //params supplémentaires
                    // attention: l'ordre des params doit être le même que dans l'uri
                    mail: pParams.mail,
                    mdp: pParams.mdp,
                    langue: pParams.langue

                } // params
            };
            return $http(nReq);
        };


        dataFactory.query = function (pUrl, pParams) {

            // définition de la requête à usage unique
            var nTimestamp = Date.now();
            var nSignature = Session.getSignature(pUrl, pParams, nTimestamp);

            var nReq = {
                method: 'GET',
                url: pUrl,
                headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                params: {
                    // requête à usage unique
                    //signature: nSignature,
                    //timestamp: nTimestamp,
                    //user: Session.getId(),

                    //params supplémentaires
                    // attention: l'ordre des params doit être le même que dans l'uri
                    mail: pParams.mail,
                    mdp: pParams.mdp

                }
            };
            return $http(nReq);
        };


        dataFactory.delete = function (pUrl, pParams) {

            // définition de la requête à usage unique
            var nTimestamp = Date.now();
            var nSignature = Session.getSignature(pUrl, pParams, nTimestamp);

            var nReq = {
                method: 'DELETE',
                url: pUrl,
                headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                params: {
                    // requête à usage unique
                    //signature: nSignature,
                    //timestamp: nTimestamp,
                    //user: Session.getId()

                    //params supplémentaires
                    // attention: l'ordre des params doit être le même que dans l'uri
                }
            };
            return $http(nReq);
        };


        dataFactory.update = function (pUrl, pParams) {

            // définition de la requête à usage unique
            var nTimestamp = Date.now();
            var nSignature = Session.getSignature(pUrl, pParams, nTimestamp);

            console.log("person factory params " + JSON.stringify(pParams));

            var nReq = {
                method: 'PUT',
                url: pUrl,
                headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                params: {
                    // requête à usage unique
                    //signature: nSignature,
                    //timestamp: nTimestamp,
                    //user: Session.getId()

                    //params supplémentaires
                    // attention: l'ordre des params doit être le même que dans l'uri
                    mail: pParams.mail,
                    langue: pParams.langue
                }
            };
            return $http(nReq);
        };

        return dataFactory;

    } // function

})();