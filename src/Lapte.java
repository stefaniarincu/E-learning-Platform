public class Lapte extends Aliment{
    public Lapte(Long dExp, String ing, Double pret){
        setTip(Categorie.LACTATE);
        setCalorii(3.05);
        setDataExp(dExp);
        setIngrediente(ing);
        setPret(pret);
    }

    @Override
    public String toString() {
        return "LAPTE: " + super.toString();
    }
}
