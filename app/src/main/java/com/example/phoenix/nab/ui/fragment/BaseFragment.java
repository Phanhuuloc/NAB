package com.example.phoenix.nab.ui.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.phoenix.nab.R;

/**
 * Created by Phoenix on 3/10/17.
 */

public abstract class BaseFragment extends Fragment {

    SharedPreferences sharedPref;

    private boolean isViewPrepared;
    private boolean hasFetchData;

    /**
     * Shows a {@link android.widget.Toast} message.
     *
     * @param message An string representing a message to be shown.
     */
    protected void showToastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    protected void showSnackBar(@NonNull View view, @StringRes int idLayout) {
        Snackbar.make(view, idLayout, Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show();
    }

    protected void showAlertDialog(String message, DialogInterface.OnClickListener ok, DialogInterface.OnClickListener cancel) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(message);
        builder.setCancelable(true);

        if (ok != null)
            builder.setPositiveButton(getString(R.string.dialog_option_ok), ok);
        if (cancel != null)
            builder.setNegativeButton(getString(R.string.dialog_option_cancel), cancel);
        AlertDialog alertDialog = builder.create();

        alertDialog.show();
    }

    /* For lazy load data ...*/
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.v(getClass().getSimpleName(), getClass().getName() + "------>isVisibleToUser = " + isVisibleToUser);
        if (isVisibleToUser) {
            lazyFetchDataIfPrepared();
        }
    }

    private void lazyFetchDataIfPrepared() {
        if (getUserVisibleHint() && !hasFetchData && isViewPrepared) {
            hasFetchData = true;
            lazyFetchData();
        }
    }

    protected void lazyFetchData() {
        Log.v(getClass().getSimpleName(), getClass().getName() + "------>lazyFetchData");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewPrepared = true;

        lazyFetchDataIfPrepared();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hasFetchData = false;
        isViewPrepared = false;
    }

}
