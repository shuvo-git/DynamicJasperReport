package jasper;

import lombok.*;
import testjasper.dto.Statistic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
public class Product {
    private Long id;
    private String productLine;
    private String item;
    private String state;
    private String branch;
    private Long quantity;
    private Float amount;
    //private Code code = new Code();
    private Boolean showAsPrices;

    public static List<Statistic> statistics_ = new ArrayList<Statistic>();
    private static SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    static {

        try {
            statistics_.add(new Statistic(formatter.parse("01/01/2003"),"West",14.3f,50.4f,43.1f));
            statistics_.add(new Statistic(formatter.parse("01/01/2004"),"West",40.0f,49.4f,44.5f));
            statistics_.add(new Statistic(formatter.parse("01/01/2005"),"North",33.3f,63.4f,45f));
            statistics_.add(new Statistic(formatter.parse("01/01/2006"),"East",91.1f,34.4f,46f));
            statistics_.add(new Statistic(formatter.parse("01/01/2007"),"South",99.3f,52.4f,47f));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    public Product(Long id, String productLine, String item, String state, String branch, Long quantity, Float amount) {
        this.id = id;
        this.productLine = productLine;
        this.item = item;
        this.state = state;
        this.branch = branch;
        this.quantity = quantity;
        this.amount = amount;
    }

    public Product(Long id, String productLine, String item, String state, String branch, Long quantity, Float amount, Boolean showAsPrices) {
        this( id,  productLine,  item,  state,  branch,  quantity,  amount);
        this.showAsPrices = showAsPrices;
    }
}
