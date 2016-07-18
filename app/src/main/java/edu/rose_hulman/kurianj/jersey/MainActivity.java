package edu.rose_hulman.kurianj.jersey;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private String[] jerseyInfo = new String[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        TextView jerseyName = (TextView)findViewById(R.id.jersey_name);
        TextView jerseyNumber = (TextView)findViewById(R.id.jersey_number);
        ImageView jerseyImage = (ImageView)findViewById(R.id.jersey_image);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addJersey();
            }
        });
        Button resetButton = (Button)findViewById(R.id.reset_button);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jerseyInfo[0] = "Android";
                jerseyInfo[1] = "17";
                jerseyInfo[2] = "red";
                changeJersey(jerseyInfo);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void addJersey() {
        DialogFragment df = new DialogFragment(){
            @Override
            public Dialog onCreateDialog(Bundle b) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_add, null, false);
                builder.setView(view);
                final EditText nameEditText = (EditText) view.findViewById(R.id.edit_player_name);
                final EditText quantityEditText = (EditText) view.findViewById(R.id.edit_number);
                final Switch colorSwitch = (Switch) view.findViewById(R.id.switch1);
                colorSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                        Log.d("Value ", ": " + isChecked);
                    }
                });
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        jerseyInfo[0] = nameEditText.getText().toString();
                        if(quantityEditText.getText().toString().matches(""))
                        {
                            jerseyInfo[1] = "0";
                        }
                        else
                        {
                            int quantity = Integer.parseInt(quantityEditText.getText().toString());
                            jerseyInfo[1] = String.format("%02d", quantity);
                        }
                        if(colorSwitch.isChecked())
                        {
                            jerseyInfo[2] = "red";
                        }
                        else
                        {
                            jerseyInfo[2] = "blue";
                        }

                        changeJersey(jerseyInfo);
                    }
                });
                builder.setNegativeButton(android.R.string.cancel, null);
                return builder.create();

            }
        };
        df.show(getSupportFragmentManager(), "add");
    }

    private void changeJersey(String[] jerseyInfo) {
        TextView jerseyName = (TextView)findViewById(R.id.jersey_name);
        TextView jerseyNumber = (TextView)findViewById(R.id.jersey_number);
        ImageView jerseyImage = (ImageView)findViewById(R.id.jersey_image);

        jerseyName.setText(jerseyInfo[0]);
        if(jerseyInfo[1].matches(""))
        {
            jerseyNumber.setText("0");
        }
        jerseyNumber.setText(jerseyInfo[1]);
        Log.d("TTT", "THE JERSEY COLOR IS: "+jerseyInfo[2]);
        if(jerseyInfo[2].equals("red")){
            jerseyImage.setImageResource(R.drawable.red_jersey);
        }
        else
        {
            jerseyImage.setImageResource(R.drawable.blue_jersey);

        }
    }
}
