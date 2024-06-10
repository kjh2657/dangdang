package aroo.myheart.batch.test.partitioner;

import aroo.myheart.core.constant.CoreConstants;
import aroo.myheart.core.ds.repository.DsMyHeartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static aroo.myheart.core.constant.CoreConstants.BATCH_CHUNK_MAX_SIZE;

@Slf4j
@RequiredArgsConstructor
@Component
public class TestPartitioner implements Partitioner {

    private final DsMyHeartRepository dsMyHeartRepository;

    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        long totalCount = dsMyHeartRepository.count();

        int pageSize = BATCH_CHUNK_MAX_SIZE;
        int totalPageNo = (int) (totalCount / pageSize);

        Map<String, ExecutionContext> result = new HashMap<>();

        for ( int i = 0 ; i<=totalPageNo; i++) {
            ExecutionContext value = new ExecutionContext();

            result.put("partion"+i, value);
            value.putInt("startRow", i * pageSize);
        }

        return result;
    }
}
