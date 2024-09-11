package com.howtodoinjava.app.digitalsigning;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.howtodoinjava.app.web.exception.ForbiddenException;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Base64;

public class JWTToken {
  private static final ObjectMapper mapper = new ObjectMapper();
  public static String generateToken(String userId) {
    try {
//
      String encodedHeader = encode(new TokenHeader());
      String encodedPayload = encode(new TokenPayload(userId));
      String signedContent = String.format("%s.%s", encodedHeader, encodedPayload);
      Signature signer = Signature.getInstance("SHA256withDSA");
      signer.initSign(Keys.getPrivateKey());
      signer.update(signedContent.getBytes(StandardCharsets.UTF_8));
      byte[] signatureBytes = signer.sign();
      String signature = Base64.getUrlEncoder().encodeToString(signatureBytes);
      return String.format("%s.%s.%s", encodedHeader, encodedPayload, signature);
    } catch (NoSuchAlgorithmException | InvalidKeyException | JsonProcessingException e) {
      throw new RuntimeException(e);
    } catch (SignatureException e) {
      throw new RuntimeException(e);
    }
  }

  public static TokenPayload decodeToken(String token)  {
    try {
      String[] arr = token.split("\\.");
      String headerString = decode(arr[0]);
      TokenHeader header = mapper.readValue(headerString, TokenHeader.class);

      String payloadString = decode(arr[1]);
      TokenPayload payload = mapper.readValue(payloadString, TokenPayload.class);

      byte[] signatureBytes = Base64.getUrlDecoder().decode(arr[2]);
        String signedContent = String.format("%s.%s", arr[0], arr[1]);
        Signature signer = Signature.getInstance("SHA256withDSA");
        signer.initVerify(Keys.getPublicKey());
        signer.update(signedContent.getBytes(StandardCharsets.UTF_8));
        if (signer.verify(signatureBytes)) {
          return payload;
        } else {
          throw new ForbiddenException("invalid token");
        }
      } catch (NoSuchAlgorithmException e) {
        throw new RuntimeException(e);
      } catch (InvalidKeyException e) {
        throw new RuntimeException(e);
      } catch (SignatureException e) {
        throw new RuntimeException(e);
      } catch (JsonMappingException e) {
        throw new RuntimeException(e);
      } catch (JsonProcessingException e) {
        throw new RuntimeException(e);
      }
  }

  private static String encode(Object obj) throws JsonProcessingException {
    String s = mapper.writeValueAsString(obj);
    byte[] bytes = s.getBytes(StandardCharsets.UTF_8);
    return Base64.getUrlEncoder().encodeToString(bytes);
  }

  private static String decode(String encodedContent) throws JsonProcessingException {
    byte[] bytes = Base64.getUrlDecoder().decode(encodedContent);
    return new String(bytes);
  }
}
