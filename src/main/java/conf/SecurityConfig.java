package conf;

import conf.security.handlers.ApiLogoutSuccessHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();

        http.authorizeRequests()
                .antMatchers("/api/logout").permitAll()
                .antMatchers("/api/login").permitAll()
                .antMatchers(HttpMethod.GET, "/api/books").permitAll()
                .antMatchers(HttpMethod.GET,"/api/books/**").hasRole("EMPLOYEE")
                .antMatchers("/api/users").hasRole("EMPLOYEE")
                .antMatchers(HttpMethod.POST, "/api/books").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/books/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PATCH, "/api/books/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/books/**").hasRole("ADMIN")
                .antMatchers("/api/**").authenticated();

        http.formLogin();
        http.logout().logoutUrl("/api/logout");
        http.logout().logoutSuccessHandler(new ApiLogoutSuccessHandler());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {

        builder.inMemoryAuthentication()
                .passwordEncoder(new BCryptPasswordEncoder())
                .withUser("user")
                .password("$2a$10$61Z3nafWrPSIcwiP7f62Gurt5Q5OnW6efRhbVGs6aiLcbJ0Yqqlaa")
                .roles("USER")
                .and()
                .withUser("employee")
                .password("$2a$10$61Z3nafWrPSIcwiP7f62Gurt5Q5OnW6efRhbVGs6aiLcbJ0Yqqlaa")
                .roles("USER", "EMPLOYEE")
                .and()
                .withUser("admin")
                .password("$2a$10$61Z3nafWrPSIcwiP7f62Gurt5Q5OnW6efRhbVGs6aiLcbJ0Yqqlaa")
                .roles("USER", "EMPLOYEE", "ADMIN");
    }
}
