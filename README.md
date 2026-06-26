# TP2 - Conectando localidades a la velocidad de la luz

Trabajo Práctico 2 de Programación III — Universidad Nacional de General Sarmiento, Comisión 01.

Aplicación para planificar conexiones de fibra óptica entre localidades mediante un Árbol Generador Mínimo (AGM).

## Integrantes

- Marcelo Agustín Gomez Rodríguez ([@MarceloAgustindev](https://github.com/MarceloAgustindev))
- Nicolás Rocha ([@AgusNico-java](https://github.com/AgusNico-java))
- Florencia Sangueso ([@FlorenciaSangueso](https://github.com/FlorenciaSangueso))
- Agustín Taibo Cruz ([@TaiboAgustin](https://github.com/TaiboAgustin))

## Funcionalidades

### Obligatorias
- Registro de localidades (nombre, provincia, latitud, longitud)
- Configuración de parámetros de costo: costo por km, tarifa interprovincial y adicional por distancias largas (>300 km)
- Cálculo del Árbol Generador Mínimo sobre el grafo completo de localidades
- Visualización del costo total de la solución

### Opcionales implementadas
- Visualización de localidades y conexiones del AGM sobre un mapa interactivo (JMapViewer)
- Persistencia de localidades entre sesiones (archivo `localidades.json`)
- Visualización del costo individual de cada conexión del AGM en el panel lateral

## Cómo ejecutar

El punto de entrada es `src/UI/Main.java`. El flujo de la aplicación es:

1. **Pantalla de bienvenida** — iniciar planificación
2. **Planificación** — ingresar parámetros de costo y agregar localidades
3. **Mapa** — visualizar las localidades, generar el AGM y ver el costo total

## Estructura del proyecto

```
tp2-agm/
├── src/
│   ├── UI/
│   │   ├── Main.java                      # Pantalla de bienvenida
│   │   ├── SolicitudDePlanificacion.java  # Parámetros y carga de localidades
│   │   ├── AgregarLocalidad.java          # Diálogo para nueva localidad
│   │   └── PantallaPrincipalMAPA.java     # Mapa + generación del AGM
│   ├── Logica_Planificador/
│   │   ├── PlanificadorRed.java           # Coordinación entre UI y lógica
│   │   └── ConexionVisual.java            # DTO para dibujar aristas en el mapa
│   ├── DTO/
│   │   ├── PersistenciaENJson.java        # Lectura y escritura de JSON
│   │   └── LocalidadDTO.java
│   └── logica/
│       ├── modelo/
│       │   ├── Localidad.java
│       │   ├── Arista.java
│       │   ├── Grafo.java
│       │   ├── GeneradorDeGrafo.java      # Construcción del grafo completo (Haversine)
│       │   ├── ParametrosPrecio.java
│       │   └── Validador.java
│       └── agm/
│           ├── AlgoritmoKruskal.java       # Retorna el AGM como Grafo
│           └── UnionFind.java
├── test/
│   ├── UI/
│   │   └── PantallaPrincipalMAPATest.java
│   ├── DTO/
│   │   └── PERSISTENCIA/
│   │       └── PersistenciaENJsonTest.java
│   ├── planificador/
│   │   ├── PlanificadorTest.java
│   │   └── ConexionesVisualesTest.java
│   └── logica/
│       ├── modelo/
│       │   ├── LocalidadTest.java
│       │   ├── AristaTest.java
│       │   ├── GrafoTest.java
│       │   ├── GeneradorDeGrafoTest.java
│       │   └── ParametrosPrecioTest.java
│       └── agm/
│           ├── AlgoritmoKruskalTest.java
│           ├── UnionFindTest.java
│           └── IntegracionAGMTest.java
└── lib/
    ├── JMapViewer.jar
    ├── gson-2.14.0.jar
    ├── junit-4.13.2.jar
    └── hamcrest-core-1.3.jar
```

## CI

El proyecto cuenta con un pipeline de GitHub Actions que corre en cada push y pull request a `main`. Ejecuta todos los tests unitarios y verifica una cobertura mínima del 80% con JaCoCo.
