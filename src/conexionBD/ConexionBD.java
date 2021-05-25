package conexionBD;

import java.sql.*;

import modelo.Juego;

public class ConexionBD {
	
	private static PreparedStatement pstm;
	private static Connection conexion = null;
	private static ConexionBD conexionBD;
	private static ResultSet rs;
	
	private ConexionBD() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			String URL = "jdbc:mysql://localhost:3306/TiendaDeVideoJuegos";
			
			conexion = DriverManager.getConnection(URL,"root","1234");
			
			System.out.println("Conexion establecida");
			
		} catch (ClassNotFoundException e) {
			System.out.printf("Error de Driver");
		} catch (SQLException e) {
			System.out.printf("Error de conexion a MySQL o de la BD");
		}
	}
	
	public static synchronized ConexionBD getInstance() {
        if (conexionBD == null) {
            new ConexionBD();
        }
        return conexionBD;
    }

    public static Connection getConexion() {
        if (conexion == null) {
            new ConexionBD();
        }
        return conexion;
    }
	
	static void cerrarConnexion() {
		try {
			pstm.close();
			conexion.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static boolean actualizarJuego(Juego j) {
		
		try {
			pstm = conexion.prepareStatement("UPDATE Juegos SET titulo=?, genero=?, estudio=?, plataforma=?, cantidad=?, precio=? WHERE idJuego = '" + j.getIdJuego()+"'");
			pstm.setString(1, j.getTitulo());
			pstm.setString(2, j.getGenero());
			pstm.setString(3, j.getEstudio());
			pstm.setString(4, j.getPlataforma());
			pstm.setInt(5, j.getCantidad());
			pstm.setDouble(6, j.getPrecio());
			
			pstm.executeUpdate();
			
			return true;
			
		} catch (Exception e) {

            e.printStackTrace();
		}
		
		return false;
	}
	
	public static boolean agregarJuego(Juego j) {
		
		try {
			pstm = conexion.prepareStatement("INSERT INTO JUEGOS VALUES('" + j.getIdJuego() + "', ?, ?, ?, ?, ?, ?)");
			pstm.setString(1, j.getTitulo());
			pstm.setString(2, j.getGenero());
			pstm.setString(3, j.getEstudio());
			pstm.setString(4, j.getPlataforma());
			pstm.setInt(5, j.getCantidad());
			pstm.setDouble(6, j.getPrecio());
			
			pstm.executeUpdate();
			
			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public static boolean eliminarRegistro(String sql) {
        try {
            String consulta = sql;
            pstm = conexion.prepareStatement(consulta);
            pstm.executeUpdate();
            return true;
     } catch (Exception ex) {
            System.out.println(ex.toString());
     }
     return false;
    }
	
	//		Consulta
	public static ResultSet ejecutarConsulta(String sql) {
		
		try {
			
			pstm = conexion.prepareStatement(sql);
			rs = pstm.executeQuery(sql);		
			
		} catch (SQLException e) {
			System.out.println("No se pudo ejecutar instruccion");
			e.printStackTrace();
		}
		return rs;
	}
	
}//class
