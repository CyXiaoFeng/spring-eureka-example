package service.client.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Component
@Aspect
public class CustomerAop {
    static Logger logger =  LoggerFactory.getLogger(CustomerAop.class);
    @Bean(name = "parameterNameDiscoverer")
    public DefaultParameterNameDiscoverer parameterNameDiscoverer() {
        return new DefaultParameterNameDiscoverer();
    }
    @Before("execution(* service.client.*.*(..))")
    public void before(JoinPoint joinPoint){
        if( RequestContextHolder.getRequestAttributes() != null) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            ServletWebRequest servletWebRequest = new ServletWebRequest(request);
            logger.info("uri={},value={}",
                    request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE),
                    request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE));
            Map<String, String> uriTemplateVars = (Map<String, String>) servletWebRequest.getAttribute(
                    HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE, RequestAttributes.SCOPE_REQUEST);
            logger.info(uriTemplateVars.toString());
        }

        logger.info(" Check for user access ");
        logger.info(" Allowed execution for {}", joinPoint);
    }


}
