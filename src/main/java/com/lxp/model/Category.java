package com.lxp.model;

import java.util.Objects;

public final class Category {

    private Long category_id;
    private String category_name;
    private Integer sort_order;
    private String insert_date;
    private String modify_date;

    private Category(Long category_id, String category_name, Integer sort_order, String insert_date, String modify_date) {
        this.category_id = category_id;
        this.category_name = category_name;
        this.sort_order = sort_order;
        this.insert_date = insert_date;
        this.modify_date = modify_date;
    }

    private Category(String category_name) {
        this.category_name = category_name;
    }

    public Long getCategory_id() {
        return category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public int getSort_order() {
        return sort_order;
    }

    public String getInsert_date() {
        return insert_date;
    }

    public String getModify_date() {
        return modify_date;
    }

    public void setCategory_id(Long category_id) {
        this.category_id = category_id;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public void setSort_order(int sort_order) {
        this.sort_order = sort_order;
    }

    public void setInsert_date(String insert_date) {
        this.insert_date = insert_date;
    }

    public void setModify_date(String modify_date) {
        this.modify_date = modify_date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return sort_order == category.sort_order &&
                Objects.equals(category_id, category.category_id) &&
                Objects.equals(category_name, category.category_name) &&
                Objects.equals(insert_date, category.insert_date) &&
                Objects.equals(modify_date, category.modify_date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(category_id, category_name, sort_order, insert_date, modify_date);
    }

    @Override
    public String toString() {
        return "Category[" +
                "category_id=" + category_id + ", " +
                "category_name='" + category_name + '\'' + ", " +
                "sort_order=" + sort_order + ", " +
                "insert_date='" + insert_date + '\'' + ", " +
                "modify_date='" + modify_date + '\'' +
                ']';
    }

    public static Category fromDb(Long category_id, String category_name, int sort_order, String insert_date, String modify_date) {
        return new Category(category_id, category_name, sort_order, insert_date, modify_date);
    }

    public static Category forCreation(String category_name) {
        return new Category(null, category_name, null, null, null);
    }
}