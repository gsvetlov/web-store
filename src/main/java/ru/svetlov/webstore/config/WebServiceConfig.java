package ru.svetlov.webstore.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@Configuration
@EnableWs
public class WebServiceConfig extends WsConfigurerAdapter {
    private static final String WS_LOCATION_URI = "/api/v1/ws";

    @Bean
    public ServletRegistrationBean<?> messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, WS_LOCATION_URI + "/*");
    }

    @Bean
    public DefaultWsdl11Definition productWsdl11Definition(XsdSchema productsSchema) {
        DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
        definition.setPortTypeName("Products");
        definition.setLocationUri(WS_LOCATION_URI);
        definition.setTargetNamespace("http://webstore.svetlov.ru/ws/products");
        definition.setSchema(productsSchema);
        return definition;
    }

    @Bean
    public XsdSchema productSchema(){
        return new SimpleXsdSchema(new ClassPathResource("xsd/products.xsd"));
    }
}
