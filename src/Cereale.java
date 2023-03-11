public class Cereale extends Aliment{
    private TipCereale tipCereale;
    public Cereale(Long dExp, String ing, Double pret, TipCereale tipC){
        setTip(Categorie.PAINE);
        setCalorii(2.56);
        setDataExp(dExp);
        setIngrediente(ing);
        setPret(pret);
        this.tipCereale = tipC;
    }
    public TipCereale getTipCereale()
    {
        return tipCereale;
    }
    public void setTipCereale(TipCereale tipC)
    {
        this.tipCereale = tipC;
    }

    @Override
    public String toString() {
        return "CEREALE:  - " + getTipCereale() + " -  " + super.toString();
    }
}
