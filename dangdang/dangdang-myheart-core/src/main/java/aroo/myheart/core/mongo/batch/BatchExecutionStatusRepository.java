package aroo.myheart.core.mongo.batch;

import aroo.myheart.core.batch.BatchExecutionStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BatchExecutionStatusRepository extends MongoRepository<BatchExecutionStatus, String> {

    Optional<BatchExecutionStatus> findByBatchName(String batchName);
}

