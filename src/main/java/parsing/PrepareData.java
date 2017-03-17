package parsing;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by user on 06.09.2016.
 */
public class PrepareData {
    static String curency;
    static String baseCurency;
    static double buy;
    static double sale;
    JSONObject jsonObject = new JSONObject();

public String filterDataCurency(JSONArray json, int numberOfObject) throws JSONException {
    jsonObject =json.getJSONObject(numberOfObject);
     curency = jsonObject.getString("ccy");
    return curency;
}
    public String filterDataBaseCurency(JSONArray json, int numberOfObject) throws JSONException {
        jsonObject =json.getJSONObject(numberOfObject);
        baseCurency = jsonObject.getString("base_ccy");
        return baseCurency;
    }
    public Double filterDataBuy(JSONArray json, int numberOfObject) throws JSONException {
        jsonObject =json.getJSONObject(numberOfObject);
         buy = jsonObject.getDouble("buy");
        return buy;
    }
    public Double filterDataSale(JSONArray json, int numberOfObject) throws JSONException {
        jsonObject =json.getJSONObject(numberOfObject);
        sale = jsonObject.getDouble("sale");
        return sale;
    }
}
