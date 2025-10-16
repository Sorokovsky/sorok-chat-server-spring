package pro.sorokovsky.sorokchatserverspring.configuration;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.DirectDecrypter;
import com.nimbusds.jose.crypto.DirectEncrypter;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jose.jwk.OctetSequenceKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pro.sorokovsky.sorokchatserverspring.deserializer.AccessTokenDeserializer;
import pro.sorokovsky.sorokchatserverspring.deserializer.RefreshTokenDeserializer;
import pro.sorokovsky.sorokchatserverspring.serializer.AccessTokenSerializer;
import pro.sorokovsky.sorokchatserverspring.serializer.RefreshTokenSerializer;

import java.text.ParseException;

@Configuration
public class TokensConfiguration {

    @Bean
    public AccessTokenSerializer accessTokenSerializer(@Value("${tokens.signing-key}") String signingKey)
            throws ParseException, KeyLengthException {
        return new AccessTokenSerializer(JWSAlgorithm.HS256, new MACSigner(OctetSequenceKey.parse(signingKey)));
    }

    @Bean
    public AccessTokenDeserializer accessTokenDeserializer(@Value("${tokens.signing-key}") String signingKey)
            throws ParseException, JOSEException {
        return new AccessTokenDeserializer(new MACVerifier(OctetSequenceKey.parse(signingKey)));
    }

    @Bean
    public RefreshTokenSerializer refreshTokenSerializer(@Value("${tokens.encrypting-key}") String encryptingKey)
            throws ParseException, KeyLengthException {
        return new RefreshTokenSerializer(
                JWEAlgorithm.DIR,
                EncryptionMethod.A192GCM,
                new DirectEncrypter(OctetSequenceKey.parse(encryptingKey))
        );
    }

    @Bean
    public RefreshTokenDeserializer refreshTokenDeserializer(@Value("${tokens.encrypting-key}") String encryptingKey)
            throws ParseException, JOSEException {
        return new RefreshTokenDeserializer(new DirectDecrypter(OctetSequenceKey.parse(encryptingKey)));
    }
}