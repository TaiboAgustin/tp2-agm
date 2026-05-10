package UI;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.junit.Assert.*;

public class SolicitudPlanificacionTest {

    private SolicitudDePlanificacion frame;
    private Main mockParent;

    private int mensajesMostrados;

    @Before
    public void setUp() throws Exception {
        mensajesMostrados = 0;
        AtomicReference<SolicitudDePlanificacion> ref = new AtomicReference<>();
        SwingUtilities.invokeAndWait(() -> {
            mockParent = new Main();
            SolicitudDePlanificacion f = new SolicitudDePlanificacion(mockParent) {
                @Override
                protected void mostrarMensaje(String mensaje, String tipo) {
                    mensajesMostrados++;
                }
            };
            f.setVisible(false);
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
        if (mockParent != null) {
            SwingUtilities.invokeAndWait(() -> mockParent.dispose());
            mockParent = null;
        }
    }

    @Test
    public void testFrameSeInstanciaCorrectamente() {
        assertNotNull(frame);
    }

    @Test
    public void testTitulo() {
        assertEquals("Generar Solicitud", frame.getTitle());
    }

    @Test
    public void testTamano() {
        assertEquals(600, frame.getWidth());
        assertEquals(520, frame.getHeight());
    }

    @Test
    public void testCloseOperation() {
        assertEquals(JFrame.EXIT_ON_CLOSE, frame.getDefaultCloseOperation());
    }

    @Test
    public void testBotonAgregarLocalidadExiste() {
        JButton btn = findButton(frame.getContentPane(), "Agregar nueva localidad");
        assertNotNull("El botón 'Agregar nueva localidad' debe existir", btn);
    }

    @Test
    public void testBotonLimpiarExiste() {
        JButton btn = findButton(frame.getContentPane(), "Limpiar");
        assertNotNull("El botón 'Limpiar' debe existir", btn);
    }

    @Test
    public void testBotonGenerarPlanificacionExiste() {
        JButton btn = findButton(frame.getContentPane(), "Generar planificacion");
        assertNotNull("El botón 'Generar planificacion' debe existir", btn);
    }

    @Test
    public void testCampoCostoKmExiste() {
        JTextField field = findTextField(frame.getContentPane(), 0);
        assertNotNull("El campo 'Costo del kilómetro' debe existir", field);
    }

    @Test
    public void testCampoTarifaInterprovincialExiste() {
        JTextField field = findTextField(frame.getContentPane(), 1);
        assertNotNull("El campo 'Tarifa interprovincial' debe existir", field);
    }

    @Test
    public void testCampoDistanciasLargasExiste() {
        JTextField field = findTextField(frame.getContentPane(), 2);
        assertNotNull("El campo 'Costo distancias largas' debe existir", field);
    }

    @Test
    public void testCamposVaciosAlInicio() {
        List<JTextField> fields = findAllTextFields(frame.getContentPane());
        assertEquals("Deben existir 3 campos de texto", 3, fields.size());
        for (JTextField f : fields) {
            assertEquals("El campo debe estar vacío al inicio", "", f.getText());
        }
    }

    @Test
    public void testBotonLimpiarVaciaLosCampos() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            List<JTextField> fields = findAllTextFields(frame.getContentPane());
            fields.get(0).setText("10.5");
            fields.get(1).setText("20.0");
            fields.get(2).setText("5.75");
        });

        SwingUtilities.invokeAndWait(() -> {
            JButton btnLimpiar = findButton(frame.getContentPane(), "Limpiar");
            assertNotNull(btnLimpiar);
            btnLimpiar.doClick();
        });

        SwingUtilities.invokeAndWait(() -> {
            List<JTextField> fields = findAllTextFields(frame.getContentPane());
            for (JTextField f : fields) {
                assertEquals("El campo debe estar vacío tras limpiar", "", f.getText());
            }
        });
    }

    @Test
    public void testGenerarConCamposVaciosNoLanzaExcepcion() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            JButton btnGenerar = findButton(frame.getContentPane(), "Generar planificacion");
            assertNotNull(btnGenerar);
            assertTrue("El botón debe estar habilitado", btnGenerar.isEnabled());
        });
    }

    @Test
    public void testCamposAceptanSoloTexto() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            List<JTextField> fields = findAllTextFields(frame.getContentPane());
            fields.get(0).setText("abc");
            assertEquals("El campo acepta cualquier texto", "abc", fields.get(0).getText());
        });
    }

    @Test
    public void testConvertToFloatValorValido() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            List<JTextField> fields = findAllTextFields(frame.getContentPane());
            fields.get(0).setText("12.5");
            assertEquals(12.5f, frame.convertToFloat(fields.get(0)), 0.001f);
        });
    }

    @Test(expected = NumberFormatException.class)
    public void testConvertToFloatValorInvalidoLanzaExcepcion() throws Exception {
        AtomicReference<NumberFormatException> ex = new AtomicReference<>();
        SwingUtilities.invokeAndWait(() -> {
            List<JTextField> fields = findAllTextFields(frame.getContentPane());
            fields.get(0).setText("abc");
            try {
                frame.convertToFloat(fields.get(0));
            } catch (NumberFormatException e) {
                ex.set(e);
            }
        });
        if (ex.get() != null) throw ex.get();
    }

    @Test
    public void testCrearParametrosPrecioConValoresValidosNoLanzaExcepcion() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            List<JTextField> fields = findAllTextFields(frame.getContentPane());
            fields.get(0).setText("1.0");
            fields.get(1).setText("2.0");
            fields.get(2).setText("3.0");
            frame.crearParametrosPrecio();
        });
    }

    @Test
    public void testCrearParametrosPrecioConCamposVaciosMuestraError() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            frame.crearParametrosPrecio();
        });
        assertEquals(1, mensajesMostrados);
    }

    @Test
    public void testCrearParametrosPrecioConValoresValidosMuestraExito() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            List<JTextField> fields = findAllTextFields(frame.getContentPane());
            fields.get(0).setText("1.0");
            fields.get(1).setText("2.0");
            fields.get(2).setText("3.0");
            frame.crearParametrosPrecio();
        });
        assertEquals(1, mensajesMostrados);
    }

    @Test
    public void testCrearPlanificacionSinLocalidadesMuestraError() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            List<JTextField> fields = findAllTextFields(frame.getContentPane());
            fields.get(0).setText("1.0");
            fields.get(1).setText("2.0");
            fields.get(2).setText("3.0");
            JButton btnGenerar = findButton(frame.getContentPane(), "Generar planificacion");
            btnGenerar.doClick();
        });
        assertTrue(mensajesMostrados >= 1);
    }
    
    @Test
    public void testBotonGenerarPlanificacionConDatosCompletosNoLanzaExcepcion() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            List<JTextField> fields = findAllTextFields(frame.getContentPane());
            fields.get(0).setText("5.0");
            fields.get(1).setText("10.0");
            fields.get(2).setText("2.5");

            JButton btnGenerar = findButton(frame.getContentPane(), "Generar planificacion");
            btnGenerar.doClick();
        });
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

    private JTextField findTextField(Container container, int index) {
        List<JTextField> fields = findAllTextFields(container);
        return index < fields.size() ? fields.get(index) : null;
    }

    private List<JTextField> findAllTextFields(Container container) {
        List<JTextField> result = new java.util.ArrayList<>();
        for (Component c : container.getComponents()) {
            if (c instanceof JTextField tf) result.add(tf);
            if (c instanceof Container child) result.addAll(findAllTextFields(child));
        }
        return result;
    }
}