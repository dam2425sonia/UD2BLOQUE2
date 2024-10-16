import java.sql.*;

public class App {

    // Parámetros de la base de datos
    static final String DB_URL = "jdbc:mysql://localhost:3306/dam2"; // Cambiar por tu base de datos
    static final String USER = "root"; // Cambiar por tu usuario
    static final String PASS = "Dam2425*"; // Cambiar por tu contraseña

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            
            // 1. Insertar datos en las tablas Departamento y Empleado usando PreparedStatement
            insertarDepartamento(conn, "Contabilidad", "PB0");
            insertarDepartamento(conn, "Marketing", "PB0");
            insertarDepartamento(conn, "RRHH", "PB1");

            insertarEmpleado(conn, "Alvaro", "1979-11-25", 1, 1);
            insertarEmpleado(conn, "Juan", "2001-07-07", 1, 2);
            insertarEmpleado(conn, "Marta", "1997-12-25", 0, 3);
            insertarEmpleado(conn, "Silvia", "1992-04-01", 0, 2);

            // 2. Recuperar empleados junto con el nombre del departamento
            System.out.println("\n--- Empleados y sus Departamentos ---");
            cargaEmpleadosConDepartamentos(conn);

            // 3. Recuperar todos los empleados ordenados por fecha de nacimiento (de mayor a menor)
            System.out.println("\n--- Empleados ordenados por fecha de nacimiento ---");
            cargarEmpleadosPorFechaNacimiento(conn);

            // 4. Editar la fecha de nacimiento de "Marta" y establecer "2002-03-04"
            actualizarEmpleadoFechaNacimiento(conn, "Marta", "2002-03-04");

            // 5. Eliminar el usuario con `id = 1`
            borrarEmpleadoPorId(conn, 1);

            // Mostrar los empleados restantes
            System.out.println("\n--- Empleados después de la eliminación ---");
            cargaEmpleadosConDepartamentos(conn);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para insertar datos en la tabla Departamento
    private static void insertarDepartamento(Connection conn, String nombre, String ubicacion) throws SQLException {
        String query = "INSERT INTO Departamento (nombre, ubicacion) VALUES (?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, nombre);
            pstmt.setString(2, ubicacion);
            pstmt.executeUpdate();
            System.out.println("Departamento insertado: " + nombre);
        }
    }

    // Método para insertar datos en la tabla Empleado
    private static void insertarEmpleado(Connection conn, String nombre, String fechaNacimiento, int genero, int departamentoId) throws SQLException {
        String query = "INSERT INTO Empleado (nombre, fecha_nacimiento, genero, departamento_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, nombre);
            pstmt.setDate(2, Date.valueOf(fechaNacimiento));
            pstmt.setInt(3, genero);
            pstmt.setInt(4, departamentoId);
            pstmt.executeUpdate();
            System.out.println("Empleado insertado: " + nombre);
        }
    }

    // Método para recuperar los empleados con el nombre de su departamento
    private static void cargaEmpleadosConDepartamentos(Connection conn) throws SQLException {
        String query = "SELECT E.id, E.nombre, E.fecha_nacimiento, E.genero, D.nombre AS departamento " +
                       "FROM Empleado E " +
                       "JOIN Departamento D ON E.departamento_id = D.id";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                System.out.printf("ID: %d, Nombre: %s, Fecha Nacimiento: %s, Género: %d, Departamento: %s\n",
                rs.getInt("id"), rs.getString("nombre"), rs.getDate("fecha_nacimiento"),
                rs.getInt("genero"), rs.getString("departamento"));
            }
        }
    }

    // Método para recuperar todos los empleados ordenados por fecha de nacimiento (mayor a menor)
    private static void cargarEmpleadosPorFechaNacimiento(Connection conn) throws SQLException {
        String query = "SELECT id, nombre, fecha_nacimiento FROM Empleado ORDER BY fecha_nacimiento DESC";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                System.out.printf("ID: %d, Nombre: %s, Fecha Nacimiento: %s\n",
                rs.getInt("id"), rs.getString("nombre"), rs.getDate("fecha_nacimiento"));
            }
        }
    }

    // Método para actualizar la fecha de nacimiento de un empleado
    private static void actualizarEmpleadoFechaNacimiento(Connection conn, String nombre, String nuevaFecha) throws SQLException {
        String query = "UPDATE Empleado SET fecha_nacimiento = ? WHERE nombre = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setDate(1, Date.valueOf(nuevaFecha));
            pstmt.setString(2, nombre);
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Fecha de nacimiento actualizada para " + nombre + ": " + nuevaFecha);
        }
    }

    // Método para eliminar un empleado por ID
    private static void borrarEmpleadoPorId(Connection conn, int id) throws SQLException {
        String query = "DELETE FROM Empleado WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Empleado con ID " + id + " eliminado.");
        }
    }

}

