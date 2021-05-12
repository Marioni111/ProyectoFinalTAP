package controlador;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import conexionBD.ConexionBD;
import modelo.Alumno;

//DAO - Data Access Object

public class AlumnoDAO {
	
	ConexionBD conexion;
	int linea;

	public AlumnoDAO() {
		conexion = new ConexionBD();
		//this.conexion = conexion;
	}
	
	// Metodos para Altas, Bajas, Cambios y Consultas
	
	public boolean insertarRegistro(Alumno a) {
		boolean resultado = false;
		
		String sql="INSERT INTO alumnos VALUES('"+a.getNumControl()+"','"+a.getNombre()+"','"+a.getPrimerAp()+"','"+a.getSegundoAp()+"',"+a.getEdad()+","+a.getSemestre()+",'"+a.getCarrera()+"')";
		resultado = conexion.ejecutarInstruccion(sql);
		
		return resultado;
	}
	
	public boolean eliminarRegistro(String nc) {
		boolean resultado = false;
		
		String sql="DELETE FROM Alumnos where NumControl = \""+nc+"\"";
		resultado = conexion.ejecutarInstruccion(sql);
		
		return resultado;
	}
	
	public boolean modificarRegistro(Alumno a) {
		boolean resultado = false;
		
		String sql = "UPDATE alumnos SET nombre='"+ a.getNombre() +
				"', primerAo='" + a.getPrimerAp() +
				"', segundoAp='" + a.getSegundoAp() + 
				"', edad=" + a.getEdad() +
				", semestre=" + a.getSemestre() +
				", carrera='" + a.getCarrera() + 
				"' WHERE numControl ='" + a.getNumControl()+"'";
		
		resultado = conexion.ejecutarInstruccion(sql);
		
		return resultado;
	}
	
	public ArrayList<Alumno> BuscarAlumnos(String filtro) {
		
		ArrayList<Alumno> listaAlumnos = new ArrayList<>();
		
		String sql = "SELECT * FROM alumnos";
		
		ResultSet rs = conexion.ejecutarConsulta(sql);
		
		try {
			if(rs.next()) {
				do {
					listaAlumnos.add(new Alumno(rs.getString(1), 
												rs.getString(2), 
												rs.getString(3), 
												rs.getString(4), 
												rs.getByte(5), 
												rs.getByte(6), 
												rs.getString(7)));
					}while(rs.next());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return listaAlumnos;
	}
	
	public Alumno BuscarAlumnosPorCarrera(String carrera) {
		
		Alumno alumno = null;
		
        String instruccionSQL = "SELECT * FROM alumnos WHERE carrera = '" + carrera + "'";
        
        ResultSet rs = conexion.ejecutarConsulta(instruccionSQL);

        try{
            if(rs.next()) {
                alumno = new Alumno(rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getByte(5),
                        rs.getByte(6),
                        rs.getString(7));
            }
            lineas();
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return  alumno;
	}
	
	
    public void lineas (){
        Alumno alumno = null;
        String instruccionSQL = "SELECT * FROM alumnos";
        ResultSet rs = conexion.ejecutarConsulta(instruccionSQL);
        try {
            linea = rs.getRow();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Alumno CargarPrimero(){
        Alumno alumno = null;
        String instruccionSQL = "SELECT * FROM alumnos";
        ResultSet rs = conexion.ejecutarConsulta(instruccionSQL);

        try{
            if(rs.next()) {
                alumno = new Alumno(rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getByte(5),
                        rs.getByte(6),
                        rs.getString(7));
            }
            lineas();
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return  alumno;
    }
	
    public Alumno CargarSiguien(int num){
        Alumno alumno = null;
        String instruccionSQL = "SELECT * FROM alumnos";
        ResultSet rs = conexion.ejecutarConsulta(instruccionSQL);

        if (num >= linea){
            try{
                if(rs.relative(num)){
                    alumno = new Alumno(rs.getString(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getByte(5),
                            rs.getByte(6),
                            rs.getString(7));
                }
            }catch(SQLException e) {
                e.printStackTrace();
            }
        }else {
            alumno = CargarUltimo();
        }

        return  alumno;
    }

    public Alumno CargarAnterior(int num){
        Alumno alumno = null;
        String instruccionSQL = "SELECT * FROM alumnos";
        ResultSet rs = conexion.ejecutarConsulta(instruccionSQL);

        if (num >= 0){
            try{
                if(rs.relative(num)){
                    alumno = new Alumno(rs.getString(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getByte(5),
                            rs.getByte(6),
                            rs.getString(7));
                }
            }catch(SQLException e) {
                e.printStackTrace();
            }
        }else {
            alumno = CargarUltimo();
        }
        return  alumno;
    }

    public Alumno CargarUltimo(){
        Alumno alumno = null;
        String instruccionSQL = "SELECT * FROM alumnos;";
        ResultSet rs = conexion.ejecutarConsulta(instruccionSQL);

        try{
            if(rs.last()){
                alumno = new Alumno(rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getByte(5),
                        rs.getByte(6),
                        rs.getString(7));
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return  alumno;
    }
	
}
