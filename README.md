# 📦 IMPACT - Sistema de Gestión de Recursos para CIMPA - Backend

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.3-green)
![MySQL](https://img.shields.io/badge/MySQL-8.0-orange)
![License](https://img.shields.io/badge/license-MIT-blue)

## 🌟 Descripción General

IMPACT (Integrated Management Platform and Control Tracker) es un sistema desarrollado para la Universidad de Costa Rica, específicamente para el CIMPA (Centro de Investigación de Matemática Pura y Aplicada), que optimiza la administración y control de recursos materiales mediante una solución tecnológica integral.

## 🎯 Objetivos del Proyecto

1. **Gestión Integral de Recursos**:
    - Administración centralizada de activos, productos y espacios
    - Control de inventario con seguimiento en tiempo real
    - Sistema de préstamos y devoluciones

2. **Sistema de Solicitudes**:
    - Flujo completo de solicitudes (creación, aprobación, seguimiento)
    - Notificaciones automáticas de estado
    - Gestión de vencimientos y renovaciones

3. **Seguridad y Control de Acceso**:
    - Autenticación robusta con JWT
    - Autorización basada en roles (Administrador, Gestor, Docente)
    - Bitácora completa de transacciones

4. **Reportes y Auditoría**:
    - Generación de reportes de movimientos
    - Trazabilidad completa de operaciones
    - Logs de sistema para diagnóstico

## 🏗️ Arquitectura del Sistema

### Estructura Cliente-Servidor (Three-Tier)
- **Frontend**: React + Vite (Interfaz de usuario dinámica)
- **Backend**: Spring Boot (Lógica de negocio y API RESTful)
- **Base de Datos**: MySQL (Almacenamiento persistente)

### Patrones Implementados
- MVC (Modelo-Vista-Controlador)
- Repository Pattern
- DTO (Data Transfer Objects)

## 🛠️ Tecnologías Clave

- **Backend**:
    - Spring Boot 3
    - Spring Security
    - Spring Data JPA
    - Hibernate
    - JavaDoc
    - Swagger

- **Base de Datos**:
    - MySQL 8.0

- **Otros**:
    - JWT (Autenticación)
    - JavaMail (Notificaciones)
    - Spring Scheduler (Tareas programadas)

## 📌 Características Principales

### Módulo de Gestión de Recursos
- CRUD completo para activos, productos y espacios
- Búsqueda avanzada con filtros
- Asignación de recursos a usuarios

### Sistema de Solicitudes
- Flujo completo de aprobación
- Historial de solicitudes por usuario
- Renovación de préstamos

### Seguridad Avanzada
- Registro con aprobación administrativa
- Políticas de contraseñas robustas
- Recuperación segura de credenciales

### Notificaciones Automáticas
- Recordatorios de vencimientos
- Confirmación de operaciones
- Alertas del sistema

## 📄 Documentación Completa

La documentación técnica detallada se encuentra disponible en:
- [Documento de Arquitectura](#) (Ver sección completa en el repositorio)
- [Swagger API](http://localhost:8080/swagger-ui.html) (Acceso local)
- [JavaDoc API](#)
- [Manual de Usuario](#)

## 👥 Equipo de Desarrollo

| Nombre | Rol | Contacto                                                             |
|--------|-----|----------------------------------------------------------------------|
| Isaac Brenes | Full Stack Dev | [isaacfelibrenes1904@gmail.com](mailto:isaacfelibrenes1904@gmail.com) |
| Raquel Alfaro | Full Stack Dev | [raquealfaba@gmail.com](mailto:raquealfaba@gmail.com)                |
| Maria González | Full Stack Dev | [nazmgb@gmail.com](mailto:maria.gonzalez.benavides@est.una.ac.cr)              |
| Dilan Hernández | Full Stack Dev | [dilanhernandez.dhu@gmail.com](mailto:dilanhernandez.dhu@gmail.com)  |
| Marco Leandro | Full Stack Dev | [marcoleandro2808@gmail.com](mailto:marcoleandro2808@gmail.com)      |
| Joel Ramírez | Full Stack Dev | [joelramva07@gmail.com](mailto:joelramva07@gmail.com)                |

## 📜 Licencia

Este proyecto no cuenta con una licencia profesional específica. El uso y distribución del código están sujetos a los términos establecidos por el autor. Se recomienda revisar el código y contactar con el propietario del proyecto para obtener detalles adicionales sobre el uso permitido.

---

> **Nota**: Este sistema fue desarrollado como parte del curso de Ingenieria en Sistemas I, II y II de la Escuela de Informática de la Universidad Nacional de Costa Rica.
