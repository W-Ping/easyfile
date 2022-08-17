package com.ping.easyfile.util;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * @author lwp
 * @date 2022-08-16
 */
public class ImageUtil {

    public static BufferedImage createWaterMark(int width, int height, int size, String text) {
        String[] content = text.split("\n");
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Font font = new Font("宋体", Font.PLAIN, size);
        Graphics2D g2d = image.createGraphics();
        image = g2d.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
        g2d.dispose();
        g2d = image.createGraphics();
        //设置字体颜色和透明度
        g2d.setColor(new Color(0, 0, 0, 50));
        g2d.setStroke(new BasicStroke(1));
        g2d.setFont(font);
        // 设置字体平滑
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //设置旋转角度
        g2d.rotate(Math.toRadians(-10), (double) image.getWidth() / 2, (double) image.getHeight() / 2);
        FontRenderContext context = g2d.getFontRenderContext();
        //找到水印信息中最长的
        int maxLengthIndex = 0;
        if (content.length > 1) {
            int contentLength = content[0].length();
            for (int i = 0; i < content.length; i++) {
                if (content[i].length() > contentLength) {
                    maxLengthIndex = i;
                }
            }
        }
        Rectangle2D bounds = font.getStringBounds(content[maxLengthIndex], context);
        double x = (width - bounds.getWidth()) / 2;
        double y = (height - bounds.getHeight()) / 2;
        double ascent = -bounds.getY();
        double baseY = y + ascent;
        // 写入水印文字原定高度过小，所以累计写水印，增加高度
        for (int i = 0; i < content.length; i++) {
            // 画出字符串
            g2d.drawString(content[i], (int) x, (int) baseY);
            baseY = baseY + font.getSize();
        }
        // 设置透明度
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
        // 释放对象
        g2d.dispose();
        return image;

    }


}
