package cz.ucl.ui.definition.menu;

import cz.ucl.logic.app.entities.definition.ICategory;
import cz.ucl.logic.app.entities.definition.ITag;
import cz.ucl.logic.app.entities.definition.ITask;
import cz.ucl.ui.definition.IUserInterface;

public interface IMenuFactory {
    IMenu createMainMenu(IUserInterface ui);
    IMenu createQuitMenu(IMenu parentMenu);
    IMenu createBackMenu(IMenu parentMenu);
    IMenu createFillFormMenu(IMenu parentMenu);

    IMenu createLoginFormMenu(IMenu parentMenu);
    IMenu createRegistrationFormMenu(IMenu parentMenu);
    IMenu createLogoutMenu(IMenu parentMenu);

    //region Settings
    IMenu createSettingsMenu(IMenu parentMenu);
    //endregion

    //region Tasks
    IMenu createTasksMenu(IMenu parentMenu);
    IMenu createTaskListMenu(IMenu parentMenu);
    IMenu createTaskDetailMenu(IMenu parentMenu, int taskId);
    IMenu createNewTaskMenu(IMenu parentMenu);
    IMenu createUpdateTaskMenu(IMenu parentMenu, int taskId);
    IMenu createDeleteTaskMenu(IMenu parentMenu, int taskId);
    //endregion

    //region Categories
    IMenu createCategoriesMenu(IMenu parentMenu);
    IMenu createCategoryListMenu(IMenu parentMenu);
    IMenu createCategoryDetailMenu(IMenu parentMenu, int categoryId);
    IMenu createNewCategoryMenu(IMenu parentMenu);
    IMenu createUpdateCategoryMenu(IMenu parentMenu, int categoryId);
    IMenu createDeleteCategoryMenu(IMenu parentMenu, int categoryId);
    //endregion

    //region Tags
    IMenu createTagsMenu(IMenu parentMenu);
    IMenu createTagListMenu(IMenu parentMenu);
    IMenu createTagDetailMenu(IMenu parentMenu, int tagId);
    IMenu createNewTagMenu(IMenu parentMenu);
    IMenu createUpdateTagMenu(IMenu parentMenu, int tagId);
    IMenu createDeleteTagMenu(IMenu parentMenu, int tagId);
    //endregion

    //region User

    IMenu createUserMenu(IMenu parentMenu);
    IMenu createChangeUserPasswordMenu(IMenu parentMenu);
    IMenu createDeleteUserMenu(IMenu parentMenu);

    //endregion
}
