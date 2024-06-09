package aroo.myheart.core.batch;


import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BatchExecutionStatusInfo {
    private final String batchName;
    private final LocalDateTime lastExecutionDate;
    private final int retryCount;

    public BatchExecutionStatusInfo(BatchExecutionStatus data) {
        this.batchName = data.getBatchName();
        this.lastExecutionDate = data.getLastExecutionDate();
        this.retryCount = data.getRetryCount();
    }
}

