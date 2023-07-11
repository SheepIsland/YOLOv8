package com.iamvioletta.yolo.v8.utils;

import ai.djl.modality.cv.output.BoundingBox;
import ai.djl.modality.cv.output.DetectedObjects;
import ai.djl.modality.cv.output.Rectangle;
import ai.djl.modality.cv.Image;
import ai.djl.util.RandomUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class ImageDrawer {

    private static Color randomColor() {
        return new Color(RandomUtils.nextInt(255));
    }

    public static void drawBoundingBoxes(Image image, DetectedObjects detections) {
        final BufferedImage bImage = (BufferedImage) image.getWrappedImage();

        Graphics2D g = (Graphics2D) bImage.getGraphics();
        final int stroke = 2;
        g.setStroke(new BasicStroke(stroke));
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        final List<DetectedObjects.DetectedObject> list = detections.items();
        for (DetectedObjects.DetectedObject result : list) {
            final BoundingBox box = result.getBoundingBox();
            g.setPaint(randomColor().darker());
            final String label = labelOnDetectedObject(result);
            final Rectangle rectangle = box.getBounds();
            final int x = (int) (rectangle.getX()) ;
            final int y = (int) (rectangle.getY());
            g.drawRect(
                    x,
                    y,
                    (int) (rectangle.getWidth() ),
                    (int) (rectangle.getHeight()));
            drawText(g, label , x, y, stroke, 4);
        }
        g.dispose();
    }
    private static String labelOnDetectedObject(DetectedObjects.DetectedObject result) {
        StringBuilder labelOnDetectedObject = new StringBuilder();
        final String className = result.getClassName();
        final Double probability = result.getProbability();
        labelOnDetectedObject.append(className).append(" ").append(probability);
        return labelOnDetectedObject.toString();
    }
    private static void drawText(Graphics2D g, String text, int x, int y, int stroke, int padding) {
       FontMetrics metrics = g.getFontMetrics();
        x += stroke / 2;
        y += stroke / 2;
        int width = metrics.stringWidth(text) + padding * 2 - stroke / 2;
        int height = metrics.getHeight() + metrics.getDescent();
        int ascent = metrics.getAscent();
        java.awt.Rectangle background = new java.awt.Rectangle(x, y, width, height);
        g.fill(background);
        g.setPaint(Color.WHITE);
        g.drawString(text, x + padding, y + ascent);
    }
}