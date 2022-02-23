#pragma once
#include <jni.h>
#include <vector>

#include <ft2build.h>
#include FT_FREETYPE_H

class Class;
struct vm_t
{
    JavaVM*             jvm;
    JNIEnv*             env;
    std::vector<Class*> cp;
};
extern vm_t vm;

class Class
{
public:
    Class(const char* name);
public:
    bool                            RegisterNatives();
    const char*                     GetName();
protected:
    void                            Register(const char* name, const char* sig, void* ptr);
protected:
    const char*                     m_Name;
    jclass                          m_Class;
    std::vector<JNINativeMethod>    m_Methods;
};