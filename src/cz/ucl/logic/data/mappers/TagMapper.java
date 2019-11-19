package cz.ucl.logic.data.mappers;

import cz.ucl.logic.app.entities.definition.ITag;
import cz.ucl.logic.data.dao.TagDAO;
import cz.ucl.logic.data.mappers.definition.ITagMapper;

import java.util.List;

public class TagMapper implements ITagMapper {
    private MapperFactory factory;

    public TagMapper(MapperFactory factory){
        this.factory = factory;
    }

    @Override
    public ITag mapFromDao(TagDAO dao) {
        return mapFromDao(dao, true);
    }

    @Override
    public ITag mapFromDao(TagDAO dao, boolean preventDeepMap) {
        return null;
    }

    @Override
    public List<ITag> mapFromDaoList(List<TagDAO> daoList) {
        return mapFromDaoList(daoList, true);
    }

    @Override
    public List<ITag> mapFromDaoList(List<TagDAO> daoList, boolean preventDeepMap) {
        return null;
    }

    @Override
    public TagDAO mapToDao(ITag entity) {
        return mapToDao(entity, true);
    }

    @Override
    public TagDAO mapToDao(ITag entity, boolean preventDeepMap) {
        return null;
    }

    @Override
    public List<TagDAO> mapToDaoList(List<ITag> entityList) {
        return mapToDaoList(entityList, true);
    }

    @Override
    public List<TagDAO> mapToDaoList(List<ITag> entityList, boolean preventDeepMap) {
        return null;
    }
}
