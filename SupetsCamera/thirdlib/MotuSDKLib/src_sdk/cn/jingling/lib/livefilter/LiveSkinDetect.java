package cn.jingling.lib.livefilter;

import android.content.Context;
import android.graphics.Point;
import android.opengl.GLES20;
import cn.jingling.lib.livefilter.BufferHelper.FrameBufferInfo;
import cn.jingling.lib.livefilter.ShaderHelper.ShaderInfo;
import cn.jingling.lib.utils.MathUtils;

public class LiveSkinDetect extends LiveOp {
	private ShaderInfo mAlphaMeanShader, mShader, mOverlayShader, mSmoothBlurHorizontalShader,
			mSmoothBlurVerticalShader, mSmoothExtractionShader,
			mSmoothTemplateShader, mSmoothApplyShader;
	private int mTextureSmoothCurveHandle, mTextureHighlightCurveHandle;
	private FrameBufferInfo mFrameBufferA, mFrameBufferB, mFrameBufferC;

	@Override
	public void glSetup(Context cx) {
		mAlphaMeanShader = ShaderHelper.glGenerateShader(cx,
				"kirsch_vertex_shader", "alpha_meansmooth_fragment_shader", "aPosition",
				"uMVPMatrix", "texelWidth", "texelHeight", "uTexture");
		
		mOverlayShader = ShaderHelper.glGenerateShader(cx, "vertex_shader",
				"skin_overlay_fragment_shader", "aPosition", "uMVPMatrix",
				"uTexture", "uTextureLayer", "uTextureWeight");
		mShader = ShaderHelper.glGenerateShader(cx, "vertex_shader",
				"skin_detect_fragment_shader", "aPosition", "uMVPMatrix",
				"uTexture");
		mSmoothBlurHorizontalShader = ShaderHelper.glGenerateShader(cx,
				"smooth_blur_horizontal_vertex_shader",
				"smooth_blur_fragment_shader", "aPosition",
				"inputImageTexture", "uMVPMatrix", "texelWidthOffset",
				"texelHeightOffset");
		mSmoothBlurVerticalShader = ShaderHelper.glGenerateShader(cx,
				"smooth_blur_vertical_vertex_shader",
				"smooth_blur_fragment_shader", "aPosition",
				"inputImageTexture", "uMVPMatrix", "texelWidthOffset",
				"texelHeightOffset");
		mSmoothExtractionShader = ShaderHelper.glGenerateShader(cx,
				"vertex_shader", "smooth_extract_selection_fragment_shader",
				"aPosition", "uTexture", "uTextureBlur", "uMVPMatrix");
		mSmoothTemplateShader = ShaderHelper.glGenerateShader(cx,
				"vertex_shader", "smooth_template_fragment_shader",
				"aPosition", "uMVPMatrix", "uTexture", "uTextureCurve");
		mSmoothApplyShader = ShaderHelper.glGenerateShader(cx, "vertex_shader",
				"smooth_apply_fragment_shader", "aPosition", "uMVPMatrix",
				"uTexture", "uTextureTemplate", "uTextureCurve");
		mTextureSmoothCurveHandle = TextureHelper.loadCurveTexture(cx,
				"curves/skin_smooth.dat");
		mTextureHighlightCurveHandle = TextureHelper.loadCurveTexture(cx,
				"curves/highlight.dat");
	}

	@Override
	public void glUpdate(Context cx, Point fboImageSize) {
		// TODO Auto-generated method stub
		super.glUpdate(cx, fboImageSize);
		mFrameBufferA = BufferHelper.glGenerateFrameBuffer(fboImageSize.x,
				fboImageSize.y);
		mFrameBufferB = BufferHelper.glGenerateFrameBuffer(fboImageSize.x,
				fboImageSize.y);
		mFrameBufferC = BufferHelper.glGenerateFrameBuffer(fboImageSize.x,
				fboImageSize.y);
	}

	@Override
	public void glDraw(float[] mvpMatrix, int vboHandle, FrameBufferInfo fbi,
			int posDataSize, int textureHandle) {
		// TODO Auto-generated method stub
		BufferHelper.glBindFrameBuffer(mFrameBufferA);
		BufferHelper.glBindFrameBuffer(mFrameBufferB);

		glDrawSmoothBlur(mSmoothBlurHorizontalShader, mvpMatrix, vboHandle,
				mFrameBufferA, posDataSize, textureHandle);
		glDrawSmoothBlur(mSmoothBlurVerticalShader, mvpMatrix, vboHandle,
				mFrameBufferB, posDataSize, mFrameBufferA.textureHandle);
		glDrawSmoothBlur(mSmoothBlurHorizontalShader, mvpMatrix, vboHandle,
				mFrameBufferA, posDataSize, mFrameBufferB.textureHandle);
		glDrawSmoothBlur(mSmoothBlurVerticalShader, mvpMatrix, vboHandle,
				mFrameBufferB, posDataSize, mFrameBufferA.textureHandle);
		glDrawSmoothBlur(mSmoothBlurHorizontalShader, mvpMatrix, vboHandle,
				mFrameBufferA, posDataSize, mFrameBufferB.textureHandle);
		glDrawSmoothBlur(mSmoothBlurVerticalShader, mvpMatrix, vboHandle,
				mFrameBufferB, posDataSize, mFrameBufferA.textureHandle);	
		
//		glDrawExtraction(mSmoothExtractionShader, mvpMatrix, vboHandle,
//		mFrameBufferA, posDataSize, textureHandle,
//		mFrameBufferB.textureHandle);
//glDrawTemplate(mSmoothTemplateShader, mvpMatrix, vboHandle,
//		mFrameBufferB, posDataSize, mFrameBufferA.textureHandle,
//		mTextureHighlightCurveHandle);
//glDrawSmoothApply(mSmoothApplyShader, mvpMatrix, vboHandle, fbi,
//		posDataSize, textureHandle, mFrameBufferB.textureHandle,
//		mTextureSmoothCurveHandle);		
		
		glSkinDetect(mShader, mvpMatrix, vboHandle, mFrameBufferA, posDataSize,
				textureHandle);		
		glAlphaMean(mAlphaMeanShader, mvpMatrix, vboHandle, mFrameBufferC, posDataSize,
				mFrameBufferA.textureHandle);			
		glSkinOverlay(mOverlayShader, mvpMatrix, vboHandle, mFrameBufferA, posDataSize,
				textureHandle, mFrameBufferB.textureHandle,
				mFrameBufferC.textureHandle);	
		
	
		glDrawExtraction(mSmoothExtractionShader, mvpMatrix, vboHandle,
				mFrameBufferB, posDataSize, textureHandle,
				mFrameBufferA.textureHandle);
		glDrawTemplate(mSmoothTemplateShader, mvpMatrix, vboHandle,
				mFrameBufferA, posDataSize, mFrameBufferB.textureHandle,
				mTextureHighlightCurveHandle);
		glDrawSmoothApply(mSmoothApplyShader, mvpMatrix, vboHandle, fbi,
				posDataSize, textureHandle, mFrameBufferA.textureHandle,
				mTextureSmoothCurveHandle);	
		

	}

	@Override
	public void glRelease() {
		// TODO Auto-generated method stub
		BufferHelper.glReleaseFrameBuffer(mFrameBufferA);
		BufferHelper.glReleaseFrameBuffer(mFrameBufferB);
		BufferHelper.glReleaseFrameBuffer(mFrameBufferC);
		mFrameBufferA = null;
		mFrameBufferB = null;
		mFrameBufferC = null;
	}
	private void glAlphaMean(ShaderInfo shader, float[] mvpMatrix,
			int vboHandle, FrameBufferInfo fbi, int posDataSize,
			int textureHandle) {
		GLES20.glUseProgram(shader.program);
		GLES20.glViewport(0, 0, mImageSize.x, mImageSize.y);
		GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, fbi == null ? 0
				: fbi.frameBufferHandle);
		GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle);
		GLES20.glUniform1i(shader.uniforms.get("uTexture"), 0);
		GLES20.glUniform1f(shader.uniforms.get("texelWidth"),
				1f / MathUtils.nextPowerOfTwo(mImageSize.x));
		GLES20.glUniform1f(shader.uniforms.get("texelHeight"),
				1f / MathUtils.nextPowerOfTwo(mImageSize.y));

		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vboHandle);
		GLES20.glVertexAttribPointer(shader.attribute, posDataSize,
				GLES20.GL_FLOAT, false, 0, 0);
		GLES20.glEnableVertexAttribArray(shader.attribute);

		GLES20.glUniformMatrix4fv(shader.uniforms.get("uMVPMatrix"), 1, false,
				mvpMatrix, 0);
		GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 6);
	}
	
	private void glSkinOverlay(ShaderInfo shader, float[] mvpMatrix,
			int vboHandle, FrameBufferInfo fbi, int posDataSize,
			int textureHandle, int textureTemplateHandle, int textureCurveHandle) {
		GLES20.glUseProgram(shader.program);
		GLES20.glViewport(0, 0, mImageSize.x, mImageSize.y);
		GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, fbi == null ? 0
				: fbi.frameBufferHandle);
		GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle);
		GLES20.glUniform1i(shader.uniforms.get("uTexture"), 0);
		GLES20.glActiveTexture(GLES20.GL_TEXTURE1);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureTemplateHandle);
		GLES20.glUniform1i(shader.uniforms.get("uTextureLayer"), 1);
		GLES20.glActiveTexture(GLES20.GL_TEXTURE2);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureCurveHandle);
		GLES20.glUniform1i(shader.uniforms.get("uTextureWeight"), 2);
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vboHandle);
		GLES20.glVertexAttribPointer(shader.attribute, posDataSize,
				GLES20.GL_FLOAT, false, 0, 0);
		GLES20.glEnableVertexAttribArray(shader.attribute);
		GLES20.glUniformMatrix4fv(shader.uniforms.get("uMVPMatrix"), 1, false,
				mvpMatrix, 0);
		GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 6);
	}

	private void glSkinDetect(ShaderInfo shader, float[] mvpMatrix,
			int vboHandle, FrameBufferInfo fbi, int posDataSize,
			int textureHandle) {
		// TODO Auto-generated method stub
		GLES20.glUseProgram(shader.program);
		GLES20.glViewport(0, 0, mImageSize.x, mImageSize.y);
		GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, fbi == null ? 0
				: fbi.frameBufferHandle);
		GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle);
		GLES20.glUniform1i(shader.uniforms.get("uTexture"), 0);
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vboHandle);
		GLES20.glVertexAttribPointer(shader.attribute, posDataSize,
				GLES20.GL_FLOAT, false, 0, 0);
		GLES20.glEnableVertexAttribArray(shader.attribute);
		GLES20.glUniformMatrix4fv(shader.uniforms.get("uMVPMatrix"), 1, false,
				mvpMatrix, 0);
		GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 6);

	}

	private void glDrawSmoothBlur(ShaderInfo shader, float[] mvpMatrix,
			int vboHandle, FrameBufferInfo fbi, int posDataSize,
			int textureHandle) {
		GLES20.glUseProgram(shader.program);
		GLES20.glViewport(0, 0, mImageSize.x, mImageSize.y);
		GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, fbi == null ? 0
				: fbi.frameBufferHandle);
		GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle);
		GLES20.glUniform1i(shader.uniforms.get("inputImageTexture"), 0);
		GLES20.glUniform1f(shader.uniforms.get("texelWidthOffset"),
				1f / mImageSize.x);
		GLES20.glUniform1f(shader.uniforms.get("texelHeightOffset"),
				1f / mImageSize.y);

		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vboHandle);
		GLES20.glVertexAttribPointer(shader.attribute, posDataSize,
				GLES20.GL_FLOAT, false, 0, 0);
		GLES20.glEnableVertexAttribArray(shader.attribute);
		GLES20.glUniformMatrix4fv(shader.uniforms.get("uMVPMatrix"), 1, false,
				mvpMatrix, 0);
		GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 6);
	}

	private void glDrawExtraction(ShaderInfo shader, float[] mvpMatrix,
			int vboHandle, FrameBufferInfo fbi, int posDataSize,
			int textureHandle, int textureBlurHandle) {
		GLES20.glUseProgram(shader.program);
		GLES20.glViewport(0, 0, mImageSize.x, mImageSize.y);
		GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, fbi == null ? 0
				: fbi.frameBufferHandle);
		GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle);
		GLES20.glUniform1i(shader.uniforms.get("uTexture"), 0);
		GLES20.glActiveTexture(GLES20.GL_TEXTURE1);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureBlurHandle);
		GLES20.glUniform1i(shader.uniforms.get("uTextureBlur"), 1);
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vboHandle);
		GLES20.glVertexAttribPointer(shader.attribute, posDataSize,
				GLES20.GL_FLOAT, false, 0, 0);
		GLES20.glEnableVertexAttribArray(shader.attribute);
		GLES20.glUniformMatrix4fv(shader.uniforms.get("uMVPMatrix"), 1, false,
				mvpMatrix, 0);
		GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 6);
	}

	private void glDrawTemplate(ShaderInfo shader, float[] mvpMatrix,
			int vboHandle, FrameBufferInfo fbi, int posDataSize,
			int textureHandle, int textureCurveHandle) {
		GLES20.glUseProgram(shader.program);
		GLES20.glViewport(0, 0, mImageSize.x, mImageSize.y);
		GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, fbi == null ? 0
				: fbi.frameBufferHandle);
		GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle);
		GLES20.glUniform1i(shader.uniforms.get("uTexture"), 0);
		GLES20.glActiveTexture(GLES20.GL_TEXTURE1);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureCurveHandle);
		GLES20.glUniform1i(shader.uniforms.get("uTextureCurve"), 1);
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vboHandle);
		GLES20.glVertexAttribPointer(shader.attribute, posDataSize,
				GLES20.GL_FLOAT, false, 0, 0);
		GLES20.glEnableVertexAttribArray(shader.attribute);
		GLES20.glUniformMatrix4fv(shader.uniforms.get("uMVPMatrix"), 1, false,
				mvpMatrix, 0);
		GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 6);
	}

	private void glDrawSmoothApply(ShaderInfo shader, float[] mvpMatrix,
			int vboHandle, FrameBufferInfo fbi, int posDataSize,
			int textureHandle, int textureTemplateHandle, int textureCurveHandle) {
		GLES20.glUseProgram(shader.program);
		GLES20.glViewport(0, 0, mImageSize.x, mImageSize.y);
		GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, fbi == null ? 0
				: fbi.frameBufferHandle);
		GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle);
		GLES20.glUniform1i(shader.uniforms.get("uTexture"), 0);
		GLES20.glActiveTexture(GLES20.GL_TEXTURE1);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureTemplateHandle);
		GLES20.glUniform1i(shader.uniforms.get("uTextureTemplate"), 1);
		GLES20.glActiveTexture(GLES20.GL_TEXTURE2);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureCurveHandle);
		GLES20.glUniform1i(shader.uniforms.get("uTextureCurve"), 2);
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vboHandle);
		GLES20.glVertexAttribPointer(shader.attribute, posDataSize,
				GLES20.GL_FLOAT, false, 0, 0);
		GLES20.glEnableVertexAttribArray(shader.attribute);
		GLES20.glUniformMatrix4fv(shader.uniforms.get("uMVPMatrix"), 1, false,
				mvpMatrix, 0);
		GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 6);
	}
}
