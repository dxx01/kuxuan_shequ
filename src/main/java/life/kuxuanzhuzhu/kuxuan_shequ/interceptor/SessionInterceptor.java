package life.kuxuanzhuzhu.kuxuan_shequ.interceptor;

import life.kuxuanzhuzhu.kuxuan_shequ.dto.UserAndKxUser;
import life.kuxuanzhuzhu.kuxuan_shequ.mapper.NotificationMapper;
import life.kuxuanzhuzhu.kuxuan_shequ.mapper.UserMapper;
import life.kuxuanzhuzhu.kuxuan_shequ.model.KxUser;
import life.kuxuanzhuzhu.kuxuan_shequ.model.User;
import life.kuxuanzhuzhu.kuxuan_shequ.service.KxUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 邓鑫鑫
 * @date 2019年07月25日 10:56:30
 * @Description  拦截类
 */
@Service
public class SessionInterceptor implements HandlerInterceptor {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private KxUserService kxUserService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        if (null != cookies && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    UserAndKxUser userAndKxUser = new UserAndKxUser();
                    //根据token获取登录用户的信息
                    if(36 == token.length()){
                        User user = userMapper.findByToken(token);
                        if (null != user) {
                            BeanUtils.copyProperties(user,userAndKxUser);
                            request.getSession().setAttribute("user", userAndKxUser);
                            //根据用户信息获取通知数
                            Integer inFormNum = notificationMapper.selectCountByReceiver(user.getId());
                            request.getSession().setAttribute("inFormNum",inFormNum);
                        }
                        break;
                    }else if(38 == token.length() && "kx".equals(token.substring(0,2))){
                        KxUser kxUser = kxUserService.selectByToken(token);
                        if(null != kxUser){
                            BeanUtils.copyProperties(kxUser,userAndKxUser);
                            request.getSession().setAttribute("user", userAndKxUser);
                            //根据用户信息获取通知数
                            Integer inFormNum = notificationMapper.selectCountByReceiver(kxUser.getId());
                            request.getSession().setAttribute("inFormNum",inFormNum);
                        }
                        break;
                    }


                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
