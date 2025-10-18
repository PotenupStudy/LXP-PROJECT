package com.lxp.model;

import java.time.LocalDateTime;

public class LectureResource {
    private long resource_id;
    private long lecture_id;
    private String resource_name;
    private ResourceType resource_type;
    private String resource_url;
    private int order_index;
    private int duration;
    private LocalDateTime insert_date;
    private LocalDateTime modify_date;

    public LectureResource(long resource_id, long lecture_id, String resource_name, ResourceType resource_type, String resource_url, int order_index, int duration) {
        this.resource_id = resource_id;
        this.lecture_id = lecture_id;
        this.resource_name = resource_name;
        this.resource_type = resource_type;
        this.resource_url = resource_url;
        this.order_index = order_index;
        this.duration = duration;
    }

    public LectureResource(long resource_id, long lecture_id, String resource_name, String resource_url, int order_index, int duration) {
        this.resource_id = resource_id;
        this.lecture_id = lecture_id;
        this.resource_name = resource_name;
        this.resource_url = resource_url;
        this.order_index = order_index;
        this.duration = duration;
    }

    public LectureResource(long resource_id) {
        this.resource_id = resource_id;
    }

    public LectureResource(long lecture_id, String resource_name, String resource_url, int order_index, int duration) {
        this.lecture_id = lecture_id;
        this.resource_name = resource_name;
        this.resource_url = resource_url;
        this.order_index = order_index;
        this.duration = duration;
    }

    public LectureResource(long lecture_id, String resource_name, ResourceType resource_type, String resource_url, int order_index, int duration) {
        this.lecture_id = lecture_id;
        this.resource_name = resource_name;
        this.resource_type = resource_type;
        this.resource_url = resource_url;
        this.order_index = order_index;
        this.duration = duration;
    }
    public LectureResource(String resource_name, ResourceType resource_type, String resource_url, int order_index, int duration, long resource_id, long lecture_id) {
        this.resource_id = resource_id;
        this.resource_name = resource_name;
        this.resource_type = resource_type;
        this.resource_url = resource_url;
        this.order_index = order_index;
        this.duration = duration;
        this.lecture_id = lecture_id;
    }
    public long getResource_id() {
        return resource_id;
    }

    public void setResource_id(long resource_id) {
        this.resource_id = resource_id;
    }

    public long getLecture_id() {
        return lecture_id;
    }

    public void setLecture_id(long lecture_id) {
        this.lecture_id = lecture_id;
    }

    public String getResource_name() {
        return resource_name;
    }

    public void setResource_name(String resource_name) {
        this.resource_name = resource_name;
    }


    public ResourceType getResource_type() {
        return resource_type;
    }

    public void setResource_type(ResourceType resource_type) {
        this.resource_type = resource_type;
    }

    public String getResource_url() {
        return resource_url;
    }

    public void setResource_url(String resource_url) {
        this.resource_url = resource_url;
    }

    public int getOrder_index() {
        return order_index;
    }

    public void setOrder_index(int order_index) {
        this.order_index = order_index;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public LocalDateTime getInsert_date() {
        return insert_date;
    }

    public void setInsert_date(LocalDateTime insert_date) {
        this.insert_date = insert_date;
    }

    public LocalDateTime getModify_date() {
        return modify_date;
    }

    public void setModify_date(LocalDateTime modify_date) {
        this.modify_date = modify_date;
    }


    @Override
    public String toString() {
        return "LectureResource{" +
                "resource_id=" + resource_id +
                ", lecture_id=" + lecture_id +
                ", resource_name='" + resource_name + '\'' +
                ", resource_type=" + resource_type +
                ", resource_url='" + resource_url + '\'' +
                ", order_index=" + order_index +
                ", duration=" + duration +
                ", insert_date=" + insert_date +
                ", modify_date=" + modify_date +
                '}';
    }
}
