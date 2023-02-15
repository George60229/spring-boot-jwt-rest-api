package com.alibou.security.dto.request;


public class CertificateEditRequestDto {
    public CertificateEditRequestDto() {
    }

    public CertificateEditRequestDto(String value, EditParameter parameter, int id) {
        this.value = value;
        this.parameter = parameter;
        this.id = id;
    }

    private String value;
    private EditParameter parameter;

    private int id;

    public String getValue() {
        return value;
    }

    public EditParameter getParameter() {
        return parameter;
    }

    public int getId() {
        return id;
    }
}
