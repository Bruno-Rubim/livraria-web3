package repositories;

import connection.ConnectionFactory;
import exceptions.DatabaseException;
import jakarta.servlet.http.HttpServletRequest;
import models.Autor;
import models.Status;
import models.Livro;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
public class LivroRepository {

    private Connection connection;

    public LivroRepository() {

        connection = ConnectionFactory.getConnection();

    }

    public List<Livro> getLivros() {

        List<Livro> livros = new ArrayList<>();

        try {

            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(
                    "SELECT livro.*," +
                            "autor.nome as autor_nome, status.status as status FROM livro " +
                            "INNER JOIN autor ON autor.id = livro.autor_id " +
                            "INNER JOIN status ON status.id = livro.status_id;"
            );

            while (result.next()) {

                Livro livro = instantiateLivro(result);

                livros.add(livro);
            }

            result.close();


        } catch (SQLException e) {

            throw new RuntimeException(e);

        } finally {
            ConnectionFactory.closeConnection();
        }


        return livros;
    }

    public Livro insert(Livro livro) {
        String sql = "INSERT INTO livro (nome, data_criacao, autor_id, status_id) " +
                "VALUES(?, ?, ?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, livro.getNome());
            statement.setDate(2, Date.valueOf(livro.getData_criacao()));
            statement.setInt(3, livro.getAutor().getId());
            statement.setInt(4, livro.getStatus().getId());

            Integer rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {

                ResultSet id = statement.getGeneratedKeys();

                id.next();

                Integer livroId = id.getInt(1);

                System.out.println("Rows inserted: " + rowsInserted);
                System.out.println("Id: " + livroId);

                livro.setId(livroId);

            }


        } catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        }

        return livro;
    }

    public void update(Livro livro) {

        String sql = "UPDATE livro SET " +
                "nome = ?, " +
                "data_criacao = ?, " +
                "autor_id = ?, " +
                "status_id = ? " +
                "WHERE (livro.Id = ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, livro.getNome());
            statement.setDate(2, Date.valueOf(livro.getData_criacao()));
            statement.setInt(3, livro.getAutor().getId());
            statement.setInt(4, livro.getStatus().getId());
            statement.setInt(5, livro.getId());

            int rowsAffected = statement.executeUpdate();

            System.out.println("Rows affected: " + rowsAffected);

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }

    }

    public void delete(Integer id) {

        String sql = "DELETE FROM livro WHERE Id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, id);
            Integer rowsDeleted = statement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Rows deleted: " + rowsDeleted);
            }

        } catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
    }

    public Livro getById(Integer id) {

        Livro livro;

        String sql =
                "SELECT livro.id as id, livro.nome as nome, livro.data_criacao as data_criacao, " +
                "autor.nome as autor_nome, autor.id as autor_id, status.status as status, status.id as status_id FROM livro " +
                "INNER JOIN autor ON autor.id = livro.autor_id " +
                "INNER JOIN status ON status.id = livro.status_id " +
                "WHERE livro.Id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                livro = this.instantiateLivro(resultSet);

            } else {
                throw new DatabaseException("Livro não encontrado");
            }

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }

        return livro;
    }

    public Livro instantiateLivro(ResultSet resultSet) throws SQLException {
        Livro livro = new Livro();
        Autor autor = instantiateAutor(resultSet);

        livro.setId(resultSet.getInt("id"));
        livro.setNome(resultSet.getString("nome"));
        livro.setData_criacao(Date.valueOf(resultSet.getString("data_criacao")).toLocalDate());
        livro.setAutor(autor);
        livro.setStatus(Status.parse(resultSet.getInt("status_id")));
        return livro;
    }

    public void alterarStatus(int livroId, int statusId){
        String sql = "UPDATE livro SET " +
                "status_id = ? " +
                "WHERE (livro.Id = ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, statusId);
            statement.setInt(2, livroId);

            int rowsAffected = statement.executeUpdate();

            System.out.println("Rows affected: " + rowsAffected);

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public Autor instantiateAutor(ResultSet resultSet) throws SQLException {

        Autor autor = new Autor();

        autor.setId(resultSet.getInt("autor_id"));
        autor.setNome(resultSet.getString("autor_nome"));

        return autor;
    }

    public Boolean validarIdLivro(HttpServletRequest request) throws SQLException {
        String id = (request.getParameter("livro_id"));
        try
        {
            Integer.parseInt(id);
        } catch (NumberFormatException e) {
            System.out.println("Id invalido");
            return false;
        }
        return true;
    }

    public Boolean validarDadosLivro(HttpServletRequest request) throws SQLException {
        if(!validarDadosAutor(request)){
            System.out.println("autor inexistente");
            return false;
        }

        String nome = request.getParameter("livro_nome").trim();
        if(nome.equals("")){
            System.out.println("titulo inexistente");
            return false;
        }

        String dataCriacao = request.getParameter("livro_data_criacao");
        String format = "yyyy-MM-dd";
        try {
            DateFormat df = new SimpleDateFormat(format);
            df.setLenient(false);
            df.parse(dataCriacao);
        } catch (ParseException e) {
            System.out.println(dataCriacao);
            System.out.println("data invalida");
            return false;
        }

        String status = (request.getParameter("livro_status"));
        int statusId;
        try
        {
            statusId = Integer.parseInt(status);
        } catch (NumberFormatException e) {
            return false;
        }

        if(Status.parse(statusId) == null){
            System.out.println("status invalido");
            return false;
        }
        return true;
    }

    public Boolean validarDadosAutor(HttpServletRequest request) throws SQLException {
        List<Integer> autoresId = new ArrayList<Integer>();
        Boolean containsAutor = false;
        Boolean validated = true;
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(
                    "SELECT id FROM autor;"
            );
            while (result.next()) {
                if (result.getInt("id") == Integer.valueOf(request.getParameter("livro_autor"))) {
                    containsAutor = true;
                }
            }
            result.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (NumberFormatException e) {
            return false;
        } finally {
            ConnectionFactory.closeConnection();
        }
        if (!containsAutor){
            return false;
        }
        return validated;
    }
}