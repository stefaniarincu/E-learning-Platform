import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
public class Aliment implements Comparable<Aliment> {
    protected Long dataExp;
    protected String ingrediente;
    protected Double pret;
    protected Double calorii;
    protected Categorie tip;
    public Aliment(Long dExp, String ing, Double pret, Double cal, Categorie tip)
    {
        this.dataExp = dExp;
        this.ingrediente = ing;
        this.pret = pret;
        this.calorii = cal;
        this.tip = tip;
    }
    public Aliment()
    {
    }
    public Long getDataExp()
    {
        return dataExp;
    }
    public void setDataExp(Long dExp)
    {
        this.dataExp = dExp;
    }
    public String getIngrediente()
    {
        return ingrediente;
    }
    public void setIngrediente(String ing)
    {
        this.ingrediente = ing;
    }
    public Double getPret()
    {
        return pret;
    }
    public void setPret(Double pret)
    {
        this.pret = pret;
    }
    public Double getCalorii()
    {
        return calorii;
    }
    public void setCalorii(Double cal)
    {
        this.calorii = cal;
    }
    public Categorie getTip()
    {
        return tip;
    }
    public void setTip(Categorie tip)
    {
        this.tip = tip;
    }

    @Override
    public int compareTo(Aliment o) {
        return Double.compare(this.getCalorii(), o.getCalorii());
    }

    @Override
    public String toString() {
        DateFormat dataAux = new SimpleDateFormat("dd-MMM-yyyy");
        Date data = new Date(getDataExp());
        return "Categoria: " + getTip() + " //  Data expirarii: " + dataAux.format(data) + " //  Ingrediente: "
                + getIngrediente() + " //  Pret: " + getPret() + " // Calorii: " + getCalorii() + "\n";
    }
}