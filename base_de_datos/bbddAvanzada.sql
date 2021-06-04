/*significados
measurings - mediciones
meter - medidores
bills - facturas
fees - tarifas
models - modelos
brands - marcas
clients - clientes
employees - empleados

measurement - medida
*/

CREATE DATABASE adea;
USE adea;

CREATE TABLE employees(
                          id_employee INT AUTO_INCREMENT,
                          NAME VARCHAR(50),
                          lastname VARCHAR(50),

                          CONSTRAINT pk_id_employee PRIMARY KEY (id_employee)
);

CREATE TABLE brands(
                       id_brand INT AUTO_INCREMENT,
                       DESCRIPTION VARCHAR(50),
                       CONSTRAINT pk_id_brand PRIMARY KEY (id_brand)
);

CREATE TABLE models(
                       id_model INT AUTO_INCREMENT,
                       DESCRIPTION VARCHAR(50),
                       id_brand INT,

                       CONSTRAINT pk_id_model PRIMARY KEY (id_model),
                       CONSTRAINT fk_id_brand FOREIGN KEY (id_brand)  REFERENCES brands(id_brand)
);

CREATE TABLE fees(
                     id_fee INT AUTO_INCREMENT,
                     type_fee VARCHAR(50),
                     price_fee INT,

                     CONSTRAINT pk_id_fee PRIMARY KEY (id_fee)
);

CREATE TABLE clients(
                        id_client INT AUTO_INCREMENT,
                        NAME VARCHAR(50),
                        lastname VARCHAR(50),

                        CONSTRAINT pk_id_client PRIMARY KEY (id_client)
);

CREATE TABLE bills(
                      id_bill INT AUTO_INCREMENT,
                      amount FLOAT,
                      pay BOOLEAN,
                      first_measurement DATE,
                      last_measurement DATE,
                      id_client INT,

                      CONSTRAINT pk_id_bill PRIMARY KEY (id_bill),
                      CONSTRAINT fk_id_client_bill FOREIGN KEY (id_client) REFERENCES clients(id_client)
);

CREATE TABLE addresses(
                          id_address INT AUTO_INCREMENT,
                          name_address VARCHAR(50),
                          number_address VARCHAR (50),
                          id_client INT,
                          CONSTRAINT pk_id_address PRIMARY KEY (id_address),
                          CONSTRAINT fk_id_client_address FOREIGN KEY (id_client) REFERENCES clients(id_client)
);

CREATE TABLE meters(
                       id_meter INT AUTO_INCREMENT,
                       id_address INT,
                       serial_number VARCHAR(50),
                       id_model INT,
                       password_meter VARCHAR(50),
                       id_fee INT,
                       CONSTRAINT unique_serial_number UNIQUE (serial_number),
                       CONSTRAINT pk_id_meter PRIMARY KEY (id_meter),
                       CONSTRAINT fk_id_model FOREIGN KEY (id_model) REFERENCES models(id_model),
                       CONSTRAINT fk_id_address FOREIGN KEY (id_address) REFERENCES addresses(id_address),
                       CONSTRAINT fk_id_fee FOREIGN KEY (id_fee) REFERENCES fees(id_fee)
);

CREATE TABLE measurings(
                           id_measuring INT AUTO_INCREMENT,
                           measurement INT,
                           TIME DATE,
                           id_bill INT,
                           id_meter INT,

                           CONSTRAINT pk_id_measuring PRIMARY KEY (id_measuring),
                           CONSTRAINT fk_id_bill FOREIGN KEY (id_bill) REFERENCES bills(id_bill),
                           CONSTRAINT fk_id_meter FOREIGN KEY (id_meter) REFERENCES meters(id_meter)
);

/*2) Consulta de facturas por rango de fechas.*/

SELECT * FROM bills;

INSERT INTO bills(amount, pay, first_measurement, last_measurement) VALUE (100, FALSE, '2020-03-26', '2020-03-27');
INSERT INTO bills(amount, pay, first_measurement, last_measurement) VALUE (100, FALSE, '2020-03-22', '2020-03-23');
INSERT INTO bills(amount, pay, first_measurement, last_measurement) VALUE (100, FALSE, '2020-03-22', '2020-03-23');
INSERT INTO bills(amount, pay, first_measurement, last_measurement) VALUE (100, FALSE, '2020-04-22', '2020-06-23');

SELECT * FROM bills b
WHERE (b.first_measurement BETWEEN '2020-03-22' AND '2020-04-24') OR (b.last_measurement BETWEEN '2020-03-25' AND '2020-03-30');

SELECT * FROM bills b
WHERE (b.first_measurement BETWEEN '2020-03-22' AND '2020-04-24');

/*3) Consulta de deuda (Facturas impagas)*/
SELECT SUM(b.amount) FROM bills b
WHERE b.pay = FALSE;

/*5) Consulta de mediciones por rango de fechas*/

SELECT * FROM measurings;
DESC measurings;

INSERT INTO measurings(measurement, TIME) VALUES (10, '2020-03-26'), (10, '2020-03-21'), (10, '2020-03-22');

SELECT * FROM measurings m
WHERE m.time BETWEEN '2020-03-22' AND '2020-04-24';

INSERT INTO brands (description) VALUES("marca1");
INSERT INTO brands (description) VALUES("marca2");
INSERT INTO models (description, id_brand) VALUES("modelo1", 1);
INSERT INTO models (description, id_brand) VALUES("modelo2", 2);


DROP PROCEDURE  IF EXISTS generedBill
DELIMITER //
CREATE PROCEDURE generedBill( IN amount FLOAT,
                              IN pay BOOLEAN,
                              IN first_measurement  DATE,
                              IN last_measurement DATE,
                              IN id_client INT)
BEGIN

INSERT INTO bills(amount, pay, first_measurement, last_measurement, id_client) VALUE (amount, pay, first_measurement, last_measurement, id_client);

END //


/*2) La facturación se realizará por un proceso automático en la base de datos. Se
debe programar este proceso para el primer día de cada mes y debe generar una
factura por medidor y debe tomar en cuenta todas las mediciones no facturadas
para cada uno de los medidores, sin tener en cuenta su fecha. La fecha de vencimiento de
esta factura será estipulado a 15 días.*/

/* despues de activar esta variable funciona*/

SET GLOBAL event_scheduler = ON;

/* Me crea el evento que sera automatico, le especifico el nombre, y a partir desde cuando y cada cuanto*/
CREATE EVENT generetedBill
ON SCHEDULE EVERY 1 MINUTE STARTS NOW()
DO CALL generedBill(4, FALSE, '2020-03-26', '2020-03-27', 1);

/* para eliminar un evento*/
DROP EVENT IF EXISTS generetedBill

/* Veo los eventos que estan en lista*/
SHOW EVENTS;
 /* para ver lo que se esta trabajanndo*/

SHOW WARNINGS
