package uea.pagamentos_api.config.basicsecurity;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Profile("basic-security")
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = false, securedEnabled = true, jsr250Enabled = true)
public class BasicSecurityConfig {
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http.cors().and().csrf().disable().authorizeHttpRequests(
				(authorize) -> authorize
				.requestMatchers(HttpMethod.GET,"/swagger-ui/**","/login","/categorias/**").permitAll()
				
				.requestMatchers(HttpMethod.GET,"/pessoas/**").hasAnyRole("PESQUISAR_PESSOA")
				.requestMatchers(HttpMethod.POST,"/pessoas/**").hasAnyRole("CADASTRAR_PESSOA")
				.requestMatchers(HttpMethod.PUT,"/pessoas/**").hasAnyRole("ATUALIZAR_PESSOA")
				.requestMatchers(HttpMethod.DELETE,"/pessoas/**").hasAnyRole("REMOVER_PESSOA")
				
				.requestMatchers(HttpMethod.POST,"/categorias/**").hasAnyRole("CADASTRAR_CATEGORIA")
				.requestMatchers(HttpMethod.PUT,"/categorias/**").hasAnyRole("ATUALIZAR_CATEGORIA")
				.requestMatchers(HttpMethod.DELETE,"/categorias/**").hasAnyRole("REMOVER_CATEGORIA")
								
				.requestMatchers(HttpMethod.GET,"/lancamentos/**").hasAnyRole("PESQUISAR_LANCAMENTO")
				.requestMatchers(HttpMethod.POST,"/lancamentos/**").hasAnyRole("CADASTRAR_LANCAMENTO")
				.requestMatchers(HttpMethod.PUT,"/lancamentos/**").hasAnyRole("ATUALIZAR_LANCAMENTO")
				.requestMatchers(HttpMethod.DELETE,"/lancamentos/**").hasAnyRole("REMOVER_LANCAMENTO")
				
				.anyRequest()				
				.authenticated())
		.httpBasic(withDefaults())
		.formLogin(withDefaults());
		return http.build();
	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
	    CorsConfiguration configuration = new CorsConfiguration();
	    configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
	    configuration.setAllowedMethods(Arrays.asList("*"));
	    configuration.setAllowedHeaders(Arrays.asList("*"));
	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", configuration);
	    return source;
	}
	
}