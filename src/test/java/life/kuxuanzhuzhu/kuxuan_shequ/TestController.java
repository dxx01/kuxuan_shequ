package life.kuxuanzhuzhu.kuxuan_shequ;

import life.kuxuanzhuzhu.kuxuan_shequ.tool.Yzm;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.junit.Test;

import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.logging.SimpleFormatter;

/**
 * @author 邓鑫鑫
 * @date 2019年09月26日 14:24:35
 * @Description
 */
public class TestController {

    @Test
    public void start(){
        UUID str = UUID.randomUUID();
        System.out.println("kx"+str);

        Long time = 1569491946975L;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:hh:ss");
        String str1 = simpleDateFormat.format(new Date(1569492436791l));
        String str2 = simpleDateFormat.format(new Date(System.currentTimeMillis()));
        System.out.println(str1);
        System.out.println(str2);
        String yzm = Yzm.create();
        System.out.println(yzm);
        boolean flag = Yzm.checkEmail("56124@qq.com");
        System.out.println(flag);
        String token = "kxb321e8eb-d4e9-4a0b-821d-d3bd241582e2";
        System.out.println(token.length());
        System.out.println(token.substring(0,2));
    }

    //@Test
    public void send(){
        try {
            HtmlEmail email = new HtmlEmail();
            email.setHostName("smtp.qq.com");//邮箱的SMTP服务器，一般123邮箱的是smtp.123.com,qq邮箱为smtp.qq.com
            email.setCharset("utf-8");//设置发送的字符类型
            email.addTo("3400510243@qq.com");//设置收件人
            email.setFrom("1693172461@qq.com","酷炫社区");//发送人的邮箱为自己的，用户名可以随便填
            email.setAuthentication("1693172461@qq.com","aqexjcphmwkwgabd");//设置发送人到的邮箱和用户名和授权码(授权码是自己设置的)
            email.setSubject("测试");//设置发送主题

            String yzm = Yzm.create();
            email.setMsg("填写你的发送内容");//设置发送内容
            email.send();//进行发送
        } catch (EmailException e) {
            e.printStackTrace();
        }

    }



}
