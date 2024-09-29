package controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Autor;
import models.Livro;
import models.Status;
import repositories.AutorRepository;
import repositories.LivroRepository;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/livros/alterar")
public class LivrosAlterController extends HttpServlet {
    LivroRepository livroRepository = new LivroRepository();
    AutorRepository autorRepository = new AutorRepository();


    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Livro livro = livroRepository.getById(Integer.valueOf(req.getParameter("livroId")));
        List<Autor> autores = autorRepository.getAutores();
        req.setAttribute("autores", autores);
        req.setAttribute("livro", livro);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/livros/alterar.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Boolean valido = false;
        try {
            valido = livroRepository.validarDadosLivro(req);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (!valido){
            resp.sendRedirect(req.getContextPath() + "/formularioIncorreto.jsp");
            return;
        }
        try {
            valido = livroRepository.validarIdLivro(req);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (!valido){
            resp.sendRedirect(req.getContextPath() + "/formularioIncorreto.jsp");
            return;
        }

        Integer id = Integer.valueOf(req.getParameter("livro_id"));
        String nome = req.getParameter("livro_nome");
        LocalDate dataCriacao = LocalDate.parse(req.getParameter("livro_data_criacao"));
        Integer autorId = Integer.valueOf(req.getParameter("livro_autor"));
        Status status = Status.parse(Integer.valueOf(req.getParameter("livro_status")));

        Autor autor = new Autor();
        autor.setId(autorId);

        Livro livro = new Livro();
        livro.setId(id);
        livro.setNome(nome);
        livro.setData_criacao(dataCriacao);
        livro.setStatus(status);
        livro.setAutor(autor);

        livroRepository.update(livro);

        resp.sendRedirect(req.getContextPath() + "/livros");

    }
}
