package com.ping.easyfile.util;

import com.ping.easyfile.request.WaterMark;
import org.apache.poi.openxml4j.opc.PackagePartName;
import org.apache.poi.openxml4j.opc.PackageRelationship;
import org.apache.poi.openxml4j.opc.TargetMode;
import org.apache.poi.xssf.usermodel.XSSFPictureData;
import org.apache.poi.xssf.usermodel.XSSFRelation;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author lwp
 * @date 2022-08-16
 */
public class ExcelWaterUtil {

    /**
     * 给 Excel 添加水印
     *
     * @param workbook  XSSFWorkbook
     * @param waterMark
     * @throws IOException
     */
    public static void putWaterMarkTextToXlsx(XSSFWorkbook workbook, WaterMark waterMark) throws IOException {
        try (ByteArrayOutputStream imageOs = new ByteArrayOutputStream();) {
            BufferedImage image = ImageUtil.createWaterMark(waterMark.getWidth(), waterMark.getHeight(), waterMark.getSize(), waterMark.getWaterMarkText());
            ImageIO.write(image, "png", imageOs);
            int pictureIdx = workbook.addPicture(imageOs.toByteArray(), XSSFWorkbook.PICTURE_TYPE_PNG);
            XSSFPictureData pictureData = workbook.getAllPictures().get(pictureIdx);
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                XSSFSheet sheet = workbook.getSheetAt(i);
                PackagePartName ppn = pictureData.getPackagePart().getPartName();
                String relType = XSSFRelation.IMAGES.getRelation();
                PackageRelationship pr = sheet.getPackagePart().addRelationship(ppn, TargetMode.INTERNAL, relType, null);
                sheet.getCTWorksheet().addNewPicture().setId(pr.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
