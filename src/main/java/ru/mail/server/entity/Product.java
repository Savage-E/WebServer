package ru.mail.server.entity;

import org.jetbrains.annotations.NotNull;

public final class Product {
    @NotNull String name;
    @NotNull String manufacturer;
    int amount;

    public Product(@NotNull String name, @NotNull String manufacturer, int amount) {
        this.name = name;
        this.manufacturer = manufacturer;
        this.amount = amount;
    }

    @NotNull
    public String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    @NotNull
    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(@NotNull String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
