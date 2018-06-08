package uy.edu.ucu.si.grupo8.obligatorio3.configurations;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration

public class CORSConfig implements WebMvcConfigurer {

    @Value("${uy.edu.ucu.si.cors.allowedorigins}")
    private String[] allowedOrigins;

    @Value("${uy.edu.ucu.si.cors.allowedmethods}")
    private String[] allowedMethods;

    @Value("${uy.edu.ucu.si.cors.urlmappings}")
    private String urlMappings;

    @Value("${uy.edu.ucu.si.cors.maxage}")
    private long maxAge;


    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping(urlMappings)
                .allowedMethods(allowedMethods)
                .allowedOrigins(allowedOrigins)
                .maxAge(maxAge);
    }
}