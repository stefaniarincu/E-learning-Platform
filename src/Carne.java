public class Carne extends Aliment{
    public Carne(Long dExp, String ing, Double pret){
        setTip(Categorie.MEZELURI);
        setCalorii(1.05);
        setDataExp(dExp);
        setIngrediente(ing);
        setPret(pret);
    }

    @Override
    public String toString() {
        return "CARNE:  " + super.toString();
    }
}
