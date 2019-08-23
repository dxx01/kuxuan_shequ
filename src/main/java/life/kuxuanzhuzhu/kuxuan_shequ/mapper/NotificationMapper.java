package life.kuxuanzhuzhu.kuxuan_shequ.mapper;

import life.kuxuanzhuzhu.kuxuan_shequ.dto.NotificationDTO;
import life.kuxuanzhuzhu.kuxuan_shequ.model.Notification;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 邓鑫鑫
 * @date 2019年08月12日 17:24:39
 * @Description
 */
@Mapper
public interface NotificationMapper {

    /**
     * 创建通知
     * @param notification 通知对象
     * @return
     */
    Integer insert(Notification notification);

    /**
     * 获取通知条数
     * @param id 接收人编号
     * @return
     */
    Integer selectCountByReceiver(@Param("receiver") Long id);

    /**
     * 根据接收人分页查询通知
     * @param id
     * @param size
     * @param offset
     * @return
     */
    List<NotificationDTO> selectByReceiver(@Param("receiver") Long id, @Param("size") Integer size, @Param("offset") Integer offset);
}
