package me.mat.freetype;

import lombok.NonNull;
import me.mat.freetype.font.face.Face;
import me.mat.freetype.util.MemoryClass;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class FreeTypeHandle extends MemoryClass {

    private int currentIndex;

    public FreeTypeHandle(@NonNull long pointer) {
        super(pointer);
        this.currentIndex = 0;
    }

    public Face newFace(String file) {
        return newFace(file, currentIndex);
    }

    public Face newFaceFromMemory(InputStream inputStream) {
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int nRead;
            byte[] data = new byte[4];
            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            buffer.flush();
            return newFace(buffer.toByteArray(), currentIndex);
        } catch (IOException e) {
            return null;
        }
    }

    private Face newFace(String file, int faceIndex) {
        try {
            Path p = FileSystems.getDefault().getPath(file);
            byte[] fileData = Files.readAllBytes(p);
            return newFace(fileData, faceIndex);
        } catch (IOException e) {
            return null;
        }
    }

    private Face newFace(byte[] file, int faceIndex) {
        ByteBuffer buffer = createBuffer(file.length);
        buffer.order(ByteOrder.nativeOrder());
        buffer.limit(buffer.position() + file.length);
        fillBuffer(file, buffer, file.length);
        return newFace(buffer, faceIndex);
    }

    private Face newFace(ByteBuffer file, int faceIndex) {
        long face = createFaceFromMemory(file, file.remaining(), faceIndex);
        if (face <= 0) {
            deleteBuffer(file);
            return null;
        }
        currentIndex++;
        return new Face(face, file, faceIndex);
    }

    protected native long createFaceFromMemory(ByteBuffer buffer, int length, int faceIndex);

}
