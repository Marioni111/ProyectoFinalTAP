package vista;

import javax.print.CancelablePrintJob;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;

import controlador.JuegoDAO;
import modelo.Juego;



public class VentanaInicio extends JFrame{
	
	 public VentanaInicio() {
	        crearComponentes();
	    }
	 
	 JInternalFrame frameAltas, frameBajas, frameModificaciones, frameConsultas;
	 
	 JTextField txtTituloAltas, txtPrecioAltas,
	 			txtIdJuegoBajas, txtNombresBajas, txtPrimerApBajas, txtSegundoApBajas,
	 			txtIdJuegoModificaciones, txtNombresModificaciones, txtPrimerApModificaciones, txtSegundoApModificaciones,
	 			txtNumControlConsultas, txtNombresConsultas, txtPrimerApConsultas, txtSegundoApConsultas;
	 
	 JButton btnAgregarAltas, btnRestablecerAltas, 
	 		 btnEliminar, btnRestablecerBajas2,  btnBuscarBajas, 
	 		 btnModificar, btnRestablecerModificaciones2, btnBuscarModificaciones,
	 		 btnRestablecerConsultas, btnBuscarConsultas;
	 
	 JComboBox<String> cboGeneroAltas, cboEstudioAltas, cboPlataformaAltas,
	 					cboCarreraBajas, cboCarreraModificaciones, cboCarreraConsultas;
	
	 JScrollPane scrollAltas, scrollBajas, scrollModificaciones, scrolConsultas;
	 
	 JTable tablaJuegosAltas, tablaJuegosBajas, tablaJuegosModificaciones, tablaJuegosConsultas;
	 
	 JSpinner spinnerCantidadAltas, spinnerEdadBajas, spinnerSemestreBajas, spinnerEdadModificaciones, spinnerSemestreModificaciones, spinnerEdadConsultas, spinnerSemestreConsultas;
	 
	 JuegoDAO aDAO;
	 
	 ResultSetTableModel modeloDatos = null;
	 
	 public void crearComponentes() {
	        
		    getContentPane().setLayout(new BorderLayout());
	        setDefaultCloseOperation(EXIT_ON_CLOSE);
	        setTitle("Control de inventario de Iguan Games");
	        setSize(710, 660);
	        setLocationRelativeTo(null);
	        setVisible(true);

	        JDesktopPane pane = new JDesktopPane();

	        //-------------------------------------- MENU PRINCIPAL ----------------------------------

	        JMenuBar menuBar = new JMenuBar();
	        JMenu menu = new JMenu("Menu Juegos");
	        JMenuItem menuCon = new JMenuItem("Buscar Juego");
	        menuCon.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.ALT_MASK));
	        menuCon.addActionListener(new ActionListener() {

	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	
	            	actualizarTabla();
	            	
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
					
	        		actualizarTabla();
	        		
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
	            	
	            	actualizarTabla();
	            	
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
	                
	            	actualizarTabla();
	            	
	            	frameAltas.setVisible(false);
					frameBajas.setVisible(true);
					frameModificaciones.setVisible(false);
					frameConsultas.setVisible(false);
	            }
	        });
	        menu.add(menuBaj);
	        
	        menuBar.add(menu);
	        setJMenuBar(menuBar);

	        //--------------------------------------- FIN DE MENU -------------------------------------
	        
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

	        Font f1 = new Font("Arial", Font.ITALIC, 12);
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

	        Font f2 = new Font("Arial", Font.CENTER_BASELINE, 14);
	        JPanel panel2 = new JPanel();
	        panel2.setLayout(null);
	        panel2.setPreferredSize(new Dimension(700, 190));
	        panel2.setBounds(0, 80, 700, 330);

	        JLabel lblInformacion = new JLabel("INGRESA LOS DATOS DEL JUEGO NUEVO: ");
	        lblInformacion.setFont(f2);
	        lblInformacion.setBounds(70, 10, 400, 25);
	        panel2.add(lblInformacion);
	        
	        JLabel lblTitulo = new JLabel("TITULO: ");
	        lblTitulo.setFont(f2);
	        lblTitulo.setBounds(100, 50, 400, 25);
	        panel2.add(lblTitulo);

	        txtTituloAltas = new JTextField(10);
	        txtTituloAltas.setFont(f2);
	        txtTituloAltas.setBounds(255, 50, 176, 23);
	        panel2.add(txtTituloAltas);

	        JLabel lblGenero = new JLabel("GENERO:");
	        lblGenero.setFont(f2);
	        lblGenero.setBounds(100, 90, 300, 25);
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
	        cboGeneroAltas.setBounds(255, 90, 176, 23);
	        panel2.add(cboGeneroAltas);

	        JLabel lblEstudio = new JLabel("ESTUDIO:");
	        lblEstudio.setFont(f2);
	        lblEstudio.setBounds(100, 130, 300, 25);
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
	        cboEstudioAltas.setBounds(255, 130, 176, 23);
	        panel2.add(cboEstudioAltas);

	        JLabel lblPlataforma = new JLabel("PLATAFORMA:");
	        lblPlataforma.setFont(f2);
	        lblPlataforma.setBounds(100, 170, 300, 25);
	        panel2.add(lblPlataforma);

	        cboPlataformaAltas = new JComboBox<String>();
	        cboPlataformaAltas.addItem("Elige la plataforma...");
	        cboPlataformaAltas.addItem("Xbox Series");
	        cboPlataformaAltas.addItem("Playstation 5");
	        cboPlataformaAltas.addItem("Playstation 4");
	        cboPlataformaAltas.addItem("Nintendos Switsh");
	        cboPlataformaAltas.setFont(f1);
	        cboPlataformaAltas.setBounds(255, 170, 176, 23);
	        panel2.add(cboPlataformaAltas);

	        JLabel lblCantidad = new JLabel("CANTIDAD:");
	        lblCantidad.setFont(f2);
	        lblCantidad.setBounds(100, 210, 300, 25);
	        panel2.add(lblCantidad);

			spinnerCantidadAltas = new JSpinner();
			spinnerCantidadAltas.setBounds(255, 210, 176, 23);
			panel2.add(spinnerCantidadAltas);
	        
	        JLabel lblSemestre = new JLabel("PRECIO:");
	        lblSemestre.setFont(f2);
	        lblSemestre.setBounds(100, 250, 300, 25);
	        panel2.add(lblSemestre);

	        txtPrecioAltas = new JTextField(10);
	        txtPrecioAltas.setFont(f2);
	        txtPrecioAltas.setBounds(255, 250, 176, 23);
	        panel2.add(txtPrecioAltas);
	        
	        btnRestablecerAltas = new JButton("LIMPIAR");
	        btnRestablecerAltas.setFont(f2);
	        btnRestablecerAltas.setBounds(460, 100, 120, 30);
	        btnRestablecerAltas.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                metodoRestablecer(txtTituloAltas, cboGeneroAltas, cboEstudioAltas, cboPlataformaAltas, spinnerCantidadAltas, txtPrecioAltas);
	            }
	        });
	        panel2.add(btnRestablecerAltas);

	        btnAgregarAltas = new JButton("AGREGAR");
	        btnAgregarAltas.setFont(f2);
	        btnAgregarAltas.setBounds(255, 290, 174, 25);
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
	        panel5.setPreferredSize(new Dimension(700, 190));
	        panel5.setBounds(0, 80, 700, 80);

	        JLabel lblIdJuego = new JLabel("id del Juego:");
	        lblIdJuego.setFont(f2);
	        lblIdJuego.setBounds(80, 30, 400, 25);
	        panel5.add(lblIdJuego);

	        txtIdJuegoBajas = new JTextField(10);
	        txtIdJuegoBajas.setFont(f2);
	        txtIdJuegoBajas.setBounds(250, 30, 150, 23);
	        panel5.add(txtIdJuegoBajas);

	        btnBuscarBajas = new JButton(new ImageIcon("Iconos/Buscar.png"));
	        btnBuscarBajas.setBounds(420, 20, 50, 45);
	        btnBuscarBajas.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	
	            }
	        });
	        panel5.add(btnBuscarBajas);

	        JLabel lblLinea = new JLabel("_____________________________________________________________________________");
	        lblLinea.setFont(f2);
	        lblLinea.setBounds(0, 60, 630, 20);
	        panel5.add(lblLinea);

	        frameBajas.add(panel5);

	        JPanel panel6 = new JPanel();
	        panel6.setLayout(null);
	        panel6.setPreferredSize(new Dimension(700, 190));
	        panel6.setBounds(0, 160, 700, 250);

	        JLabel lblNombres2 = new JLabel("NOMBRE(S):");
	        lblNombres2.setFont(f2);
	        lblNombres2.setBounds(100, 30, 400, 25);
	        panel6.add(lblNombres2);

	        txtNombresBajas = new JTextField(10);
	        txtNombresBajas.setFont(f2);
	        txtNombresBajas.setBounds(280, 30, 176, 23);
	        panel6.add(txtNombresBajas);

	        JLabel lblApePaterno2 = new JLabel("APELLIDO PATERNO:");
	        lblApePaterno2.setFont(f2);
	        lblApePaterno2.setBounds(100, 70, 300, 25);
	        panel6.add(lblApePaterno2);

	        txtPrimerApBajas = new JTextField(10);
	        txtPrimerApBajas.setFont(f2);
	        txtPrimerApBajas.setBounds(280, 70, 176, 23);
	        panel6.add(txtPrimerApBajas);

	        JLabel lblApeMaterno2 = new JLabel("APELLIDO MATERNO:");
	        lblApeMaterno2.setFont(f2);
	        lblApeMaterno2.setBounds(100, 110, 300, 25);
	        panel6.add(lblApeMaterno2);

	        txtSegundoApBajas = new JTextField(10);
	        txtSegundoApBajas.setFont(f2);
	        txtSegundoApBajas.setBounds(280, 110, 176, 23);
	        panel6.add(txtSegundoApBajas);
	        
	        JLabel lblEdad2 = new JLabel("EDAD:");
	        lblEdad2.setFont(f2);
	        lblEdad2.setBounds(100, 150, 300, 25);
	        panel6.add(lblEdad2);

			spinnerEdadBajas = new JSpinner();
			spinnerEdadBajas.setBounds(280, 150, 176, 23);
			panel6.add(spinnerEdadBajas);
	        
	        JLabel lblSemestre2 = new JLabel("SEMESTRE:");
	        lblSemestre2.setFont(f2);
	        lblSemestre2.setBounds(100, 190, 300, 25);
	        panel6.add(lblSemestre2);

	        spinnerSemestreBajas = new JSpinner();
	        spinnerSemestreBajas.setAutoscrolls(true);
	        spinnerSemestreBajas.setBounds(280, 190, 176, 23);
	        panel6.add(spinnerSemestreBajas);

	        JLabel lblCarrera2 = new JLabel("CARRERA:");
	        lblCarrera2.setFont(f2);
	        lblCarrera2.setBounds(100, 230, 300, 25);
	        panel6.add(lblCarrera2);

	        cboCarreraBajas = new JComboBox<String>();
	        cboCarreraBajas.addItem("Elige Carrera...");
	        cboCarreraBajas.addItem("Ingenieria en Sistemas Computacionales");
	        cboCarreraBajas.addItem("Ingenieria en Industrias Alimentarias");
	        cboCarreraBajas.addItem("Ingenieria en Mecatronica");
	        cboCarreraBajas.addItem("Licenciatura en Administracion");
	        cboCarreraBajas.addItem("Licenciatura en Contador Publico");
	        cboCarreraBajas.setFont(f1);
	        cboCarreraBajas.setBounds(280, 230, 175, 23);
	        panel6.add(cboCarreraBajas);

	        btnEliminar = new JButton("ELIMINAR");
	        btnEliminar.setFont(f2);
	        btnEliminar.setBounds(480, 45, 135, 25);
	        btnEliminar.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	String nc = txtIdJuegoBajas.getText();
	            	
	            	aDAO = new JuegoDAO();
	        		
	        		System.out.println(aDAO.eliminarRegistro(nc)?"EXITO":"Me cambio de carrera");
	        		
	        		actualizarTabla();
	            }
	        });
	        panel6.add(btnEliminar);

	        btnRestablecerBajas2 = new JButton("RESTABLECER");
	        btnRestablecerBajas2.setFont(f2);
	        btnRestablecerBajas2.setBounds(480, 170, 135, 25);
	        btnRestablecerBajas2.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	metodoRestablecer(txtNombresBajas, txtIdJuegoBajas, txtPrimerApBajas, txtSegundoApBajas, spinnerEdadBajas, cboCarreraBajas, spinnerSemestreBajas);
	            }
	        });
	        panel6.add(btnRestablecerBajas2);

	        frameBajas.add(panel6);

	        JPanel panel7 = new JPanel();
	        panel7.setLayout(null);
	        panel7.setPreferredSize(new Dimension(700, 190));
	        panel7.setBounds(0, 410, 700, 152);

	        tablaJuegosBajas = new JTable();
	        tablaJuegosBajas.setModel(new javax.swing.table.DefaultTableModel(
	                new Object[][] {}, new String [] {"idJuego", "Titulo", "Genero", "Estudio",
	                "Plataforma", "Cantidad", "Precio"}
	        ));

	        scrollBajas = new JScrollPane(tablaJuegosBajas);
	        scrollBajas.setBounds(5, 9, 678, 135);
	        panel7.add(scrollBajas);

	        frameBajas.add(panel7);

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

	        JPanel panel8 = new JPanel();
	        panel8.setBackground(new Color(115, 170, 249));
	        panel8.setLayout(null);
	        panel8.setPreferredSize(new Dimension(700, 190));
	        panel8.setBounds(0, 0, 700, 80);

	        JLabel lbl3 = new JLabel("ACTUALIZAR INFORMACION DE JUEGOS");
	        lbl3.setFont(new Font("Arial", Font.BOLD, 24));
	        lbl3.setForeground(new Color(255, 255, 255));
	        lbl3.setBounds(30, 30, 500, 20);
	        panel8.add(lbl3);

	        frameModificaciones.add(panel8);

	        JPanel panel9 = new JPanel();
	        panel9.setLayout(null);
	        panel9.setPreferredSize(new Dimension(700, 190));
	        panel9.setBounds(0, 80, 700, 80);

	        JLabel lblIdJuego2 = new JLabel("ID Juego:");
	        lblIdJuego2.setFont(f2);
	        lblIdJuego2.setBounds(80, 30, 400, 25);
	        panel9.add(lblIdJuego2);

	        txtIdJuegoModificaciones = new JTextField(10);
	        txtIdJuegoModificaciones.setFont(f2);
	        txtIdJuegoModificaciones.setBounds(250, 30, 150, 23);
	        panel9.add(txtIdJuegoModificaciones);

	        btnBuscarModificaciones = new JButton(new ImageIcon("Iconos/Buscar.png"));
	        btnBuscarModificaciones.setBounds(420, 20, 50, 45);
	        panel9.add(btnBuscarModificaciones);

	        JLabel lblLinea2 = new JLabel("_____________________________________________________________________________");
	        lblLinea2.setFont(f2);
	        lblLinea2.setBounds(0, 60, 630, 20);
	        panel9.add(lblLinea2);

	        frameModificaciones.add(panel9);

	        JPanel panel10 = new JPanel();
	        panel10.setLayout(null);
	        panel10.setPreferredSize(new Dimension(700, 190));
	        panel10.setBounds(0, 160, 700, 250);

	        JLabel lblNombres3 = new JLabel("NOMBRE(S):");
	        lblNombres3.setFont(f2);
	        lblNombres3.setBounds(100, 30, 400, 25);
	        panel10.add(lblNombres3);

	        txtNombresModificaciones = new JTextField(10);
	        txtNombresModificaciones.setFont(f2);
	        txtNombresModificaciones.setBounds(280, 30, 176, 23);
	        panel10.add(txtNombresModificaciones);

	        JLabel lblApePaterno3 = new JLabel("APELLIDO PATERNO:");
	        lblApePaterno3.setFont(f2);
	        lblApePaterno3.setBounds(100, 70, 300, 25);
	        panel10.add(lblApePaterno3);

	        txtPrimerApModificaciones = new JTextField(10);
	        txtPrimerApModificaciones.setFont(f2);
	        txtPrimerApModificaciones.setBounds(280, 70, 176, 23);
	        panel10.add(txtPrimerApModificaciones);

	        JLabel lblApeMaterno3 = new JLabel("APELLIDO MATERNO:");
	        lblApeMaterno3.setFont(f2);
	        lblApeMaterno3.setBounds(100, 110, 300, 25);
	        panel10.add(lblApeMaterno3);

	        txtSegundoApModificaciones = new JTextField(10);
	        txtSegundoApModificaciones.setFont(f2);
	        txtSegundoApModificaciones.setBounds(280, 110, 176, 23);
	        panel10.add(txtSegundoApModificaciones);
	        
	        JLabel lblEdad3 = new JLabel("EDAD:");
	        lblEdad3.setFont(f2);
	        lblEdad3.setBounds(100, 150, 300, 25);
	        panel10.add(lblEdad3);

			spinnerEdadModificaciones = new JSpinner();
			spinnerEdadModificaciones.setBounds(280, 150, 176, 23);
			panel10.add(spinnerEdadModificaciones);

	        JLabel lblSemestre3 = new JLabel("SEMESTRE:");
	        lblSemestre3.setFont(f2);
	        lblSemestre3.setBounds(100, 190, 300, 25);
	        panel10.add(lblSemestre3);

	        spinnerSemestreModificaciones = new JSpinner();
	        spinnerSemestreModificaciones.setAutoscrolls(true);
	        spinnerSemestreModificaciones.setBounds(280, 190, 176, 23);
	        panel10.add(spinnerSemestreModificaciones);

	        JLabel lblCarrera3 = new JLabel("CARRERA:");
	        lblCarrera3.setFont(f2);
	        lblCarrera3.setBounds(100, 230, 300, 25);
	        panel10.add(lblCarrera3);

	        cboCarreraModificaciones = new JComboBox<String>();
	        cboCarreraModificaciones.addItem("Elige Carrera...");
	        cboCarreraModificaciones.addItem("Ingenieria en Sistemas Computacionales");
	        cboCarreraModificaciones.addItem("Ingenieria en Industrias Alimentarias");
	        cboCarreraModificaciones.addItem("Ingenieria en Mecatrónica");
	        cboCarreraModificaciones.addItem("Lincenciatura en Administracion");
	        cboCarreraModificaciones.addItem("Licenciatura en Contador Publico");
	        cboCarreraModificaciones.setFont(f1);
	        cboCarreraModificaciones.setBounds(280, 230, 175, 23);
	        panel10.add(cboCarreraModificaciones);

	        btnModificar = new JButton("MODIFICAR");
	        btnModificar.setFont(f2);
	        btnModificar.setBounds(480, 45, 135, 25);
	        
	        panel10.add(btnModificar);

	        btnRestablecerModificaciones2 = new JButton("RESTABLECER");
	        btnRestablecerModificaciones2.setFont(f2);
	        btnRestablecerModificaciones2.setBounds(480, 170, 135, 25);
	        btnRestablecerModificaciones2.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	metodoRestablecer(txtNombresModificaciones, txtIdJuegoModificaciones, txtPrimerApModificaciones, txtSegundoApModificaciones, spinnerEdadModificaciones, cboCarreraModificaciones, spinnerSemestreModificaciones);
	            }
	        });
	        panel10.add(btnRestablecerModificaciones2);

	        frameModificaciones.add(panel10);

	        JPanel panel11 = new JPanel();
	        panel11.setLayout(null);
	        panel11.setPreferredSize(new Dimension(700, 190));
	        panel11.setBounds(0, 410, 700, 152);

	        tablaJuegosModificaciones = new JTable();
	        tablaJuegosModificaciones.setModel(new javax.swing.table.DefaultTableModel(
	                new Object[][] {}, new String [] {"idJuego", "Titulo", "Genero", "Estudio",
	                "Plataforma", "Cantidad", "Precio"}
	        ));

	        scrollModificaciones = new JScrollPane(tablaJuegosModificaciones);
	        scrollModificaciones.setBounds(5, 9, 678, 135);
	        panel11.add(scrollModificaciones);

	        frameModificaciones.add(panel11);

	        pane.add(frameModificaciones);

	        //--------------------------------- MODIFICACIONES ----------------------------------
	        
	        //----------------------------------- CONSULTAS -------------------------------------
	        frameConsultas = new JInternalFrame();
	        frameConsultas.setDefaultCloseOperation(HIDE_ON_CLOSE);
	        frameConsultas.setLayout(null);
	        frameConsultas.setSize(700, 595);
	        frameConsultas.setTitle("CONSULTAS");
	        frameConsultas.setClosable(true);
	        frameConsultas.setIconifiable(true);
	        frameConsultas.setMaximizable(true);
	        frameConsultas.setResizable(true);
	        frameConsultas.setVisible(false);

	        JPanel panel12 = new JPanel();
	        panel12.setBackground(new Color(0, 0, 255));
	        panel12.setLayout(null);
	        panel12.setPreferredSize(new Dimension(700, 190));
	        panel12.setBounds(0, 0, 700, 80);

	        JLabel lbl4 = new JLabel("CONSULTAS ALUMNOS");
	        lbl4.setFont(new Font("Arial", Font.BOLD, 24));
	        lbl4.setForeground(new Color(255, 255, 255));
	        lbl4.setBounds(30, 30, 400, 20);

	        panel12.add(lbl4);

	        frameConsultas.add(panel12);

	        JPanel panel13 = new JPanel();
	        panel13.setLayout(null);
	        panel13.setPreferredSize(new Dimension(700, 190));
	        panel13.setBounds(0, 80, 700, 330);

	        JLabel lblSeleccion = new JLabel("Selecciona criterio de busqueda:");
	        lblSeleccion.setFont(f2);
	        lblSeleccion.setBounds(10, 5, 300, 25);
	        panel13.add(lblSeleccion);

	        JRadioButton rbtTodos = new JRadioButton("Todos");
	        rbtTodos.setFont(f2);
	        rbtTodos.setBounds(20, 35, 80, 25);
	        panel13.add(rbtTodos);

	        JRadioButton rbtNombre = new JRadioButton("NOMBRE:");
	        rbtNombre.setFont(f2);
	        rbtNombre.setBounds(100, 35, 100, 25);
	        panel13.add(rbtNombre);

	        txtNombresConsultas = new JTextField(10);
	        txtNombresConsultas.setFont(f2);
	        txtNombresConsultas.setBounds(280, 35, 176, 23);
	        panel13.add(txtNombresConsultas);

	        JRadioButton rbtApePaterno = new JRadioButton("APELLIDO PATERNO:");
	        rbtApePaterno.setFont(f2);
	        rbtApePaterno.setBounds(100, 65, 180, 25);
	        panel13.add(rbtApePaterno);

	        txtPrimerApConsultas = new JTextField(10);
	        txtPrimerApConsultas.setFont(f2);
	        txtPrimerApConsultas.setBounds(280, 65, 176, 23);
	        panel13.add(txtPrimerApConsultas);

	        JRadioButton rbtApeMaterno = new JRadioButton("APELLIDO MATERNO:");
	        rbtApeMaterno.setFont(f2);
	        rbtApeMaterno.setBounds(100, 95, 180, 25);
	        panel13.add(rbtApeMaterno);

	        txtSegundoApConsultas = new JTextField(10);
	        txtSegundoApConsultas.setFont(f2);
	        txtSegundoApConsultas.setBounds(280, 95, 176, 23);
	        panel13.add(txtSegundoApConsultas);

	        JRadioButton rbtEdad = new JRadioButton("EDAD:");
	        rbtEdad.setFont(f2);
	        rbtEdad.setBounds(100, 125, 180, 25);
	        panel13.add(rbtEdad);

	        spinnerEdadConsultas = new JSpinner();
	        spinnerEdadConsultas.setAutoscrolls(true);
	        spinnerEdadConsultas.setBounds(280, 125, 176, 23);
	        panel13.add(spinnerEdadConsultas);
	        
	        JRadioButton rbtSemestre = new JRadioButton("SEMESTRE:");
	        rbtSemestre.setFont(f2);
	        rbtSemestre.setBounds(100, 155, 180, 25);
	        panel13.add(rbtSemestre);

	        spinnerSemestreConsultas = new JSpinner();
	        spinnerSemestreConsultas.setAutoscrolls(true);
	        spinnerSemestreConsultas.setBounds(280, 155, 176, 23);
	        panel13.add(spinnerSemestreConsultas);

	        JLabel lblCarrera4 = new JLabel("Carrera:");
	        lblCarrera4.setFont(f2);
	        lblCarrera4.setBounds(80, 185, 180, 25);
	        panel13.add(lblCarrera4);

	        ButtonGroup bg = new ButtonGroup();
			JRadioButton radioISC = new JRadioButton("ISC");
			radioISC.setFont(f2);
			radioISC.setBounds(150, 185, 50, 25);
			
			bg.add(radioISC);
			panel13.add(radioISC);
			JRadioButton radioIM = new JRadioButton("IM");
			radioIM.setFont(f2);
			radioIM.setBounds(200, 185, 50, 25);
			
			bg.add(radioIM);
			panel13.add(radioIM);
			JRadioButton radioIIA = new JRadioButton("IIA");
			radioIIA.setFont(f2);
			radioIIA.setBounds(250, 185, 50, 25);
			
			bg.add(radioIIA);
			panel13.add(radioIIA);
			JRadioButton radioIA = new JRadioButton("LA");
			radioIA.setFont(f2);
			radioIA.setBounds(300, 185, 50, 25);
			
			bg.add(radioIA);
			panel13.add(radioIA);
			JRadioButton radioLC = new JRadioButton("LC");
			radioLC.setFont(f2);
			radioLC.setBounds(350, 185, 50, 25);
			
			bg.add(radioLC);
			panel13.add(radioLC);

	        btnBuscarConsultas = new JButton(new ImageIcon("Iconos/Buscar.png"));
	        btnBuscarConsultas.setBounds(480, 85, 135, 45);
	        btnBuscarConsultas.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	               
	            	
	            }
	        });
	        panel13.add(btnBuscarConsultas);

	        btnRestablecerConsultas = new JButton("RESTABLECER");
	        btnRestablecerConsultas.setFont(f2);
	        btnRestablecerConsultas.setBounds(480, 170, 135, 35);
	        btnRestablecerConsultas.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	metodoRestablecer(txtNombresConsultas, txtNumControlConsultas, txtPrimerApConsultas, txtSegundoApConsultas, spinnerEdadConsultas, cboCarreraConsultas, spinnerSemestreConsultas);
	            }
	        });
	        panel13.add(btnRestablecerConsultas);

	        frameConsultas.add(panel13);

	        JPanel panel14 = new JPanel();
	        panel14.setLayout(null);
	        panel14.setPreferredSize(new Dimension(700, 190));
	        panel14.setBounds(0, 410, 700, 152);

	        tablaJuegosConsultas = new JTable();
	        tablaJuegosConsultas.setModel(new javax.swing.table.DefaultTableModel(
	                new Object[][] {}, new String [] {"idJuego", "Titulo", "Genero", "Estudio",
	                "Plataforma", "Cantidad", "Precio"}
	        ));

	        scrollModificaciones = new JScrollPane(tablaJuegosConsultas);
	        scrollModificaciones.setBounds(5, 9, 678, 135);
	        panel14.add(scrollModificaciones);

	        frameConsultas.add(panel14);

	        pane.add(frameConsultas);

	        //----------------------------------- CONSULTAS -------------------------------------
	       
	        add(pane, BorderLayout.CENTER);
	}
	 
	 public void actualizarTabla(){

			String controlador = "com.mysql.cj.jdbc.Driver";
    		String url = "jdbc:mysql://localhost:3306/TiendaDeVideoJuegos";
    		String consulta = "SELECT * FROM juegos";

			ResultSetTableModel modelo = null;
			try {
				modelo = new ResultSetTableModel(controlador, url, consulta);
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
