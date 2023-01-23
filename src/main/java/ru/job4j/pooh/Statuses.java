package ru.job4j.pooh;

public enum Statuses {
    NOT_IMPL("501"),
    OK("200"),
    NO_RESPONSE("204");

    private final String status;

    Statuses(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
