package life.kuxuanzhuzhu.kuxuan_shequ.service;

import life.kuxuanzhuzhu.kuxuan_shequ.Exception.CustomErrorCode;
import life.kuxuanzhuzhu.kuxuan_shequ.Exception.CustomException;
import life.kuxuanzhuzhu.kuxuan_shequ.dto.PageDTO;
import life.kuxuanzhuzhu.kuxuan_shequ.dto.QuestionDTO;
import life.kuxuanzhuzhu.kuxuan_shequ.dto.Result;
import life.kuxuanzhuzhu.kuxuan_shequ.mapper.QuestionMapper;
import life.kuxuanzhuzhu.kuxuan_shequ.mapper.UserMapper;
import life.kuxuanzhuzhu.kuxuan_shequ.model.Question;
import life.kuxuanzhuzhu.kuxuan_shequ.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 邓鑫鑫
 * @date 2019年07月24日 09:35:05
 * @Description
 */
@Service
public class QuestionService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    public Result<PageDTO> select(Integer page, Integer size, String search) {

        PageDTO pageDTO = new PageDTO();
        Integer totalPage = -1;
        Integer count = questionMapper.count(search); //获取总数据条数
        if (count > 0) {
            if (count % size == 0) {
                totalPage = count / size;
            } else {
                totalPage = count / size + 1;
            }
        }

        pageDTO.setPageDTO(totalPage, page); //处理数据
        Integer offset = size * (page - 1); //每页5条数据
        List<QuestionDTO> questionDTOList = questionMapper.select(size, offset, search);
        for (QuestionDTO questionDTO : questionDTOList) {
            User user = userMapper.findById(questionDTO.getCreator());
            //    BeanUtils.copyProperties(question,questionDTO);这个的作用是可以把question里面的数据赋值给questionDTO里面
            questionDTO.setUser(user);
        }

        pageDTO.setData(questionDTOList);
        return Result.ok(pageDTO);
    }

    public Result<PageDTO> selectByUserId(Long id, Integer page, Integer size) {
        PageDTO pageDTO = new PageDTO();
        Integer totalPage = -1;
        Integer count = questionMapper.countByUserId(id); //获取总数据条数

        if (count > 0) {
            if (count % size == 0) {
                totalPage = count / size;
            } else {
                totalPage = count / size + 1;
            }
        }
        pageDTO.setPageDTO(totalPage, page); //处理数据
        Integer offset = size * (page - 1); //每页5条数据
        List<QuestionDTO> questionDTOList = questionMapper.selectByUserId(id, size, offset);
        for (QuestionDTO questionDTO : questionDTOList) {
            User user = userMapper.findById(questionDTO.getCreator());
            //    BeanUtils.copyProperties(question,questionDTO);这个的作用是可以把question里面的数据赋值给questionDTO里面
            questionDTO.setUser(user);
        }

        pageDTO.setData(questionDTOList);
        return Result.ok(pageDTO);
    }

    public QuestionDTO getById(Long id) {
        QuestionDTO questionDTO = questionMapper.getById(id);
        if (null == questionDTO) {
            throw new CustomException(CustomErrorCode.QUESTION_NOT_FOUND);
        }
        User user = userMapper.findById(questionDTO.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if (null != question.getId()) {
            //更新
            question.setGmtModified(question.getGmtCreate());
            int row = questionMapper.update(question);
            if (row == 0) {
                throw new CustomException(CustomErrorCode.QUESTION_NOT_FOUND);
            }
        } else {
            //创建
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(System.currentTimeMillis());
            question.setViewCount(0);
            question.setLikeCount(0);
            question.setCommentCount(0);
            questionMapper.insert(question);
        }
    }

    /**
     * 跟新阅读数
     *
     * @param id 问题的编号
     */
    public Integer incView(Long id) {
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setId(id);
        questionDTO.setViewCount(1);
        int row = questionMapper.updateViewCountById(questionDTO);
        return row;
    }

    /**
     * 获取相关问题
     * @param id 问题编号
     * @param tag 标签
     * @return
     */
    public List<QuestionDTO> selectByTag(Long id, String tag) {
        tag = tag.replace(",","|");
        List<QuestionDTO> questionDTOList = questionMapper.selectByTag(id, tag);
        if(null == questionDTOList){
            return new ArrayList<>();
        }
        return questionDTOList;
    }
}
