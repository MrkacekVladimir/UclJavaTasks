package cz.ucl.ui.cli.views;

import cz.ucl.logic.app.entities.definition.ICategory;
import cz.ucl.ui.definition.views.ICategoryView;

public class CategoryView implements ICategoryView {
    private ICategory[] categories;

    public CategoryView(ICategory[] categories){
        this.categories = categories;
    }

    @Override
    public String formatCategoryList(ICategory[] categoryList) {
        return null;
    }

    @Override
    public String formatCategory(ICategory category) {
        return null;
    }
}
