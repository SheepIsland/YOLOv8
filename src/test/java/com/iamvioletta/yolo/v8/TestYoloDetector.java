package com.iamvioletta.yolo.v8;

import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.output.BoundingBox;
import ai.djl.modality.cv.output.DetectedObjects;
import ai.djl.modality.cv.output.Point;
import ai.djl.modality.cv.output.Rectangle;
import com.iamvioletta.yolo.v8.dl.Detector;

import java.util.ArrayList;
import java.util.List;

public class TestYoloDetector implements Detector {
    @Override
    public DetectedObjects detectObjects(Image image) {
       final List<String> classes = new ArrayList<>();
       classes.add("flower");
       final List<Double> probabilities = new ArrayList<>();
       probabilities.add(0.9);
       final List<BoundingBox> boundingBoxes = new ArrayList<>();
       boundingBoxes.add(new BoundingBox() {
           @Override
           public Rectangle getBounds() {
               return null;
           }

           @Override
           public Iterable<Point> getPath() {
               return null;
           }

           @Override
           public Point getPoint() {
               return null;
           }

           @Override
           public double getIoU(BoundingBox boundingBox) {
               return 0;
           }
       });
       // Return a dummy DetectedObjects instance for testing
        return new DetectedObjects(classes, probabilities, boundingBoxes);
    }
}
