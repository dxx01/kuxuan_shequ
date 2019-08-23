package life.kuxuanzhuzhu.kuxuan_shequ.controller;

import life.kuxuanzhuzhu.kuxuan_shequ.Exception.CustomErrorCode;
import life.kuxuanzhuzhu.kuxuan_shequ.dto.QuestionDTO;
import life.kuxuanzhuzhu.kuxuan_shequ.dto.Result;
import life.kuxuanzhuzhu.kuxuan_shequ.enums.CommentTypeEnum;
import life.kuxuanzhuzhu.kuxuan_shequ.mapper.UserMapper;
import life.kuxuanzhuzhu.kuxuan_shequ.model.Question;
import life.kuxuanzhuzhu.kuxuan_shequ.model.User;
import life.kuxuanzhuzhu.kuxuan_shequ.service.QuestionService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 邓鑫鑫
 * @date 2019年07月23日 16:29:29
 * @Description
 */
@Controller
public class PublishController {


    @Autowired
    private QuestionService questionService;

    @Autowired
    public UserMapper userMapper;

    @GetMapping("publish/{id}")
    public String edit(@PathVariable(name = "id") Long id,
                       Model model) {
        QuestionDTO questionDTO = questionService.getById(id);
        String[] tags = questionDTO.getTag().split(",");
        model.addAttribute("title", questionDTO.getTitle());
        model.addAttribute("description", questionDTO.getDescription());
        model.addAttribute("tags", tags);
        model.addAttribute("id", questionDTO.getId());
        return "publish";
    }

    @Test
    public void run() {
        String str = "abc,12,3yy98,0";
        String[] strs = str.split(",");
        System.out.println(strs);
        for (int i = 0, len = strs.length; i < len; i++) {
            System.out.println(strs[i].toString());
        }
    }

    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }

    @ResponseBody
    @PostMapping(value = "/publish")
    public Result doPublish(@RequestParam(value = "title", required = false) String title,
                            @RequestParam(value = "description", required = false) String description,
                            @RequestParam(value = "tag", required = false) String tag,
                            @RequestParam(value = "id", required = false) Long id,
                            HttpServletRequest request,
                            Model model) {
        User user = (User) request.getSession().getAttribute("user");
        if (null == user) {
            model.addAttribute("error", "用户未登录");
            return Result.error(CustomErrorCode.NO_LOGIN);
        }
        Question question = new Question();
        question.setId(id);
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        questionService.createOrUpdate(question);
        return Result.ok();
    }
}
