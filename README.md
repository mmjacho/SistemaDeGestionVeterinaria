# 🐾 Sistema de Gestión Veterinaria - Grupo 2

Este es un sistema informático desarrollado en Java (JDK 20) bajo la arquitectura **MVC (Modelo-Vista-Controlador)** para la gestión eficiente de una clínica veterinaria. El proyecto forma parte de la asignatura de *Verificación y Validación de Software*.

## 👥 Integrantes y Módulos Asignados
* **Galo Izquierdo:** Módulo 1 - Mascota / Cliente
* **Alonso Serrano:** Módulo 2 - Médico
* **Rubén Quiroga:** Módulo 3 - Cita Médica
* **Mario Jacho:** Módulo 4 - Atención Veterinaria
* **Steven Armijos:** Módulo 5 - Facturación

## 🛠️ Tecnologías Utilizadas
* **Lenguaje:** Java (JDK 20)
* **Interfaz Gráfica:** Java Swing
* **IDE:** Apache NetBeans
* **Gestor de Dependencias:** Maven
* **Base de Datos:** MySQL / MariaDB (Driver `mysql-connector-j`)

## ⚙️ Arquitectura
El proyecto está estructurado estrictamente en capas:
1. `modelo`: Clases POJO y lógica de negocio.
2. `vista`: Interfaces gráficas desarrolladas con Java Swing.
3. `controlador`: Intermediarios que capturan eventos de la vista y se comunican con el modelo (y base de datos).
4. `conexion`: Manejo del patrón Singleton para la conexión a la base de datos `ugproy1`.

## 🗄️ Base de Datos
Las tablas de este proyecto utilizan el prefijo `g2_vet_` para evitar conflictos en el servidor compartido. 
*(Nota para el equipo: Antes de ejecutar el proyecto, asegúrese de configurar correctamente los parámetros del servidor en la clase `Conexion.java`).*
