package com.pokerfans.excelsums.util;

import org.apache.poi.ss.usermodel.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtil {

    public static List<Double> readFirstColumn(InputStream inputStream) {
        List<Double> numbers = new ArrayList<>();

        try (Workbook workbook = WorkbookFactory.create(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                Cell cell = row.getCell(0);
                if (cell == null) continue;

                if (cell.getCellType() == CellType.NUMERIC) {
                    numbers.add(cell.getNumericCellValue()); // double
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("读取 Excel 失败", e);
        }

        return numbers;
    }
}
