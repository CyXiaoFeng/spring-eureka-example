package feign.customer;

import feign.customer.imp.HomeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@RestController
public class EurekaFeignApplication {

    @Autowired
    private HomeClient homeClient;

    public static void main(String[] args) {
        SpringApplication.run(EurekaFeignApplication.class, args);
    }

    @RequestMapping("/vapi/{apiName}")
    public ResponseEntity<Map<String,Object>> invokeInterface(@PathVariable(value="apiName") String apiName) {
        return  homeClient.vapi(apiName);
    }


}



