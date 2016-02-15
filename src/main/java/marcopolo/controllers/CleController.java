package marcopolo.controllers;

import java.util.List;

import marcopolo.dao.CleDAO;
import marcopolo.entity.Cle;
import marcopolo.entity.Langue;

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
import org.springframework.web.bind.annotation.RestController;

/**
 * Cle resource controller
 *
 */

@RestController
@RequestMapping("/cles")

public class CleController {
	
	private static Log log = LogFactory.getLog(CleController.class);	
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	  

	/**
	 * GET request for /cles/{idCle}
	 * 
	 * Get one Cle
	 * @param Long
	 * @return ResponseEntity<Cle> 
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/{idCle}")
	public HttpEntity<Cle> getCle(@PathVariable("idCle") Long idCle) {
		
		log.info("webService getCle with idCle =" + idCle);
		CleDAO cleDao = new CleDAO(jdbcTemplate);
		Cle cle = cleDao.find(idCle);
		
		return new ResponseEntity<Cle>(cle, HttpStatus.OK);
			
	}
	
	
	/**
	 * GET request for /cles?langue=:langue
	 * 
	 * Get Langues for one Cle
	 * @param Long
	 * @return List<Cle> 
	 */
	@RequestMapping(method = RequestMethod.GET, value = "")
	public List<Cle> getClesForOneLangue(@RequestParam(value="langue") Long idLangue) {
		
		log.info("webService getClesForOneLangue with idLangue =" + idLangue);
		
		CleDAO cleDao = new CleDAO(jdbcTemplate);
		List<Cle> cleList = cleDao.findClesForOneLangue(idLangue);
		
		return cleList;
			
	}
	
	
}
	    