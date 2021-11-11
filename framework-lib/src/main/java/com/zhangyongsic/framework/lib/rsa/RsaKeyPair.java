package com.zhangyongsic.framework.lib.rsa;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 *
 * @author zhangyong
 */
@Data
@ToString
@NoArgsConstructor
public class RsaKeyPair implements Serializable {

    private static final long serialVersionUID = 875441665063763097L;

    private String base64PublicKey;
    private String base64PrivateKey;
}
