package com.alibou.security.dto.request;


import com.alibou.security.utils.FindParameter;
import com.alibou.security.utils.SortParameter;
import com.alibou.security.utils.SortWay;

public class CertificateFindByRequestDTO {

    private int page;

    private SortWay sortWay = SortWay.ASC;
    private SortParameter sortParameter = SortParameter.DATE;

    private FindParameter findParameter = FindParameter.DEFAULT;
    private String value = "";

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public SortWay getSortWay() {
        return sortWay;
    }

    public SortParameter getSortParameter() {
        return sortParameter;
    }

    public FindParameter getFindParameter() {
        return findParameter;
    }

    public void setSortWay(SortWay sortWay) {
        this.sortWay = sortWay;
    }

    public void setSortParameter(SortParameter sortParameter) {
        this.sortParameter = sortParameter;
    }

    public void setFindParameter(FindParameter findParameter) {
        this.findParameter = findParameter;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
