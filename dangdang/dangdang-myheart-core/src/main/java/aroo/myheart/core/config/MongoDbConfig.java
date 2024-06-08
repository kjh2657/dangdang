package aroo.myheart.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoAuditing
@EnableMongoRepositories(basePackages = {"aroo.myheart.core.mongo.repository"})
@Configuration
public class MongoDbConfig {
}
