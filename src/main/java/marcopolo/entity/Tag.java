package marcopolo.entity;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Representation of tag
 *
 */

public class Tag extends ResourceSupport {

	private Long idTag;
	private Long idMqp;
	
	private String cle;
	private String valeur;
	
	public Tag() {

	}

	public String getCle() {
		return cle;
	}

	public void setCle(String cle) {
		this.cle = cle;
	}
	
	public String getValeur() {
		return valeur;
	}

	public void setValeur(String valeur) {
		this.valeur = valeur;
	}

	


	@JsonIgnore
	public Long getIdTag() {
		return idTag;
	}


	public void setIdTag(Long idTag) {
		this.idTag = idTag;
	}

	@JsonIgnore
	public Long getIdMqp() {
		return idMqp;
	}


	public void setIdMqp(Long idMqp) {
		this.idMqp = idMqp;
	}




}
