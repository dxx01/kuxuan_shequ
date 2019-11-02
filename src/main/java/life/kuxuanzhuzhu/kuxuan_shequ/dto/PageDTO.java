package life.kuxuanzhuzhu.kuxuan_shequ.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 邓鑫鑫
 * @date 2019年07月24日 13:13:06
 * @Description 分页类
 */
@Data
public class PageDTO<T> {
    private List<T> data;
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;
    private Integer page;
    private List<Integer> pageList = new ArrayList<>();
    private Integer totalPage;


    /**
     *
     * @param totalPage
     * @param page
     */
    public void setPageDTO(Integer totalPage, Integer page) {

        if (page > totalPage) {
            page = totalPage;
        }
        if (page < 1) {
            page = 1;
        }

        this.totalPage = totalPage;
        this.page = page;

        //处理页数集合
        if (-1 != totalPage) {
            pageList.add(page);
            for (int i = 1; i <= 3; i++) {
                if (page - i > 0) {
                    pageList.add(0, page - i);
                }
                if (page + i <= totalPage) {
                    pageList.add(page + i);
                }
            }
        }
        //是否展示上一页
        if (page == 1) {
            showPrevious = false;
        } else {
            showPrevious = true;
        }
        //是否展示下一页
        if (page == totalPage) {
            showNext = false;
        } else {
            showNext = true;
        }

        //是否展示第一页
        if (pageList.contains(1)) {
            showFirstPage = false;
        } else {
            showFirstPage = true;
        }

        //是否展示最后一页
        if (pageList.contains(totalPage)) {
            showEndPage = false;
        } else {
            showEndPage = true;
        }

        if(-1 == totalPage){
            showPrevious = false;
            showNext = false;
            showFirstPage = false;
            showEndPage = false;
            this.page = 1;
        }
    }
}
