package vista;

import javax.print.CancelablePrintJob;
import javax.swing.*;
import javax.swing.table.TableModel;
import conexionBD.ConexionBD;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.ImageFilter;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.ObjectStreamException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.*;
import controlador.JuegoDAO;
import modelo.Juego;



public class VentanaInicio extends JFrame {
	
	 public VentanaInicio() {

		 crearComponentes();
		 
	    }
	 
	 ConexionBD conexion = ConexionBD.getInstance();
	 
	 JInternalFrame frameInicioSesion, frameAltas, frameBajas, frameModificaciones, frameConsultas;
	 
	 JTextField txtUsuario,
	 			txtTituloAltas, txtEstudioAltas,
	 			txtIdJuegoBajas, txtTituloBajas, txtEstudioBajas,
	 			txtIdJuegoModificaciones, txtTituloModificaciones, txtEstudioModificaciones,
	 			txtIdJuegoConsultas, txtTituloConsultas, txtEstudioConsultas;
	 
	 JPasswordField txtPassword;
	 
	 JButton btnEntrar, btnCerrar,
	 		 btnAgregarAltas, btnRestablecerAltas, 
	         btnEliminar, btnRestablecerBajas, btnBuscarBajas,
	         btnActualizar, btnRestablecerModificaciones, btnBuscarModificaciones,
	         btnBuscarConsultas, btnRestablecerConsultas;
	 
	 JComboBox<String> cboGeneroAltas, cboPlataformaAltas,
	 				   cboGeneroBajas, cboPlataformaBajas,
	 				   cboGeneroModificaciones, cboPlataformaModificaciones,
	 				   cboGeneroConsultas, cboPlataformaConsultas;
	
	 JScrollPane scrollAltas, scrollBajas, scrollModificaciones, scrollConsultas;
	 
	 JTable tablaJuegosAltas, tablaJuegosBajas, tablaJuegosModificaciones, tablaJuegosConsultas;
	 
	 JSpinner spinnerCantidadAltas, spinnerPrecioAltas,
	 		  spinnerCantidadBajas, spinnerPrecioBajas,
	 		  spinnerCantidadModificaciones, spinnerPrecioModificaciones, 
	 		  spinnerCantidadConsultas, spinnerPrecioConsultas;
	 		  
	 JuegoDAO jDAO;
	 
	 String idJuego; String titulo; int genero; String estudio; int plataforma; int cantidad; double precio;
	 
	 ResultSetTableModel modeloDatos = null;
	 
	 DecimalFormat df = new DecimalFormat("#.##");
	 
	 Font f1 = new Font("Arial", Font.ITALIC, 12);
	 Font f2 = new Font("Arial", Font.CENTER_BASELINE, 14);
	 
	 String maxId = "";
	 
	 public void crearComponentes() {
	        
		 	JFrame ventana = new JFrame();
		 	ventana.getContentPane().setLayout(new BorderLayout());
		 	ventana.setDefaultCloseOperation(EXIT_ON_CLOSE);
		 	ventana.setTitle("Control de inventario de Iguan Games");
		 	ventana.setSize(710, 660);
		 	ventana.setLocationRelativeTo(null);
		 	
	        JDesktopPane pane = new JDesktopPane();
	        
	        double min = 0.00;
	        double value = 0.00;
	        double max = 500000;
	        double stepSize = 100.00;
	        SpinnerNumberModel model = new SpinnerNumberModel(value, min, max, stepSize);

	        int min2 = 0;
	        int value2 = 0;
	        int max2 = 500000;
	        int stepSize2 = 10;
	        SpinnerNumberModel model2 = new SpinnerNumberModel(value2, min2, max2, stepSize2);
	        
	        //-------------------------------------- MENU PRINCIPAL ----------------------------------

	        JMenuBar menuBar = new JMenuBar();
	        JMenu menu = new JMenu("Menu juegos");
	        JMenuItem menuCon = new JMenuItem("Buscar Juegos");
	        menuCon.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.ALT_MASK));
	        menuCon.addActionListener(new ActionListener() {

	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	
	            	actualizarTabla(tablaJuegosConsultas, "select*from juegos;");
	            	
	            	metodoRestablecer(txtIdJuegoConsultas, txtTituloConsultas, cboGeneroConsultas, txtEstudioConsultas, cboPlataformaConsultas, spinnerCantidadConsultas, spinnerPrecioConsultas);
	            	
	            	frameAltas.setVisible(false);
					frameBajas.setVisible(false);
					frameModificaciones.setVisible(false);
					frameConsultas.setVisible(true);
	            }
	        });
	        menu.add(menuCon);
	        
	        
	        JMenuItem menuAlt = new JMenuItem("Agregar juegos al inventario");
	        menuAlt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.ALT_MASK));
	        menuAlt.addActionListener(new ActionListener() {

	        	@Override
				public void actionPerformed(ActionEvent arg0) {
					
	        		actualizarTabla(tablaJuegosAltas, "select*from juegos;");
	        		
	        		metodoRestablecer(txtTituloAltas, cboGeneroAltas, txtEstudioAltas, cboPlataformaAltas, spinnerCantidadAltas, spinnerPrecioAltas);
	        		
	        		frameAltas.setVisible(true);
					frameBajas.setVisible(false);
					frameModificaciones.setVisible(false);
					frameConsultas.setVisible(false);
				}
	        });
	        menu.add(menuAlt);

	        JMenuItem menuMod = new JMenuItem("Actualizar informacion de Juegos");
	        menuMod.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, ActionEvent.ALT_MASK));
	        menuMod.addActionListener(new ActionListener() {

	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	
	            	actualizarTabla(tablaJuegosModificaciones, "select*from juegos;");
	            	
	            	metodoRestablecer(txtIdJuegoModificaciones, txtTituloModificaciones, cboGeneroModificaciones, txtEstudioModificaciones, cboPlataformaModificaciones, spinnerCantidadModificaciones, spinnerPrecioModificaciones);
	            	
	            	frameAltas.setVisible(false);
					frameBajas.setVisible(false);
					frameModificaciones.setVisible(true);
					frameConsultas.setVisible(false);
	            }
	        });
	        menu.add(menuMod);
	        
	        JMenuItem menuBaj = new JMenuItem("Sacar juego del inventario");
	        menuBaj.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.ALT_MASK));
	        menuBaj.addActionListener(new ActionListener() {

	            @Override
	            public void actionPerformed(ActionEvent e) {
	                
	            	actualizarTabla(tablaJuegosBajas, "select*from juegos;");
	            	
	            	metodoRestablecer(txtIdJuegoBajas, txtTituloBajas, cboGeneroBajas, txtEstudioBajas, cboPlataformaBajas, spinnerCantidadBajas, spinnerPrecioBajas);
	            	
	            	frameAltas.setVisible(false);
					frameBajas.setVisible(true);
					frameModificaciones.setVisible(false);
					frameConsultas.setVisible(false);
	            }
	        });
	        menu.add(menuBaj);
	        
	        menuBar.add(menu);
	        ventana.setJMenuBar(menuBar);

	        //--------------------------------------- FIN DE MENU -------------------------------------
	        
	        //--------------------------------------- Menu Inicio -------------------------------------
	        
	        JFrame inicio = new JFrame();
		 	inicio.getContentPane().setLayout(new FlowLayout());
		 	inicio.setDefaultCloseOperation(EXIT_ON_CLOSE);
		 	inicio.setTitle("Inicio de sesion");
		 	inicio.setSize(300, 500);
		 	inicio.setLocationRelativeTo(null);
		 	inicio.setVisible(true);
	        
	        JPanel panelInicioSesion = new JPanel();
	        panelInicioSesion.setBackground(new Color(255, 255, 255));
	        panelInicioSesion.setLayout(null);
	        panelInicioSesion.setPreferredSize(new Dimension(300, 500));
			
	        JLabel lblIniciarSesion = new JLabel("INICIAR SESIÓN");
	        lblIniciarSesion.setForeground(new Color(115, 170, 249));
	        lblIniciarSesion.setFont(f2);
	        lblIniciarSesion.setBounds(100, 40, 400, 25);
	        panelInicioSesion.add(lblIniciarSesion);
	        
	        btnCerrar = new JButton(new ImageIcon("Iconos/InicioSesion.png"));
	        btnCerrar.setBounds(105, 80, 100, 100);
	        panelInicioSesion.add(btnCerrar);
	        
	        JLabel lblUsuario = new JLabel("USUARIO: ");
	        lblUsuario.setFont(f2);
	        lblUsuario.setBounds(120, 190, 400, 25);
	        panelInicioSesion.add(lblUsuario);

	        txtUsuario = new JTextField(10);
	        txtUsuario.setFont(f2);
	        txtUsuario.setBounds(70, 220, 176, 23);
	        panelInicioSesion.add(txtUsuario);
	        
	        JLabel lblPassword = new JLabel("CONTRASEÑA: ");
	        lblPassword.setFont(f2);
	        lblPassword.setBounds(105, 250, 400, 25);
	        panelInicioSesion.add(lblPassword);

	        txtPassword = new JPasswordField(10);
	        txtPassword.setFont(f2);
	        txtPassword.setBounds(70, 280, 176, 23);
	        panelInicioSesion.add(txtPassword);
	        
	        btnEntrar = new JButton("ENTRAR");
	        btnEntrar.setFont(f2);
	        btnEntrar.setBounds(50, 320, 100, 25);
	        btnEntrar.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					if(txtUsuario.getText().isEmpty() && txtPassword.getText().isEmpty()) {
						
						JOptionPane.showMessageDialog(rootPane, "No olvides llenar los camos usuario/contraseña");	
						
		        	}else {
		        		
		        		if(txtUsuario.getText().equals("Mario") && txtPassword.getText().equals("1234")) {
		        			inicio.setVisible(false);
							ventana.setVisible(true);
		        		}else {
		        			JOptionPane.showMessageDialog(rootPane, "usuario/contraseña incorrectos");
		        		}
		        		
		        	}
				}
			});
	        panelInicioSesion.add(btnEntrar);
	        
	        btnCerrar = new JButton("CERRAR");
	        btnCerrar.setFont(f2);
	        btnCerrar.setBounds(160, 320, 100, 25);
	        btnCerrar.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					inicio.setVisible(false);
					
				}
			});
	        panelInicioSesion.add(btnCerrar);
			
			inicio.add(panelInicioSesion);
	        
			//------------------------------------------ Fin menu inicio ----------------------------------------
			
	        //------------------------------------------ ALTAS ----------------------------------------
	        frameAltas = new JInternalFrame();
	        frameAltas.setLayout(null);
	        frameAltas.setDefaultCloseOperation(HIDE_ON_CLOSE);
	        frameAltas.setSize(700, 595);
	        frameAltas.setTitle("AGREGAR JUEGOS AL INVENTARIO");
	        frameAltas.setIconifiable(true);
	        frameAltas.setResizable(true);
	        frameAltas.setClosable(true);
	        frameAltas.setResizable(true);

	        JPanel panel1 = new JPanel();
	        panel1.setBackground(new Color(115, 170, 249));
	        panel1.setLayout(null);
	        panel1.setPreferredSize(new Dimension(700, 190));
	        panel1.setBounds(0, 0, 700, 80);

	        JLabel lbl1 = new JLabel("AGREGAR JUEGOS AL INVENTARIO");
	        lbl1.setFont(new Font("Arial", Font.BOLD, 24));
	        lbl1.setForeground(new Color(255, 255, 255));
	        lbl1.setBounds(30, 30, 450, 20);
	        panel1.add(lbl1);

	        frameAltas.add(panel1);

	        JPanel panel2 = new JPanel();
	        panel2.setLayout(null);
	        panel2.setBackground(new Color(255, 255, 255));
	        panel2.setPreferredSize(new Dimension(700, 190));
	        panel2.setBounds(0, 80, 700, 330);

	        JLabel lblInformacion = new JLabel("INGRESA LOS DATOS DEL JUEGO NUEVO: ");
	        lblInformacion.setFont(f2);
	        lblInformacion.setBounds(110, 10, 400, 25);
	        panel2.add(lblInformacion);
	        
	        JLabel lblTitulo = new JLabel("TITULO: ");
	        lblTitulo.setFont(f2);
	        lblTitulo.setBounds(180, 50, 400, 25);
	        panel2.add(lblTitulo);

	        txtTituloAltas = new JTextField(10);
	        txtTituloAltas.setFont(f2);
	        txtTituloAltas.setBounds(330, 50, 176, 23);
	        panel2.add(txtTituloAltas);

	        JLabel lblGenero = new JLabel("GENERO:");
	        lblGenero.setFont(f2);
	        lblGenero.setBounds(180, 90, 300, 25);
	        panel2.add(lblGenero);

	        cboGeneroAltas = new JComboBox<String>();
	        cboGeneroAltas.addItem("Elige el genero...");
	        cboGeneroAltas.addItem("Disparos");
	        cboGeneroAltas.addItem("Terror");
	        cboGeneroAltas.addItem("Aventura");
	        cboGeneroAltas.addItem("Estrategia");
	        cboGeneroAltas.addItem("Peleas");
	        cboGeneroAltas.addItem("Deportes");
	        cboGeneroAltas.addItem("Carreras");
	        cboGeneroAltas.setFont(f1);
	        cboGeneroAltas.setBounds(330, 90, 176, 23);
	        panel2.add(cboGeneroAltas);

	        JLabel lblEstudio = new JLabel("ESTUDIO:");
	        lblEstudio.setFont(f2);
	        lblEstudio.setBounds(180, 130, 300, 25);
	        panel2.add(lblEstudio);
	        
	        txtEstudioAltas = new JTextField(10);
	        txtEstudioAltas.setFont(f2);
	        txtEstudioAltas.setBounds(330, 130, 176, 23);
	        panel2.add(txtEstudioAltas);

	        JLabel lblPlataforma = new JLabel("PLATAFORMA:");
	        lblPlataforma.setFont(f2);
	        lblPlataforma.setBounds(180, 170, 300, 25);
	        panel2.add(lblPlataforma);

	        cboPlataformaAltas = new JComboBox<String>();
	        cboPlataformaAltas.addItem("Elige la plataforma...");
	        cboPlataformaAltas.addItem("Xbox series");
	        cboPlataformaAltas.addItem("Playstation 5");
	        cboPlataformaAltas.addItem("Playstation 4");
	        cboPlataformaAltas.addItem("Nintendo switch");
	        cboPlataformaAltas.setFont(f1);
	        cboPlataformaAltas.setBounds(330, 170, 176, 23);
	        panel2.add(cboPlataformaAltas);

	        JLabel lblCantidad = new JLabel("CANTIDAD:");
	        lblCantidad.setFont(f2);
	        lblCantidad.setBounds(180, 210, 300, 25);
	        panel2.add(lblCantidad);

			spinnerCantidadAltas = new JSpinner(model2);
			spinnerCantidadAltas.setBounds(330, 210, 176, 23);
			panel2.add(spinnerCantidadAltas);
	        
	        JLabel lblSemestre = new JLabel("PRECIO:");
	        lblSemestre.setFont(f2);
	        lblSemestre.setBounds(180, 250, 300, 25);
	        panel2.add(lblSemestre);

	        spinnerPrecioAltas = new JSpinner(model);
	        spinnerPrecioAltas.setBounds(330, 250, 176, 23);
			panel2.add(spinnerPrecioAltas);
	        
	        btnRestablecerAltas = new JButton("LIMPIAR");
	        btnRestablecerAltas.setFont(f2);
	        btnRestablecerAltas.setBounds(180, 290, 120, 25);
	        btnRestablecerAltas.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                metodoRestablecer(txtTituloAltas, cboGeneroAltas, txtEstudioAltas, cboPlataformaAltas, spinnerCantidadAltas, spinnerPrecioAltas);
	            }
	        });
	        panel2.add(btnRestablecerAltas);

	        btnAgregarAltas = new JButton("AGREGAR");
	        btnAgregarAltas.setFont(f2);
	        btnAgregarAltas.setBounds(330, 290, 174, 25);
	        btnAgregarAltas.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					if(txtTituloAltas.getText().isEmpty() || cboGeneroAltas.getSelectedIndex() == 0 
						|| txtEstudioAltas.getText().isEmpty() || cboPlataformaAltas.getSelectedIndex() == 0 
						|| spinnerCantidadAltas.getValue().equals(0) || spinnerPrecioAltas.getValue().equals(0)) {
								
							JOptionPane.showMessageDialog(rootPane, "Asegurate de llenar cada uno de los espacios");
					}else {
						String id = obtenerMaxId().substring(0, 0) + obtenerMaxId().substring(1);
						
						int nuevoId = Integer.parseInt(id)+1;
						
						Juego j = new Juego("J" + nuevoId, txtTituloAltas.getText(), 
											cboGeneroAltas.getSelectedItem()+"",
											txtEstudioAltas.getText(),
											cboPlataformaAltas.getSelectedItem()+"",
					                        Integer.parseInt(spinnerCantidadAltas.getValue()+""),
					                        Double.parseDouble(spinnerPrecioAltas.getValue()+"") );
		                
		                jDAO = new JuegoDAO();
		        		
		        		System.out.println(jDAO.insertarRegistro(j)?"EXITO":"FALLO");
		        		
		        		JOptionPane.showMessageDialog(rootPane, "Se dio de alta el nuevo juego");
						
						actualizarTabla(tablaJuegosAltas, "select*from juegos;");
					}
					
				}
			});
	        panel2.add(btnAgregarAltas);

	        frameAltas.add(panel2);

	        JPanel panel3 = new JPanel();
	        panel3.setLayout(null);
	        panel3.setPreferredSize(new Dimension(700, 190));
	        panel3.setBounds(0, 410, 700, 152);

	        tablaJuegosAltas = new JTable();
	        tablaJuegosAltas.setModel(new javax.swing.table.DefaultTableModel(
	                new Object[][] {}, new String [] {"idJuego", "Titulo", "Genero", "Estudio",
	                "Plataforma", "Cantidad", "Precio"}
	        ));

	        scrollAltas = new JScrollPane(tablaJuegosAltas);
	        scrollAltas.setBounds(5, 9, 678, 135);
	        panel3.add(scrollAltas);

	        frameAltas.add(panel3);

	        pane.add(frameAltas);
	       
	        //------------------------------------- ALTAS ---------------------------------------
	        
	      //------------------------------------- BAJAS ---------------------------------------
	        frameBajas = new JInternalFrame();
	        frameBajas.setDefaultCloseOperation(HIDE_ON_CLOSE);
	        frameBajas.setLayout(null);
	        frameBajas.setSize(700, 595);
	        frameBajas.setTitle("SACAR JUEGO DEL INVENTARIO");
	        frameBajas.setClosable(true);
	        frameBajas.setIconifiable(true);
	        frameBajas.setMaximizable(true);
	        frameBajas.setResizable(true);
	        frameBajas.setVisible(false);

	        JPanel panel4 = new JPanel();
	        panel4.setBackground(new Color(115, 170, 249));
	        panel4.setLayout(null);
	        panel4.setPreferredSize(new Dimension(700, 190));
	        panel4.setBounds(0, 0, 700, 80);

	        JLabel lbl2 = new JLabel("SACAR JUEGO DEL IVENTARIO");
	        lbl2.setFont(new Font("Arial", Font.BOLD, 24));
	        lbl2.setForeground(new Color(255, 255, 255));
	        lbl2.setBounds(30, 30, 400, 20);
	        panel4.add(lbl2);

	        frameBajas.add(panel4);
	        
	        JPanel panel5 = new JPanel();
	        panel5.setLayout(null);
	        panel5.setBackground(new Color(255, 255, 255));
	        panel5.setPreferredSize(new Dimension(700, 190));
	        panel5.setBounds(0, 80, 700, 330);
	        
	        JLabel lblInformacion2 = new JLabel("BUSCA EL JUEGO A BORRAR, DESPUES SELECCIONALO DESDE LA TABLA: ");
	        lblInformacion2.setFont(f2);
	        lblInformacion2.setBounds(10, 30, 630, 25);
	        panel5.add(lblInformacion2);
	        
	        JLabel lblIdJuego = new JLabel("ID JUEGO: ");
	        lblIdJuego.setFont(f2);
	        lblIdJuego.setBounds(10, 100, 100, 25);
	        panel5.add(lblIdJuego);

	        txtIdJuegoBajas = new JTextField(10);
	        txtIdJuegoBajas.setFont(f2);
	        txtIdJuegoBajas.setBounds(100, 100, 176, 23);
	        panel5.add(txtIdJuegoBajas);
	        
	        JLabel lblTitulo2 = new JLabel("TITULO: ");
	        lblTitulo2.setFont(f2);
	        lblTitulo2.setBounds(20, 150, 100, 25);
	        panel5.add(lblTitulo2);

	        txtTituloBajas = new JTextField(10);
	        txtTituloBajas.setFont(f2);
	        txtTituloBajas.setBounds(100, 150, 176, 23);
	        panel5.add(txtTituloBajas);

	        JLabel lblGenero2 = new JLabel("GENERO:");
	        lblGenero2.setFont(f2);
	        lblGenero2.setBounds(350, 90, 300, 25);
	        panel5.add(lblGenero2);

	        cboGeneroBajas = new JComboBox<String>();
	        cboGeneroBajas.addItem("Elige el genero...");
	        cboGeneroBajas.addItem("Disparos");
	        cboGeneroBajas.addItem("Terror");
	        cboGeneroBajas.addItem("Aventura");
	        cboGeneroBajas.addItem("Estrategia");
	        cboGeneroBajas.addItem("Peleas");
	        cboGeneroBajas.addItem("Deportes");
	        cboGeneroBajas.addItem("Carreras");
	        cboGeneroBajas.setFont(f1);
	        cboGeneroBajas.setBounds(460, 90, 176, 23);
	        panel5.add(cboGeneroBajas);

	        JLabel lblEstudio2 = new JLabel("ESTUDIO:");
	        lblEstudio2.setFont(f2);
	        lblEstudio2.setBounds(350, 130, 300, 25);
	        panel5.add(lblEstudio2);
	        
	        txtEstudioBajas = new JTextField(10);
	        txtEstudioBajas.setFont(f2);
	        txtEstudioBajas.setBounds(460, 130, 176, 23);
	        panel5.add(txtEstudioBajas);

	        JLabel lblPlataforma2 = new JLabel("PLATAFORMA:");
	        lblPlataforma2.setFont(f2);
	        lblPlataforma2.setBounds(350, 170, 300, 25);
	        panel5.add(lblPlataforma2);

	        cboPlataformaBajas = new JComboBox<String>();
	        cboPlataformaBajas.addItem("Elige la plataforma...");
	        cboPlataformaBajas.addItem("Xbox series");
	        cboPlataformaBajas.addItem("Playstation 5");
	        cboPlataformaBajas.addItem("Playstation 4");
	        cboPlataformaBajas.addItem("Nintendo switch");
	        cboPlataformaBajas.setFont(f1);
	        cboPlataformaBajas.setBounds(460, 170, 176, 23);
	        panel5.add(cboPlataformaBajas);

	        JLabel lblCantidad2 = new JLabel("CANTIDAD:");
	        lblCantidad2.setFont(f2);
	        lblCantidad2.setBounds(350, 210, 300, 25);
	        panel5.add(lblCantidad2);

			spinnerCantidadBajas = new JSpinner(model2);
			spinnerCantidadBajas.setBounds(460, 210, 176, 23);
			panel5.add(spinnerCantidadBajas);
	        
	        JLabel lblSemestre2 = new JLabel("PRECIO:");
	        lblSemestre2.setFont(f2);
	        lblSemestre2.setBounds(350, 250, 300, 25);
	        panel5.add(lblSemestre2);
	        
	        spinnerPrecioBajas = new JSpinner(model);
	        spinnerPrecioBajas.setBounds(460, 250, 176, 23);
			panel5.add(spinnerPrecioBajas);
	        
	        btnRestablecerBajas = new JButton("LIMPIAR");
	        btnRestablecerBajas.setFont(f2);
	        btnRestablecerBajas.setBounds(100, 250, 174, 30);
	        btnRestablecerBajas.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                metodoRestablecer(txtIdJuegoBajas, txtTituloBajas, cboGeneroBajas, txtEstudioBajas, cboPlataformaBajas, spinnerCantidadBajas, spinnerPrecioBajas);
	                actualizarTabla(tablaJuegosBajas, "select*from juegos;");
	            }
	        });
	        panel5.add(btnRestablecerBajas);

	        btnEliminar = new JButton("ELIMINAR");
	        btnEliminar.setFont(f2);
	        btnEliminar.setBounds(100, 200, 174, 30);
	        btnEliminar.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					if(txtIdJuegoBajas.getText().isEmpty()) {
									
						JOptionPane.showMessageDialog(rootPane, "No has seleccionado ningun juego");
						
					}else {
					
						int dialogButton = JOptionPane.YES_NO_OPTION;
			            JOptionPane.showConfirmDialog (null, "Eliminar juego del inventario?","ADVERTENCIA", dialogButton);
						
			            if(dialogButton == JOptionPane.YES_OPTION) {
			            	
			            	String id = txtIdJuegoBajas.getText();
			            	
			            	jDAO = new JuegoDAO();
			        		
			            	String estado = "No se econtraron juegos con esos parametros";
			            	
			        		System.out.println(jDAO.eliminarRegistro(id)? estado = "El juego a sido eliminado del inventario": "Fallo");
			        		
			        		JOptionPane.showMessageDialog(rootPane, estado);	
			        		
			        		metodoRestablecer(txtIdJuegoBajas, txtTituloBajas, cboGeneroBajas, txtEstudioBajas, cboPlataformaBajas, spinnerCantidadBajas, spinnerPrecioBajas);
			        		
			        		actualizarTabla(tablaJuegosBajas, "select * from juegos;");
			        		
			            if(dialogButton == JOptionPane.NO_OPTION) {
			                  remove(dialogButton);
			                }
			              }
			   
					}
				}
			});
	        panel5.add(btnEliminar);
	        
	        btnBuscarBajas = new JButton(new ImageIcon("Iconos/Buscar.png"));
	        btnBuscarBajas.setFont(f2);
	        btnBuscarBajas.setBounds(280, 100, 60, 75);
	        btnBuscarBajas.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					if(txtIdJuegoBajas.getText().isEmpty() && txtTituloBajas.getText().isEmpty()) {
								
								JOptionPane.showMessageDialog(rootPane, "Ingresa informacion del nombreo o el ID del juego");	
								
				        	}else {
				        		
				        		if(txtIdJuegoBajas.getText().length()<=0) {
									txtIdJuegoBajas.setText("  ");
								}
								if(txtTituloBajas.getText().length()<=0) {
									txtTituloBajas.setText("  ");
								}
								
								actualizarTabla(tablaJuegosBajas, "SELECT * FROM juegos where idJuego like '" + txtIdJuegoBajas.getText() +
										  "%' OR titulo like '" + txtTituloBajas.getText() + "%';");
								
								if(tablaJuegosBajas.getRowCount() == 0){
					        		
					        		JOptionPane.showMessageDialog(rootPane, "No se econtraron juegos con esos parametros");	
					        		
					        		actualizarTabla(tablaJuegosBajas, "select * from juegos;");
					        	}
				        	}
				}
			});
	        
	        panel5.add(btnBuscarBajas);

	        frameBajas.add(panel5);

	        JPanel panel6 = new JPanel();
	        panel6.setLayout(null);
	        panel6.setPreferredSize(new Dimension(700, 190));
	        panel6.setBounds(0, 410, 700, 152);

	        tablaJuegosBajas = new JTable();
	        tablaJuegosBajas.setModel(new javax.swing.table.DefaultTableModel(
	                new Object[][] {}, new String [] {"idJuego", "Titulo", "Genero", "Estudio",
	                "Plataforma", "Cantidad", "Precio"}
	        ));

	        scrollBajas = new JScrollPane(tablaJuegosBajas);
	        scrollBajas.setBounds(5, 9, 678, 135);
	        
	        tablaJuegosBajas.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseClicked(MouseEvent e) {
					
					cargarDatos(tablaJuegosBajas);
					
					txtIdJuegoBajas.setText(idJuego);
					txtTituloBajas.setText(titulo);
					cboGeneroBajas.setSelectedIndex(genero);
					txtEstudioBajas.setText(estudio);
					cboPlataformaBajas.setSelectedIndex(plataforma);
					spinnerCantidadBajas.setValue(cantidad);
					spinnerPrecioBajas.setValue(precio);
				}
			});
	        panel6.add(scrollBajas);

	        frameBajas.add(panel6);

	        pane.add(frameBajas);

	        //------------------------------------- BAJAS ---------------------------------------
	        
	      //--------------------------------- MODIFICACIONES ----------------------------------
	        frameModificaciones = new JInternalFrame();
	        frameModificaciones.setDefaultCloseOperation(HIDE_ON_CLOSE);
	        frameModificaciones.setLayout(null);
	        frameModificaciones.setSize(700, 595);
	        frameModificaciones.setTitle("ACTUALIZAR INFORMACION DE JUEGOS");
	        frameModificaciones.setClosable(true);
	        frameModificaciones.setIconifiable(true);
	        frameModificaciones.setMaximizable(true);
	        frameModificaciones.setResizable(true);
	        frameModificaciones.setVisible(false);

	        JPanel panel7 = new JPanel();
	        panel7.setBackground(new Color(115, 170, 249));
	        panel7.setLayout(null);
	        panel7.setPreferredSize(new Dimension(700, 190));
	        panel7.setBounds(0, 0, 700, 80);

	        JLabel lbl3 = new JLabel("ACTUALIZAR INFORMACION DE JUEGOS");
	        lbl3.setFont(new Font("Arial", Font.BOLD, 24));
	        lbl3.setForeground(new Color(255, 255, 255));
	        lbl3.setBounds(30, 30, 500, 20);
	        panel7.add(lbl3);

	        frameModificaciones.add(panel7);

	        JPanel panel8 = new JPanel();
	        panel8.setLayout(null);
	        panel8.setBackground(new Color(255, 255, 255));
	        panel8.setPreferredSize(new Dimension(700, 190));
	        panel8.setBounds(0, 80, 700, 330);
	        
	        JLabel lblInformacion3 = new JLabel("Busca el juego que necesitas actualizar y seleccionalo para cargar sus datos: ");
	        lblInformacion3.setFont(f2);
	        lblInformacion3.setBounds(10, 30, 630, 25);
	        panel8.add(lblInformacion3);
	        
	        JLabel lblIdJuego2 = new JLabel("ID JUEGO: ");
	        lblIdJuego2.setFont(f2);
	        lblIdJuego2.setBounds(10, 100, 100, 25);
	        panel8.add(lblIdJuego2);

	        txtIdJuegoModificaciones = new JTextField(10);
	        txtIdJuegoModificaciones.setFont(f2);
	        txtIdJuegoModificaciones.setBounds(100, 100, 176, 23);
	        panel8.add(txtIdJuegoModificaciones);
	        
	        JLabel lblTitulo3 = new JLabel("TITULO: ");
	        lblTitulo3.setFont(f2);
	        lblTitulo3.setBounds(20, 150, 100, 25);
	        panel8.add(lblTitulo3);

	        txtTituloModificaciones = new JTextField(10);
	        txtTituloModificaciones.setFont(f2);
	        txtTituloModificaciones.setBounds(100, 150, 176, 23);
	        panel8.add(txtTituloModificaciones);

	        JLabel lblGenero3 = new JLabel("GENERO:");
	        lblGenero3.setFont(f2);
	        lblGenero3.setBounds(350, 90, 300, 25);
	        panel8.add(lblGenero3);

	        cboGeneroModificaciones = new JComboBox<String>();
	        cboGeneroModificaciones.addItem("Elige el genero...");
	        cboGeneroModificaciones.addItem("Disparos");
	        cboGeneroModificaciones.addItem("Terror");
	        cboGeneroModificaciones.addItem("Aventura");
	        cboGeneroModificaciones.addItem("Estrategia");
	        cboGeneroModificaciones.addItem("Peleas");
	        cboGeneroModificaciones.addItem("Deportes");
	        cboGeneroModificaciones.addItem("Carreras");
	        cboGeneroModificaciones.setFont(f1);
	        cboGeneroModificaciones.setBounds(460, 90, 176, 23);
	        panel8.add(cboGeneroModificaciones);

	        JLabel lblEstudio3 = new JLabel("ESTUDIO:");
	        lblEstudio3.setFont(f2);
	        lblEstudio3.setBounds(350, 130, 300, 25);
	        panel8.add(lblEstudio3);
	        
	        txtEstudioModificaciones = new JTextField(10);
	        txtEstudioModificaciones.setFont(f2);
	        txtEstudioModificaciones.setBounds(460, 130, 176, 23);
	        panel8.add(txtEstudioModificaciones);

	        JLabel lblPlataforma3 = new JLabel("PLATAFORMA:");
	        lblPlataforma3.setFont(f2);
	        lblPlataforma3.setBounds(350, 170, 300, 25);
	        panel8.add(lblPlataforma3);

	        cboPlataformaModificaciones = new JComboBox<String>();
	        cboPlataformaModificaciones.addItem("Elige la plataforma...");
	        cboPlataformaModificaciones.addItem("Xbox series");
	        cboPlataformaModificaciones.addItem("Playstation 5");
	        cboPlataformaModificaciones.addItem("Playstation 4");
	        cboPlataformaModificaciones.addItem("Nintendo switch");
	        cboPlataformaModificaciones.setFont(f1);
	        cboPlataformaModificaciones.setBounds(460, 170, 176, 23);
	        panel8.add(cboPlataformaModificaciones);

	        JLabel lblCantidad3 = new JLabel("CANTIDAD:");
	        lblCantidad3.setFont(f2);
	        lblCantidad3.setBounds(350, 210, 300, 25);
	        panel8.add(lblCantidad3);

			spinnerCantidadModificaciones = new JSpinner(model2);
			spinnerCantidadModificaciones.setBounds(460, 210, 176, 23);
			panel8.add(spinnerCantidadModificaciones);
	        
	        JLabel lblSemestre3 = new JLabel("PRECIO:");
	        lblSemestre3.setFont(f2);
	        lblSemestre3.setBounds(350, 250, 300, 25);
	        panel8.add(lblSemestre3);
	        
	        spinnerPrecioModificaciones = new JSpinner(model);
	        spinnerPrecioModificaciones.setBounds(460, 250, 176, 23);
			panel8.add(spinnerPrecioModificaciones);
	        
	        btnRestablecerModificaciones = new JButton("LIMPIAR");
	        btnRestablecerModificaciones.setFont(f2);
	        btnRestablecerModificaciones.setBounds(100, 250, 174, 30);
	        btnRestablecerModificaciones.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                metodoRestablecer(txtIdJuegoModificaciones, txtTituloModificaciones, cboGeneroModificaciones, txtEstudioModificaciones, cboPlataformaModificaciones, spinnerCantidadModificaciones, spinnerPrecioModificaciones);
	                actualizarTabla(tablaJuegosModificaciones, "select*from juegos;");
	            }
	        });
	        panel8.add(btnRestablecerModificaciones);

	        btnActualizar = new JButton("ACTUALIZAR");
	        btnActualizar.setFont(f2);
	        btnActualizar.setBounds(100, 200, 174, 30);
	        btnActualizar.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					if(txtTituloModificaciones.getText().isEmpty() || cboGeneroModificaciones.getSelectedIndex() == 0 
							|| txtEstudioModificaciones.getText().isEmpty() || cboPlataformaModificaciones.getSelectedIndex() == 0 
							|| spinnerCantidadModificaciones.getValue().equals(0) || spinnerPrecioModificaciones.getValue().equals(0)) {
						
						JOptionPane.showMessageDialog(rootPane, "Asegurate de llenar cada uno de los espacios");
						
					}else {
					
						int dialogButton = JOptionPane.YES_NO_OPTION;
			            JOptionPane.showConfirmDialog (null, "Cambiar la informacion de juego?","ADVERTENCIA", dialogButton);
						
			            if(dialogButton == JOptionPane.YES_OPTION) {
			            	
			            	Juego j = new Juego(txtIdJuegoModificaciones.getText(), txtTituloModificaciones.getText(), 
									cboGeneroModificaciones.getSelectedItem()+"",
									txtEstudioModificaciones.getText(),
									cboPlataformaModificaciones.getSelectedItem()+"",
			                        Integer.parseInt(spinnerCantidadModificaciones.getValue()+""),
			                        Double.parseDouble(spinnerPrecioModificaciones.getValue()+"") );
			                
			                jDAO = new JuegoDAO();
			                
			                String estado = "No se encontro el juego a modificar";
			        		
			        		System.out.println(jDAO.modificarRegistro(j)? estado = "Los cambios se han aplicado":"FALLO");
			        		
			        		JOptionPane.showMessageDialog(rootPane, estado);
			        		
			        		actualizarTabla(tablaJuegosModificaciones, "select * from juegos;");
			        		
			            if(dialogButton == JOptionPane.NO_OPTION) {
			                  remove(dialogButton);
			                }
			              }
					}
				}
			});
	        panel8.add(btnActualizar);
	        
	        btnBuscarModificaciones = new JButton(new ImageIcon("Iconos/Buscar.png"));
	        btnBuscarModificaciones.setFont(f2);
	        btnBuscarModificaciones.setBounds(280, 100, 60, 75);
	        btnBuscarModificaciones.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					if(txtIdJuegoModificaciones.getText().isEmpty() && txtTituloModificaciones.getText().isEmpty()) {
						
						JOptionPane.showMessageDialog(rootPane, "Ingresa informacion del nombreo o el ID del juego");	
						
		        	}else {
		        		
		        		if(txtIdJuegoModificaciones.getText().length()<=0) {
							txtIdJuegoModificaciones.setText("  ");
						}
						if(txtTituloModificaciones.getText().length()<=0) {
							txtTituloModificaciones.setText("  ");
						}
						
						actualizarTabla(tablaJuegosModificaciones, "SELECT * FROM juegos where idJuego like '" + txtIdJuegoModificaciones.getText() +
								  "%' OR titulo like '" + txtTituloModificaciones.getText() + "%';");
						
						if(tablaJuegosModificaciones.getRowCount() == 0){
			        		
			        		JOptionPane.showMessageDialog(rootPane, "No se econtraron juegos con esos parametros");	
			        		
			        		actualizarTabla(tablaJuegosModificaciones, "select * from juegos;");
			        	}
		        	}
				}
			});
	        
	        panel8.add(btnBuscarModificaciones);

	        frameModificaciones.add(panel8);

	        JPanel panel9 = new JPanel();
	        panel9.setLayout(null);
	        panel9.setPreferredSize(new Dimension(700, 190));
	        panel9.setBounds(0, 410, 700, 152);

	        tablaJuegosModificaciones = new JTable();
	        tablaJuegosModificaciones.setModel(new javax.swing.table.DefaultTableModel(
	                new Object[][] {}, new String [] {"idJuego", "Titulo", "Genero", "Estudio",
	                "Plataforma", "Cantidad", "Precio"}
	        ));

	        scrollModificaciones = new JScrollPane(tablaJuegosModificaciones);
	        scrollModificaciones.setBounds(5, 9, 678, 135);
	        
	        tablaJuegosModificaciones.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseClicked(MouseEvent e) {
					
					cargarDatos(tablaJuegosModificaciones);
					
					txtIdJuegoModificaciones.setText(idJuego);
					txtTituloModificaciones.setText(titulo);
					cboGeneroModificaciones.setSelectedIndex(genero);
					txtEstudioModificaciones.setText(estudio);
					cboPlataformaModificaciones.setSelectedIndex(plataforma);
					spinnerCantidadModificaciones.setValue(cantidad);
					spinnerPrecioModificaciones.setValue(precio);
				}
			});
	        
	        panel9.add(scrollModificaciones);

	        frameModificaciones.add(panel9);

	        pane.add(frameModificaciones);

	        //--------------------------------- MODIFICACIONES ----------------------------------
	        
	        //----------------------------------- CONSULTAS -------------------------------------
	        frameConsultas = new JInternalFrame();
	        frameConsultas.setDefaultCloseOperation(HIDE_ON_CLOSE);
	        frameConsultas.setLayout(null);
	        frameConsultas.setSize(700, 595);
	        frameConsultas.setTitle("Buscar Juegos");
	        frameConsultas.setClosable(true);
	        frameConsultas.setIconifiable(true);
	        frameConsultas.setMaximizable(true);
	        frameConsultas.setResizable(true);
	        frameConsultas.setVisible(false);

	        JPanel panel10 = new JPanel();
	        panel10.setBackground(new Color(115, 170, 249));
	        panel10.setLayout(null);
	        panel10.setPreferredSize(new Dimension(700, 190));
	        panel10.setBounds(0, 0, 700, 80);

	        JLabel lbl4 = new JLabel("BUSCAR JUEGOS");
	        lbl4.setFont(new Font("Arial", Font.BOLD, 24));
	        lbl4.setForeground(new Color(255, 255, 255));
	        lbl4.setBounds(30, 30, 400, 30);

	        panel10.add(lbl4);

	        frameConsultas.add(panel10);

	        JPanel panel11 = new JPanel();
	        panel11.setLayout(null);
	        panel11.setBackground(new Color(255, 255, 255));
	        panel11.setPreferredSize(new Dimension(700, 190));
	        panel11.setBounds(0, 80, 700, 330);
	        
	        JLabel lblInformacion4 = new JLabel("SELECCIONA TUS CRITERIOS DE BUSQUEDA Y PRECIONA EL BOTON BUSCAR: ");
	        lblInformacion4.setFont(f2);
	        lblInformacion4.setBounds(10, 30, 630, 25);
	        panel11.add(lblInformacion4);
	        
	        JLabel lblIdJuego3 = new JLabel("ID JUEGO: ");
	        lblIdJuego3.setFont(f2);
	        lblIdJuego3.setBounds(40, 100, 100, 25);
	        panel11.add(lblIdJuego3);

	        txtIdJuegoConsultas = new JTextField(10);
	        txtIdJuegoConsultas.setFont(f2);
	        txtIdJuegoConsultas.setBounds(150, 100, 176, 23);
	        panel11.add(txtIdJuegoConsultas);
	        
	        JLabel lblTitulo4 = new JLabel("TITULO: ");
	        lblTitulo4.setFont(f2);
	        lblTitulo4.setBounds(350, 100, 100, 25);
	        panel11.add(lblTitulo4);

	        txtTituloConsultas = new JTextField(10);
	        txtTituloConsultas.setFont(f2);
	        txtTituloConsultas.setBounds(450, 100, 176, 23);
	        panel11.add(txtTituloConsultas);

	        JLabel lblGenero4 = new JLabel("GENERO:");
	        lblGenero4.setFont(f2);
	        lblGenero4.setBounds(40, 150, 300, 25);
	        panel11.add(lblGenero4);

	        cboGeneroConsultas = new JComboBox<String>();
	        cboGeneroConsultas.addItem("Elige el genero...");
	        cboGeneroConsultas.addItem("Disparos");
	        cboGeneroConsultas.addItem("Terror");
	        cboGeneroConsultas.addItem("Aventura");
	        cboGeneroConsultas.addItem("Estrategia");
	        cboGeneroConsultas.addItem("Peleas");
	        cboGeneroConsultas.addItem("Deportes");
	        cboGeneroConsultas.addItem("Carreras");
	        cboGeneroConsultas.setFont(f1);
	        cboGeneroConsultas.setBounds(150, 150, 176, 23);
	        panel11.add(cboGeneroConsultas);

	        JLabel lblEstudio4 = new JLabel("ESTUDIO:");
	        lblEstudio4.setFont(f2);
	        lblEstudio4.setBounds(350, 150, 300, 25);
	        panel11.add(lblEstudio4);
	        
	        txtEstudioConsultas = new JTextField(10);
	        txtEstudioConsultas.setFont(f2);
	        txtEstudioConsultas.setBounds(450, 150, 176, 23);
	        panel11.add(txtEstudioConsultas);

	        JLabel lblPlataforma4 = new JLabel("PLATAFORMA:");
	        lblPlataforma4.setFont(f2);
	        lblPlataforma4.setBounds(40, 200, 300, 25);
	        panel11.add(lblPlataforma4);

	        cboPlataformaConsultas = new JComboBox<String>();
	        cboPlataformaConsultas.addItem("Elige la plataforma...");
	        cboPlataformaConsultas.addItem("Xbox series");
	        cboPlataformaConsultas.addItem("Playstation 5");
	        cboPlataformaConsultas.addItem("Playstation 4");
	        cboPlataformaConsultas.addItem("Nintendo switch");
	        cboPlataformaConsultas.setFont(f1);
	        cboPlataformaConsultas.setBounds(150, 200, 176, 23);
	        panel11.add(cboPlataformaConsultas);

	        JLabel lblCantidad4 = new JLabel("CANTIDAD:");
	        lblCantidad4.setFont(f2);
	        lblCantidad4.setBounds(350, 200, 300, 25);
	        panel11.add(lblCantidad4);
	        
			spinnerCantidadConsultas = new JSpinner(model2);
			spinnerCantidadConsultas.setBounds(450, 200, 176, 23);
			panel11.add(spinnerCantidadConsultas);
	        
	        JLabel lblSemestre4 = new JLabel("PRECIO:");
	        lblSemestre4.setFont(f2);
	        lblSemestre4.setBounds(40, 250, 300, 25);
	        panel11.add(lblSemestre4);
	        
	        spinnerPrecioConsultas = new JSpinner(model);
	        spinnerPrecioConsultas.setBounds(150, 250, 176, 23);
			panel11.add(spinnerPrecioConsultas);
	        
	        btnRestablecerConsultas = new JButton("LIMPIAR");
	        btnRestablecerConsultas.setFont(f2);
	        btnRestablecerConsultas.setBounds(500, 250, 140, 25);
	        btnRestablecerConsultas.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                metodoRestablecer(txtIdJuegoConsultas, txtTituloConsultas, cboGeneroConsultas, txtEstudioConsultas, cboPlataformaConsultas, spinnerCantidadConsultas, spinnerPrecioConsultas);
	                actualizarTabla(tablaJuegosConsultas, "select*from juegos;");
	            }
	        });
	        panel11.add(btnRestablecerConsultas);

	        btnBuscarConsultas = new JButton("BUSCAR");
	        btnBuscarConsultas.setFont(f2);
	        btnBuscarConsultas.setBounds(350, 250, 140, 25);
	        btnBuscarConsultas.addActionListener(new ActionListener() {
	        	
				@Override
				public void actionPerformed(ActionEvent e) {
					
					if(txtIdJuegoConsultas.getText().isEmpty() && txtTituloConsultas.getText().isEmpty() 
					   && cboGeneroConsultas.getSelectedIndex() == 0 && txtEstudioConsultas.getText().isEmpty()
					   && cboPlataformaConsultas.getSelectedIndex() == 0 && spinnerCantidadModificaciones.getValue().equals(0)
					   && spinnerPrecioModificaciones.getValue().equals(0)) {
						
						JOptionPane.showMessageDialog(rootPane, "Ingresa por lo menos un parametro de busqueda");	
		        	}else {
		        		
		        		if(txtIdJuegoConsultas.getText().length()<=0) {
							txtIdJuegoConsultas.setText("  ");
						}
						if(txtTituloConsultas.getText().length()<=0) {
							txtTituloConsultas.setText("  ");
						}
						if(txtEstudioConsultas.getText().length()<=0) {
							txtEstudioConsultas.setText("  ");
						}
						
						actualizarTabla(tablaJuegosConsultas, "SELECT * FROM juegos where idJuego like '" + txtIdJuegoConsultas.getText() +
								  "%' OR titulo like '" + txtTituloConsultas.getText() + 
								  "%' OR genero like '" + cboGeneroConsultas.getSelectedItem() + 
								  "%' OR estudio like '" + txtEstudioConsultas.getText() + 
								  "%' OR plataforma like '" + cboPlataformaConsultas.getSelectedItem() + 
								  "%' OR cantidad <= " + Integer.parseInt(spinnerCantidadConsultas.getValue()+"") + 
								    " OR precio <= " + df.format(Double.parseDouble(spinnerPrecioConsultas.getValue()+"")) + ";");
						
						if(tablaJuegosConsultas.getRowCount() == 0){
			        		
			        		JOptionPane.showMessageDialog(rootPane, "No se econtraton juegos con esos parametros");	
			        		
			        		actualizarTabla(tablaJuegosConsultas, "select * from juegos;");
			        	}
		        	}
				}
			});
	        panel11.add(btnBuscarConsultas);

	        frameConsultas.add(panel11);

	        JPanel panel12 = new JPanel();
	        panel12.setLayout(null);
	        panel12.setPreferredSize(new Dimension(700, 190));
	        panel12.setBounds(0, 410, 700, 152);

	        tablaJuegosConsultas = new JTable();
	        tablaJuegosConsultas.setModel(new javax.swing.table.DefaultTableModel(
	                new Object[][] {}, new String [] {"idJuego", "Titulo", "Genero", "Estudio",
	                "Plataforma", "Cantidad", "Precio"}
	        ));

	        scrollConsultas = new JScrollPane(tablaJuegosConsultas);
	        scrollConsultas.setBounds(5, 9, 678, 135);
	        
	        panel12.add(scrollConsultas);

	        frameConsultas.add(panel12);

	        pane.add(frameConsultas);

	        //----------------------------------- CONSULTAS -------------------------------------
	       
	        ventana.add(pane, BorderLayout.CENTER);
	}
	 
	 public void actualizarTabla(JTable tabla,String com){

			String controlador = "com.mysql.cj.jdbc.Driver";
    		String url = "jdbc:mysql://localhost:3306/TiendaDeVideoJuegos";

			ResultSetTableModel modelo = null;
			try {
				modelo = new ResultSetTableModel(controlador, url, com);
			} catch (SQLException e1) {
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
			
			tablaJuegosAltas.setModel(modelo);
			tablaJuegosBajas.setModel(modelo);
			tablaJuegosModificaciones.setModel(modelo);
			tablaJuegosConsultas.setModel(modelo);
		}
	 
	 public void metodoRestablecer(JComponent...componentesGraficos) {
			
			for (JComponent c : componentesGraficos) {
				c.getClass();
				
				if(c instanceof JComboBox) {
					((JComboBox<?>) c).setSelectedIndex(0);
				}else if(c instanceof JTextField) {
					((JTextField) c).setText("");
				}else if(c instanceof JSpinner) {
					((JSpinner) c).setValue(0);;
				}
			}
		}
	 
	 public void cargarDatos(JTable tabla) {
		 
		 TableModel tableModel = tablaJuegosModificaciones.getModel();

			idJuego = tableModel.getValueAt(tabla.getSelectedRow(), 0)+"";
			titulo = tableModel.getValueAt(tabla.getSelectedRow(), 1)+"";
			estudio = tableModel.getValueAt(tabla.getSelectedRow(), 3)+"";
			cantidad = Integer.parseInt(tableModel.getValueAt(tabla.getSelectedRow(), 5)+"");
			precio = Double.parseDouble(tableModel.getValueAt(tabla.getSelectedRow(), 6)+"");
			
			if(tableModel.getValueAt(tabla.getSelectedRow(), 2).equals("Disparos")) {
				genero = 1;
			}else if(tableModel.getValueAt(tabla.getSelectedRow(), 2).equals("Terror")) {
				genero = 2;
			}else if(tableModel.getValueAt(tabla.getSelectedRow(), 2).equals("Aventura")) {
				genero = 3;
			}else if(tableModel.getValueAt(tabla.getSelectedRow(), 2).equals("Estrategia")) {
				genero = 4;
			}else if(tableModel.getValueAt(tabla.getSelectedRow(), 2).equals("Peleas")) {
				genero = 5;
			}else if(tableModel.getValueAt(tabla.getSelectedRow(), 2).equals("Deportes")) {
				genero = 6;
			}else if(tableModel.getValueAt(tabla.getSelectedRow(), 2).equals("Carreras")) {
				genero = 7;
			}

			if(tableModel.getValueAt(tabla.getSelectedRow(), 4).equals("Xbox series")) {
				plataforma = 1;
			}else if(tableModel.getValueAt(tabla.getSelectedRow(), 4).equals("Playstation 5")) {
				plataforma = 2;
			}else if(tableModel.getValueAt(tabla.getSelectedRow(), 4).equals("Playstation 4")) {
				plataforma = 3;
			}else if(tableModel.getValueAt(tabla.getSelectedRow(), 4).equals("Nintendo switch")) {
				plataforma = 4;
			}
	 }
	 
	 public String obtenerMaxId() {
		
		String controlador = "com.mysql.cj.jdbc.Driver";
 		String url = "jdbc:mysql://localhost:3306/TiendaDeVideoJuegos";

			ResultSetTableModel modelo = null;
			try {
				modelo = new ResultSetTableModel(controlador, url, "SELECT MAX(idJuego) AS id FROM juegos;");
			} catch (SQLException e1) {
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
			
			return maxId = modelo.getValueAt(0, 0)+"";
	 }
	 
	public static void main(String[] args) {
		
		 try {
	            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
	        } catch (ClassNotFoundException e) {
	            e.printStackTrace();
	        } catch (InstantiationException e) {
	            e.printStackTrace();
	        } catch (IllegalAccessException e) {
	            e.printStackTrace();
	        } catch (UnsupportedLookAndFeelException e) {
	            e.printStackTrace();
	        }
     
	        SwingUtilities.invokeLater(new Runnable(){
	            @Override
	            public void run() {
	                new VentanaInicio();
	            }
	        });
	}
}
