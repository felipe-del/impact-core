CREATE DATABASE `impact_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
use impact_db;

-- USER ROLE --

CREATE TABLE user_role (
   id          INT AUTO_INCREMENT PRIMARY KEY,
   name 	   VARCHAR(50) NOT NULL UNIQUE,
   description TEXT
);

INSERT INTO user_role (name, description) VALUES 
('Administrator', 'Has the ability to receive more reports and access user management.'),
('Manager', 'Cannot receive more reports and has no access to user management.'),
('Teacher', 'Standard user with no special privileges.');

select * from user_role;

-- USER STATE --

CREATE TABLE user_state (
   id          INT AUTO_INCREMENT PRIMARY KEY,
   name        VARCHAR(50) NOT NULL UNIQUE,
   description TEXT
);

INSERT INTO user_state (name, description) VALUES 
('active', 'User is active and can log in'),
('inactive', 'User cannot access the system'),
('suspended', 'User is temporarily suspended');

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
('Pending', 'Request is pending review.'),
('Approved', 'Request has been approved.'),
('Rejected', 'Request has been rejected.'),
('Completed', 'The request has been completed and all associated tasks are finished.'),
('Cancelled', 'The request has been cancelled and will not be processed.');

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
    id       INT AUTO_INCREMENT PRIMARY KEY,
    name     VARCHAR(100) NOT NULL,
    phone    VARCHAR(100),
    email    VARCHAR(100),
    address  TEXT
);

-- CATEGORY -- 

CREATE TABLE asset_category (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    description VARCHAR(100) NOT NULL UNIQUE
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
('Available', 'The space is available for use.'),
('Occupied', 'The space is currently occupied.'),
('Under Maintenance', 'The space is under maintenance and not available for use.'),
('Out of Service', 'The space is no longer available or operational.');

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
    product_type         VARCHAR(50) NOT NULL UNIQUE,
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
    CONSTRAINT PRIMARY KEY (id),
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
('Available', 'The product is available for request.'),
('Pending', 'The product is pending to be delivered or processed.'),
('Loaned ', 'The product has been given in loan.');

-- RESOURCE REQUEST STATUS -- 

CREATE TABLE resource_request_status (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(50) NOT NULL UNIQUE,
    description TEXT
);

INSERT INTO resource_request_status (name, description) VALUES 
('Pending', 'The product is pending to be delivered or processed.'),
('Issued', 'The product has been issued or lent out.'),
('Returned', 'The product has been returned.'),
('Cancelled', 'The request for the product has been cancelled.'),
('Available', 'The product is available for request.');

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
('Available', 'The asset is available for use.'),
('In Maintenance', 'The asset is undergoing maintenance.'),
('Loaned', 'The asset has been loaned out to someone.'),
('Out of Service', 'The asset is no longer operational or in use.');


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
('Create', 'Represents the creation of a new record or entity.'),
('Read', 'Represents the retrieval of information or data.'),
('Update', 'Represents the update or modification of an existing record.'),
('Delete', 'Represents the deletion of a record or entity.');

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
