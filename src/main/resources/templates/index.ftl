<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Programación Web</title>

    <!-- Bootstrap Core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="css/blog-home.css" rel="stylesheet">

    <link href="css/Estilos.css" rel="stylesheet">


    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body>
    <!-- Navigation -->
    <nav class="navbar navbar-inverse navbar-fixed-top topbar" role="navigation">
        <div class="container">

            <!-- Collect the nav links, forms, and other content for toggling -->


            <div class="collapse navbar-collapse topbar" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav">
                    <li>
                        <a href="/Home"><span class="dorado">Inicio</span></a>
                    </li>
                    <li>
                        <a href="/NuevoPost"><span class="dorado">Nueva entrada</span></a>
                    </li>
                    <li>
                        <a href="/NuevoUsuario"><span class="dorado">Registro</span></a>
                    </li>
                    <li>
                        <a href="/cerrarSesion"><span class="dorado">Cerrar Sesion</span></a>
                    </li>
                </ul>
            </div>
            <!-- /.navbar-collapse -->
        </div>
        <!-- /.container -->
    </nav>

    <!-- Page Content -->
    <div class="container">

        <div class="row body-row">

            <!-- Blog Entries Column -->
            <div class="col-md-8">

                <h1 class="page-header">
                    Programación Web
                    <small>Práctica #3</small>
                </h1>

                <#list articulos as art>
                    <h2>
                        <a href="/Entrada/${art_index}">${art.getTitulo()}</a>
                    </h2>
                    <p>
                        Autor: <a href="">${art.getAutor().getNombre()}</a>
                    </p>
                    <p><span class="glyphicon glyphicon-time"></span>${art.getFecha()}</p>
                    <hr>

                    <#assign cuerpo=art.getCuerpo()>
                    <#if cuerpo?length &lt; 70>
                        ${cuerpo}
                    <#else>
                        ${cuerpo?substring(0,70) + "..."}
                    </#if>

                    <br><br>
                    <a class="btn btn-primary" href="/Entrada/${art_index}">Leer Más<span class="glyphicon glyphicon-chevron-right"></span></a>

                    <hr>

                </#list>
                <nav style="float:right;">

            </div>

            <!-- Blog Sidebar Widgets Column -->
            <div class="col-md-4">

                <!-- Blog Categories Well -->
                <div class="well">
                    <h4>Puede que sirva pa las etiquetas</h4>
                    <div class="row">
                        <div class="col-lg-6">
                            <ul class="list-unstyled">
                                <li><a href="#">Category Name</a>
                                </li>
                                <li><a href="#">Category Name</a>
                                </li>
                                <li><a href="#">Category Name</a>
                                </li>
                                <li><a href="#">Category Name</a>
                                </li>
                            </ul>
                        </div>
                        <!-- /.col-lg-6 -->
                        <div class="col-lg-6">
                            <ul class="list-unstyled">
                                <li><a href="#">Category Name</a>
                                </li>
                                <li><a href="#">Category Name</a>
                                </li>
                                <li><a href="#">Category Name</a>
                                </li>
                                <li><a href="#">Category Name</a>
                                </li>
                            </ul>
                        </div>
                        <!-- /.col-lg-6 -->
                    </div>
                    <!-- /.row -->
                </div>

                <!-- Side Widget Well -->
                <div class="well">
                    <h4>Sobre Nosotros</h4>
                    <p>Cuando se me ocurra algo te aviso. GN</p>
                </div>

            </div>

        </div>
        <!-- /.row -->



        <!-- Footer -->
        <footer>
            <div class="row">
                <div class="col-lg-12">
                    <p>Copyright &copy; Lit AF 2017</p>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
        </footer>

    </div>
    <!-- /.container -->

    <!-- jQuery -->
    <script src="js/jquery.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="js/bootstrap.min.js"></script>

</body>

</html>
