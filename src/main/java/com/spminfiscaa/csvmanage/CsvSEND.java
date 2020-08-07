
package com.spminfiscaa.csvmanage;

import com.spminfiscaa.domain.SolEngNonDec;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CsvSEND {
    //    public static String type = "csv";
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERs = {"bailleur", "type_cooperation", "type_fond", "solde", "commission", "date"};
    static String SHEET = "Solde_Engage_Non_Declarer";

    public static boolean hasExcelFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }

    /*   public static List<SolEngNonDec> readSend(InputStream is) {
           try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                CSVParser csvParser = new CSVParser(fileReader,
                    CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
               List<SolEngNonDec> solEngNonDecs = new ArrayList<SolEngNonDec>();
               Iterable<CSVRecord> csv = csvParser.getRecords();
               for (CSVRecord csvRecord : csv) {
                   SolEngNonDec solEngNonDec = new SolEngNonDec(
                       Long.parseLong(csvRecord.get("Id")),
                       csvRecord.get("bailleur"),
                       csvRecord.get("type_cooperation"),
                       csvRecord.get("type_fond"),
                       csvRecord.get("solde"),
                       LocalDate.parse(csvRecord.get("date")),
                       csvRecord.get("commission")

                   );
                   solEngNonDecs.add(solEngNonDec);
               }
               return solEngNonDecs;
           } catch (IOException e) {
               throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
           }
       }

       public static ByteArrayInputStream writeSend(List<SolEngNonDec> solEngNonDecs) {
           final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);
           try (ByteArrayOutputStream out = new ByteArrayOutputStream();
                CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
               for (SolEngNonDec solEngNonDec : solEngNonDecs) {
                   List<String> data = Arrays.asList(
                       solEngNonDec.getBailleur(),
                       solEngNonDec.getType_cooperation(),
                       solEngNonDec.getType_fond(),
                       String.valueOf(solEngNonDec.getSolde()),
                       String.valueOf(solEngNonDec.getDate()),
                       String.valueOf(solEngNonDec.getCommission())

                   );
                   csvPrinter.printRecord(data);
               }
               csvPrinter.flush();
               return new ByteArrayInputStream(out.toByteArray());
           } catch (IOException e) {
               throw new RuntimeException("fail to import data to CSV file:" + e.getMessage());
           }
       }*/
    public static ByteArrayInputStream writeSend(List<SolEngNonDec> solEngNonDecs) {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            Sheet sheet = workbook.createSheet(SHEET);

            //Heeader
            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < HEADERs.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(HEADERs[col]);
            }

            int rowIdx = 1;
            for (SolEngNonDec solEngNonDec : solEngNonDecs) {
                Row row = sheet.createRow(rowIdx++);

                if (solEngNonDec.getBailleur() != null) {
                    row.createCell(0).setCellValue(solEngNonDec.getBailleur().toString());
                }
                if(solEngNonDec.getType_cooperation() != null) {
                    row.createCell(1).setCellValue(solEngNonDec.getType_cooperation().toString());
                }
                if(solEngNonDec.getType_fond() != null) {
                    row.createCell(2).setCellValue(solEngNonDec.getType_fond().toString());
                }
                if(solEngNonDec.getSolde() != null) {
                    row.createCell(3).setCellValue(solEngNonDec.getSolde().toString());
                }

                if(solEngNonDec.getCommission() != null) {
                    row.createCell(4).setCellValue(solEngNonDec.getCommission().toString());
                }
                if(solEngNonDec.getDate() != null) {
                    row.createCell(5).setCellValue(solEngNonDec.getDate().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy")));
                }
            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }

    public static List<SolEngNonDec> readSend(InputStream is) {
        try {
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheet(SHEET);
            Iterator<Row> rows = sheet.iterator();

            List<SolEngNonDec> solEngNonDecs = new ArrayList<SolEngNonDec>();


            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cellsInRow = currentRow.iterator();

                SolEngNonDec solEngNonDec = new SolEngNonDec();

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIdx) {
                        case 0:
                            solEngNonDec.setBailleur(currentCell.getStringCellValue());
                            break;
                        case 1:
                            solEngNonDec.setType_cooperation(currentCell.getStringCellValue());
                            break;
                        case 2:
                            solEngNonDec.setType_fond(currentCell.getStringCellValue());
                            break;
                        case 3:
                            solEngNonDec.setSolde((long)currentCell.getNumericCellValue());
                            break;
                        case 4:
                            solEngNonDec.setDate(LocalDate.parse(currentCell.toString(), DateTimeFormatter.ofPattern("dd-MMM-yyyy")));
                            break;
                        case 5:
                            solEngNonDec.setCommission((int)currentCell.getNumericCellValue());
                            break;

                        default:
                            break;
                    }
                    cellIdx++;
                }

                solEngNonDecs.add(solEngNonDec);
                System.out.println("####################################################################");
                System.out.println(solEngNonDecs);
                System.out.println("####################################################################");
            }
            workbook.close();
            return solEngNonDecs;

        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }
}
