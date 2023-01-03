<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Clientes</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style-menu.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/data-tables/datatables.min.css">
    <script src="https://kit.fontawesome.com/82ec21a6d1.js" crossorigin="anonymous"></script>
</head>
<body>
    <div class="d-flex">
        <div id="siledabar-container" class="fondo-menu">
            <div class="logo">
                <h4>System FAC</h4>
            </div>
            <div class="menu">
                <ul class="menu-lista">
                    <li class="nav-item">
                        <a class="nav-link" aria-current="page" href="index.html"><i class="fa-solid fa-house me-2"></i>Inicio</a>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown"
                            aria-expanded="false">
                            <i class="fa-solid fa-user me-2"></i>Usuarios
                        </a>
                        <ul class="dropdown-menu">
                            <li><a class="dropdown-item" href="<%=request.getContextPath()%>/usuarios">Usuarios</a></li>
                            <li><a class="dropdown-item" href="<%=request.getContextPath()%>/usuarios/roles">Roles</a></li>
                        </ul>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<%=request.getContextPath()%>/clientes"><i class="fa-solid fa-users me-2"></i>Clientes</a>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown"
                            aria-expanded="false">
                            <i class="fa-solid fa-cart-flatbed me-2"></i>Inventario
                        </a>
                        <ul class="dropdown-menu">
                            <li><a class="dropdown-item" href="productos.html">Productos</a></li>
                            <li><a class="dropdown-item" href="producto-categorias.html">Categorías</a></li>
                        </ul>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                            <i class="fa-solid fa-cart-shopping me-2"></i>Ventas
                        </a>
                        <ul class="dropdown-menu">
                            <li><a class="dropdown-item" href="facturacion.html">Facturar</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    
        <div class="w-100">
            <nav class="navbar navbar-expand-lg border-bottom">
                <div class="container-fluid">
                    <div class="navbar-collapse" id="navbarSupportedContent">
                        <ul class="navbar-nav ms-auto">
                            <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown"
                                    aria-expanded="false">
                                    <i class="fa-solid fa-circle-user"></i>
                                    JOAQUIN FARRO
                                </a>
                                <ul class="dropdown-menu">
                                    <li><a class="dropdown-item" href="#">Mi perfil</a></li>
                                    <li>
                                        <hr class="dropdown-divider">
                                    </li>
                                    <li><a class="dropdown-item" href="#">Cerrar sesión</a></li>
                                </ul>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>

            <section class="seccion-title m-3 border-bottom">
                <div class="row">
                    <div class="col-lg-9">
                        <h1>Clientes</h1>
                        <p class="links">
                            <a href="index.html"><i class="fa-solid fa-house me-2"></i>Inicio</a> / Clientes
                        </p>
                    </div>
                </div>
            </section>

            <section class="section-data m-3">
                <div class="row ms-1">
                    <div class="col-lg-4">
                        <div class="d-grid gap-2 d-md-block">
                            <button class="btn btn-primary ps-5 pe-5 me-1" type="button" data-bs-toggle="modal" data-bs-target="#staticBackdrop">Nuevo cliente</button>
                            <button class="btn btn-danger ps-4 pe-4 me-1" type="button">Reporte PDF</button>
                            <button class="btn btn-success ps-4 pe-4 me-1" type="button">Reporte Excel</button>
                        </div>
                    </div>
                </div>
                <div class="row mt-3 ms-3 me-3" style="width: 1650px;">
                    <div class="card">
                        <div class="card-body">
                            <table id="table" class="table">
                                <thead>
                                <tr>
                                    <th>Codigo</th>
                                    <th>Nombres</th>
                                    <th>Apellido paterno</th>
                                    <th>Apellido materno</th>
                                    <th>Tipo de documento</th>
                                    <th>Nro de documento</th>
                                    <th>Celular</th>
                                    <th>Dirección</th>
                                    <th>Email</th>
                                    <th>Estado</th>
                                    <th>Acciones</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${clients}" var="c">
                                    <tr>
                                        <td>${c.code}</td>
                                        <td>${c.names}</td>
                                        <td>${c.apePat}</td>
                                        <td>${c.apeMat}</td>
                                        <td>${c.documentType.name}</td>
                                        <td>${c.nroDoc}</td>
                                        <td>${c.phone}</td>
                                        <td>${c.direction}</td>
                                        <td>${c.email}</td>
                                        <td>${c.userHistory.state == 1 ? "ACTIVO" : "INACTIVO"}</td>
                                        <td>
                                            <a href="<%=request.getContextPath()%>/clientes?id=${c.id}" class="btn btn-success">Editar</a>
                                            <a href="<%=request.getContextPath()%>/clientes?idDelete=${c.id}" class="btn btn-danger">Eliminar</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </section>

        </div>
    </div>

    <div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
        aria-labelledby="staticBackdropLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <form action="<%=request.getContextPath()%>/clientes" method="post">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="staticBackdropLabel">Código de cliente: ${code} </h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close" id="btnClose"></button>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-12 mb-3">
                                <c:if test="${!empty messages and !messages.containsKey('exito')}">
                                    <div class="alert alert-danger" role="alert">
                                        <c:forEach items="${messages.keySet()}" var="e">
                                            <div>${messages.get(e)}</div>
                                        </c:forEach>
                                    </div>
                                </c:if>
                            </div>
                        </div>
                        <div class="row">
                            <input type="hidden" name="code" value="${code}"/>
                            <div class="col-12 mb-3">
                                <label for="nombres" class="form-label">Nombre completo:</label>
                                <input type="text" class="form-control" id="nombres" name="names" value="${client.names != null ? client.names : ""}"/>
                            </div>
                        </div>
                        <div class="row g-2 mb-3">
                            <div class="col-6">
                                <label for="apePat" class="form-label">Apellido paterno:</label>
                                <input type="text" class="form-control" id="apePat" name="apePat" value="${client.apePat != null ? client.apePat : ""}"/>
                            </div>
                            <div class="col-6">
                                <label for="apeMat" class="form-label">Apellido materno:</label>
                                <input type="text" class="form-control" id="apeMat" name="apeMat" value="${client.apeMat != null ? client.apeMat : ""}"/>
                            </div>
                        </div>
                        <div class="row g-2 mb-3">
                            <div class="col-8">
                                <label class="form-label">Tipo de documento:</label>
                                <select class="form-select" name="typeDocu">
                                    <option value="0">-Seleccionar-</option>
                                    <c:forEach items="${documentTypes}" var="d">
                                        <option value="${d.id}" ${client.documentType.id == d.id ? "selected" : ""}>${d.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-4">
                                <label for="nroDocu" class="form-label">Nro de documento:</label>
                                <input type="text" class="form-control" id="nroDocu" name="nroDocu" value="${client.nroDoc != null ? client.nroDoc : ""}"/>
                            </div>
                        </div>
                        <div class="row g-2 mb-3">
                            <div class="col-4">
                                <label for="celular" class="form-label">Celular:</label>
                                <input type="text" class="form-control" id="celular" name="phone" value="${client.phone != null ? client.phone : ""}"/>
                            </div>
                            <div class="col-8">
                                <label for="email" class="form-label">Correo electrónico:</label>
                                <input type="email" class="form-control" id="email" name="email" value="${client.email != null ? client.email : ""}"/>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-12 mb-3">
                                <label for="direction" class="form-label">Dirección de domicilio:</label>
                                <input type="text" class="form-control" id="direction" name="direction" value="${client.direction != null ? client.direction : ""}"/>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <input type="submit" class="btn btn-primary" value="Guardar"/>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script src="<%=request.getContextPath()%>/bootstrap/js/bootstrap.bundle.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/jquery-3.5.1.js"></script>
    <script src="<%=request.getContextPath()%>/js/jquery.dataTables.min.js"></script>
    <script src="<%=request.getContextPath()%>/data-tables/datatables.min.js"></script>
    <c:if test="${!empty messages and !messages.containsKey('exito') or client.id != null}">
        <script>
            $(function () {
                $('#staticBackdrop').modal("show");
            });
        </script>
    </c:if>
    <script src="<%=request.getContextPath()%>/js/data-client.js"></script>
    <script src="<%=request.getContextPath()%>/js/table-pagination.js"></script>
</body>
</html>