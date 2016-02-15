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
            getSignature: function (pUrl, pValues, pTimestamp) {

                var nUri = pUrl + "?user=" + nSession['id'] + "&timestamp=" + pTimestamp + "&values="+ pValues;

                var nMdp =  (("0" === nSession['id']) && ("" === nSession['mdp'])) ? (""+pTimestamp) : nSession['mdp'];

                var nSignature = Crypto.HmacSHA1(nUri, nMdp);

                //console.log("Session factory getSignature nMdp " + nMdp);
                //console.log("Session factory getSignature uri " + nUri + "&signature=" + nSignature);

                return nSignature;
            },

            getId: function () {
                return nSession['id'];
            },

            setMdp: function (pMdp) {
                return nSession['mdp'] = pMdp;
            },

            clear: function () {
                nSession['id'] = "0";
                nSession['mdp'] = "";
            }
        }
    } // function

})();