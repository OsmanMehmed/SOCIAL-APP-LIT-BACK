package com.socialapp.litback.shared;

public final class Constants {
  private Constants() {}

  public static final String ERR_POST_NOT_FOUND = "Post not found";
  public static final String ERR_CREDENTIALS_INVALIDAS = "Credenciales invalidas";
  public static final String ERR_SESION_NO_VALIDA = "Sesion no valida";
  public static final String ERR_FRIEND_REQUEST_NOT_FOUND = "Friend request not found for id %s";
  public static final String ERR_FRIEND_SELF_RELATION = "Users cannot connect with themselves";

  public static final String DEFAULT_USER_SUBTITLE = "Nuevo usuario";
  public static final String DEFAULT_USER_ID = "user-1";
  public static final String POST_PREFIX = "post-";
  public static final String DEFAULT_POST_IMAGE_URL =
      "/api/assets/posts/post-default.jpg";
}
