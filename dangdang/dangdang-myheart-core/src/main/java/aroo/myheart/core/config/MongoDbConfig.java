package aroo.myheart.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoAuditing
@EnableMongoRepositories(basePackages = {"aroo.myheart.core.mongo"})
@Configuration
public class MongoDbConfig {
}
