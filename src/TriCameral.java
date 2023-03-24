import java.time.LocalDate;

public class TriCameral extends Apartament{
    public TriCameral(Integer nrAp, Double pret, Double dimensiune, LocalDate data, String status, Utilitati utilitati) {
        super(nrAp, pret, dimensiune, data, status, utilitati);
    }

    public Integer calcNrCamere() {
        return this.getUtilitati().getNrBai() + 4;
    }

    @Override
    public String toString() {
        if(getStatus().equals("vandut")) {
            return "APARTAMENT VANDUT --> TIP 3 CAMERE ---> Nr apartament: " + getNrApartament() + " //  Pret: " + getPret() + " //  Dimensiune: "
                    + getDimensiune() + " metrii patrati //  Data cumparare: " + getData() + " // Numar camere: " + calcNrCamere() + " "
                    + " // " + getUtilitati().toString() + "\n";
        }

        return "APARTAMENT INCHIRIAT --> TIP 3 CAMERE ---> Nr apartament: " + getNrApartament() + " //  Pret: " + getPret() + " //  Dimensiune: "
                + getDimensiune() + " metrii patrati //  Data inchiriere: " + getData() + " // Numar camere: " + calcNrCamere() + " "
                + " // " + getUtilitati().toString() + "\n";
    }
}
