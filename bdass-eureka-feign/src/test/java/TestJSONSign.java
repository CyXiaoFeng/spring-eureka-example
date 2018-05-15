import com.google.gson.annotations.Since;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Base64;

public class TestJSONSign {
    static Base64.Encoder encoder = Base64.getEncoder();
    static String key = "NO7UWhCrEf8LHj1QMmuSJ2DMN3uVmowNffIeec3Y9plSZFC1WAuIjoGNY6kJ6M4uMACVVWjKpHVFr3DFeNietpQV3aVZLiGZ9lTmmcPGT8taIrUkgXqEWDQgRQ2J58QD";
    public static void main(String[] args) {
        testSetVirtualCode("CMV10999265");
        testSetRecoder("CMV10999265");
        testCancelOrder("CMV10999265");
    }

    public static void testCancelOrder(String identifyId) {
        String json = String.format("{" +
                "\"version\":\"2.0\"," +
                "\"identity_id\":\"%s\"," +
                "\"source\":\"1\"," +
                "\"data\":" +
                "{" +
                "\"orderId\":\"V16110114687522\"," +
                "\"memo\":\"撤单原因\"," +
                "\"userPhone\":\"13700000000\"" +
                "}" +
                "}",identifyId);
        System.out.println(getReqByJson(json));
    }

    /**
     * 提交消费记录
     * @param identityId 商品编号
     */
    public static void testSetRecoder(String identityId) {
        String json = String.format("{" +
                "\"source\":\"2\"," +
                "\"version\":\"2.0\"," +
                "\"identity_id\":\"%s\"," +
                "\"data\":" +
                "{" +
                "\"recordList\":[" +
                "{" +
                "\"orderId\":\"V16110114687522\"," +
                "\"itemId\":\"X16091900000000-01\"," +
                "\"useId\":\"%s_12345\"," +
                "\"virtualCode\":\"%s_12345678\"," +
                "\"useAmount\":\"5.00\"," +
                "\"useDatetime\":\"2016-11-08 00:09:45\"," +
                "\"useContent\":\"消费内容\"," +
                "\"phone\":\"13700000000\"," +
                "\"virtualCodePass\":\"%s_12345678\"" +
                "}," +
                "{" +
                "\"orderId\":\"V16110114687522\"," +
                "\"itemId\":\"X16091900000000-01\"," +
                "\"useId\":\"%s_56789\"," +
                "\"virtualCode\":\"%s_12345678\"," +
                "\"useAmount\":\"5.00\"," +
                "\"useDatetime\":\"2016-11-08 00:09:45\"," +
                "\"useContent\":\"消费内容\"," +
                "\"phone\":\"13700000000\"," +
                "\"virtualCodePass\":\"%s_12345678\"" +
                "}," +
                "{" +
                "\"orderId\":\"V16110114687523\"," +
                "\"itemId\":\"X16091900000000-01\"," +
                "\"useId\":\"%s_67891\"," +
                "\"virtualCode\":\"%s_12345678\"," +
                "\"useAmount\":\"5.00\"," +
                "\"useDatetime\":\"2016-11-08 00:09:45\"," +
                "\"useContent\":\"消费内容\"," +
                "\"phone\":\"13700000000\"," +
                "\"virtualCodePass\":\"%s_12345678\"" +
                "}" +
                "]" +
                "}" +
                "}",identityId,identityId,identityId,identityId,identityId,identityId,identityId,identityId,identityId,identityId);
        System.out.println(getReqByJson(json));

    }
    /**
     *
     * @param identityId 商户编号
     */
    public static void testSetVirtualCode(String identityId) {
        String json = String.format("{\"version\":\"2.0\",\"identity_id\":\"%s\",\"source\":\"1\",\"data\":{\"virtualCodes\":[{\"vcodePass\":" +
                "\"%s_12345678\",\"vcode\":\"%s_12345678\"}],\"itemId\":" +
                "\"X16110300000000-01\",\"orderId\":\"V16110114687522\"}}",identityId,identityId,identityId);
        System.out.println(getReqByJson(json));

    }

    /**
     *  获得签名
     * @param str
     * @return
     */
    private static String getSign(String str) {
        return DigestUtils.md5Hex(str).toLowerCase();
    }

    /**
     * 字符串排序
     * @param str
     * @return
     */
    private static String sort(String str){
        //利用toCharArray可将字符串转换为char型的数组
        char[] s1 = str.toCharArray();
        for(int i=0;i<s1.length;i++){
            for(int j=0;j<i;j++){
                if(s1[i]<s1[j]){
                    char temp = s1[i];
                    s1[i] = s1[j];
                    s1[j] = temp;
                }
            }
        }
        //再次将字符数组转换为字符串，也可以直接利用String.valueOf(s1)转换
        String st = new String(s1);
        return st;
    }

    private static  String getReqByJson(String json) {
        String req = "";
        String sign = getSign(key + sort(json));
        req = encoder.encodeToString((sign + json).getBytes());
        return req;
    }
}
