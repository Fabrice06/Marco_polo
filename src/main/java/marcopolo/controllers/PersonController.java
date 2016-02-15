package marcopolo.controllers;


import java.util.List;

import marcopolo.dao.CleDAO;
import marcopolo.dao.MarquePageDAO;
import marcopolo.dao.PersonDAO;
import marcopolo.entity.Cle;
import marcopolo.entity.MarquePage;
import marcopolo.entity.Person;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/persons")
public class PersonController {

    private static Log log = LogFactory.getLog(PersonController.class);

    @Autowired
    JdbcTemplate jdbcTemplate;

    /* Créer une personne */
    @RequestMapping(method = RequestMethod.POST)
    public Person addPerson(
            @RequestParam(value = "mail", required = true) String pMail,
            @RequestParam(value = "mdp", required = true) String pMdp,
            @RequestParam(value = "langue", required = true) String pLangue) {

        log.info("Appel webService createPerson avec personMail = " + pMail + " " + pMdp + " " + pLangue);

        PersonDAO myPersonDAO = new PersonDAO(jdbcTemplate);

        return myPersonDAO.addPerson(pMail, pMdp, pLangue);    
    }


    /* Supprimer une personne  */
    @RequestMapping(method = RequestMethod.DELETE,value= "/{personId}")
    public void deletePerson(@PathVariable("personId") long personId) {

        log.info("Appel webService deleteWithId avec personId = " + personId);

        MarquePageDAO myMqpDAO = new MarquePageDAO(jdbcTemplate);
        myMqpDAO.deleteByIdPerson(personId);

        PersonDAO myPersonDAO = new PersonDAO(jdbcTemplate);
        myPersonDAO.deletePerson(personId);
    }

    /* Modifier une personne */
    @RequestMapping(method = RequestMethod.PUT,value="/{personId}")
    public Person updatePerson(
            @PathVariable("personId") long personId,
            @RequestParam(value = "mail", required = true) String pMail,
            @RequestParam(value = "langue", required = true) String pLangue){

        PersonDAO myPersonDAO = new PersonDAO(jdbcTemplate);
        
        return myPersonDAO.updatePerson(personId, pMail, pLangue);
    }	

    /* Renvoi liste marquepages de la personne avec id defini */

    @RequestMapping(method = RequestMethod.GET, value = "/{personId}/marquepages")
    public List<MarquePage> getPersonMqp(@PathVariable("personId") long personId) {

        log.info("Appel webService getPersonMqp avec personId = " + personId);

        MarquePageDAO myMarquePageDAO = new MarquePageDAO(jdbcTemplate);
        return (myMarquePageDAO.getPersonMqp(personId));
    }	

    /* Récuperer une personne avec son id */
    @RequestMapping(method = RequestMethod.GET, value = "/{personId}")
    public Person getPerson(@PathVariable("personId") long personId) {

        log.info("Appel webService onePerson avec personId = " + personId);

        PersonDAO myPersonDAO = new PersonDAO(jdbcTemplate);
        return (myPersonDAO.getPerson(personId));
    }

    /* Récuperer une personne avec son mail et son mdp */
    @RequestMapping(method = RequestMethod.GET)
    public Person getPersonByMailId(
            @RequestParam(value = "mail", required = true) String pMail,
            @RequestParam(value = "mdp", required = true) String pMdp) {

        log.info("Appel webService createPerson avec personMail = " + pMail + " " + pMdp);

        PersonDAO myPersonDAO = new PersonDAO(jdbcTemplate);
        return (myPersonDAO.getPersonByMailMdp(pMail, pMdp));    
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
