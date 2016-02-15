package marcopolo.dao;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import marcopolo.controllers.MarquePageController;
import marcopolo.controllers.PersonController;
import marcopolo.entity.MarquePage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/**
 * MarquePage data access object
 *
 */
public class MarquePageDAO extends DAO<MarquePage> {
	
	@Autowired
	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public MarquePageDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	private static Log log = LogFactory.getLog(MarquePageDAO.class);
	
	/**
	 * 
	 * initialize a MarquePage object
	 * with the data of the sql request
	 *
	 */
	public class MarquePageMapper implements RowMapper<MarquePage> {
		
		public MarquePage mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			
			MarquePage marquePage = new MarquePage();
			marquePage.setNom(rs.getString("nom"));
			marquePage.setLien(rs.getString("lien"));
			marquePage.setIdMarquepage(rs.getLong("id_marquepage"));
			// add Hateoas link  
			marquePage.add(linkTo(
					methodOn(MarquePageController.class)
							.getMqp(marquePage.getIdMarquepage())).withSelfRel());
			return marquePage;
		}
	}
		
	
	/**
	 * 
	 * Find one MarquePage 
	 * @param long id MarquePage
	 * @return MarquePage
	 * 
	 */
	@Override
	public MarquePage find(Long idMqp) {

		String sql = "select * " 
					+ "from marquepage "
					+ "where id_marquepage=?";
		
		List<MarquePage> marquePageList = this.jdbcTemplate.query(sql, new Object[]{idMqp},
				new MarquePageMapper());
				
		// id_marquepage not found
		if (marquePageList.isEmpty()) {
			log.info("id_marquepage does not exists");
			return null; 
			
		// list contains exactly 1 element
		} else if (marquePageList.size() == 1 ) { 
			log.info("id_marquepage=" + marquePageList.get(0));
			// add Tags to marquePage 
			TagDAO tag = new TagDAO(jdbcTemplate);
			marquePageList.get(0).setTags(tag.getTagsWithIdMqp(idMqp));
			 return marquePageList.get(0); 

		// list contains more than 1 element
		} else {
			log.error("Table marquepage : id_marquepage is not unique");
			return null;
		}
	}
		
	
	/**
	 * 
	 * update nom and lien of one marque-page in DB
	 * @param Long idMqp
	 * @param String nom, String nom
	 * 
	 */
	public void update(Long idMqp, String nom, String lien) {
		
		String sql = "update marquepage set nom = ?, lien = ? "
				+ "where id_marquepage = ?";

		this.jdbcTemplate.update(sql, nom, lien, idMqp);
	}
	
	
	
//	/**
//	 * 
//	 * update lien of one marque-page in DB
//	 * @param Long idMqp
//	 * @param String newLien
//	 * 
//	 */
//	public void updateLien(Long idMqp, String newLien) {
//		
//		String sql = "update marquepage set lien = ? "
//				+ "where id_marquepage = ?";
//
//		this.jdbcTemplate.update(sql, newLien, idMqp);
//	}
	
	
	/**
	 * 
	 * Delete one marque-page from DB
	 * @param Long idMqp
	 * 
	 */
	@Override
	public void delete(Long idMqp) {
		
		//delete tags
		TagDAO tagDao = new TagDAO(jdbcTemplate);
		tagDao.deleteTagsWithIdMqp(idMqp);
				
		String sql = "delete "
				+ "from marquepage "
				+ "where id_marquepage=?";
		
		this.jdbcTemplate.update(sql, idMqp);
	}
	
	
	public void deleteByIdPerson(Long idPerson) {
		
		String sql = "select * " 
				+ "from marquepage "
				+ "where id_person=?";
	
		List<MarquePage> MarquePageList = this.jdbcTemplate.query(sql, new Object[]{idPerson},
		new MarquePageMapper());
	
	
		for (MarquePage mqp : MarquePageList) {
			delete(mqp.getIdMarquepage());
		}
		
	}


	/**
	 * Insert a marque-page in DB
	 * 
	 * @param String lien
	 * @return MarquePage
	 * 
	 */
	public MarquePage addMqp(String lien, String nom) {
		
		String sql = "insert "
				+ "into marquepage (id_marquepage, lien, nom) "
				+ "values (seq_marquepage.nextval, ?, ?)";
		
		this.jdbcTemplate.update(sql, lien, nom);
		
		// get last id_marquepage inserted
		Long LastIdMqpInserted = this.jdbcTemplate.queryForObject(SQL_GET_LAST_ID_INSERTED, Long.class);
		log.debug("LastIdMqpInserted=" + LastIdMqpInserted);
		
		return find(LastIdMqpInserted);
	}
	
	
	
	//
	//  utilisé pour test uniquement
	//
	
	/**
	 * 
	 * Find all MarquePage 
	 * 
	 * test only
	 * 
	 * @return List<MarquePage>
	 * 
	 */
	public List<MarquePage> findAll() {

		String sql = "select * "
				+ "from marquepage";

		List<MarquePage> marquePages = this.jdbcTemplate.query(sql, new MarquePageMapper());
							
		log.info("marquePages.size()=" + marquePages.size());
			
		return marquePages;
	}


	public List<MarquePage> getPersonMqp(long personId) {
		
		TagDAO tagDAO = new TagDAO(jdbcTemplate);
		
		String sql = "select * " 
				+ "from marquepage "
				+ "where id_person=?";
	
	List<MarquePage> marquePageList = this.jdbcTemplate.query(sql, new Object[]{personId},
		new MarquePageMapper());
	
		for (MarquePage mqp : marquePageList) {
			
			mqp.setTags(tagDAO.getTagsWithIdMqp(mqp.getIdMarquepage()));
			
			mqp.add(linkTo(methodOn(PersonController.class).getPerson(personId)).withRel("persons"));
			mqp.add(linkTo(methodOn(MarquePageController.class).getTagsMqp(mqp.getIdMarquepage())).withRel("marquepageById"));
			
			
		}
		
		
		return marquePageList;
	}
	
}
