package logica.agm;

public class UnionFind {

    private final int[] padre;
    private final int[] rango;

    public UnionFind(int n) {
        if (n < 1)
            throw new IllegalArgumentException("El tamaño debe ser al menos 1");
        padre = new int[n];
        rango = new int[n];
        for (int i = 0; i < n; i++)
            padre[i] = i;
    }

    public int find(int x) {
        validar(x);
        if (padre[x] != x)
            padre[x] = find(padre[x]);
        return padre[x];
    }

    public void union(int x, int y) {
        validar(x);
        validar(y);
        int raizX = find(x);
        int raizY = find(y);
        if (raizX == raizY) return;
        if (rango[raizX] < rango[raizY])
            padre[raizX] = raizY;
        else if (rango[raizX] > rango[raizY])
            padre[raizY] = raizX;
        else {
            padre[raizY] = raizX;
            rango[raizX]++;
        }
    }

    public boolean mismoGrupo(int x, int y) {
        validar(x);
        validar(y);
        return find(x) == find(y);
    }

    private void validar(int x) {
        if (x < 0 || x >= padre.length)
            throw new IllegalArgumentException("Índice fuera de rango: " + x);
    }
}
