package com.bhanujavadev.ems.report.service;

public interface ReportService {

    byte[] exportEmployeesToExcel();
    byte[] exportEmployeesToPdf();

}