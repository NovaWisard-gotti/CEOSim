-- ============================================================
-- CEOSim - Datos de ejemplo (reflejan SeedData.kt)
-- ============================================================

-- ---------- Productos (20) ----------
INSERT INTO products (id, name, category, buyCost, defaultSellPrice, unlockLevel) VALUES
('jugo_naranja', 'Jugo de naranja', 'BEBIDA', 5, 8, 1),
('jugo_manzana', 'Jugo de manzana', 'BEBIDA', 5, 8, 1),
('agua_fresca', 'Agua fresca', 'BEBIDA', 4, 6, 1),
('manzana_roja', 'Manzana roja', 'FRUTA', 3, 5, 1),
('platano', 'Plátano', 'FRUTA', 3, 5, 1),
('naranja_fruta', 'Naranja', 'FRUTA', 3, 5, 1),
('cuaderno_rayado', 'Cuaderno rayado', 'PAPELERIA', 6, 9, 1),
('lapiz_grafito', 'Lápiz de grafito', 'PAPELERIA', 2, 4, 1),
('goma_borrar', 'Goma de borrar', 'PAPELERIA', 2, 3, 1),
('colores_caja', 'Caja de colores', 'PAPELERIA', 8, 12, 2),
('pelota_futbol', 'Pelota de fútbol', 'JUGUETE', 10, 15, 1),
('trompo_madera', 'Trompo de madera', 'JUGUETE', 6, 9, 2),
('carrito_juguete', 'Carrito de juguete', 'JUGUETE', 9, 14, 2),
('galleta_avena', 'Galleta de avena', 'GALLETA', 3, 5, 1),
('galleta_chocolate', 'Galleta de chocolate', 'GALLETA', 4, 6, 1),
('planta_maceta', 'Planta en maceta', 'PLANTA', 7, 11, 2),
('cactus_mini', 'Cactus mini', 'PLANTA', 6, 9, 2),
('cuerda_saltar', 'Cuerda para saltar', 'DEPORTE', 5, 8, 2),
('gorra_deportiva', 'Gorra deportiva', 'DEPORTE', 8, 12, 3),
('libro_cuentos', 'Libro de cuentos', 'LIBRO', 7, 11, 3);

-- ---------- Clientes (10) ----------
INSERT INTO customers (id, name, avatar, greeting) VALUES
('cli_camila', 'Camila', 'NINA_TRENZAS', '¡Hola! ¿Tienes algo rico hoy?'),
('cli_mateo', 'Mateo', 'NINO_LENTES', 'Buen día, ando buscando algo útil.'),
('cli_rosa', 'Doña Rosa', 'ABUELA_BUFANDA', 'Hola, pequeño tendero, ¿me ayudas?'),
('cli_pedro', 'Don Pedro', 'ABUELO_SOMBRERO', '¡Qué tienda tan ordenada!'),
('cli_valentina', 'Valentina', 'NINA_GORRA', '¡Vine corriendo por algo especial!'),
('cli_diego', 'Diego', 'NINO_CHALECO', 'Necesito algo para hoy, ¿tienes?'),
('cli_robo', 'Robo', 'ROBOT_AMIGABLE', 'BEEP. Solicitando producto disponible.'),
('cli_michi', 'Michi', 'GATO_CLIENTE', 'Miau, quiero algo delicioso.'),
('cli_sofia', 'Sofía', 'NINA_PATINETA', '¡Hola! Paso rapidito por algo.'),
('cli_rex', 'Rex', 'PERRO_CLIENTE', '¡Guau! Vine a visitar tu tienda.');

-- ---------- Mejoras de tienda (10) ----------
INSERT INTO store_upgrades (id, name, category, cost, unlockLevel, description) VALUES
('estante_madera', 'Estante de madera', 'ESTANTE', 20, 1, 'Un estante nuevo para ordenar más productos.'),
('maceta_decorativa', 'Maceta decorativa', 'DECORACION', 15, 1, 'Una planta que alegra la entrada de la tienda.'),
('cartel_bienvenida', 'Cartel de bienvenida', 'CARTEL', 18, 1, 'Un cartel que saluda a los clientes.'),
('lampara_calida', 'Lámpara cálida', 'ILUMINACION', 25, 2, 'Ilumina mejor los estantes de tu tienda.'),
('alfombra_colorida', 'Alfombra colorida', 'DECORACION', 22, 2, 'Le da un toque alegre a la entrada.'),
('estante_metal', 'Estante de metal', 'ESTANTE', 35, 2, 'Más resistente y con más espacio.'),
('mostrador_nuevo', 'Mostrador renovado', 'MOSTRADOR', 40, 2, 'Un mostrador más grande para atender mejor.'),
('cartel_ofertas', 'Cartel de ofertas', 'CARTEL', 20, 2, 'Anuncia tus productos destacados.'),
('luces_decorativas', 'Luces decorativas', 'ILUMINACION', 30, 3, 'Le dan un brillo especial a tu tienda.'),
('mostrador_lujo', 'Mostrador de lujo', 'MOSTRADOR', 60, 3, 'El mostrador más elegante de la ciudad.');

-- ---------- Retos (10) ----------
INSERT INTO challenges (id, title, narrative, type, targetProductId, targetQuantity, xpReward) VALUES
('reto_pocos_jugos', 'Pocos jugos', 'Parece que se están acabando los jugos. ¿Qué puedes hacer?', 'REABASTECER', 'jugo_naranja', 3, 10),
('reto_cliente_pelota', 'Un cliente especial', 'Un cliente quiere una pelota. ¿Tienes suficientes en la tienda?', 'ATENDER_CLIENTE', 'pelota_futbol', 1, 10),
('reto_precio_cuaderno', 'Precio justo', 'Revisa el precio de venta de tus cuadernos.', 'AJUSTAR_PRECIO', 'cuaderno_rayado', 1, 10),
('reto_mejorar_estante', 'Más espacio', 'Tu tienda necesita más espacio para productos.', 'MEJORAR_TIENDA', NULL, 1, 10),
('reto_ahorro_50', 'Meta de ahorro', 'Intenta juntar 50 monedas en tu caja.', 'AHORRAR', NULL, 50, 10),
('reto_pocas_galletas', 'Galletas escasas', 'Las galletas de chocolate se están agotando.', 'REABASTECER', 'galleta_chocolate', 3, 10),
('reto_cliente_libro', 'Cliente lector', 'Un cliente busca un buen libro de cuentos.', 'ATENDER_CLIENTE', 'libro_cuentos', 1, 10),
('reto_precio_pelota', 'Ganancia justa', 'Revisa si el precio de la pelota deja ganancia.', 'AJUSTAR_PRECIO', 'pelota_futbol', 1, 10),
('reto_mejorar_cartel', 'Tienda más visible', 'Un cartel ayudaría a que más clientes te visiten.', 'MEJORAR_TIENDA', NULL, 1, 10),
('reto_ahorro_100', 'Gran ahorro', 'Intenta juntar 100 monedas en tu caja.', 'AHORRAR', NULL, 100, 10);

-- ---------- Insignias (12) ----------
INSERT INTO badges (id, title, description) VALUES
('primera_compra', 'Primera Compra', 'Compraste tu primer producto para la tienda.'),
('primera_venta', 'Primera Venta', 'Vendiste tu primer producto a un cliente.'),
('tienda_organizada', 'Tienda Organizada', 'Tienes 5 productos distintos ordenados en tus estantes.'),
('buen_vendedor', 'Buen Vendedor', 'Completaste 10 ventas en tu tienda.'),
('inventario_completo', 'Inventario Completo', 'Tienes 10 productos distintos disponibles a la vez.'),
('ahorrador', 'Ahorrador', 'Llegaste a juntar 100 monedas en tu caja.'),
('gran_organizador', 'Gran Organizador', 'Tienes 8 productos distintos ordenados en tus estantes.'),
('primera_mejora', 'Primera Mejora', 'Compraste tu primera mejora para la tienda.'),
('tienda_popular', 'Tienda Popular', 'Completaste 25 ventas: ¡tu tienda es conocida!'),
('buen_administrador', 'Buen Administrador', 'Superaste 5 pequeños retos de la tienda.'),
('gran_emprendedor', 'Gran Emprendedor', '50 ventas y 5 mejoras: tu tienda crece de verdad.'),
('maestro_ceosim', 'Maestro CEOSim', 'Desbloqueaste todas las demás insignias de la tienda.');

-- ---------- Estado inicial de una partida nueva ----------
INSERT INTO user_profile (id, alias, avatarId, soundEnabled, hapticEnabled, onboardingCompleted, createdAt)
VALUES (1, 'Capi', 1, 1, 1, 0, strftime('%s','now') * 1000);

INSERT INTO store (id, storeName, balance, maxBalanceReached) VALUES (1, 'Mi Pequeña Tienda', 50, 50);

INSERT INTO progress (id, totalXp, level, updatedAt) VALUES (1, 0, 1, strftime('%s','now') * 1000);

-- Inventario inicial: todo en cero hasta que el niño compre en el Almacén.
INSERT INTO inventory (productId, quantity) SELECT id, 0 FROM products;

-- Precios iniciales = precio de venta sugerido por defecto.
INSERT INTO product_prices (productId, sellPrice) SELECT id, defaultSellPrice FROM products;

-- ---------- Ejemplo de historial (partida ya avanzada, solo referencia) ----------
-- INSERT INTO purchases (productId, quantity, unitCost, totalCost, timestamp)
-- VALUES ('jugo_naranja', 3, 5, 15, strftime('%s','now') * 1000);
--
-- INSERT INTO sales (productId, customerId, quantity, unitPrice, totalEarned, timestamp)
-- VALUES ('jugo_naranja', 'cli_camila', 1, 8, 8, strftime('%s','now') * 1000);
