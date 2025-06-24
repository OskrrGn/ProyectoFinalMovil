<?php
header("Content-Type: application/json");
header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Methods: GET, POST, PUT, DELETE, OPTIONS");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

// Conexión a la base de datos
$servername = "localhost";         // En Hostinger casi siempre es localhost para la base de datos
$username = "u781829000_caballero"; // Tu usuario MySQL, el que mencionas
$password = "20200Mw1";             // Tu contraseña MySQL
$dbname = "u781829000_database";   // El nombre de tu base de datos MySQL


$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
    http_response_code(500);
    echo json_encode(["error" => "Error de conexión a la base de datos"]);
    exit();
}

// Obtener método HTTP
$method = $_SERVER['REQUEST_METHOD'];

// Leer el input JSON para POST, PUT, DELETE
$input = json_decode(file_get_contents('php://input'), true);

// Función para obtener noticias
function getNews($conn) {
    $sql = "SELECT * FROM noticias ORDER BY fecha DESC";
    $result = $conn->query($sql);

    $news = [];
    if ($result->num_rows > 0) {
        while ($row = $result->fetch_assoc()) {
            $news[] = $row;
        }
    }
    echo json_encode($news);
}

// Función para agregar noticia
function addNews($conn, $data) {
    if (!isset($data['titulo'], $data['contenido'], $data['fecha'])) {
        http_response_code(400);
        echo json_encode(["error" => "Faltan datos requeridos"]);
        return;
    }
    $titulo = $conn->real_escape_string($data['titulo']);
    $contenido = $conn->real_escape_string($data['contenido']);
    $fecha = $conn->real_escape_string($data['fecha']);

    $sql = "INSERT INTO noticias (titulo, contenido, fecha) VALUES ('$titulo', '$contenido', '$fecha')";

    if ($conn->query($sql) === TRUE) {
        http_response_code(201);
        echo json_encode(["message" => "Noticia creada", "id" => $conn->insert_id]);
    } else {
        http_response_code(500);
        echo json_encode(["error" => "Error al crear la noticia"]);
    }
}

// Función para actualizar noticia
function updateNews($conn, $id, $data) {
    if (!isset($data['titulo'], $data['contenido'], $data['fecha'])) {
        http_response_code(400);
        echo json_encode(["error" => "Faltan datos requeridos"]);
        return;
    }
    $titulo = $conn->real_escape_string($data['titulo']);
    $contenido = $conn->real_escape_string($data['contenido']);
    $fecha = $conn->real_escape_string($data['fecha']);
    $id = intval($id);

    $sql = "UPDATE noticias SET titulo='$titulo', contenido='$contenido', fecha='$fecha' WHERE id=$id";

    if ($conn->query($sql) === TRUE) {
        echo json_encode(["message" => "Noticia actualizada"]);
    } else {
        http_response_code(500);
        echo json_encode(["error" => "Error al actualizar la noticia"]);
    }
}

// Función para eliminar noticia
function deleteNews($conn, $id) {
    $id = intval($id);
    $sql = "DELETE FROM noticias WHERE id=$id";

    if ($conn->query($sql) === TRUE) {
        echo json_encode(["message" => "Noticia eliminada"]);
    } else {
        http_response_code(500);
        echo json_encode(["error" => "Error al eliminar la noticia"]);
    }
}

// Manejo de rutas
switch ($method) {
    case 'GET':
        getNews($conn);
        break;
    case 'POST':
        addNews($conn, $input);
        break;
    case 'PUT':
        if (!isset($_GET['id'])) {
            http_response_code(400);
            echo json_encode(["error" => "Falta id en la URL"]);
            break;
        }
        updateNews($conn, $_GET['id'], $input);
        break;
    case 'DELETE':
        if (!isset($_GET['id'])) {
            http_response_code(400);
            echo json_encode(["error" => "Falta id en la URL"]);
            break;
        }
        deleteNews($conn, $_GET['id']);
        break;
    case 'OPTIONS':
        http_response_code(200);
        break;
    default:
        http_response_code(405);
        echo json_encode(["error" => "Método no permitido"]);
        break;
}

$conn->close();
?>
