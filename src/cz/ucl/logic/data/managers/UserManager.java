package cz.ucl.logic.data.managers;

import cz.ucl.logic.app.entities.definition.IUser;
import cz.ucl.logic.data.dao.UserDAO;
import cz.ucl.logic.data.managers.definition.IUserManager;
import cz.ucl.logic.data.mappers.MapperFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserManager implements IUserManager {
    private List<UserDAO> userDatabase;
    private MapperFactory mappers;
    private ManagerFactory managers;

    public UserManager(ManagerFactory managers, MapperFactory mappers) {
        this.userDatabase = new ArrayList<>();
        this.mappers = mappers;
        this.managers = managers;
    }

    @Override
    public IUser[] getAllUsers() {
        IUser[] loadedCategories = new IUser[userDatabase.size()];

        for (int i = 0; i < userDatabase.size(); i++) {
            UserDAO dao = userDatabase.get(i);

            loadedCategories[i] = mappers.getUserMapper().mapFromDAODeep(dao);
        }

        return loadedCategories;
    }

    @Override
    public IUser getUserByEmail(String email) {
        for (UserDAO userDAO : userDatabase) {
            if (userDAO.getEmail().equals(email)) {
                return mappers.getUserMapper().mapFromDAODeep(userDAO);
            }
        }

        return null;
    }

    @Override
    public IUser getUserById(int userId) {
        for (UserDAO userDAO : userDatabase) {
            if (userDAO.getId() == userId) {
                return mappers.getUserMapper().mapFromDAODeep(userDAO);
            }
        }

        return null;
    }

    @Override
    public IUser createUser(IUser user) {
        UserDAO dao = mappers.getUserMapper().mapToDAODeep(user);
        dao.setId(this.getNextIdentifier());
        this.userDatabase.add(dao);

        return mappers.getUserMapper().mapFromDAODeep(dao);
    }

    @Override
    public void updateUser(IUser user) {
        int daoIndex = -1;
        for (int i = 0; i < userDatabase.size(); i++) {
            if (userDatabase.get(i).getId() == user.getId()) {
                daoIndex = i;
                break;
            }
        }

        UserDAO newDao = mappers.getUserMapper().mapToDAODeep(user);

        userDatabase.set(daoIndex, newDao);
    }

    @Override
    public void deleteUserById(int userId) {
        int daoIndex = -1;
        for (int i = 0; i < userDatabase.size(); i++) {
            if (userDatabase.get(i).getId() == userId) {
                daoIndex = i;
                break;
            }
        }

        userDatabase.remove(daoIndex);
    }


    private int getNextIdentifier() {
        if(!this.userDatabase.isEmpty()){
            UserDAO lastDao = this.userDatabase.get(this.userDatabase.size() - 1);
            return lastDao.getId();
        }

        return 1;
    }
}
