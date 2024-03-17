<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Registro</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
        }
        .register-container {
            width: 300px;
            margin: 0 auto;
            padding: 20px;
            background-color: #fff;
            border-radius: 5px;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
            margin-top: 100px;
        }
        input[type="text"],
        input[type="password"] {
            width: 100%;
            padding: 10px;
            margin: 8px 0;
            box-sizing: border-box;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        input[type="submit"] {
            background-color: #4CAF50;
            color: white;
            padding: 14px 20px;
            margin: 8px 0;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            width: 100%;
        }
        input[type="submit"]:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
    <div class="register-container">
        <h2>Registro</h2>
        <form action="registro" method="post">
            <label for="nombre">Nombre:</label>
            <input type="text" id="nombre" name="nombre">
            <label for="contraseña">Contraseña:</label>
            <input type="password" id="contraseña" name="contraseña">
            <input type="submit" value="Registrar">
        </form>
        <p>¿Ya tienes una cuenta? <a href="../hipotecas">Inicia sesión aquí</a>.</p>
    </div>
    
</body>
</html>
