#pragma once

#include "../class.h"

class FreeType : public Class
{
public:
    FreeType(jlong ptr);
};

extern FreeType* free_type;