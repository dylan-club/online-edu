package com.nicklaus.commonutils;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Result {

    @ApiModelProperty("是否成功")
    private Boolean success;
    @ApiModelProperty("返回码")
    private Integer code;
    @ApiModelProperty("返回信息")
    private String message;
    @ApiModelProperty("返回数据")
    private Map<String, Object> data = new HashMap<String, Object>();

    //构造器私有化
    private Result(){}

    /**
     * 数据返回成功
     * @return
     */
    public static Result ok(){
        Result result = new Result();

        result.setSuccess(true);
        result.setCode(UtilConstants.ResultCode.SUCCESS_CODE);
        result.setMessage(UtilConstants.ResultCode.SUCCESS_MSG);

        return result;
    }

    /**
     * 数据返回失败
     * @return
     */
    public static Result error(){
        Result result = new Result();

        result.setSuccess(false);
        result.setCode(UtilConstants.ResultCode.ERROR_CODE);
        result.setMessage(UtilConstants.ResultCode.ERROR_MSG);

        return result;
    }

    public Result success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public Result message(String message){
        this.setMessage(message);
        return this;
    }

    public Result code(Integer code){
        this.setCode(code);
        return this;
    }

    public Result data(String key, Object value){
        this.data.put(key, value);
        return this;
    }

    public Result data(Map<String, Object> map){
        this.setData(map);
        return this;
    }
}
