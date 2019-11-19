package cz.ucl.logic.data.mappers.definition;

import cz.ucl.logic.app.entities.definition.IUser;
import cz.ucl.logic.data.dao.UserDAO;

import java.util.List;

public interface IUserMapper {
    IUser mapFromDao(UserDAO dao);

    IUser mapFromDao(UserDAO dao, boolean preventDeepMap);

    List<IUser> mapFromDaoList(List<UserDAO> daoList);

    List<IUser> mapFromDaoList(List<UserDAO> daoList, boolean preventDeepMap);

    UserDAO mapToDao(IUser entity);

    UserDAO mapToDao(IUser entity, boolean preventDeepMap);

    List<UserDAO> mapToDaoList(List<IUser> entityList);

    List<UserDAO> mapToDaoList(List<IUser> entityList, boolean preventDeepMap);
}
