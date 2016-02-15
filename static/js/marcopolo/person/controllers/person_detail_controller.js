(function () {
    'use strict';

    angular
        .module('marcopolo')
        .controller('personDetailCtrl', personDetailCtrl);

    personDetailCtrl.$inject = ['$scope', '$location', 'PersonZ', 'Language', 'Session', 'Hateoas', 'Crypto', '$filter'];
    function personDetailCtrl($scope, $location, PersonZ, Language, Session, Hateoas, Crypto, $filter ) {

        /* jshint validthis: true */
        var vm = this;
        vm.doQueryPerson = doQueryPerson;
        //vm.personDetailModel = personDetailModel;
        vm.onCancel = onCancel;
        vm.onSubmit = onSubmit;
        vm.onDelete = onDelete;
        vm.onExit = onExit;

        vm.showMdpInput = false;

        //** Récupération et affichage des informations de la ressource language*/
        Language.query('/langues', {user:Session.getId()}).then(
            function successCallback(pResponse) {

                vm.languages = pResponse.data;

                doQueryPerson({user:Session.getId()});
            },
            function errorCallback() {
                console.log("personDetailCtrl query langues échec");
            }
        );

        //** Récuperation et affichage des informations de la ressource person selectionnée*/
        function doQueryPerson(pParams) {

            // pas de hateoas dans ihmMarquepage :-/
            //PersonZ.update(Hateoas.getUri("self"), pParams).then(
            PersonZ.query($location.url(), pParams).then(
                function successCallback(pResponse) {

                    $scope.personDetailModel = pResponse.data;

                    // ce service fourni directement les liens hateoas sous forme de clé/valeur
                    Hateoas.setLinks(pResponse.data.links);

                    // sélection de la langue
                    for (var i = 0; i < vm.languages.length; i++) {
                        if (vm.languages[i].nom === pResponse.data.langue) {
                            $scope.personDetailModel.langue = vm.languages[i];
                            break;
                        } // if
                    } // for
                },
                function errorCallback() {
                    console.log("personDetailCtrl query person échec");
                }
            );
        }

        // clic sur le bouton déconnexion
        function onExit() {

            Session.clear();
            $location.path('/').replace();
        }

        // clic sur le bouton supprimer
        function onDelete() {

            var nPopup = confirm($filter('translate')('PERSON_DETAIL_VALID'));
            if (nPopup == true) {

                // pas de hateoas dans ihmMarquepage :-/
                //PersonZ.update(Hateoas.getUri("self"), {user:Session.getId()}).then(
                PersonZ.delete($location.url(), {user:Session.getId()}).then(
                    function successCallback() { // OK pResponse est le retour du backEnd

                        Session.clear();
                        $location.path('/').replace();
                    },
                    function errorCallback() {
                        console.log("personDetailCtrl onDelete échec");
                    }
                );
            }
        }

        // clic sur le bouton Retour à ma liste de mqp
        function onCancel() {

            $location.url(Hateoas.getUri("marquepages")).replace();
        }

        // retourne vrai si pString: empty, null or undefined
        function isEmpty(pString) {
            return (!pString || 0 === pString.length);
        }

        function onSubmit(pPersonDetail) {

            var nMail = pPersonDetail.mail;
            var nMdp = (isEmpty(pPersonDetail.confirm))? "" : Crypto.SHA1(pPersonDetail.confirm);
            var nLangue = pPersonDetail.langue.nom;

            // faire un check regex ????

            var nDatas = JSON.stringify({
                "mail": nMail,
                "mdp": nMdp,
                "langue": nLangue
            });

            // pas de hateoas dans ihmMarquepage :-/
            //PersonZ.update(Hateoas.getUri("self"), {user:Session.getId()}, nDatas).then(
            PersonZ.update($location.url(), {user:Session.getId()}, nDatas).then(
                function successCallback() { // OK pResponse est le retour du backEnd

                    // ce service permets de conserver le mail et le mdp crypté pendant toute la session
                    if (!isEmpty(nMdp)) {
                        Session.setMdp(nMdp);
                    }

                    $scope.changeLangue(nLangue);

                    $location.url(Hateoas.getUri("marquepages")).replace();

                },
                function errorCallback() {
                    console.log("personDetailCtrl onSubmit échec");
                }
            );
        }

    } // function

})();