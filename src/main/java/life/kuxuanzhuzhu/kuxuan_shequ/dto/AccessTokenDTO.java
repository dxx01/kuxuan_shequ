package life.kuxuanzhuzhu.kuxuan_shequ.dto;

import lombok.Data;

/**
 * @author 邓鑫鑫
 * @date 2019年07月22日 17:00:18
 * @Description
 */
@Data
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;
}
