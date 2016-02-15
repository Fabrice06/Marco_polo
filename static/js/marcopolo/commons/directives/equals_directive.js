(function () {
    'use strict';

    angular.module('marcopolo')
        .directive('EqualsTo', EqualsTo);

    function EqualsTo() {
        /* from http://www.softfluent.fr/blog/expertise/2014/07/15/Creation-dun-formulaire-dinscription-avec-Angularjs-et-Bootstrap
         *
         * <input type="password" ng-model="Password" />
         * <input type="password" ng-model="ConfirmPassword" equals-to="Password" />
         */
        return {
            restrict: 'A', // S'utilise uniquement en tant qu'attribut
            scope: true,
            require: 'ngModel',
            link: function (scope, elem, attrs, control) {
                var check = function () {
                    //Valeur du champs courant
                    var v1 = scope.$eval(attrs.ngModel); // attrs.ngModel = "ConfirmPassword"
                    //valeur du champ à comparer
                    var v2 = scope.$eval(attrs.equalsTo).$viewValue; // attrs.equalsTo = "Password"
                    return v1 == v2;
                };

                scope.$watch(check, function (isValid) {
                    // Défini si le champ est valide
                    control.$setValidity("equalsTo", isValid);
                });
            }
        };
    } // function

})();
