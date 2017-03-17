import java.io.*;
import java.util.Date;
import java.util.Properties;

import mailMerge.MailSend;
import org.json.JSONArray;
import org.json.JSONException;
import parsing.ParsingJSON;
import parsing.PrepareData;
import workWithDataBase.JDBC;

public class Main {
    //private static final String URL ="https://api.privatbank.ua/p24api/pubinfo?json&exchange&coursid=5";
    private static final Properties prop = new Properties();

        public static void main(String[] args) throws IOException, JSONException {
            prop.load(new FileInputStream("config.properties"));
            String URL = prop.getProperty("APIurl");
            //
           /* String folderToLogs = prop.getProperty("folderLog");
            FileWriter writer = new FileWriter(folderToLogs,true);
            Date timeJobs = new Date();*/
            JSONArray json= ParsingJSON.readJsonFromUrl(URL);
            PrepareData data = new PrepareData();
            JDBC dataBase = new JDBC();
            dataBase.selectToGoogleAppScript();

            if (dataBase.selectTable(data.filterDataCurency(json,2).toString()
                    ,data.filterDataBuy(json, 2)
                    ,data.filterDataSale(json,2))==true) {
                                MailSend.mailSend(data.filterDataCurency(json, 2).toString(), data.filterDataBuy(json, 2), data.filterDataSale(json, 2));
            }
            for(int i =0; i<3; i++){
                dataBase.insertTable(data.filterDataCurency(json,i),data.filterDataBaseCurency(json,i),data.filterDataBuy(json,i),data.filterDataSale(json,i));
            }
            System.out.println("End of program.");


           /* String textToLog= dataBase.selectTable(data.filterDataCurency(json,2).toString()
                    ,data.filterDataBuy(json, 2)
                    ,data.filterDataSale(json,2)).toString()+" "+timeJobs.toString()+" "+"\r\n";
            writer.write(textToLog);
            writer.flush();
            writer.close();*/
        }

    }

