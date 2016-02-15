package marcopolo.entity;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonIgnore;



/**
 * <b>Représente une personne identifié par son mail et son mdp.</b>
 * 
 */
public class Person extends ResourceSupport {



    /**
     * Id unique de la personne
     * <p><b>Attention:</b><br>
     * l'increment de l'id personne est gére en BDD par la séquence seq_person.
     * </p>
     */

    private long idPerson;

    /**
     * Mail de la personne lui servant de login
     */
    private String mail;

    /**
     * Mdp de la personne 
     */
    private String mdp;

    private String langue;
    
    private Long stamp;

    //    private ArrayList<MarquePage> listMarquePages;
    //    
    //    private ArrayList<Preference> listPreferences;



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
     * @return L'id personne, de type long.
     */

    public long getIdPerson() {
        return this.idPerson;
    }

    /**
     * Change l'id de la personne.
     *
     * @param idPerson
     *      id de la personne, de type long.
     */


    public void setId(long idPerson) {
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

    @Override
    public String toString() {
        return String.format(
                "Person[mail='%s']",
                mail);
    }

    public String getLangue() {
        return langue;
    }

    public void setLangue(final String pLangue) {
        this.langue = pLangue;
    }

    public Long getStamp() {
        return stamp;
    }

    public void setStamp(Long pStamp) {
        this.stamp = pStamp;
    }
    
}