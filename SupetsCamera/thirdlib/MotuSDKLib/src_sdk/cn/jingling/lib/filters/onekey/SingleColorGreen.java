package cn.jingling.lib.filters.onekey;

import cn.jingling.lib.filters.CMTProcessor;

public class SingleColorGreen extends SingleColor{
	public SingleColorGreen() {
		// TODO Auto-generated constructor stub
		super();
	}

	@Override
	public void singleColorEffect(int[] pixels, int w, int h, float[] matrix) {
		this.statisticEvent();
		CMTProcessor.singleColorEffect(pixels, w, h, matrix, 0.0f,1.0f,0.0f,0.45f,0.55f);
	}

}
