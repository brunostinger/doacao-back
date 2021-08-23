package com.cp5a.doacao.constants;

public class SecurityConstants {
    public static final String[]  POST_WHITELIST = {
            "/user"
    };
    public static final String[]  PUT_WHITELIST = {
            "/user/recover"
    };
    public static final String[] GET_WHITELIST = {
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/webjars/**",
            "/v3/**",
            "/v2/**",
            "/configuration/ui",
            "/configuration/security",
            "/api/**",
            "/user/organization/list/{page}/{qty}",
            "/user/organization/{id}",
            "/event/{id}",
            "/event/list/{page}/{qty}",
            "/occupationarea/list",
            "/user/organization/list/city",

    };
    public static final String[]  ORG_BLACKLIST= {
            "/transaction/donation/**",
            "/transaction/ticket/**",
    };

    public static final String HEADER_NAME = "Authorization";
    public static final String AUTH_NAME =  "doacao";
    public static final String SECRET_KEY = "TFS>xLa;ErUyXP.Z?jdU<ukJl+4>A<tSo#4G];B/7p-{(1,[+!:>{1KaTe2P4N%xsa812-0-3-99";
    public static final Long EXPIRATION_TIME =  1000L * 60 * 60 *4;
}
