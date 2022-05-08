package jasper;

import ar.com.fdvs.dj.core.DJConstants;
import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.JasperDesignDecorator;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.AutoText;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.ImageBanner;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.*;
import ar.com.fdvs.dj.domain.constants.*;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.Transparency;
import ar.com.fdvs.dj.domain.entities.DJGroup;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRImageAlignment;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import javax.xml.transform.Templates;
import java.awt.*;
import java.util.*;

public class EmployeeReport {

    private final Collection<Employee> list = new ArrayList<>();
    Map<String, Object> params = new HashMap<String, Object>();


    public EmployeeReport(Collection<Employee> c) {
        list.addAll(c);
        params.put("statistics", Product.statistics_ );
        params.put("employeeList", list );
    }

    public JasperPrint getReport() throws ColumnBuilderException, JRException, ClassNotFoundException
    {
        Style headerStyle = createHeaderStyle();
        Style detailTextStyle = createDetailTextStyle();
        Style detailNumberStyle = createDetailNumberStyle();
        DynamicReport dynaReport = getReport(headerStyle, detailTextStyle,detailNumberStyle);
        JasperPrint jp = DynamicJasperHelper.generateJasperPrint(dynaReport, new ClassicLayoutManager(), params);
                //new JRBeanCollectionDataSource(list));
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
        .setFont(new Font(30, Font._FONT_GEORGIA, true))
        .build();
    }

    private Style createSubTitleStyle()
    {
        return new StyleBuilder(true)
                .setVerticalAlign(VerticalAlign.TOP)
        .setHorizontalAlign(HorizontalAlign.CENTER)
        .setFont(new Font(Font.MEDIUM, Font._FONT_GEORGIA, true))
        .setPaddingBottom(10)
        .build();
    }



    private AbstractColumn createColumn(String property,
                                        Class type,
                                        String title,
                                        int width,
                                        Style headerStyle,
                                        Style detailStyle)
            throws ColumnBuilderException
    {
        return ColumnBuilder.getNew()
        .setColumnProperty(property, type.getName())
        .setTitle(title).setWidth(Integer.valueOf(width))
        .setStyle(detailStyle)
        .setHeaderStyle(headerStyle).build();

    }

    private DynamicReport createSubreport1(String title) {
        FastReportBuilder rb = new FastReportBuilder();

        DynamicReport dr = null;


        try {
            AbstractColumn columnEmpNo = createColumn("empNo", Integer.class,"Employee Number", 30, createHeaderStyle(), createDetailTextStyle());
            AbstractColumn columnName = createColumn("name", String.class,"Name", 30, createHeaderStyle(), createDetailTextStyle());
            AbstractColumn columnSalary = createColumn("salary", Integer.class,"Salary", 30, createHeaderStyle(), createDetailNumberStyle());
            AbstractColumn columnCommission = createColumn("commission", Float.class,"Commission", 30, createHeaderStyle(), createDetailNumberStyle());

            DJGroup g = new GroupBuilder()
                    .setCriteriaColumn((PropertyColumn) columnEmpNo)
                    .setGroupLayout(GroupLayout.VALUE_IN_HEADER)
                    .build();
            dr = rb
                    .addColumn(columnEmpNo)
                    .addColumn(columnName)
                    .addColumn(columnSalary)
                    .addColumn(columnCommission)
                    .addGroup(g)
                    .setMargins(5, 5, 20, 20)
                    .setUseFullPageWidth(true)
                    .setTitle(title)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dr;
    }

    /**
     * Creates a dynamic reports and compiles it in order to be used
     * as a subreport
     * @return
     * @throws Exception
     */
    private JasperReport createSubreport2(String title) {
        FastReportBuilder rb = new FastReportBuilder();
        DynamicReport dr = null;
        try {
            AbstractColumn nameCol = createColumn("name",String.class,"Area",30,createHeaderStyle(),createDetailTextStyle());
            AbstractColumn averageCol = createColumn("average",Float.class,"Average",30,createHeaderStyle(),createDetailTextStyle());
            AbstractColumn percentageCol = createColumn("percentage",Float.class,"%",30,createHeaderStyle(),createDetailTextStyle());
            AbstractColumn amountCol = createColumn("amount",Float.class,"Amount",30,createHeaderStyle(),createDetailTextStyle());
            dr = rb.addColumn(nameCol)
                    .addColumn(averageCol)
                    .addColumn(percentageCol)
                    .addColumn(amountCol)
                    .setMargins(5, 5, 20, 20)
                    .setUseFullPageWidth(true)
                    .setTitle(title)
                    .build();
            return DynamicJasperHelper.generateJasperReport(dr, new ClassicLayoutManager(),null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }

    private DynamicReport getReport(Style headerStyle, Style detailTextStyle, Style detailNumStyle)
            throws ColumnBuilderException, ClassNotFoundException
    {



        Integer margin = 20;



        return new DynamicReportBuilder()
                .setTemplateFile("extranetCountryWiseReport.jrxml",true,true,true,true)
//                .addFirstPageImageBanner("prb-logo.png", 50, 50, ImageBanner.Alignment.Left)
//                .addFirstPageImageBanner("eu-logo.jpg", 50, 50, ImageBanner.Alignment.Right)
//                .setTitleStyle(createTitleStyle())
//                .setTitle("Employee Report")
//                .setSubtitleStyle(createSubTitleStyle())
//                .setSubtitle("Commission received by Employee")



                .setDetailHeight(15)
                .setLeftMargin(margin)
                .setRightMargin(margin)
                .setTopMargin(margin)
                .setBottomMargin(margin)
                .setUseFullPageWidth(true)
                .setWhenNoDataAllSectionNoDetail()
                .addAutoText(AutoText.AUTOTEXT_PAGE_X_OF_Y, AutoText.POSITION_FOOTER,AutoText.ALIGNMENT_CENTER)

//                .addColumn(columnEmpNo)
//                .addColumn(columnName)
//                .addColumn(columnSalary)
//                .addColumn(columnCommission)

                /*.addGroup(g)*/
                .addConcatenatedReport(
                        createSubreport1("Sub report 1"),
                        new ClassicLayoutManager(),
                        "employeeList",
                        DJConstants.DATA_SOURCE_ORIGIN_PARAMETER,
                        DJConstants.DATA_SOURCE_TYPE_COLLECTION,
                        false
                )

                .addConcatenatedReport(
                    createSubreport2("Sub report 2"),
                    //new ClassicLayoutManager(),
                    "statistics",
                    DJConstants.DATA_SOURCE_ORIGIN_PARAMETER,
                    DJConstants.DATA_SOURCE_TYPE_COLLECTION,
                    false
                ).build();




    }
}