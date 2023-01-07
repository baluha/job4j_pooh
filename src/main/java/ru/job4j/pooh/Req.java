package ru.job4j.pooh;

public class Req {


    private final String httpRequestType;
    private final String poohMode;
    private final String sourceName;
    private final String param;

    public Req(String httpRequestType, String poohMode, String sourceName, String param) {
        this.httpRequestType = httpRequestType;
        this.poohMode = poohMode;
        this.sourceName = sourceName;
        this.param = param;
    }

    public static Req of(String content) {
        String ls = System.lineSeparator();
        String httpRequestType = content.split(ls)[0].split(" ")[0];
        String poohMode = content.split(ls)[0].split("/")[1];
        String sourceName = content.split(ls)[0].split("/")[2].split(" ")[0];
        String param = null;
        if ("POST".equals(httpRequestType)) {
            param = content.split(ls)[content.split(ls).length - 1];
        } else if ("GET".equals(httpRequestType)) {
            if (content.split(ls)[0].split("/").length == 4) {
                param = "";
            }
            if (content.split(ls)[0].split("/").length == 5) {
                param = content.split(ls)[0].split("/")[3].split(" ")[0];
            }
        }
        return new Req(httpRequestType, poohMode, sourceName, param);
    }

    public String httpRequestType() {
        return httpRequestType;
    }

    public String getPoohMode() {
        return poohMode;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getParam() {
        return param;
    }
}
