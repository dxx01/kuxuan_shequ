package life.kuxuanzhuzhu.kuxuan_shequ.tool;

/**
 * @author 邓鑫鑫
 * @date 2019年09月29日 14:36:08
 * @Description redis的key工具类
 */
public class RedisKeyUtil {


    public static String getKey(String tableName,String majorKey,String majorKeyValue,String column){
        StringBuffer buffer = new StringBuffer();
        buffer.append(tableName).append(":");
        buffer.append(majorKey).append(":");
        buffer.append(majorKeyValue).append(":");
        buffer.append(column);
        return buffer.toString();
    }
}
