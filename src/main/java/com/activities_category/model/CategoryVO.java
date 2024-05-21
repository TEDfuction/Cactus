package com.activities_category.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.*;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.activities_item.model.ItemVO;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "activity_category")
public class CategoryVO implements Serializable{
    private static final long serialVersionUID = 6263388433578802846L;

    @Id
    @Column(name = "activity_category_id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer activityCategoryId;

    @Column(name = "activity_category_name")
    @NotEmpty(message = "瘣餃?憿?迂嚗??輻征??)
            private String activityCategoryName;

            @Column(name = "activity_category_info")
            private String activityCategoryInfo;

            @JsonBackReference
            @OneToMany(mappedBy = "categoryVO" , cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @OrderBy("activityId")
    private Set<ItemVO> items;

    public Integer getActivityCategoryId() {
        return activityCategoryId;
    }

    public void setActivityCategoryId(Integer activityCategoryId) {
        this.activityCategoryId = activityCategoryId;
    }

    public String getActivityCategoryName() {
        return activityCategoryName;
    }

    public void setActivityCategoryName(String activityCategoryName) {
        this.activityCategoryName = activityCategoryName;
    }

    public String getActivityCategoryInfo() {
        return activityCategoryInfo;
    }

    public void setActivityCategoryInfo(String activityCategoryInfo) {
        this.activityCategoryInfo = activityCategoryInfo;
    }

    public Set<ItemVO> getItems() {
        return items;
    }

    public void setItems(Set<ItemVO> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "CategoryVO{" +
                "activityCategoryId=" + activityCategoryId +
                ", activityCategoryName='" + activityCategoryName + '\'' +
                ", activityCategoryInfo='" + activityCategoryInfo + '\'' +
                ", items=" + items +
                '}';
    }
}
