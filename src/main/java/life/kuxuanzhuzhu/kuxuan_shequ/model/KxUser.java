package life.kuxuanzhuzhu.kuxuan_shequ.model;

import lombok.Data;

/**
 * @author 邓鑫鑫
 * @date 2019年09月27日 15:55:46
 * @Description 项目用户类
 */
@Data
public class KxUser {
    private String id;
    private String name;  //昵称
    private String email;   //邮箱
    private String pass;    //密码
    private String token;
    private Long gmtCreate;  //创建时间
    private Long gmtModified;  //更新时间
    private String avatarUrl;   //图片地址
    private String status;  //状态
}
