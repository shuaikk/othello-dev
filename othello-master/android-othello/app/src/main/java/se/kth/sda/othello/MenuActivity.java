package se.kth.sda.othello;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import android.support.v7.app.AppCompatActivity;
import se.kth.sda.othello.MainActivity;
import se.kth.sda.othello.R;
import org.json.JSONException;
import org.json.JSONObject;

public class MenuActivity extends AppCompatActivity {
  //  User currentPlayer = null;
  //  Intent currentintent = getIntent();
   JSONObject jsonPlayer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent currentintent = getIntent();
        String jsonString = currentintent.getStringExtra("player");
        try {
            jsonPlayer = new JSONObject(jsonString);
        }catch(Exception e){

        }

    }

    /** Called when the user clicks the button */
     public void startHumanGame(View view) {


         String name ="";
         try {
             name = jsonPlayer.getString("coins");
         }catch(Exception e){

         }
         Toast t = Toast.makeText(this,""+name, Toast.LENGTH_SHORT);
         t.show();

         Intent intent = new Intent(this, MainActivity.class);
         intent.putExtra("player",jsonPlayer.toString());



        intent.putExtra(MainActivity.GAME_TYPE, MainActivity.GAME_HUMAN);startActivityForResult(intent, 0);

    }
    public void Login(View view){
        Intent itn1 = new Intent(this, RegActivity.class);
        startActivity(itn1);
    }

     public void help(View v) {

         Intent itn = new Intent(this,HelpActivity.class);
         startActivity(itn);
     }
    public void setting(View v) {

        Intent itn = new Intent(this,SettingActivity.class);
        startActivity(itn);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Toast t = Toast.makeText(this, data.getExtras().getString(MainActivity.GAME_RESULT), Toast.LENGTH_SHORT);
        t.show();
    }

}
