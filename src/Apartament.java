import java.time.LocalDate;
import java.util.List;

public class Apartament implements Comparable<Apartament> {
    public static class Utilitati{
        private Integer nrBai;
        private Integer nrDormitoare;
        private Boolean areBalcon;
        private List<Camera> camere;
        public Utilitati(Integer nrBai, Integer nrDormitoare, Boolean areBalcon, List<Camera> camere){
            this.nrBai = nrBai;
            this.nrDormitoare = nrDormitoare;
            this.areBalcon = areBalcon;
            this.camere = camere;
        }

        public void setNrBai(Integer NB) {
            this.nrBai = NB;
        }

        public Integer getNrBai() {
            return this.nrBai;
        }

        public void setNrDormitoare(Integer ND) {
            this.nrDormitoare = ND;
        }

        public Integer getNrDormitoare() {
            return this.nrDormitoare;
        }

        public void setAreBalcon(Boolean AB) {
            this.areBalcon = AB;
        }

        public Boolean getAreBalcon() {
            return this.areBalcon;
        }

        public void setCamere(List<Camera> camere) {
            this.camere = camere;
        }

        public List<Camera> getCamere() {
            return this.camere;
        }

        @Override
        public String toString() {
            String camere_S = "";
            for(Camera c: getCamere()) {
                camere_S = camere_S + " " + c.toString();
            }

            if(getAreBalcon()) {
                return "Utilitati ---->  Nr bai: " + getNrBai() + " //  Nr dormitoare: " + getNrDormitoare() + " // Are balcon " + camere_S;
            }

            return "Utilitati ---->  Nr bai: " + getNrBai() + " //  Nr dormitoare: " + getNrDormitoare() + " // NU are balcon "  + camere_S;
        }
    }
    private Integer nrApartament;
    private Double pret;
    private Double dimensiune;
    private LocalDate data;
    private String status;
    private Utilitati utilitati;

    public Apartament(Integer nrAp, Double pret, Double dimensiune, LocalDate data, String status, Utilitati utilitati) {
        this.nrApartament = nrAp;
        this.pret = pret;
        this.dimensiune = dimensiune;
        this.data = data;
        this.status = status;
        this.utilitati = utilitati;
    }

    public void setNrApartament(Integer nrAp) {
        this.nrApartament = nrAp;
    }

    public Integer getNrApartament() {
        return this.nrApartament;
    }

    public void setPret(Double pret) {
        this.pret = pret;
    }

    public Double getPret() {
        return this.pret;
    }

    public void setDimensiune(Double dimens) {
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

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

    public void setUtilitati(Utilitati utilitati) {
        this.utilitati = utilitati;
    }

    public Utilitati getUtilitati() {
        return this.utilitati;
    }

    public Integer calcNrCamere() {
        return this.utilitati.nrBai + this.utilitati.nrDormitoare + 2;
    }

    @Override
    public int compareTo(Apartament imobil) {
        return Double.compare(this.calcNrCamere(), imobil.calcNrCamere());
    }

    @Override
    public String toString() {
        if(getStatus().equals("vandut")) {
            return "APARTAMENT VANDUT ---> Nr apartament: " + getNrApartament() + " //  Pret: " + getPret() + " //  Dimensiune: "
                    + getDimensiune() + " metrii patrati //  Data cumparare: " + getData() + " // Numar camere: " + calcNrCamere() + " "
                    + " // " + getUtilitati().toString() + "\n";
        }

        return "APARTAMENT INCHIRIAT ---> Nr apartament: " + getNrApartament() + " //  Pret: " + getPret() + " //  Dimensiune: "
                + getDimensiune() + " metrii patrati //  Data inchiriere: " + getData() + " // Numar camere: " + calcNrCamere() + " "
                + " // " + getUtilitati().toString() + "\n";
    }
}