package com.cosmosoft.flashmotel.fragments;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.cosmosoft.flashmotel.R;
import com.cosmosoft.flashmotel.util.Util;

/**
 * A simple {@link Fragment} subclass.
 */
public class PromocoesFragment extends Fragment {


    public PromocoesFragment() {
        // Required empty public constructor
    }
    private View rootView;
    WebView web;
    private View mProgressView;
    private View mWebView;
    private Context ctx;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        web = (WebView) rootView.findViewById(R.id.webview01);
        mWebView = rootView.findViewById(R.id.web_view_linear);
        mProgressView = rootView.findViewById(R.id.login_progress);
        web.setWebViewClient(new PromocoesFragment.myWebClient());
        showProgress(true);
        web.getSettings().setJavaScriptEnabled(true);
        ctx = getActivity();

        //Verifica se a internet esta disponivel
        if (Util.isNetworkAvailable(ctx)) {

            //HTML 5 carregado localmente
            //String url ="file:///android_asset/teste.html";

            //HTML 5 carregado da internet
            String url = "http://cosmosoft.com.br/flashmotel/public/desconto/consultar";

            //Log para mostrar no logcat do Android Studio a url
            Log.i("URL ->",url);
            web.loadUrl(url);


        }else{
            //HTML 5 de mensagem de sem conexao
            String url ="file:///android_asset/sem_conexao.html";
            web.loadUrl(url);
        }
        return rootView;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mWebView.setVisibility(show ? View.GONE : View.VISIBLE);
            mWebView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mWebView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mWebView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    //Classe para tratar a chamada da WebView
    public class myWebClient extends WebViewClient
    {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);
            showProgress(false);
        }

        //Redireciona para HTML de erro caso nao consiga conectar ou carregar a url
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            view.loadUrl("file:///android_asset/sem_conexao.html");
        }
    }


}
