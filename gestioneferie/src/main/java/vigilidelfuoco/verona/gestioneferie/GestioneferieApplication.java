package vigilidelfuoco.verona.gestioneferie;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication

public class GestioneferieApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(GestioneferieApplication.class);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(GestioneferieApplication.class, args);
	}

	@Bean
	public CorsFilter corsFilter() {
	    CorsConfiguration corsConfiguration = new CorsConfiguration();
	    corsConfiguration.setAllowedOrigins(Arrays.asList("*"));
	    corsConfiguration.setAllowedHeaders(Arrays.asList(
	        "Origin",
	        "Content-Type",
	        "Accept",
	        "Authorization",
	        "Access-Control-Allow-Origin",
	        "Access-Control-Allow-Credentials"
	    ));
	    corsConfiguration.setExposedHeaders(Arrays.asList(
	        "Origin",
	        "Content-Type",
	        "Accept",
	        "Authorization",
	        "Access-Control-Allow-Origin",
	        "Access-Control-Allow-Credentials"
	    ));
	    corsConfiguration.setAllowedMethods(Arrays.asList(
	        "GET", "POST", "PUT", "DELETE", "OPTIONS"
	    ));

	    UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
	    urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
	    return new CorsFilter(urlBasedCorsConfigurationSource);
	}

}
