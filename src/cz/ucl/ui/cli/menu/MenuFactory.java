package cz.ucl.ui.cli.menu;

import cz.ucl.logic.IAppLogic;
import cz.ucl.logic.app.entities.definition.ICategory;
import cz.ucl.logic.app.entities.definition.ITag;
import cz.ucl.logic.app.entities.definition.ITask;
import cz.ucl.ui.cli.forms.FormField;
import cz.ucl.ui.cli.menu.system.BackMenu;
import cz.ucl.ui.cli.menu.system.FillFormMenu;
import cz.ucl.ui.cli.menu.system.LogoutMenu;
import cz.ucl.ui.cli.menu.system.QuitMenu;
import cz.ucl.ui.cli.menu.user.MainMenu;
import cz.ucl.ui.cli.menu.user.TaskListMenu;
import cz.ucl.ui.definition.IUserInterface;
import cz.ucl.ui.definition.forms.FormFieldType;
import cz.ucl.ui.definition.menu.IMenu;
import cz.ucl.ui.definition.menu.IMenuFactory;
import cz.ucl.ui.definition.menu.MenuType;
import cz.ucl.ui.definition.views.ICategoryView;
import cz.ucl.ui.definition.views.ITagView;
import cz.ucl.ui.definition.views.ITaskView;

public class MenuFactory implements IMenuFactory {
    @Override
    public IMenu createMainMenu(IUserInterface ui) {
        return new MainMenu(ui, "Hlavní nabídka");
    }

    @Override
    public IMenu createQuitMenu(IMenu parentMenu) {
        return new QuitMenu(parentMenu, "Ukončit aplikaci");
    }

    @Override
    public IMenu createBackMenu(IMenu parentMenu) {
        return new BackMenu(parentMenu, "Zpět");
    }

    @Override
    public IMenu createFillFormMenu(IMenu parentMenu) {
        return new FillFormMenu(parentMenu, "Vyplnit formulář");
    }

    @Override
    public IMenu createLoginFormMenu(IMenu parentMenu) {
        return new FormMenu(parentMenu, "login", "Přihlásit se") {

            @Override
            public MenuType getType() {
                return MenuType.USER;
            }

            @Override
            protected void defineForm() {
                addFormField(new FormField("email", "E-Mail", FormFieldType.TEXTUAL));
                addFormField(new FormField("password", "Heslo", FormFieldType.SECURE));
            }

            @Override
            protected void build() {
                setDescription("Pro přihlášení je třeba zadat uživatelské jméno a heslo.");

                IMenu backMenu = ui.getMenuFactory().createBackMenu(this);
                IMenu fillMenu = ui.getMenuFactory().createFillFormMenu(this);

                addOption(new MenuOption(nextOptionNumber(), backMenu));
                addOption(new MenuOption(nextOptionNumber(), fillMenu));
            }
        };
    }

    @Override
    public IMenu createRegistrationFormMenu(IMenu parentMenu) {
        return new FormMenu(parentMenu, "register", "Registrovat se") {

            @Override
            public MenuType getType() {
                return MenuType.USER;
            }

            @Override
            protected void defineForm() {
                addFormField(new FormField("email", "E-Mail", FormFieldType.TEXTUAL));
                addFormField(new FormField("username", "Uživatelské jméno", FormFieldType.TEXTUAL));
                addFormField(new FormField("password", "Heslo", FormFieldType.SECURE));
            }

            @Override
            protected void build() {
                setDescription("Pro registraci je třeba zadat e-mail, uživatelské jméno a heslo.");

                IMenu backMenu = ui.getMenuFactory().createBackMenu(this);
                IMenu fillMenu = ui.getMenuFactory().createFillFormMenu(this);

                addOption(new MenuOption(nextOptionNumber(), backMenu));
                addOption(new MenuOption(nextOptionNumber(), fillMenu));
            }
        };
    }

    @Override
    public IMenu createLogoutMenu(IMenu parentMenu) {
        return new LogoutMenu(parentMenu, "Odhlásit se");
    }

    //region Settings

    @Override
    public IMenu createSettingsMenu(IMenu parentMenu) {
        return new Menu(parentMenu, "settings", "Nastavení") {
            @Override
            protected void build() {
                setDescription(
                        "Zde můžete upravovat svoje štítky a kategorie."
                );

                IAppLogic logic = ui.getLogic();

                IMenu tagMenu = ui.getMenuFactory().createTagsMenu(this);
                IMenu categoryMenu = ui.getMenuFactory().createCategoriesMenu(this);
                IMenu userMenu = ui.getMenuFactory().createUserMenu(this);

                IMenu backMenu = ui.getMenuFactory().createBackMenu(this);

                addOption(new MenuOption(nextOptionNumber(), tagMenu));
                addOption(new MenuOption(nextOptionNumber(), categoryMenu));
                addOption(new MenuOption(nextOptionNumber(), userMenu));
                addOption(new MenuOption(nextOptionNumber(), backMenu));
            }

            @Override
            public MenuType getType() {
                return MenuType.USER;
            }
        };
    }

    //endregion


    //region Tasks
    @Override
    public IMenu createTasksMenu(IMenu parentMenu) {
        return new Menu(parentMenu, "tasks", "Administrace úkolů") {
            @Override
            protected void build() {
                setDescription(
                        "Zde můžete pracovat svoje úkoly."
                );

                IAppLogic logic = ui.getLogic();
                IMenuFactory menuFactory = ui.getMenuFactory();

                IMenu tasksListMenu = menuFactory.createTaskListMenu(this);
                IMenu createTaskMenu = menuFactory.createNewTaskMenu(this);
                IMenu backMenu = menuFactory.createBackMenu(this);

                addOption(new MenuOption(nextOptionNumber(), tasksListMenu));
                addOption(new MenuOption(nextOptionNumber(), createTaskMenu));
                addOption(new MenuOption(nextOptionNumber(), backMenu));
            }

            @Override
            public MenuType getType() {
                return MenuType.USER;
            }
        };
    }

    @Override
    public IMenu createTaskListMenu(IMenu parentMenu) {
        return new TaskListMenu(parentMenu, "taskList");
    }

    @Override
    public IMenu createTaskDetailMenu(IMenu parentMenu, int taskId) {
        return new Menu(parentMenu, "taskDetail", String.format("Detail úkolu č. %d", taskId)) {

            @Override
            public MenuType getType() {
                return MenuType.USER;
            }

            @Override
            protected void build() {
                IAppLogic logic = ui.getLogic();

                ITask task = logic.getTaskById(taskId);
                setDescription(String.format("Upravujete úkol #%d - '%s'", task.getId(), task.getTitle()));

                ITaskView view = ui.getTaskView();
                setDescription(view.formatTask(task));

                IMenu updateMenu = ui.getMenuFactory().createUpdateTaskMenu(this, taskId);
                IMenu deleteMenu = ui.getMenuFactory().createDeleteTaskMenu(this, taskId);
                IMenu backMenu = ui.getMenuFactory().createBackMenu(this);

                addOption(new MenuOption(nextOptionNumber(), updateMenu));
                addOption(new MenuOption(nextOptionNumber(), deleteMenu));
                addOption(new MenuOption(nextOptionNumber(), backMenu));
            }
        };
    }

    @Override
    public IMenu createNewTaskMenu(IMenu parentMenu) {
        return new FormMenu(parentMenu, "newTask", "Nový úkol") {

            @Override
            public MenuType getType() {
                return MenuType.USER;
            }

            @Override
            protected void defineForm() {
                addFormField(new FormField("title", "Titulek", FormFieldType.TEXTUAL));
                addFormField(new FormField("color", "Barva (RGB)", FormFieldType.TEXTUAL));
            }

            @Override
            protected void build() {
                setDescription("Hodláte se přidat nový úkol.");

                IMenu backMenu = ui.getMenuFactory().createBackMenu(this);
                IMenu fillMenu = ui.getMenuFactory().createFillFormMenu(this);

                addOption(new MenuOption(nextOptionNumber(), backMenu));
                addOption(new MenuOption(nextOptionNumber(), fillMenu));
            }
        };
    }

    @Override
    public IMenu createUpdateTaskMenu(IMenu parentMenu, int taskId) {
        return new FormMenu(parentMenu, "updateTask", String.format("Úprava úkolu - #%d", taskId)) {

            @Override
            public MenuType getType() {
                return MenuType.USER;
            }

            @Override
            protected void defineForm() {
                addFormField(new FormField("title", "Titulek", FormFieldType.TEXTUAL));
                addFormField(new FormField("color", "Barva (RGB)", FormFieldType.TEXTUAL));
            }

            @Override
            protected void build() {
                setDescription("Hodláte se přidat nový úkol.");

                IMenu backMenu = ui.getMenuFactory().createBackMenu(this);
                IMenu fillMenu = ui.getMenuFactory().createFillFormMenu(this);

                addOption(new MenuOption(nextOptionNumber(), backMenu));
                addOption(new MenuOption(nextOptionNumber(), fillMenu));
            }
        };
    }

    @Override
    public IMenu createDeleteTaskMenu(IMenu parentMenu, int taskId) {
        return new FormMenu(parentMenu, "deleteTask", "Smazat úkol") {

            @Override
            public MenuType getType() {
                return MenuType.USER;
            }

            @Override
            protected void defineForm() {
                addFormField(new FormField("id", "Identifier", FormFieldType.HIDDEN, false, Integer.toString(taskId)));
            }

            @Override
            protected void build() {
                setDescription("Hodláte si smazat úkol.");

                IMenu backMenu = ui.getMenuFactory().createBackMenu(this);
                IMenu fillMenu = ui.getMenuFactory().createFillFormMenu(this);

                addOption(new MenuOption(nextOptionNumber(), backMenu));
                addOption(new MenuOption(nextOptionNumber(), fillMenu));
            }
        };
    }

    //endregion


    //region Categories

    @Override
    public IMenu createCategoriesMenu(IMenu parentMenu) {
        return new Menu(parentMenu, "categories", "Administrace kategorií") {
            @Override
            protected void build() {
                setDescription(
                        "Zde můžete pracovat se svými kategoriemi."
                );

                IAppLogic logic = ui.getLogic();
                IMenuFactory menuFactory = ui.getMenuFactory();

                IMenu categoryListMenu = menuFactory.createCategoryListMenu(this);
                IMenu createCategoryMenu = menuFactory.createNewCategoryMenu(this);
                IMenu backMenu = menuFactory.createBackMenu(this);

                addOption(new MenuOption(nextOptionNumber(), categoryListMenu));
                addOption(new MenuOption(nextOptionNumber(), createCategoryMenu));
                addOption(new MenuOption(nextOptionNumber(), backMenu));
            }

            @Override
            public MenuType getType() {
                return MenuType.USER;
            }
        };
    }

    @Override
    public IMenu createCategoryListMenu(IMenu parentMenu) {
        return new Menu(parentMenu, "categoryList", "Seznam kategorií") {
            @Override
            protected void build() {
                setDescription(
                        "Zde můžete upravovat váš seznam kategorií."
                );

                ICategoryView view = ui.getCategoryView();
                IMenuFactory menuFactory = ui.getMenuFactory();

                for (ICategory category : this.logic.getAllCategories()) {
                    IMenu detailMenu = menuFactory.createCategoryDetailMenu(this, category.getId());
                    addOption(new MenuOption(category.getId(), detailMenu));
                }

                IMenu backMenu = menuFactory.createBackMenu(this);

                addOption(new MenuOption(nextOptionNumber(), backMenu));
            }

            @Override
            public MenuType getType() {
                return MenuType.USER;
            }
        };
    }

    @Override
    public IMenu createCategoryDetailMenu(IMenu parentMenu, int categoryId) {
        return new Menu(parentMenu, "categoryDetail", String.format("Detail kategorie č. %d", categoryId)) {

            @Override
            public MenuType getType() {
                return MenuType.USER;
            }

            @Override
            protected void build() {
                setDescription("");

                IAppLogic logic = ui.getLogic();

                ICategory category = logic.getCategoryById(categoryId);

                ICategoryView view = ui.getCategoryView();
                setDescription(view.formatCategory(category));

                IMenu updateMenu = ui.getMenuFactory().createUpdateCategoryMenu(this, category.getId());
                IMenu deleteMenu = ui.getMenuFactory().createDeleteCategoryMenu(this, category.getId());
                IMenu backMenu = ui.getMenuFactory().createBackMenu(this);

                addOption(new MenuOption(nextOptionNumber(), updateMenu));
                addOption(new MenuOption(nextOptionNumber(), deleteMenu));
                addOption(new MenuOption(nextOptionNumber(), backMenu));
            }
        };
    }

    @Override
    public IMenu createNewCategoryMenu(IMenu parentMenu) {
        return new FormMenu(parentMenu, "newCategory", "Nová kategorie") {

            @Override
            public MenuType getType() {
                return MenuType.USER;
            }

            @Override
            protected void defineForm() {
                addFormField(new FormField("title", "Titulek", FormFieldType.TEXTUAL));
                addFormField(new FormField("color", "Barva (RGB)", FormFieldType.TEXTUAL));
            }

            @Override
            protected void build() {
                setDescription("Hodláte se přidat novou kategorii.");

                IMenu backMenu = ui.getMenuFactory().createBackMenu(this);
                IMenu fillMenu = ui.getMenuFactory().createFillFormMenu(this);

                addOption(new MenuOption(nextOptionNumber(), backMenu));
                addOption(new MenuOption(nextOptionNumber(), fillMenu));
            }
        };
    }

    @Override
    public IMenu createUpdateCategoryMenu(IMenu parentMenu, int categoryId) {
        return new FormMenu(parentMenu, "updateCategory", "Upravit kategorii") {

            @Override
            public MenuType getType() {
                return MenuType.USER;
            }

            @Override
            protected void defineForm() {
                addFormField(new FormField("id", "Identifier", FormFieldType.HIDDEN, false, Integer.toString(categoryId)));
                addFormField(new FormField("title", "Titulek", FormFieldType.TEXTUAL));
                addFormField(new FormField("color", "Barva (RGB)", FormFieldType.TEXTUAL));
            }

            @Override
            protected void build() {
                setDescription("Hodláte se upravit kategorii.");

                IMenu backMenu = ui.getMenuFactory().createBackMenu(this);
                IMenu fillMenu = ui.getMenuFactory().createFillFormMenu(this);

                addOption(new MenuOption(nextOptionNumber(), backMenu));
                addOption(new MenuOption(nextOptionNumber(), fillMenu));
            }
        };
    }

    @Override
    public IMenu createDeleteCategoryMenu(IMenu parentMenu, int categoryId) {
        return new FormMenu(parentMenu, "deleteCategory", "Smazat kategorii") {

            @Override
            public MenuType getType() {
                return MenuType.USER;
            }

            @Override
            protected void defineForm() {
                addFormField(new FormField("id", "Identifier", FormFieldType.HIDDEN, false, Integer.toString(categoryId)));
            }

            @Override
            protected void build() {
                setDescription("Hodláte si smazat kategorii.");

                IMenu backMenu = ui.getMenuFactory().createBackMenu(this);
                IMenu fillMenu = ui.getMenuFactory().createFillFormMenu(this);

                addOption(new MenuOption(nextOptionNumber(), backMenu));
                addOption(new MenuOption(nextOptionNumber(), fillMenu));
            }
        };
    }

    //endregion

    //region Tags

    @Override
    public IMenu createTagsMenu(IMenu parentMenu) {
        return new Menu(parentMenu, "tags", "Administrace štítků") {
            @Override
            protected void build() {
                setDescription(
                        "Zde můžete pracovat se svými štítky."
                );

                IAppLogic logic = ui.getLogic();
                IMenuFactory menuFactory = ui.getMenuFactory();

                IMenu tagListMenu = menuFactory.createTagListMenu(this);
                IMenu createTagMenu = menuFactory.createNewTagMenu(this);
                IMenu backMenu = menuFactory.createBackMenu(this);

                addOption(new MenuOption(nextOptionNumber(), tagListMenu));
                addOption(new MenuOption(nextOptionNumber(), createTagMenu));
                addOption(new MenuOption(nextOptionNumber(), backMenu));
            }

            public MenuType getType() {
                return MenuType.USER;
            }
        };
    }

    @Override
    public IMenu createTagListMenu(IMenu parentMenu) {
        return new Menu(parentMenu, "tagList", "Seznam štítků") {
            @Override
            protected void build() {
                setDescription(
                        "Zde můžete upravovat váš seznam štítků."
                );

                setDescription("");

                ITagView view = ui.getTagView();
                IMenuFactory menuFactory = ui.getMenuFactory();

                for (ITag tag : logic.getAllTags()) {
                    IMenu detailMenu = menuFactory.createTagDetailMenu(this, tag.getId());
                    addOption(new MenuOption(tag.getId(), detailMenu));
                }

                IMenu backMenu = menuFactory.createBackMenu(this);

                addOption(new MenuOption(nextOptionNumber(), backMenu));
            }

            @Override
            public MenuType getType() {
                return MenuType.USER;
            }
        };
    }

    @Override
    public IMenu createTagDetailMenu(IMenu parentMenu, int tagId) {
        return new Menu(parentMenu, "tagDetail", String.format("Detail štítku č. %d", tagId)) {

            @Override
            public MenuType getType() {
                return MenuType.USER;
            }

            @Override
            protected void build() {
                IAppLogic logic = ui.getLogic();
                ITag tag = logic.getTagById(tagId);

                setDescription(String.format("Upravujete štítek #%d - '%s'", tag.getId(), tag.getTitle()));

                ITagView view = ui.getTagView();
                setDescription(view.formatTag(tag));

                IMenu updateMenu = ui.getMenuFactory().createUpdateTagMenu(this, tag.getId());
                IMenu deleteMenu = ui.getMenuFactory().createDeleteTagMenu(this, tag.getId());
                IMenu backMenu = ui.getMenuFactory().createBackMenu(this);

                addOption(new MenuOption(nextOptionNumber(), updateMenu));
                addOption(new MenuOption(nextOptionNumber(), deleteMenu));
                addOption(new MenuOption(nextOptionNumber(), backMenu));
            }
        };
    }

    @Override
    public IMenu createNewTagMenu(IMenu parentMenu) {
        return new FormMenu(parentMenu, "newTag", "Nový štítek") {

            @Override
            public MenuType getType() {
                return MenuType.USER;
            }

            @Override
            protected void defineForm() {
                addFormField(new FormField("title", "Titulek", FormFieldType.TEXTUAL));
                addFormField(new FormField("color", "Barva (RGB)", FormFieldType.TEXTUAL));
            }

            @Override
            protected void build() {
                setDescription("Hodláte se přidat nový štítek.");

                IMenu backMenu = ui.getMenuFactory().createBackMenu(this);
                IMenu fillMenu = ui.getMenuFactory().createFillFormMenu(this);

                addOption(new MenuOption(nextOptionNumber(), backMenu));
                addOption(new MenuOption(nextOptionNumber(), fillMenu));
            }
        };
    }

    @Override
    public IMenu createUpdateTagMenu(IMenu parentMenu, int tagId) {
        return new FormMenu(parentMenu, "updateTag", "Upravit") {

            @Override
            public MenuType getType() {
                return MenuType.USER;
            }

            @Override
            protected void defineForm() {
                addFormField(new FormField("id", "Identifier", FormFieldType.HIDDEN, false, Integer.toString(tagId)));
                addFormField(new FormField("title", "Titulek", FormFieldType.TEXTUAL));
                addFormField(new FormField("color", "Barva (RGB)", FormFieldType.TEXTUAL));
            }

            @Override
            protected void build() {
                setDescription("Hodláte se upravit štítek.");

                IMenu backMenu = ui.getMenuFactory().createBackMenu(this);
                IMenu fillMenu = ui.getMenuFactory().createFillFormMenu(this);

                addOption(new MenuOption(nextOptionNumber(), backMenu));
                addOption(new MenuOption(nextOptionNumber(), fillMenu));
            }
        };
    }

    @Override
    public IMenu createDeleteTagMenu(IMenu parentMenu, int tagId) {
        return new FormMenu(parentMenu, "deleteTag", "Smazat štítek") {

            @Override
            public MenuType getType() {
                return MenuType.USER;
            }

            @Override
            protected void defineForm() {
                addFormField(new FormField("id", "Identifier", FormFieldType.HIDDEN, false, Integer.toString(tagId)));
            }

            @Override
            protected void build() {
                setDescription("Hodláte si smazat štítek.");

                IMenu backMenu = ui.getMenuFactory().createBackMenu(this);
                IMenu fillMenu = ui.getMenuFactory().createFillFormMenu(this);

                addOption(new MenuOption(nextOptionNumber(), backMenu));
                addOption(new MenuOption(nextOptionNumber(), fillMenu));
            }
        };
    }

    //endregion


    //region User

    @Override
    public IMenu createUserMenu(IMenu parentMenu) {
        return new Menu(parentMenu, "userMenu", "Administrace účtu") {
            @Override
            protected void build() {
                setDescription(
                        "Zde můžete upravovat nebo smazat svůj uživatelský účet."
                );

                IAppLogic logic = ui.getLogic();

                IMenu changeUserPassword = ui.getMenuFactory().createChangeUserPasswordMenu(this);
                IMenu deleteUserMenu = ui.getMenuFactory().createDeleteUserMenu(this);
                IMenu backMenu = ui.getMenuFactory().createBackMenu(this);

                addOption(new MenuOption(nextOptionNumber(), changeUserPassword));
                addOption(new MenuOption(nextOptionNumber(), deleteUserMenu));
                addOption(new MenuOption(nextOptionNumber(), backMenu));
            }

            @Override
            public MenuType getType() {
                return MenuType.USER;
            }
        };
    }

    @Override
    public IMenu createChangeUserPasswordMenu(IMenu parentMenu) {
        return new FormMenu(parentMenu, "changePassword", "Změnit heslo.") {

            @Override
            public MenuType getType() {
                return MenuType.USER;
            }

            @Override
            protected void defineForm() {
                addFormField(new FormField("password", "Heslo", FormFieldType.TEXTUAL, true));
                addFormField(new FormField("confirmation", "Potvrzení hesla", FormFieldType.TEXTUAL, true));
            }

            @Override
            protected void build() {
                setDescription("Hodláte si změnit uživatelské heslo.");

                IMenu backMenu = ui.getMenuFactory().createBackMenu(this);
                IMenu fillMenu = ui.getMenuFactory().createFillFormMenu(this);

                addOption(new MenuOption(nextOptionNumber(), backMenu));
                addOption(new MenuOption(nextOptionNumber(), fillMenu));
            }
        };
    }

    @Override
    public IMenu createDeleteUserMenu(IMenu parentMenu) {
        return new FormMenu(parentMenu, "deleteUser", "Smazat účet (GDPR).") {

            @Override
            public MenuType getType() {
                return MenuType.USER;
            }

            @Override
            protected void defineForm() {
                addFormField(new FormField("confirmation", "Potvrzení", FormFieldType.HIDDEN));
            }

            @Override
            protected void build() {
                setDescription("Hodláte si kompletně smazat uživatelský účet.");

                IMenu backMenu = ui.getMenuFactory().createBackMenu(this);
                IMenu fillMenu = ui.getMenuFactory().createFillFormMenu(this);

                addOption(new MenuOption(nextOptionNumber(), backMenu));
                addOption(new MenuOption(nextOptionNumber(), fillMenu));
            }
        };
    }

    //endregion


}
