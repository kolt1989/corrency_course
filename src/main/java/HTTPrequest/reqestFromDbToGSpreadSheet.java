package HTTPrequest;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Kozak on 25.10.2016.
 */
public class reqestFromDbToGSpreadSheet {
    public void requestDbToSpreadsheet(String param1ToGetRequest,String param2ToGetRequest,String param3ToGetRequest) throws IOException {

        String urlGet = "https://script.google.com/macros/s/AKfycbxw1gUdJTdWIrnaZ8CoiZn5h6iqxefuIV_DFYTxqh3BFKxh7-U/exec?"
                +"date="+param1ToGetRequest+"&"
                +"buy="+param2ToGetRequest+"&"
                +"sale="+param3ToGetRequest+"&";

        URL url = new URL(urlGet);
        url.openStream().close();
    }
}
