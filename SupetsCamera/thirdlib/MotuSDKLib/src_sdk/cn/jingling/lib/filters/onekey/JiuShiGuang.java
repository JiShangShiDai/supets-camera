package cn.jingling.lib.filters.onekey;

import android.content.Context;
import android.graphics.Bitmap;
import cn.jingling.lib.filters.CMTProcessor;
import cn.jingling.lib.filters.Curve;
import cn.jingling.lib.filters.ImageProcessUtils;
import cn.jingling.lib.filters.Layer;
import cn.jingling.lib.filters.OneKeyFilter;

public class JiuShiGuang extends OneKeyFilter {

	@Override
	public Bitmap apply(Context cx, Bitmap bm) {
		this.statisticEvent();
		int w = bm.getWidth();
		int h = bm.getHeight();
		int[] pixels = new int[w * h];
		bm.getPixels(pixels, 0, w, 0, 0, w, h);
		
//		CMTProcessor.saturation(pixels, w, h, (float)-0.45);
		Curve c = new Curve(cx, "curves/fleeting16.dat");
		CMTProcessor.curveEffect(pixels, c.getCurveRed(), c.getCurveGreen(), c.getCurveBlue(), w, h);
		CMTProcessor.linearBurn(pixels, Layer.getLayerPixels(cx, "layers/fleeting16", w, h, Layer.Type.ROTATABLE), w, h, 100);
		pixels = ImageProcessUtils.saturationPs(pixels, w, h, -35);
		
		bm.setPixels(pixels, 0, w, 0, 0, w, h);
		pixels = null;
		return bm;
	}

}
