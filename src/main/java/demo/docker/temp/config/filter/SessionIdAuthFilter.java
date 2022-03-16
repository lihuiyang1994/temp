package demo.docker.temp.config.filter;

import com.alibaba.fastjson.JSONObject;
import demo.docker.temp.dto.MessageDto;
import demo.docker.temp.exception.UserValidateException;
import demo.docker.temp.util.Result;
import demo.docker.temp.util.StringUtil;
import org.springframework.core.log.LogMessage;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * lihui
 * 2022/3/16
 * SessionIdAuthFilter
 */
public class SessionIdAuthFilter implements Filter {


    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/**",
            "POST");

    private RedisTemplate<String, String> template;

    private static final String SESSION_ID = "sessionId";

    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        String qos = request.getParameter("qos");
        String tels = request.getParameter("tels");
        String userName = request.getParameter("userName");
        String sessionId = request.getParameter("sessionId");

        MessageDto messageDto = new MessageDto(tels, qos, userName, sessionId);
        if(messageDto.validate() && template.hasKey(sessionId)) {
            return new AnonymousAuthenticationToken(sessionId, messageDto, AuthorityUtils.createAuthorityList("ALL"));
        }
        return null;
    }



    public RedisTemplate<String, String> getTemplate() {
        return template;
    }

    public void setTemplate(RedisTemplate<String, String> template) {
        this.template = template;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if(DEFAULT_ANT_PATH_REQUEST_MATCHER.matches(request)) {
            Authentication authentication = attemptAuthentication(request, response);
            if (authentication != null) {
                //取到了用户数据
                successfulAuthentication(request, response, chain , authentication);
                chain.doFilter(request, response);
                return;
            }
        }
        response.setStatus(400);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(JSONObject.toJSONString(Result.BAD_REQUEST));
    }

    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authResult);
        SecurityContextHolder.setContext(context);
    }
}
