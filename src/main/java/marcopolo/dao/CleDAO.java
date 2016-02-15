package marcopolo.dao;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import marcopolo.controllers.CleController;
import marcopolo.entity.Cle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/**
 * Cle data access object
 *
 */
public class CleDAO extends DAO<Cle> {

	private final JdbcTemplate jdbcTemplate;
	
	private static Log log = LogFactory.getLog(CleDAO.class);

	@Autowired
	public CleDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	/**
	 * mapping each row of the result sql request
	 * to a Cle object
	 *
	 */
	public class CleMapper implements RowMapper<Cle> {
		
		public Cle mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			
			Cle cle = new Cle();
			cle.setIdCle(rs.getLong("id_cle"));
			cle.setCle(rs.getString("cle"));
			cle.setLangue(rs.getString("nom"));
			// add Hateoas link  
			cle.add(linkTo(methodOn(CleController.class).getCle(cle.getIdCle())).withSelfRel());
			return cle;
		}
	}
	
	
	/**
	 * Find id of a Cle with its cle
	 * @param cle
	 * @return Long (id Cle)
	 * 
	 */
	public Long findIdCleWithCle (String cle) {
		
		String sql = "select id_cle "
				+ "from cle c, langue l "
				+ "where c.id_langue = l.id_langue "
				+ "and cle=?";
		
		List<Long> idCleList = this.jdbcTemplate.query(sql, new RowMapper<Long>() {
			
			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getLong("id_cle");
			}
			
		}, cle);
		
		if (idCleList.isEmpty()) {
			log.info("id_Cle does not exists");
			return null; 
		  
		  // list contains exactly 1 element
		} else if ( idCleList.size() == 1 ) { 
			log.info("id_Cle=" + idCleList.get(0));
			 return idCleList.get(0);
			 
		// list contains more than 1 element
		} else {
			log.error("Table cle : id_Cle is not unique");
			return null;
		}
	}
	
	
	/**
	 * create a cle in DB
	 * @param String cle
	 * @return Long id cle
	 */
	public Long create(String cle, Long idLangue) {
		
		// Add cle to table Cle
		String sql = "insert "
				+ "into cle (id_cle, cle, id_langue) "
				+ "values (seq_cle.nextval, ?, ?)";
		
		this.jdbcTemplate.update(sql, cle, idLangue);
				
		// return id_cle
		return findIdCleWithCle(cle);
		
	}
	
	public List<Cle> getPersonCles(long idPerson) {
		
		String sql =  "select distinct cle "
				+ "from marquepage mp,tag tg,cle cl, langue l "
				+ "where mp.id_marquepage=tg.id_marquepage "
				+ "and cl.id_langue = l.id_langue "
				+ "and tg.id_cle=cl.id_cle "
				+ "and mp.id_person=?";
		
		List<Cle> clesList = this.jdbcTemplate.query(sql, new Object[]{idPerson},	
		        new CleMapper());
		
		return clesList;
	}
	
	/**
	 * 
	 * Find one Cle 
	 * @param Long idCle
	 * @return Cle
	 * 
	 */
	@Override
	public Cle find(Long idCle) {
		
		String sql =  "select * "
				+ "from cle c, langue l "
				+ "where c.id_langue = l.id_langue "
				+ "and id_cle=?";
		  
		List<Cle> cleList = this.jdbcTemplate.query(sql, new Object[]{idCle},
				new CleMapper());
		
		return cleList.get(0);
	}

	
	/**
	 * 
	 * Find cles for one Langue
	 * @param Long idCle
	 * @return List<Cle>
	 * 
	 */
	public List<Cle> findClesForOneLangue(Long idLangue) {
		
		String sql =  "select * "
				+ "from cle c, langue l "
				+ "where c.id_langue = l.id_langue "
				+ "and c.id_langue=?";
		  
		List<Cle> cleList = this.jdbcTemplate.query(sql, new Object[]{idLangue},
				new CleMapper());
		
		return cleList;
	}
	

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}
	
}
