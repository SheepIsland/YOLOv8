package com.iamvioletta.yolo.v8.model;

import ai.djl.ModelException;
import ai.djl.modality.Classifications;
import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.output.DetectedObjects;
import ai.djl.translate.TranslateException;

import java.io.IOException;

public interface YoloPredictor {
    DetectedObjects detectObjects(Image image) throws IOException, ModelException, TranslateException;
    Classifications classifyObjects(Image image) throws IOException, ModelException, TranslateException;
}
