package com.infe.app.domain.meeting;

import com.infe.app.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String passkey;

    private Double lat;

    private Double lon;

    @ManyToMany
    @JoinTable(name = "meeting_member",
            joinColumns = @JoinColumn(name = "meeting_id"),
            inverseJoinColumns = @JoinColumn(name = "member_id"))
    private List<Member> members = new ArrayList<>();

    @CreatedDate
    private LocalDateTime createdDateTime;

    private LocalDateTime endDateTime;

    @Builder
    public Meeting(String passkey, Double lat, Double lon, LocalDateTime createdDateTime, LocalDateTime endDateTime) {
        this.passkey = passkey;
        this.lat = lat;
        this.lon = lon;
        this.createdDateTime = createdDateTime;
        this.endDateTime = endDateTime;
    }

    public void addMember(Member member) {
        if (!this.members.contains(member)) {
            members.add(member);
            if (!member.getMeetings().contains(this))
                member.getMeetings().add(this);
        }
    }

    public void deleteMember(Member member) {
        if (this.members.contains(member)) {
            members.remove(this);
            if (this.members.contains(member))
                this.members.remove(member);
        }
    }
}