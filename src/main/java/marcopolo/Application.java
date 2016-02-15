package marcopolo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class Application implements CommandLineRunner {

	private static Log log = LogFactory.getLog(Application.class);
	
	/* le framework instancie jdbcTemplate (connexion à une BDD)  */
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	
    public static void main(String[] args) throws Exception {
       SpringApplication.run(Application.class, args);	
    }
	
	@Override
	public void run(String... args) throws Exception {
		log.info("run.execute");
	}
		
}