package com.example.scannerapp;

import java.util.Date;

public class Package {
    private String trackingNumber;
    private double length;
    private double height;
    private double width;
    private double weight;
    private String origin;
    private String destination;
    private String status;
    private String type;
    private Date date;

    public Package(double length, double height, double width, double weight, String origin, String destination, String type) {
        this.length = length;
        this.height = height;
        this.width = width;
        this.weight = weight;
        this.origin = origin;
        this.destination = destination;
        this.type = type;
    }

    public Package() {
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
