package xiaochen.jwt.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;
import xiaochen.jwt.common.Const;
import xiaochen.jwt.common.StatusCodeEnum;
import xiaochen.jwt.util.TokenConvertUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author chentaikuang
 */
public class JwtFilter extends GenericFilterBean {

    @Override
    public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain)
            throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) req;
        final HttpServletResponse response = (HttpServletResponse) res;

        if (Const.METHOD_TYPE_OPTIONS.equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            chain.doFilter(req, res);
        }

        final String authHeader = request.getHeader(Const.REQ_TOKEN);
        if (StringUtils.isEmpty(authHeader)) {
            throw new ServletException(StatusCodeEnum.NO_AUTH_HEADER.getMsg());
        }
        String shortToken;
        if (authHeader.contains(Const.AUTH_HEADER_PREFIX_BEARER)) {
            shortToken = authHeader.substring(Const.AUTH_HEADER_PREFIX_BEARER.length());
        } else {
            shortToken = authHeader;
        }
        String token = TokenConvertUtil.getLongToken(shortToken);
        Assert.notNull(token, StatusCodeEnum.CONVERT_TOKEN_NULL.getMsg());
        try {
            final Claims userClaims = Jwts.parser().setSigningKey(Const.JWT_KEY).parseClaimsJws(token).getBody();
            request.setAttribute(Const.USER_CLAIMS_ATTR_KEY, userClaims);
        } catch (Exception e) {
            //捕获token已失效，删除token map
            if (e instanceof ExpiredJwtException) {
                int code = TokenConvertUtil.rmToken(shortToken);
                Assert.isTrue(StatusCodeEnum.SUCCESS.getCode() != code, StatusCodeEnum.EXPIRED_EXCEPTION.getMsg());
            }
        }
        chain.doFilter(req, res);
    }
}
