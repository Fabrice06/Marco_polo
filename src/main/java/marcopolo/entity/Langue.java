package marcopolo.entity;


import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Representation of Langue
 *
 */
public class Langue extends ResourceSupport {

	private Long idLangue;
	private String nom;
	
	public Langue() {
		
	}

	@JsonIgnore
	public Long getIdLangue() {
		return idLangue;
	}

	public void setIdLangue(final Long idLangue) {
		this.idLangue = idLangue;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(final String nom) {
		this.nom = nom;
	}


} // class
