package jasper;

import lombok.*;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private Date date;
    private float average;
    private float percentage;
    private float amount;
}
