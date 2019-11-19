package cz.ucl.logic.app.entities.definition;

import java.time.LocalDateTime;

public interface ITag extends ITaskOwner {
    int getId();
    IUser getUser();
    String getTitle();
    Color getColor();

    // see ITaskOwner

    LocalDateTime getCreatedAt();
    LocalDateTime getUpdatedAt();
}
