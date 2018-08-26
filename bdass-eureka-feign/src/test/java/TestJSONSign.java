import com.netflix.http4.NFHttpClient;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.client.HttpClient;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestJSONSign {

    static Base64.Encoder encoder = Base64.getEncoder();
    static String key = "CzCGbrLRf8RYjT7sPOTFI8tb3D6RFZNrdvbYnRPhCgbCCvh0PtuFt3IErXPpnWSYlKXoJkvIPpUylrWc0k1P3XodgRTEuhJ0kQGB0bp5aOfEMqeZAVVdz2y4EyYCxUQK";
            //"gb4kaqL7e6NzhBruwYgiYIGiHdI3rt3mgua4pMafZJQA7FNnbzhIJwYxYoP36HUI16jOab77SWFUrqXcn486iOktjzd5jS5MF75ECfSUhBqoaucM2GWVCOgwa3lDMyBh";
    static String identifyId = "CMV10999073";
    String apiAddr = "http://pointer.100mj.com/vapi";
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture.allOf(CompletableFuture.runAsync(()->{
            testSetVirtualCode();
            testSetRecoder();
            testCancelOrder();
         }
        )).get();
    }

    /**
     * 重试提交虚拟码
     */
    public static void testRetrySetVirtualCode() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        LinkedMultiValueMap body=new LinkedMultiValueMap();
        body.add("req",getVirtualCode(identifyId));
        HttpEntity entity = new HttpEntity(body,headers);
        String url = "http://222.35.5.7/vapi/service/retry/setVirtualCode";
        RestTemplate template = new RestTemplate();
        for(int i=0;i<500;i++) {
//            System.out.println(i);
                    ResponseEntity re = template.exchange(url, HttpMethod.POST, entity, String.class);
                    System.out.println(re.getBody().toString());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 重试提交消费记录
     */
    public static void testRetrySetRecoder() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        LinkedMultiValueMap body=new LinkedMultiValueMap();
        body.add("req",getRecoderCode(identifyId));
        HttpEntity entity = new HttpEntity(body,headers);
        String url = "http://222.35.5.7/vapi/service/retry/setRecord";
        RestTemplate template = new RestTemplate();
        for(int i=0;i<500;i++) {
//            System.out.println(i);
            ResponseEntity re = template.exchange(url, HttpMethod.POST, entity, String.class);
            System.out.println(re.getBody().toString());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
    /**
     * 撤单
     */
    public static void testCancelOrder() {
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
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        LinkedMultiValueMap body=new LinkedMultiValueMap();
        body.add("req",getReqByJson(json));
        HttpEntity entity = new HttpEntity(body,headers);
        String url = "http://222.35.5.7/vapi/service/cancelOrder";
        RestTemplate template = new RestTemplate();
        ResponseEntity re = template.exchange(url, HttpMethod.POST, entity, String.class);
        System.out.println(re.getBody().toString());
    }

    /**
     * 提交消费记录
     */
    public static void testSetRecoder() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        LinkedMultiValueMap body=new LinkedMultiValueMap();
        body.add("req",getRecoderCode(identifyId));
        HttpEntity entity = new HttpEntity(body,headers);
        String url = "http://222.35.5.7/vapi/service/setRecord";
        RestTemplate template = new RestTemplate();
        ResponseEntity re = template.exchange(url, HttpMethod.POST, entity, String.class);
        System.out.println(re.getBody().toString());

    }
    /**
     * 提交虚拟单号
     */
    public static void testSetVirtualCode() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        LinkedMultiValueMap body=new LinkedMultiValueMap();
        body.add("req",getVirtualCode(identifyId));
        HttpEntity entity = new HttpEntity(body,headers);
        String url = "http://222.35.5.7/vapi/service/setVirtualCode";
        RestTemplate template = new RestTemplate();
        ResponseEntity re = template.exchange(url, HttpMethod.POST, entity, String.class);
        System.out.println(re.getBody().toString());

    }
    /**
     *  获取虚拟码
     * @param identityId 商户编号
     */
    private static String getVirtualCode(String identityId) {
        String json = String.format("{\"version\":\"2.0\",\"identity_id\":\"%s\"," +
                "\"source\":\"1\",\"data\":{\"virtualCodes\":[{\"vcodePass\":" +
                "\"%s_1234522678\",\"vcode\":\"%s_12333345678\"}],\"itemId\":" +
                "\"X17011900041640-01\",\"orderId\":\"V18032816917475\"}}",identityId,identityId,identityId);
       return getReqByJson(json);

    }

    /**
     * 获取消费记录
     * @param identityId 商户编号
     */
    private static String getRecoderCode(String identityId) {
        String json = String.format("{" +
                "\"source\":\"2\"," +
                "\"version\":\"2.0\"," +
                "\"identity_id\":\"%s\"," +
                "\"data\":" +
                "{" +
                "\"recordList\":[" +
                "{" +
                "\"orderId\":\"V18032816917475\"," +
                "\"itemId\":\"X17011900041640-01\"," +
                "\"useId\":\"%s_123454453344\"," +
                "\"virtualCode\":\"%s_123454433678\"," +
                "\"useAmount\":\"5.00\"," +
                "\"useDatetime\":\"2018-04-08 00:09:45\"," +
                "\"useContent\":\"消费内容\"," +
                "\"phone\":\"13700000000\"," +
                "\"virtualCodePass\":\"%s_12345678\"" +
                "}" +
                "]" +
                "}" +
                "}",identityId,identityId,identityId,identityId,identityId,identityId,identityId,identityId,identityId,identityId);
        return getReqByJson(json);

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
