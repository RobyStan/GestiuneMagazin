public class Produs {
    private String cod;
    private String nume;
    private double pret;
    private Stoc stoc;
    private Categorie categorie;
    private Distribuitor distribuitor;

    public Produs(String cod, String nume, double pret, int cantitate, Categorie categorie, Distribuitor distribuitor) {
        this.cod = cod;
        this.nume = nume;
        this.pret = pret;
        this.stoc = new Stoc(cantitate);
        this.categorie = categorie;
        this.distribuitor = distribuitor;
    }

    public String getCod() {
        return cod;
    }

    public String getNume() {
        return nume;
    }

    public double getPret() {
        return pret;
    }

    public int getStoc() {
        return stoc.getCantitate();
    }

    public void setStoc(int cantitate) {
        this.stoc.setCantitate(cantitate);
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public Distribuitor getDistribuitor() {
        return distribuitor;
    }

    @Override
    public String toString() {
        return cod + ": " + nume + " (" + pret + " RON, stoc: " + getStoc() + ")";
    }
}
