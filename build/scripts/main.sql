-- drop old schema and user
DROP SCHEMA IF EXISTS hackathon CASCADE;
DROP USER IF EXISTS hackathon;
-- create schema, user, rights
CREATE USER hackathon_test WITH PASSWORD 'hackathon_test';
CREATE SCHEMA hackathon_test;
GRANT USAGE ON SCHEMA hackathon_test TO hackathon_test;
ALTER DEFAULT PRIVILEGES IN SCHEMA hackathon_test GRANT ALL ON TABLES TO hackathon_test;
ALTER DEFAULT PRIVILEGES IN SCHEMA hackathon_test GRANT ALL ON SEQUENCES TO hackathon_test;

--test table with data
CREATE TABLE hackathon_test.TEST_DATA (
  ID         BIGSERIAL NOT NULL,
  DATA VARCHAR(255),
  PRIMARY KEY (id)
);

INSERT INTO hackathon_test.test_data (id, data) VALUES (1, 'Scorpions You and I');


CREATE TABLE hackathon_test.MANUFACTURE (
  ID         BIGSERIAL NOT NULL,
  NAME VARCHAR(255),
  INN VARCHAR(255) UNIQUE,
  ACTIVITY VARCHAR(255),
  ADDRESS VARCHAR(255),
  NORTH NUMERIC (2, 10),
  WEST NUMERIC (2, 10),
  PRIMARY KEY (id)
);

INSERT INTO hackathon_test.MANUFACTURE (NAME, INN, ACTIVITY, ADDRESS)
VALUES ('ПАО "ЧТПЗ"', '7449006730', 'Металлопрокат', 'г. Челябинск, ул. Машиностроителей, д. 21, кв. 21');

INSERT INTO hackathon_test.MANUFACTURE (NAME, INN, ACTIVITY, ADDRESS)
VALUES ('ООО "НОВАТЭК- ЧЕЛЯБИНСК"', '7404056114', 'Топливо и Энергетика', 'г. Челябинск, пр-кт Ленина, 42, А');

INSERT INTO hackathon_test.MANUFACTURE (NAME, INN, ACTIVITY, ADDRESS)
VALUES ('АО "ЧЭМК"', '7447010227', 'Металлы, сплавы', 'г. Челябинск, ул. Героев Танкограда, д. 80П, стр. 80');

INSERT INTO hackathon_test.MANUFACTURE (NAME, INN, ACTIVITY, ADDRESS)
VALUES ('ООО "УРАЛЭНЕРГОСБЫТ"', '7453313477', 'Электроэнергия', 'г. Челябинск, пр-кт Ленина, д. 28Д, ЭТ/ПОМ 6/7');

INSERT INTO hackathon_test.MANUFACTURE (NAME, INN, ACTIVITY, ADDRESS)
VALUES ('АО "ЧЦЗ"', '7448000013', 'Металлы, сплавы', 'г. Челябинск, Свердловский тракт, д. 24');

INSERT INTO hackathon_test.MANUFACTURE (NAME, INN, ACTIVITY, ADDRESS)
VALUES ('ООО "КОМПАНИЯ РБТ"', '7452030451', 'Бытовая техника, часы', 'г. Челябинск, ул. Производственная, д. 8Б, ОФИС 303 Лебедева');

INSERT INTO hackathon_test.MANUFACTURE (NAME, INN, ACTIVITY, ADDRESS)
VALUES ('ООО "МЕЧЕЛ-КОКС"', '7450043423', 'Твердое топливо, торф, кокс, уголь', 'г. Челябинск, ул. Павелецкая 2-я, д. 14');

INSERT INTO hackathon_test.MANUFACTURE (NAME, INN, ACTIVITY, ADDRESS)
VALUES ('ООО "МЕЧЕЛ-ЭНЕРГО"', '7722245108', 'Электроэнергия', 'г. Челябинск, ул. Павелецкая 2-я, д. 14');

INSERT INTO hackathon_test.MANUFACTURE (NAME, INN, ACTIVITY, ADDRESS)
VALUES ('АО "КОНАР"', '7451064592', 'Услуги металлообработки', 'г. Челябинск, ул. Енисейская, д. 8, кв. 59');


CREATE TABLE hackathon_test.ACTIVITY (
  ID         BIGSERIAL NOT NULL,
  NAME VARCHAR(255)
);

INSERT INTO hackathon_test.ACTIVITY (NAME) VALUES ('Услуги металлообработки');
INSERT INTO hackathon_test.ACTIVITY (NAME) VALUES ('Топливо');
INSERT INTO hackathon_test.ACTIVITY (NAME) VALUES ('Металлы');
INSERT INTO hackathon_test.ACTIVITY (NAME) VALUES ('Сплавы');
INSERT INTO hackathon_test.ACTIVITY (NAME) VALUES ('Торф');
INSERT INTO hackathon_test.ACTIVITY (NAME) VALUES ('Кокс');
INSERT INTO hackathon_test.ACTIVITY (NAME) VALUES ('Уголь');

CREATE TABLE hackathon_test.LICENSE (
  ID         BIGSERIAL NOT NULL,
  INN VARCHAR(255)
);

INSERT INTO hackathon_test.LICENSE (INN) VALUES ('75742000');

