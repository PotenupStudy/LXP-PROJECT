package com.lxp.util;

import com.lxp.dao.CategoryDAO;
import com.lxp.service.CategoryService;
import com.lxp.service.CategoryServiceImpl;
import java.sql.Connection;

public class CategoryFactory {
    public CategoryService categoryService(Connection conn) {
        CategoryDAO categoryDAO = new CategoryDAO(conn);

        return new CategoryServiceImpl(categoryDAO);
    }
}
