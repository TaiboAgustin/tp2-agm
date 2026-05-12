package UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import logica.modelo.Localidad;
import logica.modelo.ParametrosPrecio;

public class SolicitudDePlanificacion extends JFrame {

    private ParametrosPrecio parametros;
    private JTextField numCostoKm;
    private JTextField numTarifaInterprovincial;
    private JTextField numCostoDistanciasLargas;

    private List<Localidad> listaLocalidades = new ArrayList<>();
    private DefaultListModel<String> listModel = new DefaultListModel<>();

    public SolicitudDePlanificacion(Main parent) {
        setTitle("Generar Solicitud");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 520);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);

        buildForm();
        buildLocalidadesList();
        buildButtons();
        
        setVisible(true);

        getRootPane().setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
    }

    private void buildForm() {
        JLabel lblCostoKm = new JLabel("Costo del kilómetro:");
        lblCostoKm.setBounds(16, 16, 200, 30);
        getContentPane().add(lblCostoKm);

        JLabel lblTarifa = new JLabel("Tarifa interprovincial:");
        lblTarifa.setBounds(16, 56, 200, 30);
        getContentPane().add(lblTarifa);

        JLabel lblDistancias = new JLabel("Adicional por distancias largas:");
        lblDistancias.setBounds(16, 96, 200, 30);
        getContentPane().add(lblDistancias);

        numCostoKm = new JTextField();
        numCostoKm.setBounds(230, 16, 180, 30);
        getContentPane().add(numCostoKm);

        numTarifaInterprovincial = new JTextField();
        numTarifaInterprovincial.setBounds(230, 56, 180, 30);
        getContentPane().add(numTarifaInterprovincial);

        numCostoDistanciasLargas = new JTextField();
        numCostoDistanciasLargas.setBounds(230, 96, 180, 30);
        getContentPane().add(numCostoDistanciasLargas);
    }

    private void buildLocalidadesList() {
        JLabel lblLocalidades = new JLabel("Localidades agregadas:");
        lblLocalidades.setBounds(16, 140, 200, 25);
        getContentPane().add(lblLocalidades);

        JList<String> jListLocalidades = new JList<>(listModel);
        JScrollPane scroll = new JScrollPane(jListLocalidades);
        scroll.setBounds(16, 170, 550, 200);
        getContentPane().add(scroll);
    }

    private void buildButtons() {
        JButton btnAgregarLocalidad = new JButton("Agregar nueva localidad");
        btnAgregarLocalidad.setBounds(16, 390, 200, 32);
        btnAgregarLocalidad.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AgregarLocalidad dialogo = new AgregarLocalidad(SolicitudDePlanificacion.this);
                dialogo.setVisible(true);

                Localidad nueva = dialogo.getLocalidadCreada();
                if (nueva != null) {
                    listaLocalidades.add(nueva);
                    listModel.addElement(nueva.getNombre() + " — " + nueva.getProvincia());
                }
            }
        });

        JButton btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setBounds(228, 390, 100, 32);
        btnLimpiar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }
        });

        JButton btnNuevaPlanificacion = new JButton("Generar planificacion");
        btnNuevaPlanificacion.setBounds(340, 390, 190, 32);
        btnNuevaPlanificacion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                crearParametrosPrecio();
                crearPlanificacion();
            }
        });

        getContentPane().add(btnAgregarLocalidad);
        getContentPane().add(btnLimpiar);
        getContentPane().add(btnNuevaPlanificacion);
    }

    private void limpiarCampos() {
        listaLocalidades.clear();
        listModel.clear();
        numCostoKm.setText("");
        numTarifaInterprovincial.setText("");
        numCostoDistanciasLargas.setText("");
    }

    void crearParametrosPrecio() {
        try {
            double costoKm               = convertToDouble(numCostoKm);
            double tarifaInterprovincial = convertToDouble(numTarifaInterprovincial);
            double costoDistanciasLargas = convertToDouble(numCostoDistanciasLargas);

            this.parametros = new ParametrosPrecio(costoKm, tarifaInterprovincial, costoDistanciasLargas);

        } catch (NumberFormatException ex) {
        	mostrarMensaje("Los costos deben ser números válidos.", "Error");
        }
    }
    
	private void crearPlanificacion() {
		//Llamamos a iniciar una planificación. Debe verificarse que la lista de localidades no esté vacía y devolver un error en caso de que lo sea
		try {
			
		} catch (Exception e) {
			mostrarMensaje("Debe ingresarse al menos una localidad.", "Error");
		}
		
	}
	
    protected void mostrarMensaje(String mensaje, String tipo) {
        JOptionPane.showMessageDialog(this, mensaje, tipo, JOptionPane.ERROR_MESSAGE);
    }


    double convertToDouble(JTextField text) {
    	return Double.valueOf(text.getText().trim());
    }
}