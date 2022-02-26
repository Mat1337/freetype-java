#pragma once

#include "memory_class.h"

class GlyphMetrics : public MemoryClass
{
public:
    GlyphMetrics();
};

extern GlyphMetrics* glyph_metrics;