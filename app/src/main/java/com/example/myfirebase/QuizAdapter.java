package com.example.myfirebase;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

class QuizAdapter extends BaseAdapter {

    private Context context;
    private List<Quiz> list;

    public QuizAdapter(Context context, List<Quiz> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context,R.layout.layout_lista,null);
        //objs da lista
        TextView textId= v.findViewById(R.id.textId);
        TextView textPergunta= v.findViewById(R.id.textPergunta);
        TextView textResposta= v.findViewById(R.id.textResposta);

        textId.setText(list.get(position).getId());
        textPergunta.setText(list.get(position).getPergunta());
        textResposta.setText(list.get(position).getResposta());


        return v;
    }
}
