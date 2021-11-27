package com.project.newsapp.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TotalSurat {

    @NonNull
    private String status;

    @NonNull
    @SerializedName("totalResults")
    private int totalSuratCount;

    @NonNull
    @SerializedName("articles")
    private List<Surat> suratList;

    public TotalSurat() {
    }

    @NonNull
    public String getStatus() {
        return status;
    }

    public void setStatus(@NonNull String status) {
        this.status = status;
    }

    public int getTotalSuratCount() {
        return totalSuratCount;
    }

    public void setTotalSuratCount(int totalSuratCount) {
        this.totalSuratCount = totalSuratCount;
    }

    @NonNull
    public List<Surat> getSuratList() {
        return suratList;
    }

    public void setSuratList(@NonNull List<Surat> suratList) {
        this.suratList = suratList;
    }
}
