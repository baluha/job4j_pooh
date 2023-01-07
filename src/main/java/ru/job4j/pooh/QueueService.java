package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueService implements Service {
    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> queue = new ConcurrentHashMap<>();
    @Override
    public Resp process(Req req) {
        String text = "";
        String status = "501";
        String name = req.getSourceName();
        if ("POST".equals(req.httpRequestType())) {
            queue.putIfAbsent(name, new ConcurrentLinkedQueue<>());
            queue.get(name).add(req.getParam());
            status = "200";
        }
        if ("GET".equals(req.httpRequestType())) {
            text = queue.get(name).poll();
            status = "204";
            if (text == null) {
                text = "";
                status = "204";
            }
        }
        return new Resp(text, status);
    }
}