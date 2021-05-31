package conexionBD;

import java.sql.*;

import javax.swing.JOptionPane;

import modelo.Juego;

public class ConexionBD {
	
	private static PreparedStatement pstm;
	private static Connection conexion = null;
    public static String usuario;
    public static String password;
	private static ConexionBD conexionBD;
	private static ResultSet rs;
	public static boolean status = false;
	
	public static void setCuenta(String usuario, String password){
        ConexionBD.usuario = usuario;
        ConexionBD.password = password;
    }
	
	public static synchronized ConexionBD getInstance() {
        if (conexionBD == null) {
            new ConexionBD();
        }
        return conexionBD;
    }

    public static Connection getConexion() {
    	try {
    		
    		status = false;
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			String URL = "jdbc:mysql://localhost:3306/TiendaDeVideoJuegos";
			
			conexion = DriverManager.getConnection(URL,ConexionBD.usuario,ConexionBD.password);
			status = true;
			
			System.out.println("Conexion establecida");
			
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null,"No se puede establecer conexion... revisa drivers" + e.getMessage(),
                    "error de conexion",JOptionPane.ERROR_MESSAGE);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,"ERROR " + e.getMessage(),
                    "error de conexion",JOptionPane.ERROR_MESSAGE);
		}
    	return conexion;
    }
    
    public static boolean getStatus(){
        return status;
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
