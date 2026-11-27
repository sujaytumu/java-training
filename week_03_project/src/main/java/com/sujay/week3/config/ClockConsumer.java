package com.sujay.week3.config;
import java.time.Clock;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
@Component public class ClockConsumer {
 private final Clock businessClock;
 public ClockConsumer(@Qualifier("businessClock") Clock businessClock){this.businessClock=businessClock;}
 public Clock getBusinessClock(){return businessClock;}
}