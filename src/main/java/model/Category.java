package model;
public record Category(
        Long category_id,
        String category_name,
        int sort_order,
        String insert_date,
        String modify_date
) {
    public static Category fromDb(Long category_id, String category_name, int sort_order, String insert_date, String modify_date) {
        return new Category(category_id, category_name, sort_order, insert_date, modify_date);
    }

    public static Category forCreation(String category_name, int sort_order) {

        return new Category(null, category_name, sort_order, null, null);
    }
}