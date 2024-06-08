package aroo;

import aroo.myheart.core.config.YamlPropertySourceFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(ignoreResourceNotFound = true,
        value = {
                "classpath:application-core.yml",
                "classpath:application-core-${spring.profiles.active}.yml"
        }, factory = YamlPropertySourceFactory.class)
@SpringBootApplication
public class DangDangMyHeartCoreApplication {
    public static void main(String[] args) {
        SpringApplication.run(DangDangMyHeartCoreApplication.class, args);
    }
}

