package xiaochen.jwt.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import xiaochen.jwt.common.Const;
import xiaochen.jwt.common.RespRst;
import xiaochen.jwt.common.StatusCodeEnum;
import xiaochen.jwt.req.UserReq;
import xiaochen.jwt.util.DateUtil;
import xiaochen.jwt.util.MyStrUtil;
import xiaochen.jwt.util.TokenConvertUtil;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.validation.Valid;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chentaikuang
 */
@RestController
@RequestMapping("/user")
public class UserCtlr {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    static ConcurrentHashMap userMap = new ConcurrentHashMap();

    @Value("${white.list.user}")
    private String whiteList;

    @PostConstruct
    private void initWhiteListUser() {
        if (MyStrUtil.isNotBlank(whiteList)) {
            String[] userArr = whiteList.trim().split(",");
            for (String str : userArr) {
                String[] namePws = str.split("\\|");
                if (namePws.length == 2) {
                    userMap.put(namePws[0], namePws[1]);
                }
            }
        }
        logger.info("init default user --------------> {}", userMap);
    }

    /**
     * http://localhost:8080/user/register
     * @param userReq
     * @return
     * @throws ServletException
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public RespRst register(@RequestBody() UserReq userReq) throws ServletException {
        // save new User
        userMap.put(userReq.getName(), userReq.getPsw());
        RespRst result = new RespRst(StatusCodeEnum.SUCCESS);
        return result;
    }

    /**
     * http://localhost:8080/user/login
     * @param userReq
     * @return
     * @throws ServletException
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public RespRst login(@Valid @RequestBody() UserReq userReq) throws ServletException {

        RespRst result = null;
        // Create Twt token,格式为XXXX.XXXX.XXXX的字符串
        boolean nameEq = userMap.containsKey(userReq.getName());
        Assert.isTrue(nameEq, StatusCodeEnum.NO_EXIST_USER.getMsg());
        boolean pwsEq = userMap.get(userReq.getName()).equals(userReq.getPsw());

        if (isExistUser(nameEq, pwsEq)) {
            String jwtToken = Jwts.builder().setSubject(userReq.getName()).claim(Const.ROLES_NAME_STR, Const.ROLES_GUEST).setIssuedAt(new Date())
                    .setExpiration(DateUtil.atTomorrow()).signWith(SignatureAlgorithm.HS256, Const.JWT_SIGN_KEY).compact();
            result = new RespRst(StatusCodeEnum.SUCCESS, TokenConvertUtil.getShortToken(jwtToken));
        } else {
            result = new RespRst(StatusCodeEnum.NO_AUTH);
        }
        return result;
    }

    private boolean isExistUser(boolean nameEq, boolean pwsEq) {
        return nameEq && pwsEq;
    }

}
