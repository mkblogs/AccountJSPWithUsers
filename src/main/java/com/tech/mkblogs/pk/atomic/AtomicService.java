package com.tech.mkblogs.pk.atomic;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

@Service
public class AtomicService {

	 private final AtomicInteger counter = new AtomicInteger(0);
	
	 public Integer getValue() {
	    return counter.get();
	 }
	 
	 public void setValue(Integer newValue) {
		 counter.set(newValue);
	 }
	 
	 public synchronized void increment() {
		 while(true) {
            int existingValue = getValue();
            int newValue = existingValue + 1;
            if(counter.compareAndSet(existingValue, newValue)) {
                return;
            }
        }
	 }
}
