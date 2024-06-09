package aroo.myheart.core.batch;


import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Document(collection = "batchExecutionStatus")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BatchExecutionStatus {
    @Id
    private String batchName;
    private int retryCount;
    private LocalDateTime lastExecutionDate;

    @Builder
    public BatchExecutionStatus(String batchName, LocalDateTime lastExecutionDate) {
        this.batchName = batchName;
        this.lastExecutionDate = lastExecutionDate;
        this.retryCount = 0;
    }

    public void change(LocalDateTime lastExecutionDate) {
        this.retryCount = 0;
        this.lastExecutionDate = lastExecutionDate;
    }

    public void changeFailedInfo() {
        this.retryCount += 1;
    }
}

