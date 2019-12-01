package cz.ucl.logic.app.services;

import cz.ucl.logic.app.entities.User;
import cz.ucl.logic.app.entities.definition.IUser;
import cz.ucl.logic.app.services.definition.IUserService;
import cz.ucl.logic.data.managers.definition.IUserManager;
import cz.ucl.logic.exceptions.AlreadyLoggedInException;
import cz.ucl.logic.exceptions.EmailAddressAlreadyUsedException;
import cz.ucl.logic.exceptions.InvalidCredentialsException;
import cz.ucl.logic.exceptions.NotLoggedInException;

public class UserService implements IUserService {
    private IUser userLoggedIn;
    private IUserManager manager;

    public UserService(IUserManager manager) {
        this.userLoggedIn = null;
        this.manager = manager;
    }

    private IUser findUserByEmailAndPassword(String email, String password) {
        for (IUser user : manager.getAllUsers()) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                return user;
            }
        }

        return null;
    }

    @Override
    public void loginUser(String email, String password) throws InvalidCredentialsException, AlreadyLoggedInException {
        if (isUserLoggedIn()) {
            throw new AlreadyLoggedInException("You are already logged in, you have to logout before you will login as somebody else!");
        } else {
            IUser user = findUserByEmailAndPassword(email, password);

            if (user != null) {
                userLoggedIn = user;
            } else {
                throw new InvalidCredentialsException("User for provided credentials does not exist!");
            }
        }
    }

    @Override
    public void logoutUser() throws NotLoggedInException {
        if (isUserLoggedIn()) {
            userLoggedIn = null;
        } else {
            throw new NotLoggedInException("You cannot logout, because you are not logged in! You have to login, before you can logout.");
        }
    }

    @Override
    public void registerUser(String email, String username, String password) throws EmailAddressAlreadyUsedException {
        manager.createUser(new User(email, username, password));
    }

    @Override
    public boolean isUserLoggedIn() {
        return userLoggedIn != null;
    }

    @Override
    public IUser getUserLoggedIn() {
        return userLoggedIn;
    }

    @Override
    public void destroyUserLoggedIn() throws NotLoggedInException {
        if (!isUserLoggedIn()) {
            throw new NotLoggedInException("You cannot destroy yourself if you are not logged in, you have to login first.");
        } else {
            // 1. Remove logged in user from the registered users
            manager.deleteUserById(getUserLoggedIn().getId());
            // 2. Logout the logged in user
            logoutUser();
        }
    }
}
