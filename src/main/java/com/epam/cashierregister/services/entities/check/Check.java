package com.epam.cashierregister.services.entities.check;

import com.epam.cashierregister.services.entities.employee.Employee;
import com.epam.cashierregister.services.entities.goods.Goods;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public class Check {
    private int id;
    private Employee employee;
    private Date time;
    private BigDecimal totalCost;
    private boolean isClosed;
    private List<Goods> goodsList;

    public Check(int id, Employee employee, Date time, BigDecimal totalCost, boolean isClosed, List<Goods> goodsList) {
        this.id = id;
        this.employee = employee;
        this.time = time;
        this.totalCost = totalCost;
        this.isClosed = isClosed;
        this.goodsList = goodsList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }

    public List<Goods> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<Goods> goodsList) {
        this.goodsList = goodsList;
    }
}
