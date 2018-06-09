package uy.edu.ucu.si.grupo8.obligatorio3.services;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

public interface PasswordService {

    Map<String, Integer> getAllPawnedPasswordsSuffixMap(final String password) throws NoSuchAlgorithmException;

    String getSHA1HashedString(final String password) throws NoSuchAlgorithmException;

    boolean isPasswordPawned(final String password) throws NoSuchAlgorithmException;

}