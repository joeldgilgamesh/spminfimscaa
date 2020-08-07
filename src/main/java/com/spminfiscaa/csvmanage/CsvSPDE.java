package com.spminfiscaa.csvmanage;

import com.spminfiscaa.domain.ServPreDetteExt;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CsvSPDE {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String [] HEADERs = {"Bailleur", "Type financement", "Type  fond","Montant A Rembourser",  "Interet","Total", "Echeance", "Date"};
    static String SHEET = "SPD-EXT";

    public static boolean hasExcelFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return  true;
    }

    public static ByteArrayInputStream writeSend(List<ServPreDetteExt> servPreDetteExts){
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();){
            Sheet sheet = workbook.createSheet(SHEET);
            //Header
            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < HEADERs.length; col++){
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(HEADERs[col]);
            }
            int rowIdx = 1;
            for (ServPreDetteExt servPreDetteExt: servPreDetteExts){
                Row row = sheet.createRow(rowIdx++);

                if(servPreDetteExt.getBailleur() != null){
                    row.createCell(0).setCellValue(servPreDetteExt.getBailleur());
                }
                if(servPreDetteExt.getType_cooperation() != null){
                    row.createCell(1).setCellValue(servPreDetteExt.getType_cooperation());
                }
                if(servPreDetteExt.getType_fond() != null){
                    row.createCell(2).setCellValue(servPreDetteExt.getType_fond());
                }
                if(servPreDetteExt.getMontant_a_rembourser() != null){
                    row.createCell(3).setCellValue(servPreDetteExt.getMontant_a_rembourser());
                }
                if(servPreDetteExt.getInteret() != null){
                    row.createCell(4).setCellValue(servPreDetteExt.getInteret());
                }
                if(servPreDetteExt.getEcheance() != null){
                    row.createCell(5).setCellValue(servPreDetteExt.getTotal());
                }
                if(servPreDetteExt.getEcheance() != null){
                    row.createCell(6).setCellValue(servPreDetteExt.getEcheance().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy")));
                }
                if(servPreDetteExt.getDate() != null){
                    row.createCell(7).setCellValue(servPreDetteExt.getDate().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy")));
                }
            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }catch (IOException e){
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }
    public static List<ServPreDetteExt> readSend(InputStream is) {
        try {
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheet(SHEET);
            Iterator<Row> rows = sheet.iterator();

            List<ServPreDetteExt> servPreDetteExts = new ArrayList<ServPreDetteExt>();

            int rowNumber = 0;
            while (rows.hasNext()){
                Row currentRow = rows.next();
                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
            }
                Iterator<Cell> cellsInRow = currentRow.iterator();
                ServPreDetteExt servPreDetteExt = new ServPreDetteExt();

                int cellIdx = 0 ;
                while (cellsInRow.hasNext()){
                    Cell currentCell = cellsInRow.next();

                    switch (cellIdx) {
                        case 0:
                            servPreDetteExt.setBailleur(currentCell.getStringCellValue());
                            break;
                        case 1:
                            servPreDetteExt.setType_cooperation(currentCell.getStringCellValue());
                            break;
                        case 2:
                            servPreDetteExt.setType_fond(currentCell.getStringCellValue());
                            break;
                        case 3:
                            servPreDetteExt.setMontant_a_rembourser((int)currentCell.getNumericCellValue());
                            break;
                        case 4:
                            servPreDetteExt.setInteret((int)currentCell.getNumericCellValue());
                            break;
                        case 5:
                            servPreDetteExt.setTotal((long)currentCell.getNumericCellValue());
                            break;
                        case 6:
                            servPreDetteExt.setEcheance(LocalDate.parse(currentCell.toString(), DateTimeFormatter.ofPattern("dd-MMM-yyyy")));
                            break;
                        case 7 :
                            servPreDetteExt.setDate(LocalDate.parse(currentCell.toString(), DateTimeFormatter.ofPattern("dd-MMM-yyyy")));
                            break;
                        default:
                            break;
                    }
                    cellIdx++;
                }
                servPreDetteExts.add(servPreDetteExt);
        }
            workbook.close();
            System.out.println("####################################################################");
            System.out.println(servPreDetteExts);
            System.out.println("####################################################################");
            return servPreDetteExts;
    } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }

}
