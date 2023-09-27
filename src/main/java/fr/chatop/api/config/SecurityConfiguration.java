package fr.chatop.api.config;

import fr.chatop.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired private UserRepository userRepo;
	@Autowired private JwtTokenFilter jwtTokenFilter;

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**",
				"/configuration/security", "/swagger-ui.html", "/webjars/**");
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(username -> userRepo.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found.")));
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http.csrf((csrf) -> csrf.disable());
		
		//roads to set free
		http.authorizeHttpRequests((authz) -> authz
			.antMatchers("/auth/login").permitAll()
			.antMatchers("/auth/register").permitAll()
			.antMatchers("/v3/api-docs", "/configuration/ui", "/swagger-resources", "/configuration/security", "/swagger-ui.html","/swagger-ui/**", "/webjars/**","/swagger-resources/configuration/ui","/swagger-ui.html").permitAll()
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
		// @formatter:on
	}
}