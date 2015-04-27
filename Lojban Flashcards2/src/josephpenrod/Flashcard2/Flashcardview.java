package josephpenrod.Flashcard2;



import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.util.Log;




public class Flashcardview extends View 
{

	boolean front = true;//true means the front of the card, false means the back of the card
	private Paint linepaint;
	private Paint textpaint;
	private Paint rectpaint;
	private String Frontface;
	private String backface;
	private int textHeight;
	private float initialX = 0;
	private float initialY = 0;
	private float deltaX = 0;
	private float deltaY = 0;
	private Canvas canvas;

	public Flashcardview(Context context) 
	{
		super(context);
		initFlashcardview();
	}
	public Flashcardview(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		initFlashcardview();
	}
	public Flashcardview(Context context, AttributeSet attrs, int defaultstyle) 
	{
		super(context, attrs, defaultstyle);
		initFlashcardview();
	}
	
    protected void initFlashcardview()
    {
    	setFocusable(true);
    	linepaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    	linepaint.setStrokeWidth(1);
    	linepaint.setStyle(Paint.Style.FILL_AND_STROKE);
    	linepaint.setColor(R.color.line_color);
    	
    	
    	
    	
    	Resources r = this.getResources();
    	Frontface = r.getString(R.string.Book);
    	backface = r.getString(R.string.Cukta);
    	
    	textpaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    	textpaint.setColor(R.color.text_color);
    	textpaint.setStrokeWidth(1);
    	textpaint.setStyle(Paint.Style.FILL_AND_STROKE);
    	
    	rectpaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    	rectpaint.setColor(R.color.rect_color);
    	rectpaint.setStrokeWidth(1);
    	rectpaint.setStyle(Paint.Style.FILL_AND_STROKE);
    	
    }
    public void setside(boolean side)
    {
    	front = side;
    	
    }
    public boolean getside()
    {
    	
    	return front;
    }
    public String getFront()
    {
    	
    	return Frontface;
    }
    public void setFront(String string)
    {
    	Frontface = string;
    	
    	
    }
    public String getBack()
    {
    	return backface;
    	
    }
    public void Writecard(Pair pair)
    {
    	Frontface = pair.getpFront();
    	backface = pair.getpBack();
    	this.invalidate();
    	
    }
    public void setBack(String string)
    {
    	backface = string;
    }
    public void flip()
    {
    	if(front == true)
    	{
    		front = false;
    		Log.v("flip", "front is now false");
    		
    	}	
    	else
    	{
    		front = true;
    		Log.v("flip","front is now true");
    	}
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
    	
    	int measuredWidth = measure(widthMeasureSpec);
    	int measuredHeight = measure(heightMeasureSpec);
    	
    	int q = Math.min(measuredWidth, measuredHeight);
    	setMeasuredDimension(q,q);
    }
    private int measure(int measureSpec)
    {
    	int result = 0;
    	int specMode = MeasureSpec.getMode(measureSpec);
    	int specSize = MeasureSpec.getSize(measureSpec);
    	
    	if(specMode == MeasureSpec.UNSPECIFIED)
    	{
              result = 200;
        }
    	else
    	{
    		result = specSize;
    	}
    	return result;
    }
    @Override
    protected void onDraw(Canvas canvas)
    {
    	Log.v("draw", "Ondraw just got called");
    	int cent_x = getMeasuredWidth()/2;
    	int cent_y = getMeasuredHeight()/2;
    	super.onDraw(canvas);
    	rectpaint.setColor(Color.WHITE);
  
		canvas.drawRect(0, 0, getWidth(), getHeight(), rectpaint);
		Log.v("draw", "Rectangle just got drawn");
		if(front == true)
		{
		canvas.drawText(Frontface, cent_x, cent_y, textpaint);
		Log.v("draw", "Frontface just got drawn");
		}
		else
		{ 
			canvas.drawText(backface, cent_x,cent_y, textpaint);
			Log.v("draw", "backface just got drawn");
		}
		for(int q = 1; q<=10; q++)
		{
			canvas.drawLine(0, 40*q, getMeasuredWidth(), 40*q, linepaint);
			
		}
    }
 /*   @Override
	public boolean onTouchEvent(MotionEvent event)
	{
		//This avoids touchscreen events flooding the main thread
		synchronized (event)
		{
			try
			{
				//Waits 16ms.
				event.wait(16);

				//when user touches the screen
				if(event.getAction() == MotionEvent.ACTION_DOWN)
				{
					//reset deltaX and deltaY
					deltaX = deltaY = 0;

					//get initial positions
					initialX = event.getRawX();
					initialY = event.getRawY();
				}

				//when screen is released
				if(event.getAction() == MotionEvent.ACTION_UP)
				{
					deltaX = event.getRawX() - initialX;
					deltaY = event.getRawY() - initialY;

					//swiped up
					if(deltaY<0)
					{
						flip();
					}
					else //swiped down
					{
						flip();
					}

					//swiped right
					if(deltaX>0)
					{
						flip();//left
					}
					else
					{
						flip();//left
					}

					return super.onTouchEvent(event);
				}
			}

			catch (InterruptedException e)
			{
				return super.onTouchEvent(event);
			}
		}
		return super.onTouchEvent(event);
	}*/
}
    
   
    	

