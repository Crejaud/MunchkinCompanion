package com.crejaud.jrejaud.munchkincompanion;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * Created by creja_000 on 7/25/2014.
 */
// Dialog Box that shows up once someone has reached a base level of 10
public class winnerAlertFragment extends DialogFragment implements View.OnClickListener {

    Button restart, cancel;
    TextView winText;
    Communicator communicator;
    String name;

    static winnerAlertFragment newInstance(String name) {
        winnerAlertFragment f = new winnerAlertFragment();

        // Supply String input as an argument.
        Bundle args = new Bundle();
        args.putString("string", name);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        communicator = (Communicator) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, 0); // Gets rid of Title Bar on Dialog Box
        name = getArguments().getString("string"); // Retrieves string 'name' that was sent through creation of Dialog Box
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.winner_dialog, null);

        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.rounded_dialog);

        Typeface header1 = Typeface.createFromAsset(this.getResources().getAssets(),"windlass.ttf");
        Typeface header2 = Typeface.createFromAsset(this.getResources().getAssets(),"pala.ttf");

        winText = (TextView) view.findViewById(R.id.textWin);
        restart = (Button) view.findViewById(R.id.btnRestart);
        cancel = (Button) view.findViewById(R.id.btnCancel);
        setWinText(name); // Renames winText given the name of player
        restart.setOnClickListener(this);
        cancel.setOnClickListener(this);
        setCancelable(false);

        winText.setTypeface(header2);
        restart.setTypeface(header2);
        cancel.setTypeface(header2);

        return view;
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.btnRestart) {
            //communicator.onDialogMessage("Thank you for using this app to play Munchkin!");
            dismiss();
            communicator.restartGame();
        }
        if (view.getId()==R.id.btnCancel) {
            //communicator.onDialogMessage("You have selected to continue.");
            dismiss();
        }
    }

    public void setWinText(String name) {
        String winString = name + " has Won! Congratz!";
        winText.setText(winString);
    }

    interface Communicator {
        public void onDialogMessage(String message);
        public void restartGame();
    }
}
