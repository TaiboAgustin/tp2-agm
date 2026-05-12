package UI;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Main {

    private static final Color BG    = new Color(18, 18, 19);
    private static final Color GREEN = new Color(83, 141, 78);
    private static final Color GRAY  = new Color(129, 131, 132);

    private JFrame frame;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Main window = new Main();
                    window.frame.setVisible(true);
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
        frame = new JFrame();
        frame.setTitle("Conectando Localidades");
        frame.setBounds(100, 100, 720, 560);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        JPanel panel = new JPanel();
        panel.setBackground(BG);
        panel.setLayout(null);
        frame.setContentPane(panel);

        JLabel lblTitulo = new JLabel("CONECTANDO LOCALIDADES", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitulo.setForeground(GREEN);
        lblTitulo.setBounds(110, 110, 500, 40);
        panel.add(lblTitulo);

        JLabel lblSubtitulo = new JLabel("Planificación de Rutas", SwingConstants.CENTER);
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubtitulo.setForeground(Color.WHITE);
        lblSubtitulo.setBounds(110, 158, 500, 24);
        panel.add(lblSubtitulo);

        JLabel lblCurso = new JLabel("TP2 Programación III · Com 01", SwingConstants.CENTER);
        lblCurso.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblCurso.setForeground(GRAY);
        lblCurso.setBounds(110, 186, 500, 20);
        panel.add(lblCurso);

        JLabel lblNombres = new JLabel("Rocha · Rodriguez · Sangueso · Taibo Cruz", SwingConstants.CENTER);
        lblNombres.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblNombres.setForeground(GRAY);
        lblNombres.setBounds(110, 206, 500, 20);
        panel.add(lblNombres);

        JButton btnIniciar = new JButton("Iniciar planificación");
        btnIniciar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnIniciar.setForeground(Color.WHITE);
        btnIniciar.setBackground(GREEN);
        btnIniciar.setBorderPainted(false);
        btnIniciar.setFocusPainted(false);
        btnIniciar.setBounds(235, 330, 250, 46);
        btnIniciar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new SolicitudDePlanificacion().setVisible(true);
                frame.setVisible(false);
            }
        });
        panel.add(btnIniciar);
    }
}
