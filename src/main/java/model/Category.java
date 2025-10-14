package model;

public class Category {
    Long category_id;
    String category_name;
    int sort_order;
    String insert_date;
    String modify_date;

    public Category(Long category_id, String category_name, int sort_order, String insert_date, String modify_date) {
        this.category_id = category_id;
        this.category_name = category_name;
        this.sort_order = sort_order;
        this.insert_date = insert_date;
        this.modify_date = modify_date;
    }

    public Category(Long category_id, String category_name, int sort_order) {
        this.category_name = category_name;
    }


    public Long getCategory_id() {
        return category_id;
    }

    public String getName() {
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
}
