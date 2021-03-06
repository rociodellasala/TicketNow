package paneles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.EventHandler;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.AbstractListModel;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import controlador.Controlador;
import espectaculo.Espectaculo;

public class PanelResultadosEncontrados extends JPanel {

	private JPanel panelSuperior;
	private JPanel panelCentral;

	JList listEspectaculos = new JList();
	
	private Controlador controlador;
	private String cliente;
	private Set<Espectaculo> espectaculos;
	private String[] condiciones;
	
	public PanelResultadosEncontrados(Controlador controlador, String cliente, Set<Espectaculo> set, String[] str) {
		this.setLayout(new BorderLayout(0, 0));
		this.controlador = controlador;
		this.cliente = cliente;
		this.espectaculos = set;
		this.condiciones = str;
		
		panelSuperior = new PanelSuperior();
		panelSuperior.setBackground(Color.WHITE);
		add(panelSuperior, BorderLayout.NORTH);

		panelCentral = new PanelCentral();
		panelCentral.setBackground(Color.WHITE);
		add(panelCentral, BorderLayout.CENTER);
	}

	private class PanelSuperior extends JPanel {

		JButton btnvolver = new JButton();

		ImageIcon icono = new ImageIcon(PanelRegistro.class.getResource("/paneles/back.png"));

		public PanelSuperior() {
			setLayout(new BorderLayout(0, 0));
			inicializarBotones();
		}

		private void inicializarBotones() {
			btnvolver.setIcon(icono);
			btnvolver.setPreferredSize(new Dimension(icono.getIconWidth(), icono.getIconHeight()));
			btnvolver.setBorderPainted(false);
			add(btnvolver, BorderLayout.WEST);
			btnvolver.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					VistaTicketNow.changePanel("cliente", PanelResultadosEncontrados.this, controlador, cliente);
				}
			});

		}
	}

	private class PanelCentral extends JPanel {
		JPanel panelCentralSuperior = new JPanel();
		JPanel panelCentralCentral = new JPanel();
		JPanel panelCentralIzquierdo = new JPanel();

		JLabel lblResultadosEncontrados = new JLabel("                 RESULTADOS ENCONTRADOS");
		JButton btnOrdenar = new JButton("Ordenar");
		JList listOrdenar = new JList();
		
		DefaultListModel model;

		public PanelCentral() {
			setLayout(new BorderLayout(0, 0));

			panelCentralSuperior.setBackground(new Color(153, 204, 255));
			add(panelCentralSuperior, BorderLayout.NORTH);

			inicializarPanelCentralSuperior();

			panelCentralCentral.setBackground(new Color(255, 255, 255));
			add(panelCentralCentral, BorderLayout.CENTER);
			panelCentralCentral.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

			inicializarPanelCentralCentral();

			panelCentralIzquierdo.setBackground(Color.LIGHT_GRAY);
			add(panelCentralIzquierdo, BorderLayout.WEST);
			panelCentralIzquierdo.setLayout(new BoxLayout(panelCentralIzquierdo, BoxLayout.Y_AXIS));

			inicializarPanelCentralIzquierdo();
		}

		private void inicializarPanelCentralSuperior() {
			lblResultadosEncontrados.setFont(new Font("Dialog", Font.BOLD, 18));
			lblResultadosEncontrados.setHorizontalAlignment(SwingConstants.LEFT);
			lblResultadosEncontrados.setForeground(Color.WHITE);
			lblResultadosEncontrados.setBackground(Color.WHITE);
			panelCentralSuperior.add(lblResultadosEncontrados);
		}

		private void inicializarPanelCentralCentral() {
			if(espectaculos.size() > 0) {
			String[] esp = new String[espectaculos.size()];
			
			int i = 0;
			for(Espectaculo e : espectaculos) {
				if(e.getPuntaje() !=  null)
					esp[i] = "Nombre: " + e .getNombre() + " - Lugar: " + e.getLugarDeRetiro() + " - Precio: " + e.getPrecio() + "$" + " - Puntaje: " + e.getPuntaje();
				else
					esp[i] = "Nombre: " + e .getNombre() + " - Lugar: " + e.getLugarDeRetiro() + " - Precio: " + e.getPrecio() + "$";
				i++;
			}
			this.model = new DefaultListModel() {
				String[] values = esp;

				public int getSize() {
					return values.length;
				}

				public Object getElementAt(int index) {
					return values[index];
				}
			};
			
			listEspectaculos.setFont(new Font("Dialog", Font.BOLD, 13));
			listEspectaculos.setModel(model);
			
			listEspectaculos.addMouseListener(new MouseAdapter() {
			    public void mouseClicked(MouseEvent evt) {
			        JList list = (JList)evt.getSource();
			        if (evt.getClickCount() == 2) {
			            String str = (String) listEspectaculos.getSelectedValue();
			            String[] lista = str.split("-");
			           
			            
			            String espec = str.substring(8, str.indexOf("-")-1);
			            String lugar = lista[1].substring(8, lista[1].length()-1);
			     
			            VistaTicketNow.changePanel("informacion", PanelResultadosEncontrados.this, controlador, cliente, espectaculos, espec, lugar, condiciones);
			        }
			    }
			});
		
			panelCentralCentral.add(listEspectaculos);
			} else {
				JLabel subtitulo = new JLabel("No tienes ningun espectáculo agregado al sistema");
				panelCentralCentral.add(subtitulo);
				subtitulo.setFont(new Font("Dialog", Font.PLAIN, 15));
			}
		}

		private void inicializarPanelCentralIzquierdo() {

			listOrdenar.setFont(new Font("Dialog", Font.BOLD, 13));
			listOrdenar.setBackground(new Color(204, 204, 204));
			listOrdenar.setModel(new AbstractListModel() {
				String[] values = new String[] { "Lugar", "Precio", "Puntuaciones" };

				public int getSize() {
					return values.length;
				}

				public Object getElementAt(int index) {
					return values[index];
				}
			});
			
			
			btnOrdenar.setForeground(Color.WHITE);
			btnOrdenar.setBackground(new Color(0, 102, 204));
			btnOrdenar.setAlignmentX(Component.CENTER_ALIGNMENT);
			panelCentralIzquierdo.add(listOrdenar);
			panelCentralIzquierdo.add(btnOrdenar);
			btnOrdenar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (listOrdenar.isSelectionEmpty())
						JOptionPane.showMessageDialog(null, "Seleccione una opción para ordenar los resultados",
								"Ocurrió algo inesperado", JOptionPane.ERROR_MESSAGE);
					else {
						String orden = (String) listOrdenar.getSelectedValue();
						Set<Espectaculo> ordenado;
						if(orden.equals("Lugar")) {
							Comparator cmp = new Comparator<Espectaculo>() {
								public int compare(Espectaculo o1, Espectaculo o2) {
									String lugar1 = o1.getLugarDeRetiro();
									String lugar2 = o2.getLugarDeRetiro();
									if(lugar1.equals(lugar2))
										return 1;
									else
									return o1.getLugarDeRetiro().compareTo(o2.getLugarDeRetiro());
								}
							};
							ordenado = new TreeSet<Espectaculo>(cmp);
							ordenado.addAll(espectaculos);
					
						} else if(orden.equals("Precio")) {
							Comparator cmp = new Comparator<Espectaculo>() {
								public int compare(Espectaculo o1, Espectaculo o2) {
									Integer precio1 = Integer.parseInt(o1.getPrecio());
									Integer precio2 = Integer.parseInt(o2.getPrecio());
									if(precio1.equals(precio2))
										return 1;
									else
										return precio1-precio2;
								}
							};
							ordenado = new TreeSet<Espectaculo>(cmp);
							ordenado.addAll(espectaculos);
						
						} else if(orden.equals("Puntuaciones")) {
							Comparator cmp = new Comparator<Espectaculo>() {
								public int compare(Espectaculo o1, Espectaculo o2) {
									Integer puntaje1;
									Integer puntaje2;
									if(o1.getPuntaje() == null)
										puntaje1 = 0;
									else 
										puntaje1 = Integer.parseInt(o1.getPuntaje());
									if(o2.getPuntaje() == null)
										puntaje2 = 0;
									else
										puntaje2 = Integer.parseInt(o2.getPuntaje());
									
									
									
									if(puntaje1.equals(puntaje2))
										return 1;
									else
										return puntaje2-puntaje1;
								}
							};
							ordenado = new TreeSet<Espectaculo>(cmp);
							ordenado.addAll(espectaculos);
						} else {
							ordenado = new TreeSet<Espectaculo>();
						}

						model.removeAllElements();
						
						String[] esp = new String[ordenado.size()];
						
						int i = 0;
						for(Espectaculo e1 : ordenado) {
							if(e1.getPuntaje() !=  null)
								esp[i] = "Nombre: " + e1.getNombre() + " - Lugar: " + e1.getLugarDeRetiro() + " - Precio: " + e1.getPrecio() + "$" + " - Puntaje: " + e1.getPuntaje();
							else
								esp[i] = "Nombre: " + e1.getNombre() + " - Lugar: " + e1.getLugarDeRetiro() + " - Precio: " + e1.getPrecio() + "$";
							i++;
						}
						listEspectaculos.setFont(new Font("Dialog", Font.BOLD, 13));
						listEspectaculos.setModel(new DefaultListModel() {
							String[] values = esp;

							public int getSize() {
								return values.length;
							}

							public Object getElementAt(int index) {
								return values[index];
							}
						});
						panelCentralCentral.add(listEspectaculos);
						
					}
					
					
				}
			});
			
			
		}
	}

}
