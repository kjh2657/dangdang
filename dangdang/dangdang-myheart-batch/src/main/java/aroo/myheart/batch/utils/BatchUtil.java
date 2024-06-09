package aroo.myheart.batch.utils;


import aroo.myheart.core.batch.BatchExecutionStatus;
import aroo.myheart.core.batch.BatchExecutionStatusCommand;
import aroo.myheart.core.batch.BatchExecutionStatusInfo;
import aroo.myheart.core.exception.EntityNotFoundException;
import aroo.myheart.core.mongo.batch.BatchExecutionStatusRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@AllArgsConstructor
@Slf4j
@Component
public class BatchUtil {
    private final BatchExecutionStatusRepository batchExecutionStatusRepository;
    public static final int MAX_ATTEMPTS = 3;
    public static final String BATCH_START_DATETIME = "__BATCH_START_DATE_TIME";
    public static final String BATCH_END_DATETIME = "__BATCH_END_DATE_TIME";

    /**
     * 배치정보를 검색합니다.
     *
     * @param jobName JobName
     * @return 배치 정보
     */
    @Transactional(readOnly = true)
    public BatchExecutionStatusInfo getBatchInfo(String jobName) {
        BatchExecutionStatus entity = batchExecutionStatusRepository.findByBatchName(jobName).orElse(null);

        if (entity != null)
            return new BatchExecutionStatusInfo(entity);

        return null;
    }

    /**
     * 배치를 초기화합니다.
     *
     * @param jobName               JobName
     * @param lastExecutionDateTime 최초 등록 수행시간
     */
    @Transactional
    public void initBatchInfo(String jobName, LocalDateTime lastExecutionDateTime) {
        BatchExecutionStatusCommand.Register command = BatchExecutionStatusCommand.Register
                .builder()
                .batchName(jobName)
                .lastExecutionDate(lastExecutionDateTime)
                .retryCount(0)
                .build();
        batchExecutionStatusRepository.save(command.toEntity());
    }

    /**
     * 배치 처리 완료에 대한 정보를 DB에 기록합니다.
     *
     * @param jobName               JobName
     * @param lastExecutionDateTime
     */
    @Transactional
    public void success(String jobName, LocalDateTime lastExecutionDateTime) {
        BatchExecutionStatus entity = batchExecutionStatusRepository.findByBatchName(jobName).orElseThrow(EntityNotFoundException::new);
        entity.change(lastExecutionDateTime);
    }

    /**
     * 배치 실패 정보를 기록합니다.
     *
     * @param jobName JobName
     */
    @Transactional
    public void fail(String jobName) {
        BatchExecutionStatus entity = batchExecutionStatusRepository.findByBatchName(jobName).orElseThrow(EntityNotFoundException::new);

        if (entity.getRetryCount() < MAX_ATTEMPTS)
            entity.changeFailedInfo();
    }

    /**
     * 수행중인 Job 이름을 가져옵니다.
     *
     * @param contribution StepContribution
     * @return JobName
     */
    public String getJobName(StepContribution contribution) {
        return getJobName(contribution.getStepExecution().getJobExecution());
    }

    /**
     * 수행중인 Job 이름을 가져옵니다.
     *
     * @param jobExecution JobExecution
     * @return JobName
     */
    public String getJobName(JobExecution jobExecution) {
        return jobExecution.getJobInstance().getJobName();
    }

    /**
     * 배치 StartDateTime을 가져옵니다.
     *
     * @param contribution StepContribution
     * @return 마지막 성공 배치 시간
     */
    public LocalDateTime getBatchStartDateTime(StepContribution contribution) {
        return getBatchStartDateTime(contribution.getStepExecution().getJobExecution().getExecutionContext());
    }

    /**
     * 배치 StartDateTime을 가져옵니다.
     *
     * @param jobExecution JobExecution
     * @return 마지막 성공 배치 시간
     */
    public LocalDateTime getBatchStartDateTime(JobExecution jobExecution) {
        return getBatchStartDateTime(jobExecution.getExecutionContext());
    }

    /**
     * 배치 StartDateTime을 가져옵니다.
     *
     * @param context ExecutionContext
     * @return 마지막 성공 배치 시간
     */
    public LocalDateTime getBatchStartDateTime(ExecutionContext context) {
        return context.get(BATCH_START_DATETIME, LocalDateTime.class);
    }

    /**
     * 배치 EndDateTime을 가져옵니다.
     *
     * @param contribution StepContribution
     * @return 마지막 성공 배치 시간
     */
    public LocalDateTime getBatchEndDateTime(StepContribution contribution) {
        return getBatchEndDateTime(contribution.getStepExecution().getJobExecution().getExecutionContext());
    }

    /**
     * 배치 EndDateTime을 가져옵니다.
     *
     * @param jobExecution JobExecution
     * @return 마지막 성공 배치 시간
     */
    public LocalDateTime getBatchEndDateTime(JobExecution jobExecution) {
        return getBatchEndDateTime(jobExecution.getExecutionContext());
    }

    /**
     * 배치 EndDateTime을 가져옵니다.
     *
     * @param context ExecutionContext
     * @return 마지막 성공 배치 시간
     */
    public LocalDateTime getBatchEndDateTime(ExecutionContext context) {
        return context.get(BATCH_END_DATETIME, LocalDateTime.class);
    }
}