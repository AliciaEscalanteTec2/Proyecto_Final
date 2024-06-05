CREATE DATABASE bdzoo;
use bdzoo;
/*
CUANDO TENEMOS LOS DESPLEGABLES DE LOS COMBOS, TENEMOS DIFERENTES OPCIONES Y COMO REQUISITO PARA GUARDAR LA INFORMACIÓN
DEL USUARIO TENEMOS QUE INCORPORAR LAS OPCIONES, TENEMOS QUE HACER TABLAS "RELACIONALES" Y CREARLAS DE ADENTRO HACIA FUERA.
SE CREAN PRIMERO LAS TABLAS DEPENDIENTES QUE EN ESTE CASO ES "GENERO, FAMILIA Y NAMENAZA". LOS COMBOS SON TABLAS INDEPENDIENTES Y 
LA TABLA "ANIMALES" ES LA TABLA DEPENDIENTE (DEPENDE DE LOS CAMPOS QUE EXISTAN EN LAS OTRAS TABLAS).
*/

/*
CUANDO PONEMOS EL "ID" EN AUTOINCREMENTABLE SIGNIFICA QUE ESTE ID SE VA A IR INCREMENTANDO DE UNO EN UNO Y ES LLAVE PRIMARIA, QUE 
SIGNIFICA QUE NO PUEDE EXISTIR DOS REGISTROS CON EL MISMO ID.
*/
CREATE TABLE genero(
id int auto_increment not null primary key,
genero varchar(50)
);
/*
PARA EL INSERT NOS REFERIMOS QUE VAMOS A INSERTAR EN LA TABLA DE GENERO, EN EL CAMPO LLAMADO GENERO CON LOS VALORES "MACHO Y HEMBRA".
*/
INSERT INTO genero(genero) VALUES ("Macho");
INSERT INTO genero(genero) VALUES ("Hembra");

CREATE TABLE familia(
id int auto_increment not null primary key,
familia varchar(50)
);
INSERT INTO familia(familia) VALUES ("Reptiles");
INSERT INTO familia(familia) VALUES ("Antropodos");
INSERT INTO familia(familia) VALUES ("Aves");
INSERT INTO familia(familia) VALUES ("Felinos");
INSERT INTO familia(familia) VALUES ("Anfibios");
INSERT INTO familia(familia) VALUES ("Peces");
INSERT INTO familia(familia) VALUES ("Mamiferos acuaticos");
INSERT INTO familia(familia) VALUES ("Mamimeros terrestres");
INSERT INTO familia(familia) VALUES ("Caninos");
INSERT INTO familia(familia) VALUES ("Marsupiales");
INSERT INTO familia(familia) VALUES ("Roedores");

CREATE TABLE namenaza(
id int auto_increment not null primary key,
namenaza varchar(50)
);
INSERT INTO namenaza(namenaza) VALUES ("Bajo");
INSERT INTO namenaza(namenaza) VALUES ("Medio");

/*
CUANDO SE PONE EL "FK" NOS REFERIMOS A UNA LLAVE FORANEA QUE DEBE SER EL MISMO TIPO DE DATO DE LA LLAVE PRIMARIA QUE EN ESTE CASO
ES EL "ID". POR MEDIO DEL ID ES QUE SE RELACIONA LA TABLA. UNA LLAVE FORANEA ES CONECTAR UNA TABLA MEDIANTE A OTRA.
*/
CREATE TABLE animales(
id int auto_increment not null primary key, 
especie VARCHAR(100), 
nombre VARCHAR(100),
edad int,
peso int,
altura int,
fkfamilia int,
fknamenaza int,
fkgenero int,
habitatant VARCHAR(100),
fingreso date,
foto longblob, /* EL BLOB EN LA FOTO ES PARA GUARDAR ARCHIVOS PERO PEQUEÑAS Y ESPACIO LIMITADO. EL LONGBLOB ES PARA IMAGENES GRANDES, CHICAS Y MEDIANAS.*/
/*
SE LLAMAN A LAS LLAVES PRIMARIAS Y SE REFERENCIA EL NOMBRE DE LA TABLA CON QUE SE VA A UNIR Y EL ID. TAMBIEN SE PONE QUE SE VA ELIMINAR EN CASCADA EN 
CASO DE QUE SE MODIFIQUEN LOS DATOS O SE ELIMINEN. ESTO QUIERE DECIR QUE EN CASO DE YO BORRAR UN ELEMENTO, SE VA A BORRAR TAMBIEN TODOS LOS ELEMENTOS RELACIONADOS
CON ESE MISMA REFERENCIA O DATO. 
*/
foreign key (fkfamilia) references familia(id) ON DELETE CASCADE ON UPDATE CASCADE,
foreign key (fknamenaza) references namenaza(id) ON DELETE CASCADE ON UPDATE CASCADE,
foreign key (fkgenero) references genero(id) ON DELETE CASCADE ON UPDATE CASCADE
);
INSERT INTO animales (especie, nombre, edad, peso, altura, fkfamilia, fknamenaza, fkgenero, habitatant, fingreso, foto ) VALUES ("Oso", "Oso sonso", 4, 140, 2.10,8,2,1,"Africa","01/01/2020", "foto");

select animales.id, animales.especie, animales.nombre, animales.edad, animales.peso, animales.altura, familia.familia, namenaza.namenaza, genero.genero, animales.habitatant, animales.fingreso, animales.foto 
from animales INNER JOIN familia ON animales.fkfamilia = familia.id INNER JOIN namenaza ON animales.fknamenaza = namenaza.id 
INNER JOIN genero ON animales.fkgenero = genero.id;

UPDATE animales SET animales.especie = ?, animales.nombre = ? , animales.edad = ?, animales.peso = ?, animales.altura = ?, animales.familia = ?, animales.namenaza = ?, animales.genero = ?, animales.habitatant = ?, animales.fingreso = ?, animales.foto =?
WHERE animales.id = ?;

DELETE FROM animales WHERE animales.id=?;
