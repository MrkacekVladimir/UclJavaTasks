package cz.ucl.logic.data.managers.definition;

import cz.ucl.logic.app.entities.definition.ICategory;
import cz.ucl.logic.app.entities.definition.IUser;

public interface ICategoryManager {
    ICategory[] getAllCategoriesForUser(IUser user);
    ICategory getCategoryByIdForUser(int categoryId, IUser user);
    ICategory createCategory(ICategory category);
    void updateCategory(ICategory category);
    void deleteCategoryByIdForUser(int categoryId, IUser user);
}
