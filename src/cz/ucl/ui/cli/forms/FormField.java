package cz.ucl.ui.cli.forms;

import cz.ucl.ui.definition.forms.FormFieldType;
import cz.ucl.ui.definition.forms.IFormField;

import java.util.Optional;

public class FormField implements IFormField {
    private String identifier;
    private String title;
    private FormFieldType type;
    private boolean isRequired;

    public FormField(String identifier, String title, FormFieldType type) {
        this.identifier = identifier;
        this.title = title;
        this.type = type;
        this.isRequired = true;
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
    public String getLabel() {
        return "Prosím zadejte " + title.toLowerCase();
    }

    @Override
    public FormFieldType getType() {
        return this.type;
    }

    @Override
    public boolean getIsRequired() {
        return false;
    }
}
