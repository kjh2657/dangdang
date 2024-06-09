package aroo.myheart.core.response;

import aroo.myheart.core.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class CommonControllerAdvice {

    private static final List<ErrorCode> SPECIFIC_ALERT_TARGET_ERROR_CODE_LIST = new ArrayList<>();

    /**
     * http status: 500 AND result: FAIL
     * 시스템 예외 상황. 집중 모니터링 대상
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    public CommonResponse onException(Exception e) {
        e.printStackTrace();
        log.error("[onException] cause = {}, errorMsg = {}", NestedExceptionUtils.getMostSpecificCause(e), NestedExceptionUtils.getMostSpecificCause(e).getMessage());
        return CommonResponse.fail(ErrorCode.COMMON_SYSTEM_ERROR);
    }

    /**
     * http status: 200 AND result: FAIL
     * 시스템은 이슈 없고, 비즈니스 로직 처리에서 에러가 발생함
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = BaseException.class)
    public CommonResponse onBaseException(BaseException e) {
        if (SPECIFIC_ALERT_TARGET_ERROR_CODE_LIST.contains(e.getErrorCode())) {
            log.error("[BaseException] cause = {}, errorMsg = {}", NestedExceptionUtils.getMostSpecificCause(e), NestedExceptionUtils.getMostSpecificCause(e).getMessage());
        } else {
            log.warn("[BaseException] cause = {}, errorMsg = {}", NestedExceptionUtils.getMostSpecificCause(e), NestedExceptionUtils.getMostSpecificCause(e).getMessage());
        }
        return CommonResponse.fail(e.getErrorCode().getErrorCode(), e.getMessage());
    }

    /**
     * http status: 200 AND result: FAIL
     * request parameter 에러
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public CommonResponse methodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.warn("[MethodArgumentNotValidException] errorMsg = {}", NestedExceptionUtils.getMostSpecificCause(e).getMessage());
        BindingResult bindingResult = e.getBindingResult();
        FieldError fe = bindingResult.getFieldError();
        if (fe != null) {
            String message = "Request Error" + " " + fe.getField() + "=" + fe.getRejectedValue() + " (" + fe.getDefaultMessage() + ")";
            return CommonResponse.fail(ErrorCode.COMMON_INVALID_PARAMETER.getErrorCode(), message);
        } else {
            return CommonResponse.fail(ErrorCode.COMMON_INVALID_PARAMETER.getErrorCode(), ErrorCode.COMMON_INVALID_PARAMETER.getErrorMsg());
        }
    }

    /**
     * http status: 200 AND result: FAIL
     * request parameter 에러
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = {ConstraintViolationException.class})
    public CommonResponse constraintViolationException(ConstraintViolationException e) {
        log.warn("[ConstraintViolationException] errorMsg = {}", NestedExceptionUtils.getMostSpecificCause(e).getMessage());

        if (e.getMessage() != null) {
            String message = e.getMessage();
            return CommonResponse.fail(ErrorCode.COMMON_INVALID_PARAMETER.getErrorCode(), message);
        } else {
            return CommonResponse.fail(ErrorCode.COMMON_INVALID_PARAMETER.getErrorCode(), ErrorCode.COMMON_INVALID_PARAMETER.getErrorMsg());
        }
    }

    /**
     * http status: 200 AND result: FAIL
     * request parameter 에러
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = {BindException.class})
    public CommonResponse bindException(BindException e) {
        log.warn("[BindException] errorMsg = {}", NestedExceptionUtils.getMostSpecificCause(e).getMessage());

        BindingResult bindingResult = e.getBindingResult();
        List<String> messages = bindingResult.getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        if (!CollectionUtils.isEmpty(messages)) {
            return CommonResponse.fail(ErrorCode.COMMON_INVALID_PARAMETER.getErrorCode(), String.join(",", messages));
        } else {
            return CommonResponse.fail(ErrorCode.COMMON_INVALID_PARAMETER.getErrorCode(), ErrorCode.COMMON_INVALID_PARAMETER.getErrorMsg());
        }
    }

    /**
     * http status: 200 AND result: FAIL
     * request parameter 에러
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = {MissingServletRequestParameterException.class})
    public CommonResponse missingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.warn("[MissingServletRequestParameterException] errorMsg = {}", NestedExceptionUtils.getMostSpecificCause(e).getMessage());

        if (e.getParameterName() != null) {
            String message = "Request Error : 요청 파라미터" + "(" + e.getParameterName() + ") 가 없습니다. ";
            return CommonResponse.fail(ErrorCode.COMMON_INVALID_PARAMETER.getErrorCode(), message);
        } else {
            return CommonResponse.fail(ErrorCode.COMMON_INVALID_PARAMETER.getErrorCode(), ErrorCode.COMMON_INVALID_PARAMETER.getErrorMsg());
        }
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = {MaxUploadSizeExceededException.class})
    public CommonResponse maxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        log.warn("[MaxUploadSizeExceededException] errorMsg = {}", NestedExceptionUtils.getMostSpecificCause(e).getMessage());

        return CommonResponse.fail(ErrorCode.COMMON_FILE_MAX_SIZE_ERROR.getErrorCode(), ErrorCode.COMMON_FILE_MAX_SIZE_ERROR.getErrorMsg());
    }

}