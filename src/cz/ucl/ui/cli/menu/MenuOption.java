package cz.ucl.ui.cli.menu;

import cz.ucl.ui.definition.menu.IMenu;
import cz.ucl.ui.definition.menu.IMenuOption;

public class MenuOption implements IMenuOption {

    private int identifier;
    private IMenu menu;

    public MenuOption( int identifier, IMenu menu ){
        this.identifier = identifier;
        this.menu = menu;
    }

    @Override
    public int getNumber() {
        return identifier;
    }

    @Override
    public String getTitle() {
        return this.menu.getTitle();
    }

    @Override
    public IMenu getMenu() {
        return menu;
    }
}
