CREATE TABLE `usuario_permissao` (
  `codigo_usuario` bigint NOT NULL,
  `codigo_permissao` bigint NOT NULL,
  PRIMARY KEY (`codigo_usuario`,`codigo_permissao`),
  KEY `FK5tjrvuwlx1yp72mrf8t8vj93e` (`codigo_permissao`),
  CONSTRAINT `FK5tjrvuwlx1yp72mrf8t8vj93e` FOREIGN KEY (`codigo_permissao`) REFERENCES `permissao` (`codigo`),
  CONSTRAINT `FKeogfr4akeqn19xr3wmyx0n8bo` FOREIGN KEY (`codigo_usuario`) REFERENCES `usuario` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `usuario_permissao` VALUES (1,1),(1,2),(2,2),(1,3),(1,4),(1,5),(1,6),(2,6),(1,7),(1,8),(1,9),(1,10),(2,10),(1,11),(1,12),(1,13),(1,14),(1,15),(1,16),(1,17),(1,18),(1,19),(1,20);
