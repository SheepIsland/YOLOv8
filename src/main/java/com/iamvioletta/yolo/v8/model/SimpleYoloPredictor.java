package com.iamvioletta.yolo.v8.model;

import ai.djl.MalformedModelException;
import ai.djl.ModelException;
import ai.djl.inference.Predictor;
import ai.djl.modality.Classifications;
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
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class SimpleYoloPredictor implements YoloPredictor {
    private static final int IMAGE_SIZE = 640;

    private ZooModel<Image, DetectedObjects> model;
    private Predictor<Image, DetectedObjects> predictor;

    public SimpleYoloPredictor() throws IOException, ModelNotFoundException, MalformedModelException {
        Pipeline pipeline = new Pipeline();
        pipeline.add(new Resize(IMAGE_SIZE));
        pipeline.add(new ToTensor());

        Translator<Image, DetectedObjects> translator =  YoloV5Translator
                .builder()
                .setPipeline(pipeline)
                .optSynsetArtifactName("coco.names")
                .optThreshold(0.3f)
                .build();

        // Model Criteria
        Criteria<Image, DetectedObjects> criteria = Criteria.builder()
                .setTypes(Image.class, DetectedObjects.class)
                .optModelUrls(Paths.get("build/yolov8").toFile().getPath())
                .optModelName("best.torchscript")
                .optTranslator(translator)
                .optProgress(new ProgressBar())
                .build();

        model = ModelZoo.loadModel(criteria);
        predictor = model.newPredictor();
    }

    @Override
    public DetectedObjects detectObjects(Image image) throws IOException, ModelException, TranslateException {
        DetectedObjects results = predictor.predict(image);
        System.out.println(results);

        Path outputDir = Paths.get("build/output");
        Files.createDirectories(outputDir);
        ImageDrawer.drawBoundingBoxes((BufferedImage) image.getWrappedImage(),results);
        Path imagePath = outputDir.resolve("detected-DSC00020_0.png");
        image.save(Files.newOutputStream(imagePath), "png");

        return results;
    }

    public Classifications classifyObjects(Image image) throws IOException, ModelException, TranslateException {
        return null;
    }
}
