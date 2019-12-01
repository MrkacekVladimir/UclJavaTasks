package cz.ucl.logic.data.managers;

import cz.ucl.logic.app.entities.definition.ITag;
import cz.ucl.logic.app.entities.definition.IUser;
import cz.ucl.logic.data.dao.TagDAO;
import cz.ucl.logic.data.managers.definition.ITagManager;
import cz.ucl.logic.data.mappers.MapperFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TagManager implements ITagManager {
    /** Keys in the map will be emails of user who owns the tag */
    private Map<String, List<TagDAO>> tagDatabase;
    private MapperFactory mappers;
    private ManagerFactory managers;

    public TagManager(ManagerFactory managers, MapperFactory mappers) {
        this.tagDatabase = new HashMap<>();
        this.mappers = mappers;
        this.managers = managers;
    }

    // TODO
}
