package cn.jingling.lib.filters.onekey;

import android.content.Context;
import android.graphics.Bitmap;
import cn.jingling.lib.filters.OneKeyFilter;
import cn.jingling.lib.filters.SmoothSkinProcessor;

public class CLAHERGB3 extends OneKeyFilter {

	@Override
	public Bitmap apply(Context cx, Bitmap bm) {
		// TODO Auto-generated method stub
		int w = bm.getWidth();
		int h = bm.getHeight();
		int[] pixels = new int[w * h];
		bm.getPixels(pixels, 0, w, 0, 0, w, h);
//		int radius = 50;
//		int thres = 0;
//		int amount = 50;
//		SmoothSkinProcessor.usmProcess(pixels, w, h, 50, 0, 50);
  
		SmoothSkinProcessor.CLAHERGB3(pixels, w, h, 4,20);
 
		bm.setPixels(pixels, 0, w, 0, 0, w, h);
		return bm;
	}

}
