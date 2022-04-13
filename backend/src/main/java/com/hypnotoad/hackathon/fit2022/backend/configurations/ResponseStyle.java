package com.hypnotoad.hackathon.fit2022.backend.configurations;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PACKAGE, ElementType.TYPE})
@Retention(RetentionPolicy.CLASS)
@Value.Style(typeAbstract = "Abstract*", typeImmutable = "*", allParameters = true, of = "new")
@JsonSerialize
@JsonDeserialize
public @interface ResponseStyle {
}
