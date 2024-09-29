<%@ page import="models.Livro" %>
<%@ page import="java.util.List" %>
<%@ page import="models.Autor" %>
<%@ page import="models.Status" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% List<Autor> autores = (List<Autor>) request.getAttribute("autores"); %>
<% Status discartStatus = Status.parse(1); %>
<% Status statusValues[] = discartStatus.getDeclaringClass().getEnumConstants(); %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Inserir Livro</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/dashboard.css">

</head>
<body class="bg-light">

<!-- Menu superior -->
<nav class="navbar navbar-expand-lg shadow-sm navbar-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="#"> <img src="images/logo.png" alt="">Biblioteca</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="#">Início</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Livros</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Relatórios</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Configurações</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<div class="container-fluid">
    <div class="row">

        <!-- Barra lateral -->
        <nav id="sidebar" class="col-md-3 col-lg-2 d-md-block bg-light sidebar">
            <div class="position-sticky pt-3">
                <ul class="nav flex-column">
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="#">
                            <span data-feather="home"></span>
                            Dashboard
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">
                            <span data-feather="file"></span>
                            Pedidos
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">
                            <span data-feather="shopping-cart"></span>
                            Produtos
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">
                            <span data-feather="users"></span>
                            Livros
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">
                            <span data-feather="bar-chart-2"></span>
                            Relatórios
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">
                            <span data-feather="layers"></span>
                            Integrações
                        </a>
                    </li>
                </ul>
            </div>
        </nav>

        <!-- Conteúdo principal -->
        <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4 main-content">

            <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                <h1 class="h2">Cadastro de Livro</h1>
            </div>

            <form action="<%= request.getContextPath() %>/livros/inserir" method="post">

                <div class="mb-3">
                    <label for="nome" class="form-label">Nome: </label>
                    <input type="text" class="form-control" id="nome" name="livro_nome">
                </div>

                <div class="mb-3">
                    <label for="data_criacao" class="form-label">Data de criação: </label>
                    <input type="date" class="form-control" id="data_criacao" name="livro_data_criacao">
                </div>

                <div class="mb-3">
                    <label for="status" class="form-label">Status: </label>
                    <select class="form-select" name="livro_status" id="status">

                        <% for (Status status: statusValues) {%>
                        <option value="<%= status.getId()%>"><%=status.toString() %></option>
                        <% } %>

                    </select>
                </div>

                <div class="mb-3">
                    <label for="autor" class="form-label">Autor: </label>
                    <select class="form-select" name="livro_autor" id="autor">
                        <% for (Autor autor: autores) {%>
                        <option value="<%= autor.getId()%>"><%=autor.getNome() %></option>
                        <% } %>
                    </select>
                </div>

                <div class="col-12">
                    <button class="btn btn-primary btn-sm px-5" type="submit">cadastrar</button>
                </div>

            </form>

        </main>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.7/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>