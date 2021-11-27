package com.project.newsapp.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.project.newsapp.model.Surat;
import com.project.newsapp.model.TotalSurat;
import com.project.newsapp.restapi.ApiClient;
import com.project.newsapp.restapi.RestInterface;
import com.project.newsapp.utils.Util;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {

    private MutableLiveData<List<Surat>> suratLiveData;
    private List<Surat> suratList;
    private String countryCode;
    private String apiKey;

    public MainViewModel() {
        suratLiveData = new MutableLiveData<>();
        suratList = new ArrayList<>();
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
        getSurat(countryCode, "");
    }

    public MutableLiveData<List<Surat>> getSuratLiveData() {
        return suratLiveData;
    }

    private RestInterface getRestInterface() {
        RestInterface[] restInterface = new RestInterface[1];
        restInterface[0] = ApiClient.getClient(Util.API_BASE_URL).create(RestInterface.class);
        return restInterface[0];
    }

    private void getSurat(String langCode, String category) {
        RestInterface restInterface = getRestInterface();
        Call<TotalSurat> call;
        suratList.clear();
        suratLiveData.setValue(null);
        if (!category.equals("")) {
            call = restInterface.getTotalSurat(langCode, category, apiKey);
        } else {
            call = restInterface.getTotalSurat(langCode, apiKey);
        }
        call.enqueue(new Callback<TotalSurat>() {
            @Override
            public void onResponse(Call<TotalSurat> call, Response<TotalSurat> response) {
                if (response.body() != null) {
                    TotalSurat totalSurat = response.body();
                    fillSuratList(totalSurat);
                }
            }

            @Override
            public void onFailure(Call<TotalSurat> call, Throwable t) {
                suratLiveData.setValue(null);
            }
        });
    }

    private void getSearchedSurat(String keyword) {
        RestInterface restInterface = getRestInterface();
        Call<TotalSurat> call;
        suratList.clear();
        suratLiveData.setValue(null);
        call = restInterface.getSearchedTotalSurat(keyword, apiKey);

        call.enqueue(new Callback<TotalSurat>() {
            @Override
            public void onResponse(Call<TotalSurat> call, Response<TotalSurat> response) {
                if (response.body() != null) {
                    TotalSurat totalSurat = response.body();
                    fillSuratList(totalSurat);
                }
            }

            @Override
            public void onFailure(Call<TotalSurat> call, Throwable t) {
                suratLiveData.setValue(null);
            }
        });
    }

    private void fillSuratList(TotalSurat totalSurat) {
        suratList.addAll(totalSurat.getSuratList());
        suratLiveData.setValue(suratList);
    }

    public void suratCategoryClick(Object category) {
        getSurat(countryCode, String.valueOf(category));
    }

    public void searchSurat(String keyword) {
        getSearchedSurat(keyword);
    }
}
