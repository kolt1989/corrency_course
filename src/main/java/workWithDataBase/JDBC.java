package workWithDataBase;

import HTTPrequest.reqestFromDbToGSpreadSheet;

import java.io.IOException;
import java.sql.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;

public class JDBC {
   public static final Properties prop = new Properties();
    // workWithDataBase.JDBC URL, username and password
    /*public static final  String url = prop.getProperty("urlDatabase");
    final  String user = prop.getProperty("userDatabase");
    final  String password = prop.getProperty("passwordDatabase");
    */

    public static Connection con;
    String url ="jdbc:postgresql://localhost:5432/postgres";
    String user = "postgres";
    String password = "pumpkin1";

    java.util.Date now = new java.util.Date();
    long currentTime = now.getTime();
    Timestamp timestamp = new Timestamp(currentTime);
    PreparedStatement preparedStatement= null;
    Statement stmt = null;
    public Boolean selectTable(String ccy,double buy_c, double sale_c){
        SqlQueryLibrary query=new SqlQueryLibrary();
        Boolean changes = false;
        try {
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();
            ResultSet resultSet = stmt.executeQuery(query.slectQueryLastUpdate);
            while(resultSet.next()) {
                String currency = resultSet.getString("ccy");
                double buy = resultSet.getDouble("buy");
                double sale = resultSet.getDouble("sale");
                System.out.println(currency + " "+buy+" "+sale);
                System.out.println(ccy+" " +buy_c+" "+sale_c);
                if (currency.equals(ccy)){
                    if(buy!=buy_c||sale!=sale_c){
                        System.out.println("trued");
                       changes = true;}
                }
            }
            resultSet.close();

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        } finally {
            //close connection ,stmt and resultset here
            try {
                con.close();
            } catch (SQLException se) { /*can't do anything */ }
        }
        return changes ;
    }
    public void insertTable(String ccy,String base_ccy,Double buy,Double sale) {

        SqlQueryLibrary query=new SqlQueryLibrary();
        try {
            con = DriverManager.getConnection(url, user, password);
            preparedStatement =con.prepareStatement(query.insertTable);

            preparedStatement.setString(1, ccy);
            preparedStatement.setString(2, base_ccy);
            preparedStatement.setDouble(3, buy);
            preparedStatement.setDouble(4, sale);
            preparedStatement.setTimestamp(5, timestamp);
            preparedStatement.executeUpdate();


        } catch (SQLException e) {

            System.out.println(e.getMessage());

        } finally {
            //close connection ,stmt and resultset here
            try {
                con.close();
            } catch (SQLException se) { /*can't do anything */ }
            try {
                preparedStatement.close();
            } catch (SQLException se) { /*can't do anything */ }

        }

    }
    public void selectToGoogleAppScript(){
        SqlQueryLibrary query=new SqlQueryLibrary();
        try {
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();
            ResultSet resultSet = stmt.executeQuery(query.selectQueryDataMaxValueGroupByDay);
            reqestFromDbToGSpreadSheet getRequest = new reqestFromDbToGSpreadSheet();

            while(resultSet.next()) {
                String updatetime = resultSet.getString("dt");
                String buy = resultSet.getString("buy").replace(".",",");
                //double buy = resultSet.getDouble("buy");
                String sale = resultSet.getString("sale").replace(".",",");

                getRequest.requestDbToSpreadsheet(updatetime,buy,sale);
               // Thread.sleep(5);
            }
            resultSet.close();

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //close connection ,stmt and resultset here
            try {
                con.close();
            } catch (SQLException se) { /*can't do anything */ }
        }
    }
}