package com.epam.cashierregister.services.entities.goods;

import java.math.BigDecimal;
import java.util.Objects;

public class Goods {
    private int id;
    private String model;
    private String photo;
    private int numbers;
    private int totalNumber;
    private BigDecimal totalCost;
    private BigDecimal cost;
    private Category category;
    private Producer producer;

    public Goods(int id, String model, String photo, int numbers, BigDecimal cost, Category category, Producer producer) {
        this.id = id;
        this.model = model;
        this.photo = photo;
        this.numbers = numbers;
        this.cost = cost;
        this.category = category;
        this.producer = producer;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public int getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(int totalNumber) {
        this.totalNumber = totalNumber;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Goods goods = (Goods) o;
        return id == goods.id && Objects.equals(model, goods.model) && Objects.equals(cost, goods.cost) && Objects.equals(category, goods.category) && Objects.equals(producer, goods.producer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, model, cost, category, producer);
    }
}
