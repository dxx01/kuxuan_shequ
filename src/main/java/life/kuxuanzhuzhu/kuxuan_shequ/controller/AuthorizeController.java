package life.kuxuanzhuzhu.kuxuan_shequ.controller;

import life.kuxuanzhuzhu.kuxuan_shequ.dto.AccessTokenDTO;
import life.kuxuanzhuzhu.kuxuan_shequ.dto.GithubUser;
import life.kuxuanzhuzhu.kuxuan_shequ.mapper.UserMapper;
import life.kuxuanzhuzhu.kuxuan_shequ.model.User;
import life.kuxuanzhuzhu.kuxuan_shequ.provider.GitHubProvider;
import life.kuxuanzhuzhu.kuxuan_shequ.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author 邓鑫鑫
 * @date 2019年07月22日 16:49:27
 * @Description github登录授权控制层
 */
@Controller
@Slf4j
public class AuthorizeController {

    @Autowired
    private GitHubProvider gitHubProvider;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.client.url}")
    private String clientUrl;


    @Autowired
    private UserService userService;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletResponse response) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(clientUrl);
        accessTokenDTO.setState(state);
        String accessToken = gitHubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = gitHubProvider.getUser(accessToken);
        if (null != githubUser) {
            User user = new User();
            String token = UUID.randomUUID().toString();
            String id = UUID.randomUUID().toString();
            user.setId(id);
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setAvatarUrl(githubUser.getAvatarUrl());
            userService.createOrUpdate(user);
            response.addCookie(new Cookie("token", token));
            return "redirect:/index";
        } else {
            //ctrl+p提示方法参数
            log.error("AuthorizeController-callback error ,{}",githubUser);
            return "redirect:/index";
        }

    }

    @GetMapping("quit")
    public String quit(HttpServletRequest request,
                       HttpServletResponse response){
        request.getSession().removeAttribute("user");
        request.getSession().removeAttribute("inFormNum");
        Cookie cookie = new Cookie("token",null);
        response.addCookie(cookie);
        cookie.setMaxAge(0);
        return "redirect:/index";
    }
}
