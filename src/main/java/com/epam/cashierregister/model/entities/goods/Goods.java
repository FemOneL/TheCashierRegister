package com.epam.cashierregister.model.entities.goods;

import java.math.BigDecimal;

public class Goods {
    private int id;
    private String model;
    private int numbers;
    private BigDecimal cost;
    private Category category;
    private Producer producer;

    public Goods(int id, String model, int numbers, BigDecimal cost, Category category, Producer producer) {
        this.id = id;
        this.model = model;
        this.numbers = numbers;
        this.cost = cost;
        this.category = category;
        this.producer = producer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getNumbers() {
        return numbers;
    }

    public void setNumbers(int numbers) {
        this.numbers = numbers;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Producer getProducer() {
        return producer;
    }

    public void setProducer(Producer producer) {
        this.producer = producer;
    }
}
