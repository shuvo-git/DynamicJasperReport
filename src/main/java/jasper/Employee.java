package jasper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee implements Serializable
{
    private int empNo;
    private String name;
    private int salary;
    private float commission;
}