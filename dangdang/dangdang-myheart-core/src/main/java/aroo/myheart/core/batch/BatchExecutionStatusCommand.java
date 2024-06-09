package aroo.myheart.core.batch;


import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

public class BatchExecutionStatusCommand {

    @Getter
    @Builder
    @ToString
    public static class Register {
        private final String batchName;
        private final LocalDateTime lastExecutionDate;
        private final int retryCount;

        public BatchExecutionStatus toEntity() {
            return BatchExecutionStatus.builder()
                    .batchName(this.batchName)
                    .lastExecutionDate(this.lastExecutionDate)
                    .build();
        }
    }
}