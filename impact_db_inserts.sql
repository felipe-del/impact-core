INSERT INTO user_role (name, description)
VALUES ('ROLE_ADMINISTRATOR', 'Acceso total al sistema, gestión de usuarios, configuraciones y seguridad.'),
       ('ROLE_MANAGER', 'Supervisa procesos y aprueba/rechaza solicitudes.'),
       ('ROLE_TEACHER', 'Solicita Espacios, Activos o Productos.');

INSERT INTO user_state (name, description)
VALUES ('STATE_ACTIVE', 'El usuario está activo y puede iniciar sesión'),
       ('STATE_INACTIVE', 'El usuario no puede acceder al sistema'),
       ('STATE_SUSPENDED', 'El usuario está temporalmente suspendido');

INSERT INTO resource_request_status (name, description)
VALUES ('RESOURCE_REQUEST_STATUS_EARRING', 'Está pendiente de ser aprobado.'),
       ('RESOURCE_REQUEST_STATUS_ACCEPTED', 'Ha sido aceptado.'),
       ('RESOURCE_REQUEST_STATUS_RETURNED', 'Ha sido devuelto.'),
       ('RESOURCE_REQUEST_STATUS_CANCELED', 'Ha sido cancelada.'),
       ('RESOURCE_REQUEST_STATUS_RENEWAL', 'Pendiente de renovacion.'),
       ('RESOURCE_REQUEST_STATUS_WAITING_ON_RENEWAL', 'En espera de la renovación de otra solicitud.');

INSERT INTO entity_type (type_name)
VALUES ('TYPE_PHYSICAL'),
       ('TYPE_LEGAL');

INSERT INTO space_status (name, description)
VALUES ('SPACE_STATUS_AVAILABLE', 'El espacio está disponible para su uso.'),
       ('SPACE_STATUS_LOANED', 'El espacio está actualmente ocupado.'),
       ('SPACE_STATUS_IN_MAINTENANCE', 'El espacio está en mantenimiento y no está disponible para su uso.'),
       ('SPACE_STATUS_OUT_OF_SERVICE', 'El espacio ya no está disponible o no está operativo.'),
       ('SPACE_STATUS_EARRING', 'El espacio está pendiente de ser entregado o procesado.');

INSERT INTO currency (currency_code, currency_name)
VALUES ('CRC-₡', 'CURRENCY_COLON'),
       ('USD-$', 'CURRENCY_DOLLAR');

INSERT INTO asset_status (name, description)
VALUES ('ASSET_STATUS_AVAILABLE', 'El activo está disponible para su uso.'),
       ('ASSET_STATUS_IN_MAINTENANCE', 'El activo está en mantenimiento.'),
       ('ASSET_STATUS_LOANED', 'El activo ha sido prestado a alguien.'),
       ('ASSET_STATUS_OUT_OF_SERVICE', 'El activo ya no está operativo o en uso.'),
       ('ASSET_STATUS_EARRING', 'El activo está pendiente de ser entregado o procesado.');

INSERT INTO category_type (name, description)
VALUES ('Limpieza', 'Productos para limpieza'),
       ('Oficina', 'Productos de oficina');

INSERT INTO unit_of_measurement (name, abbreviation)
VALUES ('Paquete', 'PQT'),
       ('Galón', 'GLN'),
       ('Caja', 'CJ'),
       ('Unidad', 'UND');

INSERT INTO product_status (name, description)
VALUES ('PRODUCT_STATUS_AVAILABLE', 'El producto está disponible para solicitar.'),
       ('PRODUCT_STATUS_EARRING', 'El producto está pendiente de ser entregado o procesado.'),
       ('PRODUCT_STATUS_LOANED', 'El producto ha sido entregado en préstamo.');
