package bdass.feign.customer.fallBack;

import bdass.feign.customer.imp.HomeClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;
import java.util.Map;

@Component
public class FallBack implements HomeClient {
    public ResponseEntity<Map<String, Object>> vapi(String apiName) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("code",0);
        map.put("message","sucess");
        map.put("data","clientBreak");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<Map<String,Object>>(map,headers, HttpStatus.OK);
    }
}
