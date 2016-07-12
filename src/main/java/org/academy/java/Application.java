package org.academy.java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.Ordered;

import javax.transaction.Transactional;

@SpringBootApplication
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

        @Transactional
        void init();

        @Override
        default int getOrder() {
            return Ordered.LOWEST_PRECEDENCE;
        }
    }


}