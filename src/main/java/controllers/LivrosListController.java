package controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Livro;
import repositories.LivroRepository;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/livros"})
public class LivrosListController extends HttpServlet {

    private LivroRepository repository;

    public LivrosListController(){
        repository = new LivroRepository();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Livro> livros = repository.getLivros();

        RequestDispatcher dispatcher = req.getRequestDispatcher("/livros/");
        req.setAttribute("livros", livros);

        dispatcher.forward(req, resp);
    }
}