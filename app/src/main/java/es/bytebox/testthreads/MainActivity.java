package es.bytebox.testthreads;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "es.bytebox.testthreads";

    Thread worker1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        final ImageView image1 = (ImageView) findViewById(R.id.image1);
        final Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                final Drawable b = loadImage("http://www.planwallpaper.com/static/images/i-should-buy-a-boat.jpg");
                if (image1 != null) {
                    image1.post(new Runnable() {
                        @Override
                        public void run() {
                            image1.setImageDrawable(b);
                        }
                    });
                }
            }
        };

        if (fab != null) fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Cargando imagen...", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                worker1 = new Thread(runnable1);
                worker1.start();
            }
        });
    }


    private Drawable loadImage(String url) {
        Drawable d = null;
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            d = Drawable.createFromStream(is, "foto grande");
            return d;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return d;
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
}
