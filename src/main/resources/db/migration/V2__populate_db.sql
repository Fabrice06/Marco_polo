----------------------------------------------------------------
-- Base de données :  BD_MARCOPOLO revu vero
-- Script testé sur SQL Workbench --> résultats ok	vincent le 10FEB
--
----------------------------------------------------------------

----------------------------------------------------------------
-- Base de données :  BD_MARCOPOLO update
-- Script testé sur SQL Workbench --> résultats ok	vincent le 13FEB
--
-- modification des mots de passe pour répondre au règles de saisies (8 alphanumériques minimum)
----------------------------------------------------------------

--Insertion de données externes dans la table langue
INSERT INTO langue VALUES (seq_langue.nextval, 'français');
INSERT INTO langue VALUES (seq_langue.nextval, 'english');


--Insertion de données externes dans la table cle valeurs prédéfinies
INSERT INTO cle VALUES (1,1, 'nom');
INSERT INTO cle VALUES (2,2, 'name');
INSERT INTO cle VALUES (3,1, 'lien');
INSERT INTO cle VALUES (4,2, 'link');
INSERT INTO cle VALUES (5,1, 'date de création');
INSERT INTO cle VALUES (6,2, 'creation date');
INSERT INTO cle VALUES (7,1, 'couleur');
INSERT INTO cle VALUES (8,2, 'color');
INSERT INTO cle VALUES (9,1, 'description');
INSERT INTO cle VALUES (10,2, 'description');
INSERT INTO cle VALUES (11,1, 'date');
INSERT INTO cle VALUES (12,2, 'date');


--Insertion de données externes dans la table cle valeurs custom
INSERT INTO cle VALUES (seq_cle.nextval, 1, 'divers');
INSERT INTO cle VALUES (seq_cle.nextval, 2, 'project');
INSERT INTO cle VALUES (seq_cle.nextval, 1, 'todo');
INSERT INTO cle VALUES (seq_cle.nextval, 2, 'todo');
INSERT INTO cle VALUES (seq_cle.nextval, 1, 'esprit');
INSERT INTO cle VALUES (seq_cle.nextval, 2, 'spirit');
INSERT INTO cle VALUES (seq_cle.nextval, 1, 'lapin');
INSERT INTO cle VALUES (seq_cle.nextval, 2, 'rabbit');


--Insertion de données externes dans la table person
-- VALUES (id_person, id_langue, mail, mdp, stamp)
-- mdp: bbbbbbbb
INSERT INTO person VALUES (seq_person.nextval, 1, 'bill@mail.fr', '98c98d9431fecef8db2531511aa45e2bb0484ee4', 1455351533707);
-- mdp: eeeeeee
INSERT INTO person VALUES (seq_person.nextval, 2, 'english@mail.fr', '13990a053ebc6b96f2610c97f0783e769f08127e', 1455351533707);
-- mdp: bbbbbbbb
INSERT INTO person VALUES (seq_person.nextval, 1, 'bob@mail.fr', '98c98d9431fecef8db2531511aa45e2bb0484ee4', 1455351533707);
-- mdp: mmmmmmmm
INSERT INTO person VALUES (seq_person.nextval, 2, 'max@mail.fr', 'b755ea777ae1d54a21ffcda6f265c2617bd0d415', 1455351533707);
-- mdp: rrrrrrrr
INSERT INTO person VALUES (seq_person.nextval, 1, 'riton@mail.fr', '65f6c4c4745eb72f7dd98ca0cf4058e302aa1aab', 1455351533707);


--Insertion de données externes dans la table marquepage
--VALUES (id_marquepage, id_person, nom, lien)
INSERT INTO marquepage VALUES (seq_marquepage.nextval, 1, 'google', 'http://www.google.fr');
INSERT INTO marquepage VALUES (seq_marquepage.nextval, 1, 'cours android', 'www.lri.fr/~keller/Enseignement/Orsay/LP-SRSI-AF/android.html');
INSERT INTO marquepage VALUES (seq_marquepage.nextval, 2, 'painter', 'www.philippesaucourt.com');
INSERT INTO marquepage VALUES (seq_marquepage.nextval, 1, 'cours réseau', 'http://lacour.xavier.free.fr/');
INSERT INTO marquepage VALUES (seq_marquepage.nextval, 1, 'gitlab', 'https://gitlab.com/users/sign_in');
INSERT INTO marquepage VALUES (seq_marquepage.nextval, 2, 'StackOverFlow', 'http://stackoverflow.com/questions/22870930/recover-value-of-field-in-ng-repeat');
INSERT INTO marquepage VALUES (seq_marquepage.nextval, 3, 'jetTour', 'www.jettours.com/clubs');
INSERT INTO marquepage VALUES (seq_marquepage.nextval, 3, 'air france', 'www.airfrance.fr');
INSERT INTO marquepage VALUES (seq_marquepage.nextval, 2, 'my best cookbook',  'http://www.marmiton.org/recettes/recette_crumble-sale-carottes-poireaux-et-gruyere_167282.aspx');
INSERT INTO marquepage VALUES (seq_marquepage.nextval, 3, 'my best adress',  'www.picard.fr');
INSERT INTO marquepage VALUES (seq_marquepage.nextval, 4, '2014', 'https://fr.wiktionary.org/wiki/escapade');
INSERT INTO marquepage VALUES (seq_marquepage.nextval, 4, 'loterie', 'http://en.bab.la/dictionary/french-english/pas-de-chance');
INSERT INTO marquepage VALUES (seq_marquepage.nextval, 5, 'festival du vent', 'http://stackoverflow.com/questions/17329495/how-to-use-ng-option-to-set-default-value-of-select-element');
INSERT INTO marquepage VALUES (seq_marquepage.nextval, 5, 'projet marcopolo', 'http://et_qu_on_veux_y_arriver_quand_même.html');
INSERT INTO marquepage VALUES (seq_marquepage.nextval, 5, 'derrière les nuages', 'je ne suis pas un lien valide');


--Insertion de données externes dans la table tag
-- VALUES (id_tag, id_marquepage, id_cle, valeur)
INSERT INTO tag VALUES (seq_tag.nextval, 1, 9, 'site de recherche');
INSERT INTO tag VALUES (seq_tag.nextval, 3, 8, 'blue');
INSERT INTO tag VALUES (seq_tag.nextval, 5, 1000, 'site exemple');
INSERT INTO tag VALUES (seq_tag.nextval, 4, 11, '2014');
INSERT INTO tag VALUES (seq_tag.nextval, 2, 9, 'cours Chantal Keller');
INSERT INTO tag VALUES (seq_tag.nextval, 4, 9, 'Systeme-Reseaux');
INSERT INTO tag VALUES (seq_tag.nextval, 6, 10, 'my search');
INSERT INTO tag VALUES (seq_tag.nextval, 7, 1001, 'holydays');
INSERT INTO tag VALUES (seq_tag.nextval, 8, 1001, 'holydays');
INSERT INTO tag VALUES (seq_tag.nextval, 9, 1002, 'cooking');
INSERT INTO tag VALUES (seq_tag.nextval, 10, 1001, 'cooking');
INSERT INTO tag VALUES (seq_tag.nextval, 7, 1005, '2016');
INSERT INTO tag VALUES (seq_tag.nextval, 8, 1005, '2016');







