package br.com.mvc.mudi;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private DataSource dataSource;
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
		.antMatchers("/cadastro/**")
		.permitAll()
		.antMatchers("/home/**")
			.permitAll()
			.anyRequest()
			.authenticated()
		.and()
			.formLogin(form -> form
					.loginPage("/login")
					.defaultSuccessUrl("/usuario/pedido", true)
					.permitAll()
					)
			.logout(logout -> {
				logout.logoutUrl("/logout")
					.logoutSuccessUrl("/home");
			})
			.csrf().disable();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		auth.jdbcAuthentication()
			.dataSource(dataSource)
			.passwordEncoder(encoder);
	}
	
	
	@Bean
	@Override
	protected UserDetailsService userDetailsService() {
		UserDetails user = 
				User.withDefaultPasswordEncoder()
				.username("joao")
				.password("123")
				.roles("USER")
				.build();
		
		return new InMemoryUserDetailsManager(user);
	}
}
