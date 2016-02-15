package marcopolo.entity;

public class Fiche {

    private String mail;

    private String mdp;

    private String langue;
    
    private Long stamp;

    public Fiche()  {

    }

    public String getMail() {
        return this.mail;
    }

//    public void setMail(final String mail) {
//        this.mail = mail;
//    }


    public String getMdp() {
        return this.mdp;
    }

//    public void setMdp(final String mdp) {
//        this.mdp = mdp;
//    }

    public String getLangue() {
        return langue;
    }

    public Long getStamp() {
        return stamp;
    }
    
//    public void setLangue(final String pLangue) {
//        this.langue = pLangue;
//    }
    
    @Override
    public String toString() {
        
        String nReturn ="{"
                + "\"mail\":\"" + mail + "\","
                + "\"mdp\":\"" + mdp + "\","
                + "\"langue\":\"" + langue + "\""
                + "}";
        
        return nReturn;
    }
    
} // class