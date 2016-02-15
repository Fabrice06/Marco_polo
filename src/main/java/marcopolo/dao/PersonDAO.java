package marcopolo.dao;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import marcopolo.controllers.PersonController;
import marcopolo.entity.MarquePage;
import marcopolo.entity.Person;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class PersonDAO {

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    protected static Log log = LogFactory.getLog(PersonDAO.class);

    protected final static String SQL_GET_LAST_ID_INSERTED = "CALL SCOPE_IDENTITY()";

    /**
     * 
     * initialize a Person object
     * with the data of the sql request
     *
     */
    public class PersonMapper implements RowMapper<Person> {

        public Person mapRow(ResultSet rs, int rowNum)
                throws SQLException {

            Person person = new Person();
            person.setId(rs.getLong("id_person"));
            person.setLangue(rs.getString("nom"));
            person.setMail(rs.getString("mail"));
            person.setMdp(rs.getString("mdp"));
            person.setStamp(rs.getLong("stamp"));

            // add Hateoas link 
            person.add(linkTo(methodOn(PersonController.class).getPerson(person.getIdPerson())).withSelfRel());
            person.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(PersonController.class).getPersonMqp(person.getIdPerson())).withRel("marquepages"));
            return person;
        }
    }


    public void deletePerson(Long idPerson) {

        String sql = "delete "
                + "from person "
                + "where id_person=?";

        this.jdbcTemplate.update(sql, idPerson);
    }


    public Person getPerson(Long idPerson) {

        String nSql = "select p.id_person, p.mail, p.mdp, p.stamp, l.nom "
                + "from person p, langue l "
                + "where p.id_langue=l.id_langue "
                + "and p.id_person=?"; 
        
        log.info("getPerson=" + nSql);
        
        List<Person> personsList = this.jdbcTemplate.query(nSql, new Object[]{idPerson}, new PersonMapper());

        // id_person not found
        if (personsList.isEmpty()) {
            log.info("id_person does not exists");
            return null; 

            // list contains exactly 1 element
        } else if (personsList.size() == 1 ) { 
            log.info("id_person=" + personsList.get(0));

            return personsList.get(0); 

            // list contains more than 1 element
        } else {
            log.error("Table person : id_person is not unique");
            return null;
        }
    }


    public Person getPersonByMailMdp(final String pMail, final String pMdp) {

        String sql = "select p.id_person, p.mail, p.mdp, p.stamp, l.nom "
                + "from person p, langue l "
                + "where p.id_langue=l.id_langue "
                + "and p.mail=? and p.mdp=?"; 

        List<Person> personsList = this.jdbcTemplate.query(sql, new Object[]{pMail, pMdp}, new PersonMapper());

        // person not found
        if (personsList.isEmpty()) {
            log.info("person does not exists");
            return null; 

            // list contains exactly 1 element
        } else if (personsList.size() == 1 ) { 
            log.info("id_person=" + personsList.get(0));

            return personsList.get(0); 

            // list contains more than 1 element
        } else {
            log.error("Table person : person is not unique");
            return null;
        }
    }

    public Person getPersonById(Long pId) {

        String sql = "select p.id_person, p.mail, p.mdp, p.stamp, l.nom "
                + "from person p, langue l "
                + "where p.id_langue=l.id_langue "
                + "and p.id_person=?"; 

        List<Person> personsList = this.jdbcTemplate.query(sql, new Object[]{pId}, new PersonMapper());

        // person not found
        if (personsList.isEmpty()) {
            log.info("person does not exists");
            return null; 

            // list contains exactly 1 element
        } else if (personsList.size() == 1 ) { 
            log.info("id_person=" + personsList.get(0));

            return personsList.get(0); 

            // list contains more than 1 element
        } else {
            log.error("Table person : person is not unique");
            return null;
        }
    }
    

    public Person addPerson(final String pMail, final String pMdp, final String pLangue) {

        String nSql = "insert "
                + "into person (id_person, id_langue, mail, mdp, stamp) "
                //+ "values (seq_person.nextval, ?, ?)"
                + "select seq_person.nextval, l.id_langue, ?, ?, 0 "
                + "from langue l "
                + "where l.nom=?";

        this.jdbcTemplate.update(nSql, pMail, pMdp, pLangue);

        // get last id_person inserted
        Long LastIdPersonInserted = this.jdbcTemplate.queryForObject(SQL_GET_LAST_ID_INSERTED, Long.class);
        log.debug("LastIdPersonInserted=" + LastIdPersonInserted);
        log.info("id_person=" + LastIdPersonInserted);

        return getPerson(LastIdPersonInserted); 

    }

    
    public MarquePage addPersonMqp(long personId, String lien, String nom) {

        String sql = "insert "
                + "into marquepage (id_marquepage,id_person, lien, nom) "
                + "values (seq_marquepage.nextval, ?, ?, ?)";

        this.jdbcTemplate.update(sql,personId,lien,nom);	


        // get last id_marquepage inserted
        Long LastIdMarquePageInserted = this.jdbcTemplate.queryForObject(SQL_GET_LAST_ID_INSERTED, Long.class);

        log.debug("LastIdMarquePageInserted=" + LastIdMarquePageInserted);

        MarquePageDAO myMarquePageDAO = new MarquePageDAO(jdbcTemplate);

        return myMarquePageDAO.find(LastIdMarquePageInserted); 
    }

    
    public Person updatePerson(Long pPersonId, final String pMail, final String pLangue) {

        String nSql = "update person set mail = ?, ";
        
        nSql = nSql + "id_langue = (select l.id_langue from langue l where l.nom=?) ";
        nSql = nSql + "where id_person = ?";
        
        log.info("updatePerson=" + nSql);
        
        this.jdbcTemplate.update(nSql, pMail, pLangue, pPersonId);

        return getPerson(pPersonId); 
    }
    
    
    public Person updateStampById(Long pPersonId, Long pTimestamp) {

        String nSql = "update person set stamp = ? ";
        nSql = nSql + "where id_person = ?";
        
        log.info("updateStampById=" + nSql);
        
        this.jdbcTemplate.update(nSql, pTimestamp, pPersonId);

        return getPerson(pPersonId); 
    }
}
