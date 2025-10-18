package org.example;

import okhttp3.*;
import com.fasterxml.jackson.databind.*;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {

        Path projectRoot = Paths.get(System.getProperty("user.dir"));
        String fileName = "parking3_image1.jpg";
        Path imagePath = projectRoot.resolve(fileName);

        if (Files.notExists(imagePath)) {
            try (InputStream in = Main.class.getResourceAsStream("/" + fileName)) {
                if (in == null) throw new FileNotFoundException("Resource not found: " + fileName);
                Files.copy(in, imagePath, StandardCopyOption.REPLACE_EXISTING);
            }
        }

        ParkingBoxes parkingBoxes;
        try (InputStream jsonIn = Main.class.getResourceAsStream("/parking3.json")) {
            if (jsonIn == null) throw new FileNotFoundException("parking3.json not on classpath");
            ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
            parkingBoxes = mapper.readValue(jsonIn, ParkingBoxes.class);
        }

        List<String> occupied = new ArrayList<>();

        try {
            DetectionResponse response = sendImageUrl(String.valueOf(imagePath));
            for (DetectionResponse.Detections d : response.getDetections()){
                for(ParkingBoxes.ParkingBox parkingBox : parkingBoxes.getParkingBoxes()) {
                    if (doesIntersect(parkingBox, d)){
                        occupied.add(parkingBox.getLabel());
                        break;
                    }
                }
            }
            System.out.println(ListUtil.order(occupied));
        } catch (Exception e) {
            System.err.println("Error during inference: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static DetectionResponse sendImageUrl(String imageUrl) throws IOException {
        OkHttpClient http = new OkHttpClient();
        ObjectMapper mapper = new ObjectMapper();

        // Create the JSON body
        String json = mapper.createObjectNode()
                .put("image_path", imageUrl)
                .toString();

        // Prepare the POST request
        RequestBody body = RequestBody.create(json, MediaType.get("application/json"));
        String API_URL = "http://127.0.0.1:8000";
        Request req = new Request.Builder()
                .url(API_URL + "/predict/path")
                .post(body)
                .build();

        // Execute and parse response
        try (Response res = http.newCall(req).execute()) {
            if (!res.isSuccessful()) {
                throw new IOException("Unexpected HTTP " + res.code() + ": " + res.message());
            }

            assert res.body() != null;
            String responseJson = res.body().string();

//            String savePath = "C:/Users/farou/Downloads/predictions.json"; // <-- change path as needed
//            try (FileWriter writer = new FileWriter(savePath)) {
//                writer.write(responseJson);
//                System.out.println("✅ Predictions JSON saved to: " + savePath);
//            } catch (IOException e) {
//                System.err.println("⚠️ Could not save JSON: " + e.getMessage());
//            }

            return mapper.readValue(responseJson, DetectionResponse.class);
        }
    }

    private static boolean doesIntersect(ParkingBoxes.ParkingBox p1, DetectionResponse.Detections detections) {
        float x_left = Math.max(p1.getX1(), detections.getX1());
        float x_right = Math.min(p1.getX2(), detections.getX2());
        float y_top = Math.max(p1.getY1(), detections.getY1());
        float y_bottom = Math.min(p1.getY2(), detections.getY2());

        if (x_right < x_left || y_bottom < y_top) {
            return false;
        }

        float intersectionArea = (x_right - x_left) * (y_bottom - y_top);
        float parkingArea = (p1.getX2() - p1.getX1()) * (p1.getY2() - p1.getY1());
        float percentageOfIntersection = (intersectionArea / parkingArea) * 100;
        
        if (percentageOfIntersection > 50){
            return true;
        }else if (percentageOfIntersection < 35){
            return false;
        }else {
            float xCenter = (detections.getX2() + detections.getX1()) / 2;
            float yCenter = (detections.getY2() + detections.getY1()) / 2;

            return (xCenter > p1.getX1() && xCenter < p1.getX2() &&
                    yCenter > p1.getY1() && yCenter < p1.getY2());
        }
    }

}