package vista;

import javax.print.CancelablePrintJob;
import javax.swing.*;
import javax.swing.table.TableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.*;
import controlador.JuegoDAO;
import modelo.Juego;



public class VentanaInicio extends JFrame{
	
	 public VentanaInicio() {

		 crearComponentes();
		 
	    }
	 
	 JInternalFrame frameInicioSesion, frameAltas, frameBajas, frameModificaciones, frameConsultas;
	 
	 JTextField txtUsuario, txtPassword,
	 			txtTituloAltas,
	 			txtIdJuegoBajas, txtTituloBajas,
	 			txtIdJuegoModificaciones, txtTituloModificaciones,
	 			txtIdJuegoConsultas, txtTituloConsultas;
	 
	 JButton btnEntrar, btnCerrar,
	 		 btnAgregarAltas, btnRestablecerAltas, 
	         btnEliminar, btnRestablecerBajas,
	         btnActualizar, btnRestablecerModificaciones,
	         btnBuscar, btnRestablecerConsultas;
	 
	 JComboBox<String> cboGeneroAltas, cboEstudioAltas, cboPlataformaAltas,
	 				   cboGeneroBajas, cboEstudioBajas, cboPlataformaBajas,
	 				   cboGeneroModificaciones, cboEstudioModificaciones, cboPlataformaModificaciones,
	 				   cboGeneroConsultas, cboEstudioConsultas, cboPlataformaConsultas;
	
	 JScrollPane scrollAltas, scrollBajas, scrollModificaciones, scrolConsultas;
	 
	 JTable tablaJuegosAltas, tablaJuegosBajas, tablaJuegosModificaciones, tablaJuegosConsultas;
	 
	 JSpinner spinnerCantidadAltas, spinnerPrecioAltas,
	 		  spinnerCantidadBajas, spinnerPrecioBajas,
	 		  spinnerCantidadModificaciones, spinnerPrecioModificaciones, 
	 		  spinnerCantidadConsultas, spinnerPrecioConsultas;
	 		  
	 JuegoDAO jDAO;
	 
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
	                
	            	actualizarTabla(tablaJuegosConsultas, "select*from juegos;");
	            	
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
			
	        JLabel lblIniciarSesion = new JLabel("INICIAR SESI�N");
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
	        
	        JLabel lblPassword = new JLabel("CONTRASE�A: ");
	        lblPassword.setFont(f2);
	        lblPassword.setBounds(105, 250, 400, 25);
	        panelInicioSesion.add(lblPassword);

	        txtPassword = new JTextField(10);
	        txtPassword.setFont(f2);
	        txtPassword.setBounds(70, 280, 176, 23);
	        panelInicioSesion.add(txtPassword);
	        
	        btnEntrar = new JButton("ENTRAR");
	        btnEntrar.setFont(f2);
	        btnEntrar.setBounds(50, 320, 100, 25);
	        btnEntrar.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					inicio.setVisible(false);
					ventana.setVisible(true);
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
	        
	        cboEstudioAltas = new JComboBox<String>();
	        cboEstudioAltas.addItem("Elige el estudio...");
	        cboEstudioAltas.addItem("Ubisoft");
	        cboEstudioAltas.addItem("Capcom");
	        cboEstudioAltas.addItem("Epic Games");
	        cboEstudioAltas.addItem("Electronic Arts");
	        cboEstudioAltas.addItem("Microsoft Studios");
	        cboEstudioAltas.addItem("Game Freak");
	        cboEstudioAltas.setFont(f1);
	        cboEstudioAltas.setBounds(330, 130, 176, 23);
	        panel2.add(cboEstudioAltas);

	        JLabel lblPlataforma = new JLabel("PLATAFORMA:");
	        lblPlataforma.setFont(f2);
	        lblPlataforma.setBounds(180, 170, 300, 25);
	        panel2.add(lblPlataforma);

	        cboPlataformaAltas = new JComboBox<String>();
	        cboPlataformaAltas.addItem("Elige la plataforma...");
	        cboPlataformaAltas.addItem("Xbox Series");
	        cboPlataformaAltas.addItem("Playstation 5");
	        cboPlataformaAltas.addItem("Playstation 4");
	        cboPlataformaAltas.addItem("Nintendos Switsh");
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
	        btnRestablecerAltas.setBounds(180, 290, 120, 30);
	        btnRestablecerAltas.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                metodoRestablecer(txtTituloAltas, cboGeneroAltas, cboEstudioAltas, cboPlataformaAltas, spinnerCantidadAltas, spinnerPrecioAltas);
	            }
	        });
	        panel2.add(btnRestablecerAltas);

	        btnAgregarAltas = new JButton("AGREGAR");
	        btnAgregarAltas.setFont(f2);
	        btnAgregarAltas.setBounds(330, 290, 174, 25);
	        btnAgregarAltas.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					Juego j = new Juego("j15", txtTituloAltas.getText(), 
										cboGeneroAltas.getSelectedItem()+"",
										cboEstudioAltas.getSelectedItem()+"",
										cboPlataformaAltas.getSelectedItem()+"",
				                        Integer.parseInt(spinnerCantidadAltas.getValue()+""),
				                        Double.parseDouble(spinnerPrecioAltas.getValue()+"")
	                        );
	                
	                jDAO = new JuegoDAO();
	        		
	        		System.out.println(jDAO.insertarRegistro(j)?"EXITO":"FALLO");
					
					actualizarTabla(tablaJuegosAltas, "select*from juegos;");
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
	        
	        JLabel lblInformacion2 = new JLabel("Busca el juego que quieres sacar del inventario y seleccionalo para cargar sus datos: ");
	        lblInformacion2.setFont(f2);
	        lblInformacion2.setBounds(10, 30, 630, 25);
	        panel5.add(lblInformacion2);
	        
	        JLabel lblIdJuego = new JLabel("ID JUEGO: ");
	        lblIdJuego.setFont(f2);
	        lblIdJuego.setBounds(50, 100, 100, 25);
	        panel5.add(lblIdJuego);

	        txtIdJuegoBajas = new JTextField(10);
	        txtIdJuegoBajas.setFont(f2);
	        txtIdJuegoBajas.setBounds(150, 100, 176, 23);
	        panel5.add(txtIdJuegoBajas);
	        
	        JLabel lblTitulo2 = new JLabel("TITULO: ");
	        lblTitulo2.setFont(f2);
	        lblTitulo2.setBounds(50, 150, 100, 25);
	        panel5.add(lblTitulo2);

	        txtTituloBajas = new JTextField(10);
	        txtTituloBajas.setFont(f2);
	        txtTituloBajas.setBounds(150, 150, 176, 23);
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
	        
	        cboEstudioBajas = new JComboBox<String>();
	        cboEstudioBajas.addItem("Elige el estudio...");
	        cboEstudioBajas.addItem("Ubisoft");
	        cboEstudioBajas.addItem("Capcom");
	        cboEstudioBajas.addItem("Epic Games");
	        cboEstudioBajas.addItem("Electronic Arts");
	        cboEstudioBajas.addItem("Microsoft Studios");
	        cboEstudioBajas.addItem("Game Freak");
	        cboEstudioBajas.setFont(f1);
	        cboEstudioBajas.setBounds(460, 130, 176, 23);
	        panel5.add(cboEstudioBajas);

	        JLabel lblPlataforma2 = new JLabel("PLATAFORMA:");
	        lblPlataforma2.setFont(f2);
	        lblPlataforma2.setBounds(350, 170, 300, 25);
	        panel5.add(lblPlataforma2);

	        cboPlataformaBajas = new JComboBox<String>();
	        cboPlataformaBajas.addItem("Elige la plataforma...");
	        cboPlataformaBajas.addItem("Xbox Series");
	        cboPlataformaBajas.addItem("Playstation 5");
	        cboPlataformaBajas.addItem("Playstation 4");
	        cboPlataformaBajas.addItem("Nintendos Switsh");
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
	        btnRestablecerBajas.setBounds(150, 250, 174, 30);
	        btnRestablecerBajas.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                metodoRestablecer(txtIdJuegoBajas, txtTituloBajas, cboGeneroBajas, cboEstudioBajas, cboPlataformaBajas, spinnerCantidadBajas, spinnerPrecioBajas);
	            }
	        });
	        panel5.add(btnRestablecerBajas);

	        btnEliminar = new JButton("ELIMINAR");
	        btnEliminar.setFont(f2);
	        btnEliminar.setBounds(150, 200, 174, 30);
	        btnEliminar.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					String id = txtIdJuegoBajas.getText();
	            	
	            	jDAO = new JuegoDAO();
	        		
	        		System.out.println(jDAO.eliminarRegistro(id)?"EXITO":"Me cambio de carrera");
	        		
	        		actualizarTabla(tablaJuegosBajas, "select * from juegos;");
					
				}
			});
	        panel5.add(btnEliminar);

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
	        lblIdJuego2.setBounds(50, 100, 100, 25);
	        panel8.add(lblIdJuego2);

	        txtIdJuegoModificaciones = new JTextField(10);
	        txtIdJuegoModificaciones.setFont(f2);
	        txtIdJuegoModificaciones.setBounds(150, 100, 176, 23);
	        panel8.add(txtIdJuegoModificaciones);
	        
	        JLabel lblTitulo3 = new JLabel("TITULO: ");
	        lblTitulo3.setFont(f2);
	        lblTitulo3.setBounds(50, 150, 100, 25);
	        panel8.add(lblTitulo3);

	        txtTituloModificaciones = new JTextField(10);
	        txtTituloModificaciones.setFont(f2);
	        txtTituloModificaciones.setBounds(150, 150, 176, 23);
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
	        
	        cboEstudioModificaciones = new JComboBox<String>();
	        cboEstudioModificaciones.addItem("Elige el estudio...");
	        cboEstudioModificaciones.addItem("Ubisoft");
	        cboEstudioModificaciones.addItem("Capcom");
	        cboEstudioModificaciones.addItem("Epic Games");
	        cboEstudioModificaciones.addItem("Electronic Arts");
	        cboEstudioModificaciones.addItem("Microsoft Studios");
	        cboEstudioModificaciones.addItem("Game Freak");
	        cboEstudioModificaciones.setFont(f1);
	        cboEstudioModificaciones.setBounds(460, 130, 176, 23);
	        panel8.add(cboEstudioModificaciones);

	        JLabel lblPlataforma3 = new JLabel("PLATAFORMA:");
	        lblPlataforma3.setFont(f2);
	        lblPlataforma3.setBounds(350, 170, 300, 25);
	        panel8.add(lblPlataforma3);

	        cboPlataformaModificaciones = new JComboBox<String>();
	        cboPlataformaModificaciones.addItem("Elige la plataforma...");
	        cboPlataformaModificaciones.addItem("Xbox Series");
	        cboPlataformaModificaciones.addItem("Playstation 5");
	        cboPlataformaModificaciones.addItem("Playstation 4");
	        cboPlataformaModificaciones.addItem("Nintendos Switsh");
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
	        btnRestablecerModificaciones.setBounds(150, 250, 174, 30);
	        btnRestablecerModificaciones.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                metodoRestablecer(txtIdJuegoModificaciones, txtTituloModificaciones, cboGeneroModificaciones, cboEstudioModificaciones, cboPlataformaModificaciones, spinnerCantidadModificaciones, spinnerPrecioModificaciones);
	            }
	        });
	        panel8.add(btnRestablecerModificaciones);

	        btnActualizar = new JButton("ACTUALIZAR");
	        btnActualizar.setFont(f2);
	        btnActualizar.setBounds(150, 200, 174, 30);
	        panel8.add(btnActualizar);

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
					
					TableModel tableModel = tablaJuegosModificaciones.getModel();

					txtIdJuegoModificaciones.setText(tableModel.getValueAt(tablaJuegosModificaciones.getSelectedRow(), 0)+"");
					txtTituloModificaciones.setText(tableModel.getValueAt(tablaJuegosModificaciones.getSelectedRow(), 1)+"");
					
					if(tableModel.getValueAt(tablaJuegosModificaciones.getSelectedRow(), 2).equals("Disparos")) {
						cboGeneroModificaciones.setSelectedIndex(1);
					}else if(tableModel.getValueAt(tablaJuegosModificaciones.getSelectedRow(), 2).equals("Terror")) {
						cboGeneroModificaciones.setSelectedIndex(2);
					}else if(tableModel.getValueAt(tablaJuegosModificaciones.getSelectedRow(), 2).equals("Aventura")) {
						cboGeneroModificaciones.setSelectedIndex(3);
					}else if(tableModel.getValueAt(tablaJuegosModificaciones.getSelectedRow(), 2).equals("Estrategia")) {
						cboGeneroModificaciones.setSelectedIndex(4);
					}else if(tableModel.getValueAt(tablaJuegosModificaciones.getSelectedRow(), 2).equals("Peleas")) {
						cboGeneroModificaciones.setSelectedIndex(5);
					}else if(tableModel.getValueAt(tablaJuegosModificaciones.getSelectedRow(), 2).equals("Deportes")) {
						cboGeneroModificaciones.setSelectedIndex(6);
					}else if(tableModel.getValueAt(tablaJuegosModificaciones.getSelectedRow(), 2).equals("Carreras")) {
						cboGeneroModificaciones.setSelectedIndex(7);
					}
					
					
					if(tableModel.getValueAt(tablaJuegosModificaciones.getSelectedRow(), 2).equals("Capcom")) {
						cboEstudioModificaciones.setSelectedIndex(1);
					}else if(tableModel.getValueAt(tablaJuegosModificaciones.getSelectedRow(), 2).equals("Epic Games")) {
						cboEstudioModificaciones.setSelectedIndex(2);
					}else if(tableModel.getValueAt(tablaJuegosModificaciones.getSelectedRow(), 2).equals("Electronic Arts")) {
						cboEstudioModificaciones.setSelectedIndex(3);
					}else if(tableModel.getValueAt(tablaJuegosModificaciones.getSelectedRow(), 2).equals("Microsoft Studios")) {
						cboEstudioModificaciones.setSelectedIndex(4);
					}else if(tableModel.getValueAt(tablaJuegosModificaciones.getSelectedRow(), 2).equals("Game Freak")) {
						cboEstudioModificaciones.setSelectedIndex(5);
					}else if(tableModel.getValueAt(tablaJuegosModificaciones.getSelectedRow(), 2).equals("Deportes")) {
						cboEstudioModificaciones.setSelectedIndex(6);
					}
					
					if(tableModel.getValueAt(tablaJuegosModificaciones.getSelectedRow(), 2).equals("Xbox Series")) {
						cboPlataformaModificaciones.setSelectedIndex(1);
					}else if(tableModel.getValueAt(tablaJuegosModificaciones.getSelectedRow(), 2).equals("Playstation 5")) {
						cboPlataformaModificaciones.setSelectedIndex(2);
					}else if(tableModel.getValueAt(tablaJuegosModificaciones.getSelectedRow(), 2).equals("Playstation 4")) {
						cboPlataformaModificaciones.setSelectedIndex(3);
					}else if(tableModel.getValueAt(tablaJuegosModificaciones.getSelectedRow(), 2).equals("Nintendos Switsh")) {
						cboPlataformaModificaciones.setSelectedIndex(4);
					}
					
					spinnerCantidadModificaciones.setValue(tableModel.getValueAt(tablaJuegosModificaciones.getSelectedRow(), 5));
					spinnerPrecioModificaciones.setValue(tableModel.getValueAt(tablaJuegosModificaciones.getSelectedRow(), 6));
					
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
	        
	        JLabel lblInformacion4 = new JLabel("SELECCIONA TUS CRITERIOS DE BUSQUEDA: ");
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
	        
	        cboEstudioConsultas = new JComboBox<String>();
	        cboEstudioConsultas.addItem("Elige el estudio...");
	        cboEstudioConsultas.addItem("Ubisoft");
	        cboEstudioConsultas.addItem("Capcom");
	        cboEstudioConsultas.addItem("Epic Games");
	        cboEstudioConsultas.addItem("Electronic Arts");
	        cboEstudioConsultas.addItem("Microsoft Studios");
	        cboEstudioConsultas.addItem("Game Freak");
	        cboEstudioConsultas.setFont(f1);
	        cboEstudioConsultas.setBounds(450, 150, 176, 23);
	        panel11.add(cboEstudioConsultas);

	        JLabel lblPlataforma4 = new JLabel("PLATAFORMA:");
	        lblPlataforma4.setFont(f2);
	        lblPlataforma4.setBounds(40, 200, 300, 25);
	        panel11.add(lblPlataforma4);

	        cboPlataformaConsultas = new JComboBox<String>();
	        cboPlataformaConsultas.addItem("Elige la plataforma...");
	        cboPlataformaConsultas.addItem("Xbox Series");
	        cboPlataformaConsultas.addItem("Playstation 5");
	        cboPlataformaConsultas.addItem("Playstation 4");
	        cboPlataformaConsultas.addItem("Nintendos Switsh");
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
	                metodoRestablecer(txtIdJuegoConsultas, txtTituloConsultas, cboGeneroConsultas, cboEstudioConsultas, cboPlataformaConsultas, spinnerCantidadConsultas, spinnerPrecioConsultas);
	                actualizarTabla(tablaJuegosConsultas, "select*from juegos;");
	            }
	        });
	        panel11.add(btnRestablecerConsultas);

	        btnBuscar = new JButton("BUSCAR");
	        btnBuscar.setFont(f2);
	        btnBuscar.setBounds(350, 250, 140, 25);
	        btnBuscar.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					if(txtIdJuegoConsultas.getText().length()<=0) {
						txtIdJuegoConsultas.setText("  ");
					}
					if(txtTituloConsultas.getText().length()<=0) {
						txtTituloConsultas.setText("  ");
					}
					
					actualizarTabla(tablaJuegosConsultas, "SELECT * FROM juegos where idJuego like '" + txtIdJuegoConsultas.getText() +
							  "%' OR titulo like '" + txtTituloConsultas.getText() + 
							  "%' OR genero like '" + cboGeneroConsultas.getSelectedItem() + 
							  "%' OR estudio like '" + cboEstudioConsultas.getSelectedItem() + 
							  "%' OR plataforma like '" + cboPlataformaConsultas.getSelectedItem() + 
							  "%' OR cantidad = " + Integer.parseInt(spinnerCantidadConsultas.getValue()+"") + 
							    " OR precio <= " + df.format(Double.parseDouble(spinnerPrecioConsultas.getValue()+"")) + ";");
				}
			});
	        panel11.add(btnBuscar);

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

	        scrollModificaciones = new JScrollPane(tablaJuegosConsultas);
	        scrollModificaciones.setBounds(5, 9, 678, 135);
	        panel12.add(scrollModificaciones);

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
			
			return maxId = modelo+"";
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
