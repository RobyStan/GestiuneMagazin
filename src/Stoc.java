public class Stoc {
    private int cantitate;

    public Stoc(int cantitate) {
        this.cantitate = cantitate;
    }

    public int getCantitate() { return cantitate; }
    public void setCantitate(int cantitate) { this.cantitate = cantitate; }

    public String toString() {
        return "Stoc: " + cantitate;
    }
}
