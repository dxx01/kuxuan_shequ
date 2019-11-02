package life.kuxuanzhuzhu.kuxuan_shequ.Exception;

import life.kuxuanzhuzhu.kuxuan_shequ.enums.CustomDataCode;

/**
 * @author 邓鑫鑫
 * @date 2019年07月27日 21:15:09
 * @Description 异常枚举类
 */
public enum CustomErrorCode implements ICustomErrorCode {

    QUESTION_NOT_FOUND(2001, "你找到问题不在了，要不要换个试试？"),
    TARGET_PARAM_NOT_FOUND(2002, "未选中任何问题或评论进行回复"),
    NO_LOGIN(2003, "当前操作需要登录，请登陆后重试"),
    SYS_ERROR(2004, "服务冒烟了，要不然你稍后再试试！！！"),
    TYPE_PARAM_WRONG(2005, "评论类型错误或不存在"),
    COMMENT_NOT_FOUND(2006, "回复的评论不存在了，要不要换个试试？"),
    CONTENT_IS_EMPTY(2007, "输入内容不能为空"),
    READ_NOTIFICATION_FAIL(2008, "兄弟你这是读别人的信息呢？"),
    NOTIFICATION_NOT_FOUND(2009, "消息莫非是不翼而飞了？"),
    FILE_UPLOAD_FAIL(2010, "图片上传失败"),
    INVALID_INPUT(2011, "非法输入"),
    INVALID_OPERATION(2012, "兄弟，是不是走错房间了？"),
    COMMENT_DELETE_FAIL(2013, "删除失败，可能是删除的评论不存了，要不刷新试试？"),
    COMMENT_FAIL(2014,"评论失败,要不刷新试试？"),
    No_NOTICE(2015,"暂无通知"),
    DIANZAN_FAIL(2016,"点赞失败,请稍后再试！"),
    REGISTER_FAIL(2017,"注册失败，请稍后再试！")
    ;
    private Integer code;
    private String message;


    CustomErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }


    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
