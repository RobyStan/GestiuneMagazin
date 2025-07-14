public class Distribuitor implements Comparable<Distribuitor> {
    private int id;  // adăugat
    private String nume;
    private String locatie;

    public Distribuitor(int id, String nume, String locatie) {
        this.id = id;
        this.nume = nume;
        this.locatie = locatie;
    }

    public Distribuitor(String nume, String locatie) {
        this(-1, nume, locatie);
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNume() { return nume; }
    public void setNume(String nume) { this.nume = nume; }

    public String getLocatie() { return locatie; }
    public void setlocatie(String locatie) { this.locatie = locatie; }

    @Override
    public String toString() {
        return nume + " (" + locatie + ")";
    }

    @Override
    public int compareTo(Distribuitor altul) {
        return this.nume.compareTo(altul.nume);
    }
}
