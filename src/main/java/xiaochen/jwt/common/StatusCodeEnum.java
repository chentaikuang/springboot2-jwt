package xiaochen.jwt.common;

public enum  StatusCodeEnum {

    SUCCESS(200,"success"),NO_AUTH(400003,"no auth"),NO_AUTH_HEADER(400004,"no auth header");


    private int code;
    private String msg;

    StatusCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
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

}
