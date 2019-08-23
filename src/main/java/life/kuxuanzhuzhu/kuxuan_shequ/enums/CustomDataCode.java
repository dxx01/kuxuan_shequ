package life.kuxuanzhuzhu.kuxuan_shequ.enums;

import java.util.List;

/**
 * @author 邓鑫鑫
 * @date 2019年07月31日 15:11:45
 * @Description
 */
public enum CustomDataCode {

    COMMENT_IS_NULL(1001,"暂无评论");

    private Integer code;
    private String message;

    CustomDataCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
