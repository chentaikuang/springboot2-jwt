package xiaochen.jwt.common;

public class BizException extends RuntimeException {


    private StatusCodeEnum statusCodeEnum;

    public BizException(StatusCodeEnum statusCodeEnum){
        this.statusCodeEnum = statusCodeEnum;
    }

    public StatusCodeEnum getStatusCodeEnum() {
        return statusCodeEnum;
    }

    public void setStatusCodeEnum(StatusCodeEnum statusCodeEnum) {
        this.statusCodeEnum = statusCodeEnum;
    }
}
