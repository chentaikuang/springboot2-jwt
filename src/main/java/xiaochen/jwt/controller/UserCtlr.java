package xiaochen.jwt.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import xiaochen.jwt.common.Const;
import xiaochen.jwt.common.RespRst;
import xiaochen.jwt.common.StatusCodeEnum;
import xiaochen.jwt.req.UserReq;
import xiaochen.jwt.util.MyStrUtil;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import java.util.Calendar;
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

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public RespRst register(@RequestBody() UserReq userReq) throws ServletException {
        // save new User
        userMap.put(userReq.getName(), userReq.getPsw());
        RespRst result = new RespRst(StatusCodeEnum.SUCCESS);
        return result;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public RespRst login(@RequestBody() UserReq userReq) throws ServletException {

        RespRst result = null;
        // Create Twt token,格式为XXXX.XXXX.XXXX的字符串
        boolean nameEq = userMap.containsKey(userReq.getName());
        boolean pwsEq = userMap.get(userReq.getName()).equals(userReq.getPsw());

        if (nameEq && pwsEq) {
            String jwtToken = Jwts.builder().setSubject(userReq.getName()).claim(Const.ROLES_NAME_STR, Const.ROLES_GUEST).setIssuedAt(new Date())
                    .setExpiration(atTomorrow()).signWith(SignatureAlgorithm.HS256, Const.JWT_KEY).compact();
            result = new RespRst(StatusCodeEnum.SUCCESS, jwtToken);
        } else {
            result = new RespRst(StatusCodeEnum.NO_AUTH);
        }
        return result;
    }

    private Date atTomorrow() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, 1);
//        calendar.add(Calendar.SECOND,20);
        return calendar.getTime();
    }
}
