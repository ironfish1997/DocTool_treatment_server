package top.liuliyong.common.response;

import lombok.Data;
import top.liuliyong.common.status.StatusEnum;

import java.io.Serializable;

/***
 * 病人操作返回信息Bean
 */
@Data
public class OperationResponse implements Serializable {
    //返回状态
    private int rtn;
    //返回信息
    private String msg;
    //返回数据体
    private Object data;

    public OperationResponse(int rtn, String msg, Object data) {
        this.rtn = rtn;
        this.msg = msg;
        this.data = data;
    }

    public OperationResponse(StatusEnum se) {
        this.rtn = se.getCode();
        this.msg = se.getMsg();
        this.data = null;
    }


}
