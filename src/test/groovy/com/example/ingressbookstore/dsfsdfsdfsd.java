package com.example.ingressbookstore;

import spock.lang.Specification;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.KeyPairGenerator;

import static com.example.ingressbookstore.model.constant.AuthConstants.KEY_SIZE;
import static com.example.ingressbookstore.model.constant.AuthConstants.RSA;

class dsfsdfsdfsd  {

    public static void main(String[] args) throws Exception {
        new dsfsdfsdfsd().contextLoads();
    }

    void contextLoads() throws  Exception {


        KeyPair keyPair = generateKeyPair();
        Files.write(Paths.get("my_private.cert"), keyPair.getPrivate().getEncoded());
        Files.write(Paths.get("my_public.cert"), keyPair.getPublic().getEncoded());
    }

    public static KeyPair generateKeyPair() throws Exception {
        var keyPairGen = KeyPairGenerator.getInstance(RSA);
        keyPairGen.initialize(KEY_SIZE);
        return keyPairGen.generateKeyPair();
    }

}
