USE `SISDI`;
DELIMITER ;
CREATE TABLE IF NOT EXISTS `SISDI`.`T_DELETEDOFFICES`(
OFFICENUMBER varchar(45) NOT NULL,
USER varchar(45) NOT NULL,
Date DATETIME NOT NULL	
);
USE `SISDI`;
DROP procedure IF EXISTS insertOffice;
DELIMITER $$
USE `SISDI`$$
CREATE PROCEDURE insertOffice (IN offNumber VARCHAR(45),IN reason VARCHAR(45),IN name VARCHAR(45),
 IN incorDate DATE, IN incorTime TIME, 
IN deadLine DATE, IN sessionDate DATE,  IN observations longtext, IN publics TINYINT(4),
IN notified TINYINT(4), IN states INT,
IN type_id int, IN user_id VARCHAR(45),IN receiver_id VARCHAR(45),timeouts_id INT
)

BEGIN
INSERT INTO T_OFFICE (OFFNUMBER,REASON,NAME, INCORDATE,INCORTIME, 
DEADLINE, SESSIONDATE, OBSERVATIONS, PUBLIC, NOTIFIED,  STATE, TYPE_ID, USER_ID,RECEIVER_ID,TIMEOUTS_ID) 
VALUES (offNumber, reason, name, incorDate, incorTime, deadLine,
 sessionDate, observations, publics, notified, states, type_id, user_id ,receiver_id,timeouts_id);
commit; 
end$$ 
DELIMITER ;

USE `SISDI`;
DROP procedure IF EXISTS insertVersion;
DELIMITER $$
USE `SISDI`$$
CREATE PROCEDURE insertVersion (
IN office_id VARCHAR(45),
IN version_id VARCHAR(45),
IN version_number int,
IN version_date DATE,

IN version_description VARCHAR(100)
)

BEGIN
INSERT INTO T_VERSION (OFFICE_ID,VERSION_ID,VERSION_NUMBER,VERSION_DATE,VERSION_DESCRIPTION) 
VALUES (office_id,version_id, version_number, version_date, version_description);
commit; 
end$$ 
DELIMITER ;

USE `SISDI`;
DROP procedure IF EXISTS updateOffice;
DELIMITER $$
USE `SISDI`$$
create procedure updateOffice( IN _offNumber VARCHAR(45),IN _reason VARCHAR(45),IN _name VARCHAR(45), 
IN _deadLine DATE, IN _sessionDate DATE,  IN _observations longtext, IN _publics TINYINT(4),
IN _notified TINYINT(4), IN _state INT,
IN _type int,
 IN _user VARCHAR(45),IN _receiver VARCHAR(45)) 
begin
UPDATE T_OFFICE SET REASON= _reason, NAME= _name, DEADLINE=_deadline, SESSIONDATE=_sessionDate, OBSERVATIONS=_observations,PUBLIC=_publics , NOTIFIED=_notified, STATE=_state, 
TYPE_ID=_type, USER_ID=_user, RECEIVER_ID=_receiver WHERE OFFNUMBER=_offNumber;
end$$
DELIMITER ; 



USE `SISDI`;
DROP procedure IF EXISTS updateCodOffice;
DELIMITER $$
USE `SISDI`$$
create procedure updateCodOffice( IN _tempuser VARCHAR(45),IN _indx int) 
begin
DECLARE var varchar(100);
set var=  CONCAT('OFICIO MSPH-','AM-', _indx,'-',YEAR(NOW()));
UPDATE T_OFFICE SET OFFNUMBER=var where USER_ID=_tempuser AND INDX=_indx;
end$$
DELIMITER ; 

#Change States Office
USE `SISDI`;
DROP procedure IF EXISTS changeState;
DELIMITER $$
USE `SISDI`$$
CREATE PROCEDURE changeState (
IN office_id VARCHAR(45),
IN office_state int
)
BEGIN
UPDATE T_OFFICE SET STATE=office_state WHERE OFFNUMBER=office_id;
commit; 
end$$ 
DELIMITER ;



alter table T_ROLE auto_increment = 1;
alter table T_DEPARTMENT auto_increment = 1;

insert into T_TYPE (DESCRIPTION) values ('Comunicar');
insert into T_TYPE (DESCRIPTION) values ('Solicitar');
insert into T_TYPE (DESCRIPTION) values ('Aprobar');




insert into T_STATE (ID, DESCRIPTION)values (0, 'Enviado');
insert into T_STATE (ID, DESCRIPTION)values (1, 'Recibido');
insert into T_STATE (ID, DESCRIPTION)values (2, 'Respondido');
insert into T_STATE (ID, DESCRIPTION)values (3, 'Anulado');

insert into T_TEMPUSER(NAME,EMAIL) values ('SUPERUSER','superuser@superuser.com');
insert into T_TEMPUSER(NAME,EMAIL) values ('Concejo Municipal','concejomunicipal@sanpablo.go.cr');
insert into T_TEMPUSER(NAME,EMAIL) values ('Secretaria de Alcaldia','alcaldia@sanpablo.go.cr');
insert into T_DEPARTMENT (NAME) values ('SUPERUSER'); # 1
insert into T_DEPARTMENT (NAME) values ('Concejo Municipal'); #2
insert into T_DEPARTMENT (NAME) values ('Alcaldia'); #3
insert into T_DEPARTMENT (NAME) values ('Desarrollo Urbano');#4
insert into T_DEPARTMENT (NAME) values ('Hacienda Municipal'); #5
insert into T_DEPARTMENT (NAME) values ('Servicios Públicos'); #6
insert into T_DEPARTMENT (NAME) values ('Proveeduría'); #7
insert into T_DEPARTMENT (NAME) values ('Recursos Humanos'); #8


insert into T_USER (TEMPUSER,PASSWORD,DEPARTMENT,STATUS) values ('superuser@superuser.com','superuser',1,1);#$2y$12$N6OQ0DsyRhYOq/m9AK7GzePyjLGsmiUM3ax0z3xIAFF40vjTgL73q

insert into T_USER (TEMPUSER,PASSWORD,DEPARTMENT,STATUS) values ('concejomunicipal@sanpablo.go.cr','concejomunicipal',2,1);#$2a$10$iCDiliiLJjGNB93sNBc.be6suYV/B.2KeklGnEnuRsDzKC2l79bV2
insert into T_USER (TEMPUSER,PASSWORD,DEPARTMENT,STATUS) values ('alcaldia@sanpablo.go.cr','alcaldia',3,1);#$2a$10$VJnqKpTeW7FLaSf6/eI6..4w6IAUOVUoVSicZbkGDgpHV2ajFaAny
insert into T_ROLE (NAME) values ('superuser');
insert into T_ROLE (NAME) values ('Concejo Municipal');
insert into T_ROLE (NAME) values ('Secretaria de Alcaldia');
insert into T_ROLE (NAME) values('Infraestructura Privada');
insert into T_ROLE (NAME) values('Infraestructura Pública');
insert into T_ROLE (NAME) values('Dirección de Desarrollo humano');
insert into T_ROLE (NAME) values('Dirección de hacienda Muninicipal');
insert into T_ROLE (NAME) values('Dirección de Servicios Públicos');
insert into T_ROLE (NAME) values('Inspector');
insert into T_ROLE (NAME) values('Planificación y Ordenamiento territorial');
insert into T_ROLE (NAME) values('Contabilidad');
insert into T_ROLE (NAME) values('Auxiliar de Contabilidad');
insert into T_ROLE (NAME) values('Gestión de Cobro');
insert into T_ROLE (NAME) values('Tesorería');
insert into T_ROLE (NAME) values('Cajas');
insert into T_ROLE (NAME) values('Valoración de Bienes Inmuebles');
insert into T_ROLE (NAME) values('Perito');
insert into T_ROLE (NAME) values('Archivo Central');
insert into T_ROLE (NAME) values('Centro de Atención Adulto Mayor Miraflores');
insert into T_ROLE (NAME) values('Centro de Atención Adulto Mayor R_d_Ricardo');
insert into T_ROLE (NAME) values('CECUDI');
insert into T_ROLE (NAME) values('Cementerio');
insert into T_ROLE (NAME) values('Centro de Conocimiento (Biblioteca)');
insert into T_ROLE (NAME) values('Centro Cultural');
insert into T_ROLE (NAME) values('Desarrollo social inclusivo');
insert into T_ROLE (NAME) values('Gestión Ambiental');
insert into T_ROLE (NAME) values('Plataforma de Servicios');
insert into T_ROLE (NAME) values('Plataformista');
insert into T_ROLE (NAME) values('Policía Municipal');
insert into T_ROLE (NAME) values('Proveeduría');
insert into T_ROLE (NAME) values('Subproveedora');
insert into T_ROLE (NAME) values('Bodega');
insert into T_ROLE (NAME) values('Planillas');
insert into T_ROLE (NAME) values('Salud Ocupacional');
insert into T_ROLE (NAME) values('Alcalde');
insert into T_ROLE (NAME) values('Vice Alcalde 1');
insert into T_ROLE (NAME) values('Director de asesoria Legal interna');
insert into T_ROLE (NAME) values('Asesoria Legal interna');
insert into T_ROLE (NAME) values('Tecnologías de Información');
insert into T_ROLE (NAME) values('Planificación, Presupuesto y Control');
insert into T_ROLE (NAME) values('Control Interno');
insert into T_ROLE (NAME) values('Recursos Humanos');

insert into T_USER_ROLE (USER_ID,ROLE_NAME) values ('concejomunicipal@sanpablo.go.cr','Concejo Municipal');
insert into T_USER_ROLE (USER_ID,ROLE_NAME) values ('alcaldia@sanpablo.go.cr','Secretaria de Alcaldia');
insert into T_USER_ROLE (USER_ID,ROLE_NAME) values ('superuser@superuser.com','superuser');

#Direccion de desarrorllo urbano
insert into T_TEMPUSER(NAME,EMAIL) values ('Santiago Baizán Hidalgo','desarrollourbano@sanpablo.go.cr');
insert into T_TEMPUSER(NAME,EMAIL) values ('Allan Alfaro Arias','infraestructuraprivada@sanpablo.go.cr');
insert into T_TEMPUSER(NAME,EMAIL) values ('Oscar Campos Garita','infraestructurapublica@sanpablo.go.cr');
insert into T_TEMPUSER(NAME,EMAIL) values ('Miguel Cortés Sánchez','planot@sanpablo.go.cr');
insert into T_TEMPUSER(NAME,EMAIL) values ('Jorge Duarte Ramírez','visados@sanpablo.go.cr');

insert into T_USER (TEMPUSER,PASSWORD,DEPARTMENT,STATUS,ISBOSS) values ('desarrollourbano@sanpablo.go.cr','desarrollourbano',4,1,1); # pass:$2y$12$GXf2OJ06OcHRFoujC7DscewFa8AYqrhlnSd6xWsyb0Z/m5YoNKKDu
insert into T_USER_ROLE (USER_ID,ROLE_NAME) values('desarrollourbano@sanpablo.go.cr','Dirección de Desarrollo humano');

insert into T_USER (TEMPUSER,PASSWORD,DEPARTMENT,STATUS) values ('infraestructuraprivada@sanpablo.go.cr','infraestructuraprivada',4,1); # pass:$2y$12$RYOpOCvwQrJV65JXK5qrD.X/ZYVsm8sVO5nNzBx7dB7QDwlMyhhPe
insert into T_USER_ROLE (USER_ID,ROLE_NAME) values('infraestructuraprivada@sanpablo.go.cr','Infraestructura Privada');

insert into T_USER (TEMPUSER,PASSWORD,DEPARTMENT,STATUS) values ('infraestructurapublica@sanpablo.go.cr','infraestructurapublica',4,1); # pass:$2y$12$T3Vb33ERjSk6bdczS7M0i.72Mve0z544LAs1Lo3Hj4da8pAIgKdqO
insert into T_USER_ROLE (USER_ID,ROLE_NAME) values('infraestructurapublica@sanpablo.go.cr','Infraestructura Pública');

insert into T_USER (TEMPUSER,PASSWORD,DEPARTMENT,STATUS) values ('planot@sanpablo.go.cr','planot',4,1); # pass:$2y$12$toG1PoV6sDm8PSvXBA6fk.FgFNNbZb19OvSP2nLRWeRBGgjSmTFC.
insert into T_USER_ROLE (USER_ID,ROLE_NAME) values('planot@sanpablo.go.cr','Planificación y Ordenamiento territorial');

insert into T_USER (TEMPUSER,PASSWORD,DEPARTMENT,STATUS) values ('visados@sanpablo.go.cr','visados',4,1); # pass:$2y$12$0DALv6Kh4s5teatCpOPgmeAA3EbEbm3RYbdtPfn4dHvkmoypMRewK
insert into T_USER_ROLE (USER_ID,ROLE_NAME) values('visados@sanpablo.go.cr','Planificación y Ordenamiento territorial');


# Dirección de Hacienda Municipal

insert into T_TEMPUSER(NAME,EMAIL) values ('Marjorie Montoya Gamboa','haciendamunicipal@sanpablo.go.cr');
insert into T_TEMPUSER(NAME,EMAIL) values ('Gilberth Chávez','contabilidad@sanpablo.go.cr');
insert into T_TEMPUSER(NAME,EMAIL) values ('Alonso Valerio','auxiliarcontabilidad@go.cr');
insert into T_TEMPUSER(NAME,EMAIL) values ('Carolina González Valerio','gestiondecobros@sanpablo.go.cr');
insert into T_TEMPUSER(NAME,EMAIL) values ('Johnny Cabalceta Ramírez','asistente.licencias@sanpablo.go.cr');
insert into T_TEMPUSER(NAME,EMAIL) values ('Yael Solano Méndez','tesoreria@sanpablo.go.cr');
insert into T_TEMPUSER(NAME,EMAIL) values ('Juan Carlos Zúñiga Jimenez','valoracionbienesinmuebles@sanpablo.go.cr');
insert into T_TEMPUSER(NAME,EMAIL) values ('Marcial Alpízar Gutiérrez','perito.a@sanpablo.go.cr');
insert into T_TEMPUSER(NAME,EMAIL) values ('Carlo Arias Villalobos','perito.b@sanpablo.go.cr');

insert into T_USER (TEMPUSER,PASSWORD,DEPARTMENT,STATUS,ISBOSS) values ('haciendamunicipal@sanpablo.go.cr','haciendamunicipal',5,1,1); # pass:$2y$12$xKiBSkIQYGrIcwZbWKquPesSlc88TQFMdv8f.x2.L5psgGW7T7Hr6
insert into T_USER_ROLE (USER_ID,ROLE_NAME) values('haciendamunicipal@sanpablo.go.cr','Dirección de hacienda Muninicipal');

insert into T_USER (TEMPUSER,PASSWORD,DEPARTMENT,STATUS) values ('contabilidad@sanpablo.go.cr','contabilidad',5,1); # pass:$2y$12$cV2PKROtqybto5TUqJizbOXOF7I5UX04g8n6ByCPRrRbBBZQYQajW
insert into T_USER_ROLE (USER_ID,ROLE_NAME) values('contabilidad@sanpablo.go.cr','Contabilidad');

insert into T_USER (TEMPUSER,PASSWORD,DEPARTMENT,STATUS) values ('auxiliarcontabilidad@go.cr','auxiliarcontabilidad',5,1); # pass:$2y$12$BX.plgUXeQwNWHYPTOp6lexiUOkAMFvhmdRJtPxDS8ftKTZ5kDCdO
insert into T_USER_ROLE (USER_ID,ROLE_NAME) values('auxiliarcontabilidad@go.cr','Auxiliar de Contabilidad');

insert into T_USER (TEMPUSER,PASSWORD,DEPARTMENT,STATUS) values ('gestiondecobros@sanpablo.go.cr','gestiondecobros',5,1); # pass:$2y$12$uKQG2afsHC0YzG31cQ6WWuDCsbrwBCKINOeeAaN6QYiBiHJFEz2cS
insert into T_USER_ROLE (USER_ID,ROLE_NAME) values('gestiondecobros@sanpablo.go.cr','Gestión de Cobro');

insert into T_USER (TEMPUSER,PASSWORD,DEPARTMENT,STATUS) values ('asistente.licencias@sanpablo.go.cr','asistentelicencias',5,1); # pass:$2y$12$3Xo2qB3jzTnOIEzISWy6fedQviOzz06Fvw39HV0hr1OfnW5WidQ3K
insert into T_USER_ROLE (USER_ID,ROLE_NAME) values('asistente.licencias@sanpablo.go.cr','Gestión de Cobro');

insert into T_USER (TEMPUSER,PASSWORD,DEPARTMENT,STATUS) values 
('tesoreria@sanpablo.go.cr','tesoreria',5,1); # pass:$2y$12$ouEFyuz/OSgnXwGJvUJefOVWD0vMZZUNlKDhXodUvi.Lp/X2cD0eG
insert into T_USER_ROLE (USER_ID,ROLE_NAME) values('tesoreria@sanpablo.go.cr','Tesorería');

insert into T_USER (TEMPUSER,PASSWORD,DEPARTMENT,STATUS) values 
('valoracionbienesinmuebles@sanpablo.go.cr','valoracionbienesinmuebles',5,1); # pass:$2y$12$ppPziOR/CmJMYR2lPRPwGOlTLK.mqNLX/Quzmp0dVz40QcDOhXl5u
insert into T_USER_ROLE (USER_ID,ROLE_NAME) values('valoracionbienesinmuebles@sanpablo.go.cr','Valoración de Bienes Inmuebles');

insert into T_USER (TEMPUSER,PASSWORD,DEPARTMENT,STATUS) values
 ('perito.a@sanpablo.go.cr','peritoa',5,1); # pass:$2y$12$YADFgLFA.Vv6yqunsaondeCqUKx7wtb9hmcA5MKl//92464/6VTRu
insert into T_USER_ROLE (USER_ID,ROLE_NAME) values('perito.a@sanpablo.go.cr','Perito');

insert into T_USER (TEMPUSER,PASSWORD,DEPARTMENT,STATUS) values
 ('perito.b@sanpablo.go.cr','peritob',5,1); # pass:$2y$12$Kb5P5AMYk/ojENbjI9BXX.z1IzEbwaMcUSF0HIghxy6QIdRY4hQIa
insert into T_USER_ROLE (USER_ID,ROLE_NAME) values('perito.b@sanpablo.go.cr','Perito');


#####- Servicios Publicos #####-

insert into T_TEMPUSER(NAME,EMAIL) values('Mauricio González González','serviciospublicos@sanpablo.go.cr');
insert into T_TEMPUSER(NAME,EMAIL) values('José Silva Castillo','archivocentral@sanpablo.go.cr');
insert into T_TEMPUSER(NAME,EMAIL) values('Lizbeth Rodríguez Calderón','centroatencionadultomayor@sanpablo.go.cr');
insert into T_TEMPUSER(NAME,EMAIL) values('Karla Araya Núñez','cecudi@sanpablo.go.cr');
insert into T_TEMPUSER(NAME,EMAIL) values('Vanessa Valverde','cementerio@sanpablo.go.cr');
insert into T_TEMPUSER(NAME,EMAIL) values('Jennifer Conejo Vásquez','centrodeconocimiento@sanpablo.go.cr');
insert into T_TEMPUSER(NAME,EMAIL) values('Sergio Salazar Rivera','administradorcentrocultural@sanpablo.go.cr');
insert into T_TEMPUSER(NAME,EMAIL) values('Ileana Rojas Hernández','centrocultural@sanpablo.go.cr');
insert into T_TEMPUSER(NAME,EMAIL) values('Yamileth Monterey López','desarrollosocialinclusivo@sanpablo.go.cr');
insert into T_TEMPUSER(NAME,EMAIL) values('David González Ovares','gestionambiental@sanpablo.go.cr');
insert into T_TEMPUSER(NAME,EMAIL) values('Marcela Sancho Villalobos','plataforma@sanpablo.go.cr');
insert into T_TEMPUSER(NAME,EMAIL) values('Maureen Gutiérrez','plataforma.a@sanpablo.go.cr');
insert into T_TEMPUSER(NAME,EMAIL) values('Carlos García Arguedas','plataforma.b@sanpablo.go.cr');
insert into T_TEMPUSER(NAME,EMAIL) values('Patricia Zúñiga','plataforma.c@sanpablo.go.cr');
insert into T_TEMPUSER(NAME,EMAIL) values('Luis Moncada Espinoza','policiamunicipal@sanpablo.go.cr');



insert into T_USER (TEMPUSER,PASSWORD,DEPARTMENT,STATUS,ISBOSS) values
 ('serviciospublicos@sanpablo.go.cr','serviciospublicos',6,1,1); # pass:$2y$12$sv9mXszFFms8Vsp2gtJ21eJcJKq/dd7aTdB35Q6QHmqtynRdAsruW
insert into T_USER_ROLE (USER_ID,ROLE_NAME) values('serviciospublicos@sanpablo.go.cr','Dirección de Servicios Públicos');

insert into T_USER (TEMPUSER,PASSWORD,DEPARTMENT,STATUS) values
 ('archivocentral@sanpablo.go.cr','archivocentral',6,1,1); # pass:$2y$12$9knVYUk7M6gUmt9XIdHO/OMWZURJOmlAp8hcX/pNCtoRhiz6xOSqm
insert into T_USER_ROLE (USER_ID,ROLE_NAME) values('archivocentral@sanpablo.go.cr','Archivo Central');

insert into T_USER (TEMPUSER,PASSWORD,DEPARTMENT,STATUS) values
 ('centroatencionadultomayor@sanpablo.go.cr','centroatencionadultomayor',6,1); # pass:$2y$12$qPjPC8ijCMmOezlddMxA5e5tSiQ/ekgB7DMBiLj.L9xr6/kOv2MvC
insert into T_USER_ROLE (USER_ID,ROLE_NAME) values('centroatencionadultomayor@sanpablo.go.cr','Centro de Atención Adulto Mayor Miraflores');

insert into T_USER (TEMPUSER,PASSWORD,DEPARTMENT,STATUS) values
 ('cecudi@sanpablo.go.cr','cecudi',6,1); # pass:$2y$12$I9z7f6EL6mv6HYp37TEGOOetQLGKo9ld8VGvFAt4JxkCStmb6yhii
insert into T_USER_ROLE (USER_ID,ROLE_NAME) values('cecudi@sanpablo.go.cr','CECUDI');

insert into T_USER (TEMPUSER,PASSWORD,DEPARTMENT,STATUS) values
 ('cementerio@sanpablo.go.cr','cementerio',6,1); # pass:$2y$12$6cQe24xuooaWHhDAI27ouu67ZKWcSwYizRsflPK34/oBSlvKoZRCy
insert into T_USER_ROLE (USER_ID,ROLE_NAME) values('cementerio@sanpablo.go.cr','Cementerio');

insert into T_USER (TEMPUSER,PASSWORD,DEPARTMENT,STATUS) values
 ('centrodeconocimiento@sanpablo.go.cr','centrodeconocimiento',6,1); # pass:$2y$12$XUhvsKDnttd8plXdBtHSY./YTy/GcOdZ16upoEj/EUAxdb6Tg.Qje
insert into T_USER_ROLE (USER_ID,ROLE_NAME) values('centrodeconocimiento@sanpablo.go.cr','Centro de Conocimiento (Biblioteca)');

insert into T_USER (TEMPUSER,PASSWORD,DEPARTMENT,STATUS) values
 ('administradorcentrocultural@sanpablo.go.cr','administradorcentrocultural',6,1); # pass:$2y$12$H0OPp7/F4r3fzwYdZg.feuCs4nqk8mBUeZUi.AkGEsBWgXWMD4Cqa
insert into T_USER_ROLE (USER_ID,ROLE_NAME) values('administradorcentrocultural@sanpablo.go.cr','Centro Cultural');

insert into T_USER (TEMPUSER,PASSWORD,DEPARTMENT,STATUS) values
 ('desarrollosocialinclusivo@sanpablo.go.cr','desarrollosocialinclusivo',6,1); # pass:$2y$12$j7Oe/V2VvZF1rFx5GMBwDesCdlGfFk4nMTA.dOv3Hzd6Yig4TfmF.
insert into T_USER_ROLE (USER_ID,ROLE_NAME) values('desarrollosocialinclusivo@sanpablo.go.cr','Desarrollo social inclusivo');

insert into T_USER (TEMPUSER,PASSWORD,DEPARTMENT,STATUS) values
 ('gestionambiental@sanpablo.go.cr','gestionambiental',6,1); # pass:$2y$12$/aRrJ2B6P5U1Z7cf6R.a9OMoZXuD/Jj8rezPd.2VYgkbQaQy4NcTC
insert into T_USER_ROLE (USER_ID,ROLE_NAME) values('gestionambiental@sanpablo.go.cr','Gestión Ambiental');

insert into T_USER (TEMPUSER,PASSWORD,DEPARTMENT,STATUS) values
 ('plataforma@sanpablo.go.cr','plataforma',6,1); # pass:$2y$12$/zUT.lk5a4HgdlnsdZteL.of/cq0vcrZ2FKBp87vCrLH7NO6K67ei
insert into T_USER_ROLE (USER_ID,ROLE_NAME) values('plataforma@sanpablo.go.cr','Plataforma de Servicios');

insert into T_USER (TEMPUSER,PASSWORD,DEPARTMENT,STATUS) values
 ('plataforma.a@sanpablo.go.cr','plataformaa',6,1); # pass:$2y$12$vbOYKP4afxEYdzZvDGgyuegCt3iwXTDEAHrFGjqW6Udn9A3ojTlfq
insert into T_USER_ROLE (USER_ID,ROLE_NAME) values('plataforma.a@sanpablo.go.cr','Plataformista');

insert into T_USER (TEMPUSER,PASSWORD,DEPARTMENT,STATUS) values
 ('plataforma.b@sanpablo.go.cr','plataformab',6,1); # pass:$2y$12$3pm1JS0KZ9Fq6p3td1c0PONKBlIIzq832CbOIobqa4GXYftMT3zY2
insert into T_USER_ROLE (USER_ID,ROLE_NAME) values('plataforma.b@sanpablo.go.cr','Plataformista');

insert into T_USER (TEMPUSER,PASSWORD,DEPARTMENT,STATUS) values
 ('plataforma.c@sanpablo.go.cr','plataformac',6,1); # pass:$2y$12$3yoRAm7cqUjuunPtngwQQuBavPzCPi08eabm6VlKCZSeXO.rOdhr6
insert into T_USER_ROLE (USER_ID,ROLE_NAME) values('plataforma.c@sanpablo.go.cr','Plataformista');

insert into T_USER (TEMPUSER,PASSWORD,DEPARTMENT,STATUS) values
 ('policiamunicipal@sanpablo.go.cr','policiamunicipal',6,1); # pass:$2y$12$C1F.BejEy.AJjkK6JU3mSuJ2HPFyTaaYgIvA1uRs/kmIxnAMoiXhS
insert into T_USER_ROLE (USER_ID,ROLE_NAME) values('policiamunicipal@sanpablo.go.cr','Policía Municipal');










#### Proveduria #####
insert into T_TEMPUSER(NAME,EMAIL) values('Oscar Hidalgo Mena','proveeduria1@sanpablo.go.cr');
insert into T_TEMPUSER(NAME,EMAIL) values('Adriana Benavides Vargas','proveeduria2@sanpablo.go.cr');

insert into T_USER (TEMPUSER,PASSWORD,DEPARTMENT,STATUS,ISBOSS) values
 ('proveeduria1@sanpablo.go.cr','proveeduria1',7,1,1); #pass:$2y$12$glSbeA/7oQ4ppjMjQH7I1.LmnqL7GJMMhJWXpBPAr90LaWvjQW3Hy
insert into T_USER_ROLE (USER_ID,ROLE_NAME) values('proveeduria1@sanpablo.go.cr','Proveeduría');

insert into T_USER (TEMPUSER,PASSWORD,DEPARTMENT,STATUS) values
 ('proveeduria2@sanpablo.go.cr','proveeduria2',7,1); #pass:$2y$12$Kx0iSLCqJXyIzVqDub0x0.bu89dBP1l8bD4/uVWPtWw4Mgs0YRrla
insert into T_USER_ROLE (USER_ID,ROLE_NAME) values('proveeduria2@sanpablo.go.cr','Subproveedora');




##Recursos Humanos####

insert into T_TEMPUSER(NAME,EMAIL) values('Diana Arias','recursoshumanos@sanpablo.go.cr');
insert into T_TEMPUSER(NAME,EMAIL) values('Planillas','nomina@sanpablo.go.cr');
insert into T_TEMPUSER(NAME,EMAIL) values('Jorge Slon Jaikel','saludocupacional@sanpablo.go.cr');

insert into T_USER (TEMPUSER,PASSWORD,DEPARTMENT,STATUS,ISBOSS) values
 ('recursoshumanos@sanpablo.go.cr','recursoshumanos',8,1,1); #pass:$2y$12$erDRhHdAorRpNTfzRddr6uX1D.BBhBbbhyyY6g0C.lQMCWwYRy8G6
insert into T_USER_ROLE (USER_ID,ROLE_NAME) values('recursoshumanos@sanpablo.go.cr','Recursos Humanos');

insert into T_USER (TEMPUSER,PASSWORD,DEPARTMENT,STATUS) values
 ('nomina@sanpablo.go.cr','nomina',8,1); #pass:$2y$12$J3UeKhGvzHuskW9b0Gq3LObvXcaB85XHLZvIvOv.vgCgHHPEXa/ke
insert into T_USER_ROLE (USER_ID,ROLE_NAME) values('nomina@sanpablo.go.cr','Planillas');

insert into T_USER (TEMPUSER,PASSWORD,DEPARTMENT,STATUS) values
 ('saludocupacional@sanpablo.go.cr','saludocupacional',8,1); #pass:$2y$12$BE3FgDmRKoMT2i2xuY2ZiODIxcvu7kj8Kq5zAjmZwufmEfDRX7m.W
insert into T_USER_ROLE (USER_ID,ROLE_NAME) values('saludocupacional@sanpablo.go.cr','Salud Ocupacional');




insert into T_TEMPUSER(NAME,EMAIL) values('Luis Fernando Vargas Mora','asesorialegal@sanpablo.go.cr');
insert into T_TEMPUSER(NAME,EMAIL) values('Joseph Granda Vargas','informatica@sanpablo.go.cr');
# insert into T_TEMPUSER(NAME,EMAIL) values('Gilberth Acuña Cerdas','planificacionpresupuestocontrol@sanpablo.go.cr');
insert into T_TEMPUSER(NAME,EMAIL) values('Ismael Salazar Oviedo','controlinterno@sanpablo.go.cr');

insert into T_USER (TEMPUSER,PASSWORD,DEPARTMENT,STATUS) values
 ('asesorialegal@sanpablo.go.cr','asesorialegal',3,1); #pass:$2y$12$w7UGsHJM0NfW/xQROeg8x.xjX.S1XYs7F1gFccHcvqy.b6k7NjOxK
insert into T_USER_ROLE (USER_ID,ROLE_NAME) values('asesorialegal@sanpablo.go.cr','Director de asesoria Legal interna');

insert into T_USER (TEMPUSER,PASSWORD,DEPARTMENT,STATUS) values
 ('informatica@sanpablo.go.cr','informatica',3,1); #pass:$2y$12$B3VstPqR8DzS4xcVMv.2ZukKtskcNW5cDxL4vYN0XqHi4MgSOq0uC
insert into T_USER_ROLE (USER_ID,ROLE_NAME) values('informatica@sanpablo.go.cr','Tecnologías de Información');

#insert into T_USER (TEMPUSER,PASSWORD,DEPARTMENT,STATUS) values
# ('planificacionpresupuestocontrol@sanpablo.go.cr','$2y$12$ywJ1xt5cpjgt3.JBKHr9teDSyq8xAlK4oi2uyTxnJmyBe.hd9IWeq',3,1); 
#  pass:planificacionpresupuestocontrol
#insert into T_USER_ROLE (USER_ID,ROLE_NAME) values('planificacionpresupuestocontrol@sanpablo.go.cr','Planificación, Presupuesto y Control');

insert into T_USER (TEMPUSER,PASSWORD,DEPARTMENT,STATUS) values
 ('controlinterno@sanpablo.go.cr','controlinterno',3,1); #pass:$2y$12$PP.ROST1M6niteGtzRK/f.qpbOOAkLk8eZXonQgJC1uKE9QdIlpsu
insert into T_USER_ROLE (USER_ID,ROLE_NAME) values('controlinterno@sanpablo.go.cr','Control Interno');
INSERT INTO `sisdi`.`t_timeouts` (`ID`, `COD`, `LIMITDATE`,  `DEPARTMENT`, `TYPE`) VALUES ('1', 'SOLICITUD VACACIONES', '2020/10/10', '1', 'Oficio');

call sisdi.insertOffice("OFICIO MSPH-AM-CCPJ-NE-001-2020", "Plan covid", "PLAN COVID", "2020/10/1", "11:00", "2020/10/3", "2020/10/1", "texto largo", 1, 1, 1, 1, "superuser@superuser.com", "concejomunicipal@sanpablo.go.cr",1);
INSERT INTO `sisdi`.`t_version` (`ID`, `OFFICE_ID`, `VERSION_ID`, `VERSION_NUMBER`, `VERSION_DATE`,  `VERSION_DESCRIPTION`) VALUES ('1',"OFICIO MSPH-AM-CCPJ-NE-001-2020", '\"Version id\"', '1', '2020-10-01', '\"Primera version descripcion\"');

call sisdi.insertVersion("OFICIO MSPH-AM-CCPJ-NE-001-2020",'ver2', 1, '2020/10/1', 'version nueva ');
call sisdi.updateOffice('OFICIO MSPH-AM-CCPJ-NE-001-2020', 'Asamblea', 'Oficio Asamblea', '2020/10/30', '2020/10/20','texto largo', 1, 1, 0, 2, 'asistente.licencias@sanpablo.go.cr', 'gestiondecobros@sanpablo.go.cr');

call sisdi.changeState('OFICIO MSPH-AM-CCPJ-NE-003-2020', 1);

INSERT INTO `sisdi`.`t_office` (`OFFNUMBER`, `REASON`, `NAME`, `INCORDATE`, `INCORTIME`, `DEADLINE`, `SESSIONDATE`, `OBSERVATIONS`, `PUBLIC`, `NOTIFIED`, `STATE`, `TYPE_ID`, `USER_ID`, `RECEIVER_ID`, `TIMEOUTS_ID`) VALUES ('OFICIO MSPH-AM-CCPJ-NE-002-2020', 'Asamblea', 'Oficio Asamblea', '2020-10-01', '11:00:00', '2020-10-30', '2020-10-22', 'texto largo', 1, 1, 2, 1, 'gestiondecobros@sanpablo.go.cr', 'asistente.licencias@sanpablo.go.cr', 1);
UPDATE `sisdi`.`t_department` SET `COD` = 'S' WHERE (`ID` = '1');
UPDATE `sisdi`.`t_department` SET `COD` = 'CM' WHERE (`ID` = '2');
UPDATE `sisdi`.`t_department` SET `COD` = 'AL' WHERE (`ID` = '3');
UPDATE `sisdi`.`t_department` SET `COD` = 'DU' WHERE (`ID` = '4');
UPDATE `sisdi`.`t_department` SET `COD` = 'HM' WHERE (`ID` = '5');
UPDATE `sisdi`.`t_department` SET `COD` = 'SP' WHERE (`ID` = '6');
UPDATE `sisdi`.`t_department` SET `COD` = 'PV' WHERE (`ID` = '7');
UPDATE `sisdi`.`t_department` SET `COD` = 'RH' WHERE (`ID` = '8');

INSERT INTO `sisdi`.`t_expediente` (`FILENAME`, `OBSERVATIONS`, `OWNER_ID`, `RECEIVER_ID`, `OFFICE_AMOUNT`, `DATE_CREATE`,`OWNER_DEPARTMENT`, `RECEIVER_DEPARTMENT`) VALUES ('2016 Hacienda Municipal-Desarrollo Urbano', 'No Existen', 'gestiondecobros@sanpablo.go.cr', 'infraestructurapublica@sanpablo.go.cr', '0', '2016-01-01', 'Hacienda Municipal', 'Desarrollo Urbano');
/*insert into `sisdi`.`t_fileloan`(`FILE_ID`, `FILENAME`, `OBSERVATIONS`, `NAME_REQUEST`, `DEPARTMENT_REQUEST`, `DATE_CREATE`, `DATE_RETURN`) 
VALUES (1, '2016 Hacienda Municipal-Desarrollo Urbano', 'Solicito para revision', 'gestiondecobros@sanpablo.go.cr', 'Hacienda Municipal', '2021-04-19', '2021-04-26');
*/
