package cz.ucl.logic.data.dao;

import java.time.LocalDateTime;
import java.util.List;

public class TaskDAO {
    private int id;
    private UserDAO user;
    private String title;
    private String note;
    private boolean isDone;
    private CategoryDAO category;
    private List<TagDAO> tags;
    private LocalDateTime dueDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
