#Borrado y creación de la base de datos
DROP DATABASE IF EXISTS dbHearthStoneInfo;
CREATE DATABASE dbHearthStoneInfo CHARACTER SET UTF8 COLLATE UTF_GENERAL_CI;
USE dbHearthStoneInfo;

#Creación de las tablas
#Tabla para almacenar los usuarios y contraseñas
CREATE TABLE T_USUARIOS
(
Nombre VARCHAR(20),
Password VARCHAR(20),
CONSTRAINT pk_t_usuarios_nombre(Nombre)
)

#Tabla para almacenar los mazos de los usuarios
CREATE TABLE T_MAZOS
(
ID INT,
Nombre VARCHAR(20),
NombreUsuario VARCHAR(20),
CONSTRAINT pk_t_mazos_id PRIMARY KEY(ID),
CONSTRAINT fk_t_mazos_nombreusuario FOREIGN KEY (NombreUsuario) REFERENCES T_USUARIOS (Nombre)
)

#Tabla que almacena las id de cartas
CREATE TABLE T_CARTAS
(
ID VARCHAR(20),
CONSTRAINT pk_t_cartas_id PRIMARY KEY (ID)
)

#Tabla que almacena los mazos y sus cartas
CREATE TABLE T_MAZO_CARTAS
(
IdMazo INT,
IdCarta VARCHAR(10),
CONSTRAINT pk_t_mazo_cartas_idmazo_idcarta PRIMARY KEY (IdMazo, IdCarta),
CONSTRAINT fk_t_mazo_cartas_idmazo FOREIGN KEY (IdMazo) REFERENCES T_MAZOS(ID),
CONSTRAINT fk_t_mazo_cartas_idcarta FOREIGN KEY (IdCarta) REFERENCES T_CARTAS(ID)

)

#Tabla que almacena las cartas favoritas de cada usuario
CREATE TABLE T_FAVORITOS
(
NombreUsuario VARCHAR(20),
IdCarta VARCHAR(20),
CONSTRAINT pk_t_favoritos_nombreusuario_idcarta PRIMARY KEY (NombreUsuario, IdCarta),
CONSTRAINT fk_t_favoritos_nombreusuario FOREIGN KEY (NombreUsuario) REFERENCES T_USUARIOS(Nombre),
CONSTRAINT fk_t_favoritos_idcarta FOREIGN KEY (IdCarta) REFERENCES T_CARTAS(ID)
)










