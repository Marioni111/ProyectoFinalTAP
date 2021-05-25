package controlador;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import conexionBD.ConexionBD;
import modelo.Juego;

//DAO - Data Access Object

class ConsultaJuegos implements Runnable{

	
	String filtro;
	ArrayList<Juego> listaJuegos = new ArrayList<Juego>();
	
	@Override
	public void run() {

		ResultSet rs = ConexionBD.ejecutarConsulta(filtro);
		
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
		
	}

	public ConsultaJuegos(String filtro) {
		
		this.filtro = filtro;
	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

	public ArrayList<Juego> getListaJuegos() {
		return listaJuegos;
	}

	public void setListaJuegos(ArrayList<Juego> listaJuegos) {
		this.listaJuegos = listaJuegos;
	}
}

public class JuegoDAO {
	
	public boolean insertarRegistro(Juego j) {
		
		boolean resultado = false;
		
		resultado = ConexionBD.agregarJuego(j);
		
		return resultado;
	}
	
	public boolean eliminarRegistro(String nc) {
		
		boolean resultado = false;
		
		String sql="DELETE FROM juegos where idJuego = \""+nc+"\"";
		resultado = ConexionBD.eliminarRegistro(sql);
		
		return resultado;
	}
	
	public boolean modificarRegistro(Juego j) {
		
		boolean resultado = false;
		
		resultado = ConexionBD.actualizarJuego(j);
		
		return resultado;
	}
	
	public ArrayList<Juego> BuscarJuego(String filtro) {
		
		ConsultaJuegos cj = new ConsultaJuegos(filtro);
		
		Thread consulta = new Thread(cj);
		
		consulta.start();
		
		try {
			consulta.join();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return cj.getListaJuegos();
	}
	
}
