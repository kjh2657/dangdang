package aroo.myheart;

import aroo.myheart.core.config.YamlPropertySourceFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@PropertySource(
        value = {
                "classpath:application-core.yml",
                "classpath:application-core-${spring.profiles.active}.yml"
        }, factory = YamlPropertySourceFactory.class)
@ComponentScan({"aroo.myheart.core", "aroo.myheart.api"})
@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class
})
public class DangDangMyHeartApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(DangDangMyHeartApiApplication.class, args);
    }

}