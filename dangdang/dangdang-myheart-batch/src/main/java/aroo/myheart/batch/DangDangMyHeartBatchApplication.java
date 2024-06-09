package aroo.myheart.batch;

import aroo.myheart.core.config.YamlPropertySourceFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@PropertySource(ignoreResourceNotFound = true,
        value = {
                "classpath:application-core.yml",
                "classpath:application-core-${spring.profiles.active}.yml"
        }, factory = YamlPropertySourceFactory.class)
@ComponentScan({"aroo.myheart.batch", "aroo.myheart.core"})
@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class
})
public class DangDangMyHeartBatchApplication {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(DangDangMyHeartBatchApplication.class, args);
        System.exit(SpringApplication.exit(context));
    }
}

