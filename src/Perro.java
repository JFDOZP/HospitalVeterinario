public class Perro extends Paciente {
    private String raza;
    private boolean vacunado;

    public Perro(String nombre, double peso, int edad, String raza, boolean vacunado) {
        super(nombre, peso, edad, vacunado ? "INGRESADO" : "CUARENTENA", 10.0);
        this.raza = raza;
        this.vacunado = vacunado;
    }

    public String getRaza() { return raza; }
    public void setRaza(String raza) { this.raza = raza; }

    public boolean isVacunado() { return vacunado; }
    public void setVacunado(boolean vacunado) {
        this.vacunado = vacunado;
        if (vacunado && getEstado().equals("CUARENTENA")) {
            setEstado("INGRESADO");
        }
    }
}