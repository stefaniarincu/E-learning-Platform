import java.time.LocalDate;

public class OpenSpace extends Apartament {
    public OpenSpace(Integer nrAp, Double pret, Double dimensiune, LocalDate data, String status, Utilitati utilitati) {
        super(nrAp, pret, dimensiune, data, status, utilitati);
    }

    public Integer calcNrCamere() {
        return this.getUtilitati().getNrBai() + this.getUtilitati().getNrDormitoare() + 1;
    }

    @Override
    public String toString() {
        if(getStatus().equals("vandut")) {
            return "APARTAMENT VANDUT --> TIP OPEN SPACE ---> Nr apartament: " + getNrApartament() + " //  Pret: " + getPret() + " //  Dimensiune: "
                    + getDimensiune() + " metrii patrati //  Data cumparare: " + getData() + " // Numar camere: " + calcNrCamere() + " "
                    + " // " + getUtilitati().toString() + "\n";
        }

        return "APARTAMENT INCHIRIAT --> TIP OPEN SPACE ---> Nr apartament: " + getNrApartament() + " //  Pret: " + getPret() + " //  Dimensiune: "
                + getDimensiune() + " metrii patrati //  Data inchiriere: " + getData() + " // Numar camere: " + calcNrCamere() + " "
                + " // " + getUtilitati().toString() + "\n";
    }
}
