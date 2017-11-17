package com.lanmishu.alipaytest;

public class JsonResult {
    private boolean success = false; // 请求处理结果标识
    private Object id; // 可用于存放主键Id
    private String msg; // 处理结果
    private Object rows; // 结果集
    private Object row; // 单个结果
    private int total = 0; // 结果集数量

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getRows() {
        return rows;
    }

    public void setRows(Object rows) {
        this.rows = rows;
    }

    public Object getRow() {
        return row;
    }

    public void setRow(Object row) {
        this.row = row;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
