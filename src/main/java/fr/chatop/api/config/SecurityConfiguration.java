package fr.chatop.api.config;

import fr.chatop.api.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	@Autowired private UserRepository userRepo;
	@Autowired private JwtTokenFilter jwtTokenFilter;

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web)-> web.ignoring().requestMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**",
				"/configuration/security", "/swagger-ui.html", "/webjars/**");
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(username -> userRepo.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found.")));
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// @formatter:off
		http.csrf(AbstractHttpConfigurer::disable);
		
		//roads to set free
		http.authorizeHttpRequests((authz) -> authz
			.requestMatchers("/auth/login").permitAll()
			.requestMatchers("/auth/register").permitAll()
			.requestMatchers("/v3/api-docs", "/configuration/ui", "/swagger-resources", "/configuration/security", "/swagger-ui.html","/swagger-ui/**", "/webjars/**","/swagger-resources/configuration/ui","/swagger-ui.html").permitAll()
			.anyRequest().authenticated());
		
		http.sessionManagement((session)->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		//JwtFilters
		http.exceptionHandling((exception) -> exception
        .authenticationEntryPoint(
            (request, response, ex) -> response.sendError(
				HttpServletResponse.SC_UNAUTHORIZED,
				ex.getMessage()
			)
        ));
 
		http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
		// @formatter:on
	}
}