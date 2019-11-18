package cz.ucl.ui.cli.views;

import cz.ucl.ui.cli.menu.Menu;
import cz.ucl.ui.definition.menu.IMenu;
import cz.ucl.ui.definition.menu.IMenuOption;
import cz.ucl.ui.definition.views.IMenuView;
import jdk.jfr.internal.tool.PrettyWriter;

import java.util.ArrayDeque;

public class MenuView implements IMenuView {
    @Override
    public String formatMenuOption(IMenuOption option) {
        return String.format("#%i. %n", option.getNumber(), option.getTitle());
    }

    @Override
    public String formatMenu(Menu menu) {
        StringBuilder builder = new StringBuilder();



        return builder.toString();
    }

    @Override
    public String drawDescription(Menu menu) {
        return menu.getDescription();
    }

    @Override
    public String drawHeader(IMenu currentMenu) {
        return currentMenu.getTitle();
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
        if(currentMenu == null){
            return "";
        }

        ArrayDeque<String> menuArray = new ArrayDeque<String>();
        while(currentMenu.getParentMenu() != null)
        {
            menuArray.add(currentMenu.getTitle());
            currentMenu = currentMenu.getParentMenu();
        }
        menuArray.add(currentMenu.getTitle());

        StringBuilder builder = new StringBuilder();

        //while(menuArray.peek())

        return builder.toString();
    }

    @Override
    public String drawOptions(IMenu currentMenu) {
        return null;
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
