package cz.ucl.logic.data.mappers.definition;

import cz.ucl.logic.app.entities.definition.ICategory;
import cz.ucl.logic.data.dao.CategoryDAO;

import java.util.List;

public interface ICategoryMapper {
    ICategory mapFromDao(CategoryDAO dao);

    ICategory mapFromDao(CategoryDAO dao, boolean preventDeepMap);

    List<ICategory> mapFromDaoList(List<CategoryDAO> daoList);

    List<ICategory> mapFromDaoList(List<CategoryDAO> daoList, boolean preventDeepMap);

    CategoryDAO mapToDao(ICategory entity);

    CategoryDAO mapToDao(ICategory entity, boolean preventDeepMap);

    List<CategoryDAO> mapToDaoList(List<ICategory> entityList);

    List<CategoryDAO> mapToDaoList(List<ICategory> entityList, boolean preventDeepMap);
}
