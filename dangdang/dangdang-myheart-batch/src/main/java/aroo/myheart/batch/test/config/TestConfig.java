package aroo.myheart.batch.test.config;

import aroo.myheart.batch.common.JobDecider;
import aroo.myheart.batch.common.JobListener;
import aroo.myheart.batch.test.partitioner.TestPartitioner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static aroo.myheart.core.constant.CoreConstants.BATCH_CHUNK_MAX_SIZE;

@Slf4j
@RequiredArgsConstructor
@EnableBatchProcessing(dataSourceRef = "batchDataSource", transactionManagerRef = "batchTransactionManager")
@Configuration
public class TestConfig {

    private static final String TEST_ASYNC_JOB = "testAsyncJob";
    private static final String TEST_ASYNC_STEP = "testAsyncStep";

    @Bean(TEST_ASYNC_JOB)
    public Job testAsyncJob(JobRepository jobRepository, JobListener listener, JobDecider jobDecider, @Qualifier(TEST_ASYNC_STEP) Step testAsyncStep){
        log.info("===== migrationProductJob =====");
        return new JobBuilder(TEST_ASYNC_JOB, jobRepository)
                .listener(listener)
                .start(jobDecider).on(ExitStatus.COMPLETED.getExitCode()).to(testAsyncStep).end()
                .build();
    }



    @Bean(name = TEST_ASYNC_STEP)
    @JobScope
    public Step testAsyncStep(JobRepository jobRepository, PlatformTransactionManager transactionManager, @Qualifier("batchTaskExecutor") TaskExecutor batchTaskExecutor, TestPartitioner testPartitioner) {
        return new StepBuilder(TEST_ASYNC_STEP, jobRepository)
                .partitioner(TEST_ASYNC_STEP + "_slaveStep", testPartitioner)
                .step(slaveStep(jobRepository, transactionManager))
                .taskExecutor(batchTaskExecutor)
                .build();
    }

    @Bean(name = TEST_ASYNC_STEP + "_slaveStep")
    public Step slaveStep(JobRepository jobRepository,
                          PlatformTransactionManager transactionManager) {
        return new StepBuilder(TEST_ASYNC_STEP, jobRepository)
                .<String, String>chunk(BATCH_CHUNK_MAX_SIZE, transactionManager)
                .reader(itemReader(null))
                .writer(itemWriter())
                .build();
    }


    @Bean(name = TEST_ASYNC_STEP + "_reader")
    @StepScope
    JpaPagingItemReader itemReader(@Value("#{stepExecutionContext[startRow]}") Long startRow) {
        /*MyBatisCursorItemReader<WckDbProductMigrationDto> databaseReader = new MyBatisCursorItemReader<>();
        databaseReader.setSqlSessionFactory(sqlSessionFactory);
        Map<String, Object> param = new HashMap<>();
        param.put("startRow", startRow);
        param.put("endRow", startRow + BATCH_CHUNK_MAX_SIZE);
        databaseReader.setQueryId("com.wconcept.myheart.core.wckdb.WckDbMigrationDao.getProductMigrationListMsIdxRange");
        databaseReader.setParameterValues(param);*/
        System.out.println("startRow = " + startRow);
        return null;
    }


    @Bean(name = TEST_ASYNC_STEP + "_writer")
    @StepScope
    public ItemWriter<String> itemWriter() {
        return null;
        /*return items -> {
            List<Long> migIdxList = items.getItems().stream().map(WckDbProductMigrationDto::getMsIdx).toList();
            List<String> productList = items.getItems().stream().map(WckDbProductMigrationDto::getItemCd).toList();
            List<MyHeartInfo> migratedProduct = myHeartService.getMigratedMyHeart(migIdxList, HeartType.PRODUCT);
            Map<Long, MyHeartInfo> migratedProductMap = migratedProduct.stream().collect(Collectors.toMap(MyHeartInfo::getMigrationIdx, Function.identity()));
            List<MyHeartCommand.RegisterHeart> toSaveList = new ArrayList<>();
            items.getItems().stream()
                    .forEach(s -> {
                        if (!migratedProductMap.containsKey(s.getMsIdx())) {
                            MyHeartCommand.RegisterHeart product = MyHeartCommand.RegisterHeart.builder()
                                    .custNo(s.getCustno())
                                    .heartType(HeartType.PRODUCT)
                                    .migrationIdx(s.getMsIdx())
                                    .product(batchObjectMapper.toRegisterProductHeart(s))
                                    .build();
                            toSaveList.add(product);
                        }
                    });


            myHeartService.migrateMyHeart(toSaveList);
            productService.updateValidProduct(productList);

        };*/
    }
}