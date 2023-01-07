package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TopicService implements Service {

    private final ConcurrentHashMap<String, ConcurrentHashMap<String,
            ConcurrentLinkedQueue<String>>> topics = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        String text = "";
        String status = "501";
        String souseName = req.getSourceName();
        if ("GET".equals(req.httpRequestType())) {
            topics.putIfAbsent(souseName, new ConcurrentHashMap<>());
            topics.get(souseName).putIfAbsent(req.getParam(), new ConcurrentLinkedQueue<>());
            text = topics.get(souseName).get(req.getParam()).poll();
            if (text == null) {
                text = "";
                status = "204";
            } else {
                status = "200";
            }
        }
        if ("POST".equals(req.httpRequestType())) {
            var hashMap = topics.get(souseName);
            if (hashMap != null) {
                for (ConcurrentLinkedQueue<String> queue
                        : topics.getOrDefault(souseName, new ConcurrentHashMap<>()).values()) {
                    queue.add(req.getParam());
                    text = req.getParam();
                    status = "204";
                }
            }
        }
        return new Resp(text, status);
    }
}