package cz.ucl.ui.cli.views;

import cz.ucl.ui.definition.forms.IFormField;
import cz.ucl.ui.definition.views.IFormView;

public class FormView implements IFormView {
    @Override
    public String formatFormField(IFormField field) {
        StringBuilder builder = new StringBuilder();

        builder.append("Zadejte ");
        builder.append(field.getLabel());
        if(field.getIsRequired()){
            builder.append("*");
        }
        builder.append(':');

        return builder.toString();
    }
}
