package cn.jingling.lib.filters.realsize;

import android.content.Context;
import android.graphics.Bitmap;
import cn.jingling.lib.filters.CMTProcessor;
import cn.jingling.lib.filters.Curve;
import cn.jingling.lib.filters.Layer;

public class RSCameraSutroLive extends RSLineFilter {

	private Curve mCurve;
	private Bitmap mLayer;
	
	@Override
	public boolean apply(Context cx, String inPath, String outPath, int[] args) {
		mCurve = new Curve(cx, "curves/live_sutro.dat");
		mLayer = Layer.getLayerImage(cx, "layers/live_sutro", Layer.Type.NORMAL);
		return super.apply(cx, inPath, outPath, args);
	}
	
	@Override
	protected void applyLine(Context cx, int[] pixels, int line, int height) {
		// TODO Auto-generated method stub
		int w = pixels.length;
		int[] layerPixels;
		
		CMTProcessor.curveEffect(pixels, mCurve.getCurveRed(), mCurve.getCurveGreen(),
				mCurve.getCurveBlue(), w, 1);
		//
		layerPixels = getLayerPixels(mLayer, line, height);
		CMTProcessor.rsLinearBurn(pixels, layerPixels, w, 1, mLayer.getWidth(), 1, 100);
	}

	@Override
	protected void releaseLayers() {
		mLayer.recycle();	
	}

}
