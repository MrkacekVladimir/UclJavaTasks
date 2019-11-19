package cz.ucl.ui.cli.views;

import cz.ucl.ui.cli.menu.Menu;
import cz.ucl.ui.definition.menu.IMenu;
import cz.ucl.ui.definition.menu.IMenuOption;
import cz.ucl.ui.definition.views.IMenuView;

import java.util.ArrayDeque;

public class MenuView implements IMenuView {
    @Override
    public String formatMenuOption(IMenuOption option) {
        return String.format("%d. %s",  option.getNumber(), option.getTitle());
    }

    @Override
    public String formatMenu(Menu menu) {
        StringBuilder builder = new StringBuilder();

        builder.append(this.drawBreadcrumbs(menu));
        builder.append(this.drawNewLine());
        builder.append(this.drawHeader(menu));
        builder.append(this.drawNewLine());
        builder.append(this.drawDescription(menu));
        builder.append(this.drawNewLine());
        builder.append(this.drawOptions(menu));

        return builder.toString();
    }

    @Override
    public String drawDescription(Menu menu) {
        return menu.getDescription();
    }

    @Override
    public String drawHeader(IMenu currentMenu) {
        return String.format("Právě se nacházíte v '%s'",currentMenu.getTitle());
    }

    @Override
    public String drawSeparator() {
        return "|";
    }

    @Override
    public String drawNewLine() {
        return System.lineSeparator();
    }

    @Override
    public String drawBreadcrumbs(IMenu currentMenu) {
        if (currentMenu == null) {
            return "";
        }

        ArrayDeque<String> menuTitleArray = new ArrayDeque<String>();
        while (currentMenu.getParentMenu() != null) {
            menuTitleArray.add(currentMenu.getTitle());
            currentMenu = currentMenu.getParentMenu();
        }
        menuTitleArray.add(currentMenu.getTitle());

        StringBuilder builder = new StringBuilder();

        while (menuTitleArray.peek() != null) {
            builder.append(menuTitleArray.pop());
            //Check if there is another menu in the queue
            if(menuTitleArray.peek() != null){
                builder.append(" - ");
            }
        }

        return builder.toString();
    }

    @Override
    public String drawOptions(IMenu currentMenu) {
        StringBuilder builder = new StringBuilder();

        IMenuOption[] options = currentMenu.getOptions();
        for (IMenuOption option : options) {
            builder.append(this.formatMenuOption(option));
            builder.append(this.drawNewLine());
        }

        return builder.toString();
    }

    @Override
    public String drawMessage(String message) {
        return message;
    }

    @Override
    public String drawError(String message) {
        return message;
    }

    @Override
    public String drawPrompt(String message) {
        return message;
    }
}
