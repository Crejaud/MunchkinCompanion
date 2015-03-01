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
 * Created by creja_000 on 8/13/2014.
 */
public class fightAlertFragment extends DialogFragment implements View.OnClickListener {
    Button okBtn;
    TextView outcomeText;
//    Communicator communicator;
    String message;
    static fightAlertFragment newInstance(String message) {
        fightAlertFragment f = new fightAlertFragment();

        // Supply String input as an argument.
        Bundle args = new Bundle();
        args.putString("string", message);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
//        communicator = (Communicator) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, 0); // Gets rid of Title Bar on Dialog Box
        message = getArguments().getString("string"); // Retrieves string 'name' that was sent through creation of Dialog Box
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fight_dialog, null);

        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.rounded_dialog);

        Typeface header1 = Typeface.createFromAsset(this.getResources().getAssets(),"windlass.ttf");
        Typeface header2 = Typeface.createFromAsset(this.getResources().getAssets(),"pala.ttf");

        outcomeText = (TextView) view.findViewById(R.id.textOutcome);
        okBtn = (Button) view.findViewById(R.id.btnOk);
        outcomeText.setText(message); // Renames winText given the name of player
        okBtn.setOnClickListener(this);
        setCancelable(false);

        okBtn.setTypeface(header2);
        outcomeText.setTypeface(header2);

        return view;
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.btnOk) {
            //communicator.onDialogMessage("You pressed OK");
            dismiss();
            ((FightMenu)getActivity()).killFightMenu();
        }
    }

//    interface Communicator {
//        public void onDialogMessage(String message);
//    }
}
