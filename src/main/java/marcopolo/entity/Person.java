package marcopolo.entity;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * <b>Représente une personne identifié par son mail, son mdp et sa langue.</b>
 * 
 */
public class Person extends ResourceSupport {

    private long idPerson;

    private String mail;

    private String mdp;

    private String langue;
    
    private Long stamp;

    /**
     * <b>Constructeur par default</b>
     * <p>
     *     Permets la création d'une nouvelle personne sans passer de parametres
     * </p>
     */
    public Person()  {

    }

    
    /**
     * Retourne l'id de la personne.
     *
     * @return L'id personne, de type Long.
     */
    public Long getIdPerson() {
        return this.idPerson;
    }

    
    /**
     * Change l'id de la personne.
     *
     * @param idPerson
     *      id de la personne, de type Long.
     */
    public void setIdPerson(final Long idPerson) {
        this.idPerson = idPerson;
    }

    
    /**
     * Retourne le mail de la personne.
     *
     * @return Le mail de la personne, de type String.
     */
    public String getMail() {
        return this.mail;
    }

    
    /**
     * Change le mail de la personne.
     *
     * @param mail
     *      mail de la personne, de type String.
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    
    /**
     * Retourne le mot de passe (mdp) de la personne.
     *
     * @return Le mail de la personne, de type String.
     */
    @JsonIgnore
    public String getMdp() {
        return this.mdp;
    }
    
    
    /**
     * Change le mot de passe de la personne.
     *
     * @param mdp
     *      mdp de la personne, de type String.
     */
    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    
    /* (non-Javadoc)
     * @see org.springframework.hateoas.ResourceSupport#toString()
     */
    @Override
    public String toString() {
        return String.format(
                "Person[mail='%s']",
                mail);
    }

    
    /**
     * @return La langue choisie par l'utilisateur (String)
     */
    public String getLangue() {
        return langue;
    }

    
    /**
     * Change la langue choisie par l'utilisateur
     * 
     * @param pLangue
     */
    public void setLangue(final String pLangue) {
        this.langue = pLangue;
    }

    
    /**
     * @return Le timestamp du dernier service REST consommé par l'utilisateur
     */
    public Long getStamp() {
        return stamp;
    }

    
    /**
     * Change le timestamp du dernier service REST consommé par l'utilisateur
     * 
     * @param pStamp
     */
    public void setStamp(final Long pStamp) {
        this.stamp = pStamp;
    }
    
}