package com.enovell.yunwei.km_micor_service.util.communicationResumeManage;

import com.enovell.yunwei.km_micor_service.dto.communicationResumeManage.DataRouterDto;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReadExcelDataRouter {
    //总行数
    private int totalRows = 0;
    //总条数
    private int totalCells = 0;
    //错误信息接收器
    private String errorMsg;

    //构造方法
    public ReadExcelDataRouter() {
    }

    //获取总行数
    public int getTotalRows() {
        return totalRows;
    }

    //获取总列数
    public int getTotalCells() {
        return totalCells;
    }

    //获取错误信息
    public String getErrorInfo() {
        return errorMsg;
    }

    /**
     * 读EXCEL文件，获取信息集合
     *
     * @param fielName
     * @return
     */
    public List<DataRouterDto> getExcelInfo(MultipartFile mFile) {
        String fileName = mFile.getOriginalFilename();//获取文件名
        System.out.println("文件名" + fileName);
        List<DataRouterDto> fdpList = null;
        try {
            if (!validateExcel(fileName)) {// 验证文件名是否合格
                return null;
            }
            boolean isExcel2003 = true;// 根据文件名判断文件是2003版本还是2007版本
            if (isExcel2007(fileName)) {
                isExcel2003 = false;
            }
            fdpList = createExcel(mFile.getInputStream(), isExcel2003);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fdpList;
    }


    /**
     * 根据excel里面的内容信息
     *
     * @param is          输入流
     * @param isExcel2003 excel是2003还是2007版本
     * @return
     * @throws ParseException
     * @throws IOException
     */
    public List<DataRouterDto> createExcel(InputStream is, boolean isExcel2003) throws ParseException {
        List<DataRouterDto> fdpList = null;
        try {
            Workbook wb = null;
            if (isExcel2003) {// 当excel是2003时,创建excel2003
                wb = new HSSFWorkbook(is);
            } else {// 当excel是2007时,创建excel2007
                wb = new XSSFWorkbook(is);
            }
            fdpList = readExcelValue(wb);// 读取Excel里面客户的信息
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fdpList;
    }

    /**
     * 读取Excel里面的信息
     *
     * @param wb
     * @return
     * @throws ParseException
     */
    private List<DataRouterDto> readExcelValue(Workbook wb) throws ParseException {
        // 得到第一个shell
        Sheet sheet = wb.getSheetAt(0);
        // 得到Excel的行数
        this.totalRows = sheet.getPhysicalNumberOfRows();
        System.out.println("行数=======" + this.totalRows);
        // 得到Excel的列数(前提是有行数)
        if (totalRows > 1 && sheet.getRow(0) != null) {
            this.totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
            System.out.println("总列数==========" + this.totalCells);
        }
        List<DataRouterDto> fdpList = new ArrayList<DataRouterDto>();
        //开始根据模板读取excel的数据
        // 循环Excel行数，每一行转化为一个dto
        for (int r = 4; r < totalRows; r++) {
            Row row = sheet.getRow(r);
            if (row == null) {
                continue;
            }
            DataRouterDto dataRouterDto = new DataRouterDto();
            // 循环Excel的列,每个单元格数据转化为dto的属性值
            for (int c = 0; c < this.totalCells; c++) {
                Cell cell = row.getCell(c);
                if (null != cell) {
                    if (c == 1) {
                        dataRouterDto.setWorkshop(cell.getStringCellValue());
                    } else if (c == 2) {

                        dataRouterDto.setWorkArea(cell.getStringCellValue());
                    } else if (c == 3) {

                        dataRouterDto.setCombinationClass(cell.getStringCellValue());

                    } else if (c == 4) {

                        dataRouterDto.setDeviceClass(cell.getStringCellValue());

                    } else if (c == 5) {
                        if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {

                            String deviceCode = String.valueOf(cell.getNumericCellValue());
                            dataRouterDto.setDeviceCode(deviceCode);
                        } else {

                            dataRouterDto.setDeviceCode(cell.getStringCellValue());
                        }
//						dataRouterDto.setDeviceCode(cell.getStringCellValue());

                    } else if (c == 6) {

                        dataRouterDto.setDeviceName(cell.getStringCellValue());

                    } else if (c == 7) {

                        dataRouterDto.setSite_station_line(cell.getStringCellValue());

                    } else if (c == 8) {

                        dataRouterDto.setSite_station_name(cell.getStringCellValue());

                    } else if (c == 9) {

                        dataRouterDto.setSite_station_place(cell.getStringCellValue());

                    } else if (c == 10) {

                        dataRouterDto.setSite_range_line(cell.getStringCellValue());

                    } else if (c == 11) {
                        if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {

                            String post = String.valueOf(cell.getNumericCellValue());
                            dataRouterDto.setSite_range_post(post);
                        } else {

                            dataRouterDto.setSite_range_post(cell.getStringCellValue());
                        }
//						dataRouterDto.setSite_range_post(cell.getStringCellValue());

                    } else if (c == 12) {

                        dataRouterDto.setSite_range_place(cell.getStringCellValue());

                    } else if (c == 13) {

                        dataRouterDto.setSite_other_line(cell.getStringCellValue());

                    } else if (c == 14) {

                        dataRouterDto.setSite_other_place(cell.getStringCellValue());

                    } else if (c == 15) {
                        if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {

                            String site_machineRoomCode = String.valueOf(cell.getNumericCellValue());
                            dataRouterDto.setSite_machineRoomCode(site_machineRoomCode);
                        } else {

                            dataRouterDto.setSite_machineRoomCode(cell.getStringCellValue());
                        }
//						dataRouterDto.setSite_machineRoomCode(cell.getStringCellValue());

                    } else if (c == 16) {

                        dataRouterDto.setAssetOwnership(cell.getStringCellValue());

                    } else if (c == 17) {

                        dataRouterDto.setOwnershipUnitName(cell.getStringCellValue());

                    } else if (c == 18) {
                        if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                            //将表格中的科学计数法转换成正常格式
                            DecimalFormat df = new DecimalFormat("0");
                            String ownershipUnitCode = String.valueOf(df.format(cell.getNumericCellValue()));
                            dataRouterDto.setOwnershipUnitCode(ownershipUnitCode);
                        } else {

                            dataRouterDto.setOwnershipUnitCode(cell.getStringCellValue());
                        }
//						dataRouterDto.setOwnershipUnitCode(cell.getStringCellValue());

                    } else if (c == 19) {

                        dataRouterDto.setMaintainBody(cell.getStringCellValue());

                    } else if (c == 20) {

                        dataRouterDto.setMaintainUnitName(cell.getStringCellValue());

                    } else if (c == 21) {
                        if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {

                            String maintainUnitCode = String.valueOf(cell.getNumericCellValue());
                            dataRouterDto.setMaintainUnitCode(maintainUnitCode);
                        } else {

                            dataRouterDto.setMaintainUnitCode(cell.getStringCellValue());
                        }
//						dataRouterDto.setMaintainUnitCode(cell.getStringCellValue());

                    } else if (c == 22) {
                        dataRouterDto.setSlotType(cell.getStringCellValue());
                    } else if (c == 23) {

                        dataRouterDto.setManufacturers(cell.getStringCellValue());

                    } else if (c == 24) {
                        if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {

                            String deviceType = String.valueOf(cell.getNumericCellValue());
                            dataRouterDto.setDeviceType(deviceType);
                        } else {

                            dataRouterDto.setDeviceType(cell.getStringCellValue());
                        }
//						dataRouterDto.setDeviceType(cell.getStringCellValue());

                    } else if (c == 25) {

                        dataRouterDto.setUseUnit(cell.getStringCellValue());

                    } else if (c == 26) {
                        if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                            Date entryDate = cell.getDateCellValue();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            String entryDateStr = "";
                            try {
                                entryDateStr = sdf.format(entryDate);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            dataRouterDto.setProductionDate(entryDateStr);
                        } else {
                            String entryDate = String.valueOf(cell.getStringCellValue());
                            entryDate.replace('/', '-');
                            dataRouterDto.setProductionDate(entryDate);
                        }

                    } else if (c == 27) {
                        if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                            Date entryDate = cell.getDateCellValue();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            String entryDateStr = "";
                            try {
                                entryDateStr = sdf.format(entryDate);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            dataRouterDto.setUseDate(entryDateStr);
                        } else {
                            String entryDate = String.valueOf(cell.getStringCellValue());
                            entryDate.replace('/', '-');
                            dataRouterDto.setUseDate(entryDate);
                        }

//						dataRouterDto.setUseDate(cell.getStringCellValue());

                    } else if (c == 28) {

                        dataRouterDto.setDeviceOperationStatus(cell.getStringCellValue());

                    } else if (c == 29) {
                        if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                            Date entryDate = cell.getDateCellValue();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            String entryDateStr = "";
                            try {
                                entryDateStr = sdf.format(entryDate);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            dataRouterDto.setStopDate(entryDateStr);
                        } else {
                            String entryDate = String.valueOf(cell.getStringCellValue());
                            entryDate.replace('/', '-');
                            dataRouterDto.setStopDate(entryDate);
                        }
//						dataRouterDto.setStopDate(cell.getStringCellValue());

                    } else if (c == 30) {
                        if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                            Date entryDate = cell.getDateCellValue();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            String entryDateStr = "";
                            try {
                                entryDateStr = sdf.format(entryDate);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            dataRouterDto.setScrapDate(entryDateStr);
                        } else {
                            String entryDate = String.valueOf(cell.getStringCellValue());
                            entryDate.replace('/', '-');
                            dataRouterDto.setScrapDate(entryDate);
                        }
//						dataRouterDto.setScrapDate(cell.getStringCellValue());

                    } else if (c == 31) {
                        if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                            String value = String.valueOf(cell.getNumericCellValue());
                            dataRouterDto.setApplicationLevel(value);
                        } else {
                            dataRouterDto.setApplicationLevel(cell.getStringCellValue());
                        }
                    } else if (c == 32) {
                        if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                            String value = String.valueOf(cell.getNumericCellValue());
                            dataRouterDto.setSlotTotalNumber(value);
                        } else {
                            dataRouterDto.setSlotTotalNumber(cell.getStringCellValue());
                        }
                    } else if (c == 33) {
                        if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                            String value = String.valueOf(cell.getNumericCellValue());
                            dataRouterDto.setSlotUseNumber(value);
                        } else {
                            dataRouterDto.setSlotUseNumber(cell.getStringCellValue());
                        }
                    } else if (c == 34) {
                        if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                            String value = String.valueOf(cell.getNumericCellValue());
                            dataRouterDto.set_622MportConfigNumber(value);
                        } else {
                            dataRouterDto.set_622MportConfigNumber(cell.getStringCellValue());
                        }
                    } else if (c == 35) {
                        if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                            String value = String.valueOf(cell.getNumericCellValue());
                            dataRouterDto.set_622MportUseNumber(value);
                        } else {
                            dataRouterDto.set_622MportUseNumber(cell.getStringCellValue());
                        }
                    } else if (c == 36) {
                        if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                            String value = String.valueOf(cell.getNumericCellValue());
                            dataRouterDto.set_155MportConfigNumber(value);
                        } else {
                            dataRouterDto.set_155MportConfigNumber(cell.getStringCellValue());
                        }
                    } else if (c == 37) {
                        if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                            String value = String.valueOf(cell.getNumericCellValue());
                            dataRouterDto.set_155MportUseNumber(value);
                        } else {
                            dataRouterDto.set_155MportUseNumber(cell.getStringCellValue());
                        }
                    } else if (c == 38) {
                        if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                            String value = String.valueOf(cell.getNumericCellValue());
                            dataRouterDto.set_2MportConfigNumber(value);
                        } else {
                            dataRouterDto.set_2MportConfigNumber(cell.getStringCellValue());
                        }
                    } else if (c == 39) {
                        if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                            String value = String.valueOf(cell.getNumericCellValue());
                            dataRouterDto.set_2MportUseNumber(value);
                        } else {
                            dataRouterDto.set_2MportUseNumber(cell.getStringCellValue());
                        }
                    } else if (c == 40) {
                        if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                            String value = String.valueOf(cell.getNumericCellValue());
                            dataRouterDto.setGEportConfigNumber(value);
                        } else {
                            dataRouterDto.setGEportConfigNumber(cell.getStringCellValue());
                        }
                    } else if (c == 41) {
                        if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                            String value = String.valueOf(cell.getNumericCellValue());
                            dataRouterDto.setGEportUseNumber(value);
                        } else {
                            dataRouterDto.setGEportUseNumber(cell.getStringCellValue());
                        }
                    } else if (c == 42) {
                        if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                            String value = String.valueOf(cell.getNumericCellValue());
                            dataRouterDto.setFEportConfigNumber(value);
                        } else {
                            dataRouterDto.setFEportConfigNumber(cell.getStringCellValue());
                        }
                    } else if (c == 43) {
                        if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                            String value = String.valueOf(cell.getNumericCellValue());
                            dataRouterDto.setFEportUseNumber(value);
                        } else {
                            dataRouterDto.setFEportUseNumber(cell.getStringCellValue());
                        }
                    } else if (c == 44) {
                        if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                            String value = String.valueOf(cell.getNumericCellValue());
                            dataRouterDto.setOtherPortConfigNumber(value);
                        } else {
                            dataRouterDto.setOtherPortConfigNumber(cell.getStringCellValue());
                        }
                    } else if (c == 45) {
                        if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                            String value = String.valueOf(cell.getNumericCellValue());
                            dataRouterDto.setOtherPortUseNumber(value);
                        } else {
                            dataRouterDto.setOtherPortUseNumber(cell.getStringCellValue());
                        }
                    } else if (c == 46) {
                        if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                            String fixedAssetsCode = String.valueOf(cell.getNumericCellValue());
                            dataRouterDto.setFixedAssetsCode(fixedAssetsCode);
                        } else {
                            dataRouterDto.setFixedAssetsCode(cell.getStringCellValue());
                        }
                    } else if (c == 47) {

                        dataRouterDto.setRemark(cell.getStringCellValue());

                    }

                }
            }
            // 添加到list
            fdpList.add(dataRouterDto);
        }
        return fdpList;
    }

    /**
     * 验证EXCEL文件
     *
     * @param filePath
     * @return
     */
    public boolean validateExcel(String filePath) {
        if (filePath == null || !(isExcel2003(filePath) || isExcel2007(filePath))) {
            errorMsg = "文件名不是excel格式";
            return false;
        }
        return true;
    }

    // @描述：是否是2003的excel，返回true是2003
    public static boolean isExcel2003(String filePath) {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    //@描述：是否是2007的excel，返回true是2007 
    public static boolean isExcel2007(String filePath) {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }
}
