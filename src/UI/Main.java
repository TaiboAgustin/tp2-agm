package UI;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Main extends JFrame {

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Main window = new Main();
                    window.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Main() {
        initialize();
    }

    private void initialize() {
        setTitle("Planificación de Rutas");
        setBounds(100, 100, 600, 520);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);

        getRootPane().setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JLabel lblTitulo = new JLabel("Bienvenido", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 22));
        lblTitulo.setBounds(150, 50, 300, 60);
        getContentPane().add(lblTitulo);

        JLabel lblSubtitulo = new JLabel("Planificación de rutas", SwingConstants.CENTER);
        lblSubtitulo.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblSubtitulo.setBounds(150, 150, 300, 50);
        getContentPane().add(lblSubtitulo);

        JButton btnIniciar = new JButton("Iniciar planificación");
        btnIniciar.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnIniciar.setBounds(150, 280, 300, 60);
        btnIniciar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SolicitudDePlanificacion solicitud = new SolicitudDePlanificacion(Main.this);
                solicitud.setVisible(true);
                setVisible(false);
            }
        });
        getContentPane().add(btnIniciar);
    }
}