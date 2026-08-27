-- ============================================================
-- CEOSim - Esquema de base de datos (SQLite / Room)
-- Refleja exactamente las 17 entidades definidas en
-- app/src/main/kotlin/com/educalab/ceosim/data/local/entity/
-- ============================================================

PRAGMA foreign_keys = ON;

-- ---------- Catálogo (datos semilla, prácticamente de solo lectura) ----------

CREATE TABLE IF NOT EXISTS products (
    id                TEXT PRIMARY KEY NOT NULL,
    name              TEXT NOT NULL,
    category          TEXT NOT NULL,   -- ProductCategory
    buyCost           INTEGER NOT NULL,
    defaultSellPrice  INTEGER NOT NULL,
    unlockLevel       INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS customers (
    id        TEXT PRIMARY KEY NOT NULL,
    name      TEXT NOT NULL,
    avatar    TEXT NOT NULL,           -- CustomerAvatar
    greeting  TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS store_upgrades (
    id            TEXT PRIMARY KEY NOT NULL,
    name          TEXT NOT NULL,
    category      TEXT NOT NULL,       -- UpgradeCategory
    cost          INTEGER NOT NULL,
    unlockLevel   INTEGER NOT NULL,
    description   TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS challenges (
    id                TEXT PRIMARY KEY NOT NULL,
    title             TEXT NOT NULL,
    narrative         TEXT NOT NULL,
    type              TEXT NOT NULL,   -- ChallengeType
    targetProductId   TEXT,
    targetQuantity    INTEGER NOT NULL,
    xpReward          INTEGER NOT NULL,
    FOREIGN KEY (targetProductId) REFERENCES products(id)
);

CREATE TABLE IF NOT EXISTS badges (
    id            TEXT PRIMARY KEY NOT NULL,
    title         TEXT NOT NULL,
    description   TEXT NOT NULL
);

-- ---------- Estado actual (filas "singleton" para profile/store/progress) ----------

CREATE TABLE IF NOT EXISTS user_profile (
    id                    INTEGER PRIMARY KEY NOT NULL DEFAULT 1,
    alias                 TEXT NOT NULL,
    avatarId              INTEGER NOT NULL,
    soundEnabled          INTEGER NOT NULL DEFAULT 1,
    hapticEnabled         INTEGER NOT NULL DEFAULT 1,
    onboardingCompleted   INTEGER NOT NULL DEFAULT 0,
    createdAt             INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS store (
    id                  INTEGER PRIMARY KEY NOT NULL DEFAULT 1,
    storeName           TEXT NOT NULL,
    balance             INTEGER NOT NULL,
    maxBalanceReached   INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS inventory (
    productId   TEXT PRIMARY KEY NOT NULL,
    quantity    INTEGER NOT NULL,
    FOREIGN KEY (productId) REFERENCES products(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS product_prices (
    productId   TEXT PRIMARY KEY NOT NULL,
    sellPrice   INTEGER NOT NULL,
    FOREIGN KEY (productId) REFERENCES products(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS progress (
    id          INTEGER PRIMARY KEY NOT NULL DEFAULT 1,
    totalXp     INTEGER NOT NULL,
    level       INTEGER NOT NULL,
    updatedAt   INTEGER NOT NULL
);

-- ---------- Historial (append-only) ----------

CREATE TABLE IF NOT EXISTS purchases (
    id          INTEGER PRIMARY KEY AUTOINCREMENT,
    productId   TEXT NOT NULL,
    quantity    INTEGER NOT NULL,
    unitCost    INTEGER NOT NULL,
    totalCost   INTEGER NOT NULL,
    timestamp   INTEGER NOT NULL,
    FOREIGN KEY (productId) REFERENCES products(id)
);
CREATE INDEX IF NOT EXISTS idx_purchases_product ON purchases(productId);

CREATE TABLE IF NOT EXISTS sales (
    id            INTEGER PRIMARY KEY AUTOINCREMENT,
    productId     TEXT NOT NULL,
    customerId    TEXT,
    quantity      INTEGER NOT NULL,
    unitPrice     INTEGER NOT NULL,
    totalEarned   INTEGER NOT NULL,
    timestamp     INTEGER NOT NULL,
    FOREIGN KEY (productId) REFERENCES products(id),
    FOREIGN KEY (customerId) REFERENCES customers(id)
);
CREATE INDEX IF NOT EXISTS idx_sales_product ON sales(productId);
CREATE INDEX IF NOT EXISTS idx_sales_customer ON sales(customerId);

CREATE TABLE IF NOT EXISTS customer_requests (
    id           INTEGER PRIMARY KEY AUTOINCREMENT,
    customerId   TEXT NOT NULL,
    productId    TEXT NOT NULL,
    quantity     INTEGER NOT NULL,
    fulfilled    INTEGER NOT NULL,
    timestamp    INTEGER NOT NULL,
    FOREIGN KEY (customerId) REFERENCES customers(id),
    FOREIGN KEY (productId) REFERENCES products(id)
);
CREATE INDEX IF NOT EXISTS idx_requests_customer ON customer_requests(customerId);
CREATE INDEX IF NOT EXISTS idx_requests_product ON customer_requests(productId);

CREATE TABLE IF NOT EXISTS user_upgrades (
    id            INTEGER PRIMARY KEY AUTOINCREMENT,
    upgradeId     TEXT NOT NULL UNIQUE,
    purchasedAt   INTEGER NOT NULL,
    FOREIGN KEY (upgradeId) REFERENCES store_upgrades(id)
);

CREATE TABLE IF NOT EXISTS challenge_attempts (
    id            INTEGER PRIMARY KEY AUTOINCREMENT,
    challengeId   TEXT NOT NULL,
    completed     INTEGER NOT NULL,
    completedAt   INTEGER,
    FOREIGN KEY (challengeId) REFERENCES challenges(id)
);
CREATE INDEX IF NOT EXISTS idx_attempts_challenge ON challenge_attempts(challengeId);

CREATE TABLE IF NOT EXISTS user_badges (
    id           INTEGER PRIMARY KEY AUTOINCREMENT,
    badgeId      TEXT NOT NULL UNIQUE,
    unlockedAt   INTEGER NOT NULL,
    FOREIGN KEY (badgeId) REFERENCES badges(id)
);

CREATE TABLE IF NOT EXISTS transactions (
    id             INTEGER PRIMARY KEY AUTOINCREMENT,
    type           TEXT NOT NULL,   -- COMPRA | VENTA | MEJORA
    amount         INTEGER NOT NULL,
    balanceAfter   INTEGER NOT NULL,
    description    TEXT NOT NULL,
    timestamp      INTEGER NOT NULL
);

-- ============================================================
-- Consultas de ejemplo (usadas conceptualmente por el repositorio)
-- ============================================================

-- Productos con stock actual y precio de venta vigente:
-- SELECT p.id, p.name, i.quantity, pp.sellPrice
-- FROM products p
-- LEFT JOIN inventory i ON i.productId = p.id
-- LEFT JOIN product_prices pp ON pp.productId = p.id;

-- Insignias desbloqueadas con su fecha:
-- SELECT b.title, ub.unlockedAt
-- FROM user_badges ub JOIN badges b ON b.id = ub.badgeId
-- ORDER BY ub.unlockedAt DESC;

-- Total de ventas y compras (para las estadísticas de La Caja):
-- SELECT
--   (SELECT COUNT(*) FROM sales) AS total_ventas,
--   (SELECT COUNT(*) FROM purchases) AS total_compras;
