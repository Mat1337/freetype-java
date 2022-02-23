#include "bitmap.h"

jfieldID bitmap_pointer_id;

JNIEXPORT jint JNICALL GetWidth(JNIEnv* env, jobject object)
{
    jlong pointer = env->GetLongField(object, bitmap_pointer_id);
    return ((FT_Bitmap*)pointer)->width;
}

JNIEXPORT jint JNICALL GetRows(JNIEnv* env, jobject object)
{
    jlong pointer = env->GetLongField(object, bitmap_pointer_id);
    return ((FT_Bitmap*)pointer)->rows;
}

JNIEXPORT jint JNICALL GetPitch(JNIEnv* env, jobject object)
{
    jlong pointer = env->GetLongField(object, bitmap_pointer_id);
    return ((FT_Bitmap*)pointer)->pitch;
}

JNIEXPORT jshort JNICALL GetNumGrays(JNIEnv* env, jobject object)
{
    jlong pointer = env->GetLongField(object, bitmap_pointer_id);
    return ((FT_Bitmap*)pointer)->num_grays;
}

JNIEXPORT jchar JNICALL GetPaletteMode(JNIEnv* env, jobject object)
{
    jlong pointer = env->GetLongField(object, bitmap_pointer_id);
    return ((FT_Bitmap*)pointer)->palette_mode;
}

JNIEXPORT jchar JNICALL GetPixelMode(JNIEnv* env, jobject object)
{
    jlong pointer = env->GetLongField(object, bitmap_pointer_id);
    return ((FT_Bitmap*)pointer)->pixel_mode;
}

JNIEXPORT jobject JNICALL GetBuffer(JNIEnv* env, jobject object)
{
    jlong pointer = env->GetLongField(object, bitmap_pointer_id);
    FT_Bitmap* bmp = (FT_Bitmap*)pointer;
    return env->NewDirectByteBuffer((void*)bmp->buffer, bmp->rows * bmp->width * abs(bmp->pitch));
}

Bitmap::Bitmap()
    : MemoryClass::MemoryClass("me/mat/freetype/font/Bitmap")
{
    bitmap_pointer_id = this->m_PointerID;

    this->Register(
        "getWidth",
        "()I",
        (void*)&GetWidth
    );
    
    this->Register(
        "getRows",
        "()I",
        (void*)&GetRows
    );
    this->Register(
        "getPitch",
        "()I",
        (void*)&GetPitch
    );
    this->Register(
        "getNumGrays",
        "()S",
        (void*)&GetNumGrays
    );
      
    this->Register(
        "getPaletteMode",
        "()C",
        (void*)&GetPaletteMode
    );
    this->Register(
        "getPixelMode",
        "()C",
        (void*)&GetPixelMode
    );
    this->Register(
        "getBuffer",
        "()Ljava/nio/ByteBuffer;",
        (void*)&GetBuffer
    );
}