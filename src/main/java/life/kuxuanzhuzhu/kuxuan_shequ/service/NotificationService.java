package life.kuxuanzhuzhu.kuxuan_shequ.service;

import life.kuxuanzhuzhu.kuxuan_shequ.Exception.CustomErrorCode;
import life.kuxuanzhuzhu.kuxuan_shequ.dto.NotificationDTO;
import life.kuxuanzhuzhu.kuxuan_shequ.dto.PageDTO;
import life.kuxuanzhuzhu.kuxuan_shequ.dto.Result;
import life.kuxuanzhuzhu.kuxuan_shequ.mapper.NotificationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 邓鑫鑫
 * @date 2019年08月12日 19:34:59
 * @Description
 */
@Service
public class NotificationService {


    @Autowired
    private NotificationMapper notificationMapper;

    /**
     *
     * @param id
     * @param page
     * @param size
     * @return
     */
    public Result<PageDTO> selectByReceiver(Long id, Integer page, Integer size) {
        Map<String,Object> map = new HashMap<>();

        PageDTO pageDTO = new PageDTO();
        Integer totalPage = -1;
        Integer count = notificationMapper.selectCountByReceiver(id);
        map.put("unRead",count);
        if (count > 0) {
            if (count % size == 0) {
                totalPage = count / size;
            } else {
                totalPage = count / size + 1;
            }
        }else{
            return Result.error(CustomErrorCode.No_NOTICE);
        }
        pageDTO.setPageDTO(totalPage, page); //处理数据
        Integer offset = size * (page - 1); //每页5条数据
        List<NotificationDTO> list = notificationMapper.selectByReceiver(id,size, offset);
        pageDTO.setData(list);
        map.put("pageDTO",pageDTO);
        return Result.ok(map);
    }
}
