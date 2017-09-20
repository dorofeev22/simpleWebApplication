DROP DATABASE medlinesoft;
CREATE DATABASE medlinesoft
  WITH OWNER = postgres
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'Russian_Russia.1251'
       LC_CTYPE = 'Russian_Russia.1251'
       CONNECTION LIMIT = -1;
\connect medlinesoft;
CREATE SCHEMA medlinesoft;

CREATE TABLE medlinesoft.part
(
  id serial NOT NULL,
  part_name character varying(255),
  part_number character varying(255),
  vendor character varying(255),
  qty integer,
  shipped date,
  receive date,
  CONSTRAINT part_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE medlinesoft.part
  OWNER TO postgres;

CREATE INDEX part_name_idx
  ON medlinesoft.part
  USING btree
  (part_name COLLATE pg_catalog."default");

CREATE INDEX part_number_idx
  ON medlinesoft.part
  USING btree
  (part_number COLLATE pg_catalog."default");

CREATE INDEX part_vendor_idx
  ON medlinesoft.part
  USING btree
  (vendor COLLATE pg_catalog."default");

CREATE INDEX part_qty_idx
  ON medlinesoft.part
  USING btree
  (qty);

CREATE INDEX part_shipped_idx
  ON medlinesoft.part
  USING btree
  (shipped);

CREATE INDEX part_receive_idx
  ON medlinesoft.part
  USING btree
  (receive);

INSERT INTO medlinesoft.part(part_name, part_number, vendor, qty, shipped, receive)
    VALUES 
('first part', 1, 'Toyota', 100, '2017-02-06', '2017-03-15'),
('second part', 2, 'Daewoo', 750, '2017-05-12', '2017-09-25'),
('third part', 3, 'Opel', 20, '2017-01-26', '2017-03-18'),
('fourth part', 4, 'BMW', 34, '2017-04-08', '2017-06-25'),
('fifth part', 5, 'Ford', 2, '2017-05-22', '2017-07-05');
