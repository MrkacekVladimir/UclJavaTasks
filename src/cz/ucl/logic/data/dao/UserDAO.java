package cz.ucl.logic.data.dao;

import java.util.List;

public class UserDAO {
    private int id;
    private String email;
    private String username;
    private String password;
    private List<CategoryDAO> categories;
    private List<TaskDAO> tasks;
    private List<TagDAO> tags;
}
