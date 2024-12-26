CREATE
DATABASE IF NOT EXISTS impact_db;
use
impact_db;

CREATE TABLE user_role
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(50) NOT NULL UNIQUE,
    description TEXT
);

INSERT INTO user_role (name, description)
VALUES ('ROLE_ADMINISTRATOR', 'TODO'),
       ('ROLE_MANAGER', 'TODO'),
       ('ROLE_TEACHER', 'TODO');

CREATE TABLE user_state
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(50) NOT NULL UNIQUE,
    description TEXT
);

INSERT INTO user_state (name, description)
VALUES ('STATE_ACTIVE', 'El usuario está activo y puede iniciar sesión'),
       ('STATE_INACTIVE', 'El usuario no puede acceder al sistema'),
       ('STATE_SUSPENDED', 'El usuario está temporalmente suspendido');

CREATE TABLE user
(
    id       INT AUTO_INCREMENT PRIMARY KEY,
    name     VARCHAR(100) NOT NULL,
    email    VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role_id  INT,
    state_id INT,
    FOREIGN KEY (role_id) REFERENCES user_role (id),
    FOREIGN KEY (state_id) REFERENCES user_state (id)
);

CREATE TABLE user_token
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    user_id     INT          NOT NULL,
    token       VARCHAR(255) NOT NULL,
    expiry_date TIMESTAMP    NOT NULL,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user (id)
);

CREATE TABLE audit_log
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    entity_name VARCHAR(50) NOT NULL,
    action      VARCHAR(50) NOT NULL,
    details     TEXT,
    timestamp   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    user_id     INT,
    FOREIGN KEY (user_id) REFERENCES user (id)
);

CREATE TABLE request_status
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    status_name VARCHAR(50) NOT NULL UNIQUE,
    description TEXT
);

INSERT INTO request_status (status_name, description)
VALUES ('Pendiente', 'La solicitud está pendiente de revisión.'),
       ('Aprobada', 'La solicitud ha sido aprobada.'),
       ('Rechazada', 'La solicitud ha sido rechazada.'),
       ('Completada', 'La solicitud ha sido completada y todas las tareas asociadas han finalizado.'),
       ('Cancelada', 'La solicitud ha sido cancelada y no será procesada.');

CREATE TABLE resource_request_status
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(50) NOT NULL UNIQUE,
    description TEXT
);

INSERT INTO resource_request_status (name, description)
VALUES ('Pendiente', 'Está pendiente de ser entregado o procesado.'),
       ('Emitido', 'Ha sido emitido o prestado.'),
       ('Devuelto', 'Ha sido devuelto.'),
       ('Cancelado', 'Ha sido cancelada.'),
       ('Disponible', 'Está disponible para solicitar.');

CREATE TABLE request
(
    id        INT AUTO_INCREMENT PRIMARY KEY,
    user_id   INT,
    date      DATE,
    status_id INT,
    FOREIGN KEY (user_id) REFERENCES user (id),
    FOREIGN KEY (status_id) REFERENCES request_status (id)
);

CREATE TABLE entity_type
(
    id        INT AUTO_INCREMENT PRIMARY KEY,
    type_name VARCHAR(50) NOT NULL
);

INSERT INTO entity_type (type_name)
VALUES ('Física'),
       ('Jurídica');

CREATE TABLE supplier
(
    id             INT AUTO_INCREMENT PRIMARY KEY,
    name           VARCHAR(100) NOT NULL,
    phone          VARCHAR(100),
    email          VARCHAR(100),
    address        TEXT,
    entity_type_id INT,
    client_contact VARCHAR(100),
    id_number      VARCHAR(50) UNIQUE NOT NULL,
    FOREIGN KEY (entity_type_id) REFERENCES entity_type (id)
);

CREATE TABLE asset_category
(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

ALTER TABLE asset_category
    ADD CONSTRAINT unique_asset_category_name UNIQUE (name);

CREATE TABLE asset_subcategory
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(255),
    category_id INT,
    FOREIGN KEY (category_id) REFERENCES asset_category (id)
);

ALTER TABLE asset_subcategory
    ADD CONSTRAINT uc_subcategory_name UNIQUE (name);

CREATE TABLE brand
(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

ALTER TABLE brand
    ADD CONSTRAINT unique_brand_name UNIQUE (name);

CREATE TABLE building
(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE building_location
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    building_id INT         NOT NULL,
    floor       VARCHAR(50) NOT NULL,
    FOREIGN KEY (building_id) REFERENCES building (id),
    UNIQUE (building_id, floor)
);

ALTER TABLE building_location
    ADD CONSTRAINT unique_building_floor UNIQUE (building_id, floor);

CREATE TABLE space
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    space_code  INT UNIQUE,
    location_id INT          NOT NULL,
    max_people  INT,
    open_time   TIME         NOT NULL,
    close_time  TIME         NOT NULL,
    status_id   INT,
    is_deleted  BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (location_id) REFERENCES building_location (id),
    FOREIGN KEY (status_id) REFERENCES space_status (id)
);

CREATE TABLE space_status
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(50) NOT NULL UNIQUE,
    description TEXT
);

INSERT INTO space_status (name, description)
VALUES ('Disponible', 'El espacio está disponible para su uso.'),
       ('Ocupado', 'El espacio está actualmente ocupado.'),
       ('En Mantenimiento', 'El espacio está en mantenimiento y no está disponible para su uso.'),
       ('Fuera de Servicio', 'El espacio ya no está disponible o no está operativo.');

CREATE TABLE space_equipment
(
    id       INT AUTO_INCREMENT PRIMARY KEY,
    name     VARCHAR(100) NOT NULL,
    brand_id INT          NOT NULL,
    space_id INT          NOT NULL,
    quantity INT          NOT NULL,
    FOREIGN KEY (brand_id) REFERENCES brand (id),
    FOREIGN KEY (space_id) REFERENCES space (id)
);

CREATE TABLE space_reservation
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    space_id   INT,
    start_time DATETIME,
    end_time   DATETIME,
    FOREIGN KEY (space_id) REFERENCES space (id)
);

CREATE TABLE space_request
(
    id            INT AUTO_INCREMENT PRIMARY KEY,
    request_id    INT,
    space_id      INT,
    num_people    INT,
    event_desc    VARCHAR(255),
    event_obs     VARCHAR(255),
    status_id     INT,
    use_equipment TINYINT(1) DEFAULT 0,
    UNIQUE KEY (request_id, space_id),
    FOREIGN KEY (request_id) REFERENCES request (id),
    FOREIGN KEY (space_id) REFERENCES space (id),
    FOREIGN KEY (status_id) REFERENCES resource_request_status (id)
);

CREATE TABLE location_type
(
    id        INT AUTO_INCREMENT PRIMARY KEY,
    type_name VARCHAR(100) NOT NULL
);

ALTER TABLE location_type
    ADD CONSTRAINT unique_location_type_name UNIQUE (type_name);

CREATE TABLE location_number
(
    id               INT AUTO_INCREMENT PRIMARY KEY,
    location_type_id INT,
    location_number  INT,
    FOREIGN KEY (location_type_id) REFERENCES location_type (id)
);

ALTER TABLE location_number
    ADD CONSTRAINT unique_location_number UNIQUE (location_type_id, location_number);

CREATE TABLE currency
(
    id            INT AUTO_INCREMENT PRIMARY KEY,
    currency_code VARCHAR(10) NOT NULL,
    currency_name VARCHAR(50) NOT NULL
);

INSERT INTO currency (currency_code, currency_name)
VALUES ('CRC', 'Colones'),
       ('USD', 'Dólares');

CREATE TABLE asset_model
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    model_name VARCHAR(100) NOT NULL
);

ALTER TABLE asset_model
    ADD CONSTRAINT uc_model_name UNIQUE (model_name);

CREATE TABLE supplier_account
(
    id             INT AUTO_INCREMENT PRIMARY KEY,
    supplier_id    INT,
    account_number VARCHAR(50) NOT NULL,
    FOREIGN KEY (supplier_id) REFERENCES supplier (id)
);

CREATE TABLE asset_status
(
    id          INT AUTO_INCREMENT,
    name        VARCHAR(50) NOT NULL UNIQUE,
    description TEXT,
    CONSTRAINT PRIMARY KEY (id)
);

INSERT INTO asset_status (name, description)
VALUES ('Disponible', 'El activo está disponible para su uso.'),
       ('En Mantenimiento', 'El activo está en mantenimiento.'),
       ('Prestado', 'El activo ha sido prestado a alguien.'),
       ('Fuera de Servicio', 'El activo ya no está operativo o en uso.');

CREATE TABLE asset
(
    id                 INT AUTO_INCREMENT PRIMARY KEY,
    purchase_date      DATE,
    value              DECIMAL(10, 2),
    responsible_id     INT,
    supplier_id        INT,
    subcategory_id     INT,
    brand_id           INT,
    status_id          INT,
    is_deleted         BOOLEAN DEFAULT FALSE,
    asset_series       VARCHAR(50),
    plate_number       VARCHAR(50),
    asset_model_id     INT,
    currency_id        INT,
    location_number_id INT,
    FOREIGN KEY (responsible_id) REFERENCES user (id),
    FOREIGN KEY (supplier_id) REFERENCES supplier (id),
    FOREIGN KEY (subcategory_id) REFERENCES asset_subcategory (id),
    FOREIGN KEY (brand_id) REFERENCES brand (id),
    FOREIGN KEY (status_id) REFERENCES asset_status (id),
    FOREIGN KEY (currency_id) REFERENCES currency (id),
    FOREIGN KEY (asset_model_id) REFERENCES asset_model (id),
    FOREIGN KEY (location_number_id) REFERENCES location_number (id)
);

ALTER TABLE asset
    ADD UNIQUE (plate_number);

ALTER

CREATE TABLE invoices
(
    id                       INT AUTO_INCREMENT PRIMARY KEY,
    asset_id                 INT,
    image_path               VARCHAR(50),
    purchase_date            DATE,
    warranty_expiration_date DATE,
    FOREIGN KEY (asset_id) REFERENCES asset (id)
);

CREATE TABLE category_type
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(50)  NOT NULL UNIQUE,
    description VARCHAR(100) NOT NULL UNIQUE
);

INSERT INTO category_type (name, description)
VALUES ('Limpieza', 'Productos para limpieza'),
       ('Oficina', 'Productos de oficina');

CREATE TABLE unit_of_measurement
(
    id           INT AUTO_INCREMENT PRIMARY KEY,
    name         VARCHAR(50) NOT NULL UNIQUE,
    abbreviation VARCHAR(10)
);

INSERT INTO unit_of_measurement (name, abbreviation)
VALUES ('Paquete', 'PQT'),
       ('Galón', 'GLN'),
       ('Caja', 'CJ'),
       ('Unidad', 'UND');

CREATE TABLE product_category
(
    id                  INT AUTO_INCREMENT PRIMARY KEY,
    name                VARCHAR(100),
    cantidad_minima     INT NOT NULL,
    category_type       INT,
    unit_of_measurement INT,
    FOREIGN KEY (category_type) REFERENCES category_type (id),
    FOREIGN KEY (unit_of_measurement) REFERENCES unit_of_measurement (id)
);

CREATE TABLE product_status
(
    id          INT AUTO_INCREMENT,
    name        VARCHAR(50) NOT NULL UNIQUE,
    description TEXT,
    CONSTRAINT PRIMARY KEY (id)
);

INSERT INTO product_status (name, description)
VALUES ('Disponible', 'El producto está disponible para solicitar.'),
       ('Pendiente', 'El producto está pendiente de ser entregado o procesado.'),
       ('Prestado', 'El producto ha sido entregado en préstamo.');

CREATE TABLE product
(
    id            INT AUTO_INCREMENT PRIMARY KEY,
    purchase_date DATE,
    expiry_date   DATE,
    category_id   INT,
    status        INT,
    CONSTRAINT FOREIGN KEY (status) REFERENCES product_status (id),
    CONSTRAINT FOREIGN KEY (category_id) REFERENCES product_category (id)
);

CREATE TABLE product_request
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    request_id INT,
    product_id INT,
    status_id  INT,
    reason     TEXT,
    UNIQUE KEY (request_id, product_id),
    FOREIGN KEY (request_id) REFERENCES request (id),
    FOREIGN KEY (product_id) REFERENCES product (id),
    FOREIGN KEY (status_id) REFERENCES resource_request_status (id)
);

CREATE TABLE asset_request
(
    id              INT AUTO_INCREMENT PRIMARY KEY,
    request_id      INT,
    asset_id        INT,
    status_id       INT,
    reason          TEXT NULL,
    expiration_date DATE,
    UNIQUE KEY (request_id, asset_id),
    FOREIGN KEY (request_id) REFERENCES request (id),
    FOREIGN KEY (asset_id) REFERENCES asset (id),
    FOREIGN KEY (status_id) REFERENCES resource_request_status (id)
);

CREATE TABLE transaction_type
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    type_name   VARCHAR(50) NOT NULL UNIQUE,
    description TEXT
);

INSERT INTO transaction_type (type_name, description)
VALUES ('Crear', 'Representa la creación de un nuevo registro o entidad.'),
       ('Leer', 'Representa la recuperación de información o datos.'),
       ('Actualizar', 'Representa la actualización o modificación de un registro existente.'),
       ('Eliminar', 'Representa la eliminación de un registro o entidad.');

CREATE TABLE asset_movements
(
    id             INT AUTO_INCREMENT PRIMARY KEY,
    asset_id       INT,
    transaction_id INT,
    date           DATE,
    FOREIGN KEY (asset_id) REFERENCES asset (id),
    FOREIGN KEY (transaction_id) REFERENCES transaction_type (id)
);

CREATE TABLE product_movements
(
    id             INT AUTO_INCREMENT PRIMARY KEY,
    product_id     INT,
    transaction_id INT,
    date           DATE,
    FOREIGN KEY (product_id) REFERENCES product (id),
    FOREIGN KEY (transaction_id) REFERENCES transaction_type (id)
);

CREATE TABLE space_movements
(
    id                INT AUTO_INCREMENT PRIMARY KEY,
    reserved_space_id INT,
    transaction_id    INT,
    date              DATE,
    FOREIGN KEY (reserved_space_id) REFERENCES space_reservation (id),
    FOREIGN KEY (transaction_id) REFERENCES transaction_type (id)
);






