public class ProdusVestimentar extends Produs {
    private String marime;

    public ProdusVestimentar(String cod, String nume, double pret, int stoc, Categorie categorie, Distribuitor distribuitor, String marime) {
        super(cod, nume, pret, stoc, categorie, distribuitor);
        this.marime = marime;
    }

    @Override
    public String toString() {
        return super.toString() + ", Marime: " + marime;
    }
}