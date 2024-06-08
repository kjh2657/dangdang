package aroo.myheart.core.mongo.repository;

import aroo.myheart.core.mongo.domain.myheart.MongoMyHeart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoMyHeartRepository extends MongoRepository<MongoMyHeart, String> {
}
