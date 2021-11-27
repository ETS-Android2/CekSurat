package com.project.newsapp.model;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Surat {

    @NonNull
    @SerializedName("source")
    private Source source;

    @NonNull
    @SerializedName("title")
    private String suratTitle;

    @NonNull
    @SerializedName("description")
    private String suratDescription;

    @NonNull
    @SerializedName("url")
    private String suratUrl;

    @NonNull
    @SerializedName("urlToImage")
    private String suratImage;

    @NonNull
    @SerializedName("publishedAt")
    private Date suratPublishedDate;

    @NonNull
    public Source getSource() {
        return source;
    }

    public void setSource(@NonNull Source source) {
        this.source = source;
    }

    @NonNull
    public String getSuratTitle() {
        return suratTitle;
    }

    public void setSuratTitle(@NonNull String suratTitle) {
        this.suratTitle = suratTitle;
    }

    @NonNull
    public String getSuratDescription() {
        return suratDescription;
    }

    public void setSuratDescription(@NonNull String suratDescription) {
        this.suratDescription = suratDescription;
    }

    @NonNull
    public String getSuratUrl() {
        return suratUrl;
    }

    public void setSuratUrl(@NonNull String suratUrl) {
        this.suratUrl = suratUrl;
    }

    @NonNull
    public String getSuratImage() {
        return suratImage;
    }

    public void setSuratImage(@NonNull String suratImage) {
        this.suratImage = suratImage;
    }

    @NonNull
    public String getSuratPublishedDate() {
        return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(suratPublishedDate);
    }

    public void setSuratPublishedDate(@NonNull Date suratPublishedDate) {
        this.suratPublishedDate = suratPublishedDate;
    }

    //Added for Child JSON Object
    public class Source {
        @SerializedName("name")
        private String sourceName;

        public String getSourceName() {
            return sourceName;
        }

        public void setSourceName(String sourceName) {
            this.sourceName = sourceName;
        }
    }

    //Image Binding - I didn't write suratviewmodel for just this method
    @BindingAdapter({"bind:imgUrl"})
    public static void setImage(ImageView imageView, String imgUrl) {
        Glide.with(imageView.getContext()).load(imgUrl).into(imageView);
    }
}
