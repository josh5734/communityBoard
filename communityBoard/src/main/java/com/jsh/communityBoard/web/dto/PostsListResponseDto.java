package com.jsh.communityBoard.web.dto;
import com.jsh.communityBoard.domain.posts.Post;
import lombok.Data;
import lombok.Getter;
import org.apache.tomcat.jni.Local;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Getter
@Data
public class PostsListResponseDto {
    private Long id;
    private UserResponseDto user;
    private CategoryResponseDto category;
    private String title;
    private String content;
    private Integer viewCount;
    private Integer postLikesCount;
    private Integer commentCount;
    private LocalDateTime createdDate;
    private String adjustedCreatedDate;
    private LocalDateTime modifiedDate;

    public PostsListResponseDto(Post entity){
        this.id = entity.getId();
        this.user = new UserResponseDto(entity.getUser());
        this.category = new CategoryResponseDto(entity.getCategory());
        this.title = entity.getTitle();
        this.content= entity.getContent();
        this.viewCount = entity.getViewCount();
        this.postLikesCount = entity.getPostLikes().size();
        this.commentCount = entity.getCommentList().size();
        this.createdDate = entity.getCreatedDate();
        this.modifiedDate = entity.getModifiedDate();
    }

    /* 시간 표현 양식 조정하기
     * 1시간 이내에 작성된 글이면 "몇분 전"
     * 오늘 작성된 글이라면 "오늘 15시 34분"
     * 오늘 이전에 작성된 글이면 "2020년 3월 14일"
     */
    public void adjustCreatedTime(){
        LocalDateTime created = this.createdDate;
        LocalDateTime now = LocalDateTime.now();
        if(created.isAfter(now.minusHours(1L))){
            LocalDateTime nowByMinute = now.truncatedTo(ChronoUnit.MINUTES);
            LocalDateTime createdByMinute = created.truncatedTo(ChronoUnit.MINUTES);
            if(createdByMinute.isAfter(nowByMinute.minusMinutes(1L))){
                this.adjustedCreatedDate = "방금 전";
            }else{
                Duration duration = Duration.between(created, now);
                this.adjustedCreatedDate = (duration.getSeconds() / 60) + "분 전";
            }
        }else if(created.truncatedTo(ChronoUnit.DAYS).isEqual(now.truncatedTo(ChronoUnit.DAYS))){
            this.adjustedCreatedDate = this.createdDate.format(DateTimeFormatter.ofPattern("hh시 mm분"));
        }else{
            this.adjustedCreatedDate = this.createdDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"));
        }
    }
}
