package controlador;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import conexionBD.ConexionBD;
import modelo.Juego;

//DAO - Data Access Object

public class JuegoDAO {
	
	ConexionBD conexion;
	int linea;

	public JuegoDAO() {
		conexion = new ConexionBD();
		//this.conexion = conexion;
	}
	
	// Metodos para Altas, Bajas, Cambios y Consultas
	
	public boolean insertarRegistro(Juego j) {
		boolean resultado = false;
		
		String sql="INSERT INTO juegos VALUES('"+j.getIdJuego()+"','"+j.getTitulo()+"','"+j.getGenero()+"','"+j.getEstudio()+"',"+j.getPlataforma()+","+j.getCantidad()+",'"+j.getPrecio()+"')";
		resultado = conexion.ejecutarInstruccion(sql);
		
		return resultado;
	}
	
	public boolean eliminarRegistro(String nc) {
		boolean resultado = false;
		
		String sql="DELETE FROM juegos where idJuego = \""+nc+"\"";
		resultado = conexion.ejecutarInstruccion(sql);
		
		return resultado;
	}
	
	public boolean modificarRegistro(Juego j) {
		boolean resultado = false;
		
		String sql = "UPDATE juegos SET titulo='"+ j.getTitulo() +
				"', genero='" + j.getGenero() +
				"', estudio='" + j.getEstudio() + 
				"', lataforma='" + j.getPlataforma() + 
				"', cantidad=" + j.getCantidad() +
				", precio=" + j.getPrecio() +
				", WHERE idJuego ='" + j.getIdJuego()+"'";
		
		resultado = conexion.ejecutarInstruccion(sql);
		
		return resultado;
	}
	
	public ArrayList<Juego> BuscarJuego(String filtro) {
		
		ArrayList<Juego> listaJuegos = new ArrayList<>();
		
		String sql = "SELECT * FROM juegos";
		
		ResultSet rs = conexion.ejecutarConsulta(sql);
		
		try {
			if(rs.next()) {
				do {
					listaJuegos.add(new Juego(rs.getString(1), 
												rs.getString(2), 
												rs.getString(3), 
												rs.getString(4),
												rs.getString(5),
												rs.getInt(6), 
												rs.getDouble(7)));
					}while(rs.next());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return listaJuegos;
	}
	
}
