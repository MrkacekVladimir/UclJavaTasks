package cz.ucl.logic.data.managers.definition;

import cz.ucl.logic.app.entities.definition.IUser;

public interface IUserManager {
    IUser[] getAllUsers();
    IUser getUserByEmail(String email);
    IUser getUserById(int userId);
    void createUser(IUser user);
    void updateUser(IUser user);
    void deleteUserById(int userId);
}
