import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) {
        List<String> dormitor = new ArrayList<>();
        dormitor.add("birou");
        dormitor.add("pat");

        List<String> bucatarie = new ArrayList<>();
        bucatarie.add("aragaz");
        bucatarie.add("masa");
        bucatarie.add("frigider");

        List<String> sufragerie = new ArrayList<>();
        sufragerie.add("biblioteca");
        sufragerie.add("masa");
        sufragerie.add("televizor");

        Camera dormitor1 = new Camera("dormitor", dormitor);
        dormitor.add("dulap");
        Camera dormitor2 = new Camera("dormitor", dormitor);
        Camera bucatarie1 = new Camera("bucatarie", bucatarie);
        Camera sufragerie1 = new Camera("sufragerie", sufragerie);

        List<Camera> apart1 = new ArrayList<>();
        apart1.add(dormitor1);
        apart1.add(bucatarie1);

        List<Camera> apart2 = new ArrayList<>();
        apart2.add(dormitor1);
        apart2.add(dormitor2);
        apart2.add(sufragerie1);

        List<Apartament> Apartamente = List.of(
                new OpenSpace(10, 566.4, 50.5, LocalDate.of(2022,3,13), "vandut", new Apartament.Utilitati(1, 2, false, apart1)),
                new Duplex(24, 1200.0, 99.9, LocalDate.of(2022,2,22), "vandut", new Apartament.Utilitati(3, 5, true, apart2)),
                new Duplex(26, 1100.0, 98.9, LocalDate.of(2022,2,23), "vandut", new Apartament.Utilitati(3, 4, true, apart1)),
                new BiCameral(1, 709.0, 66.9, LocalDate.of(2023,2,22), "inchiriat", new Apartament.Utilitati(1, 1, true, apart1)),
                new BiCameral(11, 720.0, 75.9, LocalDate.of(2022,12,21), "inchiriat", new Apartament.Utilitati(2, 2, false, apart2))
               );

        System.out.println("\n-------------------INITIAL-------------------\n");
        System.out.println(Apartamente);
        System.out.println("----------------------------------------------\n\n");

        Apartamente = Apartamente.stream().sorted().toList();

        System.out.println("-----------------DUPA  SORTARE-----------------\n");
        System.out.println(Apartamente);
        System.out.println("-----------------------------------------------\n\n");

        HashMap<String, Integer> Statistica = new HashMap<>();
        Statistica.put("bicameral", 0);
        Statistica.put("tricameral", 0);
        Statistica.put("openspace", 0);
        Statistica.put("duplex", 0);

        TreeMap<String, Integer> StatisticaV = new TreeMap<>();
        StatisticaV.put("bicameral", 0);
        StatisticaV.put("tricameral", 0);
        StatisticaV.put("openspace", 0);
        StatisticaV.put("duplex", 0);

        System.out.println("-----------------AFISARE UTILITATI-----------------\n");
        for (Apartament imobil: Apartamente) {
            if (imobil instanceof BiCameral) {
                Statistica.computeIfPresent("bicameral", (k, v) -> v + 1);

                if (imobil.getStatus().equals("vandut")) {
                    StatisticaV.computeIfPresent("bicameral", (k, v) -> v + 1);
                }

                System.out.print("APARTAMENT CU 2 CAMERE ----> ");
                System.out.println(imobil.getUtilitati().toString());
            } else if (imobil instanceof TriCameral) {
                Statistica.computeIfPresent("tricameral", (k, v) -> v + 1);

                if (imobil.getStatus().equals("vandut")) {
                    StatisticaV.computeIfPresent("tri cameral", (k, v) -> v + 1);
                }

                System.out.print("APARTAMENT CU 3 CAMERE ----> ");
                System.out.println(imobil.getUtilitati().toString());
            } else if (imobil instanceof OpenSpace) {
                Statistica.computeIfPresent("openspace", (k, v) -> v + 1);

                if (imobil.getStatus().equals("vandut")) {
                    StatisticaV.computeIfPresent("openspace", (k, v) -> v + 1);
                }

                System.out.print("APARTAMENT OPEN SPACE ----> ");
                System.out.println(imobil.getUtilitati().toString());
            } else if (imobil instanceof Duplex) {
                Statistica.computeIfPresent("duplex", (k, v) -> v + 1);

                if (imobil.getStatus().equals("vandut")) {
                    StatisticaV.computeIfPresent("duplex", (k, v) -> v + 1);
                }

                System.out.print("APARTAMENT DUPLEX ----> ");
                System.out.println(imobil.getUtilitati().toString());
            }
        }
        System.out.println("-----------------------------------------------\n\n");

        TreeMap<Integer, String> PeInvers = new TreeMap<>();
        for(String key: StatisticaV.keySet()) {
            PeInvers.computeIfPresent((-1) * StatisticaV.get(key), (k, v) -> v + ", " + key);
            PeInvers.computeIfAbsent((-1) * StatisticaV.get(key), (v) -> key);
        }

        System.out.println("-----------------AFISARE TOP VANZARI-----------------\n");

        for(Integer key: PeInvers.keySet()) {
            System.out.println(PeInvers.get(key) + " :   " + (-1) * key + " apartamente vandute");
        }

        System.out.println("-----------------------------------------------\n\n");

        System.out.println("-----------------AFISARE NR APARTAMENTE DPDV AL TIPULUI-----------------\n");

        for(String key: Statistica.keySet()) {
            System.out.println(key.toUpperCase() + " --- " + Statistica.get(key));
        }

        System.out.println("-----------------------------------------------\n\n");
    }
}