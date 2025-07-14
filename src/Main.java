import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {

            ProdusService produsService = new ProdusService();
            ProdusAlimentarService produsAlimentarService = new ProdusAlimentarService();
            CategorieService categorieService = new CategorieService();
            DistribuitorService distribuitorService = new DistribuitorService();
            ServiciuMagazin serviciuMagazin = new ServiciuMagazin();
            LogService logService = LogService.getInstance();

            List<Categorie> categorii = categorieService.getAll();
            List<Distribuitor> distribuitori = distribuitorService.getDistribuitori();

          //  distribuitorService.insertDistribuitor(new Distribuitor("Distribuitor1", "Locatie1"));
           // distribuitorService.insertDistribuitor(new Distribuitor("Distribuitor2", "Locatie2"));
            //distribuitorService.updateDistribuitor(new Distribuitor("Distribuitor2.1", "Locatie2.1" ), 13);

            //categorieService.insertCategorie(new Categorie(4, "Gradinarit"));
            // categorieService.updateCategorie(new Categorie(4, "Gradinarit"));

            while (true) {
                System.out.println("\nAlege o actiune:");
                Actiune[] actiuni = Actiune.values();
                for (int i = 0; i < actiuni.length; i++) {
                    System.out.println((i + 1) + ". " + actiuni[i]);
                }
                int opt = scanner.nextInt();
                scanner.nextLine();

                if (opt < 1 || opt > actiuni.length) {
                    System.out.println("Optiune invalida.");
                    continue;
                }

                Actiune actiune = actiuni[opt - 1];

                switch (actiune) {
                    case Adauga_produs -> {
                        System.out.print("Cod produs: ");
                        String cod = scanner.nextLine();

                        if (produsService.produsExista(cod)) {
                            System.out.println("Produsul cu acest cod exista deja.");
                            break;
                        }

                        System.out.print("Tip produs (1 = Alimentar, 2 = Electronic, 3 = Vestimentar): ");
                        int tip = scanner.nextInt();
                        scanner.nextLine();

                        System.out.print("Nume produs: ");
                        String nume = scanner.nextLine();
                        System.out.print("Pret: ");
                        double pret = scanner.nextDouble();
                        System.out.print("Stoc: ");
                        int stoc = scanner.nextInt();
                        scanner.nextLine();

                        String numeCategorie = switch (tip) {
                            case 1 -> "Alimentar";
                            case 2 -> "Electronic";
                            case 3 -> "Vestimentar";
                            default -> "Necunoscut";
                        };
                        Categorie c = categorii.stream()
                                .filter(cat -> cat.getNume().equalsIgnoreCase(numeCategorie))
                                .findFirst()
                                .orElse(null);

                        if (c == null) {
                            System.out.println("Categoria nu a fost gasita in baza de date.");
                            break;
                        }

                        System.out.print("Distribuitor: ");
                        String numeDistribuitor = scanner.nextLine();
                        System.out.print("Locatie distribuitor: ");
                        String locatieDistribuitor = scanner.nextLine();

                        Distribuitor d = distribuitori.stream()
                                .filter(dist -> dist.getNume().equalsIgnoreCase(numeDistribuitor)
                                        && dist.getLocatie().equalsIgnoreCase(locatieDistribuitor))
                                .findFirst()
                                .orElse(null);

                        if (d == null) {
                            System.out.println("Distribuitorul nu a fost gasit in baza de date.");
                            break;
                        }

                        Produs produs = null;
                        if (tip == 1) {
                            System.out.print("Data expirare (YYYY-MM-DD): ");
                            String dataExpirare = scanner.nextLine();
                            produs = new ProdusAlimentar(cod, nume, pret, stoc, c, d, dataExpirare);
                            produsAlimentarService.insertProdusAlimentar((ProdusAlimentar) produs);
                        } else if (tip == 2) {
                            System.out.print("Garanție (luni): ");
                            int garantie = scanner.nextInt();
                            scanner.nextLine();
                            produs = new ProdusElectronic(cod, nume, pret, stoc, c, d, garantie);
                        } else if (tip == 3) {
                            System.out.print("Marime (XS/S/M/L/XL/XXL): ");
                            String marime = scanner.nextLine();
                            produs = new ProdusVestimentar(cod, nume, pret, stoc, c, d, marime);
                            produsService.insertProdus(produs, c.getId(), d.getId());
                        } else {
                            System.out.println("Tip invalid.");
                            break;
                        }

                        System.out.println("Produs adaugat cu succes.");
                        logService.log("Adauga_produs");
                    }

                    case Sterge_produs -> {
                        System.out.print("Cod produs de sters: ");
                        String cod = scanner.nextLine();

                        if (!produsService.produsExista(cod)) {
                            System.out.println("Produsul cu acest cod nu exista.");
                            break;
                        }

                        produsService.deleteProdus(cod);
                        produsAlimentarService.deleteProdusAlimentar(cod);
                        System.out.println("Produs sters cu succes.");
                        logService.log("Sterge_produs");
                    }

                    case Actualizeaza_stoc -> {
                        System.out.print("Cod produs: ");
                        String cod = scanner.nextLine();

                        if (!produsService.produsExista(cod)) {
                            System.out.println("Produsul cu acest cod nu exista.");
                            break;
                        }

                        System.out.print("Noul stoc: ");
                        int nouStoc = scanner.nextInt();
                        scanner.nextLine();

                        System.out.println("Stoc actualizat.");
                        logService.log("Actualizeaza_stoc");
                    }

                    case Cauta_dupa_categorie -> {
                        System.out.print("Categorie: ");
                        String numeCategorie = scanner.nextLine();

                        List<Categorie> categoriiCautate = categorii.stream()
                                .filter(c -> c.getNume().equalsIgnoreCase(numeCategorie))
                                .toList();

                        if (categoriiCautate.isEmpty()) {
                            System.out.println("Categoria nu a fost gasita.");
                        } else {
                            List<Produs> toateProdusele = produsService.getProdus(categorii, distribuitori);

                            List<Produs> produseGasite = toateProdusele.stream()
                                    .filter(p -> p.getCategorie() != null &&
                                            categoriiCautate.stream()
                                                    .anyMatch(c -> c.getId() == p.getCategorie().getId()))
                                    .toList();

                            if (produseGasite.isEmpty()) {
                                System.out.println("Nu au fost gasite produse in aceasta categorie.");
                            } else {
                                produseGasite.forEach(System.out::println);
                            }
                        }

                        logService.log("Cauta_dupa_categorie");
                    }

                    case Afiseaza_produse_sortate_crescator -> {
                        List<Produs> toateProdusele = produsService.getProdus(categorii, distribuitori);
                        toateProdusele.sort((p1, p2) -> Double.compare(p1.getPret(), p2.getPret()));
                        toateProdusele.forEach(System.out::println);
                        logService.log("Afiseaza_produse_sortate_crescator");
                    }

                    case Afiseaza_produse_sortate_descrescator -> {
                        List<Produs> toateProdusele = produsService.getProdus(categorii, distribuitori);
                        toateProdusele.sort((p1, p2) -> Double.compare(p2.getPret(), p1.getPret()));
                        toateProdusele.forEach(System.out::println);
                        logService.log("Afiseaza_produse_sortate_descrescator");
                    }

                    case Afiseaza_distribuitori -> {
                        distribuitorService.getDistribuitori()
                                .forEach(System.out::println);
                        logService.log("Afiseaza_distribuitori");
                    }

                    case Afiseaza_produse_sub_stoc -> {
                        System.out.print("Prag: ");
                        int prag = scanner.nextInt();
                        scanner.nextLine();

                        List<Produs> toateProdusele = produsService.getProdus(categorii, distribuitori);
                        List<Produs> produseSubStoc = toateProdusele.stream()
                                .filter(p -> p.getStoc() < prag)
                                .toList();

                        if (produseSubStoc.isEmpty()) {
                            System.out.println("Nu exista produse cu stoc sub prag.");
                        } else {
                            produseSubStoc.forEach(System.out::println);
                        }

                        logService.log("Afiseaza_produse_sub_stoc");
                    }

                    case Calculeaza_valoare_stoc -> {
                        List<Produs> toateProdusele = produsService.getProdus(categorii, distribuitori);
                        double total = toateProdusele.stream()
                                .mapToDouble(p -> p.getPret() * p.getStoc())
                                .sum();
                        System.out.println("Total: " + total);
                        logService.log("Calculeaza_valoare_stoc");
                    }

                    case Afiseaza_toate_produsele -> {
                        List<Produs> toateProdusele = produsService.getProdus(categorii, distribuitori);
                        toateProdusele.forEach(System.out::println);
                        logService.log("Afiseaza_toate_produsele");
                    }

                    case Iesire -> {
                        System.out.println("Iesire din aplicatie.");
                        logService.log("Iesire");
                        return;
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Eroare la accesul bazei de date: " + e.getMessage());
        }
    }
}
