package com.status.callie.ui.fragments;

import android.os.Bundle;

import com.status.callie.R;

/**
 * Created by jivan.ghadage on 10/20/2016.
 */
public abstract class MaterialV1Fragment extends BaseMaterialFragment {
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getActivity().getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.gray_background));
    }
}
