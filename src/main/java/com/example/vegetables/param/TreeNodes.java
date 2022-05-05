package com.example.vegetables.param;

import lombok.Data;

@Data
public class TreeNodes {

    private Long id;
    /**
     * 分类名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 父id
     */
    private Long parentId;

    /**
     * 全路径：如3级结构，1,11,111
     */
    private String path;
}
