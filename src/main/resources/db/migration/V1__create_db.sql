------------------------------------------------------------------
-- Base de données :  BD_MARCOPOLO 
-- Script testé sur SQL Workbench --> résultats ok //vincent le 10feb
--
--desactivation des commandes DROP
--penser à faire un commit
------------------------------------------------------------------

-- suppression des séquences (attention: ordre à respecter)

/*
DROP SEQUENCE seq_tag;
DROP SEQUENCE seq_marquepage;
DROP SEQUENCE seq_cle;
DROP SEQUENCE seq_person;
DROP SEQUENCE seq_langue;*/


-- suppression des tables (attention: ordre à respecter)

/*
DROP TABLE tag;
DROP TABLE marquepage;
DROP TABLE cle;
DROP TABLE person;
DROP TABLE langue;*/


-- création des tables

CREATE TABLE langue (
	id_langue NUMBER(8) CONSTRAINT PK_langue PRIMARY KEY,
	nom VARCHAR2(60) not null CONSTRAINT UNIK_langue  UNIQUE
);

CREATE TABLE cle (
	id_cle NUMBER(8) CONSTRAINT PK_cle PRIMARY KEY,
	id_langue NUMBER(8) CONSTRAINT CIR_langue_in_cle REFERENCES langue,
	cle VARCHAR2(60) not null
	
);

CREATE TABLE person (
	id_person NUMBER(8) CONSTRAINT PK_person PRIMARY KEY,
	id_langue NUMBER(8) CONSTRAINT CIR_langue_in_pers REFERENCES langue,
	mail VARCHAR2(200) not null,
	mdp VARCHAR2(40) not null,
	stamp NUMBER(8) not null
);

CREATE TABLE marquepage (
	id_marquepage NUMBER(8) CONSTRAINT PK_marquepage PRIMARY KEY,
	id_person NUMBER(8) CONSTRAINT CIR_person_in_marq REFERENCES person,
	nom VARCHAR2(60) not null,
	lien VARCHAR2(200) not null
);

CREATE TABLE tag (
	id_tag NUMBER(8) CONSTRAINT PK_tag PRIMARY KEY,
	id_marquepage NUMBER(8) CONSTRAINT CIR_marquepage_in_tag REFERENCES marquepage,
	id_cle NUMBER(8) CONSTRAINT CIR_cle_in_tag REFERENCES cle,
	valeur VARCHAR2(100)
);


-- création des séquences (attention: ordre à respecter)


CREATE SEQUENCE seq_langue
	START WITH 1
	INCREMENT BY 1
	MINVALUE 1
	NOMAXVALUE
	NOCYCLE
	CACHE 20;

CREATE SEQUENCE seq_person
	START WITH 1
	INCREMENT BY 1
	MINVALUE 1
	NOMAXVALUE
	NOCYCLE
	CACHE 20;

CREATE SEQUENCE seq_cle
	START WITH 1000
	INCREMENT BY 1
	MINVALUE 1
	NOMAXVALUE
	NOCYCLE
	CACHE 20;
	
CREATE SEQUENCE seq_marquepage
	START WITH 1
	INCREMENT BY 1
	MINVALUE 1
	NOMAXVALUE
	NOCYCLE
	CACHE 20;	

CREATE SEQUENCE seq_tag
	START WITH 1
	INCREMENT BY 1
	MINVALUE 1
	NOMAXVALUE
	NOCYCLE
	CACHE 20;


--commit
