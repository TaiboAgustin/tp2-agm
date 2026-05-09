package UI;

import logica.modelo.Localidad;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.swing.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;

public class AgregarLocalidadTest {

    private AgregarLocalidad dialog;

    private JTextField txtNombre;
    private JTextField txtProvincia;
    private JTextField txtLatitud;
    private JTextField txtLongitud;

    private JTextField getField(String name) throws Exception {
        Field f = AgregarLocalidad.class.getDeclaredField(name);
        f.setAccessible(true);
        return (JTextField) f.get(dialog);
    }

    private void invokePrivate(String methodName) throws Exception {
    	SwingUtilities.invokeAndWait(() -> {
            try {
                Method m = AgregarLocalidad.class.getDeclaredMethod(methodName);
                m.setAccessible(true);
                m.invoke(dialog);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void fillForm(String nombre, String provincia,
                          String latitud, String longitud) {
        txtNombre.setText(nombre);
        txtProvincia.setText(provincia);
        txtLatitud.setText(latitud);
        txtLongitud.setText(longitud);
    }

    @Before
    public void setUp() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            AgregarLocalidad real = new AgregarLocalidad(null);
            dialog = Mockito.spy(real);
            doNothing().when(dialog).mostrarError(anyString());
            doNothing().when(dialog).dispose();
        });

        txtNombre    = getField("txtNombre");
        txtProvincia = getField("txtProvincia");
        txtLatitud   = getField("txtLatitud");
        txtLongitud  = getField("txtLongitud");
    }

    @After
    public void tearDown() {
        if (dialog != null) SwingUtilities.invokeLater(() -> dialog = null);
    }

    @Test
    public void testDialogSeInstanciaCorrectamente() {
        assertNotNull(dialog);
    }

    @Test
    public void testLocalidadCreadaEsNulaAlInicio() {
        assertNull(dialog.getLocalidadCreada());
    }

    @Test
    public void testCamposVaciosAlInicio() {
        assertEquals("", txtNombre.getText());
        assertEquals("", txtProvincia.getText());
        assertEquals("", txtLatitud.getText());
        assertEquals("", txtLongitud.getText());
    }

    @Test
    public void testLimpiarCamposBorraContenido() throws Exception {
        fillForm("Córdoba", "Córdoba", "31.4", "-64.18");
        invokePrivate("limpiarCampos");

        assertEquals("", txtNombre.getText());
        assertEquals("", txtProvincia.getText());
        assertEquals("", txtLatitud.getText());
        assertEquals("", txtLongitud.getText());
    }

    @Test
    public void testLimpiarCamposConCamposVaciosNoLanzaExcepcion()
            throws Exception {
        invokePrivate("limpiarCampos");
    }

    @Test
    public void testCrearLocalidadValida() throws Exception {
        fillForm("Buenos Aires", "Buenos Aires", "-34.61", "-58.37");
        invokePrivate("crearLocalidad");

        Localidad loc = dialog.getLocalidadCreada();
        assertNotNull(loc);
        assertEquals("Buenos Aires", loc.getNombre());
        assertEquals("Buenos Aires", loc.getProvincia());
        assertEquals(-34.61, loc.getLatitud(),  0.0001);
        assertEquals(-58.37, loc.getLongitud(), 0.0001);
    }

    @Test
    public void testCrearLocalidadEnLimitesExactosMinimos() throws Exception {
        fillForm("Extremo", "Antártida", "-90", "-180");
        invokePrivate("crearLocalidad");
        assertNotNull(dialog.getLocalidadCreada());
    }

    @Test
    public void testCrearLocalidadEnLimitesExactoMaximos() throws Exception {
        fillForm("Extremo", "Ártico", "90", "180");
        invokePrivate("crearLocalidad");
        assertNotNull(dialog.getLocalidadCreada());
    }

    @Test
    public void testCrearLocalidadConEspaciosEnNombreYProvincia()
            throws Exception {
        fillForm("  Mendoza  ", "  Mendoza  ", "32.89", "-68.84");
        invokePrivate("crearLocalidad");
        assertNotNull(dialog.getLocalidadCreada());
    }

    @Test
    public void testCrearLocalidadSinNombreNoCreaNada() throws Exception {
        fillForm("", "Salta", "24.78", "-65.41");
        invokePrivate("crearLocalidad");
        assertNull(dialog.getLocalidadCreada());
    }

    @Test
    public void testCrearLocalidadSinProvinciaNoCreaNada() throws Exception {
        fillForm("Salta", "", "24.78", "-65.41");
        invokePrivate("crearLocalidad");
        assertNull(dialog.getLocalidadCreada());
    }

    @Test
    public void testCrearLocalidadSinLatitudNoCreaNada() throws Exception {
        fillForm("Salta", "Salta", "", "-65.41");
        invokePrivate("crearLocalidad");
        assertNull(dialog.getLocalidadCreada());
    }

    @Test
    public void testCrearLocalidadSinLongitudNoCreaNada() throws Exception {
        fillForm("Salta", "Salta", "24.78", "");
        invokePrivate("crearLocalidad");
        assertNull(dialog.getLocalidadCreada());
    }

    @Test
    public void testCrearLocalidadTodosCamposVaciosNoCreaNada()
            throws Exception {
        invokePrivate("crearLocalidad");
        assertNull(dialog.getLocalidadCreada());
    }

    @Test
    public void testCrearLocalidadSoloCamposBlancos() throws Exception {
        fillForm("   ", "   ", "   ", "   ");
        invokePrivate("crearLocalidad");
        assertNull(dialog.getLocalidadCreada());
    }

    @Test
    public void testLatitudNoNumericaNoCreaNada() throws Exception {
        fillForm("Rosario", "Santa Fe", "abc", "-60.66");
        invokePrivate("crearLocalidad");
        assertNull(dialog.getLocalidadCreada());
    }

    @Test
    public void testLongitudNoNumericaNoCreaNada() throws Exception {
        fillForm("Rosario", "Santa Fe", "-32.94", "xyz");
        invokePrivate("crearLocalidad");
        assertNull(dialog.getLocalidadCreada());
    }

    @Test
    public void testLatitudMayorA90NoCreaNada() throws Exception {
        fillForm("Invalida", "Prov", "91", "0");
        invokePrivate("crearLocalidad");
        assertNull(dialog.getLocalidadCreada());
    }

    @Test
    public void testLatitudMenorAMenos90NoCreaNada() throws Exception {
        fillForm("Invalida", "Prov", "-91", "0");
        invokePrivate("crearLocalidad");
        assertNull(dialog.getLocalidadCreada());
    }

    @Test
    public void testLongitudMayorA180NoCreaNada() throws Exception {
        fillForm("Invalida", "Prov", "0", "181");
        invokePrivate("crearLocalidad");
        assertNull(dialog.getLocalidadCreada());
    }

    @Test
    public void testLongitudMenorAMenos180NoCreaNada() throws Exception {
        fillForm("Invalida", "Prov", "0", "-181");
        invokePrivate("crearLocalidad");
        assertNull(dialog.getLocalidadCreada());
    }

    @Test
    public void testLatitudConComaDecimalNoCreaNada() throws Exception {
        fillForm("Test", "Prov", "34,61", "-58.37");
        invokePrivate("crearLocalidad");
        assertNull(dialog.getLocalidadCreada());
    }

    @Test
    public void testMostrarErrorSeLlamaConCamposVacios() throws Exception {
        fillForm("", "", "", "");
        invokePrivate("crearLocalidad");
        Mockito.verify(dialog, Mockito.times(1)).mostrarError(anyString());
    }

    @Test
    public void testMostrarErrorSeLlamaConLatitudInvalida() throws Exception {
        fillForm("X", "Y", "999", "0");
        invokePrivate("crearLocalidad");
        Mockito.verify(dialog, Mockito.times(1)).mostrarError(anyString());
    }

    @Test
    public void testMostrarErrorNoSeLlamaConDatosValidos() throws Exception {
        fillForm("Córdoba", "Córdoba", "-31.4", "-64.18");
        invokePrivate("crearLocalidad");
        Mockito.verify(dialog, Mockito.never()).mostrarError(anyString());
    }
}