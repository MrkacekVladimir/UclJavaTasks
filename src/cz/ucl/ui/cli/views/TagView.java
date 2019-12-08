package cz.ucl.ui.cli.views;

import cz.ucl.logic.app.entities.definition.ITag;
import cz.ucl.ui.definition.views.ITagView;

public class TagView implements ITagView {
    @Override
    public String formatTagList(ITag[] tagList) {
        StringBuilder builder = new StringBuilder();

        for (ITag tag : tagList ) {
            builder.append(this.formatTag(tag));
            builder.append(System.lineSeparator());
        }

        return builder.toString();
    }

    @Override
    public String formatTag(ITag tag) {
        return String.format("#%d - %s - %s", tag.getId(), tag.getTitle(), tag.getColor());
    }
}
