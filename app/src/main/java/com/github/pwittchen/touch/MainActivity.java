package com.github.pwittchen.touch;

import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.github.pwittchen.touch.library.Touch;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
  protected TextView info;
  private Touch touch;
  private Disposable disposable;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    info = (TextView) findViewById(R.id.info);
    touch = new Touch();
    disposable = touch.observe()
        .subscribeOn(Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(touchEvent -> info.setText(touchEvent.toString()));
  }

  @Override public boolean dispatchTouchEvent(MotionEvent event) {
    touch.dispatchTouchEvent(event);
    return super.dispatchTouchEvent(event);
  }

  @Override protected void onPause() {
    super.onPause();
    if (disposable != null && !disposable.isDisposed()) {
      disposable.dispose();
    }
  }
}
