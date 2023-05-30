package com.FinalProject.Service.Impl;

import com.FinalProject.Mapper.AnalysisMapper;
import com.FinalProject.Pojo.Analysis;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
@Service
@Transactional
public class ExcelToDatabase {
    @Autowired
    private AnalysisMapper analysisMapper;

    public ExcelToDatabase(AnalysisMapper analysisMapper) {
        this.analysisMapper = analysisMapper;
    }

    public String getCellStringValue(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                // Convert numeric value to string
                return Double.toString(cell.getNumericCellValue());
            case BOOLEAN:
                // Convert boolean value to string
                return Boolean.toString(cell.getBooleanCellValue());
            default:
                return "";
        }
    }

    public  void readExcelAndInsertIntoDatabase(String excelFilePath) throws IOException {
        FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet firstSheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = firstSheet.iterator();

        // Skip the first row because you want to start from the second row
        if (iterator.hasNext()) {
            iterator.next();
        }

        while (iterator.hasNext()) {
            Row nextRow = iterator.next();
            Iterator<Cell> cellIterator = nextRow.cellIterator();

            Analysis analysis = new Analysis();
            analysis.setName(cellIterator.next().getStringCellValue());
            analysis.setAge(getCellStringValue(cellIterator.next()));
            analysis.setDegree(cellIterator.next().getStringCellValue());
            analysis.setGraduation(cellIterator.next().getStringCellValue());
            analysis.setWork_years(getCellStringValue(cellIterator.next()));
            analysis.setTele(cellIterator.next().getStringCellValue());
            analysis.setProject_name(cellIterator.next().getStringCellValue());
            analysis.setProject_time(cellIterator.next().getStringCellValue());
            analysis.setGraduation_time(cellIterator.next().getStringCellValue());
            analysis.setFilename(cellIterator.next().getStringCellValue());

            if (!analysisMapper.isFilenameExists(analysis.getFilename())) {
                analysisMapper.insertAnalysis(analysis);
            }




        }

        workbook.close();
        inputStream.close();
    }
}
