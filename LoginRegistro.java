import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class LoginRegistro {

    private static final String URL = "jdbc:mysql://localhost:3306/usuarios_db";
    private static final String USUARIO = "samuelt9991";
    private static final String CONTRASENA = "Samura918";

    /**
     * @param args
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bienvenido");
        System.out.println("1. Iniciar sesión");
        System.out.println("2. Registrarse");
        System.out.print("Seleccione una opción: ");
        int opcion = scanner.nextInt();

        switch (opcion) {
            case 1:
                iniciarSesion(scanner);
                break;
            case 2:
                registrarUsuario(scanner);
                break;
            default:
                System.out.println("Opción no válida");
        }

        scanner.close();
    }

    private static void iniciarSesion(Scanner scanner) {
        System.out.println("Ingrese su nombre de usuario:");
        String usuario = scanner.next();
        System.out.println("Ingrese su contraseña:");
        String contrasena = scanner.next();

        try (Connection connection = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM usuarios WHERE usuario = ? AND contrasena = ?")) {

            statement.setString(1, usuario);
            statement.setString(2, contrasena);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                System.out.println("Inicio de sesión exitoso.");
            } else {
                System.out.println("Nombre de usuario o contraseña incorrectos.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void registrarUsuario(Scanner scanner) {
        System.out.println("Ingrese un nombre de usuario:");
        String usuario = scanner.next();
        System.out.println("Ingrese una contraseña:");
        String contrasena = scanner.next();

        try (Connection connection = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
             PreparedStatement statement = connection.prepareStatement("INSERT INTO usuarios (usuario, contrasena) VALUES (?, ?)")) {

            statement.setString(1, usuario);
            statement.setString(2, contrasena);
            int filasAfectadas = statement.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Usuario registrado exitosamente.");
            } else {
                System.out.println("Error al registrar el usuario.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
