CREATE DATABASE `impact_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
use impact_db;

-- USER ROLE --

CREATE TABLE user_role (
   id          INT AUTO_INCREMENT PRIMARY KEY,
   name 	   VARCHAR(50) NOT NULL UNIQUE,
   description TEXT
);

INSERT INTO user_role (name, description) VALUES 
('Administrador', 'Tiene la capacidad de recibir más informes y acceder a la gestión de usuarios.'),
('Gerente', 'No puede recibir más informes y no tiene acceso a la gestión de usuarios.'),
('Profesor', 'Usuario estándar sin privilegios especiales.');


select * from user_role;

-- USER STATE --

CREATE TABLE user_state (
   id          INT AUTO_INCREMENT PRIMARY KEY,
   name        VARCHAR(50) NOT NULL UNIQUE,
   description TEXT
);

INSERT INTO user_state (name, description) VALUES 
('activo', 'El usuario está activo y puede iniciar sesión'),
('inactivo', 'El usuario no puede acceder al sistema'),
('suspendido', 'El usuario está temporalmente suspendido');

select * from user_state;

-- USER --

CREATE TABLE user (
    id        INT AUTO_INCREMENT PRIMARY KEY,
    name      VARCHAR(100) NOT NULL,
    email     VARCHAR(100) NOT NULL UNIQUE,
    password  VARCHAR(100) NOT NULL,
    role_id   INT,
    state_id  INT,
    FOREIGN KEY (role_id) REFERENCES user_role(id),
    FOREIGN KEY (state_id) REFERENCES user_state(id)
);

select * from user;

-- USER TOKEN --

CREATE TABLE user_token (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    user_id     INT NOT NULL,
    token       VARCHAR(255) NOT NULL,
    expiry_date TIMESTAMP NOT NULL,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(id)
);

-- REQUEST_STATUS

CREATE TABLE request_status (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    status_name VARCHAR(50) NOT NULL UNIQUE,
    description TEXT
);

INSERT INTO request_status (status_name, description) VALUES 
('Pendiente', 'La solicitud está pendiente de revisión.'),
('Aprobada', 'La solicitud ha sido aprobada.'),
('Rechazada', 'La solicitud ha sido rechazada.'),
('Completada', 'La solicitud ha sido completada y todas las tareas asociadas han finalizado.'),
('Cancelada', 'La solicitud ha sido cancelada y no será procesada.');


select * from request_status;

-- REQUEST --

CREATE TABLE request (
    id       INT AUTO_INCREMENT PRIMARY KEY,
    user_id  INT,
    date     DATE,
    status_id INT,
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (status_id) REFERENCES request_status(id)
);

-- SUPPLIER -- 

CREATE TABLE supplier (
    id                     INT AUTO_INCREMENT PRIMARY KEY,
    name                   VARCHAR(100) NOT NULL,
    phone                  VARCHAR(100),
    email                  VARCHAR(100),
    address                TEXT,
    entity_type_id         INT,  -- Identifica si la cédula es física o jurídica
    client_contact         VARCHAR(100), -- Contacto de algún cliente del proveedor
    FOREIGN KEY (entity_type_id) REFERENCES entity_type(id)
);


-- CATEGORY -- 

CREATE TABLE asset_category (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(100) NOT NULL
);

DROP TABLE asset_category;

-- BRAND -- 

CREATE TABLE brand (
    id   INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

-- building -- 

CREATE TABLE building (
    id        INT AUTO_INCREMENT PRIMARY KEY,
    name      VARCHAR(100) NOT NULL UNIQUE
);

-- buildgin_location --

CREATE TABLE building_location (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    building_id INT NOT NULL,
    floor       VARCHAR(50) NOT NULL,
    FOREIGN KEY (building_id) REFERENCES building(id)
);

-- SPACE TYPE --

CREATE TABLE space_type (
    id   INT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(50) NOT NULL UNIQUE
);

-- SPACE --

CREATE TABLE space (
    id              INT AUTO_INCREMENT PRIMARY KEY,
    name            VARCHAR(100) NOT NULL,
    space_code      INT UNIQUE,
    location_id     INT NOT NULL,
    max_people      INT,
    type_id         INT NOT NULL,
    status_id       INT, -- Referencia al estado del espacio
    is_deleted       BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (location_id) REFERENCES building_location(id),
    FOREIGN KEY (type_id) REFERENCES space_type(id),
    FOREIGN KEY (status_id) REFERENCES space_status(id) -- Clave foránea para status
);

DROP TABLE space;

-- SPACE STATUS -- 

CREATE TABLE space_status (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    description TEXT
);

-- Inserción de datos predefinidos para los estados de space
INSERT INTO space_status (name, description) VALUES 
('Disponible', 'El espacio está disponible para su uso.'),
('Ocupado', 'El espacio está actualmente ocupado.'),
('En Mantenimiento', 'El espacio está en mantenimiento y no está disponible para su uso.'),
('Fuera de Servicio', 'El espacio ya no está disponible o no está operativo.');


-- SPACE EQUIPMENT --

CREATE TABLE space_equipment (
    id         INT AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(100) NOT NULL,
    brand_id   INT NOT NULL,
    space_id   INT NOT NULL,
    quantity   INT NOT NULL,
    FOREIGN KEY (brand_id) REFERENCES brand(id),
    FOREIGN KEY (space_id) REFERENCES space(id)
);

DROP TABLE space_equipment;

-- SPACE RESERVATION --

CREATE TABLE space_reservation (
    id           INT AUTO_INCREMENT PRIMARY KEY,
    space_id     INT,
    start_time   DATE,
    end_time	 DATE,
    FOREIGN KEY (space_id) REFERENCES space(id)
);

DROP TABLE space_reservation;

-- SPACE REQUEST -- 

CREATE TABLE space_request (
    id         INT AUTO_INCREMENT PRIMARY KEY,
    request_id INT,
    space_id   INT,
    num_people INT,
    event_desc INT,
    status_id  INT,
    UNIQUE KEY (request_id, space_id),
    FOREIGN KEY (request_id) REFERENCES request(id),
    FOREIGN KEY (space_id) REFERENCES space(id),
    FOREIGN KEY (status_id) REFERENCES resource_request_status(id)
);

DROP TABLE space_request;

-- INVOICE --

CREATE TABLE invoices (
    id            			 INT AUTO_INCREMENT PRIMARY KEY,
    asset_id      			 INT,
    image_path    			 VARCHAR(50),
    purchase_date 			 DATE,
    warranty_expiration_date DATE,
    FOREIGN KEY (asset_id) REFERENCES asset(id)
);

drop table invoices;

-- CATEGORY TYPE --

CREATE TABLE category_type (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(100) NOT NULL UNIQUE
);

INSERT INTO category_type (name, description) 
VALUES 
    ('Limpieza', 'Productos para limpieza'),
    ('Oficina', 'Productos de oficina');


-- UNIT OF MEASUREMENT --

CREATE TABLE unit_of_measurement (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(50) NOT NULL UNIQUE,
    abbreviation VARCHAR(10)
);

INSERT INTO unit_of_measurement (name, abbreviation) 
VALUES 
    ('Paquete', 'PQT'),
    ('Galón', 'GLN'),
    ('Caja', 'CJ'),
    ('Unidad', 'UND');


-- PRODUCT CATEGORY --

CREATE TABLE product_category (
    id                   INT AUTO_INCREMENT PRIMARY KEY,
    name                 VARCHAR(100),
    cantidad_minima      INT NOT NULL,
    category_type       INT,
    unit_of_measurement  INT,
    FOREIGN KEY (category_type) REFERENCES category_type(id),
    FOREIGN KEY (unit_of_measurement) REFERENCES unit_of_measurement(id)
);

DROP TABLE product_type;


-- PRODUCT -- 

CREATE TABLE product (
    id              INT AUTO_INCREMENT PRIMARY KEY,
    purchase_date   DATE,
    expiry_date     DATE,
    category_id    INT,
    status          INT,
    CONSTRAINT FOREIGN KEY (status) REFERENCES product_status(id),
    CONSTRAINT FOREIGN KEY (category_id) REFERENCES product_category(id)
);

drop table product;

-- PRODCUT STATUS -- 

CREATE TABLE product_status(
    id INT AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL UNIQUE,
    description TEXT, 
    CONSTRAINT PRIMARY KEY (id)
);

INSERT INTO product_status (name, description) VALUES 
('Disponible', 'El producto está disponible para solicitar.'),
('Pendiente', 'El producto está pendiente de ser entregado o procesado.'),
('Prestado', 'El producto ha sido entregado en préstamo.');


-- RESOURCE REQUEST STATUS -- 

CREATE TABLE resource_request_status (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(50) NOT NULL UNIQUE,
    description TEXT
);

INSERT INTO resource_request_status (name, description) VALUES 
('Pendiente', 'El producto está pendiente de ser entregado o procesado.'),
('Emitido', 'El producto ha sido emitido o prestado.'),
('Devuelto', 'El producto ha sido devuelto.'),
('Cancelado', 'La solicitud del producto ha sido cancelada.'),
('Disponible', 'El producto está disponible para solicitar.');


-- PRODUCTO REQUEST -- 

CREATE TABLE product_request (
    id              INT AUTO_INCREMENT PRIMARY KEY,
    request_id      INT,
    product_id      INT,
    status_id       INT,
    UNIQUE KEY (request_id, product_id),
    FOREIGN KEY (request_id) REFERENCES request(id),
    FOREIGN KEY (product_id) REFERENCES product(id),
    FOREIGN KEY (status_id) REFERENCES resource_request_status(id)
);

drop table product_request;


-- ASSET -- 

CREATE TABLE asset (
    id                 INT AUTO_INCREMENT PRIMARY KEY,
    purchase_date      DATE,
    value              DECIMAL(10, 2),
    responsible_id     INT,
    supplier_id        INT,
    category_id        INT,
    brand_id           INT,
    status_id          INT,
    is_deleted          BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (responsible_id) REFERENCES user(id),
    FOREIGN KEY (supplier_id) REFERENCES supplier(id),
    FOREIGN KEY (category_id) REFERENCES asset_category(id),
    FOREIGN KEY (brand_id) REFERENCES brand(id),
    FOREIGN KEY (status_id) REFERENCES asset_status(id)
);


drop table asset;

-- ASSET STATUS --

CREATE TABLE asset_status (
    id INT AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL UNIQUE,
    description TEXT, 
    CONSTRAINT PRIMARY KEY (id)
);

INSERT INTO asset_status (name, description) VALUES 
('Disponible', 'El activo está disponible para su uso.'),
('En Mantenimiento', 'El activo está en mantenimiento.'),
('Prestado', 'El activo ha sido prestado a alguien.'),
('Fuera de Servicio', 'El activo ya no está operativo o en uso.');



-- ASSET_REQUEST -- 

CREATE TABLE asset_request (
    id              INT AUTO_INCREMENT PRIMARY KEY,
    request_id      INT,
    asset_id        INT,
    status_id       INT,
    expiration_date DATE,
    UNIQUE KEY (request_id, asset_id),
    FOREIGN KEY (request_id) REFERENCES request(id),
    FOREIGN KEY (asset_id) REFERENCES asset(id),
    FOREIGN KEY (status_id) REFERENCES resource_request_status(id)
);

drop table asset_request;

-- TRANSACTION TYPE -- 

CREATE TABLE transaction_type (
    id               INT AUTO_INCREMENT PRIMARY KEY,
    type_name        VARCHAR(50) NOT NULL UNIQUE,
    description      TEXT
);

-- Inserción de datos predefinidos (CRUD y otros estados)
INSERT INTO transaction_type (type_name, description) VALUES
('Crear', 'Representa la creación de un nuevo registro o entidad.'),
('Leer', 'Representa la recuperación de información o datos.'),
('Actualizar', 'Representa la actualización o modificación de un registro existente.'),
('Eliminar', 'Representa la eliminación de un registro o entidad.');


-- ASSET MOVEMENTS --

CREATE TABLE asset_movements (
    id               INT AUTO_INCREMENT PRIMARY KEY,
    asset_id         INT, 
    transaction_id   INT,
    date             DATE,
    FOREIGN KEY (asset_id) REFERENCES asset(id),
    FOREIGN KEY (transaction_id) REFERENCES transaction_type(id)
);

drop table asset_movements;

-- PRODUCT MOVEMENTS --

CREATE TABLE product_movements(
   id            INT AUTO_INCREMENT PRIMARY KEY,
   product_id    INT, 
   transaction_id   INT,
   date          DATE,
   FOREIGN KEY (product_id) REFERENCES product(id),
   FOREIGN KEY (transaction_id) REFERENCES transaction_type(id)
);

drop table product_movements;

-- SPACE MOVEMENTS --

CREATE TABLE space_movements(
   id                 INT AUTO_INCREMENT PRIMARY KEY,
   reserved_space_id INT, 
   transaction_id     INT,
   date               DATE,
   FOREIGN KEY (reserved_space_id) REFERENCES space_reservation(id),
   FOREIGN KEY (transaction_id) REFERENCES transaction_type(id)
);


drop table space_movements;


-------------------------


-- Tabla para almacenar tipos de entidad (física o jurídica)
CREATE TABLE entity_type (
    id INT AUTO_INCREMENT PRIMARY KEY,
    type_name VARCHAR(50) NOT NULL
);

INSERT INTO entity_type (type_name) VALUES 
('Física'),
('Jurídica');

-- Tabla para almacenar monedas
CREATE TABLE currency (
    id INT AUTO_INCREMENT PRIMARY KEY,
    currency_code VARCHAR(10) NOT NULL,
    currency_name VARCHAR(50) NOT NULL
);

INSERT INTO currency (currency_code, currency_name) VALUES 
('CRC', 'Colones'),
('USD', 'Dólares');

-- Tabla para almacenar modelos de activos
CREATE TABLE asset_model (
    id INT AUTO_INCREMENT PRIMARY KEY,
    model_name VARCHAR(100) NOT NULL
);

-- Tabla para almacenar cuentas de proveedores
CREATE TABLE supplier_account (
    id INT AUTO_INCREMENT PRIMARY KEY,
    supplier_id INT,
    account_number VARCHAR(50) NOT NULL,
    FOREIGN KEY (supplier_id) REFERENCES supplier(id)
);


-- NUEVO ACTIVO 
CREATE TABLE asset (
    id                 INT AUTO_INCREMENT PRIMARY KEY,
    purchase_date      DATE,
    value              DECIMAL(10, 2),
    responsible_id     INT,
    supplier_id        INT,
    category_id        INT,
    brand_id           INT,
    status_id          INT,
    is_deleted         BOOLEAN DEFAULT FALSE,
    asset_series       VARCHAR(50),
    plate_number       VARCHAR(50),
    asset_model_id     INT,
    FOREIGN KEY (responsible_id) REFERENCES user(id),
    FOREIGN KEY (supplier_id) REFERENCES supplier(id),
    FOREIGN KEY (category_id) REFERENCES asset_category(id),
    FOREIGN KEY (brand_id) REFERENCES brand(id),
    FOREIGN KEY (status_id) REFERENCES asset_status(id),
    FOREIGN KEY (currency_id) REFERENCES currency(id),
    FOREIGN KEY (asset_model_id) REFERENCES asset_model(id)
);


alter table asset
ADD COLUMN asset_series VARCHAR(50),
ADD COLUMN plate_number VARCHAR(50),
ADD COLUMN entity_type_id INT,
ADD COLUMN currency_id INT,
ADD COLUMN asset_model_id INT,
ADD FOREIGN KEY (entity_type_id) REFERENCES entity_type(id),
ADD FOREIGN KEY (currency_id) REFERENCES currency(id),
ADD FOREIGN KEY (asset_model_id) REFERENCES asset_model(id);


-- NUEVO

CREATE TABLE asset_category (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    subcategory_id INT,
    FOREIGN KEY (subcategory_id) REFERENCES asset_subcategory(id)
);

CREATE TABLE asset_subcategory (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    description VARCHAR(255)
);

drop table asset_category;
