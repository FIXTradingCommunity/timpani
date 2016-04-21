package org.fixtrading.timpani.codec;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetTime;

import org.fixtrading.timpani.codec.JsonCodec;
import org.junit.Before;
import org.junit.Test;

public class JsonCodecTest {

  private JsonCodec codec;
  
  @Before
  public void setUp() throws Exception {
    codec = new JsonCodec();
  }

  @Test
  public void zonedDateTime() {
    OffsetTime value = OffsetTime.now();
    String message = codec.encode(value);
    OffsetTime value2 = codec.decode(message, OffsetTime.class);
    assertEquals(value, value2);
  }
  
  @Test
  public void zonedTime() {
    OffsetTime value = OffsetTime.now();
    String message = codec.encode(value);
    OffsetTime value2 = codec.decode(message, OffsetTime.class);
    assertEquals(value, value2);
  }
  
  @Test
  public void localDateTime() {
    LocalDateTime value = LocalDateTime.now();
    String message = codec.encode(value);
    LocalDateTime value2 = codec.decode(message, LocalDateTime.class);
    assertEquals(value, value2);
  }

  @Test
  public void localDate() {
    LocalDate value = LocalDate.now();
    String message = codec.encode(value);
    LocalDate value2 = codec.decode(message, LocalDate.class);
    assertEquals(value, value2);
  }
  
  @Test
  public void localTime() {
    LocalTime value = LocalTime.now();
    String message = codec.encode(value);
    LocalTime value2 = codec.decode(message, LocalTime.class);
    assertEquals(value, value2);
  }
  
  class MultivalueStringTest {
    String [] TradeCondition;
  }
  
  //277=AV AN A
  @Test
  public void multiStringValue() {
    MultivalueStringTest value = new MultivalueStringTest();
    value.TradeCondition = new String[] {"QuoteSpread", "OfficalClosingPrice", "Cash"};
    String message = codec.encode(value);
    MultivalueStringTest value2 = codec.decode(message, MultivalueStringTest.class);
    assertArrayEquals(value.TradeCondition, value2.TradeCondition);
  }
}
