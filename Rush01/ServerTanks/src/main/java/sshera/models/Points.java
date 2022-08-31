package sshera.models;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Points {
    private Integer numberClient;
    private Integer shot;
    private Integer hit;

    public Points() {
    }

    @Override
    public String toString() {
        return "Points{" +
                "client='" + numberClient + '\'' +
                ", shot=" + shot +
                ", hit=" + hit +
                '}';
    }
}
