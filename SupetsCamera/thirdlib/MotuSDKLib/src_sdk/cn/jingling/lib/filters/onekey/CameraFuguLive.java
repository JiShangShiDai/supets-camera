package cn.jingling.lib.filters.onekey;

import android.content.Context;
import android.graphics.Bitmap;
import cn.jingling.lib.filters.CMTProcessor;
import cn.jingling.lib.filters.Curve;
import cn.jingling.lib.filters.Layer;
import cn.jingling.lib.filters.OneKeyFilter;

public class CameraFuguLive extends OneKeyFilter{

	@Override
	public Bitmap apply(Context cx, Bitmap bm) {
		this.statisticEvent();
		int w = bm.getWidth();
		int h = bm.getHeight();
		int[] pixels = new int[w * h];
		bm.getPixels(pixels, 0, w, 0, 0, w, h);
		Curve c = new Curve(cx, "curves/live_fugu.dat");
		CMTProcessor.curveEffect(pixels, c.getCurveRed(), c.getCurveGreen(), c.getCurveBlue(), w, h);
		CMTProcessor.multiplyEffect(pixels, Layer.getLayerPixels(cx, "layers/live_fugu", w, h, Layer.Type.NORMAL), w, h);
		bm.setPixels(pixels, 0, w, 0, 0, w, h);
		pixels = null;
		return bm;
	}

}
