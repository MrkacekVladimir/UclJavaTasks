package cz.ucl.logic.data.mappers;

import cz.ucl.logic.app.entities.definition.ICategory;
import cz.ucl.logic.data.dao.CategoryDAO;
import cz.ucl.logic.data.mappers.definition.ICategoryMapper;

import java.util.List;

public class CategoryMapper implements ICategoryMapper {
    private MapperFactory factory;

    public CategoryMapper(MapperFactory factory){
        this.factory = factory;
    }

    @Override
    public ICategory mapFromDao(CategoryDAO dao) {
        return null;
    }

    @Override
    public List<ICategory> mapFromDaoList(List<CategoryDAO> daoList) {
        return null;
    }

    @Override
    public CategoryDAO mapToDao(ICategory entity) {
        return null;
    }

    @Override
    public List<CategoryDAO> mapToDaoList(List<ICategory> entityList) {
        return null;
    }
}
