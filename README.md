# ProyectoFinalMovil
Repositorio del proyecto final de Programacion Movil 6NM60
# 📱 NewsApp

**NewsApp** es una aplicación móvil desarrollada como proyecto escolar, cuyo propósito principal es permitir a los usuarios **crear, editar, eliminar y visualizar noticias** de cualquier tema de interés. Una de sus características distintivas es la **sincronización de datos entre dispositivos**, permitiendo que todos los usuarios puedan ver y compartir las mismas noticias en tiempo real.

---

## 🧠 Propósito del Proyecto

Brindar una plataforma colaborativa donde cualquier usuario pueda publicar noticias de manera libre, fomentando el intercambio de información desde distintos puntos de vista. Ideal para contextos educativos o de comunidades.

---

## ⚙️ Tecnologías Utilizadas

- **Lenguaje principal:** Kotlin  
- **IDE:** Android Studio  
- **Backend y Base de Datos:**  
  - API en PHP conectada a base de datos MySQL mediante **phpMyAdmin**  
  - Servidor hospedado en **Hostinger**  
  - Uso de pruebas de Script **JSON**   
- **Sincronización:**  
  - API alojada en Hostinger que permite enviar y recibir datos desde distintos dispositivos

---

## 📲 Requisitos del Sistema

- **Sistema operativo:** Android 8.0 (Oreo) o superior  
- **IDE para desarrollo:** Android Studio (versión compatible con Kotlin)  
- **Dependencias:** Configuraciones dentro del archivo `build.gradle:app` (especificaciones aún en proceso)

---

## 🔧 Instalación y Configuración

⚠️ *En desarrollo.*  
Se está definiendo un proceso estándar de instalación. La idea es realizar pruebas para establecer pasos como:

1. Clonar el repositorio.
2. Abrir el proyecto en Android Studio.
3. Conectar el entorno con el servidor remoto (API y base de datos).
4. Ejecutar la app en un emulador o dispositivo real.

---

## 🧩 Funcionalidades Principales

- Subida de noticias (sin restricciones temáticas)
- Edición y eliminación de noticias propias
- Sincronización de noticias entre dispositivos
- Filtro y búsqueda de noticias
- Preferencias de usuario (por ejemplo, cambio de tema de color)

---

## 🗄️ Gestión de Datos

- La base de datos se gestiona desde **phpMyAdmin**, alojada en **Hostinger**
- Las operaciones CRUD (crear, leer, actualizar, eliminar) se manejan a través de peticiones HTTP a una **API en PHP**
- Los datos se transmiten en formato **JSON** entre el frontend (app móvil) y el backend (servidor)

---

## 🔐 Seguridad

🚧 *Aún en proceso de implementación.*  
Se planea añadir medidas básicas de autenticación y validación en futuras versiones si el proyecto lo requiere.

---

## 🧪 Pruebas Realizadas

- Subida exitosa de noticias desde distintos dispositivos
- Sincronización en tiempo real entre usuarios
- Edición y eliminación de entradas
- Validaciones previas a la eliminación de una noticia
- Scroll continuo para visualizar noticias pasadas
- Cambio de preferencias visuales (modo oscuro/claro, colores)

---

## 🛠️ Mantenimiento y Actualizaciones

Este proyecto no contempla mantenimiento continuo, ya que fue desarrollado con fines escolares.  
Sin embargo, el código queda disponible como referencia para estudiantes o desarrolladores que deseen explorar o reutilizar partes del sistema.

---

## 📬 Contacto

Para dudas o soporte sobre este proyecto:

- **Oscar Martínez Lucero** – omartinezlucero26@gmail.com  
- **Yael Caballero Bustos** – yaelbustos14@gmail.com
