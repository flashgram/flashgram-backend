package com.app.flashgram.post.repository.entity.comment;

import com.app.flashgram.common.domain.PositiveIntegerCounter;
import com.app.flashgram.common.repository.entity.TimeBaseEntity;
import com.app.flashgram.post.domain.comment.Comment;
import com.app.flashgram.post.domain.content.CommentContent;
import com.app.flashgram.post.repository.entity.post.PostEntity;
import com.app.flashgram.user.repository.entity.UserEntity;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "fg_comment")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CommentEntity extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "author_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private UserEntity author;

    @ManyToOne
    @JoinColumn(name = "post_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private PostEntity post;

    private String content;
    private Integer likeCount;

    public CommentEntity(Comment comment) {
        this.id = comment.getId();
        this.author = new UserEntity(comment.getAuthor());
        this.post = new PostEntity(comment.getPost());
        this.content = comment.getContent();
        this.likeCount = comment.getLikeCount();
    }

    public Comment toComment() {
        return Comment.builder()
                .id(id)
                .author(author.toUser())
                .post(post.toPost())
                .content(new CommentContent(content))
                .likeCount(new PositiveIntegerCounter(likeCount))
                .build();
    }
}
