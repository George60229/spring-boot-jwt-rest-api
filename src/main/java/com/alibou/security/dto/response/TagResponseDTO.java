package com.alibou.security.dto.response;

public class TagResponseDTO {
    private Integer tagId;
    private String name;

    public Integer getId() {
        return tagId;
    }

    public void setId(Integer id) {
        this.tagId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
