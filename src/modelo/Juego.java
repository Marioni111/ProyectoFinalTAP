package modelo;

public class Juego {
	
	String idJuego;
	String titulo;
	String genero;
	String estudio;
	String plataforma;
	int cantidad;
	double precio;
	
	public Juego(String idJuego, String titulo, String genero, String estudio, String plataforma, int cantidad,
			double precio) {
		super();
		this.idJuego = idJuego;
		this.titulo = titulo;
		this.genero = genero;
		this.estudio = estudio;
		this.plataforma = plataforma;
		this.cantidad = cantidad;
		this.precio = precio;
	}

	public String getIdJuego() {
		return idJuego;
	}

	public void setIdJuego(String idJuego) {
		this.idJuego = idJuego;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getEstudio() {
		return estudio;
	}

	public void setEstudio(String estudio) {
		this.estudio = estudio;
	}

	public String getPlataforma() {
		return plataforma;
	}

	public void setPlataforma(String plataforma) {
		this.plataforma = plataforma;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}
	
}
