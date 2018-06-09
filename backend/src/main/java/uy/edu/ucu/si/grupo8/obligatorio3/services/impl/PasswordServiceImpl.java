package uy.edu.ucu.si.grupo8.obligatorio3.services.impl;

import org.springframework.security.crypto.codec.Hex;
import org.springframework.stereotype.Service;
import uy.edu.ucu.si.grupo8.obligatorio3.services.PasswordService;
import uy.edu.ucu.si.grupo8.obligatorio3.transport.PasswordPawnedRestClient;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

@Service
public class PasswordServiceImpl implements PasswordService {

    public static final String SHA_1_HASHING_ALGORITHm = "SHA-1";

    final private PasswordPawnedRestClient passwordPawnedRestClient;

    public PasswordServiceImpl(final PasswordPawnedRestClient passwordPawnedRestClient) {
        this.passwordPawnedRestClient = passwordPawnedRestClient;
    }

    @Override
    public String getSHA1HashedString(final String password) throws NoSuchAlgorithmException {
        final MessageDigest messageDigest = MessageDigest.getInstance(SHA_1_HASHING_ALGORITHm);
        final byte[] hash = messageDigest.digest(
                password.getBytes(StandardCharsets.UTF_8));
        return new String(Hex.encode(hash));
    }

    @Override
    public boolean isPasswordPawned(final String password) throws NoSuchAlgorithmException {
        final String sha1HashedString = getSHA1HashedString(password);
        final Map<String, Integer> passwordPawnedResponse = passwordPawnedRestClient.getPasswordPawnedResponse(sha1HashedString);
        final String sha1HashedStringSuffix = sha1HashedString.substring(5).toUpperCase();
        return passwordPawnedResponse.containsKey(sha1HashedStringSuffix);
    }

    @Override
    public Map<String, Integer> getAllPawnedPasswordsSuffixMap(final String password) throws NoSuchAlgorithmException {
        final String hashedPassword = getSHA1HashedString(password);
        return passwordPawnedRestClient.getPasswordPawnedResponse(hashedPassword);
    }


}
