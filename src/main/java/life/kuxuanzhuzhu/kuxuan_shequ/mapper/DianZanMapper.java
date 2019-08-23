package life.kuxuanzhuzhu.kuxuan_shequ.mapper;

import life.kuxuanzhuzhu.kuxuan_shequ.model.DianZan;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 邓鑫鑫
 * @date 2019年08月21日 23:52:18
 * @Description
 */
@Mapper
public interface DianZanMapper {

    Integer insert(DianZan dianZan);

    Integer update(DianZan dianZan);

    DianZan selectByCommentIdAndUserId(DianZan dianZan);
}
