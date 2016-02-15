package marcopolo.controllers;

import java.sql.Timestamp;

import marcopolo.dao.LangueDAO;
import marcopolo.dao.PersonDAO;
import marcopolo.entity.Langue;
import marcopolo.entity.Person;
import marcopolo.commons.HmacSha1Signature;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Langue resource controller
 *
 */

@RestController
@RequestMapping("/langues")
public class LangueController {

    private static Log log = LogFactory.getLog(LangueController.class);	

    @Autowired
    JdbcTemplate jdbcTemplate;	

    
    
    /**
     * Liste de toutes les langues
     * 
     * @return List<Langue>
     */

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody HttpEntity<?> allLangues(
            @RequestParam(value="user", required = true) Long pId,
            @RequestParam(value="timestamp", required = true) Long pTimestamp,
            @RequestParam(value="signature", required = true) String pSignature ) {

        // réponse par défaut
        HttpEntity<?> nResponseEntity = ResponseEntity.badRequest().build();
        
        // pId=0 requête issue de l'ihm new person
        if (0 == pId) {
        
            LangueDAO nLangueDAO = new LangueDAO(jdbcTemplate);
            
            // réponse retournée valide
            nResponseEntity = ResponseEntity.ok(nLangueDAO.getAllLangues());
            
        } else {
            
            // recherche du propriétaire de la requête en fonction de son Id
            PersonDAO myPersonDAO = new PersonDAO(jdbcTemplate);
            Person nPerson = myPersonDAO.getPerson(pId); 
        
        
            // création de la signature avec les données issues de la bddd du propriétaire de la requête
            String nUri= "/langues?user=" + pId + "&timestamp=" + pTimestamp + "&values={\"user\":"+ pId +"}";
            String nHmac = HmacSha1Signature.calculate(nUri, nPerson.getMdp());
    
            log.info("getMdp " + nPerson.getMdp());
            log.info("nUri " + nUri);
            log.info("nHmac " + nHmac);
            
            // comparaison des signatures
            if (pSignature.equals(nHmac)) {
                

                Timestamp nPersonTimestamp = new Timestamp(nPerson.getStamp());
                Timestamp nUriTimestamp = new Timestamp(pTimestamp);
                
                // comparaison des timestamp
                // compareTo: a value greater than 0 if this Timestamp object is after the given argument.
                if (nUriTimestamp.compareTo(nPersonTimestamp) > 0) {
                    
                    // update person stamp
                    myPersonDAO.updateStampById(pId, pTimestamp);
                    
                    LangueDAO nLangueDAO = new LangueDAO(jdbcTemplate);
                    
                    // réponse retournée valide
                    nResponseEntity = ResponseEntity.ok(nLangueDAO.getAllLangues());
                } // if
            } // if

        } // if

        return nResponseEntity;
    } // HttpEntity<?>

    
    /**
     * @param pIdLangue
     * @return Langue object
     */
    public Langue getLangue(final Long pIdLangue) {

        LangueDAO nLangueDAO = new LangueDAO(jdbcTemplate);
        
        return nLangueDAO.find(pIdLangue);
    } // Langue
    
    /**
     * GET request for /langues/{idLangue}
     * 
     * Get one Langue
     * @param Long
     * @return HttpEntity<Langue> 
     */
    
//    @RequestMapping(method = RequestMethod.GET, value = "/{idLangue}")
//    public HttpEntity<Langue> getLangue(
//            @PathVariable("idLangue") Long pId ) {
//            
//            log.info("webService getLangue with idLangue =" + pId);
//            LangueDAO nLangueDAO = new LangueDAO(jdbcTemplate);
//            Langue nLangue = nLangueDAO.find(pId);
//            
//            return new ResponseEntity<Langue>(nLangue, HttpStatus.OK);
//                    
//    }
    
} // class
