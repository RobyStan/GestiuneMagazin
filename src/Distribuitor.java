 class Distribuitor implements Comparable<Distribuitor> {
    private String nume;
    private String adresa;

    public Distribuitor(String nume, String adresa) {
        this.nume = nume;
        this.adresa = adresa;
    }

    public String getNume() { return nume; }
    public String getAdresa() { return adresa; }

    public String toString() {
        return nume + " (" + adresa + ")";
    }

    @Override
    public int compareTo(Distribuitor altul) {
        return this.nume.compareTo(altul.nume);
    }
}
