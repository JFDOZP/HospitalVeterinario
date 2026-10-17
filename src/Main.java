import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Hospital hospital = new Hospital();

        while (true) {
            String linea = scanner.nextLine().trim();
            if (linea.equalsIgnoreCase("salir")) {
                break;
            }

            String[] partes = linea.split("\\s+");
            if (partes.length == 0 || partes[0].isEmpty()) {
                continue;
            }

            String comando = partes[0].toLowerCase();
            String resultado = "";

            if (comando.equals("ingresar")) {
                if (partes.length < 2) {
                    resultado = "ERROR: comando desconocido.";
                } else {
                    String tipo = partes[1].toLowerCase();
                    if (tipo.equals("perro") && partes.length >= 7) {
                        String nombre = partes[2];
                        try {
                            double peso = Double.parseDouble(partes[3]);
                            int edad = Integer.parseInt(partes[4]);
                            String raza = partes[5];
                            String siNo = partes[6];
                            resultado = hospital.ingresarPerro(nombre, peso, edad, raza, siNo);
                        } catch (NumberFormatException e) {
                            resultado = "ERROR: datos invalidos";
                        }
                    } else if (tipo.equals("gato") && partes.length >= 5) {
                        String nombre = partes[2];
                        try {
                            double peso = Double.parseDouble(partes[3]);
                            int edad = Integer.parseInt(partes[4]);
                            resultado = hospital.ingresarGato(nombre, peso, edad);
                        } catch (NumberFormatException e) {
                            resultado = "ERROR: datos invalidos";
                        }
                    } else if (tipo.equals("ave") && partes.length >= 6) {
                        String nombre = partes[2];
                        try {
                            double peso = Double.parseDouble(partes[3]);
                            int edad = Integer.parseInt(partes[4]);
                            double envergadura = Double.parseDouble(partes[5]);
                            resultado = hospital.ingresarAve(nombre, peso, edad, envergadura);
                        } catch (NumberFormatException e) {
                            resultado = "ERROR: datos invalidos";
                        }
                    } else {
                        resultado = "ERROR: datos invalidos";
                    }
                }
            } else if (comando.equals("vacunar")) {
                if (partes.length >= 2) {
                    resultado = hospital.vacunar(partes[1]);
                } else {
                    resultado = "ERROR: comando desconocido.";
                }
            } else if (comando.equals("asignar")) {
                if (partes.length >= 2) {
                    resultado = hospital.asignarJaula(partes[1]);
                } else {
                    resultado = "ERROR: comando desconocido.";
                }
            } else if (comando.equals("medicar")) {
                if (partes.length >= 3) {
                    String nombre = partes[1];
                    try {
                        double mg = Double.parseDouble(partes[2]);
                        resultado = hospital.medicar(nombre, mg);
                    } catch (NumberFormatException e) {
                        resultado = "ERROR: datos invalidos";
                    }
                } else {
                    resultado = "ERROR: comando desconocido.";
                }
            } else if (comando.equals("alta")) {
                if (partes.length >= 2) {
                    resultado = hospital.darAlta(partes[1]);
                } else {
                    resultado = "ERROR: comando desconocido.";
                }
            } else if (comando.equals("reporte")) {
                resultado = hospital.generarReporte();
            } else {
                resultado = "ERROR: comando desconocido.";
            }

            System.out.println(resultado);
        }

        scanner.close();
    }
}