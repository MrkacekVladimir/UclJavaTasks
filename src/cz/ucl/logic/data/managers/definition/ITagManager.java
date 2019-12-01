package cz.ucl.logic.data.managers.definition;

import cz.ucl.logic.app.entities.definition.ITag;
import cz.ucl.logic.app.entities.definition.IUser;

public interface ITagManager {
    ITag[] getAllTagsForUser(IUser user);
    ITag getTagByIdForUser(int tagId, IUser user);
    void createTag(ITag tag);
    void updateTag(ITag tag);
    void deleteTagByIdForUser(int tagId, IUser user);
}
