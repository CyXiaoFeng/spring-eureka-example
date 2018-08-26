package feign.customer.imp;

import feign.customer.fallBack.FallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@FeignClient(value = "eclient",fallback = FallBack.class)
public interface HomeClient {
    @RequestMapping("/vapi/{apiName}")
    ResponseEntity<Map<String,Object>> vapi(@PathVariable(value="apiName")String apiName);

}
