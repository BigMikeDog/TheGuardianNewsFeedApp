package com.example.android.theguardiannewsfeedapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

class ArticleAdapter extends ArrayAdapter<Article>{

    private Context mContext;

    ArticleAdapter(Context context, List<Article> articles){
        super(context,0,articles);
        mContext=context;
    }

    private String formatDate(String dateOriginal){
        StringBuilder sb = new StringBuilder();
        String monthsStrings[] = {mContext.getString(R.string.january_build),
                mContext.getString(R.string.february_build),
                mContext.getString(R.string.march_build),
                mContext.getString(R.string.april_build),
                mContext.getString(R.string.may_build),
                mContext.getString(R.string.june_build),
                mContext.getString(R.string.july_build),
                mContext.getString(R.string.aug_build),
                mContext.getString(R.string.september_build),
                mContext.getString(R.string.october_build),
                mContext.getString(R.string.november_build),
                mContext.getString(R.string.december_build)};

        if (String.valueOf(dateOriginal.charAt(5)).equals("0")){
            sb.append(monthsStrings[Integer.valueOf(String.valueOf(dateOriginal.charAt(6)))]);
        }else{
            sb.append(monthsStrings[Integer.valueOf(dateOriginal.substring(5,7))]);
        }
        sb.append(" ");
        if (String.valueOf(dateOriginal.charAt(8)).equals("0")){
            sb.append(dateOriginal.charAt(9));
        }else{
            sb.append(dateOriginal.substring(8,10));
        }
        sb.append(", ").append(dateOriginal.substring(0,4));

        return sb.toString();
    }

    private String formatName(String name){
        if (name!=null){
            return "- "+name;
        }else{
            return null;
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView==null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.article_list_item,parent,false);
        }

        Article currentArticle = getItem(position);

        TextView sectionView = listItemView.findViewById(R.id.sectionView);
        TextView titleView = listItemView.findViewById(R.id.titleView);
        TextView dateView = listItemView.findViewById(R.id.dateView);
        TextView authorView = listItemView.findViewById(R.id.author_view);

        if (currentArticle!=null){
            sectionView.setText(currentArticle.getmSectionName());
            titleView.setText(currentArticle.getmWebTitle());
            dateView.setText(formatDate(currentArticle.getmDate()));

            String author = formatName(currentArticle.getmAuthor());
            if (author!=null){
                authorView.setText(author);
            }else{
                authorView.setText(R.string.no_author);
            }
        }

        return listItemView;
    }
}
