package org.fixtrading.timpani.codec;

import java.nio.ByteBuffer;

final class BufferUtil {

  public static int find(ByteBuffer buffer, int offset, byte searchKey) {
    for (int i = buffer.position() + offset; i < buffer.limit(); i++) {
      byte nextByte = buffer.get(i);
      if (nextByte == searchKey) {
        return i;
      }
    }
    return -1;
  }

  public static int find(ByteBuffer buffer, int offset, byte[] searchKey) {
    int searchIndex = 0;
    for (int i = buffer.position() + offset; i < buffer.limit() - searchKey.length; i++) {
      byte nextByte = buffer.get(i);
      if (nextByte != searchKey[searchIndex]) {
        searchIndex = 0;
      } else {
        searchIndex++;
        if (searchIndex == searchKey.length) {
          return i + 1 - searchKey.length;
        }
      }
    }
    return -1;
  }
}
