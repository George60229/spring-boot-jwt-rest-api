package com.alibou.security.dto.request;




import com.alibou.security.model.Tag;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CertificateRequestDTO {

    private String name;
    private String description;
    private BigDecimal price;
    private int duration;

    private List<Tag> tags = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
