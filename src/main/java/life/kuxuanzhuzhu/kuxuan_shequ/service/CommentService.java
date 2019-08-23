package life.kuxuanzhuzhu.kuxuan_shequ.service;

import life.kuxuanzhuzhu.kuxuan_shequ.Exception.CustomErrorCode;
import life.kuxuanzhuzhu.kuxuan_shequ.Exception.CustomException;
import life.kuxuanzhuzhu.kuxuan_shequ.dto.CommentDTO;
import life.kuxuanzhuzhu.kuxuan_shequ.dto.QuestionDTO;
import life.kuxuanzhuzhu.kuxuan_shequ.dto.Result;
import life.kuxuanzhuzhu.kuxuan_shequ.enums.CommentTypeEnum;
import life.kuxuanzhuzhu.kuxuan_shequ.enums.NotificationTypeEnum;
import life.kuxuanzhuzhu.kuxuan_shequ.mapper.CommentMapper;
import life.kuxuanzhuzhu.kuxuan_shequ.mapper.NotificationMapper;
import life.kuxuanzhuzhu.kuxuan_shequ.mapper.QuestionMapper;
import life.kuxuanzhuzhu.kuxuan_shequ.mapper.UserMapper;
import life.kuxuanzhuzhu.kuxuan_shequ.model.Comment;
import life.kuxuanzhuzhu.kuxuan_shequ.model.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 邓鑫鑫
 * @date 2019年07月29日 11:10:55
 * @Description
 */
@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;


    @Autowired
    private NotificationMapper notificationMapper;


    /**
     * 回复
     * @param comment
     */
    @Transactional
    public void insert(Comment comment) {

        if (null == comment.getParentId() || 0 == comment.getParentId()) {
            throw new CustomException(CustomErrorCode.TARGET_PARAM_NOT_FOUND);
        }

        if (null == comment.getType() || !CommentTypeEnum.isExist(comment.getType())) {
            throw new CustomException(CustomErrorCode.TYPE_PARAM_WRONG);
        }
        Notification notification = new Notification(); //创建通知对象
        if (comment.getType() == CommentTypeEnum.COMMENT.getType()) {
            //回复评论
            //查询是否有该评论
            Comment dsComment = commentMapper.selectById(comment.getId());
            if (null == dsComment) {
                throw new CustomException(CustomErrorCode.COMMENT_NOT_FOUND);
            }
            Integer row = commentMapper.insert(comment);
            if (row == 0) {
                throw new CustomException(CustomErrorCode.COMMENT_FAIL);
            }
            //问题评论数加1
            Long id ;//问题id
            Long parentId = comment.getParentId();
            while(true){
                Comment comment1 = commentMapper.selectById(parentId);

                if(comment1.getType() == 1){
                    id = comment1.getParentId();
                    break;
                }
                 parentId = comment1.getParentId();
            }
            QuestionDTO questionDTO = questionMapper.getById(id);
            if (null == questionDTO) {
                throw new CustomException(CustomErrorCode.QUESTION_NOT_FOUND);
            }
            questionDTO.setCommentCount(1);
            questionMapper.updateContentCountByIdUp(questionDTO);
            //评论评论数加1
            commentMapper.updateCommentByIdUpOrDown(comment.getParentId(), 1, "up");
            notification.setReceiver(dsComment.getCommentator());//评论接收人
            notification.setType(NotificationTypeEnum.REPLY_COMMENT.getType());
            notification.setQuestionId(questionDTO.getId());
        } else {
            //回复问题
            QuestionDTO questionDTO = questionMapper.getById(comment.getParentId());
            if (null == questionDTO) {
                throw new CustomException(CustomErrorCode.QUESTION_NOT_FOUND);
            }
            Integer row = commentMapper.insert(comment);
            if (row == 0) {
                throw new CustomException(CustomErrorCode.COMMENT_FAIL);
            }
            questionDTO.setCommentCount(1);
            questionMapper.updateContentCountByIdUp(questionDTO);
            notification.setReceiver(questionDTO.getCreator());//问题接收人
            notification.setType(NotificationTypeEnum.REPLY_QUESTION.getType());
            notification.setQuestionId(questionDTO.getId());
        }
        //通知创建
        notification.setNotifier(comment.getCommentator());//发送人
        notification.setOuterId(comment.getParentId());//评论的id
        notificationMapper.insert(notification);
    }


    /**
     * 根据父级编号查询评论和评论用户信息(评论问题的)
     *
     * @param parentId 父级编号
     * @param type     评论类型
     * @return
     */
    public List<CommentDTO> selectByParentId(Long parentId, int type) {
        //ctrl+alt+v:抽取变量  ctrl+alt+m:抽取方法   ctrl+alt+p:抽取参数
        List<CommentDTO> list = commentMapper.selectByIdAndParentIdAndType(parentId, type);
        return list;
    }

    /**
     * 删除评论
     *
     * @param id
     * @return
     */
    @Transactional
    public Result deleteById(Long id, Long parentId) {
            //查询这条评论是否存在
            Comment comment = commentMapper.selectById(id);
            if(null == comment){
                return Result.error(CustomErrorCode.COMMENT_DELETE_FAIL);
            }else{
                Integer rows = commentMapper.deleteById(id);
                if(1 == comment.getType()){
                    //问题评论
                    //修改问题评论数（减）
                    QuestionDTO questionDTO = new QuestionDTO();
                    questionDTO.setId(parentId);
                    questionDTO.setCommentCount(rows);
                    questionMapper.updateContentCountByIdDown(questionDTO);
                }else{
                    //判断子级评论的父级评论是否存在
                    Comment comment1 = commentMapper.selectById(parentId);
                    if(null == comment1){
                        return Result.error(CustomErrorCode.COMMENT_DELETE_FAIL);
                    }else{
                        //修改问题评论数（减）
                        QuestionDTO questionDTO = new QuestionDTO();
                        questionDTO.setId(comment1.getParentId());
                        questionDTO.setCommentCount(rows);
                        questionMapper.updateContentCountByIdDown(questionDTO);
                        commentMapper.updateCommentByIdUpOrDown(comment1.getId(), 1, "down");
                    }
                }
                return Result.ok();
            }
    }

}
