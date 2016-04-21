package org.fixtrading.timpani.securitydef.datastore.loader;

import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.HOUR_OF_DAY;
import static java.time.temporal.ChronoField.MICRO_OF_SECOND;
import static java.time.temporal.ChronoField.MINUTE_OF_HOUR;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static java.time.temporal.ChronoField.SECOND_OF_MINUTE;
import static java.time.temporal.ChronoField.YEAR;

import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.FileSystems;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Spliterator;
import java.util.function.Consumer;

import org.fixtrading.timpani.codec.TagValueMessageSpliterator;
import org.fixtrading.timpani.codec.TagValueMessageSpliterator.TagValueFieldSpliterator;
import org.fixtrading.timpani.securitydef.datastore.MongoDBSecurityDefinitionStore;
import org.fixtrading.timpani.securitydef.datastore.SecurityDefinitionStore;
import org.fixtrading.timpani.securitydef.messages.InstrAttribType;
import org.fixtrading.timpani.securitydef.messages.Product;
import org.fixtrading.timpani.securitydef.messages.PutOrCall;
import org.fixtrading.timpani.securitydef.messages.SecurityDefinitionImpl;
import org.fixtrading.timpani.securitydef.messages.SecurityIDSource;
import org.fixtrading.timpani.securitydef.messages.SecurityRejectReason;
import org.fixtrading.timpani.securitydef.messages.SecurityRequestResult;
import org.fixtrading.timpani.securitydef.messages.SecurityResponseType;
import org.fixtrading.timpani.securitydef.messages.SettlPriceType;
import org.fixtrading.timpani.securitydef.messages.SubscriptionRequestType;
import org.fixtrading.timpani.securitydef.messages.SecurityDefinitionImpl.InstrAttribImpl;
import org.fixtrading.timpani.securitydef.messages.SecurityDefinitionImpl.MarketSegmentImpl;

/**
 * Load security definition store from a file
 * <p>
 * The bulk loader truncates an existing collection before reloading it.
 * MongoDB database name: 'refdata' collection: 'secdef'
 * 
 * @author Don Mendelson
 * 
 * @see <a href="ftp://ftp.cmegroup.com/SBEFix/Production">CME source</a>
 */
public class TagValueSourceBulkLoader {

  /**
   * Run bulk loader
   * @param args first argument is name of file to load
   * @throws Exception if the file or data store cannot be accessed
   */
  public static void main(String[] args) throws Exception {
    if (args.length < 1) {
      System.out.println("Usage TagValueSourceBulkLoader <file-name>");
      System.exit(1);
    }
    try (MongoDBSecurityDefinitionStore store = new MongoDBSecurityDefinitionStore();
        FileChannel channel =
            FileChannel.open(FileSystems.getDefault().getPath(args[0]), StandardOpenOption.READ)) {
      store.open().get();
      store.truncate().get();

      TagValueSourceBulkLoader loader = new TagValueSourceBulkLoader();
      Instant start = Instant.now();
      loader.load(store, channel);
      Instant end = Instant.now();
      long elapsed = Duration.between(start, end).toMillis();
      int insertions = loader.getInsertions();
      double rate = elapsed == 0 ? 0 : insertions * 1000.0 / elapsed;
      System.out.format("%d inserted in %.3f seconds at %.3f insertions/second%n", insertions, 
          elapsed/1000.0, rate);
    }
  }

  private Consumer<? super ByteBuffer> fieldAction = new Consumer<ByteBuffer>() {
    
    private InstrAttribImpl instrAttribImpl;
    private MarketSegmentImpl marketSegmentImpl;

    @Override
    public void accept(ByteBuffer buffer) {
      int tag = TagValueFieldSpliterator.getTag(buffer);
      byte[] value = TagValueFieldSpliterator.getValue(buffer);
      switch (tag) {
        case 15:
          securityDefinition.setCurrency(new String(value));
          break;
        case 22:
          SecurityIDSource securityIDSource =
              SecurityIDSource.getValue(Character.valueOf((char) value[0]));
          securityDefinition.setSecurityIDSource(securityIDSource);
          break;
        case 23:
          SubscriptionRequestType subscriptionRequestType =
              SubscriptionRequestType.getValue(Character.valueOf((char) value[0]));
          securityDefinition.setSubscriptionRequestType(subscriptionRequestType);
          break;
        case 48:
          securityDefinition.setSecurityID(new String(value));
          break;
        case 55:
          securityDefinition.setSymbol(new String(value));
          break;
        case 107:
          securityDefinition.setSecurityDesc(new String(value));
          break;
        case 120:
          securityDefinition.setSettlCurrency(new String(value));
          break;
        case 167:
          securityDefinition.setSecurityType(new String(value));
          break;
        case 200:
          securityDefinition.setMaturityMonthYear(new String(value));
          break;
        case 201:
          PutOrCall putOrCall = PutOrCall.getValue(Integer.parseInt(new String(value)));
          securityDefinition.setPutOrCall(putOrCall);
          break;
        case 202:
          securityDefinition.setStrikePrice(Double.parseDouble(new String(value)));
          break;
        case 207:
          securityDefinition.setSecurityExchange(new String(value));
          break;
        case 231:
          securityDefinition.setContractMultiplier(Double.parseDouble(new String(value)));
          break;
        case 320:
          securityDefinition.setSecurityReqID(new String(value));
          break;
        case 323:
          SecurityResponseType securityResponseType =
              SecurityResponseType.getValue(Integer.parseInt(new String(value)));
          securityDefinition.setSecurityResponseType(securityResponseType);
          break;
        case 336:
          securityDefinition.setTradingSessionID(new String(value));
          break;
        case 460:
          Product product = Product.getValue(Integer.parseInt(new String(value)));
          securityDefinition.setProduct(product);
          break;
        case 461:
          securityDefinition.setCfiCode(new String(value));
          break;
        case 462:
          Product underlyingProduct = Product.getValue(Integer.parseInt(new String(value)));
          securityDefinition.setUnderlyingProduct(underlyingProduct);
          break;
        case 560:
          SecurityRequestResult securityRequestResult =
              SecurityRequestResult.getValue(Integer.parseInt(new String(value)));
          securityDefinition.setSecurityRequestResult(securityRequestResult);
          break;
        case 625:
          securityDefinition.setTradingSessionSubID(new String(value));
          break;
        case 731:
          SettlPriceType settlPriceType =
              SettlPriceType.getValue(Integer.parseInt(new String(value)));
          securityDefinition.setSettlPriceType(settlPriceType);
          break;
        case 779:
          securityDefinition.setLastUpdateTime(parseUTCTimestamp(new String(value)));
          break;
        case 871:
          instrAttribImpl = securityDefinition.addInstrAttrib();
          InstrAttribType instrAttribType =
              InstrAttribType.getValue(Integer.parseInt(new String(value)));
          instrAttribImpl.setInstrAttribType(instrAttribType);
          break;
        case 872:
          instrAttribImpl.setInstrAttribValue(new String(value));
          break;
        case 969:
          securityDefinition.setMinPriceIncrement(Double.parseDouble(new String(value)));
          break;
        case 1140:
          securityDefinition.setMaxTradeVol(Integer.parseInt(new String(value)));
          break;
        case 1142:
          securityDefinition.setMatchAlgorithm(new String(value));
          break;
        case 1151:
          securityDefinition.setSecurityGroup(new String(value));
          break;
        case 1300:
          marketSegmentImpl = securityDefinition.addMarketSegment();
          marketSegmentImpl.setMarketSegmentID(new String(value));
          break;
        case 1301:
          marketSegmentImpl = securityDefinition.addMarketSegment();
          marketSegmentImpl.setMarketID(new String(value));
          break;
        case 1607:
          SecurityRejectReason securityRejectReason =
              SecurityRejectReason.getValue(Integer.parseInt(new String(value)));
          securityDefinition.setSecurityRejectReason(securityRejectReason);
          break;
      }
    }

  };

  private int insertions;

  // handle format like '20160403172000000000'
  private DateTimeFormatter instantParser = new DateTimeFormatterBuilder().appendValue(YEAR, 4)
      .appendValue(MONTH_OF_YEAR, 2).appendValue(DAY_OF_MONTH, 2).appendValue(HOUR_OF_DAY, 2)
      .appendValue(MINUTE_OF_HOUR, 2).appendValue(SECOND_OF_MINUTE, 2).optionalStart()
      .appendValue(MICRO_OF_SECOND, 6).toFormatter();

  private Consumer<? super ByteBuffer> messageAction = new Consumer<ByteBuffer>() {

    @Override
    public void accept(ByteBuffer buffer) {
      securityDefinition = new SecurityDefinitionImpl();
    }

  };

  private SecurityDefinitionImpl securityDefinition;

  public int getInsertions() {
    return insertions;
  }

  void load(SecurityDefinitionStore store, SeekableByteChannel source) throws Exception {
    try (SecurityDefinitionStore s = store) {
      s.open().get();
      TagValueMessageSpliterator messageSpliter = new TagValueMessageSpliterator(source);

      insertions = 0;
      // todo: consider pipelining
      messageSpliter.forEachRemaining(b -> {
        messageAction.accept(b);
        Spliterator<ByteBuffer> fieldSpliter = messageSpliter.trySplit();
        fieldSpliter.forEachRemaining(fieldAction);
        try {
          store.insert(securityDefinition).get();
          insertions++;
        } catch (Exception e) {
          e.printStackTrace();
          // rethrow to stop iteration
          throw new RuntimeException(e);
        }
      });
    }
  }

  LocalDateTime parseUTCTimestamp(final CharSequence text) {
    return LocalDateTime.parse(text, instantParser);
  }
}
