import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class Imobiliare implements Comparable<Imobiliare>{
    public static class Utilitati{
        Integer nr_bai;
        Integer nr_dormitoare;
        Boolean are_balcon;
        List<Camera> camere;
        public Utilitati(Integer nr_bai, Integer nr_dormitoare, Boolean are_balcon, List<Camera> camere){
            this.nr_bai = nr_bai;
            this.nr_dormitoare = nr_dormitoare;
            this.are_balcon = are_balcon;
            this.camere = camere;
        }
        public void setNr_bai(Integer NB){
            this.nr_bai = NB;
        }
        public Integer getNr_bai(){
            return this.nr_bai;
        }
        public void setNr_dormitoare(Integer ND){
            this.nr_dormitoare = ND;
        }
        public Integer getNr_dormitoare(){
            return this.nr_dormitoare;
        }
        public void setAre_balcon(Boolean AB){
            this.are_balcon = AB;
        }
        public Boolean getAre_balcon(){
            return this.are_balcon;
        }
        public void setCamere(List<Camera> camere){
            this.camere = camere;
        }
        public List<Camera> getCamere(){
            return this.camere;
        }
        @Override
        public String toString() {
            String camere_S = "";
            for(Camera c: getCamere())
                camere_S = camere_S + " " + c.toString();
            if(getAre_balcon()){
                return "Utilitati ---->  Nr bai: " + getNr_bai() + " //  Nr dormitoare: " + getNr_dormitoare() + " // Are balcon " + camere_S;
            }
            return "Utilitati ---->  Nr bai: " + getNr_bai() + " //  Nr dormitoare: " + getNr_dormitoare() + " // NU are balcon "  + camere_S;
        }
    }
    Integer nr_apartament;
    Double pret;
    Double dimensiune;
    LocalDate data;
    String status;
    Utilitati utilitati;
    public Imobiliare(Integer nrAp, Double pret, Double dimensiune, LocalDate data, String status, Utilitati utilitati) {
        this.nr_apartament = nrAp;
        this.pret = pret;
        this.dimensiune = dimensiune;
        this.data = data;
        this.status = status;
        this.utilitati = utilitati;
    }
    public void setNr_apartament(Integer nrAp) {
        this.nr_apartament = nrAp;
    }
    public Integer getNr_apartament() {
        return this.nr_apartament;
    }
    public void setPret(Double pret) {
        this.pret = pret;
    }
    public Double getPret() {
        return this.pret;
    }
    public void setDimensiune(Double dimens){
        this.dimensiune = dimens;
    }
    public Double getDimensiune() {
        return this.dimensiune;
    }
    public void setData(LocalDate data) {
        this.data = data;
    }
    public LocalDate getData() {
        return this.data;
    }
    public void setStatus(String status){
        this.status = status;
    }
    public String getStatus(){
        return this.status;
    }
    public void setUtilitati(Utilitati utilitati){
        this.utilitati = utilitati;
    }
    public Utilitati getUtilitati(){
        return this.utilitati;
    }
    public Integer calcNrCamere(){
        return this.utilitati.nr_bai + this.utilitati.nr_dormitoare + 2;
    }
    @Override
    public int compareTo(Imobiliare imobil) {
        return Double.compare(this.calcNrCamere(), imobil.calcNrCamere());
    }
    @Override
    public String toString() {
        if(getStatus().equals("vandut"))
            return "APARTAMENT VANDUT ---> Nr apartament: " + getNr_apartament() + " //  Pret: " + getPret() + " //  Dimensiune: "
                + getDimensiune() + " metrii patrati //  Data cumparare: " + getData() + " // Numar camere: " + calcNrCamere() + " "
                + " // " + getUtilitati().toString() + "\n";
        return "APARTAMENT INCHIRIAT ---> Nr apartament: " + getNr_apartament() + " //  Pret: " + getPret() + " //  Dimensiune: "
                + getDimensiune() + " metrii patrati //  Data inchiriere: " + getData() + " // Numar camere: " + calcNrCamere() + " "
                + " // " + getUtilitati().toString() + "\n";
    }
}


/*
Firma iobiliare
        Vanzari wiiii
        Ap 2 camere, 3 camere, openspace, duplex
        Unele se inchiriaza, altele se vand

Nr ap
Pret depinde de inchiriere
dimensiune
data inchiriere / vindere
utilitati per camera(o baie/2, balcon/nu, are frigider)
adresa

1) proiectati
2) sortati dupa nr de camere
 */