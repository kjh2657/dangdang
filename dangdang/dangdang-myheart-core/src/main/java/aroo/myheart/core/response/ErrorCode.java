package aroo.myheart.core.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    COMMON_SYSTEM_ERROR(20000, "일시적인 오류가 발생했습니다. 잠시 후 다시 시도해주세요."),
    COMMON_INVALID_PARAMETER(20001, "요청한 값이 올바르지 않습니다."),
    COMMON_FILE_MAX_SIZE_ERROR(20304, "파일 사이즈 10MB를 초과하였습니다."),
    COMMON_ENTITY_NOT_FOUND(20305, "Entity no fioud")
    ;

    private final int errorCode;
    private final String errorMsg;

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg(Object... arg) {
        return String.format(errorMsg, arg);
    }
}
