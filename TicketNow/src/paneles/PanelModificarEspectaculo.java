package paneles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;

import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import controlador.Controlador;
import espectaculo.CantidadDeEntradasInvalidaException;
import espectaculo.DescripcionInvalidaException;
import espectaculo.Espectaculo;
import espectaculo.FechaEstrenoInvalidaException;
import espectaculo.PrecioInvalidoException;
import espectaculo.PromocionInvalidaException;
import usuario.NombreInvalidoException;
import usuario.Proveedor;

public class PanelModificarEspectaculo extends JPanel {

	private JPanel panelSuperior;
	private JPanel panelDerecho;
	private JPanel panelIzquierdo;
	private JPanel panelCentral;

	private Controlador controlador;
	private String proveedor;
	private String espectaculo;
	private String lugar;

	public PanelModificarEspectaculo(Controlador controlador, String proveedor, String espectaculo, String lugar) {
		setLayout(new BorderLayout(0, 0));
		this.controlador = controlador;
		this.proveedor = proveedor;
		this.espectaculo = espectaculo;
		this.lugar = lugar;

		panelSuperior = new PanelSuperior();
		panelSuperior.setBackground(Color.WHITE);
		add(panelSuperior, BorderLayout.NORTH);

		panelCentral = new PanelCentral();
		panelCentral.setBackground(Color.WHITE);
		add(panelCentral, BorderLayout.CENTER);

		panelDerecho = new JPanel();
		panelDerecho.setBackground(Color.WHITE);
		FlowLayout flowLayout1 = (FlowLayout) panelDerecho.getLayout();
		flowLayout1.setHgap(50);
		add(panelDerecho, BorderLayout.WEST);

		panelIzquierdo = new JPanel();
		panelIzquierdo.setBackground(Color.WHITE);
		FlowLayout flowLayout2 = (FlowLayout) panelIzquierdo.getLayout();
		flowLayout2.setHgap(50);
		add(panelIzquierdo, BorderLayout.EAST);

	}

	private class PanelSuperior extends JPanel {
		private JButton btnVolver = new JButton("");
		private JLabel titulo = new JLabel("      Modifique sus datos:");

		public PanelSuperior() {
			setLayout(new BorderLayout(0, 0));
			inicializarTitulo();
			inicializarBotones();
		}

		/**
		 * Método que inicializa el titulo y lo agrega al panel
		 */
		private void inicializarTitulo() {
			titulo.setFont(new Font("Dialog", Font.BOLD, 22));
			add(titulo, BorderLayout.CENTER);
		}

		/**
		 * Método que inicializa el boton volver y lo agrega al panel
		 */
		private void inicializarBotones() {
			btnVolver.setBackground(Color.WHITE);
			ImageIcon icono = new ImageIcon(PanelRegistro.class.getResource("/paneles/back.png"));
			btnVolver.setIcon(icono);
			btnVolver.setPreferredSize(new Dimension(icono.getIconWidth(), icono.getIconHeight()));
			btnVolver.setBorderPainted(false);
			add(btnVolver, BorderLayout.WEST);

			btnVolver.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					VistaTicketNow.changePanel("proveedor", PanelModificarEspectaculo.this, controlador, proveedor);
				}
			});
		}
	}

	private class PanelCentral extends JPanel {

		private JPanel panelRegistrarse = new JPanel();

		private JLabel lblNombre = new JLabel("Nombre:");
		private JLabel lblCantidadDisponible = new JLabel("Cantidad de entradas:");
		private JLabel lblFechaDeEstreno = new JLabel("Fecha de estreno:");
		private JLabel lblPromocion = new JLabel("Promoción:");
		private JLabel lblFotos = new JLabel("Foto:");
		private JLabel lblCategoria = new JLabel("Categoria:");
		private JLabel lblLugar = new JLabel("Lugar de Retiro:");
		private JLabel lblPrecio = new JLabel("Precio:");
		private JLabel lblCaracteristicas = new JLabel("Caracteristicas:");

		Espectaculo e = controlador.obtenerEspectaculo(espectaculo, lugar);

		private JTextField nombreField = new JTextField(e.getNombre());
		private JSpinner cantidadDisponibleField = new JSpinner();
		private JTextField fechaDeEstrenoField = new JTextField(e.getFechaEstreno());
		private JComboBox promocionBox = new JComboBox();
		private JButton fotosBtn = new JButton("Cargar fotos");
		private JComboBox categoriaBox = new JComboBox();
		private JComboBox lugarBox = new JComboBox();
		private JTextField precioField = new JTextField(e.getPrecio());
		private JTextArea caracteristicasPane = new JTextArea(e.getDescripcion());

		private JPanel panelInferior;

		private Font fuente = new Font("Dialog", Font.BOLD, 14);
		private Font fuente1 = new Font("Dialog", Font.PLAIN, 14);

		public PanelCentral() {
			setLayout(new BorderLayout(0, 0));

			panelRegistrarse.setLayout(new GridLayout(9, 2, 15, 30));
			panelRegistrarse.setBackground(Color.WHITE);
			add(panelRegistrarse, BorderLayout.CENTER);

			inicializarCampos();

			panelInferior = new PanelInferior();
			panelInferior.setBackground(Color.WHITE);
			add(panelInferior, BorderLayout.SOUTH);
		}

		private void inicializarCampos() {

			lblNombre.setHorizontalAlignment(SwingConstants.CENTER);
			panelRegistrarse.add(lblNombre);
			lblNombre.setFont(fuente);

			nombreField.setEditable(false);
			panelRegistrarse.add(nombreField);
			nombreField.setFont(fuente1);

			lblCantidadDisponible.setHorizontalAlignment(SwingConstants.CENTER);
			panelRegistrarse.add(lblCantidadDisponible);
			lblCantidadDisponible.setFont(fuente);

			cantidadDisponibleField.setValue(Integer.parseInt(e.getCantidadEntradas()));
			panelRegistrarse.add(cantidadDisponibleField);
			cantidadDisponibleField.setFont(fuente1);

			lblFechaDeEstreno.setHorizontalAlignment(SwingConstants.CENTER);
			panelRegistrarse.add(lblFechaDeEstreno);
			lblFechaDeEstreno.setFont(fuente);

			panelRegistrarse.add(fechaDeEstrenoField);
			fechaDeEstrenoField.setFont(fuente1);

			lblPromocion.setHorizontalAlignment(SwingConstants.CENTER);
			panelRegistrarse.add(lblPromocion);
			lblPromocion.setFont(fuente);

			promocionBox.setModel(
					new DefaultComboBoxModel(new String[] { "Sin promocion", "2x1", "Banco Asociados", "Descuento a Jubilados" }));
			panelRegistrarse.add(promocionBox);
			promocionBox.setSelectedItem(e.getPromocion());
			promocionBox.setFont(fuente1);

			lblFotos.setHorizontalAlignment(SwingConstants.CENTER);
			panelRegistrarse.add(lblFotos);
			lblFotos.setFont(fuente);

			panelRegistrarse.add(fotosBtn);
			fotosBtn.setFont(fuente);
			fotosBtn.setForeground(new Color(255, 255, 255));
			fotosBtn.setBackground(new Color(0, 102, 204));

			fotosBtn.addActionListener(new ActionListener() {

				File fichero;

				public void actionPerformed(ActionEvent e) {
					int resultado;

					CargarFoto ventana = new CargarFoto();

					FileNameExtensionFilter filtro = new FileNameExtensionFilter("JPG y PNG", "jpg", "png");

					ventana.jfchCargarfoto.setFileFilter(filtro);

					resultado = ventana.jfchCargarfoto.showOpenDialog(null);

					if (JFileChooser.APPROVE_OPTION == resultado) {
						fichero = ventana.jfchCargarfoto.getSelectedFile();

						try {
							ImageIcon icon = new ImageIcon(fichero.toString());
							Icon icono = new ImageIcon(icon.getImage());
							JLabel fotoCargada = new JLabel(); /* Aca guardo la foto que va a ir a la base de datos */
							fotoCargada.setText(null);
							fotoCargada.setIcon(icono);
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(null, "Error abriendo la imagen " + ex);
						}

					}
				}
			});

			lblCategoria.setHorizontalAlignment(SwingConstants.CENTER);
			panelRegistrarse.add(lblCategoria);
			lblCategoria.setFont(fuente);

			categoriaBox.setEditable(false);
			categoriaBox.setEnabled(false);

			categoriaBox.setModel(
					new DefaultComboBoxModel(new String[] { "Seleccione categoria", "Cine", "Teatro", "Cancha" }));
			panelRegistrarse.add(categoriaBox);
			categoriaBox.setSelectedItem(e.getCategoria());
			categoriaBox.setFont(fuente1);

			lblLugar.setHorizontalAlignment(SwingConstants.CENTER);
			panelRegistrarse.add(lblLugar);
			lblLugar.setFont(fuente);

			DefaultComboBoxModel cine = new DefaultComboBoxModel(new String[] { "Hoyts Abasto", "Hoyts Dot",
					"Hoyts Moreno", "Hoyts Moron", "Hoyts Quilmes", "Hoyts Rosario", "Hoyts Temperley",
					"Hoyts Unicenter", "Hoyts NuevoCentro", "Hoyts Patio Olmos", "Hoyts Salta" });

			DefaultComboBoxModel teatro = new DefaultComboBoxModel(new String[] { "Teatro Colon", "Teatro Gran Rex",
					"Teatro Metropolitan", "Teatro Argentino de La Plata", "Teatro Maipo", "Teatro Lola Membrives",
					"Teatro Opera", "Teatro Coliseo" });

			DefaultComboBoxModel cancha = new DefaultComboBoxModel(new String[] { "Antonio Vespusio Liberti",
					"Libertadores de América", "Ciudad de La Plata", "Presidente Peron", "Mario Alberto Kempes",
					"Jose Amalfitani", "Alberto J. Armando", "Tomas Adolfo Ducó", "Pedro Bidegain" });

			switch (e.getCategoria()) {
			case "Cine":
				lugarBox.setModel(cine);
				break;
			case "Teatro":
				lugarBox.setModel(teatro);
				break;
			case "Cancha":
				lugarBox.setModel(cancha);
				break;
			}

			lugarBox.setSelectedItem(e.getLugarDeRetiro());
			lugarBox.setEditable(false);
			lugarBox.setEnabled(false);
			panelRegistrarse.add(lugarBox);
			lugarBox.setFont(fuente1);

			lblPrecio.setHorizontalAlignment(SwingConstants.CENTER);
			panelRegistrarse.add(lblPrecio);
			lblPrecio.setFont(fuente);

			panelRegistrarse.add(precioField);
			precioField.setFont(fuente1);

			lblCaracteristicas.setHorizontalAlignment(SwingConstants.CENTER);
			panelRegistrarse.add(lblCaracteristicas);
			lblCaracteristicas.setFont(fuente);

			JScrollPane scroll = new JScrollPane(caracteristicasPane, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
					JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			caracteristicasPane.setWrapStyleWord(true);
			caracteristicasPane.setLineWrap(true);
			caracteristicasPane.setFont(fuente1);
			panelRegistrarse.add(scroll);
		}

		private class PanelInferior extends JPanel {

			JButton btnModificar = new JButton("Modificar espectáculo");

			public PanelInferior() {
				inicializarBotones();
			}

			private void inicializarBotones() {
				btnModificar.setFont(new Font("Dialog", Font.BOLD, 15));
				btnModificar.setVerticalAlignment(SwingConstants.TOP);
				btnModificar.setForeground(new Color(255, 255, 255));
				btnModificar.setBackground(new Color(0, 102, 204));
				add(btnModificar);

				btnModificar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						try {
							if (controlador.modificarEspectaculo(nombreField.getText(),
									cantidadDisponibleField.getValue().toString(), fechaDeEstrenoField.getText(),
									promocionBox.getSelectedItem().toString(),
									categoriaBox.getSelectedItem().toString(), lugarBox.getSelectedItem().toString(),
									precioField.getText(), caracteristicasPane.getText()))
								JOptionPane.showMessageDialog(null, "Su espectaculo ha sido modificado.");
							VistaTicketNow.changePanel("proveedor", PanelModificarEspectaculo.this, controlador,
									proveedor);
						} catch (CantidadDeEntradasInvalidaException m1) {
							JOptionPane.showMessageDialog(null, m1.getMessage(), "Ocurrió algo inesperado",
									JOptionPane.ERROR_MESSAGE);

						} catch (FechaEstrenoInvalidaException m2) {
							JOptionPane.showMessageDialog(null, m2.getMessage(), "Ocurrió algo inesperado",
									JOptionPane.ERROR_MESSAGE);

						} catch (PromocionInvalidaException m3) {
							JOptionPane.showMessageDialog(null, m3.getMessage(), "Ocurrió algo inesperado",
									JOptionPane.ERROR_MESSAGE);

						} catch (PrecioInvalidoException m5) {
							JOptionPane.showMessageDialog(null, m5.getMessage(), "Ocurrió algo inesperado",
									JOptionPane.ERROR_MESSAGE);

						} catch (DescripcionInvalidaException m6) {
							JOptionPane.showMessageDialog(null, m6.getMessage(), "Ocurrió algo inesperado",
									JOptionPane.ERROR_MESSAGE);

						}
					}
				});
			}

		}
	}

}
