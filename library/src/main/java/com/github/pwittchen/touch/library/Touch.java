package com.github.pwittchen.touch.library;

import android.view.MotionEvent;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;

public class Touch {
  private FlowableEmitter<TouchEvent> emitter;

  public Flowable<TouchEvent> observe() {
    return observe(BackpressureStrategy.BUFFER);
  }

  public Flowable<TouchEvent> observe(BackpressureStrategy backpressureStrategy) {
    return Flowable.create(emitter -> Touch.this.emitter = emitter, backpressureStrategy);
  }

  /**
   * Called to process touch screen events.
   *
   * @param event MotionEvent
   */
  public boolean dispatchTouchEvent(final MotionEvent event) {
    checkNotNull(event, "event == null");
    boolean isEventConsumed = false;

    switch (event.getAction()) {
      case MotionEvent.ACTION_DOWN: // user started touching the screen
        onActionDown(event);
        break;
      case MotionEvent.ACTION_UP:   // user stopped touching the screen
        isEventConsumed = onActionUp(event);
        break;
      case MotionEvent.ACTION_MOVE: // user is moving finger on the screen
        onActionMove(event);
        break;
      default:
        break;
    }

    return isEventConsumed;
  }

  private void onActionDown(final MotionEvent event) {
    onNextSafely(new TouchEvent(event.getX(), event.getY(), TouchType.DOWN));
  }

  private boolean onActionUp(final MotionEvent event) {
    onNextSafely(new TouchEvent(event.getX(), event.getY(), TouchType.UP));
    return true;
  }

  private void onActionMove(final MotionEvent event) {
    onNextSafely(new TouchEvent(event.getX(), event.getY(), TouchType.MOVE));
  }

  private void onNextSafely(final TouchEvent touchEvent) {
    if (emitter != null) {
      emitter.onNext(touchEvent);
    }
  }

  private void checkNotNull(final Object object, final String message) {
    if (object == null) {
      throw new IllegalArgumentException(message);
    }
  }
}
