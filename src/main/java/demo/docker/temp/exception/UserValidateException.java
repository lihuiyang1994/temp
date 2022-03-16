package demo.docker.temp.exception;

/**
 * lihui
 * 2022/3/16
 * UserValidateException
 *
 * @description 用户Valida异常
 */
public class UserValidateException extends RuntimeException{

    public UserValidateException(String msg) {
        super(msg);
    }
}
