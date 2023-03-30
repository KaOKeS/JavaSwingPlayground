package controller;

import model.Message;

import java.util.*;

/*
This is sort of simulated message server
 */
public class MessageServer implements Iterable<Message> {
    private final Map<Integer, List<Message>> messages = new TreeMap<>();

    private final List<Message> selected;

    public MessageServer() {
        selected = new ArrayList<>();

        List<Message> list = new ArrayList<>();
        list.add(new Message("The cat is missing", "Have you seen Felix anywhere?"));
        list.add(new Message("See you later?", "Are we still meeting in the park?"));
        messages.put(0, list);

        list = new ArrayList<>();
        list.add(new Message("Are we fishing today?", "Lets go"));
        messages.put(1, list);
    }

    public void setSelectedServers(Set<Integer> serversId) {
        selected.clear();
        for (Integer id : serversId) {
            if (messages.containsKey(id)) {
                selected.addAll(messages.get(id));
            }
        }
    }

    public int getMessageCount() {
        return selected.size();
    }

    @Override
    public Iterator<Message> iterator() {
        return new MessageIterator(selected);
    }
}

class MessageIterator implements Iterator<Message> {
    private final Iterator<Message> iterator;

    public MessageIterator(List<Message> messages) {
        this.iterator = messages.iterator();
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public Message next() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return iterator.next();
    }

    @Override
    public void remove() {
        iterator.remove();
    }
}
