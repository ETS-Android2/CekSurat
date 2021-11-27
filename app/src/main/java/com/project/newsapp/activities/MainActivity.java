package com.project.newsapp.activities;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.newsapp.R;
import com.project.newsapp.adapters.AdapterListSurat;
import com.project.newsapp.clicklisteners.AdapterItemClickListener;
import com.project.newsapp.clicklisteners.SuratDialogClickListeners;
import com.project.newsapp.databinding.SuratDialogBinding;
import com.project.newsapp.model.Surat;
import com.project.newsapp.utils.Util;
import com.project.newsapp.viewmodels.MainViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements LifecycleOwner, AdapterItemClickListener {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;


    MainActivity context;
    MainViewModel viewModel;
    AdapterListSurat adapterListSurat;
    List<Surat> suratList;

    private String firstControl = "firstControl";
    private String countryPositionPref = "countryPositionPref";
    SharedPreferences pref;
    private String[] countrys;
    private TypedArray countrysIcons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        ButterKnife.bind(this);
        countrys = getResources().getStringArray(R.array.countrys);
        countrysIcons = getResources().obtainTypedArray(R.array.countrysIcons);

        initToolbar();

        suratList = new ArrayList<>();
        adapterListSurat = new AdapterListSurat(suratList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapterListSurat);


        viewModel = ViewModelProviders.of(context).get(MainViewModel.class);
        viewModel.getSuratLiveData().observe(context, suratListUpdateObserver);
        viewModel.setApiKey(getString(R.string.surat_api_key));


    }


    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setTitle(null);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Util.setSystemBarColor(this, android.R.color.white);
        Util.setSystemBarLight(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        Util.changeMenuIconColor(menu, Color.BLACK);
        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager) MainActivity.this.getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(MainActivity.this.getComponentName()));
        }
        searchView.setQueryHint(getString(R.string.search_in_everything));
        if (searchView != null)
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    if (viewModel != null) viewModel.searchSurat(query);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
        return true;
    }

    public void categoryClicked(View view) {
        viewModel.suratCategoryClick(String.valueOf(view.getTag()));
    }


    Observer<List<Surat>> suratListUpdateObserver = new Observer<List<Surat>>() {
        @Override
        public void onChanged(List<Surat> surat) {
            suratList.clear();
            if (surat != null) {
                suratList.addAll(surat);
            }
            adapterListSurat.notifyDataSetChanged();
        }
    };


    @Override
    public void onNewsItemClick(Surat surat) {

    }

    @Override
    public void SuratItemClick(Surat surat) {
        showDialogPolygon(surat);
    }

    private void showDialogPolygon(Surat surat) {
        final Dialog dialog = new Dialog(this);
        SuratDialogBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getApplicationContext()), R.layout.dialog_header_polygon, null, false);
        binding.setSurat(surat);
        binding.setListener(new SuratDialogClickListeners() {
            @Override
            public void onGotoWebSiteClick(String url) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }

            @Override
            public void onDismissClick() {
                dialog.dismiss();
            }
        });

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(binding.getRoot());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);

        dialog.show();
    }

}
