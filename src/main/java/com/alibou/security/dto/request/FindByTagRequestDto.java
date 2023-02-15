package com.alibou.security.dto.request;

import java.util.ArrayList;
import java.util.List;

public class FindByTagRequestDto {

    List<String> tagNames = new ArrayList<>();

    int page = 0;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<String> getTagNames() {
        return tagNames;
    }

    public void setTagNames(List<String> tagNames) {
        this.tagNames = tagNames;
    }
}
