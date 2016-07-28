package aica.clienttest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView im = (ImageView) findViewById(R.id.imageView);
        String url = "https://lh6.googleusercontent.com/-H3PYmWMurJA/AAAAAAAAAAI/AAAAAAABP9A/4MO2m7bIBFo/s0-c-k-no-ns/photo.jpg";
        String name = "Kaleth";
        if (im != null) {

            //Picasso.with(context).load(imageId[position]).into(holder.imageView);
            new SendHttpRequestTask(im, this).execute(url, name);
        }
    }
}
