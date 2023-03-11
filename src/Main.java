import java.util.List;
public class Main {
    public static void main(String[] args)
    {
        List<Aliment> lAlimente = List.of(
                new Carne(1679500200000L, "carne, E59, E60",33.3),
                new Lapte(1682500200000L, "lapte, apa", 5.99),
                new Cereale(1685500200000L,"grau, E30, zahar", 10.2, TipCereale.NESQUIK),
                new Carne(1678500200000L, "carne, E59, E61",21.3),
                new Lapte(1681500200000L, "lapte, cam atat", 4.99),
                new Cereale(1691500200000L,"grau, E30, zahar", 10.2, TipCereale.CHOCAPIC));
        System.out.println("\n-------------------INITIAL-------------------\n");
        System.out.println(lAlimente);
        System.out.println("----------------------------------------------\n\n");
        lAlimente = lAlimente.stream().sorted().toList();
        System.out.println("-----------------DUPA  SORTARE-----------------\n");
        System.out.println(lAlimente);
        System.out.println("-----------------------------------------------\n\n");
    }
}

