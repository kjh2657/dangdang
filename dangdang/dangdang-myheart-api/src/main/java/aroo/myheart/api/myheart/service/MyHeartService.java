package aroo.myheart.api.myheart.service;

import aroo.myheart.core.ds.domain.DsMyHeart;
import aroo.myheart.core.ds.repository.DsMyHeartRepository;
import aroo.myheart.core.mongo.domain.myheart.MongoMyHeart;
import aroo.myheart.core.mongo.repository.MongoMyHeartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        DsMyHeart build = DsMyHeart.builder()
                .custNo(1L)
                .productCd("1")
                .brandCd("1")
                .contentCd("1")
                .build();

        dsMyHeartRepository.save(build);
    }
}
