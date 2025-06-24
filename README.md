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

- Acceso a Internet (para consumir la API).
- Un servidor PHP (como Hostinger) con base de datos MySQL.
- Tener cargado `api.php` y la tabla `noticias` en el servidor.
- Android 8.0 (Oreo) o superior  
- Android Studio (versión compatible con Kotlin)  
- Configuraciones dentro del archivo `build.gradle:app` (especificaciones aún en proceso)

---

## 🔧 Instalación y Configuración

### 1. Clonar el repositorio

```bash
git clone https://github.com/OskrrGn/ProyectoFinalMovil.git
cd newsapp
```

## 2. Configurar la base de datos

1. Accede a **phpMyAdmin** en tu hosting (por ejemplo, Hostinger).
2. Crea una nueva base de datos si no tienes una ya creada.
3. Importa el archivo `noticias.sql` que se encuentra en este repositorio.

Esto creará la tabla `noticias` con la siguiente estructura:

```sql
CREATE TABLE noticias (
  id int(11) NOT NULL,
  titulo varchar(255) NOT NULL,
  contenido text NOT NULL,
  fecha date NOT NULL
);
```

> ⚠️ **Nota:** Asegúrate de establecer el motor como `InnoDB` y la codificación como `utf8mb4`.

---

## 3. Subir la API al servidor

1. Abre el archivo `API.php`.
2. Edita las siguientes líneas con tus credenciales reales de Hostinger:

```php
$servername = "localhost";         // En Hostinger casi siempre es localhost para la base de datos
$username = "u781829000_caballero"; // Tu usuario MySQL, el que mencionas
$password = "20200Mw1";             // Tu contraseña MySQL
$dbname = "u781829000_database";   // El nombre de tu base de datos MySQL
```

3. Sube este archivo al servidor en tu hosting a la carpeta `public_html`
4. Verifica que esté accesible, por ejemplo:  
   `https://tudominio.com/api.php`

---

## 4. Configurar la URL de la API en la app

1. Abre el archivo `RetrofitClient.kt`.
2. Localiza la configuración de Retrofit y reemplaza la línea:

```kotlin
.baseUrl("https://tudominio.com/")
```

> ✅ Asegúrate de que:
> - La URL termina en `/`.
> - `api.php` es accesible públicamente.

---

## 5. Ejecutar la app en Android Studio

1. Abre el proyecto en **Android Studio**.
2. Conecta un dispositivo Android físico **(API 26 o superior)** o usa un emulador.
3. Haz clic en **Run ▶️** para compilar y ejecutar la aplicación.

## 🧩 Funcionalidades Principales

- Subida de noticias (sin restricciones temáticas)
- Edición y eliminación de noticias propias
- Sincronización de noticias entre dispositivos
- Filtro y búsqueda de noticias
- Preferencias de usuario (por ejemplo, cambio de tema de color)

---

## 🗄️ Gestión de Datos

- La base de datos se gestiona desde **phpMyAdmin**, alojada en **Hostinger** (es el backend de la aplicacion)
- Las operaciones CRUD (crear, leer, actualizar, eliminar) se manejan a través de peticiones HTTP a una **API en PHP**
- Los datos se transmiten en formato **JSON** entre el frontend (app móvil) y el backend (servidor)

---

## 🧪 Pruebas Realizadas

- Subida exitosa de noticias desde distintos dispositivos
- Sincronización en tiempo real entre usuarios
- Edición y eliminación de entradas
- Validaciones previas a la eliminación de una noticia
- Scroll continuo para visualizar noticias pasadas
- Cambio de preferencias visuales (colores)

---

## 🛠️ Mantenimiento y Actualizaciones

Este proyecto no contempla mantenimiento continuo, ya que fue desarrollado con fines escolares.  
Sin embargo, el código queda disponible como referencia para estudiantes o desarrolladores que deseen explorar o reutilizar partes del sistema.

---

## 📬 Contacto

Para dudas o soporte sobre este proyecto:

- **Oscar Martínez Lucero** – omartinezlucero26@gmail.com  
- **Yael Caballero Bustos** – yaelbustos14@gmail.com
