package com.utn.TPfinal;

import com.utn.TPfinal.filter.JWTAuthorizationFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@SpringBootApplication
public class TPfinalApplication {

	public static void main(String[] args) {
		SpringApplication.run(TPfinalApplication.class, args);
	}


	/* para decir a mi app que todos los request necesitan verificar la autorizacion, y autenticacion.
	 *    atravez de los filtors que estan en JWTAothorizationFilter. Excet el endopoint de /login, el cual entrara
	 *     sin autorizacion*/
	@EnableWebSecurity
	@Configuration
	/*para habilitar las anotaciones previas y posteriores en Spring Security*/
	@EnableGlobalMethodSecurity(prePostEnabled = true)
	class WebSecurityConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable()
					.addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
					.authorizeRequests()
					.antMatchers(HttpMethod.POST, "/login").permitAll()
					.anyRequest().authenticated();
		}
	}
}
