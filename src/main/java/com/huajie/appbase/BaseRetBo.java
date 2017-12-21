package com.huajie.appbase;

import lombok.Data;

/**
 * Created by fangxing on 17-7-3.
 */
@Data
public class BaseRetBo {

    private int retCode;
    private String message;
    private Object data;

    public BaseRetBo setBaseRetBo(int retCode, String message){
        BaseRetBo retBo = new BaseRetBo();
        retBo.setRetCode(retCode);
        retBo.setMessage(message);
        return retBo;
    }

    public BaseRetBo setBaseRetBo(int retCode, String message, Object data){
        BaseRetBo retBo = new BaseRetBo();
        retBo.setRetCode(retCode);
        retBo.setMessage(message);
        retBo.setData(data);
        return retBo;
    }

    public BaseRetBo (){
    }

    public BaseRetBo (int retCode, String message, Object data){
        this.retCode = retCode;
        this.message = message;
        this.data = data;
    }
}
