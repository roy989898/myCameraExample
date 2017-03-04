package poly.pom.reyrxcamer;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.File;

/**
 * Created by User on 4/3/2017.
 */

public class StaticMethod {

    public static void addToGallery(File file, Context context) {
        Uri photoUri = Uri.fromFile(file);
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//        File f = new File(mCurrentPhotoPath);
        mediaScanIntent.setData(photoUri);
        context.sendBroadcast(mediaScanIntent);
    }
}
