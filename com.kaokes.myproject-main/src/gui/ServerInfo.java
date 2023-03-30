package gui;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class ServerInfo {
    private String name;
    @Getter
    private Long id;
    @Setter
    @Getter
    private boolean checked;

    @Override
    public String toString() {
        return name;
    }
}
