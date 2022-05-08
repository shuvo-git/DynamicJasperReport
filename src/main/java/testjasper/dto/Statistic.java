package testjasper.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Statistic {
    private Date date;
    private String name;
    private float percentage;
    private float average;
    private float amount;

    @Getter
    public static List<DummyLevel3> dummy3 = new ArrayList<DummyLevel3>();

    static {
        dummy3.add(new DummyLevel3("name1", 1L));
        dummy3.add(new DummyLevel3("name2", 2L));
        dummy3.add(new DummyLevel3("name3", 3L));
        dummy3.add(new DummyLevel3("name3", 4L));
    }


}
