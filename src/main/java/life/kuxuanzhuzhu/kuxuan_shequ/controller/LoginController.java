package life.kuxuanzhuzhu.kuxuan_shequ.controller;

import life.kuxuanzhuzhu.kuxuan_shequ.Exception.CustomErrorCode;
import life.kuxuanzhuzhu.kuxuan_shequ.dto.Result;
import life.kuxuanzhuzhu.kuxuan_shequ.model.KxUser;
import life.kuxuanzhuzhu.kuxuan_shequ.service.KxUserService;
import life.kuxuanzhuzhu.kuxuan_shequ.tool.Yzm;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author 邓鑫鑫
 * @date 2019年09月26日 19:08:16
 * @Description 登录控制层
 */
@Controller
public class LoginController {


    @Autowired
    private KxUserService kxUserService;

    @RequestMapping(value = "login")
    public String login(){
        return "login";
    }


    @ResponseBody
    @PostMapping(value = "login")
    public String kxLogin(@RequestParam(value = "email", required = false)String email,
                          @RequestParam(value = "pass", required = false)String pass,
                          HttpServletResponse response){
        KxUser kxUser = kxUserService.login(email,pass);
        if(null != kxUser){
            response.addCookie(new Cookie("token", kxUser.getToken()));
            return "ok";
        }
        return "账号或密码错误";
    }


    @ResponseBody
    @RequestMapping(value = "getYzm",produces="text/html;charset=UTF-8;",method = RequestMethod.POST)
    public String getYzm(@RequestParam("email")String email){
        if("" == email){
            return "邮箱不能为空";
        }
        //检验邮箱格式
        boolean flag = Yzm.checkEmail(email);
        if(false == flag){
            return "请输入正确的邮箱";
        }

        KxUser kxUser = kxUserService.selectByEmail(email);
        if(null != kxUser){
            return "该邮箱已被注册";
        }

        //验证码
        String yzm = Yzm.create();
        try {
            HtmlEmail htmlEmail = new HtmlEmail();
            htmlEmail.setHostName("smtp.qq.com");//邮箱的SMTP服务器，一般123邮箱的是smtp.123.com,qq邮箱为smtp.qq.com
            htmlEmail.setCharset("utf-8");//设置发送的字符类型
            htmlEmail.addTo(email);//设置收件人
            htmlEmail.setFrom("1693172461@qq.com","酷炫社区");//发送人的邮箱为自己的，用户名可以随便填
            htmlEmail.setAuthentication("1693172461@qq.com","aqexjcphmwkwgabd");//设置发送人到的邮箱和用户名和授权码(授权码是自己设置的)
            htmlEmail.setSubject("测试");//设置发送主题
            htmlEmail.setMsg("验证码："+yzm);//设置发送内容
            htmlEmail.send();//进行发送
        } catch (EmailException e) {
            e.printStackTrace();
        }
        return yzm;
    }


    @ResponseBody
    @RequestMapping(value = "register",method = RequestMethod.GET)
    public Result register(@RequestParam(value = "name", required = false)String name,
                           @RequestParam(value = "email", required = false)String email,
                           @RequestParam(value = "pass", required = false)String pass,
                           HttpServletResponse response){
        KxUser kxUser = new KxUser();
        String token = "kx"+UUID.randomUUID().toString();//生成token
        String id = UUID.randomUUID().toString();//生成编号的
        kxUser.setId(id);
        kxUser.setToken(token);
        kxUser.setName(name);
        kxUser.setEmail(email);
        kxUser.setPass(pass);
        kxUser.setAvatarUrl("/custom/img/userdefault.jpg");
        kxUser.setGmtCreate(System.currentTimeMillis());
        kxUser.setGmtModified(System.currentTimeMillis());
        kxUserService.insert(kxUser);
        response.addCookie(new Cookie("token", token));
        return Result.ok();
    }
}
