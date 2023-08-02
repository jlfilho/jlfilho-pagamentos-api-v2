CREATE TABLE `categoria` (
  `codigo` bigint NOT NULL AUTO_INCREMENT,
  `nome` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO categoria VALUES (1,'Tecnologia');
INSERT INTO categoria VALUES (2,'Moda');
INSERT INTO categoria VALUES (3,'Saúde');
INSERT INTO categoria VALUES (4,'Educação');
INSERT INTO categoria VALUES (5,'Viagem');
INSERT INTO categoria VALUES (6,'Esportes');
INSERT INTO categoria VALUES (7,'Entretenimento');
INSERT INTO categoria VALUES (8,'Arte');
INSERT INTO categoria VALUES (9,'Automóveis');
INSERT INTO categoria VALUES (10,'Beleza');
INSERT INTO categoria VALUES (11,'Decoração');
INSERT INTO categoria VALUES (12,'Eletrônicos');
INSERT INTO categoria VALUES (13,'Finanças');
INSERT INTO categoria VALUES (14,'Jogos');
INSERT INTO categoria VALUES (15,'Música');
INSERT INTO categoria VALUES (16,'Livros');
INSERT INTO categoria VALUES (17,'Animais de Estimação');
INSERT INTO categoria VALUES (18,'Trabalho');
INSERT INTO categoria VALUES (19,'Hobbies');
INSERT INTO categoria VALUES (20,'Culinária');