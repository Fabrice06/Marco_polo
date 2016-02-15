package marcopolo.dao;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import marcopolo.entity.Langue;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;


import marcopolo.controllers.LangueController;

/**
 * Langue data access object
 *
 */
public class LangueDAO extends DAO<Langue> {

    
    private final JdbcTemplate jdbcTemplate;
    

    public LangueDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * mapping each row of the result sql request
     * to a langue object
     *
     */
    public class LangueMapper implements RowMapper<Langue> {

        public Langue mapRow(ResultSet rs, int rowNum)
                throws SQLException {

            Langue nLangue = new Langue();
            nLangue.setIdLangue(rs.getLong("id_langue"));
            nLangue.setNom(rs.getString("nom"));
            
            // add Hateoas link  
            nLangue.add(linkTo(methodOn(LangueController.class).getLangue(nLangue.getIdLangue())).withSelfRel());

            return nLangue;
        }
    }

    /**
     * @return a Langue object collection
     */
    public List<Langue> getAllLangues() {

        String sql =  "select * "
                + "from langue";

        List<Langue> langueList = this.jdbcTemplate.query(sql, new LangueMapper());

        return langueList;
    }

    /**
     * @param pId
     * @return a Langue object or null if failed
     */
    public Langue getLangue(final Long pId) {

        String sql = "select l.id_langue, l.nom "
                + "from langue l "
                + "where l.id_langue=?"; 

        List<Langue> nLangues = this.jdbcTemplate.query(sql, new Object[]{pId}, new LangueMapper());

        // person not found
        if (nLangues.isEmpty()) {
            log.info("langue does not exists");
            return null; 

            // list contains exactly 1 element
        } else if (nLangues.size() == 1 ) { 
            log.info("id_langues=" + nLangues.get(0));

            return nLangues.get(0); 

            // list contains more than 1 element
        } else {
            log.error("Table langue : langue is not unique");
            return null;
        }
    }
    
    
    /* (non-Javadoc)
     * @see marcopolo.dao.DAO#find(java.lang.Long)
     */
    @Override
    public Langue find(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    
    /* (non-Javadoc)
     * @see marcopolo.dao.DAO#delete(java.lang.Long)
     */
    @Override
    public void delete(Long id) {
        // TODO Auto-generated method stub

    }

} // class
