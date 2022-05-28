package com.cafe24.shkim30.library;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@SpringBootTest
public class libEncryptTest {

    @Test
    public void test(){
        String testStr = "TEST";
        String encryptStr = libEncrypt.encrypt_AES(testStr.getBytes(), libEncrypt.AES_KEY.getBytes());
        String decryptStr = libEncrypt.decrypt_AES(encryptStr.getBytes(),libEncrypt.AES_KEY.getBytes() );

        Assertions.assertThat(testStr).isEqualTo(decryptStr);
    }
}