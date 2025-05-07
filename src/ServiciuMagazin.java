import java.util.*;

public class ServiciuMagazin {
    private List<Produs> produse = new ArrayList<>();
    private TreeSet<Distribuitor> distribuitori = new TreeSet<>();
    private Map<String, List<Produs>> produsePeCategorie = new HashMap<>();

    public void adaugaProdus(Produs p) {
        produse.add(p);
        distribuitori.add(p.getDistribuitor());
        produsePeCategorie.computeIfAbsent(p.getCategorie().getNume(), k -> new ArrayList<>()).add(p);
    }

    public void stergeProdus(String cod) {
        produse.removeIf(p -> p.getCod().equals(cod));
    }

    public void actualizeazaStoc(String numeProdus, int nouStoc) {
        for (Produs p : produse) {
            if (p.getNume().equals(numeProdus)) {
                p.setStoc(nouStoc);
                return;
            }
        }
    }

    public List<Produs> cautaProduseDupaCategorie(String categorie) {
        return produsePeCategorie.getOrDefault(categorie, new ArrayList<>());
    }

    public void afiseazaProduseSortateDupaPretCresc() {
        produse.stream()
                .sorted(Comparator.comparingDouble(Produs::getPret))
                .forEach(System.out::println);
    }

    public void afiseazaProduseSortateDupaPretDesc() {
        produse.stream()
                .sorted(Comparator.comparingDouble(Produs::getPret).reversed())
                .forEach(System.out::println);
    }

    public void afiseazaDistribuitori() {
        distribuitori.forEach(System.out::println);
    }

    public void afiseazaProduseSubStoc(int prag) {
        produse.stream()
                .filter(p -> p.getStoc() < prag)
                .forEach(System.out::println);
    }

    public double calculeazaValoareStoc() {
        return produse.stream()
                .mapToDouble(p -> p.getPret() * p.getStoc())
                .sum();
    }

    public void afiseazaToateProdusele() {
        produse.forEach(System.out::println);
    }

    public boolean produsExista(String cod) {
        for (Produs produs : produse) {
            if (produs.getCod().equals(cod)) {
                return true;
            }
        }
        return false;
    }
}
