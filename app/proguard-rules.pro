# CEOSim - reglas ProGuard/R8 básicas
# minifyEnabled está desactivado en debug y release por defecto; este archivo
# queda preparado para cuando se active minificación en el futuro.
-keep class com.educalab.ceosim.data.local.entity.** { *; }
