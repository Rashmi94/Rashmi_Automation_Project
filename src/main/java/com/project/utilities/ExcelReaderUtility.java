package com.project.utilities;

import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.apache.poi.ss.usermodel.CellType.*;

public class ExcelReaderUtility {

    public static List<String[]> getSheetData(String filePath,String sheetName)
    {
        List<String[]> data = new ArrayList<>();
        try {
        FileInputStream fis = new FileInputStream(filePath);
           Workbook wb =  WorkbookFactory.create(fis);
           Sheet sheet = wb.getSheet(sheetName);
           if(sheet==null)
               throw new IllegalArgumentException();
           for(Row row : sheet) {
               if (row.getRowNum() == 0)
                   continue;
               List<String> rowData = new ArrayList<String>();
               for(Cell cell : row)
               {
                   rowData.add(getStringCellValue(cell));
               }
               data.add(rowData.toArray(new String[0]));
           }
           return data;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static String getStringCellValue(Cell cell )
    {
        if(cell==null)
            return "";
        switch(cell.getCellType())
        {
            case STRING :
                return cell.getStringCellValue();

            case NUMERIC:
                if(DateUtil.isCellDateFormatted(cell))
                    return cell.getDateCellValue().toString();
                else
                    return String.valueOf(cell.getNumericCellValue());

            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return "";
        }
    }}
