-- hipotecas.Usuario definition

CREATE TABLE `Usuario` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `contraseña` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- hipotecas.Hipoteca definition

CREATE TABLE `Hipoteca` (
  `importe` double NOT NULL,
  `interes` double NOT NULL,
  `plazo` int NOT NULL,
  `id` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

ALTER TABLE hipotecas.Hipoteca ADD CONSTRAINT Hipoteca_Usuario_FK FOREIGN KEY (usuario_id) REFERENCES hipotecas.Usuario(id);