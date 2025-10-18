package com.lxp.util;

import com.lxp.service.CategoryService;
import com.lxp.service.CategoryServiceImpl;
import java.sql.Connection;

public class CategoryFactory {
    public CategoryService categoryService(Connection conn) {
        return new CategoryServiceImpl(conn);
    }
}
