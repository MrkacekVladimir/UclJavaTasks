package cz.ucl.ui.cli.forms;

import cz.ucl.ui.definition.forms.FormFieldType;
import cz.ucl.ui.definition.forms.IFormField;

import java.util.Optional;

public class FormField implements IFormField {
    private String identifier;
    private String title;
    private FormFieldType type;
    private boolean isRequired;
    private String defaultValue;

    public FormField(String identifier, String title, FormFieldType type) {
        this.identifier = identifier;
        this.title = title;
        this.type = type;
    }

    public FormField(String identifier, String title, FormFieldType type, boolean isRequired) {
        this(identifier, title, type);
        this.isRequired = isRequired;
    }

    public FormField(String identifier, String title, FormFieldType type, boolean isRequired, String defaultValue) {
        this(identifier, title, type, isRequired);
        this.defaultValue = defaultValue;
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
        return title.toLowerCase();
    }

    @Override
    public FormFieldType getType() {
        return this.type;
    }

    @Override
    public boolean getIsRequired() {
        return false;
    }

    @Override
    public String getDefaultValue() {
        return this.defaultValue;
    }
}
