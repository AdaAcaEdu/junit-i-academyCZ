package org.academy.java;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.transaction.Transactional;

import static org.springframework.boot.autoconfigure.security.SecurityProperties.ACCESS_OVERRIDE_ORDER;

@SpringBootApplication
@Import(SecurityConfiguration.class)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    public static final class SpringProfiles {
        public static final String TEST = "test";
        public static final String TEST_REST = "test_rest";
        public static final String PROD = "prod";
        public static final String PROD_GEO = "prod_geo";
        public static final String TEST_DATA_1 = "test_data_1";
    }

    public interface DbInitializer extends Ordered {

        @Transactional void init();

        @Override
        default int getOrder() {
            return Ordered.LOWEST_PRECEDENCE;
        }
    }
}


@Order(ACCESS_OVERRIDE_ORDER)
@Profile("PROD")
class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {  //maybe show the one with UserDetailsService and password hash
        auth.inMemoryAuthentication()
                .withUser("test").password("test").roles("USER")
                .and()
                .withUser("admin").password("admin").roles("USER", "ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers("/index.html", "/lib/**", "/user", "/").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable();
    }
}