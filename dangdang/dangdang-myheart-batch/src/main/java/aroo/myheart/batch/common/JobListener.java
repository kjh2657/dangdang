package aroo.myheart.batch.common;


import aroo.myheart.batch.utils.BatchUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@AllArgsConstructor
public class JobListener implements JobExecutionListener {

    private final BatchUtil batchUtil;

    @Override
    public void afterJob(JobExecution jobExecution) {
        String jobName = batchUtil.getJobName(jobExecution);
        LocalDateTime endDateTime = batchUtil.getBatchEndDateTime(jobExecution);

        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            batchUtil.success(jobName, endDateTime);
            log.debug("Job COMPLETED");
        } else if (jobExecution.getStatus() == BatchStatus.FAILED) {
            batchUtil.fail(jobName);
            log.debug("Job FAILED = ###{}###", jobExecution.getJobInstance().getJobName());
        }
    }
}