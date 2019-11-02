package life.kuxuanzhuzhu.kuxuan_shequ.tool;

import java.util.Random;

/**
 * @author 邓鑫鑫
 * @date 2019年09月27日 13:17:17
 * @Description 验证码生成工具类
 */
public class Yzm {

    private static final String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    /**
     * 生成6为验证码
     *
     * @return
     */
    public static String create() {
        StringBuilder sb = new StringBuilder(4);
        for (int i = 0; i < 6; i++) {
            char ch = str.charAt(new Random().nextInt(str.length()));
            sb.append(ch);
        }
        return sb + "";
    }

    // 校验qq邮箱格式
    public static boolean checkEmail(String email) {
        return email.matches("^[1-9][0-9]{4,9}@qq.com$");
    }
}
