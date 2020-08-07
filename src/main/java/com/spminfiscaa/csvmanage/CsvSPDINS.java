package com.spminfiscaa.csvmanage;

import com.spminfiscaa.domain.ServPreDetteIntNoStruc;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CsvSPDINS {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERs = {"Categorie", "Montant", "Echéance", "Date"};
    static String SHEET = "SPD-INT-Non-Structuré";

    public static boolean hasExcelFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }


    public static ByteArrayInputStream writeSend(List<ServPreDetteIntNoStruc> servPreDetteIntNoStrucs) {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            Sheet sheet = workbook.createSheet(SHEET);

            //Heeader
            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < HEADERs.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(HEADERs[col]);
            }

            int rowIdx = 1;
            for (ServPreDetteIntNoStruc servPreDetteIntNoStruc : servPreDetteIntNoStrucs) {
                Row row = sheet.createRow(rowIdx++);

                if (servPreDetteIntNoStruc.getCategorie() != null) {
                    row.createCell(0).setCellValue(servPreDetteIntNoStruc.getCategorie().toString());
                }
                if(servPreDetteIntNoStruc.getTotal() != null) {
                    row.createCell(1).setCellValue(servPreDetteIntNoStruc.getTotal().toString());

                }
                if(servPreDetteIntNoStruc.getEcheance() != null) {
                    row.createCell(2).setCellValue(servPreDetteIntNoStruc.getEcheance().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy")));

                }
                if(servPreDetteIntNoStruc.getDate() != null) {
                    row.createCell(3).setCellValue(servPreDetteIntNoStruc.getDate().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy")));

                }




            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }

    public static List<ServPreDetteIntNoStruc> readSend(InputStream is) {
        try {
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheet(SHEET);
            Iterator<Row> rows = sheet.iterator();

            List<ServPreDetteIntNoStruc> solEngNonDecs = new ArrayList<ServPreDetteIntNoStruc>();


            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cellsInRow = currentRow.iterator();

                ServPreDetteIntNoStruc servPreDetteIntNoStruc = new ServPreDetteIntNoStruc();

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIdx) {
                        case 0:
                            servPreDetteIntNoStruc.setCategorie(currentCell.getStringCellValue());
                            break;
                        case 1:
                            servPreDetteIntNoStruc.setTotal((long) currentCell.getNumericCellValue());
                            break;
                        case 2:
                            servPreDetteIntNoStruc.setEcheance(LocalDate.parse(currentCell.toString(), DateTimeFormatter.ofPattern("dd-MMM-yyyy")));
                            break;
                        case 3:
                            servPreDetteIntNoStruc.setDate(LocalDate.parse(currentCell.toString(), DateTimeFormatter.ofPattern("dd-MMM-yyyy")));
                            break;

                        default:
                            break;
                    }
                    cellIdx++;
                }
                solEngNonDecs.add(servPreDetteIntNoStruc);
            }
            workbook.close();
            return solEngNonDecs;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }
}
