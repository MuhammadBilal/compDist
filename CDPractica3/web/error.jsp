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

        #contenido p {
            color: #cc5555
        }

    </style>
</head>
<body>
    <center>
        <div id='barra'>
            <img src='img/logo.png' id='logo'/>
        </div>
        <br>
        <div id="contenido">
            <center><p> ${sessionScope.error} </p></center>
        </div>
    </center>
</body>
</html>
