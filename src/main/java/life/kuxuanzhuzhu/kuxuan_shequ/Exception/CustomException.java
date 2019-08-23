package life.kuxuanzhuzhu.kuxuan_shequ.Exception;

/**
 * @author 邓鑫鑫
 * @date 2019年07月27日 21:01:45
 * @Description
 */
public class CustomException extends RuntimeException {

    private String message;
    private Integer code;


    public CustomException(ICustomErrorCode iCustomErrorCode) {
        this.code = iCustomErrorCode.getCode();
        this.message = iCustomErrorCode.getMessage();
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
