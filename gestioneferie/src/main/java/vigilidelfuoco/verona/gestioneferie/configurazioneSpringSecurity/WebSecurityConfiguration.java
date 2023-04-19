package vigilidelfuoco.verona.gestioneferie.configurazioneSpringSecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfiguration  {

	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
	@Bean
	AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailsService userDetailsService) 
	  throws Exception {
	    return http.getSharedObject(AuthenticationManagerBuilder.class)
	      .userDetailsService(userDetailsService)
	      .passwordEncoder(bCryptPasswordEncoder)
	      .and()
	      .build();
	}
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
        .csrf().disable()
				/*
				 * .formLogin(form -> form .loginPage("/login") .permitAll())
				 */
                .authorizeHttpRequests()
				/* .requestMatchers("/login").permitAll() */
				.requestMatchers("/").permitAll() 
                .requestMatchers("/utente/all").permitAll()

                .requestMatchers("/home").hasAuthority("UTENTE")
                .requestMatchers("/admin").hasAuthority("ADMIN")
				.anyRequest().authenticated() 
                .and().httpBasic(withDefaults());
        
        
        
        return http.build();
		}
	
	

}
