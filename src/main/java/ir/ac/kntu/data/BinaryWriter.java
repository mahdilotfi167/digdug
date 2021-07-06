package ir.ac.kntu.data;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class BinaryWriter {
    private String filename;
    public BinaryWriter(String filename) {
        this.filename = filename;
    }
    public int[] get() {
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(filename+".dat"))) {
        IntBuffer buffer = ByteBuffer.wrap(in.readAllBytes()).asIntBuffer();
            int[] res = new int[buffer.limit()];
            for (int i = 0;i<res.length;i++) {
                res[i] = buffer.get(i);
            }
            return res;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public void write(int[] input) {
        try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(filename+".dat"))) {
            for (int n : input) {
                out.write(intToByte(n));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static byte[] intToByte(int n) {
        return ByteBuffer.allocate(4).putInt(n).array();
    }
}
