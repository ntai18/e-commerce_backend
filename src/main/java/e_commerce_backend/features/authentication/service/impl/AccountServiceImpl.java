package e_commerce_backend.features.authentication.service.impl;

import e_commerce_backend.features.authentication.events.UserRegisteredEvent;
import e_commerce_backend.features.authentication.model.dto.request.AcceptOtpRequest;
import e_commerce_backend.features.authentication.model.dto.request.AccountRequest;
import e_commerce_backend.features.authentication.model.dto.request.ResetPasswordRequest;
import e_commerce_backend.features.authentication.model.dto.request.TokenRequest;
import e_commerce_backend.features.authentication.model.dto.response.OtpAcceptResponse;
import e_commerce_backend.features.authentication.model.dto.response.TokenResponse;
import e_commerce_backend.features.authentication.model.entity.Account;
import e_commerce_backend.features.authentication.repository.AccountRepository;
import e_commerce_backend.features.authentication.service.AccountService;
import e_commerce_backend.features.authentication.service.JwtService;
import e_commerce_backend.common.type.RoleType;
import e_commerce_backend.common.type.StatusAccountType;
import e_commerce_backend.common.exception.ECommerceException;
import e_commerce_backend.common.exception.ErrorCode;
import e_commerce_backend.features.email.EmailSender;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {
   private final AccountRepository accountRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final StringRedisTemplate authRedisTemplate;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final EmailSender emailSender;


    @Override
    public void createAccount(AccountRequest accountRequest) {
        if (accountRepository.findByUsername(accountRequest.getUsername()) != null)
            throw new ECommerceException(ErrorCode.AUTH_01);
        if (accountRepository.findByEmail(accountRequest.getEmail()) != null)
            throw new ECommerceException(ErrorCode.AUTH_01);
        Account accountNew = new Account();
        accountNew.setUsername(accountRequest.getUsername());
        accountNew.setPassword(passwordEncoder.encode(accountRequest.getPassword()));
        accountNew.setRole(RoleType.USER);
        accountNew.setEmail(accountRequest.getEmail());
        accountNew.setPhone(accountRequest.getPhone());
        accountNew.setStatus(StatusAccountType.ACTIVE);
        accountRepository.save(accountNew);
        UserRegisteredEvent userRegisteredEvent = new UserRegisteredEvent(accountNew.getId(), accountNew.getEmail());
        applicationEventPublisher.publishEvent(userRegisteredEvent);
    }

    @Override
    public TokenResponse login(AccountRequest accountRequest) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(accountRequest.getUsername(), accountRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        Account account = (Account) authentication.getPrincipal();
        String accessToken = jwtService.generateAccessToken(account);
        String refreshToken = jwtService.generateRefreshToken();
        authRedisTemplate.opsForValue().set(refreshToken, accountRequest.getUsername(), 15, TimeUnit.DAYS);
        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public TokenResponse refreshToken(TokenRequest tokenRequest) {
        String username = authRedisTemplate.opsForValue().get(tokenRequest.getRefreshToken());
        if (username == null)
            throw new ECommerceException(ErrorCode.AUTH_01);
        Account account = accountRepository.findByUsername(username);
        String accessToken = jwtService.generateAccessToken(account);
        String refreshToken = jwtService.generateRefreshToken();
        authRedisTemplate.opsForValue().set(refreshToken, account.getUsername(), 15, TimeUnit.DAYS);
        authRedisTemplate.delete(tokenRequest.getRefreshToken());
        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public void logout(TokenRequest tokenRequest) {
        authRedisTemplate.delete(tokenRequest.getRefreshToken());
    }

    @Override
    public void forgotPassword(AccountRequest accountRequest) {
        Account account = accountRepository.findByEmail(accountRequest.getEmail());
        if (account == null)
            throw new ECommerceException(ErrorCode.AUTH_02);
        if(!(StatusAccountType.ACTIVE.equals(account.getStatus())))
            throw new ECommerceException(ErrorCode.AUTH_01);
        String otp = String.format("%06d", new Random().nextInt(999999));
        String nameOtp = "OTP: "+account.getEmail();
        Boolean otpOld = authRedisTemplate.opsForValue().setIfAbsent(nameOtp, otp, 2, TimeUnit.MINUTES);
        if(Boolean.FALSE.equals(otpOld))
            throw new ECommerceException(ErrorCode.AUTH_02);
        String subject = "Forgot Password";
        String content = "Your password has been sent to your account %s".formatted(otp);
        emailSender.sendEmail(accountRequest.getEmail(), subject, content);
    }

    @Override
    public OtpAcceptResponse acceptOtp(AcceptOtpRequest acceptOtpRequest) {
        String email = "OTP: "+ acceptOtpRequest.getEmail();
        String otp = authRedisTemplate.opsForValue().get(email);
        if (otp == null)
            throw new ECommerceException(ErrorCode.AUTH_02);
        if (!(acceptOtpRequest.getOtp().equals(otp)))
            throw new ECommerceException(ErrorCode.AUTH_04);
        authRedisTemplate.delete(email);
        String uuid = UUID.randomUUID().toString();
        authRedisTemplate.opsForValue().set(uuid, acceptOtpRequest.getEmail(), 5, TimeUnit.MINUTES);
        return OtpAcceptResponse.builder()
                .token(uuid)
                .build();
    }

    @Override
    public void resetPassword(ResetPasswordRequest resetPasswordRequest) {
        String email = authRedisTemplate.opsForValue().get(resetPasswordRequest.getToken());
        if (email == null)
            throw new ECommerceException(ErrorCode.AUTH_01);
        Account account = accountRepository.findByEmail(email);
        if (account == null)
            throw new ECommerceException(ErrorCode.AUTH_01);
        String hashPassword = account.getPassword();
        Boolean isPass = passwordEncoder.matches(resetPasswordRequest.getPassword(), hashPassword);
        if (isPass)
            throw new ECommerceException(ErrorCode.AUTH_05);
        account.setPassword(passwordEncoder.encode(resetPasswordRequest.getPassword()));
        accountRepository.save(account);
        authRedisTemplate.delete(email);
    }

}
