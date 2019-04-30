package xiaochen.jwt.common;

/**
 * @author chentaikuang
 */
public class RespRst<T> {

    private int code;

    private String msg;

    private T data;

    public RespRst(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

//    public RespRst(StatusCodeEnum statusCodeEnum, T data) {
//        this.code = statusCodeEnum.getCode();
//        this.msg = statusCodeEnum.getMsg();
//        this.data = data;
//    }

    public RespRst(StatusCodeEnum statusCodeEnum, T... data) {
        this.code = statusCodeEnum.getCode();
        this.msg = statusCodeEnum.getMsg();
        System.out.println(data);
        System.out.println(data.length);
        if (data != null && data.length > 0) {
            this.data = data[0];
        }
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
