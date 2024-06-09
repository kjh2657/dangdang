package aroo.myheart.api.myheart.service;

import aroo.myheart.core.ds.domain.DsMyHeart;
import aroo.myheart.core.ds.repository.DsMyHeartRepository;
import aroo.myheart.core.mongo.domain.myheart.MongoMyHeart;
import aroo.myheart.core.mongo.repository.MongoMyHeartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class MyHeartService {

    private final MongoMyHeartRepository mongoMyHeartRepository;
    private final DsMyHeartRepository dsMyHeartRepository;

    public void test() {
        MongoMyHeart build = MongoMyHeart.builder()
                .custNo(1L)
                .build();

        mongoMyHeartRepository.save(build);

    }

    public void test2() {

        Random random = new Random(System.currentTimeMillis());

        for(int i = 0; i < 100000; i++) {
            Long ranNum = random.nextLong(10000000);

            DsMyHeart build = DsMyHeart.builder()
                    .custNo(ranNum)
                    .productCd(Long.toString(ranNum))
                    .brandCd(Long.toString(ranNum))
                    .contentCd(Long.toString(ranNum))
                    .build();
            dsMyHeartRepository.save(build);
        }

    }
}
