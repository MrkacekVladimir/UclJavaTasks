package cz.ucl.ui.cli;

import cz.ucl.logic.app.entities.definition.Color;
import cz.ucl.logic.exceptions.AlreadyLoggedInException;
import cz.ucl.logic.exceptions.EmailAddressAlreadyUsedException;
import cz.ucl.logic.exceptions.InvalidCredentialsException;
import cz.ucl.logic.exceptions.NotLoggedInException;
import cz.ucl.ui.cli.forms.FormManager;
import cz.ucl.ui.cli.menu.MenuFactory;
import cz.ucl.ui.cli.views.*;
import cz.ucl.ui.definition.forms.IForm;
import cz.ucl.ui.definition.forms.IFormManager;
import cz.ucl.ui.definition.menu.IMenu;
import cz.ucl.logic.IAppLogic;
import cz.ucl.ui.definition.menu.IMenuFactory;
import cz.ucl.ui.definition.menu.IMenuOption;
import cz.ucl.ui.definition.menu.MenuType;
import cz.ucl.ui.definition.views.*;

import java.io.Console;
import java.util.*;
import java.util.stream.IntStream;

public class CLI implements ICLI {
    private IMenuFactory menuFactory;
    private IAppLogic logic;

    private ICategoryView categoryView;
    private ITagView tagView;
    private ITaskView taskView;
    private IMenuView menuView;
    private IFormView formView;

    public CLI() {
        menuFactory = new MenuFactory();

        categoryView = new CategoryView();
        tagView = new TagView();
        taskView = new TaskView();
        menuView = new MenuView();
        formView = new FormView();
    }

    @Override
    public void run(IAppLogic logic) {
        this.logic = logic;

        drawOutput(getWelcomeText());

        IMenu currentMenu;
        IMenu nextMenu = getMainMenu();

        do {
            currentMenu = nextMenu;
            currentMenu.initialize();
            nextMenu = handleMenu(currentMenu);
        }
        while (nextMenu != null);
    }

    @Override
    public IAppLogic getLogic() {
        return this.logic;
    }

    @Override
    public IMenuFactory getMenuFactory() {
        return this.menuFactory;
    }

    //region Forms
    @Override
    public IFormManager createFormManagerForMenu(IForm menu) {
        return new FormManager(menu, this);
    }
    //endregion

    //region Prompts
    @Override
    public int promptNumber() {
        Scanner sc = new Scanner(System.in);
        while (!sc.hasNextInt()) sc.next();
        return sc.nextInt();
    }

    @Override
    public String promptString() {
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    @Override
    public String promptSecureString() {
        Console console = System.console();
        if (console != null) {
            return String.valueOf(console.readPassword(""));
        } else { // if secure input is not supported, fallback to the classic one
            return promptString();
        }
    }

    @Override
    public int promptOption(IMenu menu) {
        int[] validOptions = menu.getValidOptionNumbers();
        while (true) {
            int input = this.promptNumber();
            if (this.isValidOption(input, validOptions)) {
                return input;
            }

            this.drawError("Neplatné číslo výběru.");
        }
    }
    //endregion

    //region Logic
    public void invokeAppLogic(IMenu fromMenu, Map<String, String> formData) {
        String identifier = fromMenu.getIdentifier();
        switch (identifier) {
            case "login":
                actionLogin(fromMenu, formData);
                break;
            case "register":
                actionRegister(fromMenu, formData);
                break;
            case "newTask":
                actionCreateNewTask(fromMenu, formData);
                break;
            case "newTag":
                actionCreateNewTag(fromMenu, formData);
                break;
            case "newCategory":
                actionCreateNewCategory(fromMenu, formData);
                break;
            case "updateTag":
                actionUpdateTag(fromMenu, formData);
                break;
            case "updateCategory":
                actionUpdateCategory(fromMenu, formData);
                break;
            case "updateTask":
                actionUpdateTask(fromMenu, formData);
                break;
            case "deleteTag":
                actionDeleteTag(fromMenu, formData);
                break;
            case "deleteCategory":
                actionDeleteCategory(fromMenu, formData);
                break;
            case "deleteTask":
                actionDeleteTask(fromMenu, formData);
                break;
            case "changePassword":
                actionChangePassword(fromMenu, formData);
                break;
            case "deleteUser":
                actionDeleteUser(fromMenu);
                break;
        }
    }

    public void invokeAppLogic(IMenu fromMenu) {
        String identifier = fromMenu.getIdentifier();
        if (identifier.equals("main_menu")) {
            actionDashboard(fromMenu);
        } else if (identifier.equals("logout")) {
            actionLogout(fromMenu);
        }

        // TODO
    }

    @Override
    public IMenu getMainMenu() {
        return this.menuFactory.createMainMenu(this);
    }

    @Override
    public String getWelcomeText() {
        return "Vítejte v aplikaci Úkolovník 1.0!";
    }

    @Override
    public ICategoryView getCategoryView() {
        return this.categoryView;
    }

    @Override
    public ITagView getTagView() {
        return this.tagView;
    }

    @Override
    public ITaskView getTaskView() {
        return this.taskView;
    }

    @Override
    public IFormView getFormView() {
        return this.formView;
    }

    @Override
    public IMenuView getMenuView() {
        return this.menuView;
    }
    //endregion

    //region Actions
    private void actionDashboard(IMenu fromMenu) {
        if (logic.isUserLoggedIn()) {
            drawMessage("Jste přihlášen jako: " + logic.getUserLoggedIn().getUsername());
        } else {
            drawMessage("Nejste přihlášen");
        }
    }

    private void actionLogout(IMenu fromMenu) {
        if (logic.isUserLoggedIn()) {
            try {
                this.logic.logoutUser();
            } catch (NotLoggedInException e) {
                //This should never happen because we are checking if user is logged in before calling logout.
                e.printStackTrace();
            }
            drawMessage("Odhlášení proběhlo úspěšně.");
        } else {
            drawMessage("Nejste přihlášen");
        }
    }

    private void actionLogin(IMenu menu, Map<String, String> data) {
        try {
            logic.loginUser(data.get("email"), data.get("password"));
            drawMessage("Přihlášení proběhlo úspěšně");
        } catch (AlreadyLoggedInException | InvalidCredentialsException e) {
            drawError(e.getMessage());
        }
    }

    private void actionRegister(IMenu menu, Map<String, String> data) {
        try {
            logic.registerUser(data.get("email"), data.get("username"), data.get("password"));
            drawMessage("Registrace proběhla úspěšně");
        } catch (EmailAddressAlreadyUsedException e) {
            drawError(e.getMessage());
        }
    }

    private void actionCreateNewTag(IMenu menu, Map<String, String> data) {
        logic.createTag(data.get("title"));
        drawMessage("Vytvoření štítku proběhlo úspěšně");
    }

    private void actionUpdateTag(IMenu menu, Map<String, String> data) {
        int id = Integer.parseInt(data.get("id"));
        logic.updateTag(id, data.get("title"), Color.BLACK);

        drawMessage("Upravení štítku proběhlo úspěšně");
    }

    private void actionDeleteTag(IMenu menu, Map<String, String> data){
        int id = Integer.parseInt(data.get("id"));
        logic.destroyTag(id);
        drawMessage("Smazání úkolu proběhlo úspěšně");
    }

    private void actionCreateNewCategory(IMenu menu, Map<String, String> data) {
        logic.createCategory(data.get("title"), Color.BLACK);
        drawMessage("Vytvoření štítku proběhlo úspěšně");
    }

    private void actionUpdateCategory(IMenu menu, Map<String, String> data) {
        int id = Integer.parseInt(data.get("id"));
        logic.updateCategory(id, data.get("title"), Color.BLACK);
        drawMessage("Upravení kategorie proběhlo úspěšně");
    }

    private void actionDeleteCategory(IMenu menu, Map<String, String> data){
        int id = Integer.parseInt(data.get("id"));
        logic.destroyCategory(id);
        drawMessage("Smazání úkolu proběhlo úspěšně");
    }

    private void actionCreateNewTask(IMenu menu, Map<String, String> data) {
        logic.createTask(data.get("title"));
        drawMessage("Vytvoření úkolu proběhlo úspěšně");
    }

    private void actionUpdateTask(IMenu menu, Map<String, String> data) {
        int id = Integer.parseInt(data.get("id"));
        logic.updateTask(id, data.get("title"), Color.BLACK);
        drawMessage("Upravení úkolu proběhlo úspěšně");
    }

    private void actionDeleteTask(IMenu menu, Map<String, String> data){
        int id = Integer.parseInt(data.get("id"));
        logic.destroyTask(id);
        drawMessage("Smazání úkolu proběhlo úspěšně");
    }

    private void actionChangePassword(IMenu menu, Map<String, String> data) {
        int id = Integer.parseInt(data.get("id"));
        String password = data.get("password");
        drawMessage("Upravení úkolu proběhlo úspěšně");
    }

    private void actionDeleteUser(IMenu menu){
        try {
            logic.destroyUserLoggedIn();
        } catch (NotLoggedInException e) {
            e.printStackTrace();
        }
        drawMessage("Smazání uživatelského účtu proběhlo úspěšně");
    }
    //endregion

    //region Handlers
    public IMenu handleMenu(IMenu currentMenu) {
        drawMenu(currentMenu);

        invokeAppLogic(currentMenu);

        IMenuOption selectedOption = handleOptions(currentMenu);

        if (currentMenu.isForm()) {
            return handleMenuAsFormForOption(currentMenu, selectedOption);
        } else {
            return handleMenuForOption(currentMenu, selectedOption);
        }
    }

    private IMenuOption handleOptions(IMenu menu) {
        this.drawPrompt("Zadejte číslo výběru: ");
        int optionNumber = this.promptOption(menu);
        return menu.getOptionForNumber(optionNumber);
    }

    private IMenu handleMenuForOption(IMenu currentMenu, IMenuOption selectedOption) {
        IMenu nextMenu = selectedOption.getMenu();

        if (nextMenu.isSystemMenu()) {
            nextMenu = handleSystemMenuChange(currentMenu, nextMenu);
        } else {
            nextMenu = handleUserMenuChange(currentMenu, nextMenu);
        }

        return nextMenu;
    }

    private IMenu handleMenuAsFormForOption(IMenu currentMenu, IMenuOption selectedOption) {
        IMenu nextMenu = selectedOption.getMenu();

        if (nextMenu.isSystemMenu()) {
            nextMenu = handleSystemMenuChange(currentMenu, nextMenu);
        } else {
            throw new RuntimeException("You cannot use a non-system menu inside the form menus");
        }

        return nextMenu;
    }

    private IMenu handleUserMenuChange(IMenu currentMenu, IMenu nextMenu) {
        return nextMenu;
    }

    private IMenu handleSystemMenuChange(IMenu currentMenu, IMenu nextMenu) {
        if (nextMenu.getType() == MenuType.SYSTEM_BACK) {
            nextMenu = currentMenu.getParentMenu();
        } else if (nextMenu.getType() == MenuType.SYSTEM_FILL_FORM) {
            Map<String, String> formData = handleForm(currentMenu);
            invokeAppLogic(currentMenu, formData);
            nextMenu = currentMenu.getParentMenu();
        } else if (nextMenu.getType() == MenuType.SYSTEM_LOGOUT) {
            invokeAppLogic(nextMenu);
            nextMenu = nextMenu.getParentMenu();
        } else if (nextMenu.getType() == MenuType.SYSTEM_QUIT) {
            // we will close the application with status code 0 (OK) instead of rendering the menu
            System.exit(0);
        } else {
            throw new RuntimeException(nextMenu.getType() + " is not valid type of system menu ");
        }

        return nextMenu;
    }

    private Map<String, String> handleForm(IMenu menu) {
        return this.createFormManagerForMenu(menu).processForm();
    }

    //endregion

    //region Utilities

    /**
     * Checks if validOptions contains testedOption
     */
    private boolean isValidOption(int testedOption, int[] validOptions) {
        return IntStream.of(validOptions).anyMatch(x -> x == testedOption);
    }
    //endregion

    //region Draw Methods
    @Override
    public void drawOutput(String output) {
        System.out.println(output);
    }

    @Override
    public void drawMenu(IMenu menuToBeRendered) {
        System.out.println(menuToBeRendered.render());
    }

    @Override
    public void drawMessage(String message) {
        System.out.println(getMenuView().drawMessage(message));
    }

    @Override
    public void drawError(String message) {
        System.out.println(getMenuView().drawError(message));
    }

    @Override
    public void drawPrompt(String message) {
        System.out.println(getMenuView().drawPrompt(message));
    }
    //endregion
}

