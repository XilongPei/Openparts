package com.cnpc.framework.base.pojo;

import java.util.List;


public class CsvPOJO {

    private String[] headers;
    private List<CsvRow> rows;

    public String[] getHeaders() {
        return headers;
    }

    public void setHeaders(String[] headers) {
        this.headers = headers;
    }

    public List<CsvRow> getRows() {
        return rows;
    }

    public void setRows(List<CsvRow> rows) {
        this.rows = rows;
    }

}
