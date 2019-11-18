package cz.ucl.ui.cli.views;

import cz.ucl.logic.app.entities.definition.ICategory;
import cz.ucl.ui.definition.views.ICategoryView;

public class CategoryView implements ICategoryView {

    @Override
    public String formatCategoryList(ICategory[] categoryList) {
        StringBuilder builder = new StringBuilder();

        for (ICategory category : categoryList) {
            builder.append(this.formatCategory(category));
            builder.append(System.lineSeparator());
        }

        return builder.toString();
    }

    @Override
    public String formatCategory(ICategory category) {
        return String.format("#%i - %n - %c", category.getId(), category.getTitle(), category.getColor());
    }
}
