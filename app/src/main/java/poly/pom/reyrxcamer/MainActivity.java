package poly.pom.reyrxcamer;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.TextureView;
import android.widget.Button;
import android.widget.Toast;

import com.ragnarok.rxcamera.RxCamera;
import com.ragnarok.rxcamera.config.RxCameraConfig;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "RxCamera";
    @BindView(R.id.btTake)
    Button btTake;
    @BindView(R.id.preview_surface)
    TextureView previewSurface;
    private RxCameraConfig config;
    private RxCamera camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        openCamera();


    }

    private void openCamera() {
        RxCameraConfig config = new RxCameraConfig.Builder()
                .useBackCamera()
                .setAutoFocus(true)
                .setPreferPreviewFrameRate(15, 30)
                .setPreferPreviewSize(new Point(640, 480), false)
                .setHandleSurfaceEvent(true)
                .build();
        Log.d(TAG, "config: " + config);
        RxCamera.open(this, config).flatMap(new Func1<RxCamera, Observable<RxCamera>>() {
            @Override
            public Observable<RxCamera> call(RxCamera rxCamera) {

                camera = rxCamera;
                return rxCamera.bindTexture(previewSurface);
            }
        }).flatMap(new Func1<RxCamera, Observable<RxCamera>>() {
            @Override
            public Observable<RxCamera> call(RxCamera rxCamera) {

                return rxCamera.startPreview();
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<RxCamera>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "open camera error: " + e.getMessage());
            }

            @Override
            public void onNext(final RxCamera rxCamera) {
                camera = rxCamera;
                Toast.makeText(MainActivity.this, "Now you can tap to focus", Toast.LENGTH_LONG).show();
            }
        });
    }

    @OnClick(R.id.btTake)
    public void onClick() {
    }
}
