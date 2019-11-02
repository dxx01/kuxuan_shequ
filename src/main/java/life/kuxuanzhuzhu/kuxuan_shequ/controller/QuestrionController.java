package life.kuxuanzhuzhu.kuxuan_shequ.controller;

import life.kuxuanzhuzhu.kuxuan_shequ.dto.CommentDTO;
import life.kuxuanzhuzhu.kuxuan_shequ.dto.QuestionDTO;
import life.kuxuanzhuzhu.kuxuan_shequ.enums.CommentTypeEnum;
import life.kuxuanzhuzhu.kuxuan_shequ.service.CommentService;
import life.kuxuanzhuzhu.kuxuan_shequ.service.NotificationService;
import life.kuxuanzhuzhu.kuxuan_shequ.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    @Autowired
    private NotificationService notificationService;

    @RequestMapping(value = {"/question/{id}","/question/{id}/{nId}"})
    public String question(@PathVariable(name = "id") Long id, @PathVariable(value = "nId",required = false) Long nId,
                           Model model) {
        //累加阅读数
        questionService.incView(id);


        //Executors多线程测试穿透问题，一个线程池
        /*Runnable runnable = new Runnable() {
            @Override
            public void run() {
                questionService.getById(id);
            }
        };
        ExecutorService executorService =  Executors.newFixedThreadPool(25);
        for (int i = 0;i<10000;i++){
            executorService.submit(runnable);
        }*/


        QuestionDTO questionDTO = questionService.getById(id);
        //修改通知的状态
        if(null != nId){
            notificationService.updateStatusById(nId);
        }
        List<QuestionDTO> questionDTOList = questionService.selectByTag(questionDTO.getId(), questionDTO.getTag());
        //获取评论信息
        List<CommentDTO> list = commentService.selectByParentId(id, CommentTypeEnum.QUESTION.getType());
        model.addAttribute("questionDTO", questionDTO);
        model.addAttribute("list", list);
        model.addAttribute("questionDTOList", questionDTOList);
        return "question";
    }

}
