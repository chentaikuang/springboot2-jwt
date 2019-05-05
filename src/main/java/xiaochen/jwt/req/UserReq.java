package xiaochen.jwt.req;

import javax.validation.constraints.NotNull;

/**
 * @author chentaikuang
 */
public class UserReq {

    @NotNull(message = "用户名不可空")
    private String name;
    @NotNull(message = "密码不可空")
    private String psw;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }
}
