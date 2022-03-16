package demo.docker.temp.config;

import demo.docker.temp.exception.UserValidateException;
import demo.docker.temp.util.Result;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * lihui
 * 2022/3/16
 * ExeptionRestControllerAdvice
 *
 * @description 异常捕捉器
 */
@RestControllerAdvice
public class ExceptionRestControllerAdvice {

    @ExceptionHandler({Throwable.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result serverError(Throwable e) {
        e.printStackTrace();
        return Result.SERVER_ERROR;
    }

    @ExceptionHandler({UserValidateException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result validateError(UserValidateException e) {
        e.printStackTrace();
        return Result.BAD_REQUEST;
    }
}
