package com.project.newsapp.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.project.newsapp.R;
import com.project.newsapp.clicklisteners.AdapterItemClickListener;
import com.project.newsapp.databinding.SuratBinding;
import com.project.newsapp.model.Surat;

import java.util.List;

public class AdapterListSurat extends RecyclerView.Adapter<AdapterListSurat.SuratViewHolder> {

    private List<Surat> items;
    private AdapterItemClickListener adapterItemClickListener;

    public AdapterListSurat(List<Surat> items, AdapterItemClickListener adapterItemClickListener) {
        this.items = items;
        this.adapterItemClickListener = adapterItemClickListener;
    }

    @Override
    public SuratViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SuratBinding suratBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_news_dashboard, parent, false);
        return new SuratViewHolder(suratBinding);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(SuratViewHolder holder, final int position) {
        holder.bind(getItem(position), adapterItemClickListener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private Surat getItem(int position) {
        return items.get(position);
    }

    public class SuratViewHolder extends RecyclerView.ViewHolder {
        private SuratBinding suratBinding;

        public SuratViewHolder(SuratBinding suratBinding) {
            super(suratBinding.getRoot());
            this.suratBinding = suratBinding;
        }

        public void bind(Surat surat, AdapterItemClickListener adapterItemClickListener) {
            this.suratBinding.setSurat(surat);
            this.suratBinding.setClickListener(adapterItemClickListener);
        }

    }

}