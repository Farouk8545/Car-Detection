package org.example;

public class DetectionResponse {
    private Detections[] detections;

    public DetectionResponse(Detections[] detections) {
        this.detections = detections;
    }

    public DetectionResponse() {}

    public Detections[] getDetections() {
        return detections;
    }

    public void setDetections(Detections[] detections) {
        this.detections = detections;
    }

    public static class Detections {
        private float x1, y1, x2, y2, confidence;
        private int class_id;
        private String class_name;

        public Detections(float x1, float y1, float x2, float y2, float confidence, int class_id, String class_name) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
            this.confidence = confidence;
            this.class_id = class_id;
            this.class_name = class_name;
        }

        public Detections() {}

        public float getX1() {
            return x1;
        }

        public void setX1(float x1) {
            this.x1 = x1;
        }

        public float getY1() {
            return y1;
        }

        public void setY1(float y1) {
            this.y1 = y1;
        }

        public float getX2() {
            return x2;
        }

        public void setX2(float x2) {
            this.x2 = x2;
        }

        public float getY2() {
            return y2;
        }

        public void setY2(float y2) {
            this.y2 = y2;
        }

        public float getConfidence() {
            return confidence;
        }

        public void setConfidence(float confidence) {
            this.confidence = confidence;
        }

        public int getClass_id() {
            return class_id;
        }

        public void setClass_id(int class_id) {
            this.class_id = class_id;
        }

        public String getClass_name() {
            return class_name;
        }

        public void setClass_name(String class_name) {
            this.class_name = class_name;
        }
    }
}
