package com.iamvioletta.yolo.v8.dl;

import ai.djl.MalformedModelException;
import ai.djl.ModelException;
import ai.djl.inference.Predictor;
import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.output.DetectedObjects;
import ai.djl.modality.cv.transform.Resize;
import ai.djl.modality.cv.transform.ToTensor;
import ai.djl.modality.cv.translator.YoloV5Translator;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.repository.zoo.ModelZoo;
import ai.djl.repository.zoo.ZooModel;
import ai.djl.training.util.ProgressBar;
import ai.djl.translate.Pipeline;
import ai.djl.translate.TranslateException;
import ai.djl.translate.Translator;
import com.iamvioletta.yolo.v8.controller.DetectionController;
import com.iamvioletta.yolo.v8.utils.ImageDrawer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Paths;

@Service
public class YoloDetector implements Detector {
    private final Logger logger = LoggerFactory.getLogger(DetectionController.class);

    private static final int IMAGE_SIZE = 640;
    private static final String SYSNET = "coco.names";
    private static final float MODEL_THRESHOLD = 0.3f;
    private static final String MODEL_PATH = "build/yolov8";
    private static final String MODEL_NAME = "best.torchscript";

    private final ZooModel<Image, DetectedObjects> model;
    private final Predictor<Image, DetectedObjects> predictor;

    public YoloDetector() throws IOException, ModelNotFoundException, MalformedModelException {
        Pipeline pipeline = new Pipeline();
        pipeline.add(new Resize(IMAGE_SIZE));
        pipeline.add(new ToTensor());

        // Model Translator
        Translator<Image, DetectedObjects> translator =  YoloV5Translator
                .builder()
                .setPipeline(pipeline)
                .optSynsetArtifactName(SYSNET)
                .optThreshold(MODEL_THRESHOLD)
                .build();

        // Model Criteria
        Criteria<Image, DetectedObjects> criteria = Criteria.builder()
                .setTypes(Image.class, DetectedObjects.class)
                .optModelUrls(Paths.get(MODEL_PATH).toFile().getPath())
                .optModelName(MODEL_NAME)
                .optTranslator(translator)
                .optProgress(new ProgressBar())
                .build();

        model = ModelZoo.loadModel(criteria);
        logger.debug("Yolov8 model is created");
        predictor = model.newPredictor();
    }

    @Override
    public DetectedObjects detectObjects(Image image) throws IOException, ModelException, TranslateException {
        DetectedObjects results = predictor.predict(image);
        logger.info("Detected objects: " + results);
        ImageDrawer.drawBoundingBoxes(image,results);
        return results;
    }
}
