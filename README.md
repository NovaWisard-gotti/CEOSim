# CEOSim

**Mi Pequeña Tienda** — app educativa Android para niños de 8 a 12 años
sobre conceptos básicos de emprendimiento (comprar, organizar, vender,
ganar, ahorrar, mejorar), 100% offline y con datos ficticios.

- Paquete: `com.educalab.ceosim`
- Versión: `1.0.0`
- Kotlin + Jetpack Compose + Material 3 + Room + Navigation Compose
- minSdk 24 · targetSdk / compileSdk 34 · JDK 17

## ⚠️ Estado de compilación

Este proyecto fue desarrollado en un entorno **sin Android SDK y sin
acceso de red a los repositorios Maven de Google/AndroidX**. Por lo tanto:

> **COMPILACIÓN NO VERIFICADA localmente.**

El código está completo (arquitectura, Room, motores de dominio, 75 tests,
10 pantallas Compose, ilustraciones, documentación), pero no ha pasado por
un compilador Kotlin/Android real todavía. Ver
[`docs/BUILD_REPORT.md`](docs/BUILD_REPORT.md) para el detalle exacto de lo
que sí y no se pudo verificar, y por qué.

### Cómo verificar la compilación de verdad

**Opción recomendada — GitHub Actions (incluido en este repo):**

1. Sube este proyecto a un repositorio de GitHub.
2. El workflow en [`.github/workflows/build.yml`](.github/workflows/build.yml)
   se ejecuta automáticamente en cada `push`: instala un Android SDK
   completo, corre `testDebugUnitTest`, `lintDebug` y `assembleDebug`, y
   sube el APK resultante junto con su SHA-256 como artefacto descargable.

**Opción local (si tienes Android Studio / SDK instalado):**

```bash
./gradlew clean
./gradlew testDebugUnitTest
./gradlew lintDebug
./gradlew assembleDebug
```

## Estructura del proyecto

```
app/            Código fuente Android (Kotlin + Compose)
database/       schema.sql y sample_data.sql (SQL de referencia, espejo de Room)
docs/           Documentación (memoria, manuales, base de datos, build report) + PDFs
gradle/         Gradle Wrapper
deliverables/   Entregables finales (APK, ZIP fuente, PDFs) una vez generados
```

## Documentación

- [`docs/MEMORIA_DESCRIPTIVA.md`](docs/MEMORIA_DESCRIPTIVA.md) — propósito, público, objetivos, alcance, conceptos educativos.
- [`docs/MANUAL_USUARIO.md`](docs/MANUAL_USUARIO.md) — cómo se juega.
- [`docs/MANUAL_TECNICO.md`](docs/MANUAL_TECNICO.md) — arquitectura, motores, Room, pruebas, privacidad, simplificaciones y limitaciones.
- [`docs/BASE_DE_DATOS.md`](docs/BASE_DE_DATOS.md) — esquema completo, 17 entidades, diagrama ER.
- [`docs/BUILD_REPORT.md`](docs/BUILD_REPORT.md) — resultado real (no simulado) del intento de build.

## Privacidad

Sin permiso `INTERNET`, sin backend, sin analíticas ni anuncios, sin datos
personales reales. Ver `docs/MANUAL_TECNICO.md` sección 8.

## Licencia / Uso

Proyecto educativo generado para EducaLab. Todos los productos y marcas
mencionados dentro de la simulación son ficticios.
