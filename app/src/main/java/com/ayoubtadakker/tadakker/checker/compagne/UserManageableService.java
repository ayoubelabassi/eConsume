package com.ayoubtadakker.tadakker.checker.compagne;

import java.util.List;

/**
 * Created by NAWAR on 27/02/2018.
 */

public interface UserManageableService {
    public User load(int id);
    public User read(String username,String password);
    public List<User> readAll();
}
