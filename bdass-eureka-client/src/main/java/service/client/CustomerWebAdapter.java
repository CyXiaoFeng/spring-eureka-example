package service.client;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;

@Configuration
public class CustomerWebAdapter implements WebMvcConfigurer {
    static Logger logger =  LoggerFactory.getLogger(CustomerWebAdapter.class);
    @Resource
    DefaultParameterNameDiscoverer parameterNameDiscoverer;
    public void addInterceptors(InterceptorRegistry registry) {
        logger.info("enter the interception={}","addInterceptors");
        registry.addInterceptor(new HandlerInterceptorAdapter() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                logger.info("uri={},value={}",
                        request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE),
                        request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE));
                if(handler instanceof HandlerMethod) {
                    String[] paramters = parameterNameDiscoverer.getParameterNames(((HandlerMethod) handler).getMethod());

                    Method method = ((HandlerMethod) handler).getMethod();
                    logger.info("method name={}",method.getName());
                    Annotation[] annotations = method.getAnnotations();
                    for(Annotation annotation:annotations) {
                        logger.info("value={},path={},name={}",((RequestMapping) annotation).value(),((RequestMapping) annotation).path(),((RequestMapping) annotation).name());
                    }
                    getMethodParameters(((HandlerMethod) handler).getMethodParameters());
                }
                ServletWebRequest servletWebRequest=new ServletWebRequest(request);
                Map<String, String> uriTemplateVars = (Map<String, String>) servletWebRequest.getAttribute(
                        HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE, RequestAttributes.SCOPE_REQUEST);
                logger.info(uriTemplateVars.toString());
                return true;
            }

            @Override
            public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
                super.postHandle(request, response, handler, modelAndView);
            }

            @Override
            public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
                super.afterCompletion(request, response, handler, ex);
            }

            @Override
            public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                super.afterConcurrentHandlingStarted(request, response, handler);
            }
        });
    }

    private void getMethodParameters(MethodParameter[] methodParameters) {
        for(MethodParameter parameter:methodParameters) {
            for(Annotation annotation:parameter.getParameterAnnotations()) {
                if(annotation instanceof PathVariable) {
                    logger.info("parameter name = {}, value={},name={}",parameter.getParameterName(),
                            ((PathVariable) annotation).value(),((PathVariable) annotation).name());
                }

            }
        }
    }

}
