package bdass.service.regisger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
    @EnableEurekaServer
    public class EurekaServerApplication {

        public static void main(String[] args) {
            SpringApplication.run(EurekaServerApplication.class, args);
        }

}
@RestController
class ServiceInstanceRestController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping("/service-instances/{applicationName}")
    public List<ServiceInstance> serviceInstancesByApplicationName(
            @PathVariable String applicationName) {
        return this.discoveryClient.getInstances(applicationName);
    }

    @RequestMapping("/vapi/{apiName}")
    public ResponseEntity<Map<String,Object>> vapiName(@PathVariable String apiName) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", 0);
        map.put("message", "sucess");
        map.put("data", apiName);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<Map<String,Object>>(map,headers,HttpStatus.OK);
    }

    @RequestMapping("/vapi")
    public ResponseEntity<Map<String,Object>> vapiPath() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", 0);
        map.put("message", "sucess");
        map.put("data", null);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<Map<String,Object>>(map,headers,HttpStatus.OK);
    }
}