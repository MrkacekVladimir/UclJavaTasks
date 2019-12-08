package cz.ucl.ui.cli.views;

import cz.ucl.ui.cli.menu.Menu;
import cz.ucl.ui.definition.menu.IMenu;
import cz.ucl.ui.definition.menu.IMenuOption;
import cz.ucl.ui.definition.views.IMenuView;

import java.util.LinkedList;

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

        LinkedList<IMenu> linkedList = new LinkedList<>();
        while (currentMenu.getParentMenu() != null) {
            linkedList.addFirst(currentMenu);
            currentMenu = currentMenu.getParentMenu();
        }
        linkedList.addFirst(currentMenu);

        StringBuilder builder = new StringBuilder();

        while (linkedList.size() > 0 && linkedList.getFirst() != null) {
            IMenu menu = linkedList.removeFirst();
            builder.append(menu.getTitle());
            //Check if there is another menu in the linkedList
            if(linkedList.peek() != null){
                builder.append(" > ");
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
