package com.spminfiscaa.csvmanage;

import com.spminfiscaa.domain.ServPreDetteIntStruc;
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

public class CsvSPDIS {
    //    public static String type = "csv";
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERs = {"Organisme", "Categorie", "Groupe", "Principal", "Interet","Total","Echeance","Date"};
    static String SHEET = "SPD-INT-Structuré";

    public static boolean hasExcelFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }


    public static ByteArrayInputStream writeSend(List<ServPreDetteIntStruc> servPreDetteIntStrucs) {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            Sheet sheet = workbook.createSheet(SHEET);

            //Heeader
            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < HEADERs.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(HEADERs[col]);
            }

            int rowIdx = 1;
            for (ServPreDetteIntStruc servPreDetteIntStruc : servPreDetteIntStrucs) {
                Row row = sheet.createRow(rowIdx++);

                if (servPreDetteIntStruc.getOrganisme() != null) {
                    row.createCell(0).setCellValue(servPreDetteIntStruc.getCategorie().toString());
                }
                if(servPreDetteIntStruc.getCategorie() != null) {
                    row.createCell(1).setCellValue(servPreDetteIntStruc.getCategorie().toString());
                }
                if(servPreDetteIntStruc.getGroupe() != null) {
                    row.createCell(2).setCellValue(servPreDetteIntStruc.getPrincipale().toString());
                }
                if(servPreDetteIntStruc.getPrincipale() != null) {
                    row.createCell(3).setCellValue(servPreDetteIntStruc.getPrincipale().toString());
                }
                if(servPreDetteIntStruc.getInteret() != null) {
                    row.createCell(4).setCellValue(servPreDetteIntStruc.getInteret().toString());
                }
                if(servPreDetteIntStruc.getTotal() != null) {
                    row.createCell(5).setCellValue(servPreDetteIntStruc.getTotal().toString());
                }
                if(servPreDetteIntStruc.getEcheance() != null) {
                    row.createCell(6).setCellValue(servPreDetteIntStruc.getEcheance().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy")));
                }
                if(servPreDetteIntStruc.getDate() != null) {
                    row.createCell(7).setCellValue(servPreDetteIntStruc.getDate().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy")));
                }

            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }

    public static List<ServPreDetteIntStruc> readSend(InputStream is) {
        try {
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheet(SHEET);
            Iterator<Row> rows = sheet.iterator();

            List<ServPreDetteIntStruc> servPreDetteIntStrucs = new ArrayList<ServPreDetteIntStruc>();


            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cellsInRow = currentRow.iterator();

                ServPreDetteIntStruc servPreDetteIntStruc = new ServPreDetteIntStruc();

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIdx) {
                        case 0:
                            servPreDetteIntStruc.setOrganisme(currentCell.getStringCellValue());
                            break;
                        case 1:
                            servPreDetteIntStruc.setCategorie(currentCell.getStringCellValue());
                            break;
                        case 2:
                            servPreDetteIntStruc.setGroupe(currentCell.getStringCellValue());
                            break;
                        case 3:
                            servPreDetteIntStruc.setPrincipale((int)currentCell.getNumericCellValue());
                            break;
                        case 4:
                            servPreDetteIntStruc.setInteret((int)currentCell.getNumericCellValue());
                            break;
                        case 5:
                            servPreDetteIntStruc.setTotal((long)currentCell.getNumericCellValue());
                            break;
                        case 6:
                            servPreDetteIntStruc.setEcheance(LocalDate.parse(currentCell.toString(), DateTimeFormatter.ofPattern("dd-MMM-yyyy")));
                            break;
                        case 7:
                            servPreDetteIntStruc.setDate(LocalDate.parse(currentCell.toString(), DateTimeFormatter.ofPattern("dd-MMM-yyyy")));
                            break;
                        default:
                            break;
                    }
                    cellIdx++;
                }
                servPreDetteIntStrucs.add(servPreDetteIntStruc);
                System.out.println("####################################################################");
                System.out.println(servPreDetteIntStrucs);
                System.out.println("####################################################################");

            }
            workbook.close();
            return servPreDetteIntStrucs;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }
}
