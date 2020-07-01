package com.zhangyongsic.framework.shiro.token;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.shiro.authc.AuthenticationToken;


/**
 * @author fanchao
 */
@Data
@ToString
@NoArgsConstructor
public class JwtAuthenticationToken implements AuthenticationToken {

    private static final long serialVersionUID = -7026139111942461216L;

    private String token;


    public JwtAuthenticationToken(String token) {
        this.token = token;
    }

    public Object getPrincipal() {
        return this.token;
    }

    public Object getCredentials() {
        return this.token;
    }
}