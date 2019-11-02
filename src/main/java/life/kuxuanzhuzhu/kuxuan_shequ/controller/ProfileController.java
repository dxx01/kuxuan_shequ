package life.kuxuanzhuzhu.kuxuan_shequ.controller;

import life.kuxuanzhuzhu.kuxuan_shequ.dto.NotificationDTO;
import life.kuxuanzhuzhu.kuxuan_shequ.dto.PageDTO;
import life.kuxuanzhuzhu.kuxuan_shequ.dto.Result;
import life.kuxuanzhuzhu.kuxuan_shequ.dto.UserAndKxUser;
import life.kuxuanzhuzhu.kuxuan_shequ.mapper.UserMapper;
import life.kuxuanzhuzhu.kuxuan_shequ.model.User;
import life.kuxuanzhuzhu.kuxuan_shequ.service.NotificationService;
import life.kuxuanzhuzhu.kuxuan_shequ.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author 邓鑫鑫
 * @date 2019年07月24日 22:21:09
 * @Description 我的问题
 */
@Controller
public class ProfileController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private NotificationService notificationService;

    @GetMapping("profile/{action}")
    public String profile(@PathVariable(name = "action") String action,
                          Model model,
                          HttpServletRequest request,
                          @RequestParam(value = "page", defaultValue = "1") Integer page,
                          @RequestParam(value = "size", defaultValue = "5") Integer size) {

        UserAndKxUser userAndKxUser = (UserAndKxUser) request.getSession().getAttribute("user");
        if (null == userAndKxUser) {
            return "redirect:/index";
        }
        PageDTO pageDTO = null;
        if ("questions".equals(action)) {
            model.addAttribute("section", "questions");
            model.addAttribute("sectionName", "我的提问");
            Result result = questionService.selectByUserId(userAndKxUser.getId(), page, size);
            pageDTO = (PageDTO) result.getData();
            if(0 == pageDTO.getData().size()){
                model.addAttribute("error", "你还未发布过问题");
            }
            model.addAttribute("pageDTO", pageDTO);
        } else if ("replies".equals(action)) {
            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "最新回复");
            Result result = notificationService.selectByReceiver(userAndKxUser.getId(), page, size);
            pageDTO = (PageDTO) result.getData();
            if(null == pageDTO){
                model.addAttribute("error", "暂时未有通知");
            }
            model.addAttribute("pageDTO", pageDTO);
        }
        return "profile";
    }


}
