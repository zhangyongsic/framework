package com.zhangyongsic.framework.lib.rsa;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;


@Data
@ToString
@NoArgsConstructor
public class RsaKeyPair implements Serializable {

    private static final long serialVersionUID = 254234232341231L;

    private String base64PublicKey;
    private String base64PrivateKey;
}
