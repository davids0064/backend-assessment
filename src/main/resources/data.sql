-- ==========================================================
-- 1. TABLA: PARQUEADERO
-- ==========================================================
INSERT INTO parqueadero (id_parqueadero, nombre_parqueadero, ciudad_parqueadero)
VALUES (1, 'Paymeter Central Station', 'Medellín');

-- ==========================================================
-- 2. TABLA: REGLAS_DESCUENTO
-- Configuración de topes, ciclos y beneficios
-- ==========================================================
-- Regla 1: Cliente 1 (Tope 15€ cada 24h, sin cortesía)
INSERT INTO reglas_descuento (id_regla, valor_max_regla, ciclo_hora_regla, cortesia_regla, estado_regla)
VALUES (1, 15, 24, FALSE, 'ACTIVO');

-- Regla 2: Cliente 2 (Tope 20€ cada 12h, con 1h de cortesía)
INSERT INTO reglas_descuento (id_regla, valor_max_regla, ciclo_hora_regla, cortesia_regla, estado_regla)
VALUES (2, 20, 12, TRUE, 'ACTIVO');

-- ==========================================================
-- 3. TABLA: PRECIO
-- Costo base por hora vinculado a una regla
-- ==========================================================
-- Precio para Cliente 1: 2€/h
INSERT INTO precio (id_precio, detalle_precio, estado_precio, id_regla)
VALUES (10, 2, 'ACTIVO', 1);

-- Precio para Cliente 2: 3€/h
INSERT INTO precio (id_precio, detalle_precio, estado_precio, id_regla)
VALUES (20, 3, 'ACTIVO', 2);

-- ==========================================================
-- 4. TABLA: ESTACIONAMIENTO
-- Puntos de entrada para la API
-- ==========================================================
-- Punto de venta P000123 (Usa Precio 10 -> Regla 1)
INSERT INTO estacionamiento (cod_estacionamiento, id_parqueadero, id_precio, estado_estacionamiento)
VALUES ('P000123', 1, 10, 'DISPONIBLE');

-- Punto de venta P000456 (Usa Precio 20 -> Regla 2)
INSERT INTO estacionamiento (cod_estacionamiento, id_parqueadero, id_precio, estado_estacionamiento)
VALUES ('P000456', 1, 20, 'DISPONIBLE');

-- Caso de prueba para estacionamiento no disponible
INSERT INTO estacionamiento (cod_estacionamiento, id_parqueadero, id_precio, estado_estacionamiento)
VALUES ('P000999', 1, 10, 'RESERVADO');
