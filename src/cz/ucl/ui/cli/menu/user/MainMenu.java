package cz.ucl.ui.cli.menu.user;

import cz.ucl.logic.app.entities.definition.ITask;
import cz.ucl.ui.cli.menu.Menu;
import cz.ucl.ui.cli.menu.MenuOption;
import cz.ucl.ui.definition.IUserInterface;
import cz.ucl.ui.definition.menu.IMenu;
import cz.ucl.ui.definition.menu.MenuType;

public class MainMenu extends Menu {
    public MainMenu(IUserInterface ui, String title) {
        super(null, "main_menu", title);

        this.ui = ui;
        this.logic = ui.getLogic();
    }

    @Override
    protected void build() {
        this.clearOptions();
        boolean isLoggedIn = this.logic.isUserLoggedIn();
        if (isLoggedIn) {
            this.buildLoggedInMenu();
        } else {
            this.buildNotLoggedInMenu();
        }
    }

    @Override
    public void initialize() {
        this.build();
    }

    @Override
    public boolean isSystemMenu() {
        return false;
    }

    @Override
    public MenuType getType() {
        return MenuType.USER;
    }

    private void buildNotLoggedInMenu() {
        setDescription(
                "Abyste mohli aplikaci používat, je nutné se nejprve přihlásit.\n\n" +
                        "Pokud ještě nemáte svůj uživatelský účet, je možné se registrovat."
        );

        IMenu loginMenu = ui.getMenuFactory().createLoginFormMenu(this);
        IMenu registerMenu = ui.getMenuFactory().createRegistrationFormMenu(this);
        IMenu quitMenu = ui.getMenuFactory().createQuitMenu(this);

        addOption(new MenuOption(nextOptionNumber(), loginMenu));
        addOption(new MenuOption(nextOptionNumber(), registerMenu));
        addOption(new MenuOption(nextOptionNumber(), quitMenu));
    }

    private void buildLoggedInMenu() {

        setDescription("Jste úspěšně přihlášen.");

        IMenu allTasksMenu = ui.getMenuFactory().createTaskListMenu(this);
        IMenu taskMenu = ui.getMenuFactory().createTasksMenu(this);
        IMenu settingsMenu = ui.getMenuFactory().createSettingsMenu(this);
        IMenu logoutMenu = ui.getMenuFactory().createLogoutMenu(this);
        IMenu quitMenu = ui.getMenuFactory().createQuitMenu(this);

        addOption(new MenuOption(nextOptionNumber(), allTasksMenu));
        addOption(new MenuOption(nextOptionNumber(), taskMenu));
        addOption(new MenuOption(nextOptionNumber(), settingsMenu));
        addOption(new MenuOption(nextOptionNumber(), logoutMenu));
        addOption(new MenuOption(nextOptionNumber(), quitMenu));
    }
}
