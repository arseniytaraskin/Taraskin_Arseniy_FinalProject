package countries;

import countries.DataBase;

import java.awt.*;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        csvParser pr = new csvParser();

        //Connection connection = DriverManager.getConnection("jdbc:sqlite:C:/Java_Ulearn/Final_Project/FinalProject/Countries.db");
        //Statement statement = connection.createStatement();

        DataBase sqlite = new DataBase("jdbc:sqlite:FinalProject/Countries.db");
        DataBase.createTable(pr, sqlite);

        /*ResultSet resultSet = statement.executeQuery("Select count(*) from CountriesTable");

        if (resultSet.getInt(1) < 100){

            connection.close();
        }*/




        System.out.println("Ответы на задания.");

        FirstTask();
        SecondTaskSQL();
        ThirdTaskSQL();


    }
    public static void FirstTask() throws SQLException {
        //Постройте график процентного соотношения пользователей в интернете от всего населения по субрегионам.
        Map<String, Float> countUsers = new HashMap<>();
        String req = "SELECT 1.0 * InternetUsers/Population * 100 AS InternetUsers, Subregion " +
                "FROM CountriesTable " +
                "GROUP BY Subregion;";

        ResultSet resSet = countries.DataBase.statement.executeQuery(req);

        while (resSet.next()){
            countUsers.put(
                    resSet.getString("Subregion"),
                    Float.parseFloat(resSet.getString("InternetUsers"))
            );
        }

        EventQueue.invokeLater(() -> {
            var pl = new Graph(countUsers);
            pl.setVisible(true);
        });
    }

    public static void SecondTaskSQL() throws SQLException {
        //Выведите название страны с наименьшим кол-вом зарегистрированных в ин-ете пользователей в Восточной Европе.
        System.out.println("\nЗадание 2. \n" +
                "Выведите название страны с наименьшим кол-вом зарегистрированных в ин-ете пользователей в Восточной Европе.");

        String req = "Select * From CountriesTable WHERE InternetUsers = (Select Min(InternetUsers) FROM CountriesTable WHERE Subregion = 'Eastern Europe') ";

        String answer = DataBase.statement.executeQuery(req).getString("CountryOfArea");
        
        System.out.println(answer);
    }

    public static void ThirdTaskSQL() throws SQLException {
        //Выведите в консоль название страны процент зарегистрированных в интернете пользователей которой находится в промежутке от 75% до 85%
        System.out.println("\nЗадание 3. \n" +
                "Выведите в консоль название страны процент зарегистрированных в интернете пользователей которой находится в промежутке от 75% до 85%");

        String req = "SELECT * FROM CountriesTable WHERE 1.0 * InternetUsers / Population  > 0.75 and 1.0 * InternetUsers / Population <= 0.85 LIMIT 1 ";

        String answer = DataBase.statement.executeQuery(req).getString("CountryOfArea");

        System.out.println(answer);
    }



}