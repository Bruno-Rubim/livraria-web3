package connection;

import exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private static Connection connection;

    public static Connection getConnection(){

        String url = "jdbc:mysql://localhost/library";
        String user = "root";
        String pass = "root";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, pass);

        } catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return connection;
    }

    public static void  closeConnection(){

//        try{
//            connection.close();
//        }catch (SQLException e){
//
//            throw new DatabaseException("Não foi possível encerrar a conexao: " + e.getMessage());
//
//        }


    }

}