USE db_atencion;
CREATE TABLE atencion (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cita_id BIGINT NOT NULL,
    fecha_atencion DATETIME NOT NULL,
    estado VARCHAR(20) NOT NULL
);

USE db_pago;
CREATE TABLE pago (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    atencion_id BIGINT NOT NULL,
    monto DECIMAL(10,2) NOT NULL,
    fecha_pago DATETIME NOT NULL,
    estado VARCHAR(20) NOT NULL
);