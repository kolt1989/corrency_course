package workWithDataBase;

/**
 * Created by Kozak on 08.09.2016.
 */
public class SqlQueryLibrary {
    String insertTable = "INSERT INTO onyx.curency_course (ccy,base_ccy,buy,sale,updatetime)" +
            "VALUES (?,?,?,?,?)";
    String slectQueryLastUpdate = " select\n" +
            "                trim(ccy) as ccy,\n" +
            "                buy,\n" +
            "                sale,\n" +
            "\t\tupdatetime\n" +
            "                from\n" +
            "                onyx.curency_course\n" +
            "                 where updatetime\n" +
            "                between (select\n" +
            "                 max(updatetime)- TIME '00:01'\n" +
            "                from onyx.curency_course\n" +
            "                 )   \n" +
            "                and\n" +
            "                 (select\n" +
            "                 max(updatetime) \n" +
            "                 from onyx.curency_course\n" +
            "                 ) \n" +
            "\t\torder by updatetime desc \n" +
            "                 limit 3";
    String selectQueryDataMaxValueGroupByDay = "SELECT TO_CHAR(updatetime,'YYYY-MM-DD')as dt, max(buy) as buy, max(sale) as sale\n" +
            "FROM onyx.curency_course\n" +
            "WHERE ccy = 'USD'\n" +
            "GROUP BY TO_CHAR(updatetime,'YYYY-MM-DD')\n" +
            "ORDER BY TO_CHAR(updatetime,'YYYY-MM-DD') desc\n" +
            "limit 30";
}
