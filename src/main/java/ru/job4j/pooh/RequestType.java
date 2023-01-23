package ru.job4j.pooh;

public enum RequestType {
    GET ("GET"),
    POST ("POST");

    private final String req;

    RequestType(String req) {
        this.req = req;
    }

    public String getReq() {
        return req;
    }

}
