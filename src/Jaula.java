public class Jaula {
    private int numero;
    private String tamano;
    private String zona;
    private Paciente ocupante;

    public Jaula(int numero, String tamano, String zona) {
        this.numero = numero;
        this.tamano = tamano;
        this.zona = zona;
        this.ocupante = null;
    }

    public int getNumero() { return numero; }
    public void setNumero(int numero) { this.numero = numero; }

    public String getTamano() { return tamano; }
    public void setTamano(String tamano) { this.tamano = tamano; }

    public String getZona() { return zona; }
    public void setZona(String zona) { this.zona = zona; }

    public Paciente getOcupante() { return ocupante; }
    public void setOcupante(Paciente ocupante) { this.ocupante = ocupante; }

    public boolean estaLibre() {
        return this.ocupante == null;
    }
}