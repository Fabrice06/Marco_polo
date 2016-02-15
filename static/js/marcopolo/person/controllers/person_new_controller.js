(function () {
    'use strict';

    angular
        .module('marcopolo')
        .controller('personNewCtrl', personNewCtrl);

    personNewCtrl.$inject = ['$scope', '$location', 'PersonZ', 'Crypto', 'Hateoas', 'Session', 'Language'];
    function personNewCtrl($scope, $location, PersonZ, Crypto, Hateoas, Session, Language) {

        /* jshint validthis: true */
        var vm = this;

        vm.goToIhmPersonLog = goToIhmPersonLog;
        vm.onExit = onExit;
        vm.onCancel = onCancel;
        vm.onSubmit = onSubmit;

        Session.clear();

        //** Récupération et affichage des informations de la ressource language*/
        Language.query('/langues', {user: 0}).then(
            function successCallback(pResponse) {

                // this callback will be called asynchronously
                // when the response is available
                console.log("personNewCtrl query langues ok " + JSON.stringify(pResponse.data));
                vm.languages = pResponse.data;
            },
            function errorCallback(pResponse) {

                // called asynchronously if an error occurs
                // or server returns response with an error status.
                console.log("personNewCtrl query langues échec");
            }
        );

        function goToIhmPersonLog() {
            Session.clear();
            $location.path('/').replace();
        }

        // clic sur le bouton déconnexion
        function onExit() {
            goToIhmPersonLog();
        }

        // clic sur le bouton annuler
        function onCancel() {
            goToIhmPersonLog();
        }

        // clic sur le bouton valider
        function onSubmit(pPersonModel) {

            var nMail = pPersonModel.mail;
            var nMdp = Crypto.SHA1(pPersonModel.confirm);
            var nLangue = pPersonModel.langue.nom;

            // faire un check regex ????

            var nDatas = {
                mail: nMail,
                mdp: nMdp,
                langue: nLangue
            };
            console.log("personNewCtrl " + JSON.stringify(nDatas));

            PersonZ.save('/persons', nDatas).then(
                function successCallback(pResponse) { // OK pResponse est le retour du backEnd

                    // this callback will be called asynchronously
                    // when the response is available
                    if (angular.isUndefined(pResponse.data.links)) {
                        console.log("personNewCtrl query ok but Person don't exist");

                    } else {
                        console.log("personNewCtrl save ok " + JSON.stringify(pResponse.data));

                        // ce service permets de conserver l'id et le mdp crypté pendant toute la session
                        Session.setCurrent(pResponse.data.idPerson, nMdp);

                        // ce service fourni directement les liens hateoas sous forme de clé/valeur
                        Hateoas.setLinks(pResponse.data.links);

                        $location.url(Hateoas.getUri("marquepages")).replace();
                    } // else

                },
                function errorCallback(pResponse) {

                    // called asynchronously if an error occurs
                    // or server returns response with an error status.
                    console.log("personNewCtrl save échec");
                }
            );
        }
    } // function

})();