<!DOCTYPE html>
<html>
<head>
        <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
        
    <meta charset="utf-8"/>
    <title>MeteøChat</title>
    <link rel="stylesheet" type="text/css" href="http://fonts.googleapis.com/css?family=Ubuntu:regular&subset=Latin,Cyrillic">
    <style type='text/css'>
        body {
            margin: 0px;
            font-family: Ubuntu, Helvetica, Arial, sans-serif;
            background-image: url('img/fondo.jpg');
        }

        form input {
            width: 200px;
        }

        #barra {
            width: 100%;
            padding-top: 2em;
            padding-bottom: 2em;
            background-image: -webkit-gradient(linear, center top, center bottom, from(rgba(0,0,0,1)), to(rgba(255,255,255,0)));
            opacity: 0.75;
        }

        #logo {
            display: inline;
        }

        #contenido {
            color: #44cc55;
        }

        #separador {
            margin-top: 20px;
            margin-bottom: 20px;
            border-bottom: 1px solid white;
            width: 50%;
        }
    </style>
</head>
<body>
    <center>
        <div id='barra'>
            <img src='img/logo.png' id='logo'/>
        </div>
        <br>
        <form id="change_password_form" method="post" action="/meteochat/Controller">
            <input type="submit" value="Cerrar sesión" name="logout_action" style="background-color: #cc3333; color: white; border-radius: 5px; margin-bottom: 20px;"/>
        </form>
        <center>
            <h2 style="color: white">Bienvenido ${sessionScope.username}!</h2>
            <div id="contenido">
                <form id="change_password_form" method="post" action="/meteochat/Controller">
                    <label>Antigua contraseña</label>
                    <br><br>
                    <input type="password" name="old_password" value="Contraseña" onclick="this.value=''"/>
                    <br/><br/><br/>
                    <label>Nueva contraseña</label>
                    <br><br>
                    <input type="password" name="password1" value="Contraseña"  onclick="this.value=''"/>
                    <br/><br/>
                    <input type="password" name="password2" value="Contraseña"  onclick="this.value=''"/>
                    <br/><br/>
                    <input type="submit" value="Change password" name="change_password_action"/>
                    <p style="color: #dda500"> ${sessionScope.mensaje} </p>
                </form>
                <div id="separador"></div>
                <form id="delete_account_form" method="post" action="/meteochat/Controller">
                    <label>Contraseña</label>
                    <br><br>
                    <input type="password" name="password" value="Contraseña" onclick="this.value=''"/>
                    <br><br>
                    <input type="submit" value="Delete Account" name="delete_account_action" style="background-color: #cc3333; color: white; border-radius: 5px; margin-bottom: 20px;">
                </form>
            </div>
        </center>
    </center>
</body>
</html>
