package marcopolo.controllers;

import java.util.List;

import marcopolo.dao.MarquePageDAO;
import marcopolo.dao.TagDAO;
import marcopolo.entity.MarquePage;
import marcopolo.entity.Tag;

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
 * MarquePage resource controller
 *
 */
@RestController
@RequestMapping("/marquepages")

public class MarquePageController {
	
	private static Log log = LogFactory.getLog(MarquePageController.class);

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	/**
	 * GET request for /marquepages/{idMqp}
	 * 
	 * Get one MarquePage
	 * @param Long
	 * @return ResponseEntity<MarquePage> 
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/{idMqp}")
	 public HttpEntity<MarquePage> getMqp(@PathVariable("idMqp") Long idMqp) {

		log.info("webService getMqp with idMqp =" + idMqp);

		MarquePageDAO marquePageDao = new MarquePageDAO(jdbcTemplate);
		MarquePage marquePage = marquePageDao.find(idMqp);
		
		return new ResponseEntity<MarquePage>(marquePage, HttpStatus.OK);
	}
	
	
	/**
	 * GET request for /marquepages/{idMqp}/tags 
	 * 
	 * Get one marquepage's Tags
	 * @param Long
	 * @return List<Tag>
	 * 
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/{idMqp}/tags")
	public List<Tag> getTagsMqp(@PathVariable("idMqp") long idMqp) {

		log.info("Appel webService getTagsMqp avec id MQP =" + idMqp);

		TagDAO tagDao = new TagDAO(jdbcTemplate);
		
		return tagDao.getTagsWithIdMqp(idMqp);
	}
		
	
	/**
	 * PUT request for /marquepages/{idMqp}
	 * 
	 * Update lien and nom of one marque-page
	 * 
	 * @param Long, String, String
	 * @return Long
	 * 
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "/{idMqp}")
	public @ResponseBody Long updateMqp(@PathVariable("idMqp") Long idMqp, 
			@RequestParam(value="lien") String lien,
			@RequestParam(value="nom") String nom) {

		log.info("Appel webService updateMqp");
		
		MarquePageDAO marquePageDao = new MarquePageDAO(jdbcTemplate);
		
		marquePageDao.update(idMqp, nom, lien);
				
		return idMqp;
	}
	
	
	/**
	 * DELETE request for /marquepages/{idMqp}
	 * 
	 * delete one marque-page 
	 * First we delete tags of this marque-page
	 * 
	 * @param Long
	 * @return Long
	 * 
	 */
	@RequestMapping(method = RequestMethod.DELETE, value = "/{idMqp}")
	public Long deleteMqp(@PathVariable("idMqp") Long idMqp) {

		log.info("Appel webService deleteMqp avec id marquepage =" + idMqp);
		
		
		//delete marque-page
		MarquePageDAO marquePageDao = new MarquePageDAO(jdbcTemplate);
		marquePageDao.delete(idMqp);
				
		return idMqp;
	}	
	
	
	/**
	 * POST request for /marquepages/{idMqp}/tags 
	 * 
	 * Create one Tag
	 * @param Long
	 * @return Long
	 * 
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/{idMqp}/tags")
	public Long addTag(@PathVariable("idMqp") Long idMqp, 
			@RequestParam(value="cle") String cle, 
			@RequestParam(value="valeur") String valeur,
			@RequestParam(value="idLangue") Long idLangue) {

		log.info("Appel webService addTag avec id MQP =" + idMqp);

		TagDAO tagDao = new TagDAO(jdbcTemplate);
		
		return tagDao.addTag(idMqp, cle, valeur, idLangue);
	}
	
	
	/**
	 * POST request for /marquepages?lien=:lien&nom=:nom
	 *  
	 * Create one marque-page 
	 * 
	 * @param String lien, String nom
	 * @return MarquePage
	 * 
	 */
	@RequestMapping(method = RequestMethod.POST, value = "")
	public MarquePage addMqp(@RequestParam(value="lien") String lien, 
			@RequestParam(value="nom") String nom) {

		log.info("Appel webService addMqp avec lien =" + lien);
		
		MarquePageDAO marquePageDao = new MarquePageDAO(jdbcTemplate);
		
		return marquePageDao.addMqp(lien, nom);
	}
	
	
	//
	//  utilisé pour test uniquement
	//
	
	
	/**
	 * Get all marque-pages
	 * 
	 *  tests only
	 * 
	 * @return List<MarquePage>
	 */
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody List<MarquePage> allMarquePages() {

		log.info("Appel webService allMarquePages");

		MarquePageDAO marquePageDao = new MarquePageDAO(jdbcTemplate);
		
		return marquePageDao.findAll();
	}


}
