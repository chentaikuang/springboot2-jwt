package xiaochen.jwt.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import xiaochen.jwt.common.Const;
import xiaochen.jwt.req.UserReq;
import xiaochen.jwt.result.RespRst;

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

    static ConcurrentHashMap userMap = new ConcurrentHashMap();
    static {
        //init test User
        userMap.put("ctk","123456");
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public RespRst register(@RequestBody() UserReq userReq) throws ServletException {
        // save new User
        userMap.put(userReq.getName(), userReq.getPsw());
        RespRst result = new RespRst("200", "ok", "");
        return result;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public RespRst login(@RequestBody() UserReq userReq) throws ServletException {

        RespRst result = null;
        // Create Twt token,格式为XXXX.XXXX.XXXX的字符串
        boolean nameEq = userMap.containsKey(userReq.getName());
        boolean pwsEq = userMap.get(userReq.getName()).equals(userReq.getPsw());

        if (nameEq && pwsEq) {
            String jwtToken = Jwts.builder().setSubject(userReq.getName()).claim("roles", "guest").setIssuedAt(new Date())
                    .setExpiration(atTomorrow()).signWith(SignatureAlgorithm.HS256, Const.JWT_KEY).compact();
            result = new RespRst("200", "ok", jwtToken);
        } else {
            result = new RespRst("304", "no auth", "");
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
