package com.educalab.ceosim.data.local

import com.educalab.ceosim.data.local.entity.BadgeEntity
import com.educalab.ceosim.data.local.entity.ChallengeEntity
import com.educalab.ceosim.data.local.entity.CustomerEntity
import com.educalab.ceosim.data.local.entity.ProductEntity
import com.educalab.ceosim.data.local.entity.StoreUpgradeEntity
import com.educalab.ceosim.domain.engine.RewardEngine
import com.educalab.ceosim.domain.model.ChallengeType
import com.educalab.ceosim.domain.model.CustomerAvatar
import com.educalab.ceosim.domain.model.ProductCategory
import com.educalab.ceosim.domain.model.UpgradeCategory

/**
 * Datos semilla de CEOSim.
 *
 * Cumple los mínimos de la especificación: 20 productos, 10 clientes,
 * 10 mejoras, 10 retos, 12 insignias. La app debe sentirse completa desde
 * la instalación, no un prototipo con 3 elementos.
 */
object SeedData {

    val products: List<ProductEntity> = listOf(
        ProductEntity("jugo_naranja", "Jugo de naranja", ProductCategory.BEBIDA, 5, 8, 1),
        ProductEntity("jugo_manzana", "Jugo de manzana", ProductCategory.BEBIDA, 5, 8, 1),
        ProductEntity("agua_fresca", "Agua fresca", ProductCategory.BEBIDA, 4, 6, 1),
        ProductEntity("manzana_roja", "Manzana roja", ProductCategory.FRUTA, 3, 5, 1),
        ProductEntity("platano", "Plátano", ProductCategory.FRUTA, 3, 5, 1),
        ProductEntity("naranja_fruta", "Naranja", ProductCategory.FRUTA, 3, 5, 1),
        ProductEntity("cuaderno_rayado", "Cuaderno rayado", ProductCategory.PAPELERIA, 6, 9, 1),
        ProductEntity("lapiz_grafito", "Lápiz de grafito", ProductCategory.PAPELERIA, 2, 4, 1),
        ProductEntity("goma_borrar", "Goma de borrar", ProductCategory.PAPELERIA, 2, 3, 1),
        ProductEntity("colores_caja", "Caja de colores", ProductCategory.PAPELERIA, 8, 12, 2),
        ProductEntity("pelota_futbol", "Pelota de fútbol", ProductCategory.JUGUETE, 10, 15, 1),
        ProductEntity("trompo_madera", "Trompo de madera", ProductCategory.JUGUETE, 6, 9, 2),
        ProductEntity("carrito_juguete", "Carrito de juguete", ProductCategory.JUGUETE, 9, 14, 2),
        ProductEntity("galleta_avena", "Galleta de avena", ProductCategory.GALLETA, 3, 5, 1),
        ProductEntity("galleta_chocolate", "Galleta de chocolate", ProductCategory.GALLETA, 4, 6, 1),
        ProductEntity("planta_maceta", "Planta en maceta", ProductCategory.PLANTA, 7, 11, 2),
        ProductEntity("cactus_mini", "Cactus mini", ProductCategory.PLANTA, 6, 9, 2),
        ProductEntity("cuerda_saltar", "Cuerda para saltar", ProductCategory.DEPORTE, 5, 8, 2),
        ProductEntity("gorra_deportiva", "Gorra deportiva", ProductCategory.DEPORTE, 8, 12, 3),
        ProductEntity("libro_cuentos", "Libro de cuentos", ProductCategory.LIBRO, 7, 11, 3)
    )

    val customers: List<CustomerEntity> = listOf(
        CustomerEntity("cli_camila", "Camila", CustomerAvatar.NINA_TRENZAS, "¡Hola! ¿Tienes algo rico hoy?"),
        CustomerEntity("cli_mateo", "Mateo", CustomerAvatar.NINO_LENTES, "Buen día, ando buscando algo útil."),
        CustomerEntity("cli_rosa", "Doña Rosa", CustomerAvatar.ABUELA_BUFANDA, "Hola, pequeño tendero, ¿me ayudas?"),
        CustomerEntity("cli_pedro", "Don Pedro", CustomerAvatar.ABUELO_SOMBRERO, "¡Qué tienda tan ordenada!"),
        CustomerEntity("cli_valentina", "Valentina", CustomerAvatar.NINA_GORRA, "¡Vine corriendo por algo especial!"),
        CustomerEntity("cli_diego", "Diego", CustomerAvatar.NINO_CHALECO, "Necesito algo para hoy, ¿tienes?"),
        CustomerEntity("cli_robo", "Robo", CustomerAvatar.ROBOT_AMIGABLE, "BEEP. Solicitando producto disponible."),
        CustomerEntity("cli_michi", "Michi", CustomerAvatar.GATO_CLIENTE, "Miau, quiero algo delicioso."),
        CustomerEntity("cli_sofia", "Sofía", CustomerAvatar.NINA_PATINETA, "¡Hola! Paso rapidito por algo."),
        CustomerEntity("cli_rex", "Rex", CustomerAvatar.PERRO_CLIENTE, "¡Guau! Vine a visitar tu tienda.")
    )

    val upgrades: List<StoreUpgradeEntity> = listOf(
        StoreUpgradeEntity("estante_madera", "Estante de madera", UpgradeCategory.ESTANTE, 20, 1, "Un estante nuevo para ordenar más productos."),
        StoreUpgradeEntity("maceta_decorativa", "Maceta decorativa", UpgradeCategory.DECORACION, 15, 1, "Una planta que alegra la entrada de la tienda."),
        StoreUpgradeEntity("cartel_bienvenida", "Cartel de bienvenida", UpgradeCategory.CARTEL, 18, 1, "Un cartel que saluda a los clientes."),
        StoreUpgradeEntity("lampara_calida", "Lámpara cálida", UpgradeCategory.ILUMINACION, 25, 2, "Ilumina mejor los estantes de tu tienda."),
        StoreUpgradeEntity("alfombra_colorida", "Alfombra colorida", UpgradeCategory.DECORACION, 22, 2, "Le da un toque alegre a la entrada."),
        StoreUpgradeEntity("estante_metal", "Estante de metal", UpgradeCategory.ESTANTE, 35, 2, "Más resistente y con más espacio."),
        StoreUpgradeEntity("mostrador_nuevo", "Mostrador renovado", UpgradeCategory.MOSTRADOR, 40, 2, "Un mostrador más grande para atender mejor."),
        StoreUpgradeEntity("cartel_ofertas", "Cartel de ofertas", UpgradeCategory.CARTEL, 20, 2, "Anuncia tus productos destacados."),
        StoreUpgradeEntity("luces_decorativas", "Luces decorativas", UpgradeCategory.ILUMINACION, 30, 3, "Le dan un brillo especial a tu tienda."),
        StoreUpgradeEntity("mostrador_lujo", "Mostrador de lujo", UpgradeCategory.MOSTRADOR, 60, 3, "El mostrador más elegante de la ciudad.")
    )

    val challenges: List<ChallengeEntity> = listOf(
        ChallengeEntity("reto_pocos_jugos", "Pocos jugos", "Parece que se están acabando los jugos. ¿Qué puedes hacer?", ChallengeType.REABASTECER, "jugo_naranja", 3, 10),
        ChallengeEntity("reto_cliente_pelota", "Un cliente especial", "Un cliente quiere una pelota. ¿Tienes suficientes en la tienda?", ChallengeType.ATENDER_CLIENTE, "pelota_futbol", 1, 10),
        ChallengeEntity("reto_precio_cuaderno", "Precio justo", "Revisa el precio de venta de tus cuadernos.", ChallengeType.AJUSTAR_PRECIO, "cuaderno_rayado", 1, 10),
        ChallengeEntity("reto_mejorar_estante", "Más espacio", "Tu tienda necesita más espacio para productos.", ChallengeType.MEJORAR_TIENDA, null, 1, 10),
        ChallengeEntity("reto_ahorro_50", "Meta de ahorro", "Intenta juntar 50 monedas en tu caja.", ChallengeType.AHORRAR, null, 50, 10),
        ChallengeEntity("reto_pocas_galletas", "Galletas escasas", "Las galletas de chocolate se están agotando.", ChallengeType.REABASTECER, "galleta_chocolate", 3, 10),
        ChallengeEntity("reto_cliente_libro", "Cliente lector", "Un cliente busca un buen libro de cuentos.", ChallengeType.ATENDER_CLIENTE, "libro_cuentos", 1, 10),
        ChallengeEntity("reto_precio_pelota", "Ganancia justa", "Revisa si el precio de la pelota deja ganancia.", ChallengeType.AJUSTAR_PRECIO, "pelota_futbol", 1, 10),
        ChallengeEntity("reto_mejorar_cartel", "Tienda más visible", "Un cartel ayudaría a que más clientes te visiten.", ChallengeType.MEJORAR_TIENDA, null, 1, 10),
        ChallengeEntity("reto_ahorro_100", "Gran ahorro", "Intenta juntar 100 monedas en tu caja.", ChallengeType.AHORRAR, null, 100, 10)
    )

    val badges: List<BadgeEntity> = RewardEngine.ALL_BADGES.map {
        BadgeEntity(id = it.id, title = it.title, description = it.description)
    }

    /**
     * "Situaciones" narrativas de Nico (mínimo 15 exigido por la
     * especificación). No son cuestionarios: son frases cortas que Nico usa
     * para narrar lo que ocurre dentro de la simulación, según el contexto.
     */
    val nicoSituations: List<NicoSituation> = listOf(
        NicoSituation("bienvenida", "¡Hola! Soy Nico. Esta es tu pequeña tienda."),
        NicoSituation("primera_compra", "¡Buena decisión! Ya tienes tu primer producto."),
        NicoSituation("primera_venta", "¡Vendiste tu primer producto! Así se gana en la tienda."),
        NicoSituation("stock_bajo", "Ojo: a este producto le quedan pocas unidades."),
        NicoSituation("stock_agotado", "Este producto se agotó. Puedes ir al almacén a comprar más."),
        NicoSituation("precio_sin_ganancia", "Si vendes al mismo precio que compraste, no ganas monedas extra."),
        NicoSituation("precio_con_ganancia", "¡Bien pensado! Vender más caro que el costo te da ganancia."),
        NicoSituation("saldo_insuficiente", "Todavía no tienes monedas suficientes para esto."),
        NicoSituation("mejora_aplicada", "¡Tu tienda se ve mejor con esta mejora!"),
        NicoSituation("insignia_desbloqueada", "¡Desbloqueaste una insignia nueva! Sigue así."),
        NicoSituation("nivel_subido", "¡Subiste de nivel! Tu tienda sigue creciendo."),
        NicoSituation("cliente_esperando", "Un cliente está esperando en el mostrador."),
        NicoSituation("venta_sin_stock", "No puedes vender lo que no tienes en la tienda todavía."),
        NicoSituation("reto_completado", "¡Reto superado! Aprendiste algo nuevo sobre tu tienda."),
        NicoSituation("cierre_sesion", "¡Buen trabajo hoy! Tu tienda te espera para la próxima visita.")
    )
}

data class NicoSituation(val id: String, val message: String)
