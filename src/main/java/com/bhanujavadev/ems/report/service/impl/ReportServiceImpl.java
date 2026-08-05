package com.bhanujavadev.ems.report.service.impl;

import com.bhanujavadev.ems.entity.Employee;
import com.bhanujavadev.ems.report.service.ReportService;
import com.bhanujavadev.ems.repository.EmployeeRepository;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final EmployeeRepository employeeRepository;

    @Override
    public byte[] exportEmployeesToExcel() {

        List<Employee> employees = employeeRepository.findAll();

        try (XSSFWorkbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            XSSFSheet sheet = workbook.createSheet("Employees");

            Row header = sheet.createRow(0);

            header.createCell(0).setCellValue("ID");
            header.createCell(1).setCellValue("Employee Code");
            header.createCell(2).setCellValue("First Name");
            header.createCell(3).setCellValue("Last Name");
            header.createCell(4).setCellValue("Email");
            header.createCell(5).setCellValue("Mobile");
            header.createCell(6).setCellValue("Department");
            header.createCell(7).setCellValue("Designation");
            header.createCell(8).setCellValue("Salary");
            header.createCell(9).setCellValue("Active");

            int rowNum = 1;

            for (Employee employee : employees) {

                Row row = sheet.createRow(rowNum++);

                row.createCell(0).setCellValue(employee.getId());

                row.createCell(1).setCellValue(employee.getEmployeeCode());

                row.createCell(2).setCellValue(employee.getFirstName());

                row.createCell(3).setCellValue(employee.getLastName());

                row.createCell(4).setCellValue(employee.getEmail());

                row.createCell(5).setCellValue(employee.getMobileNumber());

                row.createCell(6).setCellValue(
                        employee.getDepartment() != null
                                ? employee.getDepartment().getDepartmentName()
                                : ""
                );

                row.createCell(7).setCellValue(
                        employee.getDesignation() != null
                                ? employee.getDesignation().getDesignationName()
                                : ""
                );

                row.createCell(8).setCellValue(employee.getSalary().doubleValue());

                row.createCell(9).setCellValue(employee.getActive());
            }

            workbook.write(out);

            return out.toByteArray();

        } catch (IOException e) {

            throw new RuntimeException("Failed to export employees to Excel.", e);
        }
    }
    @Override
    public byte[] exportEmployeesToPdf() {

        List<Employee> employees = employeeRepository.findAll();

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Document document = new Document(PageSize.A4.rotate());

            PdfWriter.getInstance(document, out);

            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);

            Paragraph title = new Paragraph("Employee Management Report", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);

            document.add(title);
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(7);

            table.setWidthPercentage(100);

            table.setWidths(new float[]{1.5f, 2.5f, 3f, 4f, 3f, 3f, 2f});

            table.addCell("ID");
            table.addCell("Code");
            table.addCell("Name");
            table.addCell("Email");
            table.addCell("Department");
            table.addCell("Designation");
            table.addCell("Salary");

            for (Employee employee : employees) {

                table.addCell(String.valueOf(employee.getId()));
                table.addCell(employee.getEmployeeCode());
                table.addCell(employee.getFirstName() + " " + employee.getLastName());
                table.addCell(employee.getEmail());

                table.addCell(
                        employee.getDepartment() != null
                                ? employee.getDepartment().getDepartmentName()
                                : ""
                );

                table.addCell(
                        employee.getDesignation() != null
                                ? employee.getDesignation().getDesignationName()
                                : ""
                );

                table.addCell(employee.getSalary().toString());
            }

            document.add(table);

            document.close();

            return out.toByteArray();

        } catch (Exception e) {

            throw new RuntimeException("Failed to export PDF.", e);
        }
    }
}