package com.bank.guibank.controller.user.utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TableRowData {
    private String property;
    private String value;

    public TableRowData(String property, String value) {
        this.property = property;
        this.value = value;
    }
}
