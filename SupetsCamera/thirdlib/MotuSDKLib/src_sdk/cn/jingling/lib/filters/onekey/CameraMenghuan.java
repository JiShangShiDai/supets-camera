package cn.jingling.lib.filters.onekey;

import android.content.Context;
import android.graphics.Bitmap;
import cn.jingling.lib.filters.CMTProcessor;
import cn.jingling.lib.filters.Curve;
import cn.jingling.lib.filters.ImageProcessUtils;
import cn.jingling.lib.filters.OneKeyFilter;

public class CameraMenghuan extends OneKeyFilter {

	@Override
	public Bitmap apply(Context cx, Bitmap bm) {
		this.statisticEvent();
		ImageProcessUtils.saturationPs(bm, -15);
		int w = bm.getWidth();
		int h = bm.getHeight();
		int[] pixels = new int[w * h];
		bm.getPixels(pixels, 0, w, 0, 0, w, h);
		Curve c = new Curve(cx, "curves/camera_menghuan.dat");
		CMTProcessor.fastAverageBlurWithThresholdAndWeight(pixels, w, h, 5, 10, 180);
		CMTProcessor.fastAverageBlurWithThresholdAndWeight(pixels, w, h, Math.max(w, h) / 66, 255, 120);
		CMTProcessor.brightEffect(pixels, w, h, 65);
		CMTProcessor.curveEffect(pixels, c.getCurveRed(), c.getCurveGreen(), c.getCurveBlue(), w, h);
		bm.setPixels(pixels, 0, w, 0, 0, w, h);
		pixels = null;
		return bm;
	}

}