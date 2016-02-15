(function () {
    'use strict';

    angular.module('marcopolo')
        .factory('Session', Session);

    Session.$inject = [ 'Crypto'];
    function Session(Crypto) {
        var nSession = {};

        return {
            setCurrent: function (pId, pMdp) {
                nSession['id'] = pId;
                nSession['mdp'] = pMdp;
            },

            // création de la signature de l'uri
            // pour préparer la requête à usage unique du type
            // nUri = /persons/1/marquepages?user=4&timestamp=12341523&signature=3626810c003760d1c278a356c450
            getSignature: function (pUrl, pParams, pTimestamp) {

                var nUri = pUrl + "?user=" + nSession['id'] + "&timestamp=" + pTimestamp;

                if (typeof pParams[0] != 'undefined') { // hint: le tableau est vide ?
                //if (pParams.length >= 1) {
                    // convert it into html form data by using $.param (jQuery function)
                    nUri = nUri + "&" + $.param(pParams);
                } // if

                var nSignature = Crypto.HmacSHA1(nUri, nSession['mdp']);

                console.log("Session factory getSignature uri " + nUri + "&signature=" + nSignature);

                return nSignature;
            },

            setId: function (pId) {
                nSession['id'] = pId;
            },
            getId: function () {
                return nSession['id'];
            },

            getMdp: function () {
                return nSession['mdp'];
            },

            clear: function () {
                nSession['id'] = "0";
                nSession['mdp'] = "";
            }
        }
    } // function

})();