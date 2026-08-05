package com.bhanujavadev.ems.report.service.impl;

import com.bhanujavadev.ems.entity.Employee;
import com.bhanujavadev.ems.report.service.ReportService;
import com.bhanujavadev.ems.repository.EmployeeRepository;
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
}