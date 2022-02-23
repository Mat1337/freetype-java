#include "class.h"

Class::Class(const char* name)
    : m_Name(name)
{
    m_Class = vm.env->FindClass(name);
    vm.cp.push_back(this);
}

bool Class::RegisterNatives()
{
    int size = (int)m_Methods.size();
    JNINativeMethod* native_methods = (JNINativeMethod*)calloc(size, sizeof(JNINativeMethod));
    for(int i = 0; i < size; i++)
        native_methods[i] = m_Methods[i];
    
    if (vm.env->RegisterNatives(m_Class, native_methods, size) < 0)
    {
        free(native_methods);
        m_Methods.clear();
        return false;
    }

    free(native_methods);
    m_Methods.clear();
    return true;
}

const char* Class::GetName()
{
    return m_Name;
}

void Class::Register(const char* name, const char* sig, void* ptr)
{
    m_Methods.push_back({
        (char*)name,
        (char*)sig,
        ptr
    });
}