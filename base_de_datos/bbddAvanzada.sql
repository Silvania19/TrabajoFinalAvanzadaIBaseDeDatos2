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
                          id INT AUTO_INCREMENT,
                          NAME VARCHAR(50),
                          lastname VARCHAR(50),
                          PASSWORD VARCHAR(50),
                          CONSTRAINT pk_id_employee PRIMARY KEY (id)
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
                        id INT AUTO_INCREMENT,
                        NAME VARCHAR(50),
                        lastname VARCHAR(50),
                        PASSWORD VARCHAR(50),
                        CONSTRAINT pk_id_client PRIMARY KEY (id)
);


CREATE TABLE addresses(
                          id_address INT AUTO_INCREMENT,
                          name_address VARCHAR(50),
                          number_address VARCHAR (50),
                          id_client INT,
                          CONSTRAINT pk_id_address PRIMARY KEY (id_address),
                          CONSTRAINT fk_id_client_address FOREIGN KEY (id_client) REFERENCES clients(id)
);

CREATE TABLE bills(
                      id_bill INT AUTO_INCREMENT,
                      amount FLOAT,
                      total_kwh FLOAT,
                      pay BOOLEAN,
                      date_create DATETIME,
                      expiration DATETIME,
                      first_measurement DATETIME,
                      last_measurement DATETIME,
                      id_client INT,
                      id_address INT,
                      CONSTRAINT pk_id_bill PRIMARY KEY (id_bill),
                      CONSTRAINT fk_id_client_bill FOREIGN KEY (id_client) REFERENCES clients(id),
                      CONSTRAINT fk_id_address_bill FOREIGN KEY (id_address) REFERENCES addresses(id_address)
);

CREATE TABLE meters(

                       id_address INT,
                       serial_number VARCHAR(50),
                       id_model INT,
                       password_meter VARCHAR(50),
                       id_fee INT,
                       CONSTRAINT unique_serial_number UNIQUE (serial_number),
                       CONSTRAINT unique_id_address UNIQUE (id_address),
                       CONSTRAINT pk_id_serial_number PRIMARY KEY (serial_number),
                       CONSTRAINT fk_id_model FOREIGN KEY (id_model) REFERENCES models(id_model),
                       CONSTRAINT fk_id_address FOREIGN KEY (id_address) REFERENCES addresses(id_address),
                       CONSTRAINT fk_id_fee FOREIGN KEY (id_fee) REFERENCES fees(id_fee)
);

CREATE TABLE measurings(
                           id_measuring INT AUTO_INCREMENT,
                           VALUE FLOAT,
                           DATE DATETIME,
                           id_bill INT,
                           serial_number VARCHAR(50),
                           price_measuring DOUBLE,
                           CONSTRAINT pk_id_measuring PRIMARY KEY (id_measuring),
                           CONSTRAINT fk_id_bill FOREIGN KEY (id_bill) REFERENCES bills(id_bill),
                           CONSTRAINT fk_serial_number FOREIGN KEY (serial_number) REFERENCES meters(serial_number)ON DELETE CASCADE
);

/* 1) Generar las estructuras necesarias para dar soporte a 4 sistemas diferentes :
a) BACKOFFICE, que permitirá el manejo de clientes, medidores y tarifas.
b) CLIENTES, que permitirá consultas de mediciones y facturación.
c) MEDIDORES,, que será el sistema que enviará la información de
mediciones a la base de datos.
d) FACTURACIÓN , proceso automático de facturación.
 */

/*a) BACKOFFICE, que permitirá el manejo de clientes, medidores y tarifas.*/
CREATE USER 'backoffice'@'localhost' IDENTIFIED BY '1234';

GRANT SELECT, INSERT, UPDATE, DELETE ON clients TO 'backoffice'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE ON meters TO 'backoffice'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE ON fees TO 'backoffice'@'localhost';

/*b) CLIENTES, que permitirá consultas de mediciones y facturación.*/
CREATE USER 'clients'@'localhost' IDENTIFIED BY '1234';
GRANT SELECT ON measurings TO 'clients'@'localhost';
GRANT SELECT ON bills TO 'clients'@'localhost';

/*c) MEDIDORES,, que será el sistema que enviará la información de
mediciones a la base de datos.*/
CREATE USER 'meters'@'localhost' IDENTIFIED BY '1234';
GRANT INSERT ON measurings TO 'meters'@'localhost';

/*d)FACTURACIÓN , proceso automático de facturación*/

CREATE USER 'bills'@'localhost' IDENTIFIED BY '1234';
GRANT EXECUTE ON PROCEDURE GenerateBills TO 'bills'@'localhost';


/*2) La facturación se realizará por un proceso automático en la base de datos. Se
debe programar este proceso para el primer día de cada mes y debe generar una
factura por medidor y debe tomar en cuenta todas las mediciones no facturadas
para cada uno de los medidores, sin tener en cuenta su fecha. La fecha de vencimiento de
esta factura será estipulado a 15 días.*/

DROP PROCEDURE  IF EXISTS GenerateBills;
DELIMITER //
CREATE PROCEDURE GenerateBills()
BEGIN
      DECLARE vFinished INTEGER DEFAULT 0; /*variable para controlar los erorres, para cortar el cursor*/

      /* variables que voy a necesitar, para obtener el resto de los datos*/
      DECLARE vserial_number VARCHAR(50) ;
      DECLARE vid_address INT;
      DECLARE vid_fee INT;
      DECLARE vid_client INT;


     /*declare el cursor*/
      DECLARE cur_billing CURSOR FOR
SELECT m.serial_number, m.id_address, m.id_fee, c.id
FROM meters m
         INNER JOIN addresses a ON m.id_address = a.id_address
         INNER JOIN clients c ON a.id_client = c.id;

/* declaro un handler para maejar las execpciones dentro, con CONTINUE para que siga la ejecucion en caso de una excepcion.
   Modifico vFinished=1*/
DECLARE CONTINUE HANDLER FOR NOT FOUND SET vFinished = 1;

	/*comienzo una transaction, ya que una vez tomados los datos los que ingresen despues no los tendre en cuenta, de esta manera creo una unidad unica de trabajo
	la cual no podra ser modificada por otra transaccion y se procurara la consistencia de la informacion*/
START TRANSACTION;

OPEN cur_billing;

FETCH cur_billing INTO vserial_number, vid_address, vid_fee, vid_client;

WHILE (vFinished=0) DO

		        CALL GenerateBill(vserial_number, vid_address, vid_fee, vid_client);

FETCH cur_billing INTO vserial_number, vid_address, vid_fee, vid_client;
END WHILE;
CLOSE cur_billing;

/*cierro la transaccicion*/
COMMIT;
END //

/********************* procedure generateBill para el cursor *********************/

DROP PROCEDURE  IF EXISTS GenerateBill;
DELIMITER //
CREATE PROCEDURE GenerateBill(IN serial_numberP VARCHAR(50), IN id_addressP INT, IN id_feeP INT, IN id_clientP INT)
BEGIN
        DECLARE amountR FLOAT;/*lo calculo*/
        DECLARE total_kwhR FLOAT;/*lo calculo*/
        DECLARE first_measurementR DATETIME; /*dato que tengo que encontrar mientras agrego*/
        DECLARE last_measurementR DATETIME; /*dato que tengo que encontrar mientras agrego*/
        DECLARE date_createR DATE;
        DECLARE expirationR DATE;

        SET date_createR = NOW();
        SET expirationR =  DATE_ADD(date_createR,INTERVAL 15 DAY);


SELECT SUM(price_measuring) INTO amountR
FROM measurings
WHERE serial_number = serial_numberP
GROUP BY serial_number;

SELECT SUM(VALUE) INTO total_kwhR
FROM measurings
WHERE serial_number = serial_numberP
GROUP BY serial_number;

SELECT MAX(DATE) INTO last_measurementR
FROM measurings
WHERE serial_number = serial_numberP AND id_bill IS NULL
GROUP BY serial_number;
SELECT MIN(DATE) INTO first_measurementR
FROM measurings
WHERE serial_number = serial_numberP AND id_bill IS NULL
GROUP BY serial_number;


INSERT INTO bills(amount, total_kwh, pay, date_create, expiration, first_measurement, last_measurement, id_client, id_address)
    VALUE (amountR, total_kwhR, FALSE ,date_createR, expirationR, first_measurementR, last_measurementR,  id_clientP, id_addressP);

/*Actualizar las measuring */
UPDATE measurings SET id_bill = LAST_INSERT_ID()
WHERE serial_number = serial_numberP AND id_bill IS NULL;
END//

       CALL GenerateBill("66666", 1, 1, 1);

/******************************************************/
DROP PROCEDURE  IF EXISTS GenerateBillAdjustment;
DELIMITER //
CREATE PROCEDURE GenerateBillAdjustment(IN serial_numberP VARCHAR(50), IN id_addressP INT, IN id_feeP INT, IN id_clientP INT)
BEGIN
        DECLARE amountR FLOAT;/*lo calculo*/
        DECLARE total_kwhR FLOAT;/*lo calculo*/
        DECLARE first_measurementR DATETIME; /*dato que tengo que encontrar mientras agrego*/
        DECLARE last_measurementR DATETIME; /*dato que tengo que encontrar mientras agrego*/
        DECLARE date_createR DATE;
        DECLARE expirationR DATE;
        DECLARE amount_bill FLOAT;

        SET date_createR = NOW();
        SET expirationR =  DATE_ADD(date_createR,INTERVAL 15 DAY);


SELECT SUM(price_measuring) INTO amountR
FROM measurings
WHERE serial_number = serial_numberP
GROUP BY serial_number;

SELECT SUM(b.amount) INTO amount_bill
FROM bills b
WHERE b.id_address = id_addressP AND b.pay = FALSE;

SET amountR = amountR - amount_bill;

SELECT SUM(VALUE) INTO total_kwhR
FROM measurings
WHERE serial_number = serial_numberP
GROUP BY serial_number;

SELECT MAX(DATE) INTO last_measurementR
FROM measurings
WHERE serial_number = serial_numberP
GROUP BY serial_number;
SELECT MIN(DATE) INTO first_measurementR
FROM measurings
WHERE serial_number = serial_numberP
GROUP BY serial_number;


INSERT INTO bills(amount, total_kwh, pay, date_create, expiration, first_measurement, last_measurement, id_client, id_address)
    VALUE (amountR, total_kwhR, FALSE ,date_createR, expirationR, first_measurementR, last_measurementR,  id_clientP, id_addressP);

/*Actualizar las measuring */
UPDATE measurings SET id_bill = LAST_INSERT_ID()
WHERE serial_number = serial_numberP AND id_bill IS NULL;
END//
/******************************************************/


/*              EVENTO          */
/* despues de activar esta variable funciona*/

SET GLOBAL event_scheduler = ON;

/* Me crea el evento que sera automatico, le especifico el nombre, y a partir desde cuando y cada cuanto*/
CREATE EVENT EGenerateBills
ON SCHEDULE EVERY 1 MONTH STARTS '2021-06-01 00:00:01'
DO CALL GenerateBills();

/* para eliminar un evento*/
DROP EVENT IF EXISTS EGenerateBills;

/* Veo los eventos que estan en lista*/
SHOW EVENTS;
 /* para ver lo que se esta trabajanndo*/


/*
3) Generar las estructuras necesarias para el cálculo de precio de cada medición y las
inserción de la misma. Se debe tener en cuenta que una modificación en la tarifa debe
modificar el precio de cada una de estas mediciones en la base de datos y generar una
factura de ajuste a la nueva medición de cada una de las mediciones involucradas con esta
tarifa.

*/

DROP TRIGGER IF EXISTS TIA_MEASURINGS_CALC_PRECIO;
DELIMITER //
CREATE TRIGGER TIB_MEASURINGS_CALC_PRECIO BEFORE INSERT ON measurings FOR EACH ROW
BEGIN
    DECLARE price FLOAT DEFAULT 0;

    SELECT fe.price_fee INTO price
    FROM fees fe
             INNER JOIN meters met ON fe.id_fee = met.id_fee
    WHERE met.serial_number = new.serial_number;

    SET new.price_measuring = price * new.value;
END//
 /*
3) Generar las estructuras necesarias para el cálculo de precio de cada medición y las
inserción de la misma. Se debe tener en cuenta que una modificación en la tarifa debe
modificar el precio de cada una de estas mediciones en la base de datos y generar una
factura de ajuste a la nueva medición de cada una de las mediciones involucradas con esta
tarifa.

*/
DROP TRIGGER IF EXISTS TIB_UPDATE_FEE;
DELIMITER //
CREATE TRIGGER TIB_UPDATE_FEE BEFORE UPDATE ON fees FOR EACH ROW
BEGIN

    DECLARE vid_client INT;
      DECLARE vserial_number VARCHAR(50);
      DECLARE vid_address INT;

      DECLARE vFinished INTEGER DEFAULT 0;


      DECLARE cur_adjustment CURSOR FOR

    SELECT ad.id_client, met.serial_number, ad.id_address
    FROM addresses ad
             INNER JOIN meters met ON ad.id_address = met.id_address
    WHERE met.id_fee = new.id_fee;

    DECLARE CONTINUE HANDLER FOR NOT FOUND SET vFinished = 1;
    OPEN cur_adjustment;

    FETCH cur_adjustment INTO vid_client, vserial_number, vid_address;
    WHILE (vFinished=0) DO

    UPDATE measurings  SET price_measuring = VALUE * new.price_fee
    WHERE serial_number= vserial_number;

    CALL GenerateBillAdjustment(vserial_number, vid_address, new.id_fee, vid_client);

    FETCH cur_adjustment INTO vid_client, vserial_number, vid_address;
END WHILE;

CLOSE cur_adjustment;

END;//

 END;//

/*4) Generar las estructuras necesarias para dar soporte a las consultas de mediciones
por fecha y por usuario , debido a que tenemos restricción de que estas no pueden demorar
más de dos segundos y tenemos previsto que tendremos 500.000.000 de mediciones en el
sistema en el mediano plazo. */

/* INDICES  */

CREATE INDEX idx_measurings_serial_number_date
ON measurings (serial_number, `date`)
USING BTREE;

DELIMITER//
CREATE PROCEDURE findMeasuringsIndex(IN beginDate DATE, IN endDate DATE, IN idClient INT)
BEGIN
SELECT c.name, met.serial_number, mea.date, mea.value, mea.price_measuring
FROM measurings AS mea
         INNER JOIN meters AS met ON mea.serial_number= met.serial_number
         INNER JOIN addresses AS ad ON ad.id_address = met.id_address
         INNER JOIN clients AS c ON c.id = ad.id_client
WHERE c.id=idClient AND mea.date BETWEEN beginDate AND endDate;
END;//

CALL findMeasuringsIndex('2019-06-06', '2025-06-06', 3);

EXPLAIN EXTENDED SELECT * FROM measurings AS mea
                                       INNER JOIN meters AS met ON mea.serial_number= met.serial_number
                                       INNER JOIN addresses AS ad ON ad.id_address = met.id_address
                                       INNER JOIN clients AS c ON c.id = ad.id_client
                 WHERE c.id=3 AND mea.date BETWEEN '2019-06-06' AND '2025-06-06';

