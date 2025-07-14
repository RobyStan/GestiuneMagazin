public class ProdusVestimentar extends Produs {
    private String marime;

    public ProdusVestimentar(String cod, String nume, double pret, int cantitate,
                             Categorie categorie, Distribuitor distribuitor, String marime) {
        super(cod, nume, pret, cantitate, categorie, distribuitor);
        this.marime = marime;
    }

    @Override
    public String toString() {
        return super.toString() + ", Marime: " + marime;
    }
}