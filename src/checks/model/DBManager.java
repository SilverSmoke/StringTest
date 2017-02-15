package checks.model;

import java.sql.*;

/**
 * Created by belikov.a on 13.02.2017.
 */
public class DBManager {

    private static String host = "jdbc:mysql://localhost:3306/checkdb?useSSL=false";
    private static String user = "root";
    private static String pass = "";

    private static Connection connect = null;
    private static Statement statement = null;
    private static ResultSet result;

    public DBManager(){
        //Конструктор с параметрами по умолчанию
    }

    public DBManager(String host, String user, String pass){

        //Конструктор с парамерами пользователя

        this.host = host;
        this.user = user;
        this.pass = pass;

    }

    public static ResultSet getResult(String query){
        try{

            connect = DriverManager.getConnection(host, user, pass);

            statement = connect.createStatement();

            result = statement.executeQuery(query);



        }catch(SQLException sqlEx) {
            sqlEx.printStackTrace();
        }

        return result;
    }

    public void updateDB(String query){
        try{

            connect = DriverManager.getConnection(host, user, pass);

            statement = connect.createStatement();

            statement.executeUpdate(query);

            connect.close();

            statement.close();

        }catch(SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
    }

}
