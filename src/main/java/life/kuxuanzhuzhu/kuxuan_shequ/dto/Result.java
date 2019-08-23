package life.kuxuanzhuzhu.kuxuan_shequ.dto;

import life.kuxuanzhuzhu.kuxuan_shequ.Exception.CustomErrorCode;
import life.kuxuanzhuzhu.kuxuan_shequ.Exception.CustomException;
import life.kuxuanzhuzhu.kuxuan_shequ.enums.CustomDataCode;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 邓鑫鑫
 * @date 2019年07月29日 10:24:09
 * @Description
 */
@Data
public class Result<T> {

    private Integer code;//提示码
    private String message;//提示信息
    private T data;

    public Result() {
    }

    public static Result error(Integer code, String message) {
        Result result = new Result();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    public static Result error(CustomErrorCode customErrorCode) {
        return error(customErrorCode.getCode(), customErrorCode.getMessage());
    }


    public static Result error(CustomException e) {
        return error(e.getCode(), e.getMessage());
    }

    public static Result ok() {
        Result result = new Result();
        result.setCode(200);
        result.setMessage("请求成功");
        return result;
    }


    public static <T> Result ok(T t,CustomErrorCode customErrorCode){
        Result result = new Result();
        result.setCode(customErrorCode.getCode());
        result.setMessage(customErrorCode.getMessage());
        result.setData(t);
        return result;
    }

    public static <T> Result ok(T t){
        Result result = new Result();
        result.setCode(200);
        result.setMessage("请求成功");
        result.setData(t);
        return result;
    }



    /**
     * 消息方法
     * @param customDataCode 公共消息处理类
     * @return
     */
    public static Result data(CustomDataCode customDataCode){
        return error(customDataCode.getCode(),customDataCode.getMessage());
    }



}
