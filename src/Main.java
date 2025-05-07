import java.util.*;

public class Main {
    public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);
            ServiciuMagazin serviciu = new ServiciuMagazin();

            Categorie electronice = new Categorie("Electronice");
            Categorie alimente = new Categorie("Alimente");
            Categorie vestimentar = new Categorie("Vestimentar");

            Distribuitor d1 = new Distribuitor("Altex", "Bucuresti");
            Distribuitor d2 = new Distribuitor("MegaImage", "Cluj");

            serviciu.adaugaProdus(new ProdusElectronic("001", "Laptop", 3500.0, 10, electronice, d1, 24));
            serviciu.adaugaProdus(new ProdusAlimentar("002", "Paine", 5.5, 50, alimente, d2, "2025-12-31"));
            serviciu.adaugaProdus(new ProdusElectronic("003", "Telefon", 2500.0, 5, electronice, d1, 12));
            serviciu.adaugaProdus(new ProdusVestimentar("004", "Tricou", 99.99, 20, vestimentar, d2, "M"));


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
                        if (serviciu.produsExista(cod)) {
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
                            case 1 -> "Alimente";
                            case 2 -> "Electronice";
                            case 3 -> "Vestimentar";
                            default -> "Necunoscut";
                        };
                        Categorie c = new Categorie(numeCategorie);

                        System.out.print("Distribuitor: ");
                        String numeDistribuitor = scanner.nextLine();
                        System.out.print("Locatie distribuitor: ");
                        String locatieDistribuitor = scanner.nextLine();
                        Distribuitor d = new Distribuitor(numeDistribuitor, locatieDistribuitor);

                        Produs produs;
                        if (tip == 1) {
                            System.out.print("Data expirare (YYYY-MM-DD): ");
                            String dataExpirare = scanner.nextLine();
                            produs = new ProdusAlimentar(cod, nume, pret, stoc, c, d, dataExpirare);
                        } else if (tip == 2) {
                            System.out.print("Garanție (luni): ");
                            int garantie = scanner.nextInt();
                            scanner.nextLine();
                            produs = new ProdusElectronic(cod, nume, pret, stoc, c, d, garantie);
                        } else if (tip == 3) {
                            System.out.print("Marime (XS/S/M/L/XL/XXL): ");
                            String marime = scanner.nextLine();
                            produs = new ProdusVestimentar(cod, nume, pret, stoc, c, d, marime);
                        } else {
                            System.out.println("Tip invalid.");
                            break;
                        }

                        serviciu.adaugaProdus(produs);
                        System.out.println("Produs adaugat cu succes.");
                    }

                    case Sterge_produs -> {
                        System.out.print("Cod produs de sters: ");
                        String cod = scanner.nextLine();
                        if (!serviciu.produsExista(cod)) {
                            System.out.println("Produsul cu acest cod nu exista.");
                            break;
                        }
                        serviciu.stergeProdus(cod);
                        System.out.println("Produs sters cu succes.");
                    }
                    case Actualizeaza_stoc -> {
                        System.out.print("Cod produs: ");
                        String cod = scanner.nextLine();
                        if (!serviciu.produsExista(cod)) {
                            System.out.println("Produsul cu acest cod nu exista.");
                            break;
                        }
                        System.out.print("Noul stoc: ");
                        int nouStoc = scanner.nextInt();
                        scanner.nextLine();
                        serviciu.actualizeazaStoc(cod, nouStoc);
                        System.out.println("Stoc actualizat.");
                    }
                    case Cauta_dupa_categorie -> {
                        System.out.print("Categorie: ");
                        String categorie = scanner.nextLine();
                        List<Produs> produse = serviciu.cautaProduseDupaCategorie(categorie);
                        if (produse.isEmpty()) {
                            System.out.println("Nu au fost gasite produse in aceasta categorie.");
                        } else {
                            produse.forEach(System.out::println);
                        }
                    }
                    case Afiseaza_produse_sortate_crescator -> serviciu.afiseazaProduseSortateDupaPretCresc();
                    case Afiseaza_produse_sortate_descrescator -> serviciu.afiseazaProduseSortateDupaPretDesc();
                    case Afiseaza_distribuitori -> serviciu.afiseazaDistribuitori();
                    case Afiseaza_produse_sub_stoc -> {
                        System.out.print("Prag: ");
                        int prag = scanner.nextInt();
                        scanner.nextLine();
                        serviciu.afiseazaProduseSubStoc(prag);
                    }
                    case Calculeaza_valoare_stoc -> System.out.println("Total: " + serviciu.calculeazaValoareStoc());
                    case Afiseaza_toate_produsele -> serviciu.afiseazaToateProdusele();
                    case Iesire -> {
                        System.out.println("Iesire din aplicatie.");
                        return;
                    }
                }
            }
    }
}