package aica.clienttest;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import org.apache.http.client.HttpClient;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Ciro on 27/07/2016.
 */
public class SendHttpRequestTask extends AsyncTask<String, Void, Bitmap> {

    private final WeakReference<ImageView> imageViewReference;
    private Context context;

    public SendHttpRequestTask(ImageView imageView, Context context)
    {
        this.context = context;
        imageViewReference =  new WeakReference<ImageView>(imageView);
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        String url = params[0];
        String name = params[1];


        Bitmap data = downloadImage(url, name);

        return data;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        ImageView imageView = imageViewReference.get();
        imageView.setImageBitmap(result);
    }

    public Bitmap downloadImage(String url, String imgName) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Bitmap myLogo = null;
        try {
            Log.d("SendRequest","URL ["+url+"] - Name ["+imgName+"]");

            HttpURLConnection con = (HttpURLConnection) ( new URL(url)).openConnection();
            con.setRequestMethod("POST");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.connect();
            int responseCode = con.getResponseCode();
            //con.getOutput Stream().write( ("name=" + imgName).getBytes());
            if(responseCode == HttpURLConnection.HTTP_OK) {
                Log.d("Image", "Read");
                InputStream is = con.getInputStream();
                myLogo = BitmapFactory.decodeStream(is);

            }
            con.disconnect();
        }
        catch(Throwable t) {
            t.printStackTrace();
        }

        return myLogo;
    }

    public static Bitmap decodeSampledBitmapFromResource(InputStream in,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(in);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(in);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

}