package com.github.pwittchen.touch.library;

import android.view.MotionEvent;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;

public class Touch {
  private FlowableEmitter<TouchEvent> emitter;

  /**
   * observes touch events with RxJava Flowable using BUFFER backpressure strategy
   * Remember to call {@link #dispatchTouchEvent(MotionEvent) dispatchTouchEvent} method as well.
   *
   * @return Flowable with the stream of touch events
   */
  public Flowable<TouchEvent> observe() {
    return observe(BackpressureStrategy.BUFFER);
  }

  /**
   * observes touch events with RxJava Flowable.
   * Remember to call {@link #dispatchTouchEvent(MotionEvent) dispatchTouchEvent} method as well.
   *
   * @param backpressureStrategy backpressure strategy for RxJava Flowable
   * @return Flowable with the stream of touch events
   */
  public Flowable<TouchEvent> observe(BackpressureStrategy backpressureStrategy) {
    return Flowable.create(emitter -> Touch.this.emitter = emitter, backpressureStrategy);
  }

  /**
   * Called to process touch screen events.
   * Required to invoke stream of Flowables.
   *
   * @param event MotionEvent
   */
  public boolean dispatchTouchEvent(final MotionEvent event) {
    switch (event.getAction()) {
      case MotionEvent.ACTION_DOWN: // user started touching the screen
        onNextSafely(new TouchEvent(event.getX(), event.getY(), TouchType.DOWN));
        break;
      case MotionEvent.ACTION_UP:   // user stopped touching the screen
        onNextSafely(new TouchEvent(event.getX(), event.getY(), TouchType.UP));
        break;
      case MotionEvent.ACTION_MOVE: // user is moving finger on the screen
        onNextSafely(new TouchEvent(event.getX(), event.getY(), TouchType.MOVE));
        break;
      default:
        break;
    }

    return true;
  }

  private void onNextSafely(final TouchEvent touchEvent) {
    if (emitter != null) {
      emitter.onNext(touchEvent);
    }
  }
}
