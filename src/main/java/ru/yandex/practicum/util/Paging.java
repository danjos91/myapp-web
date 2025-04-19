package ru.yandex.practicum.util;

import lombok.Data;

@Data
public class Paging {
    private int pageNumber;
    private int pageSize;
    private boolean hasNext;
    private boolean hasPrevious;

    // Constructor, getters y otros métodos...
    public Paging(int pageNumber, int pageSize, boolean hasNext, boolean hasPrevious) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.hasNext = hasNext;
        this.hasPrevious = hasPrevious;
    }

    // Getters
    public int pageNumber() { return pageNumber; }
    public int pageSize() { return pageSize; }
    public boolean hasNext() { return hasNext; }
    public boolean hasPrevious() { return hasPrevious; }
}
