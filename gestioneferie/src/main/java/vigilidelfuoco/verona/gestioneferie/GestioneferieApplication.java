package vigilidelfuoco.verona.gestioneferie;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication
@EnableScheduling
public class GestioneferieApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestioneferieApplication.class, args);
	}

    @Bean
    CorsFilter corsFilter() {
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
