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

    // TODO
}
