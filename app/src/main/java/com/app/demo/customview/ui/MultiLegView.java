package com.app.demo.customview.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import com.app.demo.customview.R;
import com.app.demo.customview.transaction.Bet;
import com.app.demo.customview.transaction.Event;
import java.util.List;

public class MultiLegView extends View {

    private static final String TAG = MultiLegView.class.getSimpleName();

    private static final String WON = "WON";
    private static final String LOST = "LOST";
    private static final String PENDING = "PENDING";

    private static final int MAX_NUMBER_OF_EVENTS_PER_LINE = 10;
    private static final int START_POS_X = 15;
    private static final int START_POS_Y = 22;
    private static final int SMALL_GAP = 1;
    private static final int START_ANGLE = 90;
    private static final int SWEEP_ANGLE = 180;

    private static final float CIRCLE_RADIUS = 5;
    private static final float LINE_WIDTH = 15;
    private static final float DEFAULT_STROKE_WIDTH = 2;
    private static final float EXTRA_HEIGHT = 2.25f;

    private int numberOfEvents;
    private int betWonColour;
    private int betPendingColour;

    private float circleRadius;
    private float lineWidth;
    private float posX;
    private float posY;
    private float lineStartPosX;
    private float lineEndPosX;

    private boolean isGreyLine;
    private boolean isCrossDisplayed;

    private Context context;
    private Paint paintCircleWon;
    private Paint paintCirclePending;
    private Paint paintCircleLost;
    private Paint paintLine;
    private Paint paintArc;
    private Paint paintCross;
    private RectF rect;
    private Bet bet;

    public MultiLegView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.MultiLegView, 0, 0);

        try {
            betWonColour = typedArray.getInt(R.styleable.MultiLegView_mlv_resulted_color, betWonColour);
            betPendingColour = typedArray.getInt(R.styleable.MultiLegView_mlv_un_resulted_color, betPendingColour);
        } finally {
            typedArray.recycle();
        }

        this.context = context;
        rect = new RectF();
        circleRadius = convertDpToPx(CIRCLE_RADIUS);
        lineWidth = convertDpToPx(LINE_WIDTH);

        setPaintCircleWon();
        setPaintCirclePending();
        setPaintCircleLost();
        setPaintArc();
        setPaintLine();
        setPaintCross();
    }

    private void setPaintCircleWon() {
        paintCircleWon = new Paint();
        paintCircleWon.setStyle(Paint.Style.FILL);
        paintCircleWon.setAntiAlias(true);
        paintCircleWon.setColor(getResources().getColor(R.color.lime_green));
    }

    private void setPaintCirclePending() {
        paintCirclePending = new Paint();
        paintCirclePending.setStrokeWidth(convertDpToPx(DEFAULT_STROKE_WIDTH));
        paintCirclePending.setStyle(Paint.Style.STROKE);
        paintCirclePending.setAntiAlias(true);
        paintCirclePending.setColor(getResources().getColor(R.color.old_grey_d6));
    }

    private void setPaintCircleLost() {
        paintCircleLost = new Paint();
        paintCircleLost.setStyle(Paint.Style.FILL);
        paintCircleLost.setAntiAlias(true);
        paintCircleLost.setColor(getResources().getColor(R.color.red));
    }

    private void setPaintArc() {
        paintArc = new Paint();
        paintArc.setStrokeWidth(convertDpToPx(DEFAULT_STROKE_WIDTH));
        paintArc.setStyle(Paint.Style.STROKE);
        paintArc.setColor(getResources().getColor(R.color.old_grey_d6));
    }

    private void setPaintLine() {
        paintLine = new Paint();
        paintLine.setStrokeWidth(convertDpToPx(DEFAULT_STROKE_WIDTH));
        paintLine.setColor(getResources().getColor(R.color.lime_green));
    }

    private void setPaintCross() {
        paintCross = new Paint();
        paintCross.setStyle(Paint.Style.FILL);
        paintCross.setColor(getResources().getColor(R.color.white));
        paintCross.setStrokeWidth(convertDpToPx(1f));
        paintCross.setStrokeCap(Paint.Cap.ROUND);
    }

    public void setBet(Bet bet) {
        this.bet = bet;
        invalidate();
    }

    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        posX = convertDpToPx(START_POS_X);
        posY = convertDpToPx(START_POS_Y);
        lineStartPosX = posX + circleRadius;
        lineEndPosX = lineStartPosX + lineWidth;
        isGreyLine = false;

        numberOfEvents = bet.getEvents().size();
        for (int i = 0; i < numberOfEvents; i++) {
            Event event = bet.getEvents().get(i);
            String betStatus = event.getBetStatus();

            if (i < MAX_NUMBER_OF_EVENTS_PER_LINE) {
                drawBetCircle(canvas, betStatus);
                if (i < numberOfEvents - 1 && i < MAX_NUMBER_OF_EVENTS_PER_LINE - 1) {
                    drawConnectingLine(canvas, betStatus, i);
                    setPosX();
                }
            }

            if (i == MAX_NUMBER_OF_EVENTS_PER_LINE) {
                drawCorners(canvas);
            }

            if (i >= MAX_NUMBER_OF_EVENTS_PER_LINE) {
                drawBetCircle(canvas, betStatus);
                if (i != numberOfEvents - 1) {
                    drawConnectingLine(canvas, betStatus, i);
                    setPosX();
                }
            }
        }
    }

    private void drawBetCircle(Canvas canvas, String betStatus) {
        boolean isReset = false;
        if (betStatus.equals(PENDING)) {
            posX -= 1;
            isReset = true;
        }

        drawCircle(canvas, posX, posY, getCircleRadius(betStatus), getPaintCircle(betStatus));

        if (betStatus.equals(LOST)) {
            drawCross(canvas);
            isGreyLine = true;
        }

        if (isReset) {
            posX += 1;
        }
    }

    private void drawConnectingLine(Canvas canvas, String betStatus, int i) {
        if (betStatus.equals(PENDING) && isCrossDisplayed) {
            drawLineLong(canvas, i);
            return;
        }

        if (betStatus.equals(LOST) || isGreyLine) {
            drawLineShort(canvas, i);
        } else {
            drawLineLong(canvas, i);
        }
    }

    private void drawCross(Canvas canvas) {
        float crossPosX = (posX + convertDpToPx(0.666665f))  - (circleRadius / 2);
        float crossPosY = (posY + convertDpToPx(0.629f))  - (circleRadius / 2);
        drawCross(canvas, crossPosX, crossPosY, crossPosX, crossPosY, convertDpToPx(4f));
        isCrossDisplayed = true;
    }

    private void drawLineShort(Canvas canvas, int i) {
        if (i < numberOfEvents - 1) {
            final String nextBetStatus = getNextBetStatus(i);
            if (nextBetStatus.equals(LOST) && !isGreyLine) {
                lineEndPosX -= convertDpToPx(SMALL_GAP);
            } else {
                lineStartPosX += convertDpToPx(SMALL_GAP);
                lineEndPosX -= convertDpToPx(SMALL_GAP);
            }

            setPaintLineColour();
            drawLine(canvas, lineStartPosX, lineEndPosX, posY, paintLine);

            if (nextBetStatus.equals(LOST)) {
                lineEndPosX += convertDpToPx(SMALL_GAP);
            } else {
                lineStartPosX -= convertDpToPx(SMALL_GAP);
                lineEndPosX += convertDpToPx(SMALL_GAP);
            }
        }
    }

    private void drawLineLong(Canvas canvas, int i) {
        boolean isReset = false;
        final String nextBetStatus = getNextBetStatus(i);
        if (nextBetStatus.equals(LOST)) {
            lineEndPosX -= convertDpToPx(SMALL_GAP);
            isReset = true;
        }

        setPaintLineColour();
        drawLine(canvas, lineStartPosX, lineEndPosX, posY, paintLine);

        if (isReset) {
            lineEndPosX += convertDpToPx(SMALL_GAP);
        }
    }

    private void setPosX() {
        posX = lineEndPosX + circleRadius;
        lineStartPosX = posX + circleRadius;
        lineEndPosX = lineStartPosX + lineWidth;
    }

    private void drawCorners(Canvas canvas) {
        float startPosX = posX + circleRadius;
        float endPosX = startPosX + convertDpToPx(6);
        float rectHeight = circleRadius * EXTRA_HEIGHT;
        float arcPosX;
        float arcPosY;

        setPaintLineColour();

        if (isGreyLine) {
            startPosX += convertDpToPx(SMALL_GAP);
        }
        drawLine(canvas, startPosX,  endPosX, posY, paintLine);

        //draw right corner
        arcPosX = endPosX - convertDpToPx(7);
        rect.set(arcPosX, posY, arcPosX + rectHeight, posY + rectHeight);
        canvas.drawArc(rect, -START_ANGLE, SWEEP_ANGLE, false, getPaintArc(isGreyLine));

        drawLine(canvas, convertDpToPx(START_POS_X - 9), endPosX, posY + rectHeight, paintLine);

        //draw left corner
        arcPosX = convertDpToPx(START_POS_X - 13);
        arcPosY = posY + rectHeight;
        rect.set(arcPosX, arcPosY, arcPosX + rectHeight, arcPosY + rectHeight);
        canvas.drawArc(rect, START_ANGLE, SWEEP_ANGLE, false, getPaintArc(isGreyLine));

        startPosX = convertDpToPx(START_POS_X - 9);
        endPosX = startPosX + (isGreyLine ? convertDpToPx(3) : convertDpToPx(5));
        drawLine(canvas, startPosX, endPosX, arcPosY + rectHeight, paintLine);

        posX = convertDpToPx(START_POS_X);
        posY = arcPosY + (circleRadius * EXTRA_HEIGHT);
        lineStartPosX = posX + circleRadius;
        lineEndPosX = lineStartPosX + lineWidth;
    }

    private String getNextBetStatus(int i) {
        String nextBetStatus = "";
        if (i < numberOfEvents - 1) {
            Event nextEvent = bet.getEvents().get(i + 1);
            nextBetStatus = nextEvent.getBetStatus();
        }
        return nextBetStatus;
    }

    private Paint getPaintCircle(String betStatus) {
        if (betStatus.equals(WON)) {
            return paintCircleWon;
        } else if (betStatus.equals(LOST)) {
            return paintCircleLost;
        }

        return paintCirclePending;
    }

    private float getCircleRadius(String betStatus) {
        if (betStatus.equals(PENDING)) {
            return circleRadius - 2;
        }
        return circleRadius;
    }

    private Paint getPaintArc(boolean isGreyLine) {
        paintArc.setColor(getLineColour(isGreyLine ? PENDING : WON));
        return paintArc;
    }

    private int getLineColour(String betStatus) {
        return betStatus.equals(WON) ? getColour(R.color.lime_green) :
                getColour(R.color.old_grey_d6);
    }

    private void setPaintLineColour() {
        paintLine.setColor(isGreyLine ? getColour(R.color.old_grey_d6) :
                getColour(R.color.lime_green));
    }

    private void drawCircle(Canvas canvas, float posX, float posY, float radius, Paint paintCircle) {
        canvas.drawCircle(posX, posY, radius, paintCircle);
    }

    private void drawLine(Canvas canvas, float startPosX, float endPosX, float posY, Paint paintLine) {
        canvas.drawLine(startPosX, posY, endPosX, posY, paintLine);
    }

    private void drawCross(Canvas canvas, float startPosX, float startPosY, float endPosX, float endPosY, float width) {
        canvas.drawLine(startPosX, startPosY, endPosX + width, endPosY + width, paintCross);
        canvas.drawLine(startPosX, startPosY + width, endPosX + width, endPosY, paintCross);
    }

    private float convertDpToPx(float dp) {
        return ScreenUtil.convertDipToPix(context, dp);
    }

    private int getColour(int color) {
        return getResources().getColor(color);
    }

    private void printList(List<Event> eventList) {
        Log.d(TAG, "*********** Print Events ************");
        for (Event e : eventList) {
            Log.d(TAG, "event title : " + e.getEventTitle());
        }
    }
}
