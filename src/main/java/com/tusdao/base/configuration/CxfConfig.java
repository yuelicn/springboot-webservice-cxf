package com.tusdao.base.configuration;

import com.tusdao.TusdaoWebserviceApplication;
import com.tusdao.webservice.service.WebService;
import com.tusdao.webservice.service.impl.WebServiceImpl;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;


/**
 * @author yueli
 * @date 2019-08-05 19:24
 */
@Configuration
@ComponentScan(basePackageClasses = TusdaoWebserviceApplication.class)
public class CxfConfig {
    @SuppressWarnings("all")
    @Bean(name = "cxfServletRegistration")
    public ServletRegistrationBean dispatcherServlet() {
        //创建服务并指定服务名称
        return new ServletRegistrationBean(new CXFServlet(), "/axis/services/*");
    }

    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        return new SpringBus();
    }


    @Bean
    public WebService webService() {

        return new WebServiceImpl();
    }

    /**
     * 注册WebServiceDemoService接口到webservice服务
     *
     * @return
     */
    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(springBus(), webService());
        endpoint.publish("/bdcgzcxfw_wx");
        endpoint.getInInterceptors().add(new ServerNameSpaceInterceptor());
        //endpoint.getInInterceptors().add(new InInterceptor());
        return endpoint;
    }

}
