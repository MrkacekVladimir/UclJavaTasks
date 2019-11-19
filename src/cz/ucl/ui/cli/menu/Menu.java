package cz.ucl.ui.cli.menu;

import cz.ucl.logic.IAppLogic;
import cz.ucl.ui.cli.forms.Form;
import cz.ucl.ui.definition.IUserInterface;
import cz.ucl.ui.definition.forms.IForm;
import cz.ucl.ui.definition.forms.IFormField;
import cz.ucl.ui.definition.menu.IMenu;
import cz.ucl.ui.definition.menu.IMenuFactory;
import cz.ucl.ui.definition.menu.IMenuOption;
import cz.ucl.ui.definition.menu.MenuType;
import cz.ucl.ui.definition.views.IMenuView;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.stream.Collectors;

public abstract class Menu implements IMenu {

    private String identifier;
    private String title;
    private String description;
    private List<IMenuOption> options;
    private IForm form;
    private boolean isBuilt;

    protected IUserInterface ui;
    protected IAppLogic logic;
    protected IMenu parentMenu;

    public Menu(IMenu parentMenu, String identifier, String title) {
        if (parentMenu != null) {
            this.ui = parentMenu.getParentInterface();
            this.logic = this.ui.getLogic();
        }
        this.parentMenu = parentMenu;
        this.identifier = identifier;
        this.title = title;

        this.options = new ArrayList<>();
        this.form = new Form(this);

        this.isBuilt = false;
    }

    /**
     * This method should be used for building the menu - setting description, adding options, etc
     */
    protected abstract void build();

    protected void clearOptions(){
        this.options.clear();
    }

    @Override
    public String getIdentifier() {
        return this.identifier;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public IMenu getParentMenu() {
        return this.parentMenu;
    }

    @Override
    public IMenuOption[] getOptions() {
        IMenuOption[] arr = new IMenuOption[this.options.size()];
        return this.options.toArray(arr);
    }

    @Override
    public int[] getValidOptionNumbers() {
        return this.options.stream().map(IMenuOption::getNumber).mapToInt(Number::intValue).toArray();
    }

    @Override
    public IMenuOption getOptionForNumber(int optionNumber) {
        return this.options.stream().filter(o -> o.getNumber() == optionNumber).findFirst().get();
    }

    @Override
    public void addOption(IMenuOption option) {
        this.options.add(option);
    }

    @Override
    public int nextOptionNumber() {
        OptionalInt maxInteger = Arrays.stream(this.getValidOptionNumbers()).max();
        if (maxInteger.isPresent())
            return maxInteger.getAsInt() + 1;

        return 1;
    }

    @Override
    public void initialize() {
        if (!isBuilt) {
            build();
            isBuilt = true;
        }
    }

    @Override
    public String render() {
        IMenuView view = this.ui.getMenuView();

        return view.formatMenu(this);
    }

    @Override
    public IAppLogic getLogic() {
        return this.logic;
    }

    @Override
    public IUserInterface getParentInterface() {
        return this.ui;
    }

    @Override
    public boolean isSystemMenu() {
         return this.getType() != MenuType.USER;
    }

    @Override
    public IFormField[] getFormFields() {
        return this.form.getFormFields();
    }

    @Override
    public void addFormField(IFormField field) {
        this.form.addFormField(field);
    }

    @Override
    public String renderFormField(IFormField formField) {
        return this.form.renderFormField(formField);
    }

    @Override
    public boolean isForm() {
        return this.form.isForm();
    }
}
