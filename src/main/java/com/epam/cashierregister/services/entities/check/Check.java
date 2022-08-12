package com.epam.cashierregister.services.entities.check;

import com.epam.cashierregister.services.entities.employee.Employee;
import com.epam.cashierregister.services.entities.goods.Goods;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Check {
    private int id;
    private Employee employee;
    private Date time;
    private BigDecimal totalCost;
    private boolean isClosed;
    private Set<Goods> goodsInWarehouseSet;

    public Check(int id, Employee employee, Date time, BigDecimal totalCost, boolean isClosed, Set<Goods> goodsInWarehouseList) {
        this.id = id;
        this.employee = employee;
        this.time = time;
        this.totalCost = totalCost;
        this.isClosed = isClosed;
        this.goodsInWarehouseSet = goodsInWarehouseList;
    }

    public Check() {
        goodsInWarehouseSet = new HashSet<>();
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

    public Set<Goods> getGoodsSet() {
        return goodsInWarehouseSet;
    }

    public void setGoodsSet(Set<Goods> goodsInWarehouseSet) {
        this.goodsInWarehouseSet = goodsInWarehouseSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Check check = (Check) o;
        return id == check.id && isClosed == check.isClosed && Objects.equals(employee, check.employee) && Objects.equals(time, check.time) && Objects.equals(totalCost, check.totalCost) && Objects.equals(goodsInWarehouseSet, check.goodsInWarehouseSet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, employee, time, totalCost, isClosed, goodsInWarehouseSet);
    }
}
