package marcopolo.entity;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Representation of Cle
 *
 */
public class Cle extends ResourceSupport {

	private Long idCle;
	private String cle;
	
	// attribut nom de la table langue
	private String langue;

	public Cle() {

	}

	public String getCle() {
		return cle;
	}

	public void setCle(String cle) {
		this.cle = cle;
	}

	@JsonIgnore
	public long getIdCle() {
		return idCle;
	}

	public void setIdCle(Long idCle) {
		this.idCle = idCle;
	}

	public String getLangue() {
		return langue;
	}

	public void setLangue(String langue) {
		this.langue = langue;
	}

}
