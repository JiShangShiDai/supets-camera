#include <jni.h>
#include <GLES2/gl2.h>

#ifndef _Included_cn_jingling_lib_livefilter_Opengl20JniLib
#define _Included_cn_jingling_lib_livefilter_Opengl20JniLib
#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT jstring JNICALL Java_cn_jingling_lib_livefilter_Opengl20JniLib_getEmptyFragmentShader(JNIEnv *env,jclass jclazz);
JNIEXPORT jstring JNICALL Java_cn_jingling_lib_livefilter_Opengl20JniLib_getSaturationFragmentShader(JNIEnv *env,jclass jclazz);
JNIEXPORT jstring JNICALL Java_cn_jingling_lib_livefilter_Opengl20JniLib_getCurveFragmentShader(JNIEnv *env,jclass jclazz);
JNIEXPORT jstring JNICALL Java_cn_jingling_lib_livefilter_Opengl20JniLib_getLinearburnFragmentShader(JNIEnv *env,jclass jclazz);
JNIEXPORT jstring JNICALL Java_cn_jingling_lib_livefilter_Opengl20JniLib_getMultiplyFragmentShader(JNIEnv *env,jclass jclazz);
JNIEXPORT jstring JNICALL Java_cn_jingling_lib_livefilter_Opengl20JniLib_getOverlayFragmentShader(JNIEnv *env,jclass jclazz);
JNIEXPORT jstring JNICALL Java_cn_jingling_lib_livefilter_Opengl20JniLib_getScreenFragmentShader(JNIEnv *env,jclass jclazz);
JNIEXPORT jstring JNICALL Java_cn_jingling_lib_livefilter_Opengl20JniLib_getDarkenFragmentShader(JNIEnv *env,jclass jclazz);
JNIEXPORT jstring JNICALL Java_cn_jingling_lib_livefilter_Opengl20JniLib_getCoverageFragmentShader(JNIEnv *env,jclass jclazz);

JNIEXPORT jstring JNICALL Java_cn_jingling_lib_livefilter_Opengl20JniLib_getOriginalShader(JNIEnv *env,jclass jclazz);
JNIEXPORT jstring JNICALL Java_cn_jingling_lib_livefilter_Opengl20JniLib_getSmoothApplyFragmentShader(JNIEnv *env,jclass jclazz);
JNIEXPORT jstring JNICALL Java_cn_jingling_lib_livefilter_Opengl20JniLib_getSmoothBlurFragmentShader(JNIEnv *env,jclass jclazz);

JNIEXPORT jstring JNICALL Java_cn_jingling_lib_livefilter_Opengl20JniLib_getSmoothBlurHorizontalVertexShader(JNIEnv *env,jclass jclazz);
JNIEXPORT jstring JNICALL Java_cn_jingling_lib_livefilter_Opengl20JniLib_getSmoothBlurVerticalVertexShader(JNIEnv *env,jclass jclazz);
JNIEXPORT jstring JNICALL Java_cn_jingling_lib_livefilter_Opengl20JniLib_getSmoothExtractSelectionFragmentShader(JNIEnv *env,jclass jclazz);

JNIEXPORT jstring JNICALL Java_cn_jingling_lib_livefilter_Opengl20JniLib_getSmoothTemplateFragmentShader(JNIEnv *env,jclass jclazz);
JNIEXPORT jstring JNICALL Java_cn_jingling_lib_livefilter_Opengl20JniLib_getVertexShader(JNIEnv *env,jclass jclazz);
JNIEXPORT jstring JNICALL Java_cn_jingling_lib_livefilter_Opengl20JniLib_getYuvFragmentShader(JNIEnv *env,jclass jclazz);
JNIEXPORT jstring JNICALL Java_cn_jingling_lib_livefilter_Opengl20JniLib_getRgbFragmentShader(JNIEnv *env,jclass jclazz);
JNIEXPORT jstring JNICALL Java_cn_jingling_lib_livefilter_Opengl20JniLib_getSceneEnhanceFragmentShader(JNIEnv *env,jclass jclazz);
JNIEXPORT jstring JNICALL Java_cn_jingling_lib_livefilter_Opengl20JniLib_getHighLightFragmentShader(JNIEnv *env,jclass jclazz);
JNIEXPORT jstring JNICALL Java_cn_jingling_lib_livefilter_Opengl20JniLib_getKirschFragmentShader(JNIEnv *env,jclass jclazz);
JNIEXPORT jstring JNICALL Java_cn_jingling_lib_livefilter_Opengl20JniLib_getKirschVertexShader(JNIEnv *env,jclass jclazz);
JNIEXPORT jstring JNICALL Java_cn_jingling_lib_livefilter_Opengl20JniLib_getKirsch1FragmentShader(JNIEnv *env,jclass jclazz);
JNIEXPORT jstring JNICALL Java_cn_jingling_lib_livefilter_Opengl20JniLib_getLevelsCompressionShader(JNIEnv *env,jclass jclazz);
JNIEXPORT jstring JNICALL Java_cn_jingling_lib_livefilter_Opengl20JniLib_getSoftlightFragmentShader(JNIEnv *env,jclass jclazz);
JNIEXPORT jstring JNICALL Java_cn_jingling_lib_livefilter_Opengl20JniLib_getSobelFragmentShader(JNIEnv *env,jclass jclazz);
JNIEXPORT jstring JNICALL Java_cn_jingling_lib_livefilter_Opengl20JniLib_getRgb2grayFragmentShader(JNIEnv *env,jclass jclazz);
JNIEXPORT jstring JNICALL Java_cn_jingling_lib_livefilter_Opengl20JniLib_getPosterizeFragmentShader(JNIEnv *env,jclass jclazz);
JNIEXPORT jstring JNICALL Java_cn_jingling_lib_livefilter_Opengl20JniLib_getGenerateBlueFragmentShader(JNIEnv *env,jclass jclazz);
JNIEXPORT jstring JNICALL Java_cn_jingling_lib_livefilter_Opengl20JniLib_getPencilOverLayFragmentShader(JNIEnv *env,jclass jclazz);
JNIEXPORT jstring JNICALL Java_cn_jingling_lib_livefilter_Opengl20JniLib_getHopeEffectFragmentShader(JNIEnv *env,jclass jclazz);
//JNIEXPORT jstring JNICALL Java_cn_jingling_lib_livefilter_Opengl20JniLib_testInitShader(JNIEnv *env,jclass jclazz);

JNIEXPORT jint JNICALL Java_cn_jingling_lib_livefilter_Opengl20JniLib_getShaderProgram(JNIEnv *env, jclass obj, jstring vertexShaderTag, jstring fragmentShaderTag, jstring attrib);

#ifdef __cplusplus
}
#endif
int initShader(const GLchar* vShaderByteArray, const GLchar* fShaderByteArray);
int compileShader(GLuint * shader, GLenum type, const GLchar* source);
#endif


