package com.example.android.theguardiannewsfeedapp;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

public class GamesFragment extends Fragment implements android.support.v4.app.LoaderManager.LoaderCallbacks<List<Article>>{

    private ListView articleListView;
    private TextView emptyView;
    private ProgressBar progressBar;

    private String GUARDIAN_BASE_URL;

    public GamesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.article_list,container,false);

        articleListView = rootView.findViewById(R.id.list);
        emptyView = rootView.findViewById(R.id.emptyView);
        progressBar = rootView.findViewById(R.id.progressBar);

        articleListView.setEmptyView(emptyView);
        progressBar.setVisibility(View.VISIBLE);

        if(getActivity()!=null){
            GUARDIAN_BASE_URL = getActivity().getString(R.string.guardian_base_url);
        }

        if (getActivity()!=null){
            ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm!=null){
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                if (activeNetwork!=null&&activeNetwork.isConnectedOrConnecting()){
                    getLoaderManager().initLoader(2,null,this);
                }else{
                    progressBar.setVisibility(View.GONE);
                    emptyView.setText(R.string.no_internet_found);
                }

            }
        }

        return rootView;
    }
    @NonNull
    @Override
    public android.support.v4.content.Loader<List<Article>> onCreateLoader(int id, @Nullable Bundle args) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        String articlesToLoad = sharedPreferences.getString(
                getString(R.string.settings_articles_to_load_key),
                getString(R.string.settings_articles_to_load_default));

        String orderBy  = sharedPreferences.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default));

        Uri baseUri = Uri.parse(GUARDIAN_BASE_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("tag","games/games");
        uriBuilder.appendQueryParameter("order-by",orderBy);
        uriBuilder.appendQueryParameter("page-size",articlesToLoad);

        return new ArticleLoader(getActivity(),uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(@NonNull android.support.v4.content.Loader<List<Article>> loader, final List<Article> articles) {
        progressBar.setVisibility(View.GONE);
        ArticleAdapter adapter = new ArticleAdapter(getActivity(), articles);

        articleListView.setAdapter(adapter);
        emptyView.setText(R.string.empty_list_string);

        articleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String articleUrl = articles.get(position).getmWebURl();
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(articleUrl));
                startActivity(browserIntent);
            }
        });
    }

    @Override
    public void onLoaderReset(@NonNull android.support.v4.content.Loader<List<Article>> loader) {

    }
}
