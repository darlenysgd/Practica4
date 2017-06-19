<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>${articulo.titulo}</title>

    <!-- Bootstrap Core CSS -->
    <link href="/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="/css/blog-post.css" rel="stylesheet">

    <link href="/css/Estilos.css" rel="stylesheet">

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
                    <a href="#"><span class="dorado">Registro</span></a>
                </li>
            </ul>
        </div>
        <!-- /.navbar-collapse -->
    </div>
    <!-- /.container -->
</nav>

<!-- Page Content -->
<div class="container">

    <div class="row">

        <!-- Blog Post Content Column -->
        <div class="col-lg-8">
            <!-- Blog Post -->

            <!-- Title -->
            <div class="row">
                <h1>${articulo.titulo}


                    <hr>
                    <div class="row">
                    <div class="col-xs-1">
                    <form action="/modificarArticulo/${indice}" method="get">
                        <button class="btn btn-icon"><i class="glyphicon glyphicon-pencil"></i></button>
                    </form>
                    </div>

                    <div class="col-xs-1">
                    <form action="/eliminarArticulo/${articulo.id}" method="post">
                        <button class="btn btn-icon"><i class="glyphicon glyphicon-trash"></i></button>
                    </form>
                    </div>
                    </div>
                    <hr>

            </div>
            <!-- Author -->
            <p>
                by <a href="#">${articulo.autor.username}</a>
            </p>

            <hr>

            <p>Likes: ${articulo.getLikes()} Dislikes: ${articulo.getDislikes()}</p>


            <hr>
            <!-- Date/Time -->
            <p><span class="glyphicon glyphicon-time"></span> Creado en ${articulo.fecha}</p>


            <!-- Post Content -->
            <p>${articulo.cuerpo}</p>

            <hr>

            <!-- Blog Comments -->

            <!-- Comments Form -->
        <#if logged>

            <div class="row">
                <div class="col-xs-1">
                    <form action="/like/${indice}" method="post">
                        <button class="btn btn-icon"><i class="glyphicon glyphicon-thumbs-up"></i></button>
                    </form>
                </div>

                <div class="col-xs-1">
                    <form action="/dislike/${indice}" method="post">
                        <button class="btn btn-icon"><i class="glyphicon glyphicon-thumbs-down"></i></button>
                    </form>
                </div>
            </div>
        <br>

            <div class="well">
                <h4>Deja un comentario:</h4>
                <form action="/comentar/${articulo.id}" method="post">
                    <div class="form-group">
                        <textarea class="form-control" name="comentario" rows="3"></textarea>
                    </div>
                    <button type="submit" class="btn btn-primary">Publicar</button>
                </form>
            </div>
        </#if>
            <hr>

            <!-- Posted Comments -->

                <!-- Comment -->
        <#if comentarioNull>
            <#list comentarios as comentario>

            <div class="media">
                <div class="media-body">
                    <h4 class="media-heading">${comentario.autor.username}</h4>
                    ${comentario.comentatio}
                    <p>Likes: ${comentario.getLikes()} Dislikes: ${comentario.getDislikes()}</p>

                <#if logged>

                    <div class="row">
                        <div class="col-xs-1">
                            <form action="/likeComment/${comentario_index}/${indice}" method="post">
                                <button class="btn btn-icon"><i class="glyphicon glyphicon-thumbs-up"></i></button>
                            </form>
                        </div>

                        <div class="col-xs-1">
                            <form action="/dislikeComment/${comentario_index}/${indice}" method="post">
                                <button class="btn btn-icon"><i class="glyphicon glyphicon-thumbs-down"></i></button>
                            </form>
                        </div>
                    </div>
                    <br>
                </#if>
                </div>
            </div>

            </#list>

        </#if>

        </div>

        <!-- Blog Sidebar Widgets Column -->
        <div class="col-md-4">


            <!-- Blog Categories Well -->
            <div class="well">
                <h4>Etiquetas</h4>
                <div class="row">
                    <div class="col-lg-4">
                    <#list etiquetas as etiqueta>
                        <form action="/Home/tags/${etiqueta_index}" method="get">

                            <button class="btn btn-icon">${etiqueta.etiqueta}</button>
                        </form>
                    </#list>
                    </div>
                </div>
            </div>
            <!-- /.row -->
        </div>

    </div>

</div>
<!-- /.row -->

<hr>

<!-- Footer -->
<footer>
    <div class="row">
        <div class="col-lg-12">
            <p>Copyright &copy; Your Website 2014</p>
        </div>
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
