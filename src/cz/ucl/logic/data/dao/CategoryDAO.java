package cz.ucl.logic.data.dao;

import cz.ucl.logic.app.entities.definition.Color;

import java.time.LocalDateTime;
import java.util.List;

public class CategoryDAO {
    private int id;
    private UserDAO user;
    private String title;
    private Color color;
    private List<TaskDAO> tasks;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
