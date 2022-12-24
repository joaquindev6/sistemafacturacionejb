CREATE DATABASE bd_facturacion;

USE bd_facturacion;

CREATE TABLE tbl_usuarios(
id BIGINT AUTO_INCREMENT NOT NULL,
codigo CHAR(10) UNIQUE NOT NULL,
nombres VARCHAR(80) NOT NULL,
ape_paterno VARCHAR(30) NOT NULL,
ape_materno VARCHAR(30) NOT NULL,
celular CHAR(9) NOT NULL,
email VARCHAR(80) UNIQUE NOT NULL,
username VARCHAR(30) UNIQUE NOT NULL,
password VARCHAR(30) NOT NULL,
estado BIT NOT NULL,
fecha_reg DATE NOT NULL COMMENT 'Fecha de registro',
user_reg VARCHAR(45) NOT NULL COMMENT 'Usuario que registró',
fecha_mod DATE COMMENT 'Fecha de modificación',
user_mod VARCHAR(45) COMMENT 'Usuario que modificó',
PRIMARY KEY(id)
);
INSERT INTO tbl_usuarios(codigo, nombres, ape_paterno, ape_materno, celular, email, username, password, estado, fecha_reg, user_reg)
VALUES('USU-000001', 'JOAQUIN SEBASTIAN', 'FARRO', 'QUIROZ', '933057428', 'JOAQUIN@GMAIL.COM', 'admin', 'admin', 1, '2022-12-23', 'JOAQUIN FARRO');

CREATE TABLE tbl_categorias(
id BIGINT AUTO_INCREMENT NOT NULL,
codigo CHAR(10) UNIQUE NOT NULL,
nombre VARCHAR(45) NOT NULL,
estado BIT NOT NULL,
fecha_reg DATE NOT NULL COMMENT 'Fecha de registro',
user_reg VARCHAR(45) NOT NULL COMMENT 'Usuario que registró',
fecha_mod DATE COMMENT 'Fecha de modificación',
user_mod VARCHAR(45) COMMENT 'Usuario que modificó',
PRIMARY KEY(id)
);

CREATE TABLE tbl_productos(
id BIGINT AUTO_INCREMENT NOT NULL,
codigo CHAR(10) UNIQUE NOT NULL,
nombre VARCHAR(120) NOT NULL,
cantidad INT NOT NULL,
precio DECIMAL(12,2) NOT NULL,
id_categoria BIGINT NOT NULL,
estado BIT NOT NULL,
fecha_reg DATE NOT NULL COMMENT 'Fecha de registro',
user_reg VARCHAR(45) NOT NULL COMMENT 'Usuario que registró',
fecha_mod DATE COMMENT 'Fecha de modificación',
user_mod VARCHAR(45) COMMENT 'Usuario que modificó',
PRIMARY KEY(id)
);

/* Varios productos puede tener varias categorias */
CREATE TABLE tbl_productos_categorias(
id_producto BIGINT NOT NULL,
id_categoria BIGINT NOT NULL,
FOREIGN KEY(id_producto) REFERENCES tbl_productos(id),
FOREIGN KEY(id_categoria) REFERENCES tbl_categorias(id)
);

CREATE TABLE tbl_tipo_documento(
id INT AUTO_INCREMENT NOT NULL,
codigo CHAR(2) NOT NULL,
nombre VARCHAR(45) NOT NULL,
PRIMARY KEY(id)
);
INSERT INTO tbl_tipo_documento(codigo, nombre) VALUES('1', 'DOCUMENTO NACIONAL DE IDENTIDAD');
INSERT INTO tbl_tipo_documento(codigo, nombre) VALUES('4', 'CARNET DE EXTRANJERIA');
INSERT INTO tbl_tipo_documento(codigo, nombre) VALUES('6', 'REGISTRO UNICO DE CONTRIBUYENTES');
INSERT INTO tbl_tipo_documento(codigo, nombre) VALUES('7', 'PASAPORTE');

CREATE TABLE tbl_clientes(
id BIGINT AUTO_INCREMENT NOT NULL,
codigo CHAR(10) UNIQUE NOT NULL,
nombres VARCHAR(80) NOT NULL,
ape_paterno VARCHAR(30) NOT NULL,
ape_materno VARCHAR(30) NOT NULL,
nro_doc CHAR(20) UNIQUE NOT NULL,
id_tipo_documento INT NOT NULL,
celular CHAR(9) NOT NULL,
email VARCHAR(80) UNIQUE NOT NULL,
direccion TEXT NOT NULL,
estado CHAR(10) NOT NULL,
fecha_reg DATE NOT NULL COMMENT 'Fecha de registro',
user_reg VARCHAR(45) NOT NULL COMMENT 'Usuario que registró',
fecha_mod DATE COMMENT 'Fecha de modificación',
user_mod VARCHAR(45) COMMENT 'Usuario que modificó',
PRIMARY KEY(id),
FOREIGN KEY(id_tipo_documento) REFERENCES tbl_tipo_documento(id)
);

CREATE TABLE tbl_facturas(
id BIGINT AUTO_INCREMENT NOT NULL,
serie CHAR(4) NOT NULl,
numero CHAR(8) NOT NULL,
nombre VARCHAR(120) NOT NULL,
fecha DATETIME NOT NULL, 
id_cliente BIGINT NOT NULL,
id_usuario BIGINT NOT NULl,
PRIMARY KEY(id),
FOREIGN KEY(id_cliente) REFERENCES tbl_clientes(id),
FOREIGN KEY(id_usuario) REFERENCES tbl_usuarios(id)
);

CREATE TABLE tbl_facturas_det(
id BIGINT AUTO_INCREMENT NOT NULL,
cantidad INT NOT NULL,
precio_unit DECIMAL(12,2) NOT NULL,
subtotal DECIMAL(12,2) NOT NULL,
id_producto BIGINT NOT NULL,
id_factura BIGINT NOT NULL,
PRIMARY KEY(id),
FOREIGN KEY(id_producto) REFERENCES tbl_productos(id),
FOREIGN KEY(id_factura) REFERENCES tbl_facturas(id)
);
