package life.kuxuanzhuzhu.kuxuan_shequ.controller;

import life.kuxuanzhuzhu.kuxuan_shequ.Exception.CustomErrorCode;
import life.kuxuanzhuzhu.kuxuan_shequ.dto.CommentCreateDTO;
import life.kuxuanzhuzhu.kuxuan_shequ.dto.CommentDTO;
import life.kuxuanzhuzhu.kuxuan_shequ.dto.Result;
import life.kuxuanzhuzhu.kuxuan_shequ.dto.UserAndKxUser;
import life.kuxuanzhuzhu.kuxuan_shequ.enums.CommentTypeEnum;
import life.kuxuanzhuzhu.kuxuan_shequ.model.Comment;
import life.kuxuanzhuzhu.kuxuan_shequ.model.User;
import life.kuxuanzhuzhu.kuxuan_shequ.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 邓鑫鑫
 * @date 2019年07月29日 09:47:56
 * @Description 评论控制层
 */
@Controller
public class CommentController {


    @Autowired
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "comment", method = RequestMethod.POST)
    public Object comment(@RequestBody CommentCreateDTO commentCreateDTO,
                          HttpServletRequest request) {

        UserAndKxUser userAndKxUser = (UserAndKxUser) request.getSession().getAttribute("user");
        //判断用户是否登录
        if (null == userAndKxUser) {
            return Result.error(CustomErrorCode.NO_LOGIN);
        }

        //判断评论是否为空
        if (null == commentCreateDTO || StringUtils.isBlank(commentCreateDTO.getContent())) {
            return Result.error(CustomErrorCode.CONTENT_IS_EMPTY);
        }
        Comment comment = new Comment();
        comment.setId(commentCreateDTO.getId());
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setType(commentCreateDTO.getType());
        comment.setContent(commentCreateDTO.getContent());
        comment.setCommentCount(0);
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setCommentator(userAndKxUser.getId());
        comment.setLikeCount(0L);
        comment.setToId(commentCreateDTO.getToId());
        //插入数据
        commentService.insert(comment);
        return Result.ok();
    }


    /**
     * 获取子评论信息
     * @param parentId 父级编号
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "selectChileByParentId", method = RequestMethod.GET)
    public Result<Map<Object,Object>> selectChileByParentId(@RequestParam("parentId") Long parentId,
                                                    HttpServletRequest request) {
        List<CommentDTO> list = commentService.selectByParentId(parentId, CommentTypeEnum.COMMENT.getType());
        Map<Object,Object> map = new HashMap<>();
        map.put("list",list);
        UserAndKxUser userAndKxUser = (UserAndKxUser) request.getSession().getAttribute("user");
        map.put("user",userAndKxUser);
        return Result.ok(map);
    }

    /**
     * 删除评论
     * @param id 评论编号
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "deletecomment", method = RequestMethod.GET)
    public Result DeleteComment(@RequestParam("id") Long id,
                                @RequestParam("parentId") Long parentId) {
        commentService.deleteById(id,parentId);
        return Result.ok();
    }


}
