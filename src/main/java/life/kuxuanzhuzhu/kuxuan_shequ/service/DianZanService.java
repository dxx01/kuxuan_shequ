package life.kuxuanzhuzhu.kuxuan_shequ.service;

import life.kuxuanzhuzhu.kuxuan_shequ.Exception.CustomErrorCode;
import life.kuxuanzhuzhu.kuxuan_shequ.Exception.CustomException;
import life.kuxuanzhuzhu.kuxuan_shequ.mapper.CommentMapper;
import life.kuxuanzhuzhu.kuxuan_shequ.mapper.DianZanMapper;
import life.kuxuanzhuzhu.kuxuan_shequ.mapper.NotificationMapper;
import life.kuxuanzhuzhu.kuxuan_shequ.model.Comment;
import life.kuxuanzhuzhu.kuxuan_shequ.model.DianZan;
import life.kuxuanzhuzhu.kuxuan_shequ.model.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 邓鑫鑫
 * @date 2019年08月21日 23:51:25
 * @Description
 */
@Service
public class DianZanService {

    @Autowired
    private DianZanMapper dianZanMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private NotificationMapper notificationMapper;


    @Transactional
    public void insertAndUpdate(DianZan dianZan) {
        DianZan isNull = dianZanMapper.selectByCommentIdAndUserId(dianZan);
        if (null == isNull) {
            Integer row = dianZanMapper.insert(dianZan);
            if (row == 0) {
                throw new CustomException(CustomErrorCode.DIANZAN_FAIL);
            }
        } else {
            Integer row = dianZanMapper.update(dianZan);
            if (row == 0) {
                throw new CustomException(CustomErrorCode.DIANZAN_FAIL);
            }
        }
        String type = "down";
        if ("1".equals(dianZan.getStatus())) {
            type = "up";
            Comment comment = commentMapper.selectById(dianZan.getCommentId());
            Notification notification  = new Notification();
            notification.setNotifier(dianZan.getUserId());
            notification.setReceiver(comment.getCommentator());
            notification.setOuterId(dianZan.getCommentId());
            notification.setType(3);
            notification.setQuestionId(dianZan.getQuestionId());
            notification.setGmtCreate(System.currentTimeMillis());
            Integer row = notificationMapper.insert(notification);
            if(row == 0){
                throw new CustomException(CustomErrorCode.DIANZAN_FAIL);
            }
        }
        Integer row = commentMapper.updateLikeCountByIdUpOrDown(dianZan.getCommentId(), 1, type);
        if (row == 0) {
            throw new CustomException(CustomErrorCode.DIANZAN_FAIL);
        }
    }

}
