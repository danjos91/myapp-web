package ru.yandex.practicum.util;

public class Paging {
    private final int pageNumber;
    private final int pageSize;
    private final int totalPages;
    private final boolean hasNext;
    private final boolean hasPrevious;

    // Constructor, getters y otros métodos...
    public Paging(int pageNumber, int pageSize, int totalPages, boolean hasNext, boolean hasPrevious) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalPages = totalPages;
        this.hasNext = hasNext;
        this.hasPrevious = hasPrevious;
    }

    // Getters
    public int pageNumber() { return pageNumber; }
    public int pageSize() { return pageSize; }
    public boolean hasNext() { return hasNext; }
    public boolean hasPrevious() { return hasPrevious; }
}
