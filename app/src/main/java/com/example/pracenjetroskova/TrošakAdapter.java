package com.example.pracenjetroskova;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TrošakAdapter extends RecyclerView.Adapter {
    Context context;
    Date datum;
    List<Troškovi> troskovilista;
    List<String> lkljucevi;



    public TrošakAdapter(List<Troškovi> troskovilista, List<String> lkljucevi){
        this.troskovilista = troskovilista;
        this.lkljucevi = lkljucevi;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = (View) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.trosak_red, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder)holder;
        Troškovi troškovi = troskovilista.get(position);
        myViewHolder.datum.setText(troškovi.DajDatum());
        myViewHolder.naziv.setText(troškovi.DajNaziv());
        myViewHolder.opis.setText(troškovi.DajOpis());
        myViewHolder.trosak.setText(troškovi.DajTrosak());
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -7);
        Date trenutniDatum = calendar.getTime();
        SimpleDateFormat datumFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
             datum = datumFormat.parse(troškovi.DajDatum());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (datum.after(trenutniDatum)) {
            holder.itemView.setClickable(true);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, UrediTrosak.class);
                    intent.putExtra("kljuc", lkljucevi.get(position));
                    intent.putExtra("naziv", troškovi.DajNaziv());
                    intent.putExtra("datum", troškovi.DajDatum());
                    intent.putExtra("opis", troškovi.DajOpis());
                    intent.putExtra("ftrosak", troškovi.DajTrosak());
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return troskovilista.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView datum, naziv, opis, trosak;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            datum = itemView.findViewById(R.id.datum_red);
            naziv = itemView.findViewById(R.id.naziv_red);
            opis = itemView.findViewById(R.id.opis_red);
            trosak = itemView.findViewById(R.id.trosak_red);

        }
    }

}
