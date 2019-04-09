package top.liuliyong.common.exception;

/**
 * @Author liyong.liu
 * @Date 2019/3/30
 **/

import top.liuliyong.common.status.StatusEnum;

/**
 * 抛出自定义异常
 */
public class OperationException extends RuntimeException {
    private Integer rtn;
    private String msg;

    public OperationException(Integer rtn, String msg) {
        this.rtn = rtn;
        this.msg = msg;
    }

    public OperationException(StatusEnum statusEnum) {
        this.rtn = statusEnum.getCode();
        this.msg = statusEnum.getMsg();
    }

    public Integer getRtn() {
        return this.rtn;
    }

    public String getMsg() {
        return this.msg;
    }
}
