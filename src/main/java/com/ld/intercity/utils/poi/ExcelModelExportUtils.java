package com.ld.intercity.utils.poi;

import com.ld.intercity.utils.ResponseResult;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

/**
 * @author ld
 * @name 导入模板 下载excel
 * @table
 * @remarks
 */
public class ExcelModelExportUtils {
    /**
     * 根据模板导出excel
     *
     * @param basePath 模板路径
     * @param outPath  生成的excel存放路径
     * @param datas    具体数据
     * @return 成功标记以及文件名称
     */
    public ResponseResult<String> exportCurrency(String basePath, String outPath, List<String[]> datas) throws Exception {
        if (datas == null || datas.size() <= 0) {
            return new ResponseResult<>(false, "数据为空，不允许导出");
        }
        //excel模板路径
        File fi = new File(basePath);
        POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(fi));
//读取excel模板
        HSSFWorkbook wb = new HSSFWorkbook(fs);
//读取了模板内所有sheet内容
        HSSFSheet sheet0 = wb.getSheetAt(0);
//            调用组装数据
        if (datas.size() > 0)
            assembleCurrency(sheet0, datas);

        //修改模板内容导出新模板
        String fileName = UUID.randomUUID().toString();
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(outPath + fileName + ".xls");
            wb.write(out);
        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            if (out != null) {
                out.close();
                out.flush();
            }
        }
        return new ResponseResult<>(true, "生成excel成功", fileName + ".xls");
    }

    /**
     * sheet中生成数据
     *
     * @param sheet 操作的sheet
     * @param datas 数据集合
     * @
     */
    private void assembleCurrency(HSSFSheet sheet, List<String[]> datas) {
//        获取标题行
        HSSFRow row1 = sheet.getRow(0);
//        获取标题行总列数
        int cellSize = sheet.getRow(0).getPhysicalNumberOfCells();
//        循环数据行数
        for (int i = 0; i < datas.size(); i++) {
            String[] o = datas.get(i);
            HSSFRow row = sheet.createRow(i + 1);
//            循环列数
            for (int j = 0; j < cellSize; j++) {
                HSSFCell cell = row.createCell(j);
                cell.setCellValue(o[j] == null ? "" : o[j]);
            }
        }
    }

//    public ResponseResult<String> exportRctz(String basePath, String outPath, List<TyModel> datas) {
//        try {
//            //excel模板路径
//            File fi = new File(basePath);
//            POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(fi));
////读取excel模板
//            HSSFWorkbook wb = new HSSFWorkbook(fs);
////读取了模板内所有sheet内容
//            HSSFSheet sheet0 = wb.getSheetAt(0);
////            调用组装数据
//            if (datas.size() > 0)
//                assembleRctz(sheet0, datas);
//
////            boolean b = assemble(sheet, datas);
////            if (!b)
////                return new ResponseResult<>(false, "数据组装错误,生成excel失败", null);
//////创建行设置样式，创建单元格，设置单元格样式
////            sheet.shiftRows(startRow, startRow + 1, 1, true, false);
////            sheet.createRow(startRow);
////            sheet.getRow(startRow).setRowStyle(rowstyle);
////            for (int j = 0; j < 9; j++) {
////                sheet.getRow(startRow).createCell(j);
////            }
////            HSSFCell temp1 = sheet.getRow(startRow).getCell(0);
////            temp1.setCellValue(1);
////            temp1.setCellStyle(style);
//            //修改模板内容导出新模板
////            String fileName = GetUuid.getUUID();
//            String fileName = UUID.randomUUID().toString();
//            FileOutputStream out = new FileOutputStream(outPath + fileName + ".xls");
//            wb.write(out);
//            out.close();
//            out.flush();
//            return new ResponseResult<>(true, "生成excel成功", fileName + ".xls");
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseResult<>(false, "生成excel失败", null);
//        }
//    }

    //    组装数据
//    private boolean assembleRctz(HSSFSheet sheet, List<TyModel> datas) {
//        SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd");
//        try {
////        获取第一行每列的数据与datas做对比，对数据进行组装
//            for (int i = 0; i < datas.size(); i++) {//数据行数
//
//                TyModel model = datas.get(i);
//                HSSFRow row = sheet.createRow(i + 1);
//                HSSFCell cell = row.createCell(0);
//                cell.setCellValue(model.getNumbers().substring(0, 8));
//                HSSFCell cell1 = row.createCell(1);
//                cell1.setCellValue(model.getNumbers());
//                HSSFCell cell2 = row.createCell(2);
//                cell2.setCellValue(model.getClassification());
//                HSSFCell cell3 = row.createCell(3);
//                cell3.setCellValue(model.getAssetName());
//                HSSFCell cell4 = row.createCell(4);
//                cell4.setCellValue(model.getBrand());
//                HSSFCell cell5 = row.createCell(5);
//                cell5.setCellValue(model.getSpecification());
//                HSSFCell cell6 = row.createCell(6);
//                cell6.setCellValue(model.getManufacturer());
//                HSSFCell cell7 = row.createCell(7);
//                cell7.setCellValue(model.getJbr());
//                HSSFCell cell8 = row.createCell(8);
//                cell8.setCellValue(model.getJbrdh());
//
//            }
//        } catch (ClassCastException e) {
//            return false;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//        return true;
//    }

//    public ResponseResult<String> export(String basePath, String outPath, Map<String, Object> datas) {
//        try {
//            //excel模板路径
//            File fi = new File(basePath);
//            POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(fi));
////读取excel模板
//            HSSFWorkbook wb = new HSSFWorkbook(fs);
////读取了模板内所有sheet内容
//            HSSFSheet sheet0 = wb.getSheetAt(0);
////            调用组装数据
////            if (datas.size() > 0)
////                assemble(sheet0, datas.get("a"));
//
////            boolean b = assemble(sheet, datas);
////            if (!b)
////                return new ResponseResult<>(false, "数据组装错误,生成excel失败", null);
//////创建行设置样式，创建单元格，设置单元格样式
////            sheet.shiftRows(startRow, startRow + 1, 1, true, false);
////            sheet.createRow(startRow);
////            sheet.getRow(startRow).setRowStyle(rowstyle);
////            for (int j = 0; j < 9; j++) {
////                sheet.getRow(startRow).createCell(j);
////            }
////            HSSFCell temp1 = sheet.getRow(startRow).getCell(0);
////            temp1.setCellValue(1);
////            temp1.setCellStyle(style);
//            //修改模板内容导出新模板
////            String fileName = GetUuid.getUUID();
//            String fileName = UUID.randomUUID().toString();
//            FileOutputStream out = new FileOutputStream(outPath + fileName + ".xls");
//            wb.write(out);
//            out.close();
//            out.flush();
//            return new ResponseResult<>(true, "生成excel成功", fileName + ".xls");
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseResult<>(false, "生成excel失败", null);
//        }
//    }

//    private boolean assemble(HSSFSheet sheet, List<Object> datas) {
//        SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd");
//        try {
////        获取第一行每列的数据与datas做对比，对数据进行组装
//            for (int i = 0; i < datas.size(); i++) {//数据行数
//                HSSFRow row = sheet.createRow(i + 1);
//                Object o[] = (Object[]) datas.get(i);
//                for (int j = 0; j < o.length; j++) {
//                    HSSFCell cell = row.createCell(j);
//                    cell.setCellValue((String) o[j]);
//                }
//            }
//        } catch (ClassCastException e) {
//            return false;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//        return true;
//    }

//    public ResponseResult<String> exportZcpk(String basePath, String outPath, List<TyModel> datas, String bt) {
//        try {
//            //excel模板路径
//            File fi = new File(basePath);
//            POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(fi));
////读取excel模板
//            HSSFWorkbook wb = new HSSFWorkbook(fs);
////读取了模板内所有sheet内容
//            HSSFSheet sheet0 = wb.getSheetAt(0);
////            调用组装数据
//            if (datas.size() > 0)
//                assembleZcpk(sheet0, datas, bt);
//
//            String fileName = UUID.randomUUID().toString();
//            FileOutputStream out = new FileOutputStream(outPath + fileName + ".xls");
//            wb.write(out);
//            out.close();
//            out.flush();
//            return new ResponseResult<>(true, "生成excel成功", fileName + ".xls");
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseResult<>(false, "生成excel失败", null);
//        }
//    }

//    private boolean assembleZcpk(HSSFSheet sheet, List<TyModel> datas, String bt) {
//        SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd");
//        try {
//            HSSFRow row0 = sheet.createRow(0);
//            row0.createCell(0).setCellValue(bt);
//            row0.getCell(0).getCellStyle().setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
//            for (int i = 0; i < datas.size(); i++) {//数据行数
//                HSSFRow row = sheet.createRow(i + 2);
//
//                HSSFCell cell = row.createCell(0);
//                cell.setCellValue(datas.get(i).getNumbers());
//
//                HSSFCell cell1 = row.createCell(1);
//                cell1.setCellValue(datas.get(i).getClassification());
//
//                HSSFCell cell2 = row.createCell(2);
//                cell2.setCellValue(datas.get(i).getAssetName());
//
//                HSSFCell cell3 = row.createCell(3);
//                cell3.setCellValue(datas.get(i).getSpecification());
//
//                HSSFCell cell4 = row.createCell(4);
//                cell4.setCellValue(datas.get(i).getManufacturer());
//
//                HSSFCell cell5 = row.createCell(5);
//                cell5.setCellValue(datas.get(i).getSeller());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//        return true;
//    }

//    public ResponseResult<String> export(String basePath, String outPath, List<PatentDataPutModel> datas) {
//        try {
//            //excel模板路径
//            File fi = new File(basePath);
//            POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(fi));
////读取excel模板
//            HSSFWorkbook wb = new HSSFWorkbook(fs);
////读取了模板内所有sheet内容
//            HSSFSheet sheet = wb.getSheetAt(0);
////            调用组装数据
//            boolean b = assemble(sheet, datas);
//            if (!b)
//                return new ResponseResult<>(false, "数据组装错误,生成excel失败", null);
//////创建行设置样式，创建单元格，设置单元格样式
////            sheet.shiftRows(startRow, startRow + 1, 1, true, false);
////            sheet.createRow(startRow);
////            sheet.getRow(startRow).setRowStyle(rowstyle);
////            for (int j = 0; j < 9; j++) {
////                sheet.getRow(startRow).createCell(j);
////            }
////            HSSFCell temp1 = sheet.getRow(startRow).getCell(0);
////            temp1.setCellValue(1);
////            temp1.setCellStyle(style);
//            //修改模板内容导出新模板
//            String fileName = GetUuid.getUUID();
//            FileOutputStream out = new FileOutputStream(outPath + fileName + ".xls");
//            wb.write(out);
//            out.close();
//            out.flush();
//            return new ResponseResult<>(true, "生成excel成功", fileName + ".xls");
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseResult<>(false, "生成excel失败", null);
//        }
//    }

    //    组装数据-专利数据维护导出查询结果
//    private boolean assemble(HSSFSheet sheet, List<PatentDataPutModel> datas) {
//        SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd");
//        try {
////        获取异地样总列数
//            int cells = sheet.getRow(0).getPhysicalNumberOfCells();
////        获取第一行每列的数据与datas做对比，对数据进行组装
//            for (int i = 0; i < datas.size(); i++) {//数据行数
//                HSSFRow row = sheet.createRow(i + 1);
//                for (int j = 0; j < cells; j++) {//title列数
//                    String s = sheet.getRow(0).getCell(j).getStringCellValue();
//                    HSSFCell cell = row.createCell(j);
//                    if (s.equals("申请号"))
//                        cell.setCellValue(datas.get(i).getApplyCode());
//                    if (s.equals("授权入库日")) {
//                        long date = datas.get(i).getEmpowermentDate();
//                        if (date == 0)
//                            cell.setCellValue("");
//                        else {
//                            String s1 = format.format(date);
//                            cell.setCellValue(s1);
//                        }
//                    }
//                    if (s.equals("申请日")) {
//                        long date = datas.get(i).getApplyDate();
//                        if (date == 0)
//                            cell.setCellValue("");
//                        else {
//                            String s1 = format.format(date);
//                            cell.setCellValue(s1);
//                        }
//                    }
//                    if (s.equals("发明名称"))
//                        cell.setCellValue(datas.get(i).getInventionName());
//                    if (s.equals("代理机构名称"))
//                        cell.setCellValue(datas.get(i).getAgentName());
//                    if (s.equals("主分类号"))
//                        cell.setCellValue(datas.get(i).getMainTypes());
//                    if (s.equals("专利人名称"))
//                        cell.setCellValue(datas.get(i).getPatentPeoName());
//                    if (s.equals("申请人邮编"))
//                        cell.setCellValue(datas.get(i).getZipCode());
//                    if (s.equals("申请人地址"))
//                        cell.setCellValue(datas.get(i).getPeoAddress());
//                    if (s.equals("专利类型"))
//                        cell.setCellValue(datas.get(i).getPatentTypes());
//                    if (s.equals("申请人类型"))
//                        cell.setCellValue(datas.get(i).getAppPeoTypes());
//                    if (s.equals("省份名称"))
//                        cell.setCellValue(datas.get(i).getProvince());
//                    if (s.equals("城市名称"))
//                        cell.setCellValue(datas.get(i).getCity());
//                    if (s.equals("区/县名称"))
//                        cell.setCellValue(datas.get(i).getArea());
//                    if (s.equals("申请方式名称"))
//                        cell.setCellValue(datas.get(i).getApplyMode());
//                    if (s.equals("案卷入库日")) {
//                        long date = datas.get(i).getFileEnterDate();
//                        if (date == 0)
//                            cell.setCellValue("");
//                        else {
//                            String s1 = format.format(date);
//                            cell.setCellValue(s1);
//                        }
//                    }
//                    if (s.equals("导入时间")) {
//                        long date = datas.get(i).getImpDate();
//                        if (date == 0)
//                            cell.setCellValue("");
//                        else {
//                            String s1 = format.format(date);
//                            cell.setCellValue(s1);
//                        }
//                    }
//
//                    if (s.equals("标识码")) {
//                        cell.setCellValue(datas.get(i).getUuid());
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//        return true;
//    }

    //下载
//    public void download(String path, HttpServletResponse response) {
//        try {
//            // path是指欲下载的文件的路径。
//            File file = new File(path);
//            // 取得文件名。
//            String filename = file.getName();
//            // 以流的形式下载文件。
//            InputStream fis = new BufferedInputStream(new FileInputStream(path));
//            byte[] buffer = new byte[fis.available()];
//            fis.read(buffer);
//            fis.close();
//            // 清空response
//            response.reset();
//            // 设置response的Header
//            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
//            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
//            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes(StandardCharsets.UTF_8)));
//            response.addHeader("Content-Length", "" + file.length());
//            toClient.write(buffer);
//            toClient.flush();
//            toClient.close();
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//    }

    //下载
    public void download2(String path, String fileName, HttpServletResponse response) throws IOException {
        // path是指欲下载的文件的路径。
        File file = new File(path, fileName);
        if (file.length() > 0) {
            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(path + "/" + fileName));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.addHeader("Content-Disposition", "attachment;filename="
                    + new String(fileName.getBytes(), StandardCharsets.ISO_8859_1));
            response.addHeader("Content-Length", "" + file.length());
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        }
    }


//    HSSFCellStyle cellStyle = wb.createCellStyle();
//    一、设置背景色:
//
//
//            cellStyle.setFillForegroundColor((short) 13);// 设置背景色
//cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//    二、设置边框:
//
//
//            cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
//cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
//cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
//cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
//    三、设置居中:
//
//
//            cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
//    四、设置字体:
//
//
//    HSSFFont font = wb.createFont();
//font.setFontName("黑体");
//font.setFontHeightInPoints((short) 16);//设置字体大小
//
//    HSSFFont font2 = wb.createFont();
//font2.setFontName("仿宋_GB2312");
//font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
//font2.setFontHeightInPoints((short) 12);
//
//cellStyle.setFont(font);//选择需要用到的字体格式
//    五、设置列宽:
//
//            sheet.setColumnWidth(0, 3766);
////第一个参数代表列id(从0开始),第2个参数代表宽度值  参考 ："2012-08-10"的宽度为2500
//    六、设置自动换行:
//
//            cellStyle.setWrapText(true);//设置自动换行
//    七、合并单元格:
//
//    Region region1 = new Region(0, (short) 0, 0, (short) 6);//参数1：行号 参数2：起始列号 参数3：行号 参数4：终止列号
//
//
////此方法在POI3.8中已经被废弃，建议使用下面一个
//    或者用
//
//
//    CellRangeAddress region1 = new CellRangeAddress(rowNumber, rowNumber, (short) 0, (short) 11);
//
//
////参数1：起始行 参数2：终止行 参数3：起始列 参数4：终止列
//    但应注意两个构造方法的参数不是一样的，具体使用哪个取决于POI的不同版本。
//
//
//            sheet.addMergedRegion(region1);
}
