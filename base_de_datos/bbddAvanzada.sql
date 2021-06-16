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
DROP DATABASE adea;

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
                      pay BOOLEAN,
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
                           price_measuring double,
                           CONSTRAINT pk_id_measuring PRIMARY KEY (id_measuring),
                           CONSTRAINT fk_id_bill FOREIGN KEY (id_bill) REFERENCES bills(id_bill),
                           CONSTRAINT fk_serial_number FOREIGN KEY (serial_number) REFERENCES meters(serial_number)
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


/*2) La facturación se realizará por un proceso automático en la base de datos. Se
debe programar este proceso para el primer día de cada mes y debe generar una
factura por medidor y debe tomar en cuenta todas las mediciones no facturadas
para cada uno de los medidores, sin tener en cuenta su fecha. La fecha de vencimiento de
esta factura será estipulado a 15 días.*/

DROP PROCEDURE  IF EXISTS GenerateBills
DELIMITER //
CREATE PROCEDURE GenerateBills()
BEGIN
      DECLARE amountR FLOAT;/*lo calculo*/
      DECLARE first_measurementR DATETIME; /*dato que tengo que encontrar mientras agrego*/
      DECLARE last_measurementR DATETIME; /*dato que tengo que encontrar mientras agrego*/
      DECLARE id_clientR INT;/*dato que tengo que encontrar mientras agrego*/

      DECLARE vFinished INTEGER DEFAULT 0; /*variable para controlar los erorres, para cortar el cursor*/

      /* variables que voy a necesitar, para obtener el resto de los datos*/
      DECLARE vserial_number VARCHAR(50) ;
      DECLARE vid_address INT;
      DECLARE vid_fee INT;

     /*declare el cursor*/
      DECLARE cur_billing CURSOR FOR
      SELECT serial_number, id_address, id_fee
      FROM meters ;

      /* declaro un handler para maejar las execpciones dentro, con CONTINUE para que siga la ejecucion en caso de una excepcion.
         Modifico vFinished=1*/
      DECLARE CONTINUE HANDLER FOR NOT FOUND SET vFinished = 1;


	    OPEN cur_billing;
		    FETCH cur_billing INTO vserial_number, vid_address, vid_fee;
		    WHILE (vFinished=0) DO

		        SET id_clientR= ( SELECT id_client FROM addresses
		        WHERE  id_address = vid_address);


			SET amountR = (SELECT SUM(VALUE) FROM measurings
				WHERE id_bill IS NULL AND serial_number = vserial_number
				GROUP BY serial_number)* (SELECT price_fee FROM fees WHERE id_fee = vid_fee);

			SET last_measurementR = (  SELECT MAX(DATE)
							 FROM measurings
							 WHERE serial_number = vserial_number AND id_bill IS NULL
							 GROUP BY serial_number);
		       SET first_measurementR = (  SELECT MIN(DATE)
							 FROM measurings
							 WHERE serial_number = vserial_number AND id_bill IS NULL
							 GROUP BY serial_number);

		        /*INSERTO LA BILL, CON PARCIALMENTE LOS DATOS*/
		         INSERT INTO bills(amount, pay, first_measurement, last_measurement, id_client)
		          VALUE (amountR, FALSE , first_measurementR, last_measurementR,  id_clientR);

			/*Actualizar las measuring */
			UPDATE measurings SET id_bill = LAST_INSERT_ID()
			WHERE serial_number = vserial_number AND id_bill IS NULL;

		       FETCH cur_billing INTO vserial_number, vid_address, vid_fee;
		    END WHILE;
	    CLOSE cur_billing;


END //
SELECT * FROM fees
/* despues de activar esta variable funciona*/

SET GLOBAL event_scheduler = ON;

/* Me crea el evento que sera automatico, le especifico el nombre, y a partir desde cuando y cada cuanto*/
CREATE EVENT EGenerateBills
ON SCHEDULE EVERY 1 MINUTE STARTS NOW()
DO CALL GenerateBills();

/* para eliminar un evento*/
DROP EVENT IF EXISTS EGenerateBills;

/* Veo los eventos que estan en lista*/
SHOW EVENTS;
 /* para ver lo que se esta trabajanndo*/

SHOW WARNINGS

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
 END;//

