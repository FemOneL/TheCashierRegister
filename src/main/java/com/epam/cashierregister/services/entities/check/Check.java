package com.epam.cashierregister.services.entities.check;

import com.epam.cashierregister.services.entities.Entity;
import com.epam.cashierregister.services.entities.employee.Employee;
import com.epam.cashierregister.services.entities.goods.Goods;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public class Check implements Entity {
    private int id;
    private Employee employee;
    private Timestamp time;
    private BigDecimal totalCost;
    private Set<Goods> goodsInWarehouseSet;

    public Check(int id, Employee employee, Timestamp time, BigDecimal totalCost) {
        this.id = id;
        this.employee = employee;
        this.time = time;
        this.totalCost = totalCost;
        goodsInWarehouseSet = new LinkedHashSet<>();
    }

    public Check() {
        goodsInWarehouseSet = new LinkedHashSet<>();
        this.totalCost = new BigDecimal(0);
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

    public void setEmployee(Employee employeeId) {
        this.employee = employee;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public Set<Goods> getGoodsSet() {
        return goodsInWarehouseSet;
    }

    public void setGoodsSet(Set<Goods> goodsInWarehouseSet) {
        this.goodsInWarehouseSet = goodsInWarehouseSet;
    }

}
