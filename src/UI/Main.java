package UI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import Logica_Planificador.PlanificadorRed;
import logica.modelo.ParametrosPrecio;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import java.awt.Color;
import java.awt.Window.Type;

public class Main {

    private JFrame frame;

    private JTextField txtCostoKm;
    private JTextField txtTarifa;
    private JTextField txtAdicional;

    private JButton btnGenerarMapa;
    private final Action action = new SwingAction();

    /**
     * Launch the application.
     */
    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {

            public void run() {

                try {

                    Main window =
                            new Main();

                    window.frame.setVisible(true);

                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public Main() {

        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {

        frame = new JFrame();
        frame.setType(Type.UTILITY);
        frame.getContentPane().setBackground(Color.BLACK);

        frame.setTitle("Bienvenido al Planificador");

        frame.setBounds(100, 100, 767, 550);

        frame.setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        frame.getContentPane().setLayout(null);

        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);

        panel.setBounds(0, 0, 884, 511);

        frame.getContentPane().add(panel);

        panel.setLayout(null);

        JLabel lblTitulo = new JLabel(
                "Bienvenido al Planificador"
        );
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);

        lblTitulo.setFont(
                new Font("Times New Roman", Font.PLAIN, 22)
        );

        lblTitulo.setBounds(152, 11, 450, 40);

        panel.add(lblTitulo);

        //-----------------------------------------
        // COSTO KM
        //-----------------------------------------

        JLabel lblCostoKm = new JLabel(
                "Costo del kilómetro"
        );
        lblCostoKm.setForeground(Color.WHITE);
        lblCostoKm.setHorizontalAlignment(SwingConstants.CENTER);

        lblCostoKm.setFont(
                new Font("Times New Roman", Font.PLAIN, 22)
        );

        lblCostoKm.setBounds(224, 62, 317, 25);

        panel.add(lblCostoKm);

        txtCostoKm = new JTextField();

        txtCostoKm.setBounds(252, 92, 250, 30);

        panel.add(txtCostoKm);

        //-----------------------------------------
        // TARIFA
        //-----------------------------------------

        JLabel lblTarifa = new JLabel(
                "Tarifa interprovincial"
        );
        lblTarifa.setForeground(Color.WHITE);
        lblTarifa.setHorizontalAlignment(SwingConstants.CENTER);

        lblTarifa.setFont(
                new Font("Times New Roman", Font.PLAIN, 22)
        );

        lblTarifa.setBounds(224, 142, 320, 25);

        panel.add(lblTarifa);

        txtTarifa = new JTextField();

        txtTarifa.setBounds(252, 172, 250, 30);

        panel.add(txtTarifa);

        //-----------------------------------------
        // ADICIONAL
        //-----------------------------------------

        JLabel lblAdicional = new JLabel(
                "Adicional por distancias largas"
        );
        lblAdicional.setForeground(Color.WHITE);
        lblAdicional.setHorizontalAlignment(SwingConstants.CENTER);

        lblAdicional.setFont(
                new Font("Times New Roman", Font.PLAIN, 22)
        );

        lblAdicional.setBounds(224, 216, 320, 25);

        panel.add(lblAdicional);

        txtAdicional = new JTextField();

        txtAdicional.setBounds(252, 252, 250, 30);

        panel.add(txtAdicional);

        //-----------------------------------------
        // BOTON
        //-----------------------------------------

        btnGenerarMapa = new JButton(
                "Generar mapa"
        );
        btnGenerarMapa.setAction(action);

        btnGenerarMapa.setEnabled(false);

        btnGenerarMapa.setBounds(252, 308, 250, 30);

        panel.add(btnGenerarMapa);

        //-----------------------------------------
        // VALIDACION
        //-----------------------------------------

        DocumentListener listener =
                new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {

                validarCampos();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {

                validarCampos();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

                validarCampos();
            }
        };

        txtCostoKm.getDocument()
                .addDocumentListener(listener);

        txtTarifa.getDocument()
                .addDocumentListener(listener);

        txtAdicional.getDocument()
                .addDocumentListener(listener);
    }

    //-----------------------------------------
    // VALIDAR CAMPOS
    //-----------------------------------------

    private void validarCampos() {

        boolean completos =

                !txtCostoKm.getText()
                        .trim()
                        .isEmpty()

                &&

                !txtTarifa.getText()
                        .trim()
                        .isEmpty()

                &&

                !txtAdicional.getText()
                        .trim()
                        .isEmpty();

        btnGenerarMapa.setEnabled(completos);
    }
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "Generar Mapa");
			putValue(SHORT_DESCRIPTION, "Desea Generar un Mapa?");
		}
		public void actionPerformed(ActionEvent e) {
			double costoKm =
		            Double.parseDouble(
		                    txtCostoKm.getText()
		            );

		    double tarifa =
		            Double.parseDouble(
		                    txtTarifa.getText()
		            );

		    double adicional =
		            Double.parseDouble(
		                    txtAdicional.getText()
		            );

		    ParametrosPrecio parametros =
		            new ParametrosPrecio(
		                    costoKm,
		                    tarifa,
		                    adicional
		            );

		    PlanificadorRed.configurarParametros(
		            parametros
		    );

		    PantallaPrincipalMAPA mapa =
		            new PantallaPrincipalMAPA();

		    mapa.mostrarVentana();

		    frame.dispose();
		}
		}
	}
