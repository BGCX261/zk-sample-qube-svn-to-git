create sequence id_taglia;
create sequence id_gelato;
create sequence ID_GUSTO;
--SELECT GEN_ID( id_gelato, 0 ) FROM RDB$DATABASE;
CREATE TABLE GELATI
(
   ID_GELATO integer PRIMARY KEY NOT NULL,
   NUMERO_TAVOLO integer NOT NULL,
   GUSTO_UNO integer,
   GUSTO_DUE integer,
   GUSTO_TRE integer,
   PANNA char(1),
   TAGLIA integer NOT NULL,
   PAGATO char(1),
   FATTO char(1)
);
CREATE TABLE GUSTI
(
   ID_GUSTO integer PRIMARY KEY NOT NULL,
   GUSTO varchar(40) NOT NULL,
   DESCRIZIONE varchar(80),
   DISPONIBILE char(1),
   IMAGE blob subtype -1
);
CREATE TABLE RESET_LOGIN
(
   ID varchar(40) PRIMARY KEY NOT NULL,
   USERNAME varchar(30) NOT NULL,
   EXPIRE timestamp NOT NULL
);
CREATE TABLE TAGLIA
(
   ID_TAGLIA integer PRIMARY KEY NOT NULL,
   DESRIZIONE varchar(20) NOT NULL,
   PREZZO double precision NOT NULL
);
CREATE TABLE USERS
(
   USERNAME varchar(30) PRIMARY KEY NOT NULL,
   HASHED_PASSWORD varchar(80) NOT NULL,
   EMAIL varchar(80),
   ROLE varchar(1),
   SALT varchar(40) NOT NULL,
   FIRSTNAME varchar(40) NOT NULL,
   LASTNAME varchar(40) NOT NULL
);

ALTER TABLE GELATI ADD CONSTRAINT FK_GELATI_TAGLIA FOREIGN KEY (TAGLIA) REFERENCES TAGLIA(ID_TAGLIA);
ALTER TABLE GELATI ADD CONSTRAINT FK_GELATI_GUSTO_3 FOREIGN KEY (GUSTO_TRE) REFERENCES GUSTI(ID_GUSTO);
ALTER TABLE GELATI ADD CONSTRAINT FK_GELATI_GUSTO_2 FOREIGN KEY (GUSTO_DUE) REFERENCES GUSTI(ID_GUSTO);
ALTER TABLE GELATI ADD CONSTRAINT FK_GELATI_GUSTO_1 FOREIGN KEY (GUSTO_UNO) REFERENCES GUSTI(ID_GUSTO);
CREATE UNIQUE INDEX PK_GELATI ON GELATI(ID_GELATO);
CREATE UNIQUE INDEX PK_GUSTI ON GUSTI(ID_GUSTO);
ALTER TABLE RESET_LOGIN ADD CONSTRAINT FK_RESET_LOGIN_USERS FOREIGN KEY (USERNAME) REFERENCES USERS(USERNAME);
CREATE UNIQUE INDEX PK_RESET_LOGIN ON RESET_LOGIN(ID);
CREATE UNIQUE INDEX PK_TAGLIA ON TAGLIA(ID_TAGLIA);
CREATE UNIQUE INDEX PK_USERS ON USERS(USERNAME);
CREATE INDEX IDX_USERS_EMAIL ON USERS(EMAIL);
