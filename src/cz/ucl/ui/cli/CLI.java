package cz.ucl.ui.cli;

import cz.ucl.logic.exceptions.AlreadyLoggedInException;
import cz.ucl.logic.exceptions.EmailAddressAlreadyUsedException;
import cz.ucl.logic.exceptions.InvalidCredentialsException;
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

import javax.swing.text.html.FormView;
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

        categoryView = this.getCategoryView();
        tagView = this.getTagView();
        taskView = this.getTaskView();
        menuView = this.getMenuView();
        formView = this.getFormView();
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
        }
        else { // if secure input is not supported, fallback to the classic one
            return promptString();
        }
    }

    @Override
    public int promptOption(IMenu menu) {
        return promptNumber();
    }
    //endregion

    //region Logic
    public void invokeAppLogic(IMenu fromMenu, Map<String, String> formData) {
        if (fromMenu.getIdentifier().equals("login")) {
            actionLogin(fromMenu, formData);
        } else if (fromMenu.getIdentifier().equals("register")) {
            actionRegister(fromMenu, formData);
        }
        // TODO
    }

    public void invokeAppLogic(IMenu fromMenu) {
        if (fromMenu.getIdentifier().equals("main_menu")) {
            actionDashboard(fromMenu);
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
        return null;
    }

    @Override
    public ITagView getTagView() {
        return null;
    }

    @Override
    public ITaskView getTaskView() {
        return null;
    }

    @Override
    public IFormView getFormView() {
        return null;
    }

    @Override
    public IMenuView getMenuView() {
        return null;
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

    // TODO
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

    private IMenuOption handleOptions(IMenu menu){
        return null;
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
        } else if (nextMenu.getType() == MenuType.SYSTEM_QUIT) {
            // we will close the application with status code 0 (OK) instead of rendering the menu
            System.exit(0);
        } else {
            throw new RuntimeException(nextMenu.getType() + " is not valid type of system menu ");
        }

        return nextMenu;
    }

    private Map<String, String> handleForm(IMenu menu){
        //TODO
        return null;
    }
    // TODO

    //endregion

    //region Utilities
    /** Checks if validOptions contains testedOption */
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

