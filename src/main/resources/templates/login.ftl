<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Iniciar Sesion</title>

    <!-- Bootstrap -->
    <link href="/css/bootstrap.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<div class="col-md-4 col-md-offset-4">
    <h2>Iniciar Sesion</h2>
    <form method='post' action='/loginForm' style="padding-bottom:20px">
        <div class="form-group">
            <label>Usuario</label>
            <input type="text" name="Usuario" class="form-control">
        </div>
        <div class="form-group">
            <label>Password</label>
            <input type="password"  name="password" class="form-control">
        </div>
          <div class="form-group">
            <input type="submit" id="btnSubmit" class="form-control" value="Iniciar Sesion">
        </div>
        <div class="form-group">
            <a href="#"><input type="Button" class="form-control" value="Registrarse"  ></a>
        </div>
    </form>
</div
</body>
</html>