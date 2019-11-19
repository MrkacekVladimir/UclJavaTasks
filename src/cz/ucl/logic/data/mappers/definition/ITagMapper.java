package cz.ucl.logic.data.mappers.definition;

import cz.ucl.logic.app.entities.definition.ITag;
import cz.ucl.logic.data.dao.TagDAO;

import java.util.List;

public interface ITagMapper {
    ITag mapFromDao(TagDAO dao);

    ITag mapFromDao(TagDAO dao, boolean preventDeepMap);

    List<ITag> mapFromDaoList(List<TagDAO> daoList);

    List<ITag> mapFromDaoList(List<TagDAO> daoList, boolean preventDeepMap);

    TagDAO mapToDao(ITag entity);

    TagDAO mapToDao(ITag entity, boolean preventDeepMap);

    List<TagDAO> mapToDaoList(List<ITag> entityList);

    List<TagDAO> mapToDaoList(List<ITag> entityList, boolean preventDeepMap);
}
