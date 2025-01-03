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

-- NOT NECESSARY
-- CREATE TABLE request_status

CREATE TABLE resource_request_status
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(50) NOT NULL UNIQUE,
    description TEXT
);

INSERT INTO resource_request_status (name, description)
VALUES ('RESOURCE_REQUEST_STATUS_EARRING', 'Está pendiente de ser aprobado.'),
       ('RESOURCE_REQUEST_STATUS_ACCEPTED', 'Ha sido aceptado.'),
       ('RESOURCE_REQUEST_STATUS_RETURNED', 'Ha sido devuelto.'),
       ('RESOURCE_REQUEST_STATUS_CANCELED', 'Ha sido cancelada.');

-- NOT NECESSARY
-- CREATE TABLE request

CREATE TABLE entity_type
(
    id        INT AUTO_INCREMENT PRIMARY KEY,
    type_name VARCHAR(50) NOT NULL
);

INSERT INTO entity_type (type_name)
VALUES ('TYPE_PHYSICAL'),
       ('TYPE_LEGAL');

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
VALUES ('SPACE_STATUS_AVAILABLE', 'El espacio está disponible para su uso.'),
       ('SPACE_STATUS_LOANED', 'El espacio está actualmente ocupado.'),
       ('SPACE_STATUS_IN_MAINTENANCE', 'El espacio está en mantenimiento y no está disponible para su uso.'),
       ('SPACE_STATUS_OUT_OF_SERVICE', 'El espacio ya no está disponible o no está operativo.'),
       ('SPACE_STATUS_OUT_OF_EARRING', 'El espacio está pendiente de ser entregado o procesado.');

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
    space_id      INT,
    num_people    INT,
    event_desc    VARCHAR(255),
    event_obs     VARCHAR(255),
    status_id     INT,
    use_equipment TINYINT(1) DEFAULT 0,
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
VALUES ('ASSET_STATUS_AVAILABLE', 'El activo está disponible para su uso.'),
       ('ASSET_STATUS_IN_MAINTENANCE', 'El activo está en mantenimiento.'),
       ('ASSET_STATUS_LOANED', 'El activo ha sido prestado a alguien.'),
       ('ASSET_STATUS_OUT_OF_SERVICE', 'El activo ya no está operativo o en uso.'),
       ('ASSET_STATUS_EARRING', 'El activo está pendiente de ser entregado o procesado.');

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
    name                VARCHAR(100) NOT NULL UNIQUE,
    minimum_quantity     INT NOT NULL,
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
VALUES ('PRODUCT_STATUS_AVAILABLE', 'El producto está disponible para solicitar.'),
       ('PRODUCT_STATUS_EARRING', 'El producto está pendiente de ser entregado o procesado.'),
       ('PRODUCT_STATUS_LOANED', 'El producto ha sido entregado en préstamo.');

CREATE TABLE product
(
    id            INT AUTO_INCREMENT PRIMARY KEY,
    name          VARCHAR(100) NOT NULL UNIQUE,
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
    product_id INT,
    status_id  INT,
    reason     TEXT,
    user_id    int  null,
    created_at   DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES product (id),
    FOREIGN KEY (status_id) REFERENCES resource_request_status (id),
    FOREIGN KEY (user_id) REFERENCES user (id),
    CONSTRAINT unique_product_user UNIQUE (product_id, user_id)
);

CREATE TABLE asset_request
(
    id              INT AUTO_INCREMENT PRIMARY KEY,
    asset_id        INT,
    status_id       INT,
    reason          TEXT NULL,
    expiration_date DATE,
    FOREIGN KEY (asset_id) REFERENCES asset (id),
    FOREIGN KEY (status_id) REFERENCES resource_request_status (id)
);


-- IMPORTANT NOTE: WE DECIDED TO CHANGE THE WAY WE KEEP TRACK OF THE MOVEMENTS IN THE DATABASE
-- WE IMPLEMENTED THE AUDIT LOG TABLE TO KEEP TRACK OF ALL THE CHANGES MADE IN THE DATABASE
-- so we don't need the following tables asset_movements, product_movements, space_movements




