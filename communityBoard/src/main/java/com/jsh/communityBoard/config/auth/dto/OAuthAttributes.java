package com.jsh.communityBoard.config.auth.dto;


import com.jsh.communityBoard.domain.user.Role;
import com.jsh.communityBoard.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture){
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }


    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes){
        if("naver".equals(registrationId)){
            return ofNaver("id", attributes);
        }else if("Kakao".equals(registrationId)){
            return ofKakao("id", attributes);
        }

        return ofGoogle(userNameAttributeName, attributes);
    }

    /*
     * OAuth2User에서 반환하는 사용자 정보는 Map이기 때문에 하나하나를 반환
     */
    public static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes){
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuthAttributes.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .picture((String) response.get("profile_image"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes){
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) attributes.get("profile");
        profile.put("email", kakaoAccount.get("email"));
        profile.put("name", profile.get("nickname"));
        profile.put("id", attributes.get("nickname"));

        return OAuthAttributes.builder()
                .name((String) profile.get("name"))
                .email((String) profile.get("email"))
                .attributes(profile)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }


    /*
     * User 엔티티 생성
     * OAuthAttributes에서 엔티티를 생성하는 시점은 처음 가입할 때
     * 가입할 때 권한을 GUEST로 주기 위해 role 빌더값에는 Role.GUEST 사용
     */
    public User toEntity(){
        return User.builder()
                .nickname(name)
                .email(email)
                .picture(picture)
                .role(Role.USER)
                .build();
    }
}
