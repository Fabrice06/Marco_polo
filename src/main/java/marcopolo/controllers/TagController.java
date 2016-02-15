package marcopolo.controllers;

import marcopolo.dao.TagDAO;
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
import org.springframework.web.bind.annotation.RestController;

/**
 * Tag resource controller
 *
 */
@RestController
@RequestMapping("/tags")

public class TagController {

	private static Log log = LogFactory.getLog(TagController.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	
	/**
	 * GET request for /tags/{tagid}
	 * Get one tag
	 * 
	 * @param (Long) id marque-page
	 * @return Tag
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/{tagid}")
	public HttpEntity<Tag> getTag(@PathVariable("tagid") long idTag) {

		log.info("Appel webService getTag avec idTag =" + idTag);
		
		TagDAO tagDao = new TagDAO(jdbcTemplate);
		Tag tag = tagDao.find(idTag);
		
		return new ResponseEntity<Tag>(tag, HttpStatus.OK);
	}

	
	
	/**
	 * DELETE request for /tags/{tagid}
	 * Delete one tag
	 * 
	 * @param (Long) id tag
	 * @return (Long) id tag
	 */
	@RequestMapping(method = RequestMethod.DELETE, value = "/{tagid}")
	public long deleteTag(@PathVariable("tagid") long idTag) {

		log.info("Appel webService deleteTag avec tagId = " + idTag);
		
		TagDAO tagDao = new TagDAO(jdbcTemplate);
		tagDao.delete(idTag);
		
		return idTag;
	}

	
	
	
	//////////////////////////////////////////////
	// services provisoires pour test, a supprimer 
	//////////////////////////////////////////////
//	/**
//	 * Liste de tous les tags
//	 * Utilisé uniquement pour les tests
//	 * 
//	 * @return List<Tag>
//	 */
//	
//	@RequestMapping(method = RequestMethod.GET)
//	public @ResponseBody List<Tag> allTags() {
//
//		log.info("Appel webService allTags");
//
//		String requete = "select * "
//				+ "from tag";
//
//		List<Tag> tags = this.jdbcTemplate.query(requete, new RowMapper<Tag>() {
//			public Tag mapRow(ResultSet rs, int rowNum) throws SQLException {
//				Tag tag = new Tag();
//				tag.setValeur(rs.getString("valeur"));
//				return tag;
//			}
//		});
//		return tags;
//	}
	
	
	
}
