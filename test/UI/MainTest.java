package UI;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.*;

public class MainTest {

    private Main frame;

    @Before
    public void setUp() throws Exception {
        AtomicReference<Main> ref = new AtomicReference<>();
        SwingUtilities.invokeAndWait(() -> {
            Main f = new Main();
            f.setVisible(true);
            ref.set(f);
        });
        frame = ref.get();
    }

    @After
    public void tearDown() throws Exception {
        if (frame != null) {
            SwingUtilities.invokeAndWait(() -> {
                frame.setVisible(false);
                frame.dispose();
            });
            frame = null;
        }
    }

    @Test
    public void testFrameSeInstanciaCorrectamente() {
        assertNotNull(frame);
    }

    @Test
    public void testTitulo() {
        assertEquals("Planificación de Rutas", frame.getTitle());
    }

    @Test
    public void testTamano() {
        assertEquals(600, frame.getWidth());
        assertEquals(520, frame.getHeight());
    }

    @Test
    public void testBotonIniciarExiste() {
        JButton btn = findButton(frame.getContentPane(), "Iniciar planificación");
        assertNotNull("El botón 'Iniciar planificación' debe existir", btn);
    }

    private JButton findButton(Container container, String text) {
        for (Component c : container.getComponents()) {
            if (c instanceof JButton btn && text.equals(btn.getText())) return btn;
            if (c instanceof Container child) {
                JButton found = findButton(child, text);
                if (found != null) return found;
            }
        }
        return null;
    }
}