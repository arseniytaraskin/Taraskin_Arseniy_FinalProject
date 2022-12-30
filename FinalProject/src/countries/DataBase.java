package countries;

import au.com.bytecode.opencsv.CSVParser;

import java.sql.*;
import java.util.Iterator;
import java.util.List;

public class DataBase {
    private static Connection connection;
    protected static Statement statement;
    public DataBase(String path) throws SQLException {
        connection = DriverManager.getConnection(path);
        statement = connection.createStatement();
    }

    public void fillDatabase(List<Countries> buildings) {
        Iterator var2 = buildings.iterator();

        while(var2.hasNext()) {
            Countries c = (Countries) var2.next();
            this.addValue(c);
        }

    }

    public void addValue(Countries item){
        String req = "INSERT INTO CountriesTable (Id, CountryOfArea, Subregion, Region, InternetUsers, Population) " +
                "VALUES(?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(req)) {

            statement.setObject(1, item.Id());

            statement.setObject(2, item.CountryOfArea());

            statement.setObject(3, item.Subregion());

            statement.setObject(4, item.Region());

            String var1 = item.InternetUsers();

            String[] val1 = var1.split(",");

            String res1 = String.join("", val1);

            statement.setObject(5, Integer.parseInt(res1));

            if (item.Population().length() > 0){
                String var2 = item.Population();
                String[] val2 = var2.split(",");
                String res2 = String.join("", val2);

                statement.setObject(6, res2);
            }
            else{
                statement.setObject(6, Integer.parseInt(res1));
            }
            //statement.setObject(7, forbes.industry().substring(0, forbes.industry().length() - 1));
            statement.execute();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createTable(csvParser pr, DataBase sql) throws SQLException{
        String req= " CREATE TABLE IF NOT EXISTS CountriesTable( Id INTEGER, CountryOfArea TEXT, Subregion TEXT, Region TEXT, InternetUsers INTEGER, Population INTEGER); ";
        statement.execute(req);
        //здесь у меня будет проверка, для того чтобы исключить добавление новых элементов при перезапуске
        ResultSet resultSet = statement.executeQuery("Select count(*) from CountriesTable");

        if (resultSet.getInt(1) < 10){
        pr.countriesList.forEach(sql::addValue);
        }
    }


}
