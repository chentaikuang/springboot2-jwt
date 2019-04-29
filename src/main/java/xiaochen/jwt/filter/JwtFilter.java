package xiaochen.jwt.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;
import xiaochen.jwt.common.Const;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtFilter extends GenericFilterBean {

    @Override
    public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain)
            throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) req;
        final HttpServletResponse response = (HttpServletResponse) res;

        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            chain.doFilter(req, res);
        }

        final String authHeader = request.getHeader(Const.REQ_TOKEN);
        if (StringUtils.isEmpty(authHeader)) {
            throw new ServletException("no auth header");
        }
        final String token;
        if (authHeader.contains("Bearer")) {
            token = authHeader.substring(7);
        } else {
            token = authHeader;
        }
        final Claims userClaims = Jwts.parser().setSigningKey(Const.JWT_KEY).parseClaimsJws(token).getBody();
        request.setAttribute(Const.USER_CLAIMS_ATTR_KEY, userClaims);

        chain.doFilter(req, res);
    }
}