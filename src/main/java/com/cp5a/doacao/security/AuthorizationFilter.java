package com.cp5a.doacao.security;

import com.cp5a.doacao.constants.SecurityConstants;
import com.cp5a.doacao.dto.userdto.LoggedUserDTO;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class AuthorizationFilter extends BasicAuthenticationFilter {

    public AuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {

        if (req.getCookies() == null && req.getHeader(SecurityConstants.HEADER_NAME)==null){
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req,res);
    }

    private String getAuth(HttpServletRequest req){
        String cookie = null;
        String header;

        if(req.getHeader(SecurityConstants.HEADER_NAME)!=null){
            header = req.getHeader(SecurityConstants.HEADER_NAME);

            if(header!=null)
                return header;
        }

        if (req.getCookies() != null){
            cookie = getCookieValue(req, SecurityConstants.AUTH_NAME);

            if(cookie==null)
                return null;
        }

        JsonObject jsonCookie = new JsonObject();
        try {
            jsonCookie =  JsonParser.parseString(URLDecoder.decode(cookie,"UTF-8")).getAsJsonObject();
        }catch (UnsupportedEncodingException  e){
            return null;
        }

        if(jsonCookie.get("token").isJsonNull())
            return null;

        return jsonCookie.get("token").getAsString();

    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest req) throws JwtException{

        String token = getAuth(req);

        if (token != null) {
            Claims user = null;

            try {
                 user = Jwts.parser()
                        .setSigningKey(Keys.hmacShaKeyFor(SecurityConstants.SECRET_KEY.getBytes()))
                        .parseClaimsJws(token)
                        .getBody();
            } catch (ExpiredJwtException e) {
                System.out.println(e);
                return null;
            } catch(Exception e){
                System.out.println(e);
                return null;
            }

            if (user != null) {
                LoggedUserDTO loggedUser  = new LoggedUserDTO();
                loggedUser.setId(user.get("id", Integer.class));
                loggedUser.setSub(user.get("sub", String.class));
                loggedUser.setRole(user.get("role", String.class));
                loggedUser.setExp(user.get("exp", Long.class));
                return new UsernamePasswordAuthenticationToken(loggedUser, null, new ArrayList<>());
            }else{
                return  null;
            }
        }
        return null;
    }

    public static Integer getLoggedUserId(){
        LoggedUserDTO user = (LoggedUserDTO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getId();
    }

    public static String getLoggedUserRole(){
        LoggedUserDTO user = (LoggedUserDTO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getRole();
    }

    private String getCookieValue(HttpServletRequest req, String cookieName) {

        return Arrays.stream(req.getCookies())
                .filter(c -> c.getName().equals(cookieName))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);
    }
}
