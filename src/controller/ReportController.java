package controller;

import javafx.collections.ObservableList;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import view.tm.OrderTM;

import java.util.ArrayList;
import java.util.HashMap;

public class ReportController {
    public void printKOT(ObservableList<OrderTM> list,String orderNo,String customerName,String customerMobile,String orderType) throws JRException {
        JasperDesign load = JRXmlLoader.load(this.getClass().getResourceAsStream("../view/reports/KOT.jrxml"));
        JasperReport jasperReport = JasperCompileManager.compileReport(load);
        HashMap map=new HashMap();
        map.put("orderNo",orderNo);
        map.put("customerName",customerName);
        map.put("customerMobile",customerMobile);
        map.put("orderType",orderType);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, new JRBeanArrayDataSource(list.toArray()));
        JasperViewer.viewReport(jasperPrint,false);
    }
    public void printBill(ObservableList<OrderTM> list,String orderNo,String customerName,String customerMobile,String orderType,String subTotal,String deliveryCharges,String grandTotal,String cash,String balance) throws JRException {
        JasperDesign load = JRXmlLoader.load(this.getClass().getResourceAsStream("../view/reports/BILL.jrxml"));
        JasperReport jasperReport = JasperCompileManager.compileReport(load);
        HashMap map=new HashMap();
        map.put("orderNo",orderNo);
        map.put("customerName",customerName);
        map.put("customerMobile",customerMobile);
        map.put("orderType",orderType);
        map.put("subTotal",subTotal);
        map.put("deliveryCharges",deliveryCharges);
        map.put("grandTotal",grandTotal);
        map.put("cash",cash);
        map.put("balance",balance);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, new JRBeanArrayDataSource(list.toArray()));
        JasperViewer.viewReport(jasperPrint,false);
    }

}
