# TP2 - Conectando localidades a la velocidad de la luz

Trabajo Práctico 2 de Programación III - Universidad Nacional de General Sarmiento.

Aplicación para planificar conexiones de fibra óptica entre localidades mediante un Árbol Generador Mínimo (AGM).

## Integrantes

- Agustín Taibo Cruz
- Marcelo Agustín Gomez Rodríguez
- Nicolás Rocha
- Florencia Sangueso

## Estado del proyecto

### Módulo 1 - Carga de datos
- [ ] Lectura de localidades desde archivo
- [ ] Validación de datos de entrada

### Módulo 2 - Construcción del grafo
- [ ] Cálculo de distancias entre localidades
- [ ] Construcción del grafo completo

### Módulo 3 - Algoritmo AGM
- [x] Clases contrato (`Localidad`, `Arista`, `Grafo`, `ResultadoAGM`)
- [x] `UnionFind` con union por rango y compresión de camino
- [x] `AlgoritmoKruskal`
- [x] Tests de integración
- [x] CI con GitHub Actions (tests + cobertura mínima 80%)
- [x] PR mergeado a `main`

### Módulo 4 - Visualización
- [ ] Mostrar resultado del AGM en pantalla

## Estructura del proyecto

```
tp2-agm/
├── src/
│   └── logica/
│       ├── modelo/
│       │   ├── Localidad.java
│       │   ├── Arista.java
│       │   └── Grafo.java
│       └── agm/
│           ├── ResultadoAGM.java
│           ├── UnionFind.java
│           └── AlgoritmoKruskal.java
├── test/
│   └── logica/
│       ├── modelo/
│       │   ├── LocalidadTest.java
│       │   ├── AristaTest.java
│       │   └── GrafoTest.java
│       └── agm/
│           ├── ResultadoAGMTest.java
│           ├── UnionFindTest.java
│           ├── AlgoritmoKruskalTest.java
│           └── IntegracionAGMTest.java
└── lib/
    ├── junit-4.13.2.jar
    └── hamcrest-core-1.3.jar
```
