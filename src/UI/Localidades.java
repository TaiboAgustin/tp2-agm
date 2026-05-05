package UI;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import logica.modelo.Localidad;

public class Localidades extends JFrame {
	private List<Localidad> localidadesAgregadas;

	public Localidades(List<Localidad> localidadesAgregadas, JFrame parent) {
		this.localidadesAgregadas = localidadesAgregadas;
		initialize();
	}

	private void initialize() {
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
	}

}
