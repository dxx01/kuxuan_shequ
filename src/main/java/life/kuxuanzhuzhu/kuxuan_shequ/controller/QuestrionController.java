package life.kuxuanzhuzhu.kuxuan_shequ.controller;

import life.kuxuanzhuzhu.kuxuan_shequ.dto.CommentDTO;
import life.kuxuanzhuzhu.kuxuan_shequ.dto.QuestionDTO;
import life.kuxuanzhuzhu.kuxuan_shequ.enums.CommentTypeEnum;
import life.kuxuanzhuzhu.kuxuan_shequ.service.CommentService;
import life.kuxuanzhuzhu.kuxuan_shequ.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author 邓鑫鑫
 * @date 2019年07月25日 18:44:53
 * @Description
 */
@Controller
public class QuestrionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id,
                           Model model) {
        //累加阅读数
        questionService.incView(id);
        QuestionDTO questionDTO = questionService.getById(id);
        List<QuestionDTO> questionDTOList = questionService.selectByTag(questionDTO.getId(),questionDTO.getTag());
        //获取评论信息
        List<CommentDTO> list = commentService.selectByParentId(id, CommentTypeEnum.QUESTION.getType());
        model.addAttribute("questionDTO",questionDTO);
        model.addAttribute("list",list);
        model.addAttribute("questionDTOList",questionDTOList);
        return "question";
    }

}
