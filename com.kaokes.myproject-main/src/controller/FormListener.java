package controller;

import java.util.EventListener;

public interface FormListener extends EventListener {
    void formEventOccurred(FormEvent formEvent);
}
