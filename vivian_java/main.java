package viviantong.driving_tracker;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;


public class main extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /* Called when use clicks view_event button */
    public void view_event(View view){
        Intent intent = new Intent(this, view_event.class);
        startActivity(intent);
    }

    /* Called when use clicks view_log button */
    public void view_log(View view){
        Intent intent = new Intent(this, view_log.class);
        startActivity(intent);
    }

    /* Called when use clicks add_friend button */
    public void add_friend(View view){
        Intent intent = new Intent(this, add_friend.class);
        startActivity(intent);
    }

    /* Called when use clicks sign_out button */
    public void sign_out(View view){
        Intent intent = new Intent(this, sign_out.class);
        startActivity(intent);
    }
}
