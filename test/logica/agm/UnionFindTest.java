package logica.agm;

import org.junit.Test;
import static org.junit.Assert.*;

public class UnionFindTest {

    @Test
    public void seCreaCorrectamente() {
        UnionFind uf = new UnionFind(3);
        assertFalse(uf.mismoGrupo(0, 1));
        assertFalse(uf.mismoGrupo(1, 2));
        assertFalse(uf.mismoGrupo(0, 2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void nCeroLanzaExcepcion() {
        new UnionFind(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nNegativoLanzaExcepcion() {
        new UnionFind(-1);
    }

    @Test
    public void despuesDeUnionEstanEnMismoGrupo() {
        UnionFind uf = new UnionFind(3);
        uf.union(0, 1);
        assertTrue(uf.mismoGrupo(0, 1));
    }

    @Test
    public void unionNoAfectaAOtrosGrupos() {
        UnionFind uf = new UnionFind(3);
        uf.union(0, 1);
        assertFalse(uf.mismoGrupo(0, 2));
        assertFalse(uf.mismoGrupo(1, 2));
    }

    @Test
    public void unionEsTransitiva() {
        UnionFind uf = new UnionFind(3);
        uf.union(0, 1);
        uf.union(1, 2);
        assertTrue(uf.mismoGrupo(0, 2));
    }

    @Test
    public void unionEnMismoGrupoNoHaceNada() {
        UnionFind uf = new UnionFind(3);
        uf.union(0, 1);
        uf.union(0, 1);
        assertTrue(uf.mismoGrupo(0, 1));
        assertFalse(uf.mismoGrupo(0, 2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void findConIndiceNegativoLanzaExcepcion() {
        new UnionFind(3).find(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void findConIndiceFueraDeRangoLanzaExcepcion() {
        new UnionFind(3).find(3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void unionConPrimerIndiceNegativoLanzaExcepcion() {
        new UnionFind(3).union(-1, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void unionConSegundoIndiceFueraDeRangoLanzaExcepcion() {
        new UnionFind(3).union(0, 3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void mismoGrupoConIndiceNegativoLanzaExcepcion() {
        new UnionFind(3).mismoGrupo(-1, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void mismoGrupoConIndiceFueraDeRangoLanzaExcepcion() {
        new UnionFind(3).mismoGrupo(0, 3);
    }
}
