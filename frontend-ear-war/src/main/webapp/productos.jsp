<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Productos</title>
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
                            <li><a class="dropdown-item" href="<%=request.getContextPath()%>/inventario/productos">Productos</a></li>
                            <li><a class="dropdown-item" href="producto-categorias.jsp">Categor??as</a></li>
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
                                    <li><a class="dropdown-item" href="#">Cerrar sesi??n</a></li>
                                </ul>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>

            <section class="seccion-title m-3 border-bottom">
                <div class="row">
                    <div class="col-lg-9">
                        <h1>Productos</h1>
                        <p class="links">
                            <a href="<%=request.getContextPath()%>"><i class="fa-solid fa-house me-2"></i>Inicio</a> / Inventario / Productos
                        </p>
                    </div>
                </div>
            </section>

            <section class="section-data m-3">
                <div class="row ms-1">
                    <div class="col-lg-4">
                        <div class="d-grid gap-2 d-md-block">
                            <button class="btn btn-primary ps-5 pe-5 me-1" type="button" data-bs-toggle="modal" data-bs-target="#staticBackdrop">Nuevo producto</button>
                            <button class="btn btn-danger ps-4 pe-4 me-1" type="button">Reporte PDF</button>
                            <button class="btn btn-success ps-4 pe-4 me-1" type="button">Reporte Excel</button>
                        </div>
                    </div>
                </div>
                <div class="row mt-3 ms-3 me-3">
                    <div class="card">
                        <div class="card-body">
                            <table id="table" class="table">
                                <thead>
                                    <tr>
                                        <th>Codigo</th>
                                        <th>Nombre</th>
                                        <th>Categor??a</th>
                                        <th>Cantidad</th>
                                        <th>Precio</th>
                                        <th>Estado</th>
                                        <th>Acciones</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${products}" var="p">
                                        <tr>
                                            <td>${p.code}</td>
                                            <td>${p.name}</td>
                                            <td>${p.category.name}</td>
                                            <td>${p.amount}</td>
                                            <td>${p.price}</td>
                                            <td>${p.userHistory.state}</td>
                                            <td>
                                                <a href="<%=request.getContextPath()%>/inventario/productos?id=${p.id}" class="btn btn-success">Editar</a>
                                                <a href="<%=request.getContextPath()%>/inventario/productos?idDelete=${p.id}" class="btn btn-danger">Eliminar</a>
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
                <form action="<%=request.getContextPath()%>/inventario/productos" method="post">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="staticBackdropLabel">C??digo de producto: ${code} </h1>
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
                            <div class="col-12 mb-3">
                                <label for="names" class="form-label">Nombre:</label>
                                <input type="text" class="form-control" id="names" name="names" value="${product.name != null ? product.name : ""}"/>
                            </div>
                        </div>
                        <div class="row g-2 mb-3">
                            <div class="col-6">
                                <label for="amount" class="form-label">Cantidad:</label>
                                <input type="text" class="form-control" id="amount" name="amount" value="${product.amount != null ? product.amount : ""}"/>
                            </div>
                            <div class="col-6">
                                <label for="price" class="form-label">Precio:</label>
                                <input type="text" class="form-control" id="price" name="price" value="${product.price != null ? product.price : ""}"/>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-12 mb-3">
                                <label class="form-label">Categor??a:</label>
                                <select class="form-select" name="category">
                                    <option selected>-Seleccionar-</option>
                                    <c:forEach items="${categories}" var="c">
                                        <option value="${c.id}" value="${product.category.id == c.id ? "selected" : ""}">${c.name}</option>
                                    </c:forEach>
                                </select>
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
    <script src="<%=request.getContextPath()%>/data-tables/datatables.min.js"></script>
    <c:if test="${!empty messages and !messages.containsKey('exito') or product.id != null}">
        <script>
            $(function () {
                $('#staticBackdrop').modal("show");
            });
        </script>
    </c:if>
    <script src="<%=request.getContextPath()%>/js/table-pagination.js"></script>
    <script src="<%=request.getContextPath()%>/js/data-product.js"></script>
</body>
</html>