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

CREATE TABLE user_state
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(50) NOT NULL UNIQUE,
    description TEXT
);

CREATE TABLE resource_request_status
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(50) NOT NULL UNIQUE,
    description TEXT
);

CREATE TABLE entity_type
(
    id        INT AUTO_INCREMENT PRIMARY KEY,
    type_name VARCHAR(50) NOT NULL
);

CREATE TABLE asset_category
(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,

    CONSTRAINT unique_asset_category_name UNIQUE (name)
);

CREATE TABLE brand
(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,

    CONSTRAINT unique_brand_name UNIQUE (name)
);

CREATE TABLE building
(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE space_status
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(50) NOT NULL UNIQUE,
    description TEXT
);

CREATE TABLE location_type
(
    id        INT AUTO_INCREMENT PRIMARY KEY,
    type_name VARCHAR(100) NOT NULL,

    CONSTRAINT unique_location_type_name UNIQUE (type_name)
);

CREATE TABLE currency
(
    id            INT AUTO_INCREMENT PRIMARY KEY,
    currency_code VARCHAR(10) NOT NULL,
    currency_name VARCHAR(50) NOT NULL
);

CREATE TABLE asset_model
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    model_name VARCHAR(100) NOT NULL,

    CONSTRAINT uc_model_name UNIQUE (model_name)
);

CREATE TABLE asset_status
(
    id          INT AUTO_INCREMENT,
    name        VARCHAR(50) NOT NULL UNIQUE,
    description TEXT,

    CONSTRAINT PRIMARY KEY (id)
);

CREATE TABLE category_type
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(50)  NOT NULL UNIQUE,
    description VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE unit_of_measurement
(
    id           INT AUTO_INCREMENT PRIMARY KEY,
    name         VARCHAR(50) NOT NULL UNIQUE,
    abbreviation VARCHAR(10)
);

CREATE TABLE product_status
(
    id          INT AUTO_INCREMENT,
    name        VARCHAR(50) NOT NULL UNIQUE,
    description TEXT,

    CONSTRAINT PRIMARY KEY (id)
);

CREATE TABLE user
(
    id       INT AUTO_INCREMENT PRIMARY KEY,
    name     VARCHAR(100) NOT NULL,
    email    VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role_id  INT,
    state_id INT,

    FOREIGN KEY (role_id)   REFERENCES user_role (id),
    FOREIGN KEY (state_id)  REFERENCES user_state (id)
);

CREATE TABLE supplier
(
    id             INT AUTO_INCREMENT PRIMARY KEY,
    name           VARCHAR(100)       NOT NULL,
    phone          VARCHAR(100),
    email          VARCHAR(100),
    address        TEXT,
    entity_type_id INT,
    client_contact VARCHAR(100),
    id_number      VARCHAR(50) UNIQUE NOT NULL,

    FOREIGN KEY (entity_type_id) REFERENCES entity_type (id)
);

CREATE TABLE asset_subcategory
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(100),
    description VARCHAR(255) NOT NULL UNIQUE,
    category_id INT,

    FOREIGN KEY (category_id) REFERENCES asset_category (id),
    CONSTRAINT uc_subcategory_name UNIQUE (name)
);

CREATE TABLE building_location
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    building_id INT         NOT NULL,
    floor       VARCHAR(50) NOT NULL,

    FOREIGN KEY (building_id) REFERENCES building (id),
    CONSTRAINT unique_building_floor UNIQUE (building_id, floor)
);

CREATE TABLE location_number
(
    id               INT AUTO_INCREMENT PRIMARY KEY,
    location_type_id INT,
    location_number  INT,

    FOREIGN KEY (location_type_id) REFERENCES location_type (id),
    CONSTRAINT unique_location_number UNIQUE (location_type_id, location_number)
);

CREATE TABLE product_category
(
    id                  INT AUTO_INCREMENT PRIMARY KEY,
    name                VARCHAR(100) NOT NULL UNIQUE,
    minimum_quantity    INT          NOT NULL,
    category_type       INT,
    unit_of_measurement INT,

    FOREIGN KEY (category_type)         REFERENCES category_type (id),
    FOREIGN KEY (unit_of_measurement)   REFERENCES unit_of_measurement (id)
);

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

    FOREIGN KEY (status_id)     REFERENCES space_status (id),
    FOREIGN KEY (location_id)   REFERENCES building_location (id)
);

CREATE TABLE space_equipment
(
    id       INT AUTO_INCREMENT PRIMARY KEY,
    name     VARCHAR(100) NOT NULL UNIQUE,
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
    user_id    INT NOT NULL,

    FOREIGN KEY (space_id) REFERENCES space (id),
    CONSTRAINT fk_space_reservation_user FOREIGN KEY (user_id) REFERENCES user (id)
);

CREATE TABLE space_request
(
    id            INT AUTO_INCREMENT PRIMARY KEY,
    space_id      INT,
    num_people    INT,
    event_desc    VARCHAR(255),
    event_obs     VARCHAR(255),
    user_id       INT NOT NULL,
    use_equipment TINYINT(1) DEFAULT 0,
    status_id     INT,
    reservation_id INT NOT NULL UNIQUE,
    created_at    TIMESTAMP  DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (user_id)           REFERENCES user (id),
    FOREIGN KEY (space_id)          REFERENCES space (id),
    FOREIGN KEY (status_id)         REFERENCES resource_request_status (id),
    FOREIGN KEY (reservation_id)    REFERENCES space_reservation (id)
);

CREATE TABLE supplier_account
(
    id             INT AUTO_INCREMENT PRIMARY KEY,
    supplier_id    INT,
    account_number VARCHAR(50) NOT NULL,

    FOREIGN KEY (supplier_id) REFERENCES supplier (id)
);

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

    FOREIGN KEY (responsible_id)        REFERENCES user (id),
    FOREIGN KEY (supplier_id)           REFERENCES supplier (id),
    FOREIGN KEY (subcategory_id)        REFERENCES asset_subcategory (id),
    FOREIGN KEY (brand_id)              REFERENCES brand (id),
    FOREIGN KEY (status_id)             REFERENCES asset_status (id),
    FOREIGN KEY (currency_id)           REFERENCES currency (id),
    FOREIGN KEY (asset_model_id)        REFERENCES asset_model (id),
    FOREIGN KEY (location_number_id)    REFERENCES location_number (id),
    UNIQUE (plate_number)
);

CREATE TABLE product
(
    id            INT AUTO_INCREMENT PRIMARY KEY,
    purchase_date DATE,
    expiry_date   DATE,
    category_id   INT,
    status        INT,

    CONSTRAINT FOREIGN KEY (status)         REFERENCES product_status (id),
    CONSTRAINT FOREIGN KEY (category_id)    REFERENCES product_category (id)
);

CREATE TABLE invoices
(
    id                       INT AUTO_INCREMENT PRIMARY KEY,
    asset_id                 INT,
    image_path               VARCHAR(50),
    purchase_date            DATE,
    warranty_expiration_date DATE,

    FOREIGN KEY (asset_id)  REFERENCES asset (id)
);

CREATE TABLE product_request
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT,
    status_id  INT,
    reason     TEXT,
    user_id    INT NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP NULL,

    FOREIGN KEY (product_id) REFERENCES product (id),
    FOREIGN KEY (status_id) REFERENCES resource_request_status (id),
    FOREIGN KEY (user_id) REFERENCES user (id),
    CONSTRAINT unique_product_user UNIQUE (product_id, user_id)
);

CREATE TABLE products_of_request
(
    id                 INT AUTO_INCREMENT PRIMARY KEY,
    product_id         INT,
    product_request_id INT,

    FOREIGN KEY (product_id)            REFERENCES product (id),
    FOREIGN KEY (product_request_id)    REFERENCES product_request (id)
);

CREATE TABLE asset_request
(
    id              INT AUTO_INCREMENT PRIMARY KEY,
    asset_id        INT,
    status_id       INT,
    reason          TEXT                               NULL,
    expiration_date DATE,
    user_id         INT                                NOT NULL,
    created_at      DATETIME DEFAULT CURRENT_TIMESTAMP NULL,

    FOREIGN KEY (asset_id)  REFERENCES asset (id),
    FOREIGN KEY (user_id)   REFERENCES user (id),
    FOREIGN KEY (status_id) REFERENCES resource_request_status (id)
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

-- CREATED VIEWS FOR ASSET STATISTICS
CREATE VIEW asset_request_statistics_by_date AS
SELECT a.status_id,
       ar.status_id        AS request_status_id,
       ar.id               AS asset_request_id,
       COUNT(ar.asset_id)  AS total_assets_requested,
       DATE(ar.created_at) AS request_date
FROM asset_request ar
         JOIN asset a ON ar.asset_id = a.id
GROUP BY ar.id, a.status_id, ar.status_id, ar.created_at;

-- CREATED VIEWS FOR PRODUCT STATISTICS
CREATE VIEW product_request_statistics_by_date AS
SELECT pc.id                 AS category_id,
       pc.category_type      AS category_type,
       pr.status_id,
       pr.id                 AS product_request_id,
       COUNT(por.product_id) AS total_products_requested,
       DATE(pr.created_at)   AS request_date
FROM product_request pr
         JOIN products_of_request por ON pr.id = por.product_request_id
         JOIN product p ON por.product_id = p.id
         JOIN product_category pc ON p.category_id = pc.id
GROUP BY pr.id, pc.id, pr.status_id, pr.created_at;

CREATE VIEW product_entries_by_date AS
SELECT p.category_id,
       COUNT(*) AS total_ingresos,
       p.purchase_date
FROM product p
WHERE p.purchase_date IS NOT NULL
GROUP BY p.purchase_date, p.category_id;