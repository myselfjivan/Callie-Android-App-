package com.status.callie.ui.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.status.callie.R;
import com.status.callie.accounts.AccountConstants;
import com.status.callie.model.Status;
import com.status.callie.ui.cards.StatusCardWithList;

import it.gmariotti.cardslib.library.view.CardView;

/**
 * Created by om on 16/10/16.
 */
public class StatusShow extends Fragment implements View.OnClickListener {

    String TAG = "fragment_status_show";
    Button setStatusButton;
    EditText setStatusEditText;
    String textStatus;
    Status status;
    View view;
    private SharedPreferences shared_pref_login;
    Context context;
    StatusCardWithList card;
    CardView cardView2;
    String last10Statuses;

    public StatusShow() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        context = getActivity();
        status = new Status(context);
        shared_pref_login = context.getSharedPreferences(AccountConstants.SHARED_PREF_LOGIN, Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initCard();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_status_show, container, false);
        last10Statuses = status.getLast10Status(shared_pref_login.getString(AccountConstants.TOKEN, ""));
        Log.d(TAG, "onCreateView: "+last10Statuses);
        setStatusButton = (Button) view.findViewById(R.id.set_status_button);
        setStatusEditText = (EditText) view.findViewById(R.id.set_status_edit_text);
        setStatusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textStatus = setStatusEditText.getText().toString();
                status.statusStore(textStatus, shared_pref_login.getString(AccountConstants.TOKEN, ""));

            }
        });

        return view;
    }

    @Override
    public void onClick(View v) {

    }

    private void initCard() {
        card = new StatusCardWithList(context);
        card.init();

        //Set card in the cardView
        cardView2 = (CardView) getActivity().findViewById(R.id.recent_status);
        cardView2.setCard(card);

    }
}
