package com.hardboris.bgquiebra.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hardboris.bgquiebra.R;
import com.hardboris.bgquiebra.model.Baixa;

import java.util.ArrayList;

public class BaixaAdapter extends ArrayAdapter<Baixa> {

    public BaixaAdapter(@NonNull Context context, @NonNull ArrayList<Baixa> baixas) {
        super(context, 0, baixas);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        return view(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        if (position == 0){
            return viewInicial(position,convertView,parent);
        }
        return view(position,convertView,parent);
    }

    private View view(int position, View convertView, ViewGroup parent){
        convertView = LayoutInflater.from(getContext()).inflate(
                R.layout.spinner_item, parent, false
        );

        TextView txtCodigo = convertView.findViewById(R.id.txtCodigo);
        TextView txtBaixa = convertView.findViewById(R.id.txtMotivo);
        Baixa baixa = getItem(position);
        txtCodigo.setText(baixa.getCodigo());
        txtBaixa.setText(baixa.getBaixa());

        return convertView;
    }

    private View viewInicial(int position, View convertView, ViewGroup parent){
        convertView = LayoutInflater.from(getContext()).inflate(
                R.layout.spinner_item, parent, false
        );

        TextView txtCodigo = convertView.findViewById(R.id.txtCodigo);
        TextView txtBaixa = convertView.findViewById(R.id.txtMotivo);
        Baixa baixa = getItem(position);
        txtCodigo.setText(baixa.getCodigo());
        txtBaixa.setText(baixa.getBaixa());

        return convertView;
    }

}
