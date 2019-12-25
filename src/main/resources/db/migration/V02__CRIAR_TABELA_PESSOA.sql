CREATE TABLE pessoa
(
    codigo     BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    nome       VARCHAR(250) NOT NULL,
    ativo      tinyint(1) NOT NULL,
    logradouro VARCHAR(100),
    numero     VARCHAR(10),
    bairro     VARCHAR(150),
    cep        VARCHAR(10),
    cidade     VARCHAR(150),
    estado     VARCHAR(100)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

