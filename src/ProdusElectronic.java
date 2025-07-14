public class ProdusElectronic extends Produs {
    private int garantie;

    public ProdusElectronic(String cod, String nume, double pret, int stoc, Categorie categorie, Distribuitor distribuitor, int garantie) {
        super(cod, nume, pret, stoc, categorie, distribuitor);
        this.garantie = garantie;
    }

    @Override
    public String toString() {
        return super.toString() + ", garantie: " + garantie + " luni";
    }
}