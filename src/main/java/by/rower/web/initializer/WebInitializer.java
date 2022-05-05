package by.rower.web.initializer;


import by.rower.model.config.DatabaseConfig;
import by.rower.web.config.SecurityConfig;
import by.rower.web.config.WebConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    public static final String DISPATCHER_SERVLET_URL_PATTERN = "/";

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{DatabaseConfig.class, SecurityConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{DISPATCHER_SERVLET_URL_PATTERN};
    }
}
