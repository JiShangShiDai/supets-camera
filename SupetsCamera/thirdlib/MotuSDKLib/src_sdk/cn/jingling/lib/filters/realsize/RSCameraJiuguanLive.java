package cn.jingling.lib.filters.realsize;

import android.content.Context;
import android.graphics.Bitmap;
import cn.jingling.lib.filters.CMTProcessor;
import cn.jingling.lib.filters.Curve;
import cn.jingling.lib.filters.Layer;

public class RSCameraJiuguanLive extends RSLineFilter {

	private Curve mCurve;
	private Bitmap mLayer;
	
	@Override
	public boolean apply(Context cx, String inPath, String outPath, int[] args) {
		mCurve = new Curve(cx, "curves/live_jiuguan.dat");
		mLayer = Layer.getLayerImage(cx, "layers/live_jiuguan",
				Layer.Type.NORMAL);
		return super.apply(cx, inPath, outPath, args);
	}
	
	@Override
	protected void applyLine(Context cx, int[] pixels, int line, int height) {
		// TODO Auto-generated method stub
		int w = pixels.length;
		int[] layerPixels;
		
		layerPixels = getLayerPixels(mLayer, line, height);
		CMTProcessor.rsOverlayAlphaEffect(pixels, layerPixels, w, 1, mLayer.getWidth(), 1, 65);
		//
		CMTProcessor.curveEffect(pixels, mCurve.getCurveRed(), mCurve.getCurveGreen(),
				mCurve.getCurveBlue(), w, 1);
	}

	@Override
	protected void releaseLayers() {
		// TODO Auto-generated method stub
		mLayer.recycle();	
	}

}
