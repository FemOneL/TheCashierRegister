package com.epam.cashierregister.services.entities.report;

import com.epam.cashierregister.services.entities.Entity;
import com.epam.cashierregister.services.entities.employee.Employee;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Report implements Entity {
    private Employee seniorCashier;
    private Timestamp createdDate;
    private Timestamp date;
    private Selling selling;
    private Return returned;
    private BigDecimal profit;

    public Report() {
        this.seniorCashier = null;
        this.createdDate = new Timestamp(System.currentTimeMillis());
        this.date = null;
        this.selling = new Selling();
        this.returned = new Return();
        this.profit = new BigDecimal(0);
    }

    public Report(Timestamp date, Selling selling, Return returned, BigDecimal profit){
        this.date = date;
        this.selling = selling;
        this.returned = returned;
        this.profit = profit;
    }

    public Report(Employee seniorCashier, Timestamp createdDate, Timestamp date, Selling selling, Return returned, BigDecimal profit) {
        this.seniorCashier = seniorCashier;
        this.createdDate = createdDate;
        this.date = date;
        this.selling = selling;
        this.returned = returned;
        this.profit = profit;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal benefit) {
        this.profit = benefit;
    }

    public Employee getSeniorCashier() {
        return seniorCashier;
    }

    public void setSeniorCashier(Employee seniorCashier) {
        this.seniorCashier = seniorCashier;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Selling getSelling() {
        return selling;
    }

    public void setSelling(Selling selling) {
        this.selling = selling;
    }

    public Return getReturned() {
        return returned;
    }

    public void setReturned(Return returned) {
        this.returned = returned;
    }
}
