package org.academy.java;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.Collections;
import java.util.Map;

import static org.springframework.boot.autoconfigure.security.SecurityProperties.*;

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