package org.fixtrading.timpani.codec;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.util.Spliterator;
import java.util.function.Consumer;

public class TagValueMessageSpliterator implements Spliterator<ByteBuffer> {

  public static class TagValueFieldSpliterator implements Spliterator<ByteBuffer> {
    private static final byte FIELD_DELIMITER = 0x01;
    private static final byte TAG_DELIMITER = '=';

    public static int getTag(ByteBuffer buffer) {

      int delimiterPos = BufferUtil.find(buffer, 0, TAG_DELIMITER);
      if (delimiterPos > 0) {
        int length = delimiterPos - buffer.position();
        byte[] tagBuffer = new byte[length];
        buffer.get(tagBuffer, 0, length);
        //consumer the delimiter
        buffer.get();
        return Integer.parseInt(new String(tagBuffer));
      } else {
        return -1;
      }
    }

    public static byte[] getValue(ByteBuffer buffer) {
      int bytes = buffer.remaining();
      byte[] valueBuffer = new byte[bytes];
      buffer.get(valueBuffer, 0, bytes);
      return valueBuffer;
    }

    private final ByteBuffer buffer;
    private int offset;

    public TagValueFieldSpliterator(ByteBuffer buffer) {
      this.buffer = buffer;
      this.offset = 0;
    }

    @Override
    public int characteristics() {
      // TODO Auto-generated method stub
      return 0;
    }

    @Override
    public long estimateSize() {
      // TODO Auto-generated method stub
      return 0;
    }
    
    void reset() {
      offset = 0;
    }

    @Override
    public boolean tryAdvance(Consumer<? super ByteBuffer> consumer) {
      if (offset >= buffer.limit()) {
        return false;
      }
      int start = offset;
      int end = BufferUtil.find(buffer, this.offset, FIELD_DELIMITER);
      if (end > start) {
        try {
          ByteBuffer fieldBuffer = buffer.duplicate();
          fieldBuffer.position(start);
          fieldBuffer.limit(end);
          consumer.accept(fieldBuffer);
          this.offset = end + 1;
          return true;
        } catch (Exception ex) {
          ex.printStackTrace();
          return false;
        }
      } else {
        return false;
      }

    }

    @Override
    public Spliterator<ByteBuffer> trySplit() {
      // TODO Auto-generated method stub
      return null;
    }
  }


  private static final byte[] MESSAGE_DELIMITER = {0x0a};

  private final ByteBuffer buffer;
  private final TagValueFieldSpliterator fieldSpliter;
  private final SeekableByteChannel source;

  /**
   * Constructor for a file soucre
   * 
   * @param source byte channel
   * 
   *        todo: create a version that works with a supplied buffer
   */
  public TagValueMessageSpliterator(SeekableByteChannel source) {
    this.source = source;
    this.buffer = ByteBuffer.allocate(4096);
    this.fieldSpliter = new TagValueFieldSpliterator(buffer);
  }

  @Override
  public int characteristics() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public long estimateSize() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public boolean tryAdvance(Consumer<? super ByteBuffer> consumer) {
    try {
      long position = source.position();
      buffer.clear();
      int bytesRead = source.read(buffer);
      if (bytesRead > 0) {
        buffer.flip();
        int index = BufferUtil.find(buffer, 1, MESSAGE_DELIMITER);
        if (index < 0) {
          index = buffer.limit();
        }
        if (index == 0) {
          return false;
        }
        buffer.limit(index);
        consumer.accept(buffer);
        source.position(position + index + MESSAGE_DELIMITER.length);
        return true;
      }
      return false;
    } catch (IOException e) {
      return false;
    }
  }

  @Override
  public TagValueFieldSpliterator trySplit() {
    fieldSpliter.reset();
    return fieldSpliter;
  }

}
