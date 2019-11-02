package life.kuxuanzhuzhu.kuxuan_shequ.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 邓鑫鑫
 * @date 2019年09月27日 23:52:57
 * @Description GITHUB和酷炫用户集合类
 */
@Data
public class UserAndKxUser implements Serializable {
    private String id;
    private String name;
    private String accountId;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
    private String avatarUrl;
    private String email;   //邮箱
    private String pass;    //密码
    private String status;  //状态
}
