package edu.washington.sraden.lifecounter;

import android.nfc.Tag;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;


public class LifeCounter extends ActionBarActivity {
    private static final String TAG = "LifeCounterMain";
    private int numberOfPlayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_counter);

        if (savedInstanceState != null) {
            numberOfPlayers = savedInstanceState.getInt("numPlayers");
            for (int i = 0; i < numberOfPlayers; i++) {
                
            }
        }


    }

    public void clickFunction(View v) {
        TableLayout player = (TableLayout) v.getParent().getParent();
        TextView health = (TextView) player.getChildAt(2).findViewById(R.id.player_health);
        int currHealth = Integer.parseInt(health.getText().toString());
        switch (v.getTag().toString()) {
            case "Pos1": currHealth += 1;
                break;
            case "Pos5": currHealth += 5;
                break;
            case "Min1": currHealth -= 1;
                break;
            case "Min5": currHealth -= 5;
                break;
        }
        if (currHealth < 0) {
            currHealth = 0;
        }
        health.setText("" + currHealth);
    }

    protected void onSaveInstanceState(Bundle saveInstanceState) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_life_counter, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
