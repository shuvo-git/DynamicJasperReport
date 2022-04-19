package jasper;

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.builders.StyleBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Transparency;
import ar.com.fdvs.dj.domain.constants.VerticalAlign;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

public class EmployeeReport {

    private final Collection<Employee> list = new ArrayList<>();

    public EmployeeReport(Collection<Employee> c) {
        list.addAll(c);
    }

    public JasperPrint getReport() throws ColumnBuilderException, JRException, ClassNotFoundException
    {
        Style headerStyle = createHeaderStyle();
        Style detailTextStyle = createDetailTextStyle();
        Style detailNumberStyle = createDetailNumberStyle();
        DynamicReport dynaReport = getReport(headerStyle, detailTextStyle,detailNumberStyle);
        JasperPrint jp = DynamicJasperHelper.generateJasperPrint(dynaReport, new ClassicLayoutManager(), new JRBeanCollectionDataSource(list));
        return jp;
    }

    private Style createHeaderStyle() {
        return new StyleBuilder(true)
        .setFont(Font.VERDANA_MEDIUM_BOLD)
        .setBorder(Border.THIN())
        .setBorderBottom(Border.PEN_2_POINT())
        .setBorderColor(Color.BLACK)
        .setBackgroundColor(Color.CYAN)
        .setTextColor(Color.BLACK)
        .setHorizontalAlign(HorizontalAlign.CENTER)
        .setVerticalAlign(VerticalAlign.MIDDLE)
        .setTransparency((ar.com.fdvs.dj.domain.constants.Transparency) Transparency.OPAQUE)
        .build();
    }

    private Style createDetailTextStyle(){
        return new StyleBuilder(true)
        .setFont(Font.VERDANA_MEDIUM)
        .setBorder(Border.THIN())
        .setBorderColor(Color.BLACK)
        .setTextColor(Color.BLACK)
        .setHorizontalAlign(HorizontalAlign.LEFT)
        .setVerticalAlign(VerticalAlign.MIDDLE)
        .setPaddingLeft(5)
        .build();
    }

    private Style createDetailNumberStyle(){
        return new StyleBuilder(true)
        .setFont(Font.VERDANA_MEDIUM)
        .setBorder(Border.THIN())
        .setBorderColor(Color.BLACK)
        .setTextColor(Color.BLACK)
        .setHorizontalAlign(HorizontalAlign.RIGHT)
        .setVerticalAlign(VerticalAlign.MIDDLE)
        .setPaddingRight(5)
        .build();
    }

    private Style createTitleStyle()
    {
        return new StyleBuilder(true)
        .setHorizontalAlign(HorizontalAlign.CENTER)
        .setFont(new Font(20, Font._FONT_GEORGIA, true))
        .build();
    }

    private Style createSubTitleStyle()
    {
        return new StyleBuilder(true)
        .setHorizontalAlign(HorizontalAlign.CENTER)
        .setFont(new Font(Font.MEDIUM, Font._FONT_GEORGIA, true))
        .setPaddingBottom(10)
        .build();
    }



    private AbstractColumn createColumn(String property, Class type, String title, int width, Style headerStyle, Style detailStyle) throws ColumnBuilderException
    {
        return ColumnBuilder.getNew()
        .setColumnProperty(property, type.getName())
        .setTitle(title).setWidth(Integer.valueOf(width))
        .setStyle(detailStyle)
        .setHeaderStyle(headerStyle).build();

    }

    private DynamicReport getReport(Style headerStyle, Style detailTextStyle, Style detailNumStyle)
            throws ColumnBuilderException, ClassNotFoundException
    {
        AbstractColumn columnEmpNo = createColumn("empNo", Integer.class,"Employee Number", 30, headerStyle, detailTextStyle);
        AbstractColumn columnName = createColumn("name", String.class,"Name", 30, headerStyle, detailTextStyle);
        AbstractColumn columnSalary = createColumn("salary", Integer.class,"Salary", 30, headerStyle, detailNumStyle);
        AbstractColumn columnCommission = createColumn("commission", Float.class,"Commission", 30, headerStyle, detailNumStyle);

        return new DynamicReportBuilder()
            .addColumn(columnEmpNo)
            .addColumn(columnName)
            .addColumn(columnSalary)
            .addColumn(columnCommission)
            .setTitle("Employee Report")
            .setTitleStyle(createTitleStyle())
            .setSubtitle("Commission received by Employee")
            .setSubtitleStyle(createSubTitleStyle())
            .setUseFullPageWidth(true)
            .build();
    }
}