package repositories;

import connection.ConnectionFactory;
import exceptions.DatabaseException;
import models.Autor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AutorRepository {

    private Connection connection;

    public AutorRepository() {

        connection = ConnectionFactory.getConnection();

    }

    public List<Autor> getAutores() {

        List<Autor> autores = new ArrayList<>();

        try {

            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(
                    "SELECT * FROM autor;"
            );

            while (result.next()) {

                Autor autor = instantiateAutor(result);

                autores.add(autor);
            }

            result.close();

        } catch (SQLException e) {

            throw new RuntimeException(e);

        } finally {
            ConnectionFactory.closeConnection();
        }


        return autores;
    }

    public Autor insert(Autor autor) {
        String sql = "INSERT INTO autor (nome) VALUES(?)";

        try {
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, autor.getNome());

            Integer rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {

                ResultSet id = statement.getGeneratedKeys();

                id.next();

                Integer autorId = id.getInt(1);

                System.out.println("Rows inserted: " + rowsInserted);
                System.out.println("Id: " + autorId);

                autor.setId(autorId);
            }


        } catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        }

        return autor;
    }

    public void update(Autor autor) {

        String sql = "UPDATE autor SET " +
                "nome = ? " +
                "WHERE (autor.Id = ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, autor.getNome());
            statement.setInt(2, autor.getId());

            int rowsAffected = statement.executeUpdate();

            System.out.println("Rows affected: " + rowsAffected);

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }

    }

    public void delete(Integer id) {

        String sql = "DELETE FROM autor WHERE Id = ?";

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

    public Autor getById(Integer id) {

        Autor autor;

        String sql =
                "SELECT * FROM autor WHERE id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                autor = this.instantiateAutor(resultSet);

            } else {
                throw new DatabaseException("Autor não encontrado");
            }

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }

        return autor;
    }

    public Autor instantiateAutor(ResultSet resultSet) throws SQLException {

        Autor autor = new Autor();

        autor.setId(resultSet.getInt("id"));
        autor.setNome(resultSet.getString("nome"));

        return autor;
    }
}