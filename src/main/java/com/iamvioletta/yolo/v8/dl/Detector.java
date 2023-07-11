package com.iamvioletta.yolo.v8.dl;

import ai.djl.ModelException;
import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.output.DetectedObjects;
import ai.djl.translate.TranslateException;

import java.io.IOException;

public interface Detector {
    DetectedObjects detectObjects(Image image) throws IOException, ModelException, TranslateException;

}
