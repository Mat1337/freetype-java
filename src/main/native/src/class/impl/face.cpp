#include "face.h"

jfieldID face_pointer_id;

JNIEXPORT jint JNICALL DeleteFace(JNIEnv* env, jobject object)
{
    jlong pointer = env->GetLongField(object, face_pointer_id);
    return FT_Done_Face((FT_Face)pointer);
}

JNIEXPORT jint JNICALL GetAscender(JNIEnv *env, jclass object) {
    jlong pointer = env->GetLongField(object, face_pointer_id);
	return ((FT_Face)pointer)->ascender;
}

JNIEXPORT jint JNICALL GetDescender(JNIEnv *env, jclass object) {
    jlong pointer = env->GetLongField(object, face_pointer_id);
	return ((FT_Face)pointer)->descender;
}

JNIEXPORT jlong JNICALL GetFaceFlags(JNIEnv *env, jclass object) {
    jlong pointer = env->GetLongField(object, face_pointer_id);
	return ((FT_Face)pointer)->face_flags;
}

JNIEXPORT jint JNICALL GetFaceIndex(JNIEnv *env, jclass object) {
    jlong pointer = env->GetLongField(object, face_pointer_id);
	return ((FT_Face)pointer)->face_index;
}

JNIEXPORT jstring JNICALL GetFamilyName(JNIEnv *env, jclass object) {
    jlong pointer = env->GetLongField(object, face_pointer_id);
	return env->NewStringUTF(((FT_Face)pointer)->family_name);
}

JNIEXPORT jint JNICALL GetHeight(JNIEnv *env, jclass object) {
    jlong pointer = env->GetLongField(object, face_pointer_id);
	return ((FT_Face)pointer)->height;
}

JNIEXPORT jint JNICALL GetMaxAdvanceHeight(JNIEnv *env, jclass object) {
    jlong pointer = env->GetLongField(object, face_pointer_id);
	return ((FT_Face)pointer)->max_advance_height;
}

JNIEXPORT jint JNICALL GetMaxAdvanceWidth(JNIEnv *env, jclass object) {
    jlong pointer = env->GetLongField(object, face_pointer_id);
	return ((FT_Face)pointer)->max_advance_width;
}

JNIEXPORT jint JNICALL GetNumFaces(JNIEnv *env, jclass object) {
    jlong pointer = env->GetLongField(object, face_pointer_id);
	return ((FT_Face)pointer)->num_faces;
}

JNIEXPORT jint JNICALL GetNumGlyphs(JNIEnv *env, jclass object) {
    jlong pointer = env->GetLongField(object, face_pointer_id);
	return ((FT_Face)pointer)->num_glyphs;
}

JNIEXPORT jlong JNICALL GetStyleFlags(JNIEnv *env, jclass object) {
    jlong pointer = env->GetLongField(object, face_pointer_id);
	return ((FT_Face)pointer)->style_flags;
}

JNIEXPORT jstring JNICALL GetStyleName(JNIEnv *env, jclass object) {
    jlong pointer = env->GetLongField(object, face_pointer_id);
	return env->NewStringUTF(((FT_Face)pointer)->style_name);
}

JNIEXPORT jint JNICALL GetUnderlinePosition(JNIEnv *env, jclass object) {
    jlong pointer = env->GetLongField(object, face_pointer_id);
	return ((FT_Face)pointer)->underline_position;
}

JNIEXPORT jint JNICALL GetUnderlineThickness(JNIEnv *env, jclass object) {
    jlong pointer = env->GetLongField(object, face_pointer_id);
	return ((FT_Face)pointer)->underline_thickness;
}

JNIEXPORT jint JNICALL GetUnitsPerEM(JNIEnv *env, jclass object) {
    jlong pointer = env->GetLongField(object, face_pointer_id);
	return ((FT_Face)pointer)->units_per_EM;
}

JNIEXPORT jint JNICALL GetCharIndex(JNIEnv *env, jclass object, jint code) {
    jlong pointer = env->GetLongField(object, face_pointer_id);
	return FT_Get_Char_Index((FT_Face)pointer, code);
}

JNIEXPORT jboolean JNICALL HasKering(JNIEnv *env, jclass object) {
    jlong pointer = env->GetLongField(object, face_pointer_id);
	return FT_HAS_KERNING(((FT_Face)pointer));
}

JNIEXPORT jboolean JNICALL SelectSize(JNIEnv *env, jclass object, jint strikeIndex) {
    jlong pointer = env->GetLongField(object, face_pointer_id);
	return FT_Select_Size((FT_Face)pointer, strikeIndex);
}

JNIEXPORT jboolean JNICALL SetCharSize(JNIEnv *env, jclass object, jint char_width, jint char_height, jint horz_resolution, jint vert_resolution) {
    jlong pointer = env->GetLongField(object, face_pointer_id);
	return FT_Set_Char_Size((FT_Face)pointer, char_width, char_height, horz_resolution, vert_resolution);
}

JNIEXPORT jboolean JNICALL LoadGlyph(JNIEnv *env, jclass object, jint glyphIndex, jint loadFlags) {
    jlong pointer = env->GetLongField(object, face_pointer_id);
	return FT_Load_Glyph((FT_Face)pointer, glyphIndex, loadFlags);
}

JNIEXPORT jboolean JNICALL LoadChar(JNIEnv *env, jclass object, jchar aChar, jint flags) {
    jlong pointer = env->GetLongField(object, face_pointer_id);
	return FT_Load_Char((FT_Face)pointer, aChar, flags);
}

JNIEXPORT jobject JNICALL GetKerning(JNIEnv *env, jclass object, jchar left, jchar right, jint mode) {
    jlong pointer = env->GetLongField(object, face_pointer_id);
	
    FT_Vector vector;
	int x = 0;
	int y = 0;
	if (!FT_Get_Kerning((FT_Face)pointer, left, right, mode, &vector)) {
		x = vector.x;
		y = vector.y;
	}
	
	jclass cls = env->FindClass("me/mat/freetype/font/kerning/Kerning");
	jmethodID methodID = env->GetMethodID(cls, "<init>", "(II)V");
	return env->NewObject(cls, methodID, x, y);
}

JNIEXPORT jboolean JNICALL SetPixelSizes(JNIEnv *env, jclass object, jfloat width, jfloat heigth) {
    jlong pointer = env->GetLongField(object, face_pointer_id);
	return FT_Set_Pixel_Sizes((FT_Face)pointer, (FT_UInt)width, (FT_UInt)heigth);
}

JNIEXPORT jlong JNICALL GetGlyph(JNIEnv *env, jclass object) {
    jlong pointer = env->GetLongField(object, face_pointer_id);
	return (jlong)((FT_Face)pointer)->glyph;
}

JNIEXPORT jlong JNICALL GetFaceSize(JNIEnv *env, jclass object) {
    jlong pointer = env->GetLongField(object, face_pointer_id);
	return (jlong)(((FT_Face)pointer)->size);
}

JNIEXPORT jboolean JNICALL CheckTrueTypePatents(JNIEnv *env, jclass object) {
    jlong pointer = env->GetLongField(object, face_pointer_id);
	return FT_Face_CheckTrueTypePatents((FT_Face)pointer);
}

JNIEXPORT jboolean JNICALL SetUnpatentedHinting(JNIEnv *env, jclass object, jboolean value) {
    jlong pointer = env->GetLongField(object, face_pointer_id);
	return FT_Face_SetUnpatentedHinting((FT_Face)pointer, value);
}

JNIEXPORT jboolean JNICALL ReferenceFace(JNIEnv *env, jclass object) {
    jlong pointer = env->GetLongField(object, face_pointer_id);
	return FT_Reference_Face((FT_Face)pointer);
}

JNIEXPORT jboolean JNICALL RequestSize(JNIEnv *env, jclass object, jobject sizeRequest) {
    jlong pointer = env->GetLongField(object, face_pointer_id);
	jclass sizeRequestClass = env->GetObjectClass(sizeRequest);

	FT_Size_RequestRec req = {};
	req.height = (FT_Long)env->GetLongField(sizeRequest, env->GetFieldID(sizeRequestClass, "height", "J"));
	req.width = (FT_Long)env->GetLongField(sizeRequest, env->GetFieldID(sizeRequestClass, "width", "J"));
	req.horiResolution = (FT_UInt)env->GetIntField(sizeRequest, env->GetFieldID(sizeRequestClass, "horizontalResolution", "I"));
	req.vertResolution = (FT_UInt)env->GetIntField(sizeRequest, env->GetFieldID(sizeRequestClass, "verticalResolution", "I"));
	req.type = (FT_Size_Request_Type)(env->GetIntField(sizeRequest, env->GetFieldID(sizeRequestClass, "type", "I")));

	return FT_Request_Size((FT_Face)pointer, (FT_Size_Request)&req);
}

JNIEXPORT jintArray JNICALL GetFirstChar(JNIEnv *env, jclass object) {
    jlong pointer = env->GetLongField(object, face_pointer_id);
	FT_ULong  charcode;
	FT_UInt   gindex;
	charcode = FT_Get_First_Char((FT_Face)pointer, &gindex);

	jintArray result = env->NewIntArray(2);
	if (result == NULL) // Out of memory
		return NULL;
	jint fill[2];
	fill[0] = charcode;
	fill[1] = gindex;
	env->SetIntArrayRegion(result, 0, 2, fill);

	return result;
}

JNIEXPORT jint JNICALL GetNextChar(JNIEnv *env, jclass object, jlong charcode) {
    jlong pointer = env->GetLongField(object, face_pointer_id);
	FT_UInt   gindex;
	FT_Get_Next_Char((FT_Face)pointer, (FT_ULong)charcode, &gindex);
	return gindex;
}

JNIEXPORT jint JNICALL GetNameIndex(JNIEnv *env, jclass object, jstring name) {
    jlong pointer = env->GetLongField(object, face_pointer_id);
	const char *nameNative = env->GetStringUTFChars(name, 0);
	jint glyphIndex = FT_Get_Name_Index((FT_Face)pointer, (char*)nameNative);
	env->ReleaseStringUTFChars(name, nameNative);
	return glyphIndex;
}

JNIEXPORT jlong JNICALL GetTrackKerning(JNIEnv *env, jclass object, jlong point_size, jint degree) {
    jlong pointer = env->GetLongField(object, face_pointer_id);
	long kern;
	if (FT_Get_Track_Kerning((FT_Face)pointer, (FT_Fixed)point_size, degree, &kern) != 0)
		return 0;
	return kern;
}

JNIEXPORT jstring JNICALL GetFaceGlyphName(JNIEnv *env, jclass object, jint glyphIndex) {
    jlong pointer = env->GetLongField(object, face_pointer_id);
	char* name[100];
	FT_Get_Glyph_Name((FT_Face)pointer, glyphIndex, name, 100);
	return env->NewStringUTF((const char*)name);
}

JNIEXPORT jstring JNICALL GetPostScriptName(JNIEnv *env, jclass object) {
    jlong pointer = env->GetLongField(object, face_pointer_id);
	return env->NewStringUTF(FT_Get_Postscript_Name((FT_Face)pointer));
}

JNIEXPORT jboolean JNICALL SelectFaceCharMap(JNIEnv *env, jclass object, jint encoding) {
    jlong pointer = env->GetLongField(object, face_pointer_id);
	return FT_Select_Charmap((FT_Face)pointer, (FT_Encoding)encoding);
}

JNIEXPORT jboolean JNICALL SetFaceCharMap(JNIEnv *env, jclass object, jlong charmap) {
    jlong pointer = env->GetLongField(object, face_pointer_id);
	return FT_Set_Charmap((FT_Face)pointer, (FT_CharMap)charmap);
}

JNIEXPORT jshort JNICALL GetTypeFlags(JNIEnv *env, jclass object) {
    jlong pointer = env->GetLongField(object, face_pointer_id);
	return FT_Get_FSType_Flags((FT_Face)pointer);
}

Face::Face()
    : MemoryClass::MemoryClass("me/mat/freetype/font/Face")
{
    face_pointer_id = m_PointerID;

    this->Register(
        "deleteFace",
        "()Z",
        (void*)&DeleteFace
    );
    this->Register(
        "getAscender",
        "()I",
        (void*)&GetAscender
    );
    this->Register(
        "getDescender",
        "()I",
        (void*)&GetDescender
    );
    this->Register(
        "getFaceFlags",
        "()J",
        (void*)&GetFaceFlags
    );
    this->Register(
        "getFaceIndex",
        "()I",
         (void*)&GetFaceIndex
    );
    this->Register(
        "getFamilyName",
        "()Ljava/lang/String;",
         (void*)&GetFamilyName
    );
    this->Register(
        "getHeight",
        "()I",
         (void*)&GetHeight
    );
    this->Register(
        "getMaxAdvanceHeight",
        "()I",
        (void*)&GetMaxAdvanceHeight
    );
    this->Register(
        "getMaxAdvanceWidth",
        "()I",
        (void*)&GetMaxAdvanceWidth
    );
    this->Register(
        "getNumFaces",
        "()I",
        (void*)&GetNumFaces
    );
    this->Register(
        "getNumGlyphs",
        "()I",
        (void*)&GetNumGlyphs
    );
    this->Register(
        "getStyleFlags",
        "()J",
        (void*)&GetStyleFlags
    );
    this->Register(
        "getStyleName",
        "()Ljava/lang/String;",
        (void*)&GetStyleName
    );
    this->Register(
        "getUnderlinePosition",
        "()I",
        (void*)&GetUnderlinePosition
    );
    this->Register(
        "getUnderlineThickness",
        "()I",
        (void*)&GetUnderlineThickness
    );
    this->Register(
        "getUnitsPerEM",
        "()I",
        (void*)&GetUnitsPerEM
    );
    this->Register(
        "getCharIndex",
        "(I)I",
        (void*)&GetCharIndex
    );
    this->Register(
        "hasKerning",
        "()Z",
        (void*)&HasKering
    );
    this->Register(
        "selectSize",
        "(I)Z",
        (void*)&SelectSize
    );
    this->Register(
        "setCharSize",
        "(IIII)Z",
        (void*)&SetCharSize
    );
    this->Register(
        "loadGlyph",
        "(II)Z",
        (void*)&LoadGlyph
    );
    this->Register(
        "loadChar",
        "(CI)Z",
        (void*)&LoadChar
    );
    this->Register(
        "getKerning",
        "(CCI)Lme/mat/freetype/font/kerning/Kerning;",
        (void*)&GetKerning
    );
    this->Register(
        "setPixelSizes",
        "(FF)Z",
        (void*)&SetPixelSizes
    );
    this->Register(
        "getGlyphSlotPointer",
        "()J",
        (void*)&GetGlyph
    );
    this->Register(
        "getSizePointer",
        "()J",
        (void*)&GetFaceSize
    );
    this->Register(
        "checkTrueTypePatents",
        "()Z",
        (void*)&CheckTrueTypePatents
    );
    this->Register(
        "setUnpatentedHinting",
        "(Z)Z",
        (void*)&SetUnpatentedHinting
    );
    this->Register(
        "referenceFace",
        "()Z",
        (void*)&ReferenceFace
    );
    this->Register(
        "requestSize",
        "(Lme/mat/freetype/font/size/SizeRequest;)Z",
        (void*)&RequestSize
    );
    this->Register(
        "getFirstChar",
        "()[I",
        (void*)&GetFirstChar
    );
    this->Register(
        "getNextChar",
        "(J)I",
        (void*)&GetNextChar
    );
    this->Register(
        "getGlyphIndexByName",
        "(Ljava/lang/String;)I",
        (void*)&GetNameIndex
    );
    this->Register(
        "getTrackKerning",
        "(II)J",
        (void*)&GetTrackKerning
    );
    this->Register(
        "getGlyphName",
        "(I)Ljava/lang/String;",
        (void*)&GetFaceGlyphName
    );
    this->Register(
        "getPostscriptName",
        "()Ljava/lang/String;",
        (void*)&GetPostScriptName
    );
    this->Register(
        "selectCharmap",
        "(I)Z",
        (void*)&SelectFaceCharMap
    );
    this->Register(
        "setCharmap",
        "(Lme/mat/freetype/font/CharMap;)Z",
        (void*)&SetFaceCharMap
    );
    this->Register(
        "getFSTypeFlags",
        "()S",
        (void*)&GetTypeFlags
    );
}