package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TopicService implements Service {

    private final ConcurrentHashMap<String, ConcurrentHashMap<String,
            ConcurrentLinkedQueue<String>>> topics = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        String text = "";
        String status = Statuses.NOT_IMPL.getStatus();
        String souseName = req.getSourceName();
        if (RequestType.GET.getReq().equals(req.httpRequestType())) {
            topics.putIfAbsent(souseName, new ConcurrentHashMap<>());
            topics.get(souseName).putIfAbsent(req.getParam(), new ConcurrentLinkedQueue<>());
            text = topics.get(souseName).get(req.getParam()).poll();
            if (text == null) {
                text = "";
                status = Statuses.NO_RESPONSE.getStatus();
            } else {
                status = Statuses.OK.getStatus();
            }
        }
        if (RequestType.POST.getReq().equals(req.httpRequestType())) {
            var hashMap = topics.get(souseName);
            if (hashMap != null) {
                for (ConcurrentLinkedQueue<String> queue
                        : topics.getOrDefault(souseName, new ConcurrentHashMap<>()).values()) {
                    queue.add(req.getParam());
                    text = req.getParam();
                    status = Statuses.NO_RESPONSE.getStatus();
                }
            }
        }
        return new Resp(text, status);
    }
}