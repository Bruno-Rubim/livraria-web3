package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repositories.LivroRepository;

import java.io.IOException;

@WebServlet("/livros/excluir")
public class LivrosDeleteController extends HttpServlet {
    LivroRepository repository = new LivroRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Integer id = Integer.valueOf(req.getParameter("livroId"));

        repository.delete(id);

        resp.sendRedirect("http://localhost:8080/livraria_web3_war_exploded/livros");

    }
}
