(function () {
    'use strict';

    angular.module('marcopolo')
        .factory('Hateoas', Hateoas);

    Hateoas.$inject = ['$location'];
    function Hateoas($location) {
        var nLinks = {};

        return {
            getUri: function (pKey) {
                return nLinks[pKey];
            },

            setLinks: function (pLinks) {
                for	(var index = 0; index < pLinks.length; index++) {

                    var nUrl = pLinks[index].href;
                    // remplace aprés le # dans la barre d'adresse
                    var nPort = $location.port(nUrl);
                    var nPathArray = nUrl.split(nPort);

                    nLinks[pLinks[index].rel] = nPathArray[1];
                } // for
            }
        } // return
    } // function

})();