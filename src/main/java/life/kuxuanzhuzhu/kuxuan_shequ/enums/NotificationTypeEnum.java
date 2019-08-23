package life.kuxuanzhuzhu.kuxuan_shequ.enums;

/**
 * @author 邓鑫鑫
 * @date 2019年08月12日 17:41:07
 * @Description
 */
public enum NotificationTypeEnum {
    REPLY_QUESTION(1,"回复了问题"),
    REPLY_COMMENT(2,"回复了评论"),
    ;

    private int type;
    private String name;

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    NotificationTypeEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }
}
