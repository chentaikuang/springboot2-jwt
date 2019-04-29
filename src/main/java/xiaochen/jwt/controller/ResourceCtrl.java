package xiaochen.jwt.controller;

import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import xiaochen.jwt.common.Const;
import xiaochen.jwt.result.RespRst;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.Random;

/**
 * @author chentaikuang
 */
@RestController
@RequestMapping("/resource")
public class ResourceCtrl {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private HttpServletRequest request;

    @RequestMapping(value = "/data", method = RequestMethod.POST)
    public RespRst data() throws ServletException {
        Claims claims = (Claims) request.getAttribute(Const.USER_CLAIMS_ATTR_KEY);
        logger.info("claims:{}", claims.toString());
        String sub = claims.getSubject();
        RespRst result = new RespRst("200", "welcome", sub);
        return result;
    }
}
