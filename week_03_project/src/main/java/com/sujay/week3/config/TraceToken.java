package com.sujay.week3.config;
import java.util.UUID;
public class TraceToken { private final String value=UUID.randomUUID().toString(); public String getValue(){return value;} }