package org.fixtrading.timpani.codec;

import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.FileSystems;
import java.nio.file.StandardOpenOption;

import org.fixtrading.timpani.codec.TagValueMessageSpliterator;
import org.fixtrading.timpani.codec.TagValueMessageSpliterator.TagValueFieldSpliterator;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class TagValueMessageSpliteratorTest {

  private TagValueMessageSpliterator spliter;
  private static SeekableByteChannel channel;

  @Before
  public void setUp() throws Exception {
    channel = FileChannel.open(FileSystems.getDefault().getPath("secdef.dat"),
        StandardOpenOption.READ);

    spliter = new TagValueMessageSpliterator(channel);
  }

  @After
  public void tearDown() throws Exception {
    channel.close();
  }

  @Test
  public void testTryAdvance() {
    spliter.forEachRemaining(b -> {
      TagValueFieldSpliterator fieldSpliter = spliter.trySplit();
      fieldSpliter.forEachRemaining(b2 -> {
        int tag = TagValueFieldSpliterator.getTag(b2);
        byte [] value = TagValueFieldSpliterator.getValue(b2);
        System.out.format("Tag=%d Value='%s'%n", tag, new String(value));
      });
    });
  }

}
