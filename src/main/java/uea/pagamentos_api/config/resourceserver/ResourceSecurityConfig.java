package uea.pagamentos_api.config.resourceserver;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
//import org.springframework.security.core.userdetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.util.StringUtils;

import uea.pagamentos_api.config.PagamentosApiProperty;
import uea.pagamentos_api.models.Usuario;
import uea.pagamentos_api.repositories.UsuarioRepository;
import uea.pagamentos_api.security.UserDetailsImpl;

@Profile("oauth-security")
@EnableWebSecurity
@Configuration
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true) //prePostEnabled = true, se true habilita as permissões para os resources 
public class ResourceSecurityConfig {

	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	private PagamentosApiProperty pagamentosApiProperty;
	
	
	@Bean
	public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests()
		.requestMatchers(HttpMethod.GET,"/swagger-ui/**","/oauth2/**","/login","/categorias/**").permitAll()
		.requestMatchers(HttpMethod.POST,"/oauth2/**").permitAll()
		//Essas opeções aqui somente são habilitadas se prePostEnabled = false
		.requestMatchers(HttpMethod.GET,"/pessoas/**").hasAnyRole("PESQUISAR_PESSOA")
		.requestMatchers(HttpMethod.POST,"/pessoas/**").hasAnyRole("CADASTRAR_PESSOA")
		.requestMatchers(HttpMethod.PUT,"/pessoas/**").hasAnyRole("ATUALIZAR_PESSOA")
		.requestMatchers(HttpMethod.DELETE,"/pessoas/**").hasAnyRole("REMOVER_PESSOA")
		
		.requestMatchers(HttpMethod.POST,"/categorias/**").hasAnyRole("CADASTRAR_CATEGORIA")
		.requestMatchers(HttpMethod.PUT,"/categorias/**").hasAnyRole("ATUALIZAR_CATEGORIA")
		.requestMatchers(HttpMethod.DELETE,"/categorias/**").hasAnyRole("REMOVER_CATEGORIA")
						
		.requestMatchers(HttpMethod.GET,"/lancamentos/**").hasAnyAuthority("ROLE_PESQUISAR_LANCAMENTO")
		.requestMatchers(HttpMethod.POST,"/lancamentos/**").hasAnyRole("CADASTRAR_LANCAMENTO")
		.requestMatchers(HttpMethod.PUT,"/lancamentos/**").hasAnyRole("ATUALIZAR_LANCAMENTO")
		.requestMatchers(HttpMethod.DELETE,"/lancamentos/**").hasAnyRole("REMOVER_LANCAMENTO")
		
		.anyRequest().authenticated().and().csrf().disable().oauth2ResourceServer().jwt()
				.jwtAuthenticationConverter(jwtAuthenticationConverter());
		
		http.logout(logoutConfig -> {
			logoutConfig.logoutSuccessHandler((httpServletRequest, httpServletResponse, authentication) -> {
				String returnTo = httpServletRequest.getParameter("returnTo");

				if (!StringUtils.hasText(returnTo)) {
					returnTo = pagamentosApiProperty.getSeguranca().getAuthServerUrl();
				}

				httpServletResponse.setStatus(302);
				httpServletResponse.sendRedirect(returnTo);
			});
		});
		
		return http.formLogin(Customizer.withDefaults()).build();
	}

	private JwtAuthenticationConverter jwtAuthenticationConverter() {
		JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
			List<String> authorities = jwt.getClaimAsStringList("authorities");

			if (authorities == null) {
				authorities = Collections.emptyList();
			}

			JwtGrantedAuthoritiesConverter scopesAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
			Collection<GrantedAuthority> grantedAuthorities = scopesAuthoritiesConverter.convert(jwt);

			grantedAuthorities
					.addAll(authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));

			return grantedAuthorities;
		});

		return jwtAuthenticationConverter;
	}

		
		// Personalizar o mapeador de informações do usuário
		@Bean
		public OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer() {
			return (context) -> {
				if (OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType())) {
					UserDetailsImpl user = (UserDetailsImpl) context.getPrincipal().getPrincipal();
					Usuario usuarioLogado = usuarioRepository.findByEmail(user.getUsername())
							.orElseThrow(()-> new UsernameNotFoundException("Usário " + user.getUsername() + " não encontrado!")); 
					
					Set<String> authorities = new HashSet<String>();
					usuarioLogado.getPermissoes().forEach(p -> 
					authorities.add(p.getDescricao().toUpperCase())); 
					
					context.getClaims().claims((claims) -> {
						claims.put("name", usuarioLogado.getNome());
						claims.put("authorities", authorities);
					});
				}
			};
		}

		@Bean
		SessionRegistry sessionRegistry() {
			return new SessionRegistryImpl();
		}

		@Bean
		HttpSessionEventPublisher httpSessionEventPublisher() {
			return new HttpSessionEventPublisher();
		}

}

