package life.kuxuanzhuzhu.kuxuan_shequ.enums;



/**
 * @author 邓鑫鑫
 * @date 2019年07月29日 11:04:47
 * @Description 评论type的枚举
 */
public enum CommentTypeEnum {

    QUESTION(1),//回复问题
    COMMENT(2);//回复评论

    private Integer type;

    CommentTypeEnum(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public static boolean isExist(Integer type) {
        for(CommentTypeEnum commentTypeEnum : CommentTypeEnum.values()){
            if(commentTypeEnum.getType() == type){
                return true;
            }
        }
        return false;
    }
}
