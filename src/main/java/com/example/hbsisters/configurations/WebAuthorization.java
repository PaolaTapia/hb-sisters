package com.example.hbsisters.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@EnableWebSecurity
@Configuration
public class WebAuthorization {
    @Bean

    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //solo para pruebas:

                .antMatchers( "/web/index.html","/web/css/**", "/web/img/**","/web/js/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/login").permitAll()
                .antMatchers(HttpMethod.POST,"/api/clients").hasAnyAuthority("ADMIN", "CLIENT")
                .antMatchers(HttpMethod.POST,"/clients/current/cards","/api/**" ).hasAnyAuthority("ADMIN", "CLIENT")
                .antMatchers(HttpMethod.POST, "/api/logout").hasAnyAuthority("ADMIN", "CLIENT")
                .antMatchers("/clients/current/cards","/api/**","/web/*.html", "api/clients/current" ).hasAnyAuthority("ADMIN", "CLIENT")
                .antMatchers( "/h2-console/**","/web/*" ).hasAnyAuthority("ADMIN", "CLIENT")
                .antMatchers("/admin/**", "/**").hasAuthority("ADMIN")
                .anyRequest().denyAll();
       // .anyRequest().authenticated();

        http.formLogin()
                .usernameParameter("email")
                .passwordParameter("password")
                .loginPage("/api/login");

        // turn off checking for CSRF tokens

        http.csrf().disable();


        //disabling frameOptions so h2-console can be accessed, para permitir contenido de 3ros

        http.headers().frameOptions().disable();

        // if user is not authenticated, just send an authentication failure response, 401 si no tenes permiso

        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if login is successful, just clear the flags asking for authentication

        http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

        // if login fails, just send an authentication failure response

        http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if logout is successful, just send a success response

        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());


        http.logout().logoutUrl("/api/logout").deleteCookies("JSESSIONID");

        return http.build();

    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }
}