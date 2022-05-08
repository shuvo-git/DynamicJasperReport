package testjasper2;

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.util.SortUtils;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.SQLException;
import java.util.*;

public class TestReport {

    protected static JasperPrint jp;
    protected static JasperReport jr;
    protected static Map params = new HashMap();
    protected static DynamicReport dr;

    public static void mmain(String args[]) throws SQLException, ColumnBuilderException, ClassNotFoundException {

        TestReport t = new TestReport();
        t.createReport();

    }

    public void createReport() throws SQLException, ColumnBuilderException, ClassNotFoundException {

        ArrayList<Fruit> createMockDataset = createMockDataset();

        Style titleStyle = new Style();
        titleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
        titleStyle.setFont(Font.ARIAL_SMALL_BOLD);

        Style dataStyle = new Style();
        dataStyle.setHorizontalAlign(HorizontalAlign.CENTER);
        dataStyle.setFont(Font.ARIAL_SMALL);
        dataStyle.setBlankWhenNull(true);

        final List items = SortUtils.sortCollection(createMockDataset, Arrays.asList(new String[]{"name", "description"}));

        FastReportBuilder drb = new FastReportBuilder();
        drb.setTemplateFile("templatePortrait.jrxml", true, true, true, true);
        drb.addColumn("name", "name", String.class.getName(), 30, dataStyle)
                .addColumn("description", "description", String.class.getName(), 50, dataStyle)
                .setTitle("Report")
                .setSubtitle("")
                .setPrintBackgroundOnOddRows(true)
                .setUseFullPageWidth(true);



        DynamicReport dynamicReport = drb.build();

        dynamicReport.setTitleStyle(titleStyle);

        HashMap parametros = new HashMap();
        //parametros.put("dataRelatorio", MyTools.getDataPorExtenso());

        doReport(dynamicReport, items, parametros);
    }

    public void doReport(final DynamicReport _report, final Collection _data, HashMap parametros) {
        try {
            JRDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(_data);

            JasperPrint jasperPrint = DynamicJasperHelper.generateJasperPrint(_report, new ClassicLayoutManager(), beanCollectionDataSource, parametros);

            JasperViewer.viewReport(jasperPrint);
        } catch (JRException ex) {
            ex.printStackTrace();
        }
    }


    public ArrayList<Fruit> createMockDataset() {
        ArrayList<Fruit> fruits = new ArrayList<>();

        Fruit f1 = new Fruit();
        f1.name = "Apple X1";
        f1.description = "Yummy yummy apple for the stackoverflow readers 1";

        Fruit f2 = new Fruit();
        f2.name = "Apple Ag";
        f2.description = "Yummy yummy apple for the stackoverflow readers 2";

        Fruit f3 = new Fruit();
        f3.name = "Apple Mn";
        f3.description = "Yummy yummy apple for the stackoverflow readers 3";

        Fruit f4 = new Fruit();
        f4.name = "Apple O2";
        f4.description = "Yummy yummy apple for the stackoverflow readers 4";

        //Evaluations for f1
        for (int i = 0; i < 4; i++) {
            Evaluation e = new Evaluation();
            e.id = i;
            e.score = Math.random() * 10;
            f1.evaluations.add(e);
        }

        //evaluations for f4
        for (int i = 0; i < 4; i++) {
            Evaluation e = new Evaluation();
            e.id = i;
            e.score = Math.random() * 10;
            f4.evaluations.add(e);
        }

        fruits.add(f1);
        fruits.add(f2);
        fruits.add(f3);
        fruits.add(f4);

        return fruits;

    }

    public class Fruit {

        public String name;
        public String description;
        public ArrayList<Evaluation> evaluations = new ArrayList<Evaluation>();

        public Fruit() {

        }

        public Fruit(String name, String description) {
            this.name = name;
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public ArrayList<Evaluation> getEvaluations() {
            return evaluations;
        }

        public void setEvaluations(ArrayList<Evaluation> evaluations) {
            this.evaluations = evaluations;
        }

    }

    public class Evaluation {

        public int id;
        public double score;

        public Evaluation() {

        }

        public Evaluation(int id, double score) {
            this.id = id;
            this.score = score;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }

    }

}