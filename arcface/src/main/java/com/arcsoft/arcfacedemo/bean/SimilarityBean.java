package com.arcsoft.arcfacedemo.bean;

public class SimilarityBean {
    private int MinAge;
    private int MaxAge;
    private double Similarity;

    public SimilarityBean(int minAge, int maxAge, double similarity) {
        MinAge = minAge;
        MaxAge = maxAge;
        Similarity = similarity;
    }

    public int getMinAge() {
        return MinAge;
    }

    public void setMinAge(int minAge) {
        MinAge = minAge;
    }

    public int getMaxAge() {
        return MaxAge;
    }

    public void setMaxAge(int maxAge) {
        MaxAge = maxAge;
    }

    public double getSimilarity() {
        return Similarity;
    }

    public void setSimilarity(float similarity) {
        Similarity = similarity;
    }
}
