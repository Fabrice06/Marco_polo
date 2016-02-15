package marcopolo.dao;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import marcopolo.controllers.MarquePageController;
import marcopolo.controllers.TagController;
import marcopolo.entity.Tag;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class TagDAO extends DAO<Tag> {

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public TagDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	private static Log log = LogFactory.getLog(TagDAO.class);
	
	/**
	 * mapping each row of the result sql request
	 * to a Tag object
	 *
	 */
	public class TagMapper implements RowMapper<Tag> {
		
		public Tag mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			
			Tag tag = new Tag();
			tag.setCle(rs.getString("cle"));
			tag.setValeur(rs.getString("valeur"));
			tag.setIdMqp(rs.getLong("id_marquepage"));
			tag.setIdTag(rs.getLong("id_tag"));
			//add Hateoas links
			tag.add(linkTo(methodOn(TagController.class).getTag(tag.getIdTag())).withSelfRel());
			tag.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(MarquePageController.class).getMqp(tag.getIdMqp())).withRel("marquepages"));
						
			return tag;
		}
	}
		
	
	/**
	 * Get one Tag 
	 * 
	 * @param (Long) id Tag
	 * 
	 * @return Tag
	 */
	@Override
	public Tag find(Long idTag) {
		 
		String sql = "select * "
				+ "from tag, cle "
				+ "where tag.id_cle = cle.id_cle "
				+ "and tag.id_tag = ?";

		return this.jdbcTemplate.queryForObject(sql, new Object[]{idTag},
				new TagMapper());		
	 }
	
	 
	/**
	 * Delete one Tag 
	 * 
	 * @param (Long) id Tag
	 * 
	 * @return Tag
	 */
	 @Override
	 public void delete(Long idTag) {
		 
		String sql = "delete "
					+ "from tag "
					+ "where id_tag=?";
		
		this.jdbcTemplate.update(sql, idTag);			
	 }
	
	
	/**
	 * 
	 * Get one marque-page's Tags 
	 * 
	 * @param (Long) id marque-page
	 * 
	 * @return List<Tag>
	 * 
	 */
	 public List<Tag> getTagsWithIdMqp(Long idMqp) {
	
		String sql = "select * "
				+ "from tag, cle " 
				+ "where tag.id_cle=cle.id_cle "
				+ "and tag.id_marquepage=?";

		List<Tag> tags = this.jdbcTemplate.query(sql, new Object[]{idMqp},
				new TagMapper());
		
		log.info("tags.size()=" + tags.size());
		
		return tags;
	}
	
	 
	 /**
	 * 
	 * delete all tags of one marque-page
	 * @param (Long) id marque-page
	 * 
	 */
	public void deleteTagsWithIdMqp(Long idMqp) {
		 
		 String sql = "delete "
					+ "from tag "
					+ "where id_marquepage=?";
		 
		this.jdbcTemplate.update(sql, idMqp);
	 }
	
	public Long addTag(Long idMqp, String cle, String valeur, Long idLangue) {
		 
		log.debug("cle=" + cle);
		log.debug("valeur=" + valeur);
		
		// verify if cle already exists
		CleDAO cleDao = new CleDAO(jdbcTemplate);
		Long idCle = cleDao.findIdCleWithCle(cle);
		
		// if cle doesnt exists
		if (idCle == null) {
			// create a new Cle and get id
			idCle = cleDao.create(cle, idLangue);
		}	
					
		// insert tag in DB
		String sql = "insert "
					+ "into tag (id_tag, id_marquepage, id_cle, valeur) "
					+ "values (seq_tag.nextval ,?, ?, ?)";
			 
		this.jdbcTemplate.update(sql, idMqp, idCle, valeur);
		  		
		// get last id_tag inserted			
		Long LastIdInserted = this.jdbcTemplate.queryForObject(SQL_GET_LAST_ID_INSERTED, Long.class);
		log.info("LastIdTagInserted=" + LastIdInserted);
				
		return LastIdInserted;
	 }
}