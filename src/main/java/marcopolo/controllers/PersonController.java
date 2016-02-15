package marcopolo.controllers;


import java.sql.Timestamp;
import java.util.List;

import marcopolo.commons.HmacSha1Signature;
import marcopolo.dao.CleDAO;

import marcopolo.dao.MarquePageDAO;
import marcopolo.dao.PersonDAO;
import marcopolo.entity.Cle;
import marcopolo.entity.Fiche;

import marcopolo.entity.MarquePage;
import marcopolo.entity.Person;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;

import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/persons")
public class PersonController {

    private static Log log = LogFactory.getLog(PersonController.class);

    @Autowired
    JdbcTemplate jdbcTemplate;
    
    //**************************************************************************************************** début
    // évaluation requête à usage unique
    
    /**
     * @param pRequestSignature
     * @param pCurrentSignature
     * @param pRequestTimestamp
     * @param pCurrentTimestamp
     * @return true boolean if footprints are the same and if timestamp from uri is older than the database one
     */
    private boolean checkRequest(final String pRequestSignature, final String pCurrentSignature, final Long pRequestTimestamp, final Long pCurrentTimestamp) {
        
        // valeur par défaut du retour
        boolean nReturn = false;
        
        // comparaison des signatures
        if (pRequestSignature.equals(pCurrentSignature)) {

            Timestamp nPersonTimestamp = new Timestamp(pCurrentTimestamp);
            Timestamp nUriTimestamp = new Timestamp(pRequestTimestamp);
            
            // comparaison des timestamp
            // compareTo: a value greater than 0 if this Timestamp object is after the given argument.
            nReturn = (nUriTimestamp.compareTo(nPersonTimestamp) > 0);
        } // if
        
        return nReturn;
    } // boolean
    
    
    /**
     * @param pIdPerson
     * @return Person object
     */
    public Person getPerson(final Long pIdPerson) {

        PersonDAO myPersonDAO = new PersonDAO(jdbcTemplate);
        
        return myPersonDAO.getPerson(pIdPerson);
    } // Person

    /**
     * Créer une personne
     * 
     * @param pFiche
     * @param pId
     * @param pTimestamp
     * @param pSignature
     * @return a ResponseEntity without data if failed
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public HttpEntity<?> addPerson(
            
            @RequestBody Fiche pFiche,
            
            @RequestParam(value = "user", required = true) Long pId,
            @RequestParam(value = "timestamp", required = true) Long pTimestamp,
            @RequestParam(value = "signature", required = true) String pSignature ) {

        // réponse par défaut
        HttpEntity<?> nResponseEntity = ResponseEntity.badRequest().build();

        // recherche du propriétaire de la requête en fonction de son Id
        PersonDAO myPersonDAO = new PersonDAO(jdbcTemplate);
    
        // création de la signature avec les données issues de la bdd
        String nUri= "/persons" + "?user=" + pId + "&timestamp=" + pTimestamp + "&values=" + pFiche.toString();
        String nCurrentSignature = HmacSha1Signature.calculate(nUri, String.valueOf(pTimestamp));

        log.info("Appel webService addPerson nUri " + nUri);
        log.info("Appel webService addPerson nCurrentSignature " + nCurrentSignature);
    
        // comparaison des signatures
        if (pSignature.equals(nCurrentSignature)) {
            
            // création fiche person
            Person nPerson = myPersonDAO.addPerson(pFiche.getMail(), pFiche.getMdp(), pFiche.getLangue(), pTimestamp);
            
            // ajout d'un marquepage vers l'aide en ligne
            String nLien = (pFiche.getLangue().equals("english")) ? "help_en" : "help_fr";
            String nNom = (pFiche.getLangue().equals("english")) ? "welcome" : "bienvenue";
            myPersonDAO.addPersonMqp(nPerson.getIdPerson(), "http://localhost:8080/#/" + nLien, nNom);
            
            // réponse retournée valide: 
            nResponseEntity = ResponseEntity.ok(nPerson);

        } // if
       
        return nResponseEntity;    
    } // HttpEntity<?>
    

    /**
     * Récupérer une personne avec son id
     * 
     * @param pIdPerson
     * @param pId
     * @param pTimestamp
     * @param pSignature
     * @return a ResponseEntity without data if failed
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{IdPerson}")
    @ResponseBody
    public HttpEntity<?> getPersonById(
            
            @PathVariable("IdPerson") Long pIdPerson,
            
            @RequestParam(value = "user", required = true) Long pId,
            @RequestParam(value = "timestamp", required = true) Long pTimestamp,
            @RequestParam(value = "signature", required = true) String pSignature ) {

        // réponse par défaut
        HttpEntity<?> nResponseEntity = ResponseEntity.badRequest().build();

        // recherche du propriétaire de la requête en fonction de son Id
        PersonDAO myPersonDAO = new PersonDAO(jdbcTemplate);
        Person nPerson = myPersonDAO.getPerson(pId); 
    
        // création de la signature avec les données issues de la bdd
        String nUri= "/persons/" + pIdPerson + "?user=" + pId + "&timestamp=" + pTimestamp + "&values={\"user\":"+ pId +"}";
        String nCurrentSignature = HmacSha1Signature.calculate(nUri, nPerson.getMdp());

        log.info("Appel webService getPersonById nUri " + nUri);
        log.info("Appel webService getPersonById nCurrentSignature " + nCurrentSignature);
        
        // checkRequest retourne vrai si: pSignature=nCurrentSignature et pTimestamp > nPerson.getStamp()
        if (checkRequest(pSignature, nCurrentSignature, pTimestamp, nPerson.getStamp())) {
            
            // update person stamp
            myPersonDAO.updateStampById(pId, pTimestamp);

            // réponse retournée valide
            nResponseEntity = ResponseEntity.ok(myPersonDAO.getPerson(pId)); 
        } // if
        
        return nResponseEntity;
    } // HttpEntity<?>
    

    /**
     * Récuperer une personne avec son mail et son mdp
     * 
     * @param pMail
     * @param pMdp
     * @param pId
     * @param pTimestamp
     * @param pSignature
     * @return a ResponseEntity without data if failed
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public HttpEntity<?> getPersonByMailMdp(
            
            @RequestParam(value = "mail", required = true) String pMail,
            @RequestParam(value = "mdp", required = true) String pMdp,
            
            @RequestParam(value = "user", required = true) Long pId,
            @RequestParam(value = "timestamp", required = true) Long pTimestamp,
            @RequestParam(value = "signature", required = true) String pSignature ) {

        // réponse par défaut
        HttpEntity<?> nResponseEntity = ResponseEntity.badRequest().build();
        
        PersonDAO nPersonDAO = new PersonDAO(jdbcTemplate);
        
        // création de la signature
        String nUri= "/persons?user=" + pId + "&timestamp=" + pTimestamp 
                + "&values={\"user\":" + pId + ",\"mail\":\"" + pMail + "\",\"mdp\":\"" + pMdp + "\"}";
        String nCurrentSignature = HmacSha1Signature.calculate(nUri, pMdp);
        
        log.info("Appel webService getPersonByMailMdp nUri " + nUri);
        log.info("Appel webService getPersonByMailMdp nCurrentSignature " + nCurrentSignature);
        
        // comparaison des signatures
        if (pSignature.equals(nCurrentSignature)) {
        
            // update person stamp
            nPersonDAO.updateStampById(pId, pTimestamp);

            // réponse retournée valide
            nResponseEntity = ResponseEntity.ok(nPersonDAO.getPersonByMailMdp(pMail, pMdp));
        } // if

        return nResponseEntity;
    } // HttpEntity<?>
    
    /* Modifier une personne */
    /**
     * Modifier une personne
     * 
     * @param pIdPerson
     * @param pFiche
     * @param pId
     * @param pTimestamp
     * @param pSignature
     * @return a ResponseEntity without data if failed
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/{IdPerson}")
    @ResponseBody
    public HttpEntity<?> updatePerson(
            
            @PathVariable("IdPerson") long pIdPerson,
            
            @RequestBody Fiche pFiche,
            
            @RequestParam(value = "user", required = true) Long pId,
            @RequestParam(value = "timestamp", required = true) Long pTimestamp,
            @RequestParam(value = "signature", required = true) String pSignature ) {

        // réponse par défaut
        HttpEntity<?> nResponseEntity = ResponseEntity.badRequest().build();

        // recherche du propriétaire de la requête en fonction de son Id
        PersonDAO myPersonDAO = new PersonDAO(jdbcTemplate);
        Person nPerson = myPersonDAO.getPerson(pId); 
    
        // création de la signature avec les données issues de la bdd
        String nUri= "/persons/" + pIdPerson + "?user=" + pId + "&timestamp=" + pTimestamp + "&values=" + pFiche.toString();
        String nCurrentSignature = HmacSha1Signature.calculate(nUri, nPerson.getMdp());

        log.info("Appel webService updatePerson nUri " + nUri);
        log.info("Appel webService updatePerson nCurrentSignature " + nCurrentSignature);
        
        // checkRequest retourne vrai si: pSignature=nCurrentSignature et pTimestamp > nPerson.getStamp()
        if (checkRequest(pSignature, nCurrentSignature, pTimestamp, nPerson.getStamp())) {
            
            if(pFiche.getMdp().isEmpty()) {
                // réponse retournée valide
                nResponseEntity = ResponseEntity.ok(myPersonDAO.updatePersonWithoutMdp(pIdPerson, pFiche.getMail(), pFiche.getLangue(), pTimestamp));
                
            } else {
             // réponse retournée valide
                nResponseEntity = ResponseEntity.ok(myPersonDAO.updatePerson(pIdPerson, pFiche.getMail(), pFiche.getMdp(), pFiche.getLangue(), pTimestamp));  
            }
             
        } // if

        return nResponseEntity;
    } // HttpEntity<?>
    

    /**
     * Supprimer une personne 
     * 
     * @param pIdPerson
     * @param pId
     * @param pTimestamp
     * @param pSignature
     * @return a ResponseEntity without data if failed
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/{IdPerson}")
    @ResponseBody 
    public HttpEntity<?> deletePerson(
            
            @PathVariable("IdPerson") Long pIdPerson,
            
            @RequestParam(value = "user", required = true) Long pId,
            @RequestParam(value = "timestamp", required = true) Long pTimestamp,
            @RequestParam(value = "signature", required = true) String pSignature ) {
        
        // réponse par défaut
        HttpEntity<?> nResponseEntity = ResponseEntity.badRequest().build();

        // recherche du propriétaire de la requête en fonction de son Id
        PersonDAO myPersonDAO = new PersonDAO(jdbcTemplate);
        Person nPerson = myPersonDAO.getPerson(pId); 
    
        // création de la signature avec les données issues de la bdd
        String nUri= "/persons/" + pIdPerson + "?user=" + pId + "&timestamp=" + pTimestamp + "&values={\"user\":"+ pId +"}";
        String nCurrentSignature = HmacSha1Signature.calculate(nUri, nPerson.getMdp());

        log.info("Appel webService deletePerson nUri " + nUri);
        log.info("Appel webService deletePerson nCurrentSignature " + nCurrentSignature);

        // checkRequest retourne vrai si: pSignature=nCurrentSignature et pTimestamp > nPerson.getStamp()
        if (checkRequest(pSignature, nCurrentSignature, pTimestamp, nPerson.getStamp())) {
            
            MarquePageDAO myMqpDAO = new MarquePageDAO(jdbcTemplate);
            myMqpDAO.deleteByIdPerson(pIdPerson);
            
            // réponse retournée valide
            nResponseEntity = ResponseEntity.ok(myPersonDAO.deletePerson(pIdPerson));
             
        } // if
        
        return nResponseEntity;
    }

    // évaluation requête à usage unique
    //**************************************************************************************************** fin

    
    /* Renvoi liste marquepages de la personne avec id defini */

    @RequestMapping(method = RequestMethod.GET, value = "/{personId}/marquepages")
    public List<MarquePage> getPersonMqp(@PathVariable("personId") long personId) {

        log.info("Appel webService getPersonMqp avec personId = " + personId);

        MarquePageDAO myMarquePageDAO = new MarquePageDAO(jdbcTemplate);
        return (myMarquePageDAO.getPersonMqp(personId));
    }   
    
    /* Renvoi les clés de la personne avec id donné */

    @RequestMapping(method = RequestMethod.GET, value = "/{personId}/cles")
    public List<Cle> findClesByIdPerson(@PathVariable("personId") long personId) {

        log.info("Appel webService onePerson avec personId = " + personId);

        CleDAO myCleDAO = new CleDAO(jdbcTemplate);
        return (myCleDAO.getPersonCles(personId)); 
    }

    /* Créer marquepage à partir de l'idPerson */
    @RequestMapping(method = RequestMethod.POST, value = "/{personId}/marquepages")
    public MarquePage addPersonMqp(
            @PathVariable("personId") long personId,
            
            @RequestParam(value = "lien", required = true) String lien,
            @RequestParam(value = "nom", required = true) String nom) {

        log.info("Appel webService addPersonMqp avec idPerson = " + personId + " " + lien + " " + nom );

        PersonDAO myPersonDAO = new PersonDAO(jdbcTemplate);

        return myPersonDAO.addPersonMqp(personId, lien, nom );    
    }

}
