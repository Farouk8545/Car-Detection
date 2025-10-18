package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ParkingBoxes {
    @JsonProperty("boxes")
    private ParkingBox[] parkingBoxes;

    public ParkingBoxes(ParkingBox[] parkingBoxes) {
        this.parkingBoxes = parkingBoxes;
    }

    public ParkingBoxes() {}

    public ParkingBox[] getParkingBoxes() {
        return parkingBoxes;
    }

    public void setParkingBoxes(ParkingBox[] parkingBoxes) {
        this.parkingBoxes = parkingBoxes;
    }

    public static class ParkingBox {
        private String label;
        private float x1;
        private float y1;
        private float x2;
        private float y2;

        public ParkingBox(String label, float x1, float y1, float x2, float y2) {
            this.label = label;
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }

        public ParkingBox() {}

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

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
    }
}
