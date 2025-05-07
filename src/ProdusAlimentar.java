public class ProdusAlimentar extends Produs {
    private String dataExpirare;

    public ProdusAlimentar(String cod, String nume, double pret, int stoc, Categorie categorie, Distribuitor distribuitor, String dataExpirare) {
        super(cod, nume, pret, stoc, categorie, distribuitor);
        this.dataExpirare = dataExpirare;
    }

    @Override
    public String toString() {
        return super.toString() + ", expira la: " + dataExpirare;
    }
}