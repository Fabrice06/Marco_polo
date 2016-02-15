(function () {
    'use strict';

    angular.module('marcopolo')
        .factory('Crypto', Crypto);

    function Crypto() {
        return {
            SHA1: function (pValue) {
                var nString = JSON.stringify(pValue);
                return CryptoJS.SHA1(nString).toString();
            },
            HmacSHA1: function (pValue, pMdp) {
                var nValue = JSON.stringify(pValue);
                var nMdp = JSON.stringify(pMdp);
                return CryptoJS.HmacSHA1(pValue, pMdp).toString();
            }
        }
    } // function

})();