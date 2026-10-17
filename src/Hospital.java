public class Hospital {
    private Paciente[] pacientes;
    private int contadorPacientes;
    private Jaula[] jaulas;

    public Hospital() {
        pacientes = new Paciente[10];
        contadorPacientes = 0;
        jaulas = new Jaula[6];
        inicializarJaulas();
        cargarDatosIniciales();
    }

    private void inicializarJaulas() {
        jaulas[0] = new Jaula(1, "S", "A");
        jaulas[1] = new Jaula(2, "M", "A");
        jaulas[2] = new Jaula(3, "M", "A");
        jaulas[3] = new Jaula(4, "L", "A");
        jaulas[4] = new Jaula(5, "S", "B");
        jaulas[5] = new Jaula(6, "M", "B");
    }

    public void cargarDatosIniciales() {

    }

    public Paciente buscarPaciente(String nombre) {
        for (int i = 0; i < contadorPacientes; i++) {
            if (pacientes[i].getNombre().equalsIgnoreCase(nombre)) {
                return pacientes[i];
            }
        }
        return null;
    }

    public boolean existeNombre(String nombre) {
        return buscarPaciente(nombre) != null;
    }

    public String ingresarPerro(String nombre, double peso, int edad, String raza, String siNo) {
        if (nombre == null || nombre.trim().isEmpty() || peso <= 0 || peso > 80 || edad < 0 || edad > 30) {
            return "ERROR: datos invalidos";
        }
        if (existeNombre(nombre)) {
            return "ERROR: nombre duplicado";
        }
        if (contadorPacientes >= 10) {
            return "ERROR: hospital lleno";
        }

        boolean vacunado = siNo.equalsIgnoreCase("si");
        Perro p = new Perro(nombre, peso, edad, raza, vacunado);
        pacientes[contadorPacientes] = p;
        contadorPacientes++;

        if (!vacunado) {
            return "OK: " + nombre + " ingresado en CUARENTENA";
        } else {
            return "OK: " + nombre + " ingresado";
        }
    }

    public String ingresarGato(String nombre, double peso, int edad) {
        if (nombre == null || nombre.trim().isEmpty() || peso <= 0 || peso > 15 || edad < 0 || edad > 30) {
            return "ERROR: datos invalidos";
        }
        if (existeNombre(nombre)) {
            return "ERROR: nombre duplicado";
        }
        if (contadorPacientes >= 10) {
            return "ERROR: hospital lleno";
        }

        Gato g = new Gato(nombre, peso, edad, 0);
        pacientes[contadorPacientes] = g;
        contadorPacientes++;
        return "OK: " + nombre + " ingresado";
    }

    public String ingresarAve(String nombre, double peso, int edad, double envergadura) {
        if (nombre == null || nombre.trim().isEmpty() || peso <= 0 || peso > 15 || edad < 0 || edad > 30 || envergadura <= 0) {
            return "ERROR: datos invalidos";
        }
        if (existeNombre(nombre)) {
            return "ERROR: nombre duplicado";
        }
        if (contadorPacientes >= 10) {
            return "ERROR: hospital lleno";
        }

        AveExotica a = new AveExotica(nombre, peso, edad, envergadura);
        pacientes[contadorPacientes] = a;
        contadorPacientes++;
        return "OK: " + nombre + " ingresado";
    }

    public String vacunar(String nombre) {
        Paciente p = buscarPaciente(nombre);
        if (p == null) {
            return "ERROR: paciente no encontrado";
        }
        if (!(p instanceof Perro)) {
            return "ERROR: solo aplica a perros";
        }
        Perro perro = (Perro) p;
        if (perro.isVacunado()) {
            return "ERROR: ya vacunado";
        }

        perro.setVacunado(true);
        return "OK: " + nombre + " vacunado (pasa a INGRESADO)";
    }

    private String determinarTamanoJaula(Paciente p) {
        double peso = p.getPeso();
        String tamano = "S";
        if (peso >= 5 && peso <= 25) {
            tamano = "M";
        } else if (peso > 25) {
            tamano = "L";
        }

        if (p instanceof AveExotica) {
            AveExotica ave = (AveExotica) p;
            if (ave.getEnvergadura() > 60) {
                if (tamano.equals("S")) {
                    tamano = "M";
                }
            }
        }
        return tamano;
    }

    private boolean tamanoCompatible(String tamanoJaula, String tamanoRequerido) {
        if (tamanoRequerido.equals("S")) {
            return tamanoJaula.equals("S") || tamanoJaula.equals("M") || tamanoJaula.equals("L");
        } else if (tamanoRequerido.equals("M")) {
            return tamanoJaula.equals("M") || tamanoJaula.equals("L");
        } else if (tamanoRequerido.equals("L")) {
            return tamanoJaula.equals("L");
        }
        return false;
    }

    public String asignarJaula(String nombre) {
        Paciente p = buscarPaciente(nombre);
        if (p == null) {
            return "ERROR: paciente no encontrado";
        }
        if (p.getEstado().equals("CUARENTENA")) {
            return "ERROR: paciente en cuarentena";
        }
        if (p.getJaulaAsignada() != null) {
            return "ERROR: paciente ya tiene jaula";
        }

        String zonaNecesaria = "A";
        if (p instanceof AveExotica) {
            zonaNecesaria = "B";
        }

        String tamanoNecesario = determinarTamanoJaula(p);

        Jaula jaulaEncontrada = null;
        for (int i = 0; i < jaulas.length; i++) {
            Jaula j = jaulas[i];
            if (j.estaLibre() && j.getZona().equals(zonaNecesaria)) {
                if (tamanoCompatible(j.getTamano(), tamanoNecesario)) {
                    jaulaEncontrada = j;
                    break;
                }
            }
        }

        if (jaulaEncontrada == null) {
            return "ERROR: no hay jaula compatible";
        }

        jaulaEncontrada.setOcupante(p);
        p.setJaulaAsignada(jaulaEncontrada);
        return "OK: " + nombre + " asignado a jaula " + jaulaEncontrada.getNumero();
    }

    public String medicar(String nombre, double mg) {
        Paciente p = buscarPaciente(nombre);
        if (p == null) {
            return "ERROR: paciente no encontrado";
        }
        if (mg <= 0) {
            return "ERROR: datos invalidos";
        }
        if (p.getEstado().equals("CUARENTENA")) {
            return "ERROR: paciente en cuarentena";
        }
        if (p.getJaulaAsignada() == null) {
            return "ERROR: paciente sin jaula";
        }
        if (p instanceof Gato) {
            Gato g = (Gato) p;
            if (g.getNivelEstres() >= 8) {
                return "ERROR: gato con estres alto";
            }
        }

        double dosisMaxima = p.getPeso() * p.getFactorDosis();
        if (mg > dosisMaxima) {
            return "ERROR: dosis excede el maximo";
        }

        double topeAcumulado = 3 * dosisMaxima;
        if (p.getDosisAcumulada() + mg > topeAcumulado) {
            return "ERROR: tope acumulado superado";
        }

        p.setDosisAcumulada(p.getDosisAcumulada() + mg);
        p.setEstado("EN_TRATAMIENTO");

        if (p instanceof Gato) {
            Gato g = (Gato) p;
            int nuevoEstres = g.getNivelEstres() + 2;
            if (nuevoEstres > 10) nuevoEstres = 10;
            g.setNivelEstres(nuevoEstres);
        }

        return "OK: " + nombre + " medicado con " + mg + " mg";
    }

    public String darAlta(String nombre) {
        Paciente p = buscarPaciente(nombre);
        if (p == null) {
            return "ERROR: paciente no encontrado";
        }
        if (!p.getEstado().equals("EN_TRATAMIENTO")) {
            return "ERROR: paciente no apto para alta";
        }

        if (p.getJaulaAsignada() != null) {
            p.getJaulaAsignada().setOcupante(null);
            p.setJaulaAsignada(null);
        }

        int index = -1;
        for (int i = 0; i < contadorPacientes; i++) {
            if (pacientes[i] == p) {
                index = i;
                break;
            }
        }

        if (index != -1) {
            for (int i = index; i < contadorPacientes - 1; i++) {
                pacientes[i] = pacientes[i + 1];
            }
            pacientes[contadorPacientes - 1] = null;
            contadorPacientes--;
        }

        return "OK: " + nombre + " dado de alta";
    }

    public String generarReporte() {
        StringBuilder sb = new StringBuilder();
        sb.append("REPORTE\n");
        sb.append("Pacientes: ").append(contadorPacientes).append("/10\n");
        for (int i = 0; i < contadorPacientes; i++) {
            Paciente p = pacientes[i];
            String tipo = "Perro";
            if (p instanceof Gato) tipo = "Gato";
            else if (p instanceof AveExotica) tipo = "Ave";

            String jaulaStr = (p.getJaulaAsignada() == null) ? "-" : String.valueOf(p.getJaulaAsignada().getNumero());
            sb.append(p.getNombre()).append(" | ").append(tipo).append(" | ")
                    .append(String.format("%.1f", p.getPeso())).append(" kg | ")
                    .append(p.getEstado()).append(" | jaula: ").append(jaulaStr).append("\n");
        }

        int jaulasLibres = 0;
        for (int i = 0; i < jaulas.length; i++) {
            if (jaulas[i].estaLibre()) {
                jaulasLibres++;
            }
        }
        sb.append("Jaulas libres: ").append(jaulasLibres);
        return sb.toString();
    }
}