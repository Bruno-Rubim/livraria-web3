<%--
  Created by IntelliJ IDEA.
  User: brnuo
  Date: 24/09/2024
  Time: 18:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="models.Livro" %>
<%@ page import="java.util.List" %>
<%@ page import="utils.DateUtils" %>
<%@ page import="models.Status" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% List<Livro> livros = (List<Livro>) request.getAttribute("livros"); %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lista de livros</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <link rel="stylesheet" href="../css/dashboard.css"></head>
<body class="bg-light">
    <div class="container-fluid">
        <div class="row">
            <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4 main-content">
                <br>
                <a class="btn btn-sm btn-primary" href="<%= request.getContextPath() %>/livros/inserir">Inserir</a>
                <br><br><br>
                <div class="table-responsive">
                    <table class="table table-striped table-sm">
                        <thead>
                        <tr>
                            <th>Id</th>
                            <th>Nome</th>
                            <th>Autor</th>
                            <th>Data de criação</th>
                            <th>Status</th>
                        </tr>
                        </thead>
                        <tbody>

                        <% if(livros != null) {; %>
                            <% for (Livro livro : livros) {%>
                            <tr>
                                <td><%= livro.getId() %></td>
                                <td><%= livro.getNome() %></td>
                                <td><%= livro.getAutor().getNome()%></td>
                                <td><%= livro.getData_criacao()%></td>
                                <td>
                                    <% Status statusValues[] = livro.getStatus().getDeclaringClass().getEnumConstants(); %>
                                    <b><%= livro.getStatus().toString()%></b>
                                    <% for (Status possibleSatus : statusValues) {%>
                                        <%
                                            System.out.println(possibleSatus.toString());
                                            if (possibleSatus != livro.getStatus()){
                                        %>
                                        <a class="btn btn-sm btn-primary" href="<%= request.getContextPath() %>/livros/alterarStatus?livroId=<%= livro.getId() %>&statusId=<%= possibleSatus.getId() %>"><%= possibleSatus.toString()%></a>
                                        <% } %>
                                    <% } %>
                                </td>
                                <td><a class="btn btn-sm btn-primary" href="<%= request.getContextPath() %>/livros/alterar?livroId=<%= livro.getId()%>">Alterar</a></td>
                                <td><a class="btn btn-sm btn-danger" href="<%= request.getContextPath() %>/livros/excluir?livroId=<%= livro.getId()%>">Excliur</a></td>
                            </tr>
                            <% } %>
                        <% } %>
                    </table>
                </div>
            </main>
        </div>
    </div>
</body>
</html>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.7/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>