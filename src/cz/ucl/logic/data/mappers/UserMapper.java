package cz.ucl.logic.data.mappers;

import cz.ucl.logic.app.entities.definition.IUser;
import cz.ucl.logic.data.dao.UserDAO;
import cz.ucl.logic.data.mappers.definition.IUserMapper;

import java.util.List;

public class UserMapper implements IUserMapper {
    private MapperFactory factory;

    public UserMapper(MapperFactory factory){
        this.factory = factory;
    }

    @Override
    public IUser mapFromDao(UserDAO dao) {
        return this.mapFromDao(dao, true);
    }

    @Override
    public IUser mapFromDao(UserDAO dao, boolean preventDeepMap) {
        return null;
    }

    @Override
    public List<IUser> mapFromDaoList(List<UserDAO> daoList) {
        return mapFromDaoList(daoList, true);
    }

    @Override
    public List<IUser> mapFromDaoList(List<UserDAO> daoList, boolean preventDeepMap) {
        return null;
    }

    @Override
    public UserDAO mapToDao(IUser entity) {
        return mapToDao(entity, true);
    }

    @Override
    public UserDAO mapToDao(IUser entity, boolean preventDeepMap) {
        return null;
    }

    @Override
    public List<UserDAO> mapToDaoList(List<IUser> entityList) {
        return mapToDaoList(entityList, true);
    }

    @Override
    public List<UserDAO> mapToDaoList(List<IUser> entityList, boolean preventDeepMap) {
        return null;
    }
}
