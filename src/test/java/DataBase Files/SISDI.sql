
DROP DATABASE SISDI;
CREATE SCHEMA IF NOT EXISTS `SISDI` DEFAULT CHARACTER SET utf8 ;

use SISDI;

create table T_STATE (
ID int,
DESCRIPTION varchar(45) NOT NULL, 
constraint PK_STATE primary key (ID)
);

create table T_TYPE (
ID int auto_increment, 
DESCRIPTION varchar(25) NOT NULL,
constraint PK_TYPE primary key (ID)
);


create table T_DEPARTMENT (
ID int auto_increment,
COD  varchar(45),
NAME varchar(100) NOT NULL,
constraint PK_DEPARMENT primary key(ID),
constraint UK_NAMED unique key(NAME)
);

create table T_TIMEOUTS (
ID int auto_increment,
COD  varchar(45),
LIMITDATE date,
DEPARTMENT int,
TYPE varchar(45),
constraint PK_WAIT primary key(ID),
constraint FK_DEPARTMENT_TIMEOUTS foreign key (DEPARTMENT) references T_DEPARTMENT(ID)

);

create table T_TEMPUSER(
INDX int auto_increment,
NAME varchar(45) NOT NULL,
EMAIL varchar(45) NOT NULL,
constraint PK_TEMPUSER primary key (INDX),
constraint UK_EMAIL unique key(EMAIL)
); 

create table T_USER(
ID int auto_increment, 
DEPARTMENT int NOT NULL,
PASSWORD varchar(100) NOT NULL,
TEMPUSER varchar(45) NOT NULL,
STATUS tinyint NOT NULL,
ISBOSS int NOT NULL DEFAULT 0,
constraint PK_USER primary key (ID),
constraint FK_TEMPUSER foreign key (TEMPUSER) references T_TEMPUSER(EMAIL),
constraint FK_DEPARTMENT foreign key (DEPARTMENT) references T_DEPARTMENT(ID)
);
 create table T_EXPEDIENTE(
INDX int auto_increment,
FILENAME varchar(100) DEFAULT "sin asignar",
OBSERVATIONS longtext, 
OWNER_ID varchar(45),-- Emisor propietario del expediente que pertenece a algun departamento
RECEIVER_ID  varchar(45), -- Receptor del expediente que pertenece a algun departamento
 OFFICE_AMOUNT int, 
 DATE_CREATE date,
 DATE_RETURN date,
 STATE int DEFAULT 0,
 OWNER_DEPARTMENT varchar(45),
 RECEIVER_DEPARTMENT varchar(45),
 constraint PK_EXPEDIENTE primary key(INDX),
 constraint UK_FILENAME unique key (FILENAME),
 constraint FK_OWNER foreign key (OWNER_ID) references T_USER(TEMPUSER),
 constraint FK_RECEIVER foreign key (RECEIVER_ID) references T_USER(TEMPUSER),
  constraint FK_OWNER_DEPARTMENT foreign key (OWNER_DEPARTMENT) references T_DEPARTMENT(NAME),
 constraint FK_RECEIVER_DEPARTMENT foreign key (RECEIVER_DEPARTMENT) references T_DEPARTMENT(NAME)
 
 );
create table T_TRANSFER(
	INDX int auto_increment,
    INDX_FILE int,
    OWNER_ID varchar(45),
    RECEIVER_ID varchar(45),
    DATE_TRANSFER date,
    STATE int,
    OFFICE_AMOUNT int,
    OBSERVATIONS longtext, 
    constraint PK_TRANSFER primary key(INDX),
	constraint FK_OWNER_ID foreign key (OWNER_ID) references T_USER(TEMPUSER),
	constraint FK_RECEIVER_ID foreign key (RECEIVER_ID) references T_USER(TEMPUSER)
);
create table T_OFFICE(
INDX int auto_increment,
OFFNUMBER varchar(45),
REASON varchar(45),
NAME varchar(45),
EXPEDIENTE int DEFAULT 0,
INCORDATE date,
INCORTIME time,
DEADLINE date null,
SESSIONDATE date,
OBSERVATIONS longtext,
PUBLIC tinyint,
NOTIFIED tinyint,
STATE int,
TYPE_ID int,
USER_ID varchar(45),
RECEIVER_ID varchar(45),
TIMEOUTS_ID INT,

constraint PK_OFFICE primary key (INDX),
constraint UK_SESSIONDATE unique key (SESSIONDATE),
constraint FK_TYPE foreign key (TYPE_ID) references T_TYPE(ID),
constraint FK_STATE foreign key (STATE) references T_STATE(ID),
constraint UK_OFFNUMBER unique key (OFFNUMBER),
constraint FK_USER foreign key (USER_ID) references T_USER(TEMPUSER),

constraint FK_RECEIVER_EXP foreign key (RECEIVER_ID) references T_USER(TEMPUSER),
constraint FK_TIMEOUTS foreign key (TIMEOUTS_ID) references T_TIMEOUTS(ID)
);

 
 

create table T_VERSION (
ID int auto_increment, 
OFFICE_ID varchar(45),
VERSION_ID varchar(45) NOT NULL,
VERSION_NUMBER INT,
VERSION_DATE date,
VERSION_DESCRIPTION varchar(100),
REASON varchar(45),
OBSERVATIONS longtext,
constraint FK_VERSION_OFFICE foreign key (OFFICE_ID) references T_OFFICE(OFFNUMBER),
constraint PK_VERSION primary key (ID)
);


create table T_OFFPDF(
ID int auto_increment,
OFFICE varchar(45),
URL mediumblob,
constraint PK_OFFPDF primary key(ID)
);

create table T_OTHERDOCS(
ID int auto_increment,
OFFICE varchar(45),
URL mediumblob,
NAME_DOC varchar(45),
TYPE_DOC varchar(30),
constraint PK_OFFPDF primary key(ID)
);


create table T_USEROFF(
TEMPUSER varchar(45),
OFFICE varchar(45),
constraint FK_TEMPUSER1 foreign key (TEMPUSER) references T_TEMPUSER(EMAIL),
constraint FK_OFFICE1 foreign key (OFFICE) references T_OFFICE(OFFNUMBER),
constraint PK_T_USEROFF PRIMARY KEY(TEMPUSER,OFFICE)
);

create table T_NOTIFICATION(
OFFICE varchar(45),
USER varchar(45),
constraint FK_OFFICE2 foreign key(OFFICE) references T_OFFICE(OFFNUMBER),
constraint FK_USERNOTI foreign key (USER) references T_USER(TEMPUSER)
);


create table T_OFFDPRMNT(
DEPARTMENT int,
OFFICE varchar(45),
constraint FK_DEPARTMENT1 foreign key (DEPARTMENT) references T_DEPARTMENT(ID),
constraint FK_OFFICE3 foreign key (OFFICE) references T_OFFICE(OFFNUMBER)
);

create table T_ROLE(
ID int auto_increment,
NAME varchar(45),
constraint PK_ROLE primary key(ID),
constraint UK_NAME unique key(NAME)
);

create table T_USER_ROLE(
USER_ID varchar(45),
ROLE_NAME varchar(45),
constraint PK_ROLE primary key(USER_ID,ROLE_NAME),
constraint FK_USER_ROLE foreign key(USER_ID) references T_USER(TEMPUSER),
constraint FK_ROLE_UR foreign key(ROLE_NAME) references T_ROLE(NAME)

);

create table T_FORGOT_PASSWORD(
USER_ID varchar(45),
TOKEN varchar(6),
DATE_TIME datetime,
constraint FK_T_FORGOT foreign key (USER_ID) references T_USER(TEMPUSER)
);

create table T_FILELOAN(
	ID int auto_increment,
	FILE_ID INT,
	FILENAME varchar(100),
	OBSERVATIONS longtext, 
	NAME_REQUEST varchar(45),
	DEPARTMENT_REQUEST varchar(45),
	DATE_CREATE date,
	DATE_RETURN date, 
    STATE tinyint,
	constraint PK_FILELOAN primary key(ID),
	constraint UK_FILENAME unique key (FILENAME),
	constraint FK_NAME_REQUEST foreign key (NAME_REQUEST) references T_USER(TEMPUSER),
	constraint FK_DEPARTMENT_REQUEST foreign key (DEPARTMENT_REQUEST) references T_DEPARTMENT(NAME),
	constraint FK_FILE_ID foreign key (FILE_ID) references T_EXPEDIENTE(INDX)
);
create table T_SIGNATURE(
	ID int auto_increment,
    CERTIFICATE_ID longtext,
    constraint PK_SIGNATURE primary key(ID)
);

CREATE TABLE T_FILEACT(
ID int auto_increment,
FILE_ID INT,
FILENAME varchar(100),
OBSERVATIONS longtext, 
NAME_REQUEST varchar(45),
DEPARTMENT_REQUEST varchar(45),
DATE_CREATE date,
DATE_FILE date,
constraint PK_FILEACT primary key(ID),
constraint FK_NAME_REQUEST_ACT foreign key (NAME_REQUEST) references T_USER(TEMPUSER),
constraint FK_DEPARTMENT_REQUEST_ACT foreign key (DEPARTMENT_REQUEST) references T_DEPARTMENT(NAME)

);
