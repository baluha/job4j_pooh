package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueService implements Service {

    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> queue = new ConcurrentHashMap<>();
    @Override
    public Resp process(Req req) {
        String text = "";
        String status = Statuses.NOT_IMPL.getStatus();
        String name = req.getSourceName();
        if (RequestType.POST.getReq().equals(req.httpRequestType())) {
            queue.putIfAbsent(name, new ConcurrentLinkedQueue<>());
            queue.get(name).add(req.getParam());
            status = Statuses.OK.getStatus();
        }
        if (RequestType.GET.getReq().equals(req.httpRequestType())) {
            text = queue.get(name).poll();
            status = Statuses.NO_RESPONSE.getStatus();
            if (text == null) {
                text = "";
                status = Statuses.NO_RESPONSE.getStatus();
            }
        }
        return new Resp(text, status);
    }
}