package cz.ucl.logic.app.services;

import cz.ucl.logic.app.entities.User;
import cz.ucl.logic.app.entities.definition.IUser;
import cz.ucl.logic.app.services.definition.IUserService;
import cz.ucl.logic.exceptions.AlreadyLoggedInException;
import cz.ucl.logic.exceptions.EmailAddressAlreadyUsedException;
import cz.ucl.logic.exceptions.InvalidCredentialsException;
import cz.ucl.logic.exceptions.NotLoggedInException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class UserService implements IUserService {

    private List<IUser> registeredUsers;
    private IUser loggedInUser;

    public UserService() {
        registeredUsers = new ArrayList<>();
    }

    @Override
    public void loginUser(String email, String password) throws AlreadyLoggedInException, InvalidCredentialsException {
        if (this.isUserLoggedIn()) {
            throw new AlreadyLoggedInException("User is already logged in.");
        }

        IUser user = this.registeredUsers.stream()
                .filter((u) -> u.getEmail().equals(email))
                .findFirst()
                .orElse(null);
        if (user == null) {
            throw new InvalidCredentialsException("Invalid credentials.");
        }

        boolean isValid = this.validatePassword(password, user.getPassword());
        if(!isValid){
            throw new InvalidCredentialsException("Invalid credentials.");
        }

        this.loggedInUser = user;
    }

    @Override
    public void logoutUser() throws NotLoggedInException {
        if (!this.isUserLoggedIn()) {
            throw new NotLoggedInException("User is not logged in.");
        }

        this.loggedInUser = null;
    }

    @Override
    public void registerUser(String email, String username, String password) throws EmailAddressAlreadyUsedException {
        IUser user = this.registeredUsers.stream()
                .filter((u) -> u.getEmail().equals(email))
                .findFirst()
                .orElse(null);
        if (user != null) {
            throw new EmailAddressAlreadyUsedException(String.format("Email %s is already in use.", email));
        }

        String securePassword = this.computeSecureString(password);
        User newUser = new User(email, username, securePassword);

        this.registeredUsers.add(newUser);
    }

    @Override
    public boolean isUserLoggedIn() {
        return this.getUserLoggedIn() != null;
    }

    @Override
    public IUser getUserLoggedIn() {
        return this.loggedInUser;
    }

    @Override
    public void destroyUserLoggedIn() throws NotLoggedInException {
        if (!this.isUserLoggedIn()) {
            throw new NotLoggedInException("User is not logged in.");
        }

        this.registeredUsers.remove(this.loggedInUser);
        this.logoutUser();
    }

    //https://howtodoinjava.com/security/how-to-generate-secure-password-hash-md5-sha-pbkdf2-bcrypt-examples/
    private String computeSecureString(String input) {
        String hashtext = input;
        try {
            int iterations = 1000;
            char[] chars = input.toCharArray();
            byte[] salt = getSalt();

            PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = skf.generateSecret(spec).getEncoded();
            hashtext = iterations + ":" + toHex(salt) + ":" + toHex(hash);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return hashtext;
    }

    private byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }

    private String toHex(byte[] array) throws NoSuchAlgorithmException {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if (paddingLength > 0) {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        } else {
            return hex;
        }
    }

    private boolean validatePassword(String originalPassword, String storedPassword) {
        String[] parts = storedPassword.split(":");
        int iterations = Integer.parseInt(parts[0]);
        byte[] salt = fromHex(parts[1]);
        byte[] hash = fromHex(parts[2]);

        PBEKeySpec spec = new PBEKeySpec(originalPassword.toCharArray(), salt, iterations, hash.length * 8);
        byte[] testHash = new byte[0];
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            testHash = skf.generateSecret(spec).getEncoded();
        } catch (Exception e) {
            e.printStackTrace();
        }

        int diff = hash.length ^ testHash.length;
        for (int i = 0; i < hash.length && i < testHash.length; i++) {
            diff |= hash[i] ^ testHash[i];
        }
        return diff == 0;
    }

    private byte[] fromHex(String hex) {
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }
}
