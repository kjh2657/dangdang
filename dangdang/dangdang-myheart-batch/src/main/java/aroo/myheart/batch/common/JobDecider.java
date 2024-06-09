package aroo.myheart.batch.common;


import aroo.myheart.batch.utils.BatchUtil;
import aroo.myheart.core.batch.BatchExecutionStatusInfo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@AllArgsConstructor
@Component
@Slf4j
public class JobDecider implements JobExecutionDecider {

    private final BatchUtil batchUtil;

    @Override
    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
        String jobName = batchUtil.getJobName(jobExecution);

        // Job 수행가능여부 체크 (최대 재시도 횟수 초과 등)
        log.debug("***************************");
        log.debug("jobName : {}", jobName);
        log.debug("***************************");

        BatchExecutionStatusInfo info = batchUtil.getBatchInfo(jobName);
        LocalDateTime now = LocalDateTime.now().withNano(0);
        if (info == null) {
            // 최초시 배치 수행정보 초기화
            batchUtil.initBatchInfo(jobName, now);
            return new FlowExecutionStatus("최초 배치에 대한 초기화를 완료하였습니다. 다음 배치부터 수행됩니다.");
        } else {
            // Tasklet 에서 참조할 배치 처리 범위에 대한 시간정보 셋팅 (직전 배치 처리시간 ~ 현재 시간)
            jobExecution.getExecutionContext().put(BatchUtil.BATCH_START_DATETIME, info.getLastExecutionDate());
            jobExecution.getExecutionContext().put(BatchUtil.BATCH_END_DATETIME, now);

            // 기존 수행한 내역이 있을 경우 재시도 횟수 체크
            if (info.getRetryCount() >= BatchUtil.MAX_ATTEMPTS)
                return new FlowExecutionStatus("허용된 재시도 횟수를 초과하였습니다. 실패원인 해결 후 재시도 횟수를 초기화 해주시기 바랍니다.");
        }

        return new FlowExecutionStatus(ExitStatus.COMPLETED.getExitCode());
    }
}

